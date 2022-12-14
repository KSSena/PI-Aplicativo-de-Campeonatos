/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.dao;

import br.senac.sp.model.Campeonato;
import br.senac.sp.model.Equipe;
import br.senac.sp.utils.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * *
 * 
 * @author Kaio
 */
public class CampeonatoDAO {

    /**
     * Método para adição de campeonato no banco de dados
     * 
     * @param campeonato Objeto Campeonato para inserção no banco de dados
     * @param uuid       UUid do usuário para registrar usuario como organizador do
     *                   campeonato
     * @return True = Sucesso e False = Falha
     */
    public static boolean adicionar(Campeonato campeonato, String uuid) {
        boolean retorno;
        Connection conexao = null;
        PreparedStatement comandoSQL1 = null;
        PreparedStatement comandoSQL2 = null;

        try {
            conexao = Conexao.abrirConexao();

            comandoSQL1 = conexao.prepareStatement(
                    "INSERT INTO campeonato(nome, qtd_times, categoria, descricao, data_inicial, data_final ) VALUES (?,?, ?, ?, ?, ?)");
            comandoSQL2 = conexao.prepareStatement(
                    "INSERT INTO organiza(fk_campeonato_id, fk_login_uuid) VALUES (LAST_INSERT_ID(),?)");

            comandoSQL1.setString(1, campeonato.getNome());
            comandoSQL1.setInt(2, campeonato.getQtdTimes());
            comandoSQL1.setString(3, campeonato.getCategoria());
            comandoSQL1.setString(4, campeonato.getDescricao());
            comandoSQL1.setDate(5, new java.sql.Date(campeonato.getDataInicial().getTime()));
            comandoSQL1.setDate(6, new java.sql.Date(campeonato.getDataFinal().getTime()));

            comandoSQL2.setString(1, uuid);

            int linhasAfetadas = comandoSQL1.executeUpdate() + comandoSQL2.executeUpdate();

            if (linhasAfetadas >= 2) {
                retorno = true;
            } else {
                retorno = false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = false;
        } finally {
            try {
                if (comandoSQL1 != null || comandoSQL1 != null) {
                    comandoSQL1.close();
                    comandoSQL2.close();
                }
                Conexao.fecharConexao();
            } catch (SQLException e) {
            }
        }
        return retorno;
    }

    /**
     * Método para atualização de campeonato no banco de dados
     * 
     * @param campeonato      Objeto Campeonato para atualização no banco de dados
     * @param uuidOrganizador UUID do usuario para verificação
     * @return True = Sucesso e False = Falha
     */
    public static boolean atualizar(Campeonato campeonato, String uuidOrganizador) {
        boolean retorno;
        Connection conexao = null;
        PreparedStatement comandoSQL1 = null;

        try {
            if (verificarOrganizador(campeonato.getId(), uuidOrganizador)) {
                conexao = Conexao.abrirConexao();
                comandoSQL1 = conexao.prepareStatement(
                        "UPDATE campeonato SET nome = ?, qtd_times = ?, categoria = ?, descricao = ?, data_inicial = ?, data_final = ? WHERE id = ? ");

                comandoSQL1.setString(1, campeonato.getNome());
                comandoSQL1.setInt(2, campeonato.getQtdTimes());
                comandoSQL1.setString(3, campeonato.getCategoria());
                comandoSQL1.setString(4, campeonato.getDescricao());
                comandoSQL1.setDate(5, new java.sql.Date(campeonato.getDataInicial().getTime()));
                comandoSQL1.setDate(6, new java.sql.Date(campeonato.getDataFinal().getTime()));
                comandoSQL1.setInt(7, campeonato.getId());

                int linhasAfetadas = comandoSQL1.executeUpdate();

                if (linhasAfetadas >= 1) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = false;
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = false;
        } finally {
            try {
                if (comandoSQL1 != null) {
                    comandoSQL1.close();
                }
                Conexao.fecharConexao();
            } catch (SQLException e) {
            }
        }

        return retorno;
    }

    /**
     * Método para buscar campeonatos em que o usuário participa
     * 
     * @param listaEquipes - Lista de equipes que o usuario participa
     * @return retorna lista de campeonatos que o usuario participa
     */
    public static ArrayList<Campeonato> carregarListaCampeonatos(ArrayList<Equipe> listaEquipes) {
        ArrayList<Campeonato> retorno = new ArrayList<>();
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {

            for (Equipe e : listaEquipes) {
                conexao = Conexao.abrirConexao();
                comandoSQL = conexao.prepareStatement(
                        "SELECT * FROM campeonato INNER JOIN compete ON campeonato.id = compete.fk_campeonato_id WHERE compete.fk_equipe_id = ?");

                comandoSQL.setInt(1, e.getId());

                rs = comandoSQL.executeQuery();

                while (rs.next()) {
                    Campeonato campeonato = new Campeonato(rs.getInt("campeonato.id"));
                    campeonato.setNome(rs.getString("campeonato.nome"));
                    retorno.add(campeonato);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (comandoSQL != null) {
                    comandoSQL.close();
                }

                Conexao.fecharConexao();

            } catch (SQLException e) {
            }
        }
        return retorno;
    }

    /**
     * Método para buscar campeonatos em que uma euipe participa
     * 
     * @param idEquipe - id da equipe
     * @return retorna lista de campeonatos que a equipe participa
     */
    public static ArrayList<Campeonato> carregarListaCampeonatos(int idEquipe) {
        ArrayList<Campeonato> retorno = new ArrayList<>();
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {

            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement(
                    "SELECT * FROM campeonato INNER JOIN compete ON campeonato.id = compete.fk_campeonato_id WHERE compete.fk_equipe_id = ?");

            comandoSQL.setInt(1, idEquipe);

            rs = comandoSQL.executeQuery();

            while (rs.next()) {
                Campeonato campeonato = new Campeonato(rs.getInt("campeonato.id"));
                campeonato.setNome(rs.getString("campeonato.nome"));
                retorno.add(campeonato);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (comandoSQL != null) {
                    comandoSQL.close();
                }

                Conexao.fecharConexao();

            } catch (SQLException e) {
            }
        }
        return retorno;
    }

    /**
     * Método para pesquisa de campeonatos
     * 
     * @param nome Parametro para pesquisa
     * @return Lista de campeonatos que possuem o nome pesquisado
     */
    public static ArrayList<Campeonato> buscarCampeonatos(String nome) {
        ArrayList<Campeonato> retorno = new ArrayList<>();
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {

            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement("SELECT * FROM campeonato WHERE nome LIKE ?");

            comandoSQL.setString(1, "%" + nome + "%");

            rs = comandoSQL.executeQuery();

            while (rs.next()) {
                Campeonato campeonato = new Campeonato(rs.getInt("campeonato.id"));
                campeonato.setNome(rs.getString("campeonato.nome"));
                retorno.add(campeonato);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (comandoSQL != null) {
                    comandoSQL.close();
                }

                Conexao.fecharConexao();

            } catch (SQLException e) {
            }
        }
        return retorno;
    }

    /**
     * Método para busca das informações de um campeonato no banco de dados
     * 
     * @param id Id do campeonato
     * @return retorna as informações do campeonato apartir do id
     */
    public static Campeonato carregarCampeonato(int id) {
        Campeonato retorno;
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement("SELECT * FROM campeonato WHERE id = ?");

            comandoSQL.setInt(1, id);

            rs = comandoSQL.executeQuery();

            if (rs.next()) {
                retorno = new Campeonato(rs.getInt("id"));
                retorno.setNome(rs.getString("nome"));
                retorno.setCategoria(rs.getString("categoria"));
                retorno.setDescricao(rs.getString("descricao"));
                retorno.setQtdTimes(rs.getInt("qtd_times"));
                retorno.setDataInicial(rs.getDate("data_inicial"));
                retorno.setDataFinal(rs.getDate("data_final"));
            } else {
                retorno = null;
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (comandoSQL != null) {
                    comandoSQL.close();
                }

                Conexao.fecharConexao();

            } catch (SQLException e) {
            }
        }
        return retorno;
    }

    /**
     * Método para carregar equipes participantes do campeonato
     * 
     * @param id Id do campeonato para pesquisa
     * @return retorna lista de equipes participantes
     */
    public static ArrayList<Equipe> carregarEquipesParticipantes(int id) {
        ArrayList<Equipe> retorno = new ArrayList<>();
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement(
                    "SELECT * FROM compete INNER JOIN equipe ON compete.fk_equipe_id = equipe.id WHERE compete.fk_campeonato_id = ?");

            comandoSQL.setInt(1, id);

            rs = comandoSQL.executeQuery();

            while (rs.next()) {
                Equipe equipe = new Equipe(rs.getInt("equipe.id"));
                equipe.setNome(rs.getString("equipe.nome"));
                retorno.add(equipe);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (comandoSQL != null) {
                    comandoSQL.close();
                }

                Conexao.fecharConexao();

            } catch (SQLException e) {
            }
        }
        return retorno;
    }

    /**
     * Método para exclusão de campeonato no banco de dados
     * 
     * @param idCampeonato    Id do campeonato para exclusão
     * @param uuidOrganizador UUID do organizador para verificação
     * @return True = Sucesso e False = Falha
     */
    public static boolean excluirCampeonato(int idCampeonato, String uuidOrganizador) {
        boolean retorno = false;
        Connection conexao = null;
        PreparedStatement comandoSQL1 = null;
        PreparedStatement comandoSQL2 = null;
        PreparedStatement comandoSQL3 = null;
        PreparedStatement comandoSQL4 = null;

        try {

            if (verificarOrganizador(idCampeonato, uuidOrganizador)) {
                conexao = Conexao.abrirConexao();

                comandoSQL1 = conexao.prepareStatement("DELETE FROM organiza WHERE fk_campeonato_id = ?");
                comandoSQL1.setInt(1, idCampeonato);

                comandoSQL2 = conexao.prepareStatement("DELETE FROM ocorre WHERE fk_campeonato_id = ?");
                comandoSQL2.setInt(1, idCampeonato);

                comandoSQL3 = conexao.prepareStatement("DELETE FROM compete WHERE fk_campeonato_id = ?");
                comandoSQL3.setInt(1, idCampeonato);

                comandoSQL4 = conexao.prepareStatement(" DELETE FROM campeonato WHERE id = ? ");
                comandoSQL4.setInt(1, idCampeonato);

                int linhasAfetadas = comandoSQL1.executeUpdate() + comandoSQL2.executeUpdate()
                        + comandoSQL3.executeUpdate() + comandoSQL4.executeUpdate();

                if (linhasAfetadas >= 4) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = false;
        } finally {
            try {
                if (comandoSQL1 != null || comandoSQL2 != null || comandoSQL3 != null || comandoSQL4 != null) {
                    comandoSQL1.close();
                    comandoSQL2.close();
                    comandoSQL3.close();
                    comandoSQL4.close();
                }

                Conexao.fecharConexao();

            } catch (SQLException e) {
            }
        }

        return retorno;
    }

    /**
     * Método para adição de equipe no campeonato
     * 
     * @param idCampeonato Id do campeonato que receberá a equipe
     * @param idEquipe     Id da equipe para adicionar no Campeonato
     * @return True = Sucesso e False = Falha
     */
    public static boolean adicionarEquipe(int idCampeonato, int idEquipe) {
        boolean retorno;
        Connection conexao = null;

        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            comandoSQL = conexao.prepareStatement("INSERT INTO compete (fk_campeonato_id, fk_equipe_id) VALUES (?, ?)");

            comandoSQL.setInt(1, idCampeonato);
            comandoSQL.setInt(2, idEquipe);

            int linhasAfetadas = comandoSQL.executeUpdate();
            if (linhasAfetadas > 0) {
                retorno = true;
            } else {
                retorno = false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = false;
        } finally {
            try {
                if (comandoSQL != null) {
                    comandoSQL.close();
                }
                Conexao.fecharConexao();
            } catch (SQLException e) {

            }
        }
        return retorno;
    }

    /**
     * Método para exclusão da equipe no campeonato
     * 
     * @param idCampeonato Id do campeonato para excluir a equipe
     * @param idEquipe     Id da equipe a ser excluida do campeonato
     * @return True = Sucesso e False = Falha
     */
    public static boolean excluirEquipe(int idCampeonato, int idEquipe) {
        boolean retorno = false;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        try {
            conexao = Conexao.abrirConexao();

            comandoSQL = conexao
                    .prepareStatement("DELETE FROM compete WHERE fk_campeonato_uuid = ? AND fk_equipe_id = ?");
            comandoSQL.setInt(1, idCampeonato);
            comandoSQL.setInt(2, idEquipe);

            int linhasAfetadas = comandoSQL.executeUpdate();

            if (linhasAfetadas >= 1) {
                retorno = true;
            } else {
                retorno = false;
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = false;
        } finally {
            try {
                if (comandoSQL != null) {
                    comandoSQL.close();
                }

                Conexao.fecharConexao();

            } catch (SQLException e) {
            }
        }

        return retorno;
    }

    /**
     * Método para verificação se o usuário é organizador
     * 
     * @param idCampeonato Id do campeonato para verificar
     * @param uuid         UUID do usuário para verificação
     * @return True = Sucesso e False = Falha
     */
    public static boolean verificarOrganizador(int idCampeonato, String uuid) {
        Boolean retorno = false;
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao
                    .prepareStatement("SELECT * FROM organiza WHERE fk_campeonato_id = ? AND fk_login_uuid = ?");

            comandoSQL.setInt(1, idCampeonato);
            comandoSQL.setString(2, uuid);

            rs = comandoSQL.executeQuery();

            retorno = rs.next();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (comandoSQL != null) {
                    comandoSQL.close();
                }

                Conexao.fecharConexao();

            } catch (SQLException e) {
            }
        }
        return retorno;
    }

    /**
     * Método para carregar equipes que o usuário é organizador
     * 
     * @param uuid         UUID do usuário
     * @param idCampeonato Id do campeonato
     * @return Lista de equipes
     */
    public static ArrayList<Equipe> carregarEquipesAptas(String uuid, int idCampeonato) {
        ArrayList<Equipe> retorno = new ArrayList<>();
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement(
                    "SELECT * FROM equipe INNER JOIN participa ON equipe.id = participa.fk_equipe_id  WHERE  participa.fk_login_uuid = ? AND participa.editor = true AND NOT EXISTS (SELECT fk_equipe_id FROM compete WHERE fk_Campeonato_ID = ?)");
            comandoSQL.setString(1, uuid);
            comandoSQL.setInt(2, idCampeonato);

            rs = comandoSQL.executeQuery();

            while (rs.next()) {
                Equipe equipe = new Equipe(rs.getInt("equipe.id"));
                equipe.setNome(rs.getString("equipe.nome"));
                retorno.add(equipe);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (comandoSQL != null) {
                    comandoSQL.close();
                }

                Conexao.fecharConexao();

            } catch (SQLException e) {
            }
        }
        return retorno;
    }

    /**
     * Método para carregar equipes que o usuário é organizador
     * 
     * @param uuid         UUID do usuário
     * @param idCampeonato Id do campeonato
     * @return Lista de equipes
     */
    public static ArrayList<Campeonato> carregarCampeonatosOrganiza(String uuid) {
        ArrayList<Campeonato> retorno = new ArrayList<>();
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement(
                    "SELECT * FROM campeonato INNER JOIN organiza ON campeonato.id = organiza.fk_campeonato_id WHERE organiza.fk_login_uuid = ?");
            comandoSQL.setString(1, uuid);

            rs = comandoSQL.executeQuery();

            while (rs.next()) {
                Campeonato campeonato = new Campeonato(rs.getInt("campeonato.id"));
                campeonato.setNome(rs.getString("campeonato.nome"));
                retorno.add(campeonato);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (comandoSQL != null) {
                    comandoSQL.close();
                }

                Conexao.fecharConexao();

            } catch (SQLException e) {
            }
        }
        return retorno;
    }
}

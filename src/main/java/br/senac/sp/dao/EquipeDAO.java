/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.dao;

import br.senac.sp.model.Equipe;
import br.senac.sp.utils.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Kaio
 */
public class EquipeDAO {

    /**
     * Método para adicionar Equipe no banco de dados
     * 
     * @param equipe Objeto equipe com as informações
     * @param uuid   UUID do usuário para adicionar como editor da equipe
     * @return True = Sucesso e False = Falha
     */
    public static boolean adicionar(Equipe equipe, String uuid) {
        boolean retorno;
        Connection conexao = null;
        PreparedStatement comandoSQL1 = null;
        PreparedStatement comandoSQL2 = null;

        try {
            conexao = Conexao.abrirConexao();

            comandoSQL1 = conexao.prepareStatement(
                    "INSERT INTO equipe(nome, qtd_membros, categoria, descricao ) VALUES (?,?, ?, ?)");
            comandoSQL2 = conexao.prepareStatement(
                    "INSERT INTO participa(fk_equipe_id, fk_login_uuid, editor) VALUES (LAST_INSERT_ID(),?,?)");

            comandoSQL1.setString(1, equipe.getNome());
            comandoSQL1.setInt(2, equipe.getQtdMembros());
            comandoSQL1.setString(3, equipe.getCategoria());
            comandoSQL1.setString(4, equipe.getDescricao());

            comandoSQL2.setString(1, uuid);
            comandoSQL2.setBoolean(2, true);

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
     * Método para alterar Equipe no banco de dados
     * 
     * @param equipe Objeto equipe com as informações
     * @return True = Sucesso e False = Falha
     */
    public static boolean atualizar(Equipe equipe) {
        boolean retorno;
        Connection conexao = null;
        PreparedStatement comandoSQL1 = null;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL1 = conexao.prepareStatement(
                    "UPDATE equipe SET nome = ?, qtd_membros = ?, categoria = ?, descricao = ? WHERE id = ? ");

            comandoSQL1.setString(1, equipe.getNome());
            comandoSQL1.setInt(2, equipe.getQtdMembros());
            comandoSQL1.setString(3, equipe.getCategoria());
            comandoSQL1.setString(4, equipe.getDescricao());
            comandoSQL1.setInt(5, equipe.getId());

            int linhasAfetadas = comandoSQL1.executeUpdate();

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
     * Método para carregar lista de equipes em que um usuário participa
     * 
     * @param uuid UUID do usuario para busca
     * @return Lista de equipes em que o usuario participa
     */
    public static ArrayList<Equipe> carregarListaEquipes(String uuid) {
        ArrayList<Equipe> retorno = new ArrayList<>();
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement(
                    "SELECT * FROM equipe INNER JOIN participa ON equipe.id = participa.fk_equipe_id WHERE participa.fk_login_uuid = ?");

            comandoSQL.setString(1, uuid);

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
     * Método para carregar informações de uma equipe no banco de dados
     * 
     * @param id ID da equipe
     * @return Objeto Equipe com as informações da equipe pesquisada
     */
    public static Equipe carregarEquipe(int id) {
        Equipe retorno;
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement(
                    "SELECT * FROM equipe INNER JOIN participa ON equipe.id = participa.fk_equipe_id WHERE equipe.id = ? AND participa.editor = true");

            comandoSQL.setInt(1, id);

            rs = comandoSQL.executeQuery();

            if (rs.next()) {
                retorno = new Equipe(rs.getInt("id"));
                retorno.setNome(rs.getString("nome"));
                retorno.setCategoria(rs.getString("categoria"));
                retorno.setDescricao(rs.getString("descricao"));
                retorno.setQtdMembros(rs.getInt("qtd_membros"));
                retorno.setUuidEditor(rs.getString("fk_login_uuid"));
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
     * Método para busca de equipes apartir de um parametro
     * 
     * @param nome Parametro de pesquisa
     * @return Lista de equipes com o parametro pesquisado
     */
    public static ArrayList<Equipe> buscarEquipes(String nome) {
        ArrayList<Equipe> retorno = new ArrayList<>();
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement("SELECT * FROM equipe WHERE nome LIKE ?");

            comandoSQL.setString(1, "%" + nome + "%");

            rs = comandoSQL.executeQuery();

            while (rs.next()) {
                Equipe equipe = new Equipe(rs.getInt("id"));
                equipe.setNome(rs.getString("nome"));
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
     * Método para excluir time de um banco de dados
     * 
     * @param Id Id do time a ser excluido
     * @return True = Sucesso e False = Falha
     */
    public static boolean excluirTime(int Id) {
        boolean retorno = false;
        Connection conexao = null;
        PreparedStatement comandoSQL1 = null;
        PreparedStatement comandoSQL2 = null;
        PreparedStatement comandoSQL3 = null;

        try {
            conexao = Conexao.abrirConexao();

            comandoSQL1 = conexao.prepareStatement("DELETE FROM compete WHERE fk_equipe_id = ?");
            comandoSQL1.setInt(1, Id);

            comandoSQL2 = conexao.prepareStatement("DELETE FROM participa WHERE fk_equipe_id = ?");
            comandoSQL2.setInt(1, Id);

            comandoSQL3 = conexao.prepareStatement(" DELETE FROM equipe WHERE id = ? ");
            comandoSQL3.setInt(1, Id);

            int linhasAfetadas = comandoSQL1.executeUpdate() + comandoSQL2.executeUpdate()
                    + comandoSQL3.executeUpdate();

            if (linhasAfetadas >= 3) {
                retorno = true;
            } else {
                retorno = false;
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = false;
        } finally {
            try {
                if (comandoSQL1 != null || comandoSQL2 != null || comandoSQL3 != null) {
                    comandoSQL1.close();
                    comandoSQL2.close();
                    comandoSQL3.close();
                }

                Conexao.fecharConexao();

            } catch (SQLException e) {
            }
        }

        return retorno;
    }

    /**
     * Método para adicionar integrante à uma equipe
     * @param idEquipe Id da equipe a receber o integrante
     * @param uuid UUID do membro a ser integrado
     * @return True = Sucesso e False = Falha
     */
    public static boolean adicionarIntegrante(int idEquipe, String uuid) {
        boolean retorno;
        Connection conexao = null;

        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            comandoSQL = conexao.prepareStatement("INSERT INTO participa (fk_equipe_id,fk_login_uuid) VALUES (?, ?)");

            comandoSQL.setInt(1, idEquipe);
            comandoSQL.setString(2, uuid);

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
     * Método para excluir integrante à uma equipe
     * @param idEquipe Id da equipe a excluir o integrante
     * @param uuid UUID do membro a ser excluido
     * @return True = Sucesso e False = Falha
     */
    public static boolean excluirIntegrante(int idEquipe, String uuid) {
        boolean retorno = false;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            comandoSQL = conexao.prepareStatement("DELETE FROM participa WHERE fk_equipe_id = ? AND fk_login_uuid = ?");
            comandoSQL.setInt(1, idEquipe);
            comandoSQL.setString(2, uuid);

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
}

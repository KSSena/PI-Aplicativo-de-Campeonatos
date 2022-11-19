/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.dao;

import br.senac.sp.model.Endereco;
import br.senac.sp.model.Usuario;
import br.senac.sp.utils.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Kaio
 */
public class UsuarioDAO {

    public static boolean adicionar(Usuario usuario) {
        boolean retorno;
        Connection conexao = null;
        PreparedStatement comandoSQL1 = null;
        PreparedStatement comandoSQL2 = null;
        PreparedStatement comandoSQL3 = null;
        PreparedStatement comandoSQL4 = null;
        UUID uuid = UUID.randomUUID();

        try {
            if (emailValido(usuario.getEmail())) {
                int enderecoID = EnderecoDAO.enderecoValido(usuario.getEndereco());
                conexao = Conexao.abrirConexao();
                comandoSQL1 = conexao.prepareStatement("INSERT INTO login(uuid, email, senha ) VALUES (?,?,MD5(?))");
                comandoSQL2 = conexao.prepareStatement("INSERT INTO usuario(fk_login_uuid, nome, cpf, data_de_nascimento) VALUES (?,?,?,?)");

                //Login
                comandoSQL1.setString(1, uuid.toString());
                comandoSQL1.setString(2, usuario.getEmail());
                comandoSQL1.setString(3, usuario.getSenha());

                //Usuario
                comandoSQL2.setString(1, uuid.toString());
                comandoSQL2.setString(2, usuario.getNome());
                comandoSQL2.setString(3, usuario.getCpf());
                comandoSQL2.setDate(4, new java.sql.Date(usuario.getNascimento().getTime()));

                if (enderecoID == -1) {
                    comandoSQL3 = conexao.prepareStatement("INSERT INTO endereco(logradouro, bairro,cep, uf, cidade, numero) VALUES (?,?,?,?,?,?)");
                    comandoSQL4 = conexao.prepareStatement("INSERT INTO reside(fk_endereco_id, fk_login_uuid) VALUES (LAST_INSERT_ID(), ?)");

                    //Endereço
                    comandoSQL3.setString(1, usuario.getEndereco().getLogradouro());
                    comandoSQL3.setString(2, usuario.getEndereco().getBairro());
                    comandoSQL3.setString(3, usuario.getEndereco().getCep());
                    comandoSQL3.setString(4, usuario.getEndereco().getUf());
                    comandoSQL3.setString(5, usuario.getEndereco().getCidade());
                    comandoSQL3.setString(6, usuario.getEndereco().getNumero());

                    //Reside
                    comandoSQL4.setString(1, uuid.toString());

                    int linhasAfetadas = comandoSQL1.executeUpdate() + comandoSQL2.executeUpdate() + comandoSQL3.executeUpdate() + comandoSQL4.executeUpdate();

                    if (linhasAfetadas >= 4) {
                        retorno = true;
                    } else {
                        retorno = false;
                    }

                } else {

                    comandoSQL3 = conexao.prepareStatement("INSERT INTO reside(fk_endereco_id, fk_login_uuid) VALUES (?, ?)");

                    //Reside
                    comandoSQL3.setInt(1, enderecoID);
                    comandoSQL3.setString(2, uuid.toString());
                    System.out.println("teste");

                    int linhasAfetadas = comandoSQL1.executeUpdate() + comandoSQL2.executeUpdate() + comandoSQL3.executeUpdate();

                    if (linhasAfetadas >= 3) {
                        retorno = true;
                    } else {
                        retorno = false;
                    }
                }
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
                if (comandoSQL4 != null) {
                    comandoSQL4.close();
                }
                Conexao.fecharConexao();
            } catch (SQLException e) {
            }
        }
        return retorno;
    }

    public static boolean atualizar(Usuario usuario) {
        boolean retorno;
        Connection conexao = null;
        PreparedStatement comandoSQL1 = null;
        PreparedStatement comandoSQL2 = null;
        PreparedStatement comandoSQL3 = null;

        try {
            if (emailValido(usuario.getEmail(), usuario.getUuid())) {
                int enderecoID = EnderecoDAO.enderecoValido(usuario.getEndereco());
                if (enderecoID != -1) {
                    conexao = Conexao.abrirConexao();
                    comandoSQL1 = conexao.prepareStatement("UPDATE usuario SET nome = ?, cpf = ?, data_de_nascimento = ? WHERE fk_login_uuid = ? ");
                    comandoSQL2 = conexao.prepareStatement("UPDATE reside SET fk_endereco_id = ? WHERE fk_login_uuid = ?");

                    comandoSQL1.setString(1, usuario.getNome());
                    comandoSQL1.setString(2, usuario.getCpf());
                    comandoSQL1.setDate(3, new java.sql.Date(usuario.getNascimento().getTime()));
                    comandoSQL1.setString(4, usuario.getUuid());

                    comandoSQL2.setInt(1, enderecoID);
                    comandoSQL2.setString(2, usuario.getUuid());

                    int linhasAfetadas = comandoSQL1.executeUpdate() + comandoSQL2.executeUpdate();

                    if (linhasAfetadas >= 2) {
                        retorno = true;
                    } else {
                        retorno = false;
                    }
                } else {
                    conexao = Conexao.abrirConexao();
                    comandoSQL1 = conexao.prepareStatement("UPDATE usuario SET nome = ?, cpf = ?, data_de_nascimento = ? WHERE fk_login_uuid = ? ");
                    comandoSQL2 = conexao.prepareStatement("INSERT INTO endereco(logradouro, bairro,cep, uf, cidade, numero) VALUES (?,?,?,?,?,?)");
                    comandoSQL3 = conexao.prepareStatement("UPDATE reside SET fk_endereco_id = LAST_INSERT_ID() WHERE fk_login_uuid = ?");

                    comandoSQL1.setString(1, usuario.getNome());
                    comandoSQL1.setString(2, usuario.getCpf());
                    comandoSQL1.setDate(3, new java.sql.Date(usuario.getNascimento().getTime()));
                    comandoSQL1.setString(4, usuario.getUuid());

                    comandoSQL2.setString(1, usuario.getEndereco().getLogradouro());
                    comandoSQL2.setString(2, usuario.getEndereco().getBairro());
                    comandoSQL2.setString(3, usuario.getEndereco().getCep());
                    comandoSQL2.setString(4, usuario.getEndereco().getUf());
                    comandoSQL2.setString(5, usuario.getEndereco().getCidade());
                    comandoSQL2.setString(6, usuario.getEndereco().getNumero());

                    comandoSQL3.setString(1, usuario.getUuid());

                    int linhasAfetadas = comandoSQL1.executeUpdate() + comandoSQL2.executeUpdate() + comandoSQL3.executeUpdate();

                    if (linhasAfetadas >= 2) {
                        retorno = true;
                    } else {
                        retorno = false;
                    }
                }
            } else {
                retorno = false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = false;
        } finally {
            try {
                if (comandoSQL1 != null || comandoSQL2 != null) {
                    comandoSQL1.close();
                    comandoSQL2.close();
                }
                Conexao.fecharConexao();
            } catch (SQLException e) {
            }
        }

        return retorno;
    }

    public static boolean emailValido(String email) {
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        boolean retorno;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement("SELECT email FROM login WHERE email = ?;");

            comandoSQL.setString(1, email);

            rs = comandoSQL.executeQuery();

            if (rs.next()) {
                retorno = false;
            } else {
                retorno = true;
            }

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

    public static boolean emailValido(String email, String uuid) {
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        boolean retorno;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement("SELECT * FROM login WHERE email = ?;");

            comandoSQL.setString(1, email);

            rs = comandoSQL.executeQuery();

            if (rs.next()) {
                if (uuid.equals(rs.getString("uuid"))) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = true;
            }

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

    public static Usuario carregarCartao(String uuid) {
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        Usuario retorno = new Usuario(uuid);

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement("SELECT usuario.nome, login.email FROM login "
                    + "INNER JOIN usuario ON usuario.fk_login_uuid = login.uuid WHERE login.uuid = ?;");

            comandoSQL.setString(1, uuid);

            rs = comandoSQL.executeQuery();

            if (rs.next()) {
                retorno.setNome(rs.getString("usuario.nome"));
                retorno.setEmail(rs.getString("login.email"));
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

    public static Usuario carregarUsuario(String uuid) {
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        Usuario retorno = new Usuario(uuid);

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement("SELECT * FROM login "
                    + "INNER JOIN usuario ON usuario.fk_login_uuid = login.uuid "
                    + "INNER JOIN reside ON login.uuid = reside.fk_login_uuid "
                    + "INNER JOIN endereco ON reside.fk_endereco_id = endereco.id "
                    + "WHERE login.uuid = ?;");

            comandoSQL.setString(1, uuid);

            rs = comandoSQL.executeQuery();

            if (rs.next()) {
                retorno.setEmail(rs.getString("login.email"));
                retorno.setNome(rs.getString("usuario.nome"));
                retorno.setCpf(rs.getString("usuario.cpf"));
                retorno.setNascimento(rs.getDate("usuario.data_de_nascimento"));
                retorno.setEndereco(new Endereco(rs.getInt("endereco.id"), rs.getString("endereco.logradouro"),
                        rs.getString("endereco.cep"), rs.getString("endereco.numero"),
                        rs.getString("endereco.bairro"), rs.getString("endereco.uf"), rs.getString("endereco.cidade")));
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

    public static ArrayList<Usuario> carregarListaMembros(int id) {
        ArrayList<Usuario> retorno = new ArrayList<>();
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement("SELECT * FROM participa INNER JOIN usuario ON participa.fk_login_uuid = usuario.fk_login_uuid WHERE participa.fk_equipe_id = ?");

            comandoSQL.setInt(1, id);

            rs = comandoSQL.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getString("usuario.fk_login_uuid"));
                usuario.setNome(rs.getString("usuario.nome"));
                retorno.add(usuario);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.dao;

import br.senac.sp.model.Endereco;
import br.senac.sp.utils.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Kaio
 */
public class EnderecoDAO {

    /**
     * Método para verificar se o endereço já existe no banco de dados
     * @param endereco
     * @return retorna id do endereço caso exista ou -1 caso não exista
     */
    public static int enderecoValido(Endereco endereco) {
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        int retorno;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement("SELECT * FROM endereco WHERE logradouro = ? AND bairro = ? AND cep = ? AND uf = ? AND cidade = ? AND numero = ?;");

            comandoSQL.setString(1, endereco.getLogradouro());
            comandoSQL.setString(2, endereco.getBairro());
            comandoSQL.setString(3, endereco.getCep());
            comandoSQL.setString(4, endereco.getUf());
            comandoSQL.setString(5, endereco.getCidade());
            comandoSQL.setString(6, endereco.getNumero());

            rs = comandoSQL.executeQuery();

            if (rs.next()) {
                retorno = rs.getInt("id");
            } else {
                retorno = -1;
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            retorno = -1;
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

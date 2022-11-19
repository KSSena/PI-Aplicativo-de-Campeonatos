/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.dao;

import br.senac.sp.utils.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Kaio
 */
public class LoginDAO {

    public static String login(String email, String senha) {
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        String retorno;

        try {
            conexao = Conexao.abrirConexao();
            comandoSQL = conexao.prepareStatement("SELECT * FROM login WHERE email = ? AND senha = MD5(?);");

            comandoSQL.setString(1, email);
            comandoSQL.setString(2, senha);

            rs = comandoSQL.executeQuery();
            
            if(rs.next()){
                retorno = rs.getString("uuid");
            }else{
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

}

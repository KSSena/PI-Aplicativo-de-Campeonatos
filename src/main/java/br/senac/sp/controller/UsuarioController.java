/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.controller;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.dao.EquipeDAO;
import br.senac.sp.dao.UsuarioDAO;
import br.senac.sp.model.Endereco;
import br.senac.sp.model.Usuario;
import java.util.Date;

/**
 *
 * @author Kaio
 */
public class UsuarioController {

    public static boolean cadastrar(String email, String senha, String nome, String cpf, Date nascimento, String logradouro, String cep, String numero, String bairro, String uf, String cidade) {
        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setNascimento(nascimento);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setEndereco(new Endereco(logradouro, cep, numero, bairro, uf, cidade));
        
        return UsuarioDAO.adicionar(usuario);
    }

    public static boolean alterar(String uuid, String email, String senha, String nome, String cpf, Date nascimento, String logradouro, String cep, String numero, String bairro, String uf, String cidade) {
        Usuario usuario = new Usuario(uuid);

        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setNascimento(nascimento);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setEndereco(new Endereco(logradouro, cep, numero, bairro, uf, cidade));
        
        return UsuarioDAO.atualizar(usuario);
    }

    public static Usuario carregarTelaInicial(String uuid) {
        Usuario usuario = UsuarioDAO.carregarCartao(uuid);
        usuario.setListaEquipes(EquipeDAO.carregarListaEquipes(uuid));
        usuario.setListaCampeonato(CampeonatoDAO.carregarListaCampeonatos(usuario.getListaEquipes()));
        
        return usuario;
    }

    public static Usuario carregarPerfil(String uuid) {
        return UsuarioDAO.carregarUsuario(uuid);
    }
    
}

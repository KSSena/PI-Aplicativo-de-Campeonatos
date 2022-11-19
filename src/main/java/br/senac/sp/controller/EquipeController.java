/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.controller;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.dao.EquipeDAO;
import br.senac.sp.dao.UsuarioDAO;
import br.senac.sp.model.Equipe;

/**
 *
 * @author Kaio
 */
public class EquipeController {

    public static boolean criar(String nome, int qtdMembros, String categoria, String descricao, String uuid) {
        Equipe equipe = new Equipe();

        equipe.setNome(nome);
        equipe.setQtdMembros(qtdMembros);
        equipe.setCategoria(categoria);
        equipe.setDescricao(descricao);

        return EquipeDAO.adicionar(equipe, uuid);
    }

    public static boolean alterar(int id, String nome, int qtdMembros, String categoria, String descricao, String uuid) {
        Equipe equipe = new Equipe(id);

        equipe.setNome(nome);
        equipe.setQtdMembros(qtdMembros);
        equipe.setCategoria(categoria);
        equipe.setDescricao(descricao);

        return EquipeDAO.atualizar(equipe);
    }

    public static Equipe carregarTime(int id) {
        Equipe equipe = EquipeDAO.carregarEquipe(id);
        equipe.setListaMembros(UsuarioDAO.carregarListaMembros(id));
        equipe.setListaCampeonatos(CampeonatoDAO.carregarListaCampeonatos(id));

        return equipe;
    }

    public static Equipe carregarEditarTime(int id) {
        return EquipeDAO.carregarEquipe(id);
    }

    public static boolean excluirTime(int id) {
        return EquipeDAO.excluirTime(id);
    }

    public static boolean adicionarIntegrante(int idEquipe, String uuid) {
        return EquipeDAO.adicionarIntegrante(idEquipe, uuid);
    }

    public static boolean excluirIntegrante(int idEquipe, String uuid) {
        return EquipeDAO.excluirIntegrante(idEquipe, uuid);
    }
}

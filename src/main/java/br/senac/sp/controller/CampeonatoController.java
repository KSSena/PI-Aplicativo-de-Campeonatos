/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.controller;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.model.Campeonato;
import java.util.Date;

/**
 *
 * @author Kaio
 */
public class CampeonatoController {

    public static boolean criar(String nome, int qtdTimes, String categoria, String descricao, Date DataInicial, Date DataFinal, String uuid) {
        Campeonato campeonato = new Campeonato();

        campeonato.setNome(nome);
        campeonato.setQtdTimes(qtdTimes);
        campeonato.setCategoria(categoria);
        campeonato.setDescricao(descricao);
        campeonato.setDataInicial(DataInicial);
        campeonato.setDataFinal(DataFinal);

        return CampeonatoDAO.adicionar(campeonato, uuid);

    }

    public static boolean alterar(int id, String nome, int qtdTimes, String categoria, String descricao, Date DataInicial, Date DataFinal, String uuid) {
        Campeonato campeonato;
        //Id
        campeonato = new Campeonato(id);

        //Dados Basicos
        campeonato.setNome(nome);
        campeonato.setQtdTimes(qtdTimes);
        campeonato.setCategoria(categoria);
        campeonato.setDescricao(descricao);
        campeonato.setDataInicial(DataInicial);
        campeonato.setDataFinal(DataFinal);

        return CampeonatoDAO.atualizar(campeonato, uuid);

    }
    
    public static Campeonato carregarCampeonato(int id) {
        Campeonato campeonato = CampeonatoDAO.carregarCampeonato(id);
        campeonato.setListaEquipes(CampeonatoDAO.carregarEquipesParticipantes(id));
        return campeonato;
    }
    
    public static Campeonato carregarEditarCampeonato(int id){
        return CampeonatoDAO.carregarCampeonato(id);
    }
    
    public static boolean excluirCampeonato(int id, String uuid){
        return CampeonatoDAO.excluirCampeonato(id, uuid);
    }
    
    public static boolean adicionarEquipe(int idCampeonato, int idEquipe){
        return CampeonatoDAO.adicionarEquipe(idCampeonato, idEquipe);
    }
    
    public static boolean excluirEquipe(int idCampeonato, int idEquipe){
        return CampeonatoDAO.excluirEquipe(idCampeonato, idEquipe);
    }
}

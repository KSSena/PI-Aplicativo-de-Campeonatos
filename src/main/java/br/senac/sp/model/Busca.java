/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.model;

import java.util.ArrayList;

/**
 *
 * @author Kaio
 */
public class Busca {

    private ArrayList<Equipe> listaEquipes;
    private ArrayList<Campeonato> listaCampeonato;

    public Busca() {
    }

    public ArrayList<Equipe> getListaEquipes() {
        return listaEquipes;
    }

    public void setListaEquipes(ArrayList<Equipe> listaEquipes) {
        this.listaEquipes = listaEquipes;
    }

    public ArrayList<Campeonato> getListaCampeonato() {
        return listaCampeonato;
    }

    public void setListaCampeonato(ArrayList<Campeonato> listaCampeonato) {
        this.listaCampeonato = listaCampeonato;
    }
    
    
}

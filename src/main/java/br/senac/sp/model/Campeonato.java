/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Kaio
 */
public class Campeonato {
    private int id;
    private String nome;
    private String local;
    private String categoria;
    private String descricao;
    private Date DataInicial;
    private Date DataFinal;
    private int qtdTimes;
    private ArrayList<Equipe> listaEquipes;

    public Campeonato(int id) {
        this.id = id;
    }

    public Campeonato() {
    }

    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInicial() {
        return DataInicial;
    }

    public void setDataInicial(Date DataInicial) {
        this.DataInicial = DataInicial;
    }

    public Date getDataFinal() {
        return DataFinal;
    }

    public void setDataFinal(Date DataFinal) {
        this.DataFinal = DataFinal;
    }
    
    
    public int getQtdTimes() {
        return qtdTimes;
    }

    public void setQtdTimes(int qtdTimes) {
        this.qtdTimes = qtdTimes;
    }

    public ArrayList<Equipe> getListaEquipes() {
        return listaEquipes;
    }

    public void setListaEquipes(ArrayList<Equipe> listaEquipes) {
        this.listaEquipes = listaEquipes;
    }

    @Override
    public String toString() {
        return getNome();
    }
    
}

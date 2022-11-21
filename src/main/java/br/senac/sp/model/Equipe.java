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
public class Equipe {
    private int id;
    private String nome;
    private int qtdMembros;
    private String uuidEditor;
    private String categoria;
    private String descricao;
    private ArrayList<Usuario> listaMembros;
    private ArrayList<Campeonato> listaCampeonatos;
    

    public Equipe(int id) {
        this.id = id;
    }

    public Equipe() {
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

    public int getQtdMembros() {
        return qtdMembros;
    }

    public void setQtdMembros(int qtdMembros) {
        this.qtdMembros = qtdMembros;
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

    public ArrayList<Usuario> getListaMembros() {
        return listaMembros;
    }

    public void setListaMembros(ArrayList<Usuario> listaMembros) {
        this.listaMembros = listaMembros;
    }

    public ArrayList<Campeonato> getListaCampeonatos() {
        return listaCampeonatos;
    }

    public void setListaCampeonatos(ArrayList<Campeonato> listaCampeonatos) {
        this.listaCampeonatos = listaCampeonatos;
    }

    public String getUuidEditor() {
        return uuidEditor;
    }

    public void setUuidEditor(String uuidEditor) {
        this.uuidEditor = uuidEditor;
    }

    @Override
    public String toString() {
        return getNome();
    }

    
    
}

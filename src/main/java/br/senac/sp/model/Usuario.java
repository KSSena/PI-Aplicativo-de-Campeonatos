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
public class Usuario {
    private String uuid;
    private String email;
    private String senha;
    private String nome;
    private String cpf;
    private Date nascimento;
    private Endereco endereco;
    private ArrayList<Equipe> listaEquipes;
    private ArrayList<Campeonato> listaCampeonato;
    private boolean editor;

    public Usuario() {
    }

    public Usuario(String uuid) {
        this.uuid = uuid;
    }
    

    public String getUuid() {
        return uuid;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public boolean isEditor() {
        return editor;
    }

    public void setEditor(boolean editor) {
        this.editor = editor;
    }
    
    @Override
    public String toString(){
        return "Nome: " + getNome() + "\nCPF: " + getCpf() + "\nData de Nascimento: " + getNascimento() 
                + "\nEndereco: " + getEndereco().getLogradouro();
    }
}

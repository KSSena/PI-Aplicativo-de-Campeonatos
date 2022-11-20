/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.dao.EquipeDAO;
import br.senac.sp.model.Busca;

/**
 *
 * @author Kaio
 */
public class BuscaController {
    
    public static Busca buscar(String nome) {
        Busca retorno = new Busca();
        retorno.setListaCampeonato(CampeonatoDAO.buscarCampeonatos(nome));
        retorno.setListaEquipes(EquipeDAO.buscarEquipes(nome));
        
        return retorno;
    }
}

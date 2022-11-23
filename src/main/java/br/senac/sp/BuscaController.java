/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp;

import java.io.IOException;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.dao.EquipeDAO;
import br.senac.sp.model.Campeonato;
import br.senac.sp.model.Equipe;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author Kaio
 */
public class BuscaController {

    @FXML
    ListView<Campeonato> listViewCampeonatos;
    @FXML
    ListView<Equipe> listViewEquipes;
    @FXML
    TextField textFieldPesquisa;


    /**
     * Método para buscar campeonatos e equipes 
     */
    public void buscar() {
        String pesquisa = textFieldPesquisa.getText();
        listViewCampeonatos.getItems().setAll(CampeonatoDAO.buscarCampeonatos(pesquisa));
        listViewEquipes.getItems().setAll(EquipeDAO.buscarEquipes(pesquisa));
    }

    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }

    @FXML
    private void switchToTime() throws IOException {
        if (listViewEquipes.getSelectionModel().getSelectedItem() != null) {
            int id = listViewEquipes.getSelectionModel().getSelectedItem().getId();
            App.idTime = id;
            App.setRoot("time");
        }
    }

    @FXML
    private void switchToCampeonato() throws IOException {
        if (listViewCampeonatos.getSelectionModel().getSelectedItem() != null) {
            int id = listViewCampeonatos.getSelectionModel().getSelectedItem().getId();
            App.idCampeonato = id;
            App.setRoot("campeonato");
        }
    }
}

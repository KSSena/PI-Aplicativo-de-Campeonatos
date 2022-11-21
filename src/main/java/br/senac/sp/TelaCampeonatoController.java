package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.model.Campeonato;
import br.senac.sp.model.Equipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class TelaCampeonatoController implements Initializable{
    @FXML Text textNome;
    @FXML TextArea textAreaDescricao;
    @FXML Text textQuantidade;
    @FXML Text textCategoria;
    @FXML Text textEditar;
    @FXML Button buttonInscreverEquipe;
    @FXML ComboBox<Equipe> comboBoxInscrever;
    ObservableList<Equipe> listaEquipesAptas;

    public void carregar(int id){
        Campeonato campeonato = CampeonatoDAO.carregarCampeonato(id);
        campeonato.setListaEquipes(CampeonatoDAO.carregarEquipesParticipantes(id));
        
        textNome.setText(campeonato.getNome());
        textQuantidade.setText("Quantidade Máxima de Equipes: " + campeonato.getQtdTimes());
        textCategoria.setText("Categoria: " + campeonato.getCategoria());
        textAreaDescricao.setText(campeonato.getDescricao());

        if(!CampeonatoDAO.verificarOrganizador(id, App.uuid)){
            textEditar.setVisible(false);
        };

        listaEquipesAptas = FXCollections.observableArrayList(CampeonatoDAO.carregarEquipesAptas(App.uuid, id));
        
        comboBoxInscrever.setItems(listaEquipesAptas);

        for(Equipe e : listaEquipesAptas){
            System.out.println(e.getNome());
        }
    }

    @FXML
    private void switchToEditarCampeonato() throws IOException {
        App.setRoot("editarCampeonato");
        App.idCampeonato = 1;
    }    
    
    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar(App.idCampeonato);
    }

}

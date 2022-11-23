package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.dao.EquipeDAO;
import br.senac.sp.dao.UsuarioDAO;
import br.senac.sp.model.Campeonato;
import br.senac.sp.model.Equipe;
import br.senac.sp.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TelaInicioController implements Initializable{
    @FXML ImageView imageViewFoto;
    @FXML Text textNome;
    @FXML Text textEmail;
    @FXML Pane paneCartao;
    @FXML Text textCriarTime;
    @FXML ListView<Equipe> listViewTimes;
    @FXML ListView<Campeonato> listViewMeusCampeonatos;
    @FXML ListView<Campeonato> listViewCampeonatos;

    public void carregar(){
        Usuario usuario = UsuarioDAO.carregarCartao(App.uuid);
        ArrayList<Equipe> listaEquipes = EquipeDAO.carregarListaEquipes(App.uuid);
        usuario.setListaEquipes(listaEquipes);
        ArrayList<Campeonato> listaCampeonatos = CampeonatoDAO.carregarListaCampeonatos(usuario.getListaEquipes());
        ArrayList<Campeonato> listaCampeonatosOrganiza = CampeonatoDAO.carregarCampeonatosOrganiza(App.uuid);

        usuario.setListaCampeonato(listaCampeonatos);

        listViewTimes.getItems().setAll(listaEquipes);
        listViewCampeonatos.getItems().setAll(listaCampeonatos);
        listViewMeusCampeonatos.getItems().setAll(listaCampeonatosOrganiza);
        
        textNome.setText(usuario.getNome());
        textEmail.setText(usuario.getEmail());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar();
    }

    @FXML
    private void switchToPerfil() throws IOException {
        App.setRoot("perfil");
    }

    @FXML
    private void switchToCadastroTime() throws IOException {
        App.setRoot("cadastroTime");
    }
    
    @FXML
    private void switchToTime() throws IOException {   
        if(listViewTimes.getSelectionModel().getSelectedItem() != null){
            int id = listViewTimes.getSelectionModel().getSelectedItem().getId();
            App.idTime = id;
            App.setRoot("time");
        }
    }   
    
    @FXML
    private void switchToCadastroCampeonato() throws IOException {
        App.setRoot("cadastroCampeonato");
    }  
    

    @FXML
    private void switchToMeusCampeonato() throws IOException {
        if(listViewMeusCampeonatos.getSelectionModel().getSelectedItem() != null){
            int id = listViewMeusCampeonatos.getSelectionModel().getSelectedItem().getId();
            App.idCampeonato= id;
            App.setRoot("campeonato");
        }
    }
    @FXML
    private void switchToCampeonato() throws IOException {
        if(listViewCampeonatos.getSelectionModel().getSelectedItem() != null){
            int id = listViewCampeonatos.getSelectionModel().getSelectedItem().getId();
            App.idCampeonato= id;
            App.setRoot("campeonato");
        }
    }

    @FXML
    private void switchToBusca() throws IOException {
        App.setRoot("buscar");
    }   
    
}

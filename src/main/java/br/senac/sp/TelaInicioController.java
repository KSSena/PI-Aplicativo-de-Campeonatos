package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.dao.EquipeDAO;
import br.senac.sp.dao.UsuarioDAO;
import br.senac.sp.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TelaInicioController implements Initializable{
    @FXML ImageView imageViewFoto;
    @FXML Text textNome;
    @FXML Text textEmail;
    @FXML Pane paneCartao;
    @FXML Text textCriarTime;

    public void carregar(){
        Usuario usuario = UsuarioDAO.carregarCartao(App.uuid);
        usuario.setListaEquipes(EquipeDAO.carregarListaEquipes(App.uuid));
        usuario.setListaCampeonato(CampeonatoDAO.carregarListaCampeonatos(usuario.getListaEquipes()));

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
        App.idTime = 1;
        App.setRoot("time");
    }   
    
    @FXML
    private void switchToCadastroCampeonato() throws IOException {
        App.setRoot("cadastroCampeonato");
    }  
    
    @FXML
    private void switchToCampeonato() throws IOException {
        App.idCampeonato = 1;
        App.setRoot("campeonato");
    }   
    
}

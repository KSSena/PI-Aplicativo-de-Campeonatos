package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.senac.sp.controller.UsuarioController;
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
    Usuario usuario;

    public void carregar(){
        System.out.println(App.uuid);
        usuario = UsuarioController.carregarTelaInicial(App.uuid);
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
    
}

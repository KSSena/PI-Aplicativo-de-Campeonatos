package br.senac.sp;

import java.net.URL;
import java.util.ResourceBundle;

import br.senac.sp.controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class PrimaryController implements Initializable {
    @FXML TextField textFieldLogin;
    @FXML PasswordField passwordFieldSenha;
    @FXML Button botaoLogin;
    @FXML Text textCadastrar;
    @FXML ImageView imageViewLogo;

    public void logar(){
        String uuid = LoginController.logar(textFieldLogin.getText(), passwordFieldSenha.getText());
        if(uuid == null){
            mostrarMensagem(uuid , AlertType.ERROR);
       }else
            mostrarMensagem(uuid , AlertType.CONFIRMATION);
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void mostrarMensagem(String mensagem, AlertType tipo){
        var alerta = new Alert(tipo);
        alerta.setContentText(mensagem);
        alerta.show();
    }


}

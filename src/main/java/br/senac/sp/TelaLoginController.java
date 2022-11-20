package br.senac.sp;

import java.io.IOException;
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

public class TelaLoginController implements Initializable {
    @FXML TextField textFieldLogin;
    @FXML PasswordField passwordFieldSenha;
    @FXML Button botaoLogin;
    @FXML Text textCadastrar;
    @FXML ImageView imageViewLogo;

    public void logar() throws IOException{
        App.uuid = LoginController.logar(textFieldLogin.getText(), passwordFieldSenha.getText());
        if(App.uuid == null){
            mostrarMensagem("Email ou senha incorretos." , AlertType.ERROR);
       }else{
            mostrarMensagem("Login realizado com sucesso." , AlertType.CONFIRMATION);
            switchToTelaInicio();
        }  
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void mostrarMensagem(String mensagem, AlertType tipo){
        var alerta = new Alert(tipo);
        alerta.setContentText(mensagem);
        alerta.show();
    }

    @FXML
    private void switchToCadastro() throws IOException {
        App.setRoot("cadastro");
    }

    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }


}

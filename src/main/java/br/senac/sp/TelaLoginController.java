package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.senac.sp.dao.LoginDAO;
import br.senac.sp.utils.MessageFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        App.uuid = LoginDAO.login(textFieldLogin.getText(), passwordFieldSenha.getText());
        if(App.uuid == null){
            MessageFactory.mostrarMensagem("Email ou senha incorretos." , AlertType.ERROR);
       }else{
            MessageFactory.mostrarMensagem("Login realizado com sucesso." , AlertType.CONFIRMATION);
            switchToTelaInicio();
        }  
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) { 
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

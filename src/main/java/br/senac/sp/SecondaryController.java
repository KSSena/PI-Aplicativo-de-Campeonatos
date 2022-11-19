package br.senac.sp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SecondaryController {
    @FXML TextField textFieldEmail;
    @FXML PasswordField passwordFieldSenha;
    @FXML TextField textFieldNome;
    @FXML TextField textFieldCPF;
    @FXML DatePicker datePickerNascimento;
    @FXML TextField textFieldLogradouro;
    @FXML TextField textFieldCEP;
    @FXML TextField textFieldNumero;
    @FXML TextField textFieldBairro;
    @FXML Button buttonCadastrar;
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
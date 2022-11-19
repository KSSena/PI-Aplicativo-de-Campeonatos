package br.senac.sp;

import java.io.IOException;

import br.senac.sp.controller.UsuarioController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.time.ZoneId;
import java.util.Date;

public class SecondaryController{
    @FXML TextField textFieldEmail;
    @FXML PasswordField passwordFieldSenha;
    @FXML TextField textFieldNome;
    @FXML TextField textFieldCPF;
    @FXML DatePicker datePickerNascimento;
    @FXML TextField textFieldLogradouro;
    @FXML TextField textFieldCEP;
    @FXML TextField textFieldNumero;
    @FXML TextField textFieldBairro;
    @FXML ComboBox<String> comboBoxUF;
    @FXML TextField textFieldCidade;
    @FXML Button buttonCadastrar;
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }

    public void cadastrar(){
        UsuarioController.cadastrar(textFieldEmail.getText(), passwordFieldSenha.getText(), textFieldNome.getText(), 
            textFieldCPF.getText(), Date.from(datePickerNascimento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), textFieldLogradouro.getText(), textFieldCEP.getText(),
            textFieldNumero.getText(), textFieldBairro.getText(), comboBoxUF.getSelectionModel().getSelectedItem(), textFieldCidade.getText());
        System.out.println("Funcionou");    
    }
}
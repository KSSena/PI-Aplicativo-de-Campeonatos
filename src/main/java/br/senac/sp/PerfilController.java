package br.senac.sp;

import br.senac.sp.controller.UsuarioController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.time.ZoneId;
import java.util.Date;

public class PerfilController{
    private String uuid;
    @FXML ImageView imageViewFoto;
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

    public void alterar(){
        UsuarioController.alterar(App.uuid ,textFieldEmail.getText(), passwordFieldSenha.getText(), textFieldNome.getText(), 
            textFieldCPF.getText(), Date.from(datePickerNascimento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), textFieldLogradouro.getText(), textFieldCEP.getText(),
            textFieldNumero.getText(), textFieldBairro.getText(), comboBoxUF.getSelectionModel().getSelectedItem(), textFieldCidade.getText());
    }
}
package br.senac.sp;

import java.io.IOException;
import java.net.URL;

import br.senac.sp.dao.UsuarioDAO;
import br.senac.sp.model.Endereco;
import br.senac.sp.model.Usuario;
import br.senac.sp.utils.MessageFactory;
import br.senac.sp.utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class CadastroController implements Initializable {
    @FXML
    TextField textFieldEmail;
    @FXML
    PasswordField passwordFieldSenha;
    @FXML
    TextField textFieldNome;
    @FXML
    TextField textFieldCPF;
    @FXML
    DatePicker datePickerNascimento;
    @FXML
    TextField textFieldLogradouro;
    @FXML
    TextField textFieldCEP;
    @FXML
    TextField textFieldNumero;
    @FXML
    TextField textFieldBairro;
    @FXML
    ChoiceBox<String> choiceBoxUF;
    @FXML
    TextField textFieldCidade;
    @FXML
    Button buttonCadastrar;

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    public void cadastrar() throws IOException {
        if (!Validator.isEmpty(textFieldEmail) && !Validator.isEmpty(passwordFieldSenha) && !Validator.isEmpty(textFieldNome)
        && !Validator.isEmpty(textFieldCPF) && Validator.isFull(textFieldCPF, 11) && !Validator.isEmpty(datePickerNascimento)) {
            Usuario usuario = new Usuario();
            Endereco endereco = new Endereco();

            usuario.setNome(textFieldNome.getText());
            usuario.setCpf(textFieldCPF.getText());
            usuario.setNascimento(
                    Date.from(datePickerNascimento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            usuario.setEmail(textFieldEmail.getText());
            usuario.setSenha(passwordFieldSenha.getText());
            endereco.setLogradouro(textFieldLogradouro.getText());
            endereco.setCep(textFieldCEP.getText());
            endereco.setNumero(textFieldNumero.getText());
            endereco.setUf(choiceBoxUF.getSelectionModel().getSelectedItem());
            endereco.setCidade(textFieldCidade.getText());
            endereco.setBairro(textFieldBairro.getText());
            usuario.setEndereco(endereco);

            if (UsuarioDAO.adicionar(usuario)) {
                MessageFactory.mostrarMensagem("Cadastro Efetuado com Sucesso", AlertType.CONFIRMATION);
                switchToLogin();
            } else {
                MessageFactory.mostrarMensagem("Falha ao cadastrar", AlertType.ERROR);
            }
        }else{
            MessageFactory.mostrarMensagem("Preencher campos obrigatorios (*)", AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        choiceBoxUF.getItems().addAll("RO", "AC", "AM", "RR", "SP", "RJ");

        Validator.limitador(textFieldEmail, 30);
        Validator.limitador(passwordFieldSenha, 15);
        Validator.limitadorSomenteletras(textFieldNome, 50);
        Validator.limitadorSomenteNumeros(textFieldCPF, 11);
        Validator.limitadorSomenteletras(textFieldLogradouro, 50);
        Validator.limitadorSomenteNumeros(textFieldCEP, 8);
        Validator.limitadorSomenteNumeros(textFieldNumero, 10);
        Validator.limitadorSomenteletras(textFieldBairro, 30);
        Validator.limitadorSomenteletras(textFieldCidade, 30);

    }

}
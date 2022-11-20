package br.senac.sp;

import java.io.IOException;

import br.senac.sp.dao.UsuarioDAO;
import br.senac.sp.model.Endereco;
import br.senac.sp.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.time.ZoneId;
import java.util.Date;

public class CadastroController{
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

    public void mostrarMensagem(String mensagem, AlertType tipo){
        var alerta = new Alert(tipo);
        alerta.setContentText(mensagem);
        alerta.show();
    }

    public void cadastrar() throws IOException {
        Usuario usuario = new Usuario();
        Endereco endereco = new Endereco();

        usuario.setNome(textFieldNome.getText());
        usuario.setCpf(textFieldCPF.getText());
        usuario.setNascimento(Date.from(datePickerNascimento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        usuario.setEmail(textFieldEmail.getText());
        usuario.setSenha(passwordFieldSenha.getText());
        endereco.setLogradouro(textFieldLogradouro.getText());
        endereco.setCep(textFieldCEP.getText());
        endereco.setNumero(textFieldNumero.getText());
        endereco.setUf(comboBoxUF.getSelectionModel().getSelectedItem());
        endereco.setCidade(textFieldCidade.getText());
        endereco.setBairro(textFieldBairro.getText());
        usuario.setEndereco(endereco);
        
        if(UsuarioDAO.adicionar(usuario)){
            mostrarMensagem("Cadastro Efetuado com Sucesso", AlertType.CONFIRMATION);
            switchToPrimary();
        }else{
            mostrarMensagem("Falha ao cadastrar", AlertType.ERROR);
        };
    }


}
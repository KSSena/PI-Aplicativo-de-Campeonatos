package br.senac.sp;

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
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class PerfilController implements Initializable{
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
    @FXML ChoiceBox<String> choiceBoxUF;
    @FXML TextField textFieldCidade;
    @FXML Button buttonCadastrar;


    public void alterar() throws IOException {
        Usuario usuario = new Usuario(App.uuid);
        Endereco endereco = new Endereco();

        usuario.setNome(textFieldNome.getText());
        usuario.setCpf(textFieldCPF.getText());
        usuario.setNascimento(Date.from(datePickerNascimento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        usuario.setEmail(textFieldEmail.getText());
        usuario.setSenha(passwordFieldSenha.getText());
        endereco.setLogradouro(textFieldLogradouro.getText());
        endereco.setCep(textFieldCEP.getText());
        endereco.setNumero(textFieldNumero.getText());
        endereco.setUf(choiceBoxUF.getSelectionModel().getSelectedItem());
        endereco.setCidade(textFieldCidade.getText());
        endereco.setBairro(textFieldBairro.getText());
        usuario.setEndereco(endereco);
        
        if(UsuarioDAO.atualizar(usuario)){
            MessageFactory.mostrarMensagem("Alteração realizada com sucesso", AlertType.CONFIRMATION);
            switchToTelaInicio();
        }else
            MessageFactory.mostrarMensagem("Falha ao salvar alteração", AlertType.ERROR);
    }

    public void carregar(){
        Usuario usuario = UsuarioDAO.carregarUsuario(App.uuid);
        textFieldEmail.setText(usuario.getEmail());
        textFieldNome.setText(usuario.getNome());
        textFieldCPF.setText(usuario.getCpf());
        //datePickerNascimento.setValue(usuario.getNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        textFieldLogradouro.setText(usuario.getEndereco().getLogradouro());
        textFieldCEP.setText(usuario.getEndereco().getCep());
        textFieldNumero.setText(usuario.getEndereco().getNumero());
        textFieldBairro.setText(usuario.getEndereco().getBairro());
        choiceBoxUF.setValue(usuario.getEndereco().getUf());;
        textFieldCidade.setText(usuario.getEndereco().getCidade());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar();
        choiceBoxUF.getItems().addAll("RO","AC","AM", "RR" ,"SP", "RJ");

        Validator.limitador(textFieldEmail, 30);
        Validator.limitador(passwordFieldSenha, 15);
        Validator.limitadorSomenteletras(textFieldNome, 50);
        Validator.limitadorSomenteNumeros(textFieldCPF,  11);   
        Validator.limitadorSomenteletras(textFieldLogradouro, 50);
        Validator.limitadorSomenteNumeros(textFieldCEP,  8);
        Validator.limitadorSomenteNumeros(textFieldNumero,  10);
        Validator.limitadorSomenteletras(textFieldBairro, 30);
        Validator.limitadorSomenteletras(textFieldCidade, 30);
    }

    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }
}
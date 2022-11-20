package br.senac.sp;

import br.senac.sp.controller.UsuarioController;
import br.senac.sp.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    @FXML ComboBox<String> comboBoxUF;
    @FXML TextField textFieldCidade;
    @FXML Button buttonCadastrar;

    public void alterar(){
        if(UsuarioController.alterar(App.uuid ,textFieldEmail.getText(), passwordFieldSenha.getText(), textFieldNome.getText(), 
            textFieldCPF.getText(), Date.from(datePickerNascimento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), textFieldLogradouro.getText(), textFieldCEP.getText(),
            textFieldNumero.getText(), textFieldBairro.getText(), comboBoxUF.getSelectionModel().getSelectedItem(), textFieldCidade.getText())){
                mostrarMensagem("Alteração realizada com sucesso", AlertType.CONFIRMATION);
                try {
                    switchToTelaInicio();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        
            }else{
                mostrarMensagem("Falha ao salvar alteração", AlertType.ERROR);
            }
          
    }

    public void carregar(){
        Usuario usuario = UsuarioController.carregarPerfil(App.uuid);
        textFieldEmail.setText(usuario.getEmail());
        textFieldNome.setText(usuario.getNome());
        textFieldCPF.setText(usuario.getCpf());
        //datePickerNascimento.setValue(usuario.getNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        textFieldLogradouro.setText(usuario.getEndereco().getLogradouro());
        textFieldCEP.setText(usuario.getEndereco().getCep());
        textFieldNumero.setText(usuario.getEndereco().getNumero());
        textFieldBairro.setText(usuario.getEndereco().getBairro());
        //comboBoxUF.setSelectionModel(Sele);
        textFieldCidade.setText(usuario.getEndereco().getCidade());
    }

    public void mostrarMensagem(String mensagem, AlertType tipo){
        var alerta = new Alert(tipo);
        alerta.setContentText(mensagem);
        alerta.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar();
    }

    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }
}
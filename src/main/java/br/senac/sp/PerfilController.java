package br.senac.sp;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.dao.EquipeDAO;
import br.senac.sp.dao.UsuarioDAO;
import br.senac.sp.model.Endereco;
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
        endereco.setUf(comboBoxUF.getSelectionModel().getSelectedItem());
        endereco.setCidade(textFieldCidade.getText());
        endereco.setBairro(textFieldBairro.getText());
        usuario.setEndereco(endereco);
        
        if(UsuarioDAO.atualizar(usuario)){
            mostrarMensagem("Alteração realizada com sucesso", AlertType.CONFIRMATION);
            switchToTelaInicio();
        }else
            mostrarMensagem("Falha ao salvar alteração", AlertType.ERROR);
    }

    public void carregar(){
        Usuario usuario = UsuarioDAO.carregarCartao(App.uuid);
        usuario.setListaEquipes(EquipeDAO.carregarListaEquipes(App.uuid));
        usuario.setListaCampeonato(CampeonatoDAO.carregarListaCampeonatos(usuario.getListaEquipes()));

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
package br.senac.sp;

import java.io.IOException;

import br.senac.sp.dao.EquipeDAO;
import br.senac.sp.model.Equipe;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroTimeController {
    @FXML TextField textFieldNome;
    @FXML Spinner<Integer> spinnerQuantidade;
    @FXML TextField textFieldCategorias;
    @FXML TextArea textAreaDescricao;
    @FXML Button buttonCriar;

    public void mostrarMensagem(String mensagem, AlertType tipo){
        var alerta = new Alert(tipo);
        alerta.setContentText(mensagem);
        alerta.show();
    }

    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }

    public void cadastrar() throws IOException {
        Equipe equipe = new Equipe();

        equipe.setNome(textFieldNome.getText());
        equipe.setQtdMembros(1);
        equipe.setCategoria(textFieldCategorias.getText());
        equipe.setDescricao(textAreaDescricao.getText());

        if(EquipeDAO.adicionar(equipe, App.uuid)){
            mostrarMensagem("Criação de Equipe Efetuada com Sucesso", AlertType.CONFIRMATION);
            switchToTelaInicio();
        }else
            mostrarMensagem("Falha ao cadastrar", AlertType.ERROR);
    }
}

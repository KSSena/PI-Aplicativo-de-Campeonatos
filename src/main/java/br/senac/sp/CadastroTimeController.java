package br.senac.sp;

import java.io.IOException;

import br.senac.sp.controller.EquipeController;
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


    public void cadastrar() throws IOException{
        if(EquipeController.criar(textFieldNome.getText(), 1, textFieldCategorias.getText(), textAreaDescricao.getText(), App.uuid)){
            mostrarMensagem("Criação de Equipe Efetuada com Sucesso", AlertType.CONFIRMATION);
            switchToTelaInicio();
        }else{
            mostrarMensagem("Falha ao cadastrar", AlertType.ERROR);
        }
    }

    public void mostrarMensagem(String mensagem, AlertType tipo){
        var alerta = new Alert(tipo);
        alerta.setContentText(mensagem);
        alerta.show();
    }

    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }
}

package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.senac.sp.controller.EquipeController;
import br.senac.sp.model.Equipe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditarTimeController implements Initializable{
    @FXML TextField textFieldNome;
    @FXML Spinner<Integer> spinnerQuantidade;
    @FXML TextField textFieldCategoria;
    @FXML TextArea textAreaDescricao;
    @FXML Button buttonAlterar;

    public void carregar(int id){
        Equipe equipe = EquipeController.carregarTime(id);
        textFieldNome.setText(equipe.getNome());
        //spinnerQuantidade.set
        textFieldCategoria.setText(equipe.getCategoria());
        textAreaDescricao.setText(equipe.getDescricao());
    }

    public void alterar(){
        if(EquipeController.alterar(App.idTime, textFieldNome.getText(), 1, textFieldCategoria.getText(), textAreaDescricao.getText(), App.uuid)){
            mostrarMensagem("Alteração realizada com sucesso", AlertType.CONFIRMATION);
            try {
                switchToTime();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            mostrarMensagem("Falha ao salvar alteração", AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar(App.idTime);
    }

    public void mostrarMensagem(String mensagem, AlertType tipo){
        var alerta = new Alert(tipo);
        alerta.setContentText(mensagem);
        alerta.show();
    }

    @FXML
    private void switchToTime() throws IOException {
        App.setRoot("time");
    }
}

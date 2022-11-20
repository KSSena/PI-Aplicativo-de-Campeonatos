package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.senac.sp.controller.EquipeController;
import br.senac.sp.model.Equipe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class TimeController implements Initializable{
    @FXML Text textNome;
    @FXML TextArea textAreaDescricao;
    @FXML Text textQuantidade;
    @FXML Text textCategoria;

    public void carregar(int id){
        Equipe equipe = EquipeController.carregarTime(id);
        textNome.setText(equipe.getNome());
        textQuantidade.setText("Quantidade de membros: " + equipe.getQtdMembros());
        textCategoria.setText("Categoria: " + equipe.getCategoria());
        textAreaDescricao.setText(equipe.getDescricao());
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar(App.idTime);
    }

    @FXML
    private void switchToEditarTime() throws IOException {
        App.setRoot("editarTime");
        App.idTime = 1;
    }    
    
    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }

}

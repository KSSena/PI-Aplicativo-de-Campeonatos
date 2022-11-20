package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.senac.sp.controller.CampeonatoController;
import br.senac.sp.model.Campeonato;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class TelaCampeonatoController implements Initializable{
    @FXML Text textNome;
    @FXML TextArea textAreaDescricao;
    @FXML Text textQuantidade;
    @FXML Text textCategoria;

    public void carregar(int id){
        Campeonato campeonato = CampeonatoController.carregarCampeonato(id);
        textNome.setText(campeonato.getNome());
        textQuantidade.setText("Quantidade Máxima de Equipes: " + campeonato.getQtdTimes());
        textCategoria.setText("Categoria: " + campeonato.getCategoria());
        textAreaDescricao.setText(campeonato.getDescricao());
    }

    @FXML
    private void switchToEditarCampeonato() throws IOException {
        App.setRoot("editarCampeonato");
        App.idCampeonato = 1;
    }    
    
    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar(App.idCampeonato);
    }

}

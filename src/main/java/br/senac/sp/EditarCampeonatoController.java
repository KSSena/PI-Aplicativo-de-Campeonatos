package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.model.Campeonato;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditarCampeonatoController implements Initializable{
    @FXML TextField textFieldNome;
    @FXML Spinner<Integer> spinnerQuantidade;
    @FXML TextField textFieldCategoria;
    @FXML TextArea textAreaDescricao;
    @FXML DatePicker datePickerInicio;
    @FXML DatePicker datePickerFim;
    @FXML Button buttonAlterar;

    public void carregar(int id){
        Campeonato campeonato = CampeonatoDAO.carregarCampeonato(id);
        campeonato.setListaEquipes(CampeonatoDAO.carregarEquipesParticipantes(id));
        
        textFieldNome.setText(campeonato.getNome());
        //spinnerQuantidade.set
        textFieldCategoria.setText(campeonato.getCategoria());
        textAreaDescricao.setText(campeonato.getDescricao());
    }

    public void mostrarMensagem(String mensagem, AlertType tipo){
        var alerta = new Alert(tipo);
        alerta.setContentText(mensagem);
        alerta.show();
    }

    @FXML
    private void switchToCampeonato() throws IOException {
        App.setRoot("campeonato");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar(App.idCampeonato);
        
    }

    public void alterar() throws IOException {
        Campeonato campeonato;
        //Id
        campeonato = new Campeonato(App.idCampeonato);

        //Dados Basicos
        campeonato.setNome(textFieldNome.getText());
        campeonato.setQtdTimes(0);
        campeonato.setCategoria(textFieldCategoria.getText());
        campeonato.setDescricao(textAreaDescricao.getText());
        campeonato.setDataInicial(Date.from(datePickerInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        campeonato.setDataFinal(Date.from(datePickerFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        if(CampeonatoDAO.atualizar(campeonato, App.uuid)){
            mostrarMensagem("Alteração de campeonato efetuado com sucesso", AlertType.CONFIRMATION);
            switchToCampeonato();
        }else
            mostrarMensagem("Falha ao alterar campeonato.", AlertType.ERROR);
    }
}

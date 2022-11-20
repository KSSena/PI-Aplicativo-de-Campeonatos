package br.senac.sp;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.model.Campeonato;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroCampeonatoController {
    @FXML TextField textFieldNome;
    @FXML Spinner<Integer> spinnerQuantidade;
    @FXML TextField textFieldCategoria;
    @FXML TextArea textAreaDescricao;
    @FXML DatePicker datePickerInicio;
    @FXML DatePicker datePickerFim;
    @FXML Button buttonCriar;

    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }

    public void mostrarMensagem(String mensagem, AlertType tipo){
        var alerta = new Alert(tipo);
        alerta.setContentText(mensagem);
        alerta.show();
    }

    public void cadastrar() throws IOException {
        Campeonato campeonato = new Campeonato();

        campeonato.setNome(textFieldNome.getText());
        campeonato.setQtdTimes(0);
        campeonato.setCategoria(textFieldCategoria.getText());
        campeonato.setDescricao(textAreaDescricao.getText());
        campeonato.setDataInicial(Date.from(datePickerInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        campeonato.setDataFinal(Date.from(datePickerFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        if(CampeonatoDAO.adicionar(campeonato, App.uuid)){
            mostrarMensagem("Cadastro de campeonato efetuado com sucesso", AlertType.CONFIRMATION);
            switchToTelaInicio();
        }else{
        
        }
    }
    
}

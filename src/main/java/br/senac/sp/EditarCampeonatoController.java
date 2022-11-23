package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.model.Campeonato;
import br.senac.sp.utils.MessageFactory;
import br.senac.sp.utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditarCampeonatoController implements Initializable {
    @FXML
    TextField textFieldNome;
    @FXML
    Spinner<Integer> spinnerQuantidade;
    @FXML
    TextField textFieldCategoria;
    @FXML
    TextArea textAreaDescricao;
    @FXML
    DatePicker datePickerInicio;
    @FXML
    DatePicker datePickerFim;
    @FXML
    Button buttonAlterar;

    public void carregar(int id) {
        Campeonato campeonato = CampeonatoDAO.carregarCampeonato(id);
        campeonato.setListaEquipes(CampeonatoDAO.carregarEquipesParticipantes(id));
        textFieldNome.setText(campeonato.getNome());
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                campeonato.getQtdTimes());
        this.spinnerQuantidade.setValueFactory(valueFactory);
        textFieldCategoria.setText(campeonato.getCategoria());
        textAreaDescricao.setText(campeonato.getDescricao());
    }

    @FXML
    private void switchToCampeonato() throws IOException {
        App.setRoot("campeonato");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar(App.idCampeonato);
        Validator.limitadorSomenteletras(textFieldNome, 30);
        Validator.limitadorSomenteletras(textFieldCategoria, 20);
        Validator.limitadorTextArea(textAreaDescricao, 200);
    }

    public void alterar() throws IOException {
        if (!Validator.isEmpty(textFieldNome) && !Validator.isEmpty(textFieldCategoria) && !Validator.isEmpty(datePickerInicio) && !Validator.isEmpty(datePickerFim)) {
            Campeonato campeonato;
            campeonato = new Campeonato(App.idCampeonato);

            campeonato.setNome(textFieldNome.getText());
            campeonato.setQtdTimes(spinnerQuantidade.getValue());
            campeonato.setCategoria(textFieldCategoria.getText());
            campeonato.setDescricao(textAreaDescricao.getText());
            campeonato.setDataInicial(
                    Date.from(datePickerInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            campeonato
                    .setDataFinal(Date.from(datePickerFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            if (CampeonatoDAO.atualizar(campeonato, App.uuid)) {
                MessageFactory.mostrarMensagem("Alteração de campeonato efetuado com sucesso", AlertType.CONFIRMATION);
                switchToCampeonato();
            } else
                MessageFactory.mostrarMensagem("Falha ao alterar campeonato.", AlertType.ERROR);
        } else {
            MessageFactory.mostrarMensagem("Preencher campos obrigatorios (*)", AlertType.ERROR);
        }
    }
}

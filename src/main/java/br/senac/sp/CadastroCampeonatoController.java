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

public class CadastroCampeonatoController implements Initializable {
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
    Button buttonCriar;

    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }

    public void cadastrar() throws IOException {
        if (!Validator.isEmpty(textFieldNome) && !Validator.isEmpty(textFieldCategoria) && !Validator.isEmpty(datePickerInicio) && !Validator.isEmpty(datePickerFim)) {
            Campeonato campeonato = new Campeonato();

            campeonato.setNome(textFieldNome.getText());
            campeonato.setQtdTimes(spinnerQuantidade.getValue());
            campeonato.setCategoria(textFieldCategoria.getText());
            campeonato.setDescricao(textAreaDescricao.getText());
            campeonato.setDataInicial(Date.from(datePickerInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            campeonato.setDataFinal(Date.from(datePickerFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            if (CampeonatoDAO.adicionar(campeonato, App.uuid)) {
                MessageFactory.mostrarMensagem("Cadastro de campeonato efetuado com sucesso", AlertType.CONFIRMATION);
                switchToTelaInicio();
            } else {
                MessageFactory.mostrarMensagem("Falha ao cadastrar campeonato", AlertType.ERROR);
            }
        } else {
            MessageFactory.mostrarMensagem("Preencher campos obrigatorios (*)", AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        this.spinnerQuantidade.setValueFactory(valueFactory);

        Validator.limitadorSomenteletras(textFieldNome, 30);
        Validator.limitadorSomenteletras(textFieldCategoria, 20);
        Validator.limitadorTextArea(textAreaDescricao, 200);
    }

}

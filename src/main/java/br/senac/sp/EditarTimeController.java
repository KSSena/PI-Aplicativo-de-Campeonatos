package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.dao.EquipeDAO;
import br.senac.sp.dao.UsuarioDAO;
import br.senac.sp.model.Equipe;
import br.senac.sp.utils.MessageFactory;
import br.senac.sp.utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditarTimeController implements Initializable {
    @FXML
    TextField textFieldNome;
    @FXML
    Spinner<Integer> spinnerQuantidade;
    @FXML
    TextField textFieldCategoria;
    @FXML
    TextArea textAreaDescricao;
    @FXML
    Button buttonAlterar;

    public void carregar(int id) {
        Equipe equipe = EquipeDAO.carregarEquipe(id);
        equipe.setListaMembros(UsuarioDAO.carregarListaMembros(id));
        equipe.setListaCampeonatos(CampeonatoDAO.carregarListaCampeonatos(id));

        textFieldNome.setText(equipe.getNome());
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                equipe.getQtdMembros());
        this.spinnerQuantidade.setValueFactory(valueFactory);
        textFieldCategoria.setText(equipe.getCategoria());
        textAreaDescricao.setText(equipe.getDescricao());
    }

    public void alterar() throws IOException {
        if (!Validator.isEmpty(textFieldNome) && !Validator.isEmpty(textFieldCategoria)) {
            Equipe equipe = new Equipe(App.idTime);

            equipe.setNome(textFieldNome.getText());
            equipe.setQtdMembros(spinnerQuantidade.getValue());
            equipe.setCategoria(textFieldCategoria.getText());
            equipe.setDescricao(textAreaDescricao.getText());

            if (EquipeDAO.atualizar(equipe)) {
                MessageFactory.mostrarMensagem("Alteração realizada com sucesso", AlertType.CONFIRMATION);
                switchToTime();
            } else
                MessageFactory.mostrarMensagem("Falha ao salvar alteração", AlertType.ERROR);
        } else {
            MessageFactory.mostrarMensagem("Preencher campos obrigatorios (*)", AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar(App.idTime);
        Validator.limitadorSomenteletras(textFieldNome, 30);
        Validator.limitadorSomenteletras(textFieldCategoria, 20);
        Validator.limitadorTextArea(textAreaDescricao, 200);
    }

    @FXML
    private void switchToTime() throws IOException {
        App.setRoot("time");
    }
}

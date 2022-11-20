package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.senac.sp.dao.EquipeDAO;
import br.senac.sp.model.Equipe;
import br.senac.sp.utils.MessageFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroTimeController implements Initializable{
    @FXML TextField textFieldNome;
    @FXML Spinner<Integer> spinnerQuantidade;
    @FXML TextField textFieldCategorias;
    @FXML TextArea textAreaDescricao;
    @FXML Button buttonCriar;

    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }

    public void cadastrar() throws IOException {
        Equipe equipe = new Equipe();

        equipe.setNome(textFieldNome.getText());
        equipe.setQtdMembros(spinnerQuantidade.getValue());
        equipe.setCategoria(textFieldCategorias.getText());
        equipe.setDescricao(textAreaDescricao.getText());

        if(EquipeDAO.adicionar(equipe, App.uuid)){
            MessageFactory.mostrarMensagem("Criação de Equipe Efetuada com Sucesso", AlertType.CONFIRMATION);
            switchToTelaInicio();
        }else
            MessageFactory.mostrarMensagem("Falha ao cadastrar", AlertType.ERROR);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
         SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
         this.spinnerQuantidade.setValueFactory(valueFactory);
    }
}

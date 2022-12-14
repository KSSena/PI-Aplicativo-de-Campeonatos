package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.model.Campeonato;
import br.senac.sp.model.Equipe;
import br.senac.sp.utils.MessageFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class TelaCampeonatoController implements Initializable {
    @FXML
    Text textNome;
    @FXML
    TextArea textAreaDescricao;
    @FXML
    Text textQuantidade;
    @FXML
    Text textCategoria;
    @FXML
    ImageView imageViewEditar;
    @FXML
    Button buttonInscreverEquipe;
    @FXML
    Button buttonExcluirCampeonato;
    @FXML
    ComboBox<Equipe> comboBoxInscrever;
    @FXML
    ListView<Equipe> listViewTimes;

    public void carregar(int id) {
        Campeonato campeonato = CampeonatoDAO.carregarCampeonato(id);

        ArrayList<Equipe> listaEquipes = CampeonatoDAO.carregarEquipesParticipantes(id);

        campeonato.setListaEquipes(listaEquipes);
        textNome.setText(campeonato.getNome());
        textQuantidade.setText("Quantidade Máxima de Equipes: " + campeonato.getQtdTimes());
        textCategoria.setText("Categoria: " + campeonato.getCategoria());
        textAreaDescricao.setText(campeonato.getDescricao());

        listViewTimes.getItems().addAll(listaEquipes);

        if (!CampeonatoDAO.verificarOrganizador(id, App.uuid)) {
            imageViewEditar.setVisible(false);
            buttonExcluirCampeonato.setVisible(false);
        }
        ;

        ArrayList<Equipe> listaEquipesAptas = CampeonatoDAO.carregarEquipesAptas(App.uuid, App.idCampeonato);

        ObservableList<Equipe> listaObservavel = FXCollections.observableArrayList(listaEquipesAptas);

        if (listaObservavel.isEmpty()) {
            comboBoxInscrever.setVisible(false);
            buttonInscreverEquipe.setVisible(false);
        } else
            comboBoxInscrever.setItems(listaObservavel);

    }

    @FXML
    private void switchToEditarCampeonato() throws IOException {
        App.setRoot("editarCampeonato");
    }

    @FXML
    private void switchToTelaInicio() throws IOException {
        App.setRoot("telaInicio");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar(App.idCampeonato);
    }

    /**
     * Método para inscrever a equipe selecionada no campeonato
     */
    public void inscreverEquipe() {
        if (comboBoxInscrever.getSelectionModel().getSelectedItem() != null) {
            CampeonatoDAO.adicionarEquipe(App.idCampeonato,
                    comboBoxInscrever.getSelectionModel().getSelectedItem().getId());
            carregar(App.idCampeonato);
        }
    }

    @FXML
    private void switchToTime() throws IOException {
        if (listViewTimes.getSelectionModel().getSelectedItem() != null) {
            int id = listViewTimes.getSelectionModel().getSelectedItem().getId();
            App.idTime = id;
            App.setRoot("time");
        }
    }

    /**
     * Método para excluir o campeonato
     * 
     * @throws IOException
     */
    public void excluirCampeonato() throws IOException {
        CampeonatoDAO.excluirCampeonato(App.idCampeonato, App.uuid);
        MessageFactory.mostrarMensagem("Campeonato excluido com sucesso", AlertType.CONFIRMATION);
        switchToTelaInicio();
    }

}

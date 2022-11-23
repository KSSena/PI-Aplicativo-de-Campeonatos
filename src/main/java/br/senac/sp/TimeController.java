package br.senac.sp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.senac.sp.dao.CampeonatoDAO;
import br.senac.sp.dao.EquipeDAO;
import br.senac.sp.dao.UsuarioDAO;
import br.senac.sp.model.Campeonato;
import br.senac.sp.model.Equipe;
import br.senac.sp.model.Usuario;
import br.senac.sp.utils.MessageFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class TimeController implements Initializable {
    @FXML
    Text textNome;
    @FXML
    TextArea textAreaDescricao;
    @FXML
    Text textQuantidade;
    @FXML
    ImageView imageViewEditar;
    @FXML
    Text textCategoria;
    @FXML
    Button buttonEntrarTime;
    @FXML
    ListView<Usuario> listViewMembros;
    @FXML
    ListView<Campeonato> listViewCampeonatos;
    private boolean editor;

    private void carregar(int id) {
        Equipe equipe = EquipeDAO.carregarEquipe(id);
        ArrayList<Usuario> listaMembros = UsuarioDAO.carregarListaMembros(id);
        equipe.setListaMembros(listaMembros);
        ArrayList<Campeonato> listaCampeonatos = CampeonatoDAO.carregarListaCampeonatos(id);
        equipe.setListaCampeonatos(listaCampeonatos);
        listViewMembros.getItems().addAll(listaMembros);
        listViewCampeonatos.getItems().addAll(listaCampeonatos);
        textNome.setText(equipe.getNome());
        textQuantidade.setText("Quantidade de membros: " + equipe.getQtdMembros());
        textCategoria.setText("Categoria: " + equipe.getCategoria());
        textAreaDescricao.setText(equipe.getDescricao());
        editor = false;
        for (Usuario u : listaMembros) {
            if (u.getUuid().equals(App.uuid)) {
                buttonEntrarTime.setVisible(false);
                imageViewEditar.setVisible(false);
                if (u.isEditor()) {
                    buttonEntrarTime.setText("Excluir time");
                    imageViewEditar.setVisible(true);
                    editor = true;
                    buttonEntrarTime.setVisible(true);
                }
            } else {
                imageViewEditar.setVisible(false);
            }
        }
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

    public void entrarTime() throws IOException {
        if (editor) {
            EquipeDAO.excluirTime(App.idTime);
            MessageFactory.mostrarMensagem("Equipe excluida", AlertType.CONFIRMATION);
            switchToTelaInicio();
        } else {
            EquipeDAO.adicionarIntegrante(App.idTime, App.uuid);
            MessageFactory.mostrarMensagem("Parabens por entrar no time", AlertType.CONFIRMATION);
            carregar(App.idTime);
        }
    }

    @FXML
    private void switchToCampeonato() throws IOException {
        if (listViewCampeonatos.getSelectionModel().getSelectedItem() != null) {
            int id = listViewCampeonatos.getSelectionModel().getSelectedItem().getId();
            App.idCampeonato = id;
            App.setRoot("campeonato");
        }
    }

}

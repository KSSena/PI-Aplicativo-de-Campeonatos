package br.senac.sp.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageFactory {


    /**
     * Método para criação de uma caixa visual de mensagem 
     * @param mensagem Mensagem a ser exibida
     * @param tipo Tipo de alerta a ser exibido
     */
    public static void mostrarMensagem(String mensagem, AlertType tipo){
        var alerta = new Alert(tipo);
        alerta.setContentText(mensagem);
        alerta.show();
    }
}

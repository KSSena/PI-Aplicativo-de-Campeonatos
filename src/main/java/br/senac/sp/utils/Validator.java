package br.senac.sp.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Validator {

    public static void limitador(final TextField field, final int maxLength) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (field.getText().length() > maxLength) {
                    String s = field.getText().substring(0, maxLength);
                    field.setText(s);
                }
            }
        });
    }

    public static void limitador(final PasswordField field, final int maxLength) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (field.getText().length() > maxLength) {
                    String s = field.getText().substring(0, maxLength);
                    field.setText(s);
                }
            }
        });
    }
    
    public static void limitadorSomenteNumeros(final TextField field, int maxLength) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (field.getText().length() > maxLength) {
                    String s = field.getText().substring(0, maxLength);
                    field.setText(s);
                }
            }
        });
    }
}

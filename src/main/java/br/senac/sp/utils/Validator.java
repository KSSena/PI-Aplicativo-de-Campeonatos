package br.senac.sp.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
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

    public static void limitadorSomenteletras(final TextField field, int maxLength) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("[^\\d]+")) {
                    field.setText(newValue.replaceAll("\\d", ""));
                }
                if (field.getText().length() > maxLength) {
                    String s = field.getText().substring(0, maxLength);
                    field.setText(s);
                }
            }
        });
    }
    
    
    // Limitador do TextArea
    
    public static void limitadorTextArea(final TextArea field, final int maxLength) {
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

    public static boolean isEmpty(final TextField field){
        if(field.getText().length() == 0){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(final DatePicker field){
        if(field.getValue() == null){
            return true;
        }
        return false;
    }

    public static boolean isFull(final TextField field, int maxLength){
        if(field.getText().length() == maxLength){
            return true;
        }
        return false;
    }
}

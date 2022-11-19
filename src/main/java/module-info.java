module br.senac.sp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens br.senac.sp to javafx.fxml;
    opens br.senac.sp.model to javafx.base;

    exports br.senac.sp;
}

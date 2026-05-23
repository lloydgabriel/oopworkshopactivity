module org.example.oopworkshopactivity {
    requires java.sql;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires org.postgresql.jdbc;

    opens org.example.oopworkshopactivity to javafx.fxml;

    exports org.example.oopworkshopactivity;
}
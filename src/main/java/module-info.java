module ubb.scs.socialnetworkgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;

    opens ubb.scs.socialnetworkgui to javafx.fxml;
    exports ubb.scs.socialnetworkgui;
    exports ubb.scs.socialnetworkgui.gui;
    opens ubb.scs.socialnetworkgui.gui to javafx.fxml;
}
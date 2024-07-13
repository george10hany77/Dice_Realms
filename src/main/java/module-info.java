module DiceRealms {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.base;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires com.google.gson;

    opens game.gui to javafx.fxml;

    exports game.gui;

    opens game.creatures to com.google.gson;
    opens game.collectibles to com.google.gson;
    opens game.dice to com.google.gson;

}
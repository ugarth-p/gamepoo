module Game {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;

    opens com.gamepoo.launcher to javafx.graphics;
    opens com.gamepoo.scene to javafx.fxml;
    opens com.gamepoo.ui to javafx.fxml;
    opens com.gamepoo.model to javafx.base;
}

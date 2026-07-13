package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreLabel {
    private int score;
    private Label scoreLabel;

    public ScoreLabel(double x, double y) {
        this.score = 0;
        this.scoreLabel = new Label("Score: " + this.score);
        Font f = Font.loadFont(getClass().getResourceAsStream("/application/font2.ttf"), 56);
        this.scoreLabel.setFont(f != null ? f : Font.font("Verdana", 56));
        this.scoreLabel.setTextFill(Color.AQUA);
        this.scoreLabel.setLayoutX(x);
        this.scoreLabel.setLayoutY(y);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        updateLabel();
    }

    public Label getLabel() {
        return this.scoreLabel;
    }

    public void updateLabel() {
        this.scoreLabel.setText("Score: " + this.score);
    }

    public void addToScore() {
        this.score++;
        updateLabel();
    }
}

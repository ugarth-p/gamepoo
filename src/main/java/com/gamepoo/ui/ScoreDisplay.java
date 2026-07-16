package com.gamepoo.ui;

import com.gamepoo.assets.AssetManager;
import com.gamepoo.config.Constants;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ScoreDisplay {
    private int score;
    private final Label scoreLabel;

    public ScoreDisplay(double x, double y) {
        this.score = 0;
        this.scoreLabel = new Label("Score: " + this.score);
        this.scoreLabel.setFont(AssetManager.getFontOrFallback(Constants.FONT_PATH, 56, Constants.DEFAULT_FONT_FAMILY));
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
        return scoreLabel;
    }

    private void updateLabel() {
        scoreLabel.setText("Score: " + score);
    }

    public void addToScore() {
        score++;
        updateLabel();
    }
}

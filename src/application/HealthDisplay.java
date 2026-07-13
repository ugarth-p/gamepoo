package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HealthDisplay {
    private Label healthLabel;
    private int maxHealth;
    private int currentHealth;

    public HealthDisplay(String characterName, int maxHealth, double x, double y) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.healthLabel = new Label(characterName + " HP: " + currentHealth + "/" + maxHealth);
        try {
            this.healthLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/application/font2.ttf"), 30));
        } catch (Exception e) {
            this.healthLabel.setFont(Font.font("Verdana", 20));
        }
        this.healthLabel.setTextFill(Color.LAWNGREEN);
        this.healthLabel.setLayoutX(x);
        this.healthLabel.setLayoutY(y);
    }

    public Label getLabel() {
        return healthLabel;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void reduceHealth() {
        if (currentHealth > 0) {
            currentHealth--;
            updateLabel();
        }
    }

    public void setToFull() {
        currentHealth = maxHealth;
        updateLabel();
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

    private void updateLabel() {
        String name = healthLabel.getText().split(" HP:")[0];
        healthLabel.setText(name + " HP: " + currentHealth + "/" + maxHealth);
        if (currentHealth <= 2) {
            healthLabel.setTextFill(Color.RED);
        } else if (currentHealth <= 5) {
            healthLabel.setTextFill(Color.ORANGE);
        } else {
            healthLabel.setTextFill(Color.LAWNGREEN);
        }
    }
}

package model;

import javax.swing.*;

public class CustomButton extends JButton {

    private int buttonID;

    public CustomButton(int buttonID, String text) {
        super(text);
        this.buttonID = buttonID;
    }

    public int getButtonID() {
        return buttonID;
    }
}

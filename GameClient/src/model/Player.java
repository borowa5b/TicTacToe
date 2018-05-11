package model;

public class Player {

    private char side;
    private char opponentSide;
    private Callback callback;

    public void register(Callback callback) {
        this.callback = callback;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public char getSide() {
        return side;
    }

    public void setOpponentSide(char opponentSide) {
        this.opponentSide = opponentSide;
    }

    public char getOpponentSide() {
        return opponentSide;
    }

    public void checkField(int btnID) {
        callback.onClick(btnID);
    }

}

package controller;

import model.*;

public class Controller {

    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayer(Player player) {
        game.setPlayer(player);
    }

    public void setView(View view) {
        game.setView(view);
    }

    public void registerPlayer(Callback callback) {
        game.getPlayer().register(callback);
    }

    public void setSide(char side) {
        game.getPlayer().setSide(side);
    }

    public char getSide() {
        return game.getPlayer().getSide();
    }

    public void setOpponentSide(char side) {
        game.getPlayer().setOpponentSide(side);
    }

    public char getOpponentSide() {
        return game.getPlayer().getOpponentSide();
    }

    public void startGame() {
        game.start();
    }

    public void onCheck(int btnID) {
        game.getPlayer().checkField(btnID);
    }

    public void setClient(Client client) {
        game.setClient(client);
    }

    public void registerClient(Callback callback) {
        game.getClient().registerClient(callback);
    }

    public void sendToServer(String msg) {
        game.getClient().sendToServer(msg);
    }

    public void closeConnection() {
        game.getClient().closeConnection();
    }

}

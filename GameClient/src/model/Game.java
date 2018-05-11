package model;

public class Game {

    private Player player;
    private View view;
    private Client client;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void start() {
        view.init();
    }

}

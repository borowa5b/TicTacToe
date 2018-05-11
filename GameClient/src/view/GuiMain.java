package view;

import controller.Controller;
import model.Client;
import model.Game;
import model.Player;

public class GuiMain {

    public static void main(String[] args) {
        Game game = new Game();
        Player player = new Player();
        GuiView view = new GuiView();
        Client client = new Client();
        Controller controller = new Controller();

        view.setController(controller);
        controller.setGame(game);
        controller.setPlayer(player);
        controller.registerPlayer(view);
        controller.setView(view);

        controller.setClient(client);
        controller.registerClient(view);
        Thread cl = new Thread(client, "Client Thread");
        cl.start();

        controller.startGame();
    }

}

package view;

import controller.Controller;
import model.Callback;
import model.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        Controller controller = new Controller();
        GuiView view = new GuiView();
        Callback callback = view;

        view.setController(controller);
        controller.setServer(server);
        controller.registerServer(callback);
        view.init();
    }

}

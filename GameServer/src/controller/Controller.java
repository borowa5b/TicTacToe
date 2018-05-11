package controller;

import model.Callback;
import model.Server;

public class Controller {

    private Server server;

    public void setServer(Server server) {
        this.server = server;
    }

    public void startServer() {
        server.start();
    }

    public void stopServer() {
        server.stop();
    }

    public void registerServer(Callback callback) {
        server.registerServer(callback);
    }

}

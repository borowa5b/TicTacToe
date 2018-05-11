package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {

    private ArrayList<ServerConnection> connections = new ArrayList<>();
    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private Callback callback;
    private Thread server;

    public synchronized void start() {
        if(server != null) return;

        server = new Thread(() -> {
            try {
                running = true;
                serverSocket = new ServerSocket(9090);
                int id = 0;
                while (running) {
                    callback.onLog("Started server");
                    Socket clientSocket = serverSocket.accept();
                    ServerConnection serverConnection = new ServerConnection(clientSocket, id++, connections, callback);
                    Thread sc = new Thread(serverConnection, "ServerConnectionThread");
                    sc.start();
                    connections.add(serverConnection);
                }
            } catch (SocketException e) {
                callback.onLog("Server closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "ServerThread");
        server.start();
    }

    public synchronized void stop() {
        if(server == null) return;

        running = false;
        try {
            if(serverSocket != null) {
                for(ServerConnection sc : connections) {
                    sc.closeConnection();
                }
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server = null;
        }
    }

    public void registerServer(Callback callback) {
        this.callback = callback;
    }

}

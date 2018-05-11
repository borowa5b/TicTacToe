package model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerConnection implements Runnable {

    private Socket clientSocket;
    private int clientID;
    private boolean running = true;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ServerConnection> connections;
    private Callback callback;

    ServerConnection(Socket clientSocket, int clientID, ArrayList<ServerConnection> connections, Callback callback) {
        this.clientSocket = clientSocket;
        this.clientID = clientID;
        this.connections = connections;
        this.callback = callback;
    }

    @Override
    public void run() {
        callback.onLog("Accepted client:\nID: " + clientID + "\nAddress: " + clientSocket.getInetAddress().getHostAddress());
        try {

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            while (running) {
                String clientInput = in.readLine();
                callback.onLog("Client ID: " + clientID + " pressed btn[" + clientInput + "]");
                if (clientInput.equals("exit")) {
                    running = false;
                    callback.onLog("Stopped client thread for clientID: " + clientID);
                } else {
                    sendToOtherClient(clientInput);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendToOtherClient(String clientInput) {
        for(ServerConnection sc : connections) {
            if(sc != this) {
                sc.out.println(clientInput);
                sc.out.flush();
            }
        }
    }

    private void sendToAll(String msg) {
        for(ServerConnection sc : connections) {
            sc.out.println(msg);
            sc.out.flush();
        }
    }

    public void closeConnection() throws IOException {
        sendToAll("close");
        connections.remove(this);
        in.close();
        out.close();
        clientSocket.close();
    }

}

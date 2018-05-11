package model;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private boolean running = true;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Callback callback;

    public void registerClient(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket("localhost", 9090);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            listen();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void sendToServer(String msg) {
        out.println(msg);
        out.flush();
    }

    private void listen() {
        while (running) {
            try {
                String opponentInput = in.readLine();
                if (opponentInput != null) {
                    switch (opponentInput) {
                        case "close":
                            closeConnection();
                            break;
                        case "over":
                            callback.onGameOver();
                            break;
                        case "unlock":
                            callback.onUnlockButtons();
                            break;
                        case "reset":
                            callback.onReset();
                            break;
                        default:
                            callback.onOpponentClick(Integer.valueOf(opponentInput));
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        try {
            running = false;
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

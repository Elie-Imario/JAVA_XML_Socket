package com.app.core.server;

import java.util.Timer;
import java.util.TimerTask;
import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) throws IOException{
        Timer timer = new Timer();

        CheckConnection checkConnection = new CheckConnection();
        timer.scheduleAtFixedRate(checkConnection, 0, 4000);// Exécution toutes les 4 secondes
    }

    static class CheckConnection extends TimerTask {
        private boolean state;
        @Override
        public void run() {
            Socket socket = new Socket();
            InetSocketAddress serverAddress = new InetSocketAddress("127.0.0.1", 8000);
            try {
                socket.connect(serverAddress);
                this.setState(true);
                System.out.println("Le serveur est en marche.");
                // afficher que la connection marche 
            } catch (IOException e) {
                this.setState(false);
                System.out.println("Le serveur n'est pas accessible.");

                // afficher que la connection est coupée
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }
    }
}

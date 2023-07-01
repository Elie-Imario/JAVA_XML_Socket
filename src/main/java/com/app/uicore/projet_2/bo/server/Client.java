package com.app.uicore.projet_2.bo.server;

import java.util.Timer;
import java.util.TimerTask;
import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) {
        Timer timer = new Timer();
        long interval = 5000; // 5 secondes d'interval pour la boucle qui check la connexion

        TimerTask serverSocketCheckerTask = new TimerTask() {
            @Override
            public void run() {
                String serverIP = "127.0.0.1";
                int serverPort = 8000;
                try {
                    Socket socket = new Socket();
                    boolean serverState = isServerRunning(serverIP, serverPort, socket);
                    if(serverState){
                        System.out.println("----------connectée!-------------");
                        try {
                            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                            output.println("Connexion au serveur...");

                            String response = input.readLine();

                            if (response.equals("DataUploaded")) {
                                String path = System.getProperty("user.home") + "\\Downloads\\exportedFile\\etudiant.xml";
                                File file = new File(path);
                                if (file.exists()) file.delete();
                                System.out.println("Enregistrement effectué");

                            }else{
                                System.out.println("En stand by");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println("--------------non connectée!----------------------");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        timer.scheduleAtFixedRate(serverSocketCheckerTask, 0, interval);

    }

    public static boolean isServerRunning(String _serverIp, int serverPort, Socket socket) {
        try{
            int timeout = 5000; // 5 secondes
            socket.connect(new InetSocketAddress(_serverIp, serverPort), timeout);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

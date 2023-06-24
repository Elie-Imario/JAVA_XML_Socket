package com.app.uicore.projet_2.bo.server;

import com.app.core.util.StaxWritter;
import com.app.uicore.projet_2.bo.beans.Etudiant;
import com.app.uicore.projet_2.bo.service.EtudiantService;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Server {

    public static void main(String[] args) throws IOException {
        int port = 8000; // Port sur lequel le serveur écoute
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            Timer timer = new Timer();
            System.out.println("Le serveur est en écoute sur le port " + port);
            timer.scheduleAtFixedRate(new CheckXML(), 0, 5000); // Exécution toutes les 4 secondes

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread();
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class CheckXML extends TimerTask {
        @Override
        public void run(){
            StaxWritter staxWritter = new StaxWritter();
            EtudiantService etudiantService = new EtudiantService();
            try{
                String path = "src\\exportedFile\\etudiant.xml";
                File file = new File(path);
                Etudiant etudiant;

                if(!file.exists()){
                    System.out.println("File Not found");
                }
                else{
                    System.out.println("File exist");
                    NodeList nodeListetudiants = etudiantService.getNodeListEtudiants(staxWritter);
                    for (int i = 0; i < nodeListetudiants.getLength(); i++) {
                        Node n = nodeListetudiants.item(i);
                        Element e = (Element) n;

                        if(e.getAttribute("requestType").equals("AJOUT")){
                            etudiant = new Etudiant(e.getElementsByTagName("nom").item(0).getTextContent(), e.getElementsByTagName("adresse").item(0).getTextContent(), Integer.valueOf(e.getElementsByTagName("bourse").item(0).getTextContent()));
                            etudiantService.createEtudiant(etudiant);
                        }else if(e.getAttribute("requestType").equals("EDIT")){
                            etudiant = new Etudiant(Long.valueOf(e.getElementsByTagName("id").item(0).getTextContent()),e.getElementsByTagName("nom").item(0).getTextContent(), e.getElementsByTagName("adresse").item(0).getTextContent(), Integer.valueOf(e.getElementsByTagName("bourse").item(0).getTextContent()));
                            etudiantService.editEtudiant(etudiant);
                        }else if(e.getAttribute("requestType").equals("DELETE")){
                            etudiantService.deleteEtudiant(Long.valueOf(e.getElementsByTagName("id").item(0).getTextContent()));
                        }
                    }
                    file.delete();
                }
            } catch (Exception e) {

            } finally {
                try {
                
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
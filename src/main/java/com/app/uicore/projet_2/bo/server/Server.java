package com.app.uicore.projet_2.bo.server;

import com.app.uicore.projet_2.bo.util.StaxWritter;
import com.app.uicore.projet_2.bo.beans.Etudiant;
import com.app.uicore.projet_2.bo.service.EtudiantService;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Server {

    public static void main(String[] args) throws IOException {
        int port = 8000;
        while(true){
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Le serveur est en écoute sur le port " + port);
                Socket clientSocket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);

                if (clientSocket != null) {
                    System.out.println("User connected");
                } else {
                    System.out.println("No user");
                }

                String message = bufferedReader.readLine();

                /*
                ***-------VERIFY XML--------***
                 */
                String responseMessage = "";

                if(verifyFile()){
                    String path = System.getProperty("user.home") + "\\Downloads\\exportedFile\\etudiant.xml";
                    File file = new File(path);
                    StaxWritter staxWritter = new StaxWritter();
                    EtudiantService etudiantService = new EtudiantService();
                    Etudiant etudiant = new Etudiant();
                    NodeList nodeListetudiants = etudiantService.getNodeListEtudiants(staxWritter);

                    if(CheckXMLFile(nodeListetudiants)){
                        System.out.println("Données reçues");
                        System.out.println("Upload des données en cours....");
                        boolean isDataUploaded = UploadData(nodeListetudiants, etudiant, etudiantService);

                        responseMessage = "DataUploaded";
                        if(isDataUploaded) staxWritter.deleteXMLContent(file);
                    }
                }
                else{
                    responseMessage = "NoUpload";
                }

                printWriter.println(responseMessage);

                printWriter.close();
                serverSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean CheckXMLFile(NodeList _nodeListEtudiants){
        try {
            if (_nodeListEtudiants.getLength() > 0) {
                System.out.println("File Fill");
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean UploadData(NodeList nodeListetudiants, Etudiant etudiant, EtudiantService etudiantService){
        boolean Addissuccess = false, EditisSuccess = false, DeleteisSuccess = false;
        for (int i = 0; i < nodeListetudiants.getLength(); i++) {
            Node n = nodeListetudiants.item(i);
            Element e = (Element) n;
            if (e.getAttribute("requestType").equals("AJOUT")) {
                etudiant = new Etudiant(e.getElementsByTagName("nom").item(0).getTextContent(), e.getElementsByTagName("adresse").item(0).getTextContent(), Integer.valueOf(e.getElementsByTagName("bourse").item(0).getTextContent()));
                Addissuccess = etudiantService.createEtudiant(etudiant);
            } else if (e.getAttribute("requestType").equals("EDIT")) {
                etudiant = new Etudiant(Long.valueOf(e.getElementsByTagName("id").item(0).getTextContent()), e.getElementsByTagName("nom").item(0).getTextContent(), e.getElementsByTagName("adresse").item(0).getTextContent(), Integer.valueOf(e.getElementsByTagName("bourse").item(0).getTextContent()));
                EditisSuccess = etudiantService.editEtudiant(etudiant);
            } else if (e.getAttribute("requestType").equals("DELETE")) {
                DeleteisSuccess = etudiantService.deleteEtudiant(Long.valueOf(e.getElementsByTagName("id").item(0).getTextContent()));
            }
        }

        if (Addissuccess || EditisSuccess || DeleteisSuccess) return true;
        else return false;
    }

    public static boolean verifyFile(){
        String path = System.getProperty("user.home") + "\\Downloads\\exportedFile\\etudiant.xml";
        File file = new File(path);
        if(file.exists()){
            return true;
        }else{
            return false;
        }
    }
}
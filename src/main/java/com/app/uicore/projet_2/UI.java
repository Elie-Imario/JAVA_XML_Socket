package com.app.uicore.projet_2;

import com.app.core.util.StaxWritter;
import com.app.uicore.projet_2.bo.service.EtudiantService;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.Scanner;

public class UI {
    public static void main(String... args){
        Scanner sc = new Scanner(System.in);
        StaxWritter staxWritter = new StaxWritter();
        int choice;
        EtudiantService etudiantService = new EtudiantService();

        System.out.println("---------------ETUDIANT UI-----------------");
        System.out.println("1- Ajouter un etudiant" +
                "\n 2- Modifier un etudiant" +
                "\n 3- Supprimer un etudiant" +
                "\n 4- Lister les etudiants");

        System.out.println("Quel est votre choix?");
        choice = sc.nextInt();

        sc.nextLine();
        if(choice == 1){
            System.out.println("--------------Ajouter un etudiant-------------");

            String firstname, adresse;
            int bourse;

            System.out.println("Quel est son nom?");
            firstname = sc.nextLine();
            System.out.println("Quel est son adresse?");
            adresse = sc.nextLine();
            System.out.println("Quel est le montant de sa bourse?");
            bourse = sc.nextInt();

            //verify File
            String path = "src\\exportedFile\\etudiant.xml";
            File file = new File(path);
            boolean flag;

            flag = !file.exists();

            if(flag){
                staxWritter.setFile("src\\exportedFile\\etudiant.xml");
                etudiantService.saveEtudiantData_toXML(staxWritter, null, firstname, adresse, bourse, "AJOUT");
            }
            else{
                etudiantService.saveEtudiantData_toCurrentXML(staxWritter, null, firstname, adresse, bourse, "AJOUT");
            }
        }else if(choice == 2){
            System.out.println("--------------Modifier un professeur-------------");
            String newName, newAdresse;
            int newBourse;
            Long id_to_edit;
            System.out.println("Qui est l'etudiant à modifier?");
            id_to_edit = sc.nextLong();

            sc.nextLine();
            System.out.println("Quel est son nouveau nom?");
            newName = sc.nextLine();
            System.out.println("Quel est son nouvel adresse?");
            newAdresse = sc.nextLine();
            System.out.println("Quel est le nouvel montant de sa bourse?");
            newBourse = sc.nextInt();

            //verify File
            String path = "src\\exportedFile\\etudiant.xml";
            File file = new File(path);
            boolean flag;

            flag = !file.exists();

            if(flag){
                staxWritter.setFile("src\\exportedFile\\etudiant.xml");
                etudiantService.saveEtudiantData_toXML(staxWritter, id_to_edit, newName, newAdresse, newBourse, "EDIT");
            }
            else{
                etudiantService.saveEtudiantData_toCurrentXML(staxWritter, id_to_edit, newName, newAdresse, newBourse, "EDIT");
            }

        }else if(choice == 3){
            System.out.println("Quel etudiant voulez vous retirer?");
            Long id_to_delete = sc.nextLong();
            //verify File
            String path = "src\\exportedFile\\etudiant.xml";
            File file = new File(path);
            boolean flag;

            flag = !file.exists();

            if(flag){
                staxWritter.setFile("src\\exportedFile\\etudiant.xml");
                etudiantService.saveEtudiantData_toXML(staxWritter, id_to_delete, null, null, 0, "DELETE");
            }
            else{
                etudiantService.saveEtudiantData_toCurrentXML(staxWritter, id_to_delete, null, null, 0, "DELETE");
            }
        }else if(choice == 4){
            System.out.println("Voici la liste des etudiants");


            NodeList nodeListetudiants = etudiantService.getNodeListEtudiants(staxWritter);
            for (int i = 0; i < nodeListetudiants.getLength(); i++) {
                Node n = nodeListetudiants.item(i);
                Element e = (Element) n;

                System.out.println("nom: "+e.getElementsByTagName("nom").item(0).getTextContent()+"/"+"adresse: "+e.getElementsByTagName("adresse").item(0).getTextContent()+"/"+"bourse: "+e.getElementsByTagName("bourse").item(0).getTextContent());
                System.out.println("-------------------------------------------------------------------");
            }


        }else{
            System.out.println("Mauvais choix");
        }
    }
}

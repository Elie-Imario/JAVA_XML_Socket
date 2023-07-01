package com.app.uicore.projet_2;

import com.app.uicore.projet_2.bo.beans.Etudiant;
import com.app.uicore.projet_2.bo.service.EtudiantService;

import com.app.uicore.projet_2.bo.util.StaxWritter;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.*;

import com.app.uicore.projet_2.bo.util.AlertMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static com.app.uicore.projet_2.bo.server.Client.isServerRunning;

public class mainWindowController implements Initializable {
    @FXML
    private VBox mainWindow;

    @FXML
    private Button btnAddEtudiant;
    @FXML
    private Button btnCloseMainWindow;

    @FXML private TableView<Etudiant> TabListEtudiant;
    @FXML private TableColumn<Etudiant, Long> numetudiant;
    @FXML private TableColumn<Etudiant, String> nometudiant;
    @FXML private TableColumn<Etudiant, String> adresseEtudiant;
    @FXML private TableColumn<Etudiant, Integer> bourseETudiant;

    @FXML private MenuItem editEtudiant, deleteEtudiant;

    @FXML private ImageView connexionState;
    @FXML private HBox alertGroup;
    @FXML private Label responseMsg;

    String path = System.getProperty("user.home") + "\\Downloads\\exportedFile\\etudiant.xml";
    File file = new File(path);
    StaxWritter staxWritter = new StaxWritter();
    EtudiantService etudiantService = new EtudiantService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FillTableAction();

        /**Verify Connexion To server **/
        Timer timer = new Timer();
        long interval = 3500; // 2.5 secondes d'interval pour la boucle qui check la connexion

        TimerTask serverSocketCheckerTask = new TimerTask() {
            @Override
            public void run() {
                String serverIP = "127.0.0.1";
                int serverPort = 8000;
                try {
                    Socket socket = new Socket();
                    boolean serverState = isServerRunning(serverIP, serverPort, socket);
                    if(serverState){
                        connexionState.setImage(new Image(this.getClass().getResourceAsStream("img/connected.gif")));
                        System.out.println("----------connectée!-------------");
                        try {
                            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                            output.println("Connexion au serveur...");

                            String response = input.readLine();

                            if (response.equals("DataUploaded")) {
                                String path = System.getProperty("user.home") + "\\Downloads\\exportedFile\\etudiant.xml";
                                File file = new File(path);
                                if (file.exists()) {
                                    file.delete();
                                };
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        responseMsg.setText("Enregistrement effectué");
                                        FillTableAction();
                                    }
                                });
                                alertGroup.setVisible(true);


                            }else{
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        responseMsg.setText("");
                                    }
                                });
                                alertGroup.setVisible(false);
                                System.out.println("En stand by");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                responseMsg.setText("");
                            }
                        });
                        alertGroup.setVisible(false);
                        connexionState.setImage(new Image(this.getClass().getResourceAsStream("img/connection_lost.gif")));
                        System.out.println("--------------non connectée!----------------------");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        timer.scheduleAtFixedRate(serverSocketCheckerTask, 0, interval);

        //<!-------------------------!>

        btnCloseMainWindow.setOnMouseClicked((ActionEvent)->{
            Stage stage = (Stage) btnCloseMainWindow.getScene().getWindow();
            stage.close();
            System.exit(0);
        });
        btnAddEtudiant.setOnMouseClicked((ActionEvent)->{
            showAddModal();
        });

        editEtudiant.setOnAction((ActionEvent)->{
            Etudiant selectedEtudiantToUpdate = TabListEtudiant.getSelectionModel().getSelectedItem();
            if(selectedEtudiantToUpdate == null){
                AlertMessage.WarningAlert(mainWindow, "Veuillez selectionner au moins une ligne!!!");
            }
            else{
                ShowEditModal(selectedEtudiantToUpdate);
            }
        });

        deleteEtudiant.setOnAction((ActionEvent)->{
            Etudiant selectedEtudiantToDelete = TabListEtudiant.getSelectionModel().getSelectedItem();
            if(selectedEtudiantToDelete == null){
                AlertMessage.WarningAlert(mainWindow,"Veuillez selectionner au moins une ligne!!!");
            }
            else{
                deleteEtudiantAction(selectedEtudiantToDelete);
            }
        });
    }

    public void FillTableAction(){
        etudiantService.fillTableEtudiant(TabListEtudiant ,numetudiant, nometudiant, adresseEtudiant, bourseETudiant);
    }


    public void showAddModal(){
        try {
            Stage stage = new Stage();
            FXMLLoader ModalAddEtudiantFxml = new FXMLLoader(mainWindowController.class.getResource("ModalAddEtudiant.fxml"));
            Scene scene = new Scene(ModalAddEtudiantFxml.load(), 400, 462);

            boAddEtudiantController boAddEtudiantController = ModalAddEtudiantFxml.getController();
            boAddEtudiantController.setMainWindow(mainWindow);
            boAddEtudiantController.setStaxWritter(staxWritter);
            boAddEtudiantController.setFile(file);
            boAddEtudiantController.setEtudiantService(etudiantService);

            Stage Primary = new Stage();
            Primary = (Stage) mainWindow.getScene().getWindow();
            stage.initStyle(StageStyle.UNIFIED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("img/plus_icon.jpg")));

            stage.setX(Primary.getX() + (Primary.getWidth()/2-200));
            stage.setY(Primary.getY() + 95);
            stage.initOwner(Primary);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void ShowEditModal(Etudiant _selectedEtudiantToUpdate){
        try {
            Stage stage = new Stage();
            FXMLLoader ModalAddEtudiantFxml = new FXMLLoader(mainWindowController.class.getResource("ModalEditEtudiant.fxml"));
            Scene scene = new Scene(ModalAddEtudiantFxml.load(), 400, 462);

            boEditEtudiantController boEditEtudiantController = ModalAddEtudiantFxml.getController();
            boEditEtudiantController.setMainWindow(mainWindow);
            boEditEtudiantController.setDetailToUpdate(_selectedEtudiantToUpdate);
            boEditEtudiantController.setStaxWritter(staxWritter);
            boEditEtudiantController.setFile(file);

            boEditEtudiantController.setEtudiantService(etudiantService);

            Stage Primary = new Stage();
            Primary = (Stage) mainWindow.getScene().getWindow();
            stage.initStyle(StageStyle.UNIFIED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("img/edit.png")));

            stage.setX(Primary.getX() + (Primary.getWidth()/2-200));
            stage.setY(Primary.getY() + 95);
            stage.initOwner(Primary);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * CRUD
     */

    public void deleteEtudiantAction(Etudiant _etudianttoDelete){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setContentText("Supprimer l'etudiant ET-"+_etudianttoDelete.getId());
        confirmation.setHeaderText("");
        confirmation.setTitle("Alert-Confirmation");
        confirmation.initStyle(StageStyle.UTILITY);
        Stage Primary = new Stage();
        Primary = (Stage) mainWindow.getScene().getWindow();
        confirmation.setWidth( 350);confirmation.setHeight(100);
        confirmation.setX(Primary.getX() + (Primary.getWidth()/2-200));
        confirmation.setY(Primary.getY() + (Primary.getHeight()/2));
        confirmation.initOwner(Primary);

        Optional<ButtonType> buttonType = confirmation.showAndWait();

        if(buttonType.get() == ButtonType.OK){
            if(file.exists()){
                System.out.println("File exist");
                etudiantService.saveEtudiantData_toCurrentXML(staxWritter, _etudianttoDelete.getId(), null, null, 0, "DELETE");
            }
            else{
                System.out.println("File not exist");
                String file_path = System.getProperty("user.home") + "\\Downloads\\exportedFile\\etudiant.xml";
                staxWritter.setFile(file_path);
                etudiantService.saveEtudiantData_toXML(staxWritter, _etudianttoDelete.getId(), null, null, 0, "DELETE");
            }
        }
    }


    public TableView<Etudiant> getTabListEtudiant() {
        return TabListEtudiant;
    }
}

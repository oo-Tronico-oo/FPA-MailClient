/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import application.DateiVerwaltung;
import Message.Message;
import Message.MessageImportance;
import Message.MessageStakeholder;
import application.FolderSelectionObservable;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import modell.Directory;

/**
 * FXML Controller class
 *
 * @author Tronico
 */
public class MailTableViewController implements Initializable, Observer {

    @FXML
    private Label fromHeaderMail;

    @FXML
    private Label datumHeaderMail;

    @FXML
    private Button buttonForward;

    @FXML
    private Label toHeaderMail;

    @FXML
    private Button buttonReply;

    @FXML
    private Label ueberschriftMail;

    @FXML
    private TextArea textFieldMail;

    @FXML
    private TableView<Message> table;

    @FXML
    private Button buttonReplyAll;

    private ObservableList<Message> tableContent;
    private ContextMenu contextMenu;
    private boolean contextRead = false;
    private DateiVerwaltung dV;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableContent = FXCollections.observableArrayList();
        contextMenu = new ContextMenu(new MenuItem("mark as unread"));
        dV = new DateiVerwaltung("src");
        configureTable();
        FolderSelectionObservable.getInstance(null).addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

        TreeView<Directory> treeView = (TreeView<Directory>) arg;
        TreeItem<Directory> treeItem = treeView.getSelectionModel().getSelectedItem();
        Directory folder = (Directory) treeItem;

        clearMailDetail();
        tableContent.clear();
        editColumnView();
        dV.setPath(folder.getFile().getPath());
        tableContent.addAll(dV.getMessageListe());
    }

    private void configureTable() {

        deletEmptyRow();
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("importanceOfMessage"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("receivedAt"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("readStatus"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("sender"));
        table.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("subject"));

        editColumnView();

        tableContent.addAll(dV.getMessageListe());
        table.setItems(tableContent);
        table.setContextMenu(contextMenu);
        table.getSelectionModel().selectedItemProperty().addListener((e) -> handleSelectedMessage());
        contextMenu.getItems().get(0).setOnAction((e) -> handleContextItem());
    }

    private void editColumnView() {
        prioColumnEdit(table.getColumns().get(0));
        receColumnEdit(table.getColumns().get(1));
        readColumnEdit(table.getColumns().get(2));
        sendColumnEdit(table.getColumns().get(3));
    }

    private void handleContextItem() {
        contextRead = true;
        setReadStatus();
    }

    private void handleSelectedMessage() {
        setReadStatus();
        mailDetail();
    }

    private void setReadStatus() {
        Message selectedMessage = table.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {
            if (!selectedMessage.getReadStatus()) {
                selectedMessage.setReadStatus(true);
                dV.xmlDateiSpeichern(selectedMessage);
            }
            if (contextRead && selectedMessage.getReadStatus()) {
                selectedMessage.setReadStatus(false);
                contextRead = false;
                dV.xmlDateiSpeichern(selectedMessage);
            }
        }
    }

    private void mailDetail() {

        buttonReply.setOnAction((e) -> System.out.println("Reply wurde gedrueckt!"));
        buttonReplyAll.setOnAction((e) -> System.out.println("ReplyAll wurde gedrueckt!"));
        buttonForward.setOnAction((e) -> System.out.println("Forward wurde gedrueckt!"));

        Message m = table.getSelectionModel().getSelectedItem();
        String to = "";
        if (m != null) {
            for (Iterator<MessageStakeholder> it = m.getRecipients().iterator(); it.hasNext();) {
                if (to.equals("")) {
                    to = it.next().getMailAddress();
                } else {
                    to = to + "; " + it.next().getMailAddress();
                }
            }
            toHeaderMail.setText(to);
            fromHeaderMail.setText(m.getSender().getMailAddress());
            ueberschriftMail.setText(m.getSubject());
            datumHeaderMail.setText(m.getReceivedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            textFieldMail.setText(m.getText());
        }
        textFieldMail.setEditable(false);
        textFieldMail.setWrapText(true);
    }

    private void clearMailDetail() {
        toHeaderMail.setText("");
        fromHeaderMail.setText("");
        ueberschriftMail.setText("");
        datumHeaderMail.setText("");
        textFieldMail.setText("");
    }

    private void prioColumnEdit(TableColumn column) {

        column.setCellFactory((e) -> new TableCell<Message, MessageImportance>() {
            @Override
            protected void updateItem(MessageImportance importanceOfMessage, boolean empty) {
                if (empty) {
                    super.setVisible(false);
                } else {
                    super.updateItem(importanceOfMessage, empty);

                    if (importanceOfMessage == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        ImageView imageView = new ImageView();
                        Image priorityImage;
                        if (importanceOfMessage.equals(MessageImportance.LOW)) {
                            priorityImage = new Image("/resource/pfeilLow.png");
                        } else if (importanceOfMessage.equals(MessageImportance.NORMAL)) {
                            priorityImage = new Image("/resource/pfeilNormal.png");
                        } else {
                            priorityImage = new Image("/resource/pfeilHigh.png");
                        }
                        imageView.setImage(priorityImage);
                        setGraphic(imageView);
                    }
                }
            }
        });
    }

    private void receColumnEdit(TableColumn column) {
        column.setCellFactory((e) -> new TableCell<Message, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime received, boolean empty) {
                if (empty) {
                    super.setVisible(false);
                } else {
                    super.updateItem(received, empty);

                    if (received == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(received.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                    }
                }
            }
        });
    }

    private void readColumnEdit(TableColumn column) {
        column.setCellFactory((e) -> new TableCell<Message, Boolean>() {
            @Override
            protected void updateItem(Boolean readStatus, boolean empty) {
                if (empty) {
                    super.setVisible(false);
                } else {
                    super.updateItem(readStatus, empty);

                    if (readStatus == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        ImageView imageView = new ImageView();
                        Image priorityImage;
                        if (readStatus) {
                            priorityImage = new Image("/resource/true.png");
                        } else {
                            priorityImage = new Image("/resource/false.png");
                        }
                        imageView.setImage(priorityImage);
                        setGraphic(imageView);
                    }

                }
            }
        });
    }

    private void sendColumnEdit(TableColumn column) {
        column.setCellFactory((e) -> new TableCell<Message, MessageStakeholder>() {
            @Override
            protected void updateItem(MessageStakeholder sender, boolean empty) {
                if (empty) {
                    super.setVisible(false);
                } else {
                    super.updateItem(sender, empty);

                    if (sender == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(sender.getMailAddress());
                    }
                }
            }
        });
    }

    private void deletEmptyRow() {
        Callback<TableView<Message>, TableRow<Message>> myCallback2
                = new Callback<TableView<Message>, TableRow<Message>>() {
                    @Override
                    public TableRow call(TableView table) {
                        return new TableRow<Message>() {
                            @Override
                            protected void updateItem(Message item, boolean empty) {
                                if (empty) {
                                    super.setStyle("-fx-background-color: white");
                                }
                                super.updateItem(item, empty);
                            }
                        };
                    }
                };
        table.setRowFactory(myCallback2);
    }
}

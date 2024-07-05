package Controllers;


import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;


import Entites.UserEntity;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class UserController {

    public static UserEntity current_user;
    public static ObservableList<UserEntity> allusers;
    Parent root = null;
    ObservableList<String> roleList = FXCollections.observableArrayList("ADMIN", "COACH", "NUTRITIONNISTE", "ADHERENT");
    private Connection connection;
    UserService us = new UserService(connection);


    //FXML LOGIN
    @FXML
    private Label llogin;
    @FXML
    private Label lmdp;
    @FXML
    private Label lcreer;
    @FXML
    private TextField tflogin;
    @FXML
    private TextField tfmdp;

    //CREATE ACCOUNT
    @FXML
    private Label labage;

    @FXML
    private Label labemail;

    @FXML
    private Label lablogin;

    @FXML
    private Label labname;

    @FXML
    private Label labnumber;

    @FXML
    private Label labprename;

    @FXML
    private Label labpwd;

    @FXML
    private TextField txtage;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtnumber;

    @FXML
    private TextField txtprename;

    //success

    @FXML
    private Label lbienvenue;

    @FXML
    private Label lsuccess;

    @FXML
    private Label lmenu;
    @FXML
    private ImageView tfimage;
    @FXML
    private ImageView tfphoto;
    @FXML
    private TextField tfrole;
    @FXML
    private Label lprenom;

    //FXML GERER USER
    @FXML
    private Label lage;
    @FXML
    private Label lemail;
    @FXML
    private Label lnumtel;
    @FXML
    private Label lrole;
    @FXML
    private Label llogin2;
    @FXML
    private Label lmdp2;
    @FXML
    private ChoiceBox<String> chrole = new ChoiceBox<>();
    @FXML
    private Label linom;
    @FXML
    private TextField tfage;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tflogin2;
    @FXML
    private TextField tfmdp2;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfnumtel;
    @FXML
    private TextField tfprenom;
    @FXML
    private TableColumn<UserEntity, String> colrole = new TableColumn<UserEntity, String>();
    @FXML
    TableColumn<UserEntity, Void> colBtn = new TableColumn("Modification");
    @FXML
    TableColumn<UserEntity, Void> colBtun = new TableColumn("Activation/Desactivation");

    //FXML AFFICHER LISTE USERS
    @FXML
    private TableColumn<UserEntity, String> colemail = new TableColumn<UserEntity, String>();

    @FXML
    private TableColumn<UserEntity, String> colnom = new TableColumn<UserEntity, String>();

    @FXML
    private TableColumn<UserEntity, String> colprenom = new TableColumn<UserEntity, String>();

    @FXML
    private TableView<UserEntity> tableview = new TableView<>();

    //edit
    @FXML
    private TextField edage=new TextField();

    @FXML
    private TextField edemail=new TextField();

    @FXML
    private TextField edlogin=new TextField();


    @FXML
    private TextField ednom=new TextField();

    @FXML
    private TextField edprenom=new TextField();

    @FXML
    private TextField edrole=new TextField();

    @FXML
    private TextField edtel=new TextField();

    @FXML
    private AnchorPane edpane;

    @FXML
    private TextField tfrecherche;

    @FXML
    private TextField chemail;

    @FXML
    private TextField chnom;

    @FXML
    private TextField chprenom;

    @FXML
    private TextField chtel;


    // INTERFACE LOGIN
    //SIGNIN

    @FXML
    void signin(ActionEvent event) throws IOException {
        String login = tflogin.getText();
        String mdp = tfmdp.getText();
        System.out.println(login);
        System.out.println(mdp);

        if (login.length() > 0 && mdp.length() > 6) {
            current_user = UserService.login(login, mdp);
            if (current_user == null) {
                System.out.println("erreur");
            } else {
                System.out.println("role" + current_user.getRole());
                if (current_user.getRole().equals("ADMIN")) {
                    System.out.println("IS ADMIN");

                    FXMLLoader a = new FXMLLoader(getClass().getResource("/GererUsers.fxml"));
                    try {
                        Parent e = a.load();
                        tfmdp.getScene().setRoot(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else if (current_user.getRole().equals("COACH")) {
                    System.out.println("IS COACH");

                    //nom d'interface à modifier avec interface coach
                    FXMLLoader a = new FXMLLoader(getClass().getResource("/GererUsers.fxml"));
                    try {
                        Parent e = a.load();
                        tfmdp.getScene().setRoot(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else if (current_user.getRole().equals("NUTRITIONNISTE")) {
                    System.out.println("IS NUTRITIONNISTE");

                    //nom d'interface à modifier avec interface nutritionniste
                    FXMLLoader a = new FXMLLoader(getClass().getResource("/GererUsers.fxml"));
                    try {
                        Parent e = a.load();
                        tfmdp.getScene().setRoot(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else if (current_user.getRole().equals("ADHERENT")) {
                    System.out.println("IS ADHERENT");

                    //nom d'interface à modifier avec interface adherent
                    FXMLLoader a = new FXMLLoader(getClass().getResource("/GererUsers.fxml"));
                    try {
                        Parent e = a.load();
                        tfmdp.getScene().setRoot(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

        }
    }

    //SIGNIUP ADHERENT
    @FXML
    void signup(ActionEvent event) {

        FXMLLoader l = new FXMLLoader(getClass().getResource("/CreerUser.fxml"));
        Parent root = null;
        try {
            root = l.load();
            tfmdp.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void createUser(ActionEvent event) {

        System.out.println("enter");

        try {
            UserEntity user = new UserEntity();
            user.setNom(txtname.getText());
            user.setPrenom(txtprename.getText());
            user.setAge(Integer.parseInt(txtage.getText()));
            user.setEmail(txtemail.getText());
            user.setN_tel(Integer.parseInt(txtnumber.getText()));
            user.setLogin(user.getPrenom().toLowerCase().substring(0, 1) + user.getNom().toLowerCase().replace(" ", "") + user.getAge());
            user.setMdp(generatePassword());
            user.setRole("Adherent");

            if (!us.getUserByEmail(user.getEmail())) {
                us.ajouter(user);
                System.out.println("User added successfully.");
                sendFirstMail(user);
            } else System.out.println("this email is already used");
        } catch (Exception e) {
            e.printStackTrace();
        }

        FXMLLoader l = new FXMLLoader(getClass().getResource("/Success.fxml"));
        Parent root = null;
        try {
            root = l.load();
            txtname.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String generatePassword() {
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
        CharacterRule special = new CharacterRule(EnglishCharacterData.Special);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword(8, alphabets, digits, special);
        System.out.println(password);
        return password;
    }

    private void sendFirstMail(UserEntity user) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        String myEmail = "malek.moalla888@gmail.com";
        String mdp = "skhn azqu wjtn wepf";
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, mdp);

            }
        });
        String emailBody = "Dear User,\n" +
                "\n" +
                "Your new Login is: " + user.getLogin() + "\n" +
                "\n" +
                "Your new Password is: " + user.getMdp() + "\n" +
                "\n" +
                "Please keep this password secure and consider changing it after logging in.\n" +
                "\n" +
                "Best regards,\n" +
                "ProFit Team";
        Message message = prepareMessage(session, myEmail, user.getEmail(), emailBody);
        if (message != null) {
            new Alert(Alert.AlertType.INFORMATION, "Send Email Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Please Try Again").show();
        }
        Transport.send(message);
    }

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO,
                    new InternetAddress[]
                            {
                                    new InternetAddress(recipientEmail)
                            });
            message.setSubject("Welcome to ProFit");
            message.setText(msg);
            return message;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // GERERUSERS
    //GERERUTILISATEUR

    @FXML
    void AjouterUser(ActionEvent event) {
        FXMLLoader a = new FXMLLoader(getClass().getResource("/GererUtilisateur.fxml"));
        try {
            root = a.load();
            lmenu.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void InsertUser(ActionEvent event) {

        FXMLLoader l = new FXMLLoader(getClass().getResource("/GererUsers.fxml"));
        Parent root = null;
        try {
            root = l.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            UserEntity user = new UserEntity();
            user.setAge(Integer.parseInt(tfage.getText()));
            user.setN_tel(Integer.parseInt(tfnumtel.getText()));
            user.setNom(tfnom.getText());
            user.setPrenom(tfprenom.getText());
            user.setLogin(tflogin2.getText());
            user.setMdp(tfmdp2.getText());
            user.setEmail(tfemail.getText());
            user.setRole(chrole.getValue());

            if (!us.getUserByEmail(user.getEmail())) {
                us.ajouter(user);
                System.out.println("User added successfully.");
            } else System.out.println("this email is already used");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void pagePrecedente(ActionEvent event) {
        try {
            // Load the FXML file for the previous page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GererUsers.fxml"));
            Parent root = loader.load();
            if (linom != null)
                linom.getScene().setRoot(root);
            if (tableview != null)
                tableview.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //CONTROLLER LISTE DES UTILISATEURS
    //BOUTON MODIFIER
    //BOUTON ACTIVER/DESACTIVER

    @FXML
    public void initialize() {

        chrole.setValue("ADMIN");
        chrole.setItems(roleList);



        allusers = FXCollections.observableArrayList();
        loadUsersFromDatabase();
        System.out.println(allusers.size());
        colemail.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("email"));
        colemail.setText("Email");
        colnom.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("nom"));
        colnom.setText("Nom");
        colprenom.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("prenom"));
        colprenom.setText("Prenom");
        colrole.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("role"));
        colrole.setText("Role");


        //MODIFIER
        Callback<TableColumn<UserEntity, Void>, TableCell<UserEntity, Void>> cellFactory = new Callback<TableColumn<UserEntity, Void>, TableCell<UserEntity, Void>>() {
            @Override
            public TableCell<UserEntity, Void> call(final TableColumn<UserEntity, Void> param) {
                final TableCell<UserEntity, Void> cell = new TableCell<UserEntity, Void>() {

                    @FXML
                    private final Button btn = new Button("Modifier");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            UserEntity selectedUser = getTableView().getItems().get(getIndex());
                            editUser (selectedUser);
                        });
                    }

                    @Override
                    @FXML
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };


        // Bouton Activer/Désactiver

        TableColumn<UserEntity, Void> colActivate = new TableColumn<>("Activer/Désactiver");
        Callback<TableColumn<UserEntity, Void>, TableCell<UserEntity, Void>> cellFactoryActivate = new Callback<TableColumn<UserEntity, Void>, TableCell<UserEntity, Void>>() {
            @Override
            public TableCell<UserEntity, Void> call(final TableColumn<UserEntity, Void> param) {
                final TableCell<UserEntity, Void> cell = new TableCell<UserEntity, Void>() {

                    private final Button btn = new Button("Activer/Désactiver");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            UserEntity selectedUser = getTableView().getItems().get(getIndex());
                            toggleUserStatus(selectedUser);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colActivate.setCellFactory(cellFactoryActivate);

        colBtn.setCellFactory(cellFactory);
        tableview.getColumns().add(colBtn);
        colBtun.setCellFactory(cellFactoryActivate);
        tableview.getColumns().add(colBtun);
        tableview.getColumns().add(0, colnom);
        tableview.getColumns().add(1, colprenom);
        tableview.getColumns().add(2, colemail);
        tableview.getColumns().add(3, colrole);
        tableview.setItems(allusers);
    }

    private void editUser(UserEntity selectedUser) {
        try {
            selectedUser = us.findById(selectedUser.getId());
            //ednom=new TextField();
            FXMLLoader a = new FXMLLoader(getClass().getResource("/ModifierUser.fxml"));
            root = a.load();
            lmenu.getScene().setRoot(root);
            //setUser(selectedUser);

            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException exc) {
                    throw new Error("Unexpected interruption", exc);
                }
                Platform.runLater(() -> ednom.setText("Hello World!"));
            });
            thread.setDaemon(true);
            thread.start();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setUserEntity (UserEntity selectedUser) {
        ednom.setText(selectedUser.getNom());
        edprenom.setText(selectedUser.getPrenom());
        edage.setText(String.valueOf(selectedUser.getAge()));
        edemail.setText(selectedUser.getEmail());
        edtel.setText(String.valueOf(selectedUser.getN_tel()));
        edlogin.setText(selectedUser.getLogin());
        edrole.setText(selectedUser.getRole());
    }


    private void toggleUserStatus(UserEntity selectedUser) {

        boolean newStatus = !selectedUser.isActive();
        selectedUser.setActive(newStatus);
        try {
            us.update(selectedUser);
            tableview.refresh();
            System.out.println("User " + selectedUser.getNom() + " is now " + (newStatus ? "active" : "inactive"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void showUserDetails(UserEntity selectedUser) {
    }


    @FXML
    private void saveEditedUser(ActionEvent event) {
    }

    private void showAlert(String alert) {
    }

    @FXML
    public void chercherUser() throws SQLException {
    }


    private void loadUsersFromDatabase () {
        List<UserEntity> users = us.readAll();
        //System.out.println(users.size());
        allusers.setAll(users);
        System.out.println("ID:" + allusers.get(5).getId());
    }

    private void loadData() throws SQLException {
        List<UserEntity> list = us.readAll();
        ObservableList<UserEntity> data = FXCollections.observableArrayList(list);
        tableview.setItems(data);
    }


    @FXML
    void chercherUser(ActionEvent event) {
        try {
            FXMLLoader a = new FXMLLoader(getClass().getResource("/ChercherUser.fxml"));
            root = a.load();
            lmenu.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void findUser(ActionEvent event) {
        String nom = chnom.getText();
        String prenom = chprenom.getText();
        String email = chemail.getText();
        int tel = 0;
        if (chtel.getText().length() > 0) tel = Integer.parseInt(chtel.getText());
        System.out.println("Search tel: " + tel);
        UserEntity user = us.findUser(nom, prenom, email, tel);
        if (user == null) System.out.println("NO USER FOUND");
        else System.out.println(user.getId());
    }

}


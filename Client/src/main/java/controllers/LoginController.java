package controllers;

import config.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ChangeScene;
import utils.CurrentClient;
import utils.ServerCommunicator;

import java.io.IOException;

//import models.User;

public class LoginController {
    private static Logger logger = LogManager.getLogger(LoginController.class);
    public static String USERNAME;
    public static String PROFILE;
    @FXML
    private TextField usernametxt;
    @FXML
    private PasswordField passwordtxt;
    @FXML
    private Label loginlabel;
        public void goToSigninpage(ActionEvent event) throws Exception {
        logger.debug("in goToSigninpage from LoginController class on values"+event);
//        Config config=Config.getConfig("fxml");
       new ChangeScene().change_scene(event,"../SignUpPage.fxml");
    }
    public void initialize() throws IOException {
//        error.setVisible(false);
        ServerCommunicator = CurrentClient.getServerCommunicator();
    }
    public static ServerCommunicator ServerCommunicator;


    public void logIn(ActionEvent event) throws Exception {
//        ServerCommunicator sv=new ServerCommunicator();
        if (!usernametxt.getText().equals("") && !passwordtxt.getText().equals("")) {
            ServerCommunicator.send("SIGN_IN");
            ServerCommunicator.send(usernametxt.getText());
            ServerCommunicator.send(passwordtxt.getText());
            String res = ServerCommunicator.get();
            System.out.println(res);
            if (res.equals("Signed In")) {
                CurrentClient.setAuthenticationCode(ServerCommunicator.get());
                CurrentClient.setUsername(usernametxt.getText());
//                new ChangeScene("../FXML/menu.fxml");
                new ChangeScene().change_scene(event,"../Menu.fxml");
            } else {
                loginlabel.setVisible(true);
            }
        }


    }
//
//    public void loginCheck(ActionEvent event) throws Exception {
//        logger.debug("in loginCheck from LoginController class on values"+event);
//        Config config2=Config.getConfig("other");
//        Config config=Config.getConfig("fxml");
//        String username=usernametxt.getText();
//        String password=passwordtxt.getText();
////        List<user> accounts=new ArrayList<>();
////        accounts.add(new user("hi","jd","djc","js","dc"));
////        new Filemethods().saveToFile(accounts);
//        List<User> accounts=new Filemethods().loadFromFile();
//
////        System.out.println(foundeduser.getUsername());
////        Scanner s=new Scanner(System.in);
//        if(AccountsMethods.findAccount(accounts,username)!=(null)){
//            User foundeduser=AccountsMethods.findAccount(accounts,username);
//            if(foundeduser.getPassword().equals(password)){
//                this.USERNAME=username;
//                PROFILE=AccountsMethods.findAccount(accounts,username).getProfilepicUrl();
//                new ChangeScene().change_scene(event,config.getProperty("HomePage"));
//            }else {
//                loginlabel.setText(config2.getProperty("LoginWrongPass"));
//            }
//
//        }else {
//            loginlabel.setText(config2.getProperty("LoginNoAccFound")+username);
//        }
//
//    }

//    public void p(){
//        System.out.println("fuck");
//    }

}

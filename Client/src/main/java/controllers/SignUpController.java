package controllers;

import config.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ChangeScene;
import utils.CurrentClient;
import utils.ServerCommunicator;

import java.io.IOException;
import java.util.List;

public class SignUpController {
    ServerCommunicator ServerCommunicator;
    private static Logger logger = LogManager.getLogger(SignUpController.class);
    @FXML
    private TextField signinusername;
    @FXML
    private TextField signinpassword;
    @FXML
    private TextField signinconfirmpass;
    @FXML
    private TextField signinemail;
    @FXML
    private TextField signinfirstname;
    @FXML
    private TextField signinlastname;
    @FXML
    private Label signinlabel1;


//    public void signInCheck(ActionEvent event) throws Exception {
//        logger.debug("in signInCheck from SignUpController class on values"+event);
//        Config config2=Config.getConfig("other");
//        Config config=Config.getConfig("fxml");
//        String username=signinusername.getText();
//        String password=signinpassword.getText();
//        String corfirmpass=signinconfirmpass.getText();
//        String email=signinemail.getText();
//        String firstname=signinfirstname.getText();
//        String lastname=signinlastname.getText();
//        List<User> Accounts=new Filemethods().loadFromFile();
//        if(new AccountsMethods().isThereAccount(Accounts,username)){
//            signinlabel1.setText(config2.getProperty("SignUpExistAccMess"));
//        }else {
//            if(corfirmpass.equals(password)){
//                User NewUser=new User(username,firstname,lastname,email,password);
//                Accounts.add(NewUser);
//                new Filemethods().saveToFile(Accounts);
//                LoginController.USERNAME=username;
//                new ChangeScene().change_scene(event,config.getProperty("HomePage"));
//            }
//            else {
//                signinlabel1.setText(config2.getProperty("SignUpConfirmPassWrong"));
//            }
//
//        }
    public void initialize(){
        signinlabel1.setVisible(false);
        this.ServerCommunicator = CurrentClient.getServerCommunicator();
    }
public void signUp(ActionEvent event) throws Exception {
    if (!signinusername.getText().equals("") && !signinpassword.getText().equals("")) {
//        System.out.println("fedw");
        ServerCommunicator.send("SIGN_UP");
        ServerCommunicator.send(signinusername.getText());
        ServerCommunicator.send(signinpassword.getText());
        String res = ServerCommunicator.get();
        System.out.println(res);
        if (res.equals("Signed Up")){
            new ChangeScene().change_scene(event,"../Menu.fxml");
        } else {
            signinlabel1.setVisible(true);
        }
    }
}


//    }
    public void backToLogin(ActionEvent event) throws Exception {
        logger.debug("in backToLogin from SignUpController class on values"+event);
        Config config=Config.getConfig("fxml");
        new ChangeScene().change_scene(event,config.getProperty("LoginPage"));
    }
}

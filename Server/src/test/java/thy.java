import models.User;
import utils.Filemethods;
import utils.UserMethods;

import java.io.IOException;
import java.util.LinkedList;

public class thy {
    public static void main(String[] args) throws IOException {
//        User d=new User("ali","ss");
        LinkedList<User> sxr=new LinkedList<>();
//        sx.add(d);
//        Filemethods.saveToFile(sx);
        sxr=Filemethods.loadFromFile();
        System.out.println(sxr);
    }
}

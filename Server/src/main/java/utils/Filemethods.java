package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import config.Config;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Filemethods {
    private final File userDirectory;
    public Filemethods() {

        userDirectory = new File("./UserFiles   ");
        if (!userDirectory.exists()) userDirectory.mkdirs();
//        Ann();
    }
//
//    public void Ann(){
//        File File  = Config.getConfig("mainConfig").getProperty(File.class, "blueColor");
//        File File1  = Config.getConfig("mainConfig").getProperty(File.class, "redColor");
//        File File2  = Config.getConfig("mainConfig").getProperty(File.class, "database");
//        File File3  = Config.getConfig("mainConfig").getProperty(File.class, "login");
//        File File4  = Config.getConfig("mainConfig").getProperty(File.class, "signUp");
//        File File5  = Config.getConfig("mainConfig").getProperty(File.class, "mainPage");
//        File File6  = Config.getConfig("mainConfig").getProperty(File.class, "settings");
//        File File7  = Config.getConfig("mainConfig").getProperty(File.class, "groups");
//        File File8  = Config.getConfig("mainConfig").getProperty(File.class, "tweetsPane");
//        File File9  = Config.getConfig("mainConfig").getProperty(File.class, "messages");
//        File File10  = Config.getConfig("mainConfig").getProperty(File.class, "port");
//        File File11 = Config.getConfig("mainConfig").getProperty(File.class, "connection");
//
//    }

    private static Logger logger = LogManager.getLogger(Filemethods.class);
    public static LinkedList<User> loadFromFile() throws IOException {
        logger.debug("in loadFromFile from Filemethods class on values");
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        LinkedList<User> d=new LinkedList<>();
        for (int i = 0; i < e.size(); i++) {
            d.add(e.get(i));
        }
        return d;
    }
    public static void saveToFile(LinkedList<User> d) throws IOException {
        List<User> e=new ArrayList<>();
        for (int i = 0; i < d.size(); i++) {
            e.add(d.get(i));
        }
        logger.debug("in saveToFile from Filemethods class on values"+e);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Writer w=new FileWriter("Accounts.json ");
        g.toJson(e,w);
        w.close();
    }
    public void save(LinkedList<?> users,String name) {
        try {
            File file = getUserFile(name+".txt");
            assert file != null;
            PrintStream printStream = new PrintStream(file);
            Gson gson = new Gson();
            for (Object us : users) {
                String jsonInString = gson.toJson(us);
                printStream.print(jsonInString);
                printStream.println();
            }

            printStream.close();
        } catch (FileNotFoundException e) {
        }
    }


//    public List<GroupChats> loadFromFileGroup() throws IOException {
//        logger.debug("in loadFromFileGroup from Filemethods class on values");
//        Gson g=new GsonBuilder().setPrettyPrinting().create();
//        Reader r= Files.newBufferedReader(Paths.get("Groups.json"));
//        List<GroupChats> e = g.fromJson(r, new TypeToken<List<GroupChats>>() {}.getType());
//        r.close();
//        return e;
//    }
//    public void saveToFileGroup(List<GroupChats> e) throws IOException {
//        logger.debug("in saveToFileGroup from Filemethods class on values"+e);
//        Gson g=new GsonBuilder().setPrettyPrinting().create();
//        Writer w=new FileWriter("Groups.json");
//        g.toJson(e,w);
//        w.close();
//    }
private File getUserFile(String name) {
    File directory = new File(userDirectory.getAbsolutePath());
    File[] d = directory.listFiles();
    if (d != null) {
        for (File dd : d) {
            if (dd.getName().equals(name)) {
                return dd;
            }
        }
        return null;
    } else {
        return null;

    }
}


}

package utils;

import events.EventHandler;
import models.ClientHandlerConector;
import models.User;
import models.WaitingRoom;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class UserMethods {
    static Filemethods ml;
    static LinkedList<User> users;
    static LinkedList<WaitingRoom> waitingRooms;
    static LinkedList<EventHandler> onlines;
    static LinkedList<ClientHandlerConector> clientConnections;

    public UserMethods(Filemethods modelLoader) throws IOException {

        users = Filemethods.loadFromFile();

        ml = modelLoader;
        waitingRooms = new LinkedList<>();
        onlines = new LinkedList<>();
        clientConnections = new LinkedList<>();
    }
    public static String signUp(String username, String password) throws IOException {
        if (!search(username)) {
            User user = new User(username, password);
            users.add(user);
            ml.saveToFile(users);
            return "Signed Up";
        } else {
            return "Username exists";
        }
    }
//    public static void save() {
//        ml.save(users, "Users");
//    }
private static boolean search(String username) {
    for (User user : users) {
        return user.getUsername().equals(username);
    }
    return false;
}
    public static LinkedList<User> rank() {
        LinkedList<User> copy = (LinkedList<User>) users.clone();
        LinkedList<User> del = new LinkedList<>();
        while (true) {
            if (copy.size() == 0) {
                break;
            } else {
                User user = copy.getFirst();
                for (User us : copy) {
                    if (us.getScores() > user.getScores()) {
                        user = us;
                    }
                }
                del.add(user);
                copy.remove(user);
            }
        }
        return del;
    }

    public static void won(String winner, String loser) throws IOException {
        User win = findUser(winner);
        User lose = findUser(loser);
        assert win != null;
        win.setWin(win.getWin() + 1);
        assert lose != null;
        lose.setLose(lose.getLose() + 1);
        waitingRooms.removeIf(waitingRooms -> waitingRooms.getGameState().getPlayer1().equals(winner) || waitingRooms.getGameState().getPlayer2().equals(winner));
        Filemethods.saveToFile(users);
    }
    public static String signIn(String user, String pass) {
//        System.out.println(user + " " + pass);
//        System.out.println(users.size());
        for (User u : users) {
//            System.out.println(u.getUsername() + " " + u.getPassword());
            if (u.getUsername().equals(user) && u.getPassword().equals(pass)) {
                Random random = new Random();
                int rand = random.nextInt(100000);
                return Integer.toString(rand);
            }
        }
        return "Error";
    }
    public static User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static LinkedList<WaitingRoom> getLobbies() {
        return waitingRooms;
    }

    public static void setLobbies(LinkedList<WaitingRoom> lobbies) {
        UserMethods.waitingRooms = lobbies;
    }
    public static boolean searchOnline(String username) {
        for (EventHandler sh : onlines) {
            if (sh.getUser().getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    public static void addOnline(EventHandler onlines) {
        UserMethods.onlines.add(onlines);
    }

}

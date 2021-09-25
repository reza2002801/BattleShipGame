package events;

import com.google.gson.Gson;
import models.ClientHandlerConector;
import models.Game;
import models.User;
import models.WaitingRoom;
import utils.UserMethods;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;


public class EventHandler extends Thread{
    WaitingRoom currentwaitingroom;
    private String Authorization;
    private  final Socket socket;
    User user;
    public final DataOutputStream dataOutputStream;
    public final DataInputStream dataInputStream;
    public EventHandler(Socket socket) throws IOException {
        this.socket = socket;
//        System.out.println("hear");
//        String s=new Scanner(System.in).nextLine();
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }
    @Override
    public void start() {
        super.start();
    }
    public void run() {
        while (true) {
            try {
                String f=dataInputStream.readUTF();
                System.out.println(f);
                if(f.equals("SIGN_IN")){
//                        System.out.println(dataInputStream.readUTF());

                    signIn();
                }
                else if(f.equals("SIGN_UP")){
//                    System.out.println("sdc");
                    signUp();
                }
                else if(f.equals(this.Authorization) ){
                    String t= dataInputStream.readUTF();
                    System.out.println(t);

                    if(t.equals("WAIT")){
                        waiting();
                    }
                    else if(t.equals("GAME")){
                        game();

                    }
                    else if(t.equals("SPECTATE")){
                        spectate();

                    }
                    else if(t.equals("PROFILE")){
                        profile();

                    }
                    else if(t.equals("LEADERBOARD")){
                        leaderboard();
                    }
                    else if(t.equals("GO")){
//                        System.out.println("ftg");
                        go();

                    }
                    else if(t.equals("WATCH")){
                        watch();
                    }
                    else if(t.equals("Exit")){
                        System.out.println("sud");
                        break;
                    }
                }

//
//                private void signUp() throws IOException {
//                    String user = dataInputStream.readUTF();
//                    System.out.println(user);
//                    String pass = dataInputStream.readUTF();
//                    if (Users.signUp(user, pass).equals("Signed Up")) {
//                        dataOutputStream.writeUTF("Signed Up");
////                        SignInHandler signInHandler = new SignInHandler(socket);
////                        signInHandler.start();
//                    } else {
//                        dataOutputStream.writeUTF("Username is already exists");
//                    }
//                }


//                switch (dataInputStream.readUTF()) {
//
//                    case "SIGN_UP" -> signUp();
//                    case "GAME" -> game();
//                    case "WAIT" -> waiting();
//                    case "SPECTATE" -> spectate();
//                    case "PROFILE" -> profile();
//                    case "LEADERBOARD" -> leaderboard();
//                    case "GO" -> go();
//                    case "WATCH" -> watch();
//                }
            } catch (IOException e) {
                System.out.print("");;
            }
        }
    }
    private void watch() throws IOException {
        String str = dataInputStream.readUTF();
        WaitingRoom waitingRooms = null;
        for (WaitingRoom waitingRoom : UserMethods.getLobbies()) {
            if (waitingRoom.getGameState().getPlayer1().equals(str) || waitingRoom.getGameState().getPlayer2().equals(str)) {
                waitingRooms = waitingRoom;
            }
        }
        assert waitingRooms != null;
        WaitingRoom finalLobb = waitingRooms;
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    dataOutputStream.writeUTF(finalLobb.getGameState().spectate());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    private void spectate() throws IOException {
        LinkedList<WaitingRoom> waitingRooms = UserMethods.getLobbies();
        LinkedList<SpectateResponse> sr = new LinkedList<>();
        for (WaitingRoom waitingRoom : waitingRooms) {
            Game game = waitingRoom.getGameState();
            SpectateResponse spectateResponse = new SpectateResponse(game.getPlayer1(), game.getPlayer2(), game.getTurns(), game.getDestroyed());
            sr.add(spectateResponse);
        }
        Gson gson = new Gson();
        dataOutputStream.writeUTF(String.valueOf(sr.size()));
        for (SpectateResponse spectateResponse : sr) {
            String str = gson.toJson(spectateResponse);
            dataOutputStream.writeUTF(str);
        }
    }
    private void leaderboard() throws IOException {
        LinkedList<User> del = UserMethods.rank();
        int c = 0;
        if (del.size() >= 10) {
            c = 10;
            dataOutputStream.writeUTF("10");
        } else {
            c = del.size();
            dataOutputStream.writeUTF(String.valueOf(del.size()));
        }
        Gson gson = new Gson();
        for (int i = 0; i < c; i++) {
            del.get(i).setOnline(UserMethods.searchOnline(del.get(i).getUsername()));
            dataOutputStream.writeUTF(gson.toJson(del.get(i)));

        }
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }
    private void signUp() throws IOException {
//        System.out.println(dataInputStream.readUTF());

        String user = dataInputStream.readUTF();
        System.out.println(user);
        String pass = dataInputStream.readUTF();
        System.out.println(pass);
        if (UserMethods.signUp(user, pass).equals("Signed Up")) {
            dataOutputStream.writeUTF("Signed Up");
//                        SignInHandler signInHandler = new SignInHandler(socket);
//                        signInHandler.start();
        } else {
            dataOutputStream.writeUTF("Username is already exists");
        }
    }
    private void go() throws IOException {
//        System.out.println("fu");
        if (currentwaitingroom.isReady()) {
            dataOutputStream.writeUTF("YES");
        } else {
            dataOutputStream.writeUTF("NO");
        }
    }
    private void game() throws IOException {
        int c = currentwaitingroom.getNumber();
        ClientHandlerConector player = new ClientHandlerConector(socket, c, currentwaitingroom.getGameState(), user);
        player.sendToClient(Integer.toString(player.getPlayerType()));
        player.socket=socket;
        player.waitingRoom=currentwaitingroom;
        player.sendToClient(currentwaitingroom.getGameState().sendBack(c));
        player.start();
        currentwaitingroom.start();
//        UserMethods.getLobbies().remove(currentwaitingroom);
//        player=null;
    }

    private void waiting() {
        LinkedList<WaitingRoom> waitingRooms = UserMethods.getLobbies();
        Game gameState = new Game();

        if (waitingRooms.isEmpty()) {
            waitingRooms.add(new WaitingRoom(gameState));
        }
        else {
            if (waitingRooms.getLast().isFull()) {
                waitingRooms.add(new WaitingRoom(gameState));
            }
        }
        WaitingRoom waitingRoom = waitingRooms.getLast();
        int c = 1;
        if (waitingRoom.isSomeOneWaiting()) {
            c++;
        }
        if (waitingRoom.getGameState().getPlayer1() == null) {
            waitingRoom.getGameState().setPlayer1(this.user.getUsername());
        } else {
            waitingRoom.getGameState().setPlayer2(this.user.getUsername());
        }
        currentwaitingroom = waitingRoom;
        waitingRoom.joinMatch(this);
        UserMethods.setLobbies(waitingRooms);
    }
    private void profile() throws IOException {
        dataOutputStream.writeUTF(user.getUsername());
        dataOutputStream.writeUTF(Integer.toString(user.getWin()));
        dataOutputStream.writeUTF(Integer.toString(user.getLose()));
        dataOutputStream.writeUTF(Integer.toString(user.getScores()));
    }

    private void signIn() throws IOException {
        String username = dataInputStream.readUTF();
        String pass = dataInputStream.readUTF();
        System.out.println(username+pass);
        String str = UserMethods.signIn(username,pass);
        System.out.println(str);
        if (str != null ) {
            if (!str.equals("Error")) {
                dataOutputStream.writeUTF("Signed In");
                dataOutputStream.writeUTF(str);
                setAuthorization(str);
                // Auth Token
                user = UserMethods.findUser(username);
                if (user != null) {
                    UserMethods.addOnline(this);
                }
            } else {
                dataOutputStream.writeUTF("Username or Password is incorrect");
            }
        }else {
            dataOutputStream.writeUTF("Username or Password is incorrect");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

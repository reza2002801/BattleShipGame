import events.Servers;
import utils.Filemethods;
import utils.UserMethods;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Filemethods m = new Filemethods();

        UserMethods users = new UserMethods(m);

        Servers server = new Servers();

        server.run();


    }
}

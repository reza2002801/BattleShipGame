package utils;

import config.Config;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunicator extends Thread{
//    public Socket socket;
//    public DataOutputStream dataOutputStream;
//    public java.io.DataInputStream dataInputStream;
//
//    public ServerCommunicator() throws IOException {
//        socket=new Socket("localhost",9000);
//        dataOutputStream=new DataOutputStream(socket.getOutputStream());
//        dataInputStream=new DataInputStream(socket.getInputStream());
//    }
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private int playerType;

    public   ServerCommunicator() throws IOException {
        Socket socket = new Socket("localhost",9000);
//        Config config2=Config.getConfig("ports");
//        if(!config2.getProperty("port1").equals("")){
//            socket = new Socket(config2.getProperty("host"),Integer.valueOf(config2.getProperty("port1")));
//        }else {
//            socket = new Socket("localhost",8000);
//        }

//        socket = new Socket("localhost",9000);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
//        playerType = Integer.parseInt(dataInputStream.readUTF());
    }
    public void send(String s) throws IOException {
        dataOutputStream.writeUTF(s);
    }
    public String get() throws IOException {
        return dataInputStream.readUTF();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

}

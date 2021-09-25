package utils;

import java.io.IOException;

public class CurrentClient {
    public static String Username="";
    public static ServerCommunicator serverCommunicator;
    public static String AuthenticationCode;
    static public String spectateName;

    public CurrentClient() throws IOException {
        serverCommunicator = new ServerCommunicator();
        serverCommunicator.start();
    }

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static ServerCommunicator getServerCommunicator() {
        return serverCommunicator;
    }

    public static void setServerCommunicator(ServerCommunicator serverCommunicator) {
        serverCommunicator = serverCommunicator;
    }

    public static String getAuthenticationCode() {
        return AuthenticationCode;
    }

    public static void setAuthenticationCode(String authenticationCode) {
        AuthenticationCode = authenticationCode;
    }
    public static String getSpectateName() {
        return spectateName;
    }

    public static void setSpectateName(String spectateName) {
        CurrentClient.spectateName = spectateName;
    }

}

package models;

public class User {
    private final String username;
    private final String password;
    private int scores;
    private boolean Online;
    private int win;
    private int lose;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.win = 0;
        this.lose = 0;
        this.Online = false;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int wins) {
        this.win = wins;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int score) {
        this.scores = score;
    }

    public boolean Online() {
        return Online;
    }

    public void setOnline(boolean online) {
        Online = online;
    }
}

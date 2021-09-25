package models;

public class MainResponse {

    //    int type;
    int time;
    int turn;
    boolean isFinished;

    public MainResponse(int time, int turn, boolean isFinished){
        this.time = time;
        this.turn = turn;
        this.isFinished = isFinished;
    }


    public int getTime() {
        return time;
    }


    public int getTurn(){
        return turn;
    }

    public boolean getIsFinished(){
        return isFinished;
    }
}

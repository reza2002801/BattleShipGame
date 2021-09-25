package models;

public class WatchResponse {

    int[][] myMatrix;
    int[][] oppMatrix;
    int[][] myShips;
    int[][] oppShips;
    int time;
    public int turn;
    public String player1;
    public String player2;
    public boolean isfinished=false;

    WatchResponse(int[][] matrix,int[][] oppMatrix,int[][]myShips,int[][]oppShips, int time,int turn,String player1,String player2){
        this.myMatrix = matrix;
        this.oppMatrix = oppMatrix;
        this.myShips = myShips;
        this.oppShips = oppShips;
        this.time = time;
        this.turn = turn;
        this.player1=player1;
        this.player2=player2;
    }

    public int[][] getMyMatrix() {
        return myMatrix;
    }

    public int[][] getOppMatrix() {
        return oppMatrix;
    }

    public int[][] getMyShips() {
        return myShips;
    }

    public int[][] getOppShips() {
        return oppShips;
    }

    public int getTime() {
        return time;
    }

    public int getTurn() {
        return turn;
    }
}

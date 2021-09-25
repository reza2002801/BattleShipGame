package models;

public class Response {
    private final int[][] myMatrix;
    private final int[][] oppMatrix;
    private final int[][] myShips;
    int type;
    private String Player1;
    private  String Player2;

    public String getPlayer1() {
        return Player1;
    }

    public void setPlayer1(String player1) {
        Player1 = player1;
    }

    public String getPlayer2() {
        return Player2;
    }

    public void setPlayer2(String player2) {
        Player2 = player2;
    }

    public Response(int[][] matrix, int[][] oppMatrix, int[][] myShips, int type, String Player1, String Player2) {
        this.myMatrix = matrix;
        this.oppMatrix = oppMatrix;
        this.myShips = myShips;
        this.type = type;
        this.Player2 = Player2;
        this.Player1 = Player1;
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
}

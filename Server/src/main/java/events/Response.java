package events;

public class Response {
    private final int[][] myMatrix;
    private final int[][] oppMatrix;
    private final int[][] myShips;
    int type;
    private String Player1;
    private  String Player2;

    public Response(int[][] matrix, int[][] oppMatrix, int[][] myShips, int type,String Player1,String Player2) {
        this.myMatrix = matrix;
        this.oppMatrix = oppMatrix;
        this.myShips = myShips;
        this.type = type;
        this.Player2 = Player2;
        this.Player1 = Player1;
    }
}

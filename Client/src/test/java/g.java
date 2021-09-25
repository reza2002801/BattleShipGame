import java.io.File;

public class g {
    static public int t ;

    public static int getT() {
        return t;
    }

    public static void setT(int k) {
        t = k;
    }

    public g() {
    }


    public static void main(String[] args) {
//        setT(5);
        File userDirectory = new File("./Userss");
        userDirectory.mkdirs();
    }
}

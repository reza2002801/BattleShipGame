package models;

public class TimerClass extends Thread {
    private boolean isRunning;
    public int time;
    public boolean isTurn1;
    public boolean firstTime;
    public boolean player1ready;
    public boolean player2ready;

    TimerClass(){
        isRunning = true;
        time = 30;
        isTurn1 = true;
        firstTime=true;
        player1ready=false;
        player2ready=false;
    }

    @Override
    public void run() {
        super.run();
        while (isRunning) {
            if(firstTime==true){
                if (time <= 0) {
                    firstTime=false;
                    player2ready=true;
                    player1ready=true;
                    continue;
//                    time = 30;
//                    isTurn1 = !isTurn1;
                } else {
                    time--;
                    if(player1ready==true &&player2ready==true){
                        time=25;
                        firstTime=false;
                        continue;
                    }
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                if (time <= 0) {
                    time = 25;
                    isTurn1 = !isTurn1;
                } else {
                    time--;
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}

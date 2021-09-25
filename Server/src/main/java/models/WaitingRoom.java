package models;

import events.EventHandler;

import java.io.IOException;
import java.util.LinkedList;

public class WaitingRoom extends Thread {
    private Game game;
    private EventHandler firstPlayer;
    ClientHandlerConector player1;
    ClientHandlerConector player2;
    private EventHandler secondPlayer;
    private boolean isReady;
    private boolean isFull;
    private boolean isSomeOneWaiting;
    public boolean isFinished;
    private LinkedList<Integer> nums;
    private Thread worker;

    public WaitingRoom(Game game) {
        this.isReady = false;
        this.game = game;
        isSomeOneWaiting = false;
        nums = new LinkedList<>();
        nums.add(1);
        nums.add(2);
    }
//    public void f(){
//        start();
//    }

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("raftim prep");
        try {
            prepPhase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!isFinished) {
//            player2.setCurrentState(gameState.sendBack(2));
//            player1.setCurrentState(gameState.sendBack(1));
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
        System.out.println("end");
    }
//    @Override
//    public void run(){
//        System.out.println("raftim prep");
//        try {
//            prepPhase();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        while (!isFinished) {
//            System.out.println("uygdstfga");
////            player2.setCurrentState(gameState.sendBack(2));
////            player1.setCurrentState(gameState.sendBack(1));
////        try {
////            Thread.sleep(50);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//        }
//        System.out.println("hhh");
//    }
//    public void stop(){
//        isFinished=true;
//    }


    public synchronized void joinMatch(EventHandler player) {
        if (firstPlayer != null) {
            this.secondPlayer = player;
//            start();
            isReady = true;
            isFull = true;
            System.out.println("We're Ready");
        } else {
            firstPlayer = player;
            isSomeOneWaiting = true;
        }
    }

    public void prepPhase() throws IOException {
//        gameState = new GameState();
        isFinished = false;
        game.startGame();
        System.out.println("game shoro shod");
//        player1.sendToClient(gameState.sendBack(1));
//        player2.sendToClient(gameState.sendBack(2));
    }

    public int getNumber(){
        int a = nums.getLast();
        nums.removeLast();
        return a;
    }

    public boolean isFull() {
        return isFull;
    }

    public boolean isSomeOneWaiting() {
        return isSomeOneWaiting;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Game getGameState() {
        return game;
    }

    public ClientHandlerConector getPlayer1() {
        return player1;
    }

    public void setPlayer1(ClientHandlerConector player1) {
        this.player1 = player1;
    }

    public ClientHandlerConector getPlayer2() {
        return player2;
    }

    public void setPlayer2(ClientHandlerConector player2) {
        this.player2 = player2;
    }

    public boolean isReady() {
        return isReady;
    }

}

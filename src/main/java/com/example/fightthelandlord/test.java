package com.example.fightthelandlord;

import javafx.util.Pair;

import java.io.IOException;

public class test {
    public static void main(String[]args) throws IOException, InterruptedException {
        gamePage game=new gamePage();
        if(game.getConnectStatus()){
            game.gameStart();
        }
        else {
            System.out.println("Connect Error");
        }


    }
}

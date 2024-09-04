package com.example.fightthelandlord;

import javafx.util.Pair;

public class test {
    public static void main(String[]args){
        Pair<Integer,Integer>a= new Pair<>(5, 3);
        Deck deck=new Deck(1,1,2);
        Card cardA=new Card(1,1);
        Card cardB=new Card(2,1);
        deck.add(cardA);
        deck.add(cardB);
        deck.check();
        gamePage s=new gamePage();
    }
}

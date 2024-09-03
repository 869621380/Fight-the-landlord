package com.example.fightthelandlord;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.IOException;

//卡牌类

//发牌
class Dissing {
    //创建一副牌,0表示3，13~小王，14~大王
    List<Card> deck = new ArrayList<>();

    int[] sizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    //牌的花色
    int[] suits = {0, 1, 2, 3};

    public Dissing() {
        for (int size : sizes) {
            for (int suit : suits) {
                deck.add(new Card(size, suit));
            }
        }
        //添加大小王
        deck.add(new Card(13, 0));
        deck.add(new Card(14, 0));

        //洗牌
        Collections.shuffle(deck);

        //创建三个玩家
        List<Card> player1 = new ArrayList<>();
        List<Card> player2 = new ArrayList<>();
        List<Card> player3 = new ArrayList<>();
        for (int i = 0; i < 51; i += 3) {
            player1.add(deck.get(i));
            player2.add(deck.get(i + 1));
            player3.add(deck.get(i + 2));
        }
        //剩余三个地主牌
        List<Card> bottonCards = deck.subList(51, 54);

        //打印玩家手牌
        System.out.println("player1's cards:");
        for (Card card : player1) {
            System.out.println(card.getCardInfo() + " ");
        }
        System.out.println("player2's cards:");
        for (Card card : player2) {
            System.out.println(card.getCardInfo() + " ");
        }
        System.out.println("player3's cards:");
        for (Card card : player3) {
            System.out.println(card.getCardInfo() + " ");
        }

    }
}

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
    }

    public static void main(String[] args) {
        Dissing diss = new Dissing();
    }
}
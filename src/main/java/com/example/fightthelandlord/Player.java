package com.example.fightthelandlord;
import java.util.ArrayList;
import java.util.Iterator;


public class Player {
    public Player(){
        this.deck = new ArrayList<>();
        identity=0;
    }
    //无参初始化
    public Player(ArrayList<Card>theDeck){
        this.deck = new ArrayList<>(theDeck);
        identity=0;
    }

    //获取卡牌剩余数目
    int getCardCount(){
        return deck.size();
    }

    //接收卡牌
    void receiveCard(ArrayList<Card>deck){
        for (Card card : deck) {
            this.deck.add(card);
        }
    }

    int showScore(){
        int score=getScore();
        //界面操作
        return score;
    }

    //
    int getScore(){
        return 0;
    }

    //设置身份农民 or 地主
    void setIdentity(int theIdentity){
        identity=theIdentity;
    }

    //获取身份
    int getIdentity(){
        return identity;
    }

    //玩家类型 1：地主 2：农民 0:未定义
    private int identity;
    //玩家手中卡组//个人认为访问量更多采用ArrayList
    private ArrayList<Card> deck;

}

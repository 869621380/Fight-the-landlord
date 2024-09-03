package com.example.fightthelandlord;
import java.util.ArrayList;




public class Player {
    //无参初始化
    public Player(ArrayList<Card>theDeck){
        this.deck = new ArrayList<>(theDeck);
        identity=0;
    }

    //获取卡牌剩余数目
    int getCardCount(){
        return deck.size();
    }

    //发牌阶段插入卡牌
    void insertCard(Card newCard){
        deck.add(newCard);
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

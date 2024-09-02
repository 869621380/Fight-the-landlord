package com.example.fightthelandlord;
import java.util.ArrayList;




public class Player {
    //无参初始化
    public Player(){
        this.deck = new ArrayList<>();
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

    //设置身份农民 or 地主
    void setIdentity(int theIdentity){
        identity=theIdentity;
    }

    //获取身份
    int getIdentity(){
        return identity;
    }

    //在分排结束后对卡组进行排序
    void cardSorted(){
        this.deck.sort((p1, p2) -> {
            int sizeComparison = Integer.compare(p1.getSize(), p2.getSize());
            if (sizeComparison != 0) {
                return sizeComparison;
            } else {
                return Integer.compare(p1.getSuit(), p2.getSuit());
            }
        });
    }

    //玩家类型 1：地主 2：农民 0:未定义
    int identity;
    //玩家手中卡组//个人认为访问量更多采用ArrayList
    ArrayList<Card> deck;

}

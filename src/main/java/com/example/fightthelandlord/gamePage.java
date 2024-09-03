package com.example.fightthelandlord;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class gamePage {
    //斗地主游戏界面
    gamePage(){

    }
    //开始游戏
    void gameStart(){
        dealCard();
    }
    //发牌
    void dealCard(){
        ArrayList<Card> deck = new ArrayList<>();
        //创建一副牌,0表示3，13~小王，14~大王
        int[] sizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        //牌的花色
        int[] suits = {0, 1, 2, 3};

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

        //创建三个卡组
        ArrayList<Card> cards1 = new ArrayList<>();
        ArrayList<Card> cards2 = new ArrayList<>();
        ArrayList<Card> cards3 = new ArrayList<>();
        //分发手牌
        for (int i = 0; i < 51; i += 3) {
            cards1.add(deck.get(i));
            cards2.add(deck.get(i + 1));
            cards3.add(deck.get(i + 2));
        }

        //剩余三个地主牌
        List<Card> subList = deck.subList(51, 54); // 获取子列表
        ArrayList<Card> bottomCards = new ArrayList<>(subList);

        //洗牌
        cardSorted(cards1);
        cardSorted(cards2);
        cardSorted(cards3);

        //打印玩家手牌
        System.out.println("player1's cards:");
        for (Card card : cards1) {
            System.out.println(card.getCardInfo() + " ");
        }
        System.out.println("player2's cards:");
        for (Card card : cards2) {
            System.out.println(card.getCardInfo() + " ");
        }
        System.out.println("player3's cards:");
        for (Card card : cards3) {
            System.out.println(card.getCardInfo() + " ");
        }

        players[0]=new Player(cards1);
        players[1]=new Player(cards2);
        players[2]=new Player(cards3);
    }
    //在分排结束后对卡组进行排序
    void cardSorted(ArrayList<Card> deck){
        deck.sort((p1, p2) -> {
            int sizeComparison = Integer.compare(p1.getSize(), p2.getSize());
            if (sizeComparison != 0) {
                return sizeComparison;
            } else {
                return Integer.compare(p1.getSuit(), p2.getSuit());
            }
        });
    }

    //抢地主
    void snatchLandlord(){
        int maxScore=0;
        int []score=new int[3];
        boolean hasChoose=false;
        //获取玩家分数，三分直接结束
        for(int i=0;i<3;i++){
            score[i]=players[i].getScore();
            if(score[i]==3){
                players[i].setIdentity(1);
                hasChoose=true;
                break;
            }
            else maxScore=Math.max(maxScore,score[i]);
        }
        //相同分数没有选出地主选择第一个等于最高分数的
        if(!hasChoose){
            for(int i=0;i<3;i++)
                if(score[i]==maxScore){
                    players[i].setIdentity(1);
                    break;
                }
        }
        //设置农民身份
        for(int i=0;i<3;i++){
            if(players[i].getIdentity()==0)
                players[i].setIdentity(2);
        }
    }

    //出牌阶段


    //报单报双
    int onlyOneOrTwo(Player player){
        int cardCount= player.getCardCount();
        if(cardCount==1||cardCount==2)
            return cardCount;
        return 0;
    }
    //胜利检验
    boolean checkWin(){
        for(int i=0;i<3;i++){
            if(players[i].getCardCount()==0)
                return true;
        }
        return false;
    }

    private Player[]players;

}

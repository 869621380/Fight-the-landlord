package com.example.fightthelandlord;
import java.util.ArrayList;

public class Player {
    //无参初始化
    public Player(){
        this.deck = new ArrayList<>();
        identity=0;
    }

    //含手牌初始化
    public Player(ArrayList<Card>theDeck){
        this.deck = new ArrayList<>(theDeck);
        identity=0;
    }

    //获取卡牌剩余数目
    public int getCardCount(){
        return deck.size();
    }

    //清空卡牌
    public void clearCard(){
        deck.clear();
    }

    //接收卡牌
    public void receiveCard(ArrayList<Card>deck){
        this.deck.addAll(deck);
        deck.sort((p1, p2) -> {
            int sizeComparison = Integer.compare(p1.getSize(), p2.getSize());
            if (sizeComparison != 0) {
                return sizeComparison;
            } else {
                return Integer.compare(p1.getSuit(), p2.getSuit());
            }
        });
    }

    //设置玩家选择的房间

    public void setSelectRoom(String selectRoom) {
        this.selectRoom = selectRoom;
    }

    public String getSelectRoom() {
        return selectRoom;
    }

    //设置身份农民 or 地主
    public void setIdentity(int theIdentity){
        identity=theIdentity;
    }

    //获取当前手牌
    public ArrayList<Card>getDeck(){
        return new ArrayList<>(deck);
    }

    //移除卡牌
    public void removeCard(ArrayList<Card>cards){
        for(Card card:cards){
            deck.remove(card);
        }
    }

    //获取身份
    int getIdentity(){
        return identity;
    }

    //设置准备状态
    public void setState(String state){
        this.state=state;
    }
    public String getState(){return state;}
    //玩家类型 1：地主 2：农民 0:未定义
    private int identity;
    //玩家手中卡组、
    private ArrayList<Card> deck;
    private String state;
    private String selectRoom;//玩家选择的房间

}

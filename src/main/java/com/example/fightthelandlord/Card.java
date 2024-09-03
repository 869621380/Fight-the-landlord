package com.example.fightthelandlord;
import javafx.util.Pair;
public class Card {
    //卡牌大小3~big joker用0~14表示
    //卡牌类型♥️，♠️，♣️，♦️用0~3表示

    //根据大小和花色构建卡牌
    public Card(int size, int suit) {
        this.cardElement = new Pair<>(size, suit);
    }

    //获取牌面大小
    final int getSize(){
        return cardElement.getKey();
    }

    //获取花色
    final int getSuit(){
        return cardElement.getValue();
    }

    //牌面信息输出
    public String getCardInfo() {
        return cardElement.getKey().toString() + " " + cardElement.getValue().toString();
    }

    private Pair<Integer, Integer>cardElement;
}

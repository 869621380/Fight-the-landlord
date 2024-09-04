package com.example.fightthelandlord;
import javafx.util.Pair;
public class Card implements Comparable<Card>{
    //卡牌大小3~big joker用0~14表示
    //卡牌类型♥️，♠️，♣️，♦️用0~3表示

    //根据大小和花色构建卡牌
    public Card(int size, int suit) {
        this.cardElement = new Pair<>(size, suit);
    }

    //获取牌面大小
    public int getSize(){
        return cardElement.getKey();
    }

    //获取花色
    public int getSuit(){
        return cardElement.getValue();
    }

    public String getCardInfo() {
        return cardElement.getKey().toString() + " " + cardElement.getValue().toString();
    }
    @Override
    public int compareTo(Card other) {
        // 首先比较大小
        int sizeComparison = Integer.compare(this.cardElement.getKey(), other.cardElement.getKey());
        if (sizeComparison != 0) {
            return sizeComparison;
        }
        // 如果大小相同，再比较花色
        return Integer.compare(this.cardElement.getValue(), other.cardElement.getValue());
    }

    Pair<Integer, Integer>cardElement;
}

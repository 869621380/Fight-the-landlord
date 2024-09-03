package com.example.fightthelandlord;
import javafx.util.Pair;
public class Card implements Comparable<Card>  {
    //卡牌大小3~big joker用0~14表示
    //卡牌类型♥️，♠️，♣️，♦️用0~3表示

    //根据大小和花色构建卡牌
    public Card(int size, int suit) {
        this.cardElement = new Pair<>(size, suit);
    }

    //根据服务器数据创建卡牌
    public Card(String size,String suit){
        this.cardElement=new Pair<>(Integer.parseInt(size),Integer.parseInt(suit));
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

    //
    @Override
    public int compareTo(Card other) {
        // 首先比较第一个整数
        int compareFirst = this.cardElement.getKey().compareTo(other.cardElement.getKey());
        if (compareFirst != 0) {
            return compareFirst;
        }
        // 如果第一个整数相同，则比较第二个整数
        return this.cardElement.getValue().compareTo(other.cardElement.getValue());
    }
    private Pair<Integer, Integer>cardElement;
}

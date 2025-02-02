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

    //输出卡牌信息方便调试
    public String getCardInfo() {
        int size=cardElement.getKey();
        String alpha;
        if(size<7)alpha= String.valueOf((char) ('3' +size));
        else{
            if(size==7)alpha="10";
            else if(size==8)alpha="J";
            else if(size==9)alpha="Q";
            else if(size==10)alpha="K";
            else if (size==11)alpha="A";
            else if(size==12)alpha="2";
            else if(size==13)alpha="Small Joker";
            else alpha="Big Joker";
        }
        return "["+alpha+","+cardElement.getValue()+"] ";
    }

    //实现可比较功能
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

    //判断相同
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // 同一对象
        if (obj == null || getClass() != obj.getClass()) return false; // 类型检查
        Card other = (Card) obj; // 强制转换
        return this.cardElement.equals(other.cardElement); // 比较 cardElement
    }
    @Override
    public int hashCode() {
        return cardElement.hashCode(); // 使用 cardElement 的 hashCode
    }

    Pair<Integer, Integer>cardElement;
}

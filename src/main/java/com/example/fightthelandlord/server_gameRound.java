//package com.example.fightthelandlord;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.List;
//
//public class server_gameRound {
//    private ArrayList<server_player> players;//三个玩家
//    private ArrayList<Card> remain;
//    private ArrayList<String> n;//抢地主的点数
//    private int whoIsLord;//地主玩家序号
//    private ArrayList<Card> allCards;
//
//    /**
//     * &#064;title:  gameRound
//     * &#064;description:  构造函数,相关属性初始化
//     * @param  players ArrayList<Player>
//     */
//    public server_gameRound(ArrayList<server_player> players) {
//        this.players = players;
//        n = new ArrayList<>();
//        //==================================
//    }
//
//    /**
//
//     * &#064;title:  gameStart
//
//     * &#064;description:  游戏发牌到出牌阶段
//
//     */
//    public void gameStart() {
//        for(server_player player : players) {
//            player.setInGame(true);//设置玩家在游戏内，回合结束直接开始准备阶段
//        }
//        do {
//            //发牌
//            dealCard();
//            //==================================
//            //抢地主
//        }while(!finishLord());//抢地主，如果都不抢地主，则从新发牌
//        //出牌循环
//        int i =whoIsLord;//记录谁是地主
//        while(true){
//            //接收出的牌
//            sendToOne(i,"请你出牌");
//            ArrayList<Card> r = receiveCard(i);
//            if (r != null) {
//                for (Card x : players.get(i).getPlayerCard()) {
//                    Iterator<Card> rIterator = r.iterator();
//                    while (rIterator.hasNext()) {
//                        Card cardInR = rIterator.next();
//                        if ((x.getSize() == cardInR.getSize()) && (x.getSuit() == cardInR.getSuit())) {
//                            rIterator.remove();
//                            break; // Found and removed the matching card, break to avoid modifying list during iteration
//                        }
//                    }
//                }
//            }
//            if(players.get(i).getPlayerCard().isEmpty()){//如果玩家出完牌了
//                sendToAll("游戏结束");//提示游戏结束
//                if (i == whoIsLord){
//                    sendToOne(i,"you");
//                    sendToOne((i+1)%3,"l");//你左边的玩家赢了
//                    sendToAnotherTwo((i+2)%3,"r");//你右边的玩家赢了
//                }else{
//                    sendToAll("");//========================未设置信号
//                }
//                break;
//            }
//            System.out.println("第"+i+":");
//            for (Card p: players.get(i).getPlayerCard()) {
//                System.out.println(p.getSize()+p.getSuit());
//            }
//            sendCardToAnotherTwo(i,r);//向玩家发送此玩家出的牌
//            i = ( i + 1 ) % 3;
//        }
//    }
//
//
//
//
//    /**
//     * 发牌
//     */
////发牌
//    void dealCard(){
//        ArrayList<Card> deck = new ArrayList<>();
//        //创建一副牌,0表示3，13~小王，14~大王
//        int[] sizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
//        //牌的花色
//        int[] suits = {0, 1, 2, 3};
//
//        for (int size : sizes) {
//            for (int suit : suits) {
//                deck.add(new Card(size, suit));
//            }
//        }
//        //添加大小王
//        deck.add(new Card(13, 0));
//        deck.add(new Card(14, 0));
//        //洗牌
//        Collections.shuffle(deck);
//
//        //创建三个卡组
//        ArrayList<Card> cards1 = new ArrayList<>();
//        ArrayList<Card> cards2 = new ArrayList<>();
//        ArrayList<Card> cards3 = new ArrayList<>();
//        //分发手牌
//        for (int i = 0; i < 51; i += 3) {
//            cards1.add(deck.get(i));
//            cards2.add(deck.get(i + 1));
//            cards3.add(deck.get(i + 2));
//        }
//
//        //剩余三个地主牌
//        List<Card> subList = deck.subList(51, 54); // 获取子列表
//        ArrayList<Card> bottomCards = new ArrayList<>(subList);
//
//        //洗牌
//        cardSorted(cards1);
//        cardSorted(cards2);
//        cardSorted(cards3);
//
//        //打印玩家手牌
//        System.out.println("player1's cards:");
//        for (Card card : cards1) {
//            System.out.println(card.getCardInfo() + " ");
//        }
//        System.out.println("player2's cards:");
//        for (Card card : cards2) {
//            System.out.println(card.getCardInfo() + " ");
//        }
//        System.out.println("player3's cards:");
//        for (Card card : cards3) {
//            System.out.println(card.getCardInfo() + " ");
//        }
//
////        players[0]=new Player(cards1);
////        players[1]=new Player(cards2);
////        players[2]=new Player(cards3);
//        players.get(0).setPlayerCard(cards1);
//        players.get(1).setPlayerCard(cards2);
//        players.get(2).setPlayerCard(cards3);
//        remain = bottomCards;
//        for (int i = 0; i < 3; i++) {
//            sendToOne(i, "游戏开始");
//        }
//        sendCardToOne(1,cards1);//给玩家发送手牌
//        sendCardToOne(2,cards2);//给玩家发送手牌
//        sendCardToOne(3,cards3);//给玩家发送手牌
//    }
//
//    //在分排结束后对卡组进行排序
//    void cardSorted(ArrayList<Card> deck){
//        deck.sort((p1, p2) -> {
//            int sizeComparison = Integer.compare(p1.getSize(), p2.getSize());
//            if (sizeComparison != 0) {
//                return sizeComparison;
//            } else {
//                return Integer.compare(p1.getSuit(), p2.getSuit());
//            }
//        });
//    }
//
//
//    /**
//     * 抢地主
//     */
//    public boolean finishLord() {
//
//        int noBark = 0;//都不叫,则重新发牌
//        init();//初始化抢地主点数
//        for (int i = 0; i < 3; i++) {
//            sendToOne(i, "抢地主");
//            String s = sendToOne(i, tostring(n));
//            // if(s.equals("3"))break;
//            players.get(i).setScore(Integer.parseInt(s));
//            if (s.equals("0")) {
//                noBark++;
//            }else {
//                n.remove(s);
//            }
//            sendToOne((i+1)%3, 'l'+s);//给右手玩家发送此玩家抢的点数
//            sendToOne((i+2)%3, 'r'+s);//给左手玩家发送此玩家抢的点数
//        }
//        if (noBark == 3){
//            System.out.println("重新洗牌");
//            return false;
//        }
//        //找出地主
//        for (int i = 1; i < 3; i++) {
//            if (players.get(i).getScore() > players.get(whoIsLord).getScore()) {
//                whoIsLord = i;//i是地主
//            }
//        }
////        for (int i = 0; i < 3; i++) {
////            if (i == whoIsLord) {
////                sendToOne(i, "you");
////                sendCardToOne(i, remain);
////            } else {
////                //===================================信号未确定
////                sendToOne(i, "" + whoIsLord );//给其他玩家说地主是谁和地主牌
////                sendCardToOne(i, remain);
////            }
////        }
//
//        //发送底牌
//        for (int i = 0; i < 3; i++) {
//            sendCardToOne(i, remain);
//        }
//        sendToOne(whoIsLord,"you");
//        //上一玩家是2，下一玩家是1
//        sendToOne((whoIsLord+1)%3,"l");//上一玩家(手）
//        sendToOne((whoIsLord+2)%3,"r");//下一玩家(右手)
//
//        return true;
//    }
//
//    /**
//     * 初始化抢地主积分
//     */
//    public void init(){//初始化抢地主点数
//        ArrayList<String> i = new ArrayList<>();
//        i.add("0");
//        i.add("1");
//        i.add("2");
//        i.add("3");
//        n = i;
//        whoIsLord = 0;
//    }
//   // public void finishLord(){
//
//    //}
//    //给所有人发消息
//    /**
//     * &#064;title:  sendToAll
//     * &#064;description:  给所有人发送信息
//     *
//     * @param  message String
//     */
//    public void sendToAll(String message) {
//        for (int i = 0; i < 3; i++) {
//            players.get(i).sendMsg(message);
//            players.get(i).receiveMsg();
//        }
//    }
//
//    /**
//
//     * &#064;title:  sendToAnotherTwo
//
//     * &#064;description:  给除了i的其他两个人发送消息
//     *
//     * @param message String  消息
//     *
//     * @param i int
//
//     */
//    //给除了i的其他两人发送消息
//    public void sendToAnotherTwo(int i, String message) {
//        for (int j = 0; j < 3; j++) {
//            if (j != i) {
//                players.get(j).sendMsg(message);
//                players.get(j).receiveMsg();
//            }
//        }
//    }
//
//    /**
//     * &#064;title:  sendToOne
//     * &#064;description:  给i发送消息
//     *
//     * @param message String  消息
//     *
//     * @param i int
//     */
//    //给i发消息
//    public String sendToOne(int i, String message) {
//        players.get(i).sendMsg(message);
//        return players.get(i).receiveMsg();
//    }
//    public String tostring(ArrayList<String> arrayList){
//        StringBuilder str = new StringBuilder();
//        for (String s : arrayList) {
//            str.append(s);
//        }
//        return str.toString();
//    }
//
//    /**
//     * 传输牌
//     */
//    public void sendCardToOne(int i, ArrayList<Card> cards) {
//        StringBuilder card_str = new StringBuilder();
////        for (String s : playerDeck){
////            str.append(s);
////        }
////        return str.toString();
//        for (Card card : cards) {
//            card_str.append(card.getCardInfo());
//        }
//        players.get(i).sendMsg(card_str.toString());
//    }
//
//    /**
//     * 接受牌
//     */
//    public ArrayList<Card> receiveCard(int i) {
//        ArrayList<Card>deck=new ArrayList<>();
//        String[] result = players.get(i).receiveMsg().split(" ");
//        for(int k=0;k<result.length;k+=2){
//            Card newCard=new Card(Integer.parseInt(result[i]),Integer.parseInt(result[i+1]));
//            deck.add(newCard);
//        }
//        return deck;
//    }
//
//    /**
//     * &#064;title:  sendCardToAnotherTwo
//
//     * &#064;description:  给除了i的其他两个人发送卡牌
//     *
//     * @param  i, ArrayList<Card> cards String  消息
//     *
//
//     */
////给除了i的其他两人发送卡牌
//    public void sendCardToAnotherTwo(int i, ArrayList<Card> cards) {
//        // 构建卡片信息的字符串
//        StringBuilder card_str = new StringBuilder();
//        for (Card card : cards) {
//            card_str.append(card.getCardInfo());
//        }
//
//        // 遍历所有玩家并发送消息，但排除当前玩家
//        for (int j = 0; j < 3; j++) {
//            if (j != i) {
//                players.get(i).sendMsg(card_str.toString());
//            }
//        }
//    }
//}
package com.example.fightthelandlord;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class server_gameRound {
    private ArrayList<server_player> players;//三个玩家
    private ArrayList<Card> remain;
    private ArrayList<String> n = new ArrayList<>();//抢地主的点数
    private int whoIsLord;//地主玩家序号
    private ArrayList<Card> allCards;

    /**
     * &#064;title:  gameRound
     * &#064;description:  构造函数,相关属性初始化
     * @param  players ArrayList<Player>
     */
    public server_gameRound(ArrayList<server_player> players) {
        this.players = players;
        //==================================
    }

    /**

     * &#064;title:  gameStart

     * &#064;description:  游戏发牌到出牌阶段

     */
    public void gameStart() {
        for(server_player player : players) {
            player.setInGame(true);//设置玩家在游戏内，回合结束直接开始准备阶段
        }
        do {
            //发牌
            dealCard();
            //==================================
            //抢地主
        }while(!finishLord());//抢地主，如果都不抢地主，则从新发牌
        //出牌循环
        int i =whoIsLord;//记录谁是地主
        while(true){
            //接收出的牌
            players.get(i).sendMsg("请你出牌");
            ArrayList<Card> r = receiveCard(i);
            for(Card card:r){
                players.get(i).removeCard(card);
            }

            System.out.println("第"+(i+1)+":");
            for (Card p: players.get(i).getPlayerCard()) {
                System.out.print(p.getCardInfo());
            }
            System.out.println();
            sendCardToAnotherTwo(i,r);//向玩家发送此玩家出的牌
            if(players.get(i).getPlayerCard().isEmpty()){
                System.out.println("游戏结束");//如果玩家出完牌了
                sendToAll("游戏结束");//提示游戏结束
                break;
            }
            i = ( i + 1 ) % 3;
        }
    }


    /**
     * 发牌
     */
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
            System.out.print(card.getCardInfo());
        }
        System.out.println();
        System.out.println("player2's cards:");
        for (Card card : cards2) {
            System.out.print(card.getCardInfo());
        }
        System.out.println();
        System.out.println("player3's cards:");
        for (Card card : cards3) {
            System.out.print(card.getCardInfo());
        }
        System.out.println();
//        players[0]=new Player(cards1);
//        players[1]=new Player(cards2);
//        players[2]=new Player(cards3);
        players.get(0).setPlayerCard(cards1);
        players.get(1).setPlayerCard(cards2);
        players.get(2).setPlayerCard(cards3);
        remain = bottomCards;
        cardSorted(remain);
//        for (int i = 0; i < 3; i++) {
//            sendToOne(i, "游戏开始");
//        }
        sendCardToOne(0,cards1);//给玩家发送手牌
        sendCardToOne(1,cards2);//给玩家发送手牌
        sendCardToOne(2,cards3);//给玩家发送手牌
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


    /**
     * 抢地主
     */
    public boolean finishLord() {

        int noBark = 0;//都不叫,则重新发牌
        init();//初始化抢地主点数
        boolean hasFind=false;
        for (int i = 0; i < 3; i++) {
            sendToOne(i, "抢地主");
            String s= players.get(i).receiveMsg();
            players.get(i).setScore(Integer.parseInt(s));
            System.out.println(i+1 +"号玩家抢了"+s+"分");

            if (s.equals("0")) {
                noBark++;
            }
            if(s.equals("3")){
                whoIsLord=i;
                sendToOne((i+1)%3,"l");//上一玩家(手）
                sendToOne((i+2)%3,"r");//下一玩家(右手)
                players.get(whoIsLord).addCard(remain);
                for (int j = 0; j < 3; j++) {
                    sendCardToOne(j, remain);
                }
                System.out.println("send Bottom Card OK");
                return true;
            }
            else{
                sendToOne((i+1)%3, 'l'+s);//给右手玩家发送此玩家抢的点数
                sendToOne((i+2)%3, 'r'+s);//给左手玩家发送此玩家抢的点数
            }
        }
        if (noBark == 3){
            System.out.println("重新洗牌");
            return false;
        }
        //找出地主
        for (int i = 0; i < 3; i++) {
            if (players.get(i).getScore() > players.get(whoIsLord).getScore()) {
                whoIsLord = i;//i是地主
            }
        }
        sendToOne(whoIsLord,"you");
        sendToOne((whoIsLord+1)%3,"l");//上一玩家(手）
        sendToOne((whoIsLord+2)%3,"r");
        players.get(whoIsLord).addCard(remain);
        for (int i = 0; i < 3; i++) {
            sendCardToOne(i, remain);
        }
        return true;
    }

    /**
     * 初始化抢地主积分
     */
    public void init(){//初始化抢地主点数
        ArrayList<String> i = new ArrayList<>();
        i.add("0");
        i.add("1");
        i.add("2");
        i.add("3");
        n = i;
        whoIsLord = 0;
    }
    //给所有人发消息
    /**
     * &#064;title:  sendToAll
     * &#064;description:  给所有人发送信息
     *
     * @param  message String
     */
    public void sendToAll(String message) {
        for (int i = 0; i < 3; i++) {
            players.get(i).sendMsg(message);
            players.get(i).receiveMsg();
        }
    }

    /**

     * &#064;title:  sendToAnotherTwo

     * &#064;description:  给除了i的其他两个人发送消息
     *
     * @param message String  消息
     *
     * @param i int

     */
    //给除了i的其他两人发送消息
    public void sendToAnotherTwo(int i, String message) {
        for (int j = 0; j < 3; j++) {
            if (j != i) {
                players.get(j).sendMsg(message);
                players.get(j).receiveMsg();
            }
        }
    }

    /**
     * &#064;title:  sendToOne
     * &#064;description:  给i发送消息
     *
     * @param message String  消息
     *
     * @param i int
     */
    //给i发消息
    public String sendToOne(int i, String message) {
        players.get(i).sendMsg(message);
        return players.get(i).receiveMsg();
    }
    public String tostring(ArrayList<String> arrayList){
        int n = arrayList.size();
        StringBuilder str = new StringBuilder();
        for (String s : arrayList) {
            str.append(s);
        }
        return str.toString();
    }

    /**
     * 传输牌
     */
    public void sendCardToOne(int i, ArrayList<Card> cards) {
        StringBuilder card_str = new StringBuilder();
//        for (String s : playerDeck){
//            str.append(s);
//        }
//        return str.toString();
        for (Card card : cards) {
            card_str.append(card.getSize()).append(" ").append(card.getSuit()).append(" ");
        }
        players.get(i).sendMsg(card_str.toString());
    }

    /**
     * 接受牌
     */
    public ArrayList<Card> receiveCard(int i) {
        ArrayList<Card>deck=new ArrayList<>();
        String s=players.get(i).receiveMsg();
        System.out.println("收到牌"+s);

        String[] result = s.split(" ");
        //非空处理，不加if报错
        if(result.length!=1) {
            for(int k=0;k<result.length;k+=2){
            Card newCard=new Card(Integer.parseInt(result[k]),Integer.parseInt(result[k+1]));
            deck.add(newCard);
        }}
        String msg="";
        for(Card card:deck)
            msg+=card.getCardInfo();
        System.out.println("已经收到上一玩家出牌"+msg);
        return deck;
    }

    /**
     * &#064;title:  sendCardToAnotherTwo

     * &#064;description:  给除了i的其他两个人发送卡牌
     *
     * @param  i, ArrayList<Card> cards String  消息
     *

     */
//给除了i的其他两人发送卡牌
    public void sendCardToAnotherTwo(int i, ArrayList<Card> cards) {
        // 构建卡片信息的字符串
        StringBuilder card_str = new StringBuilder("l ");
        for (Card card : cards) {
            card_str.append(card.getSize()).append(" ").append(card.getSuit()).append(" ");
        }
        players.get((i+1)%3).sendMsg(card_str.toString());
        card_str.replace(0,1,"r");
        players.get((i+2)%3).sendMsg(card_str.toString());
        // 遍历所有玩家并发送消息，但排除当前玩家
//        for (int j = 0; j < 3; j++) {
//            if (j != i) {
//                players.get(i).sendMsg(card_str.toString());
//            }
//        }
    }
}
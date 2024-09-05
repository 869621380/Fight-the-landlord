package com.example.fightthelandlord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

public class gamePage {
    //斗地主游戏界面
    gamePage(){
        player1=new Player();
        socket=new Socket();
        try {
            //String ip = "106.13.40.227";//服务器ip
            String ip = "127.0.0.1";//本地ip
            InetSocketAddress socketAddress = new InetSocketAddress(ip, 8888);
            this.socket.connect(socketAddress);
            System.out.println("接入成功");//前端可以忽视
            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("断开连接");//前端可以忽视
        }

    }

    //开始游戏
    public void gameStart() throws IOException {
        String startMsg=in.readUTF();
        System.out.println(startMsg);
        do{
            dealCard();
        }
        while (!snatchLandlord());
        if(whoIsLord==1){
            player1.receiveCard(receiveServeCard());
        }
        else if(whoIsLord==2)numOfPlayer3+=3;
        else numOfPlayer3+=3;
        playGame();
    }

    //开局发牌
    public void dealCard() throws IOException {
        numOfPlayer3=numOfPlayer2=17;
        player1.receiveCard(receiveServeCard());
    }

    //抢地主
    public boolean snatchLandlord(){

        return false;
    }

    //出牌阶段
    void playGame(){

    }

    //报单报双
    public int onlyOneOrTwo(Player player){
        int cardCount= player.getCardCount();
        if(cardCount==1||cardCount==2)
            return cardCount;
        return 0;
    }

    //接受客户端卡牌信息
    private ArrayList<Card> receiveServeCard() throws IOException {
        ArrayList<Card>deck=new ArrayList<>();
        String[] result = in.readUTF().split("");
        for(int i=0;i<result.length;i+=2){
            Card newCard=new Card(Integer.parseInt(result[i]),Integer.parseInt(result[i+1]));
            deck.add(newCard);
        }
        return deck;
    }

    private Player player1;
    private ArrayList<Card> bottomCards;
    private final Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private int numOfPlayer2;
    private int numOfPlayer3;
    private int whoIsLord;

}

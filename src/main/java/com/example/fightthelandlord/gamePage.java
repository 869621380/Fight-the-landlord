package com.example.fightthelandlord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

public class gamePage {
    //斗地主游戏界面
    public gamePage(){
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
    public void gameStart() throws IOException, InterruptedException {
        String startMsg=in.readUTF();
        System.out.println(startMsg);
        do{
            dealCard();
        }
        while (!snatchLandlord());
        receiveButtonCard();
    }

    //开局发牌
    public void dealCard() throws IOException {
        numOfPlayer3=numOfPlayer2=17;
        player1.receiveCard(receiveServeCard());
    }

    //抢地主
    public boolean snatchLandlord() throws IOException, InterruptedException{
        System.out.println("抢地主");
        //调用前端函数切换页面（未实现）
        int noBark_num = 0;//初始化不叫的人数，如果3个人不叫就重发牌
        for (int i = 0; i < 3; i++) {
            //调用前端定位窗口位置（未实现）
            serverMessage = receiveMsg();
            if (serverMessage.equals("抢地主")){
                //到本玩家抢地主的逻辑
                serverMessage = in.readUTF();
                String s = "";
                //-----------------这里要通过点击前端的按钮返回点数：String s = 函数值（未实现）
                if(s.equals("0")){
                    noBark_num++;
                }
                out.writeUTF(s);
                System.out.println("你抢了"+s+"分");
            }
            else {
                //不到本玩家抢地主的逻辑
                int score=Integer.parseInt(String.valueOf(serverMessage.charAt(1)));
                if(serverMessage.charAt(0)=='l'){
                    //展示左侧玩家分数
                }
                else{
                    //展示右侧玩家分数
                }
                //调用前端显示其他玩家的点数（未实现）
                if(score==0){
                    noBark_num++;
                }
            }
        }
        if(noBark_num == 3){
            //调用前端抢地主页面关闭，重新发牌（未实现）
            return false;
        }
//        serverMessage = receiveMsg();
//        whoIsLord = serverMessage;//本机接受谁是地主
//        if(serverMessage.equals("you")){//你是地主的逻辑
//            System.out.println("你是地主");//在前端显示自己是地主
//            isLord = true;
//        }
        serverMessage=receiveMsg();
        if(serverMessage.equals("you")){
            player1.setIdentity(1);
            whoIsLord=1;
        }
        else if(serverMessage.equals("l")){
            whoIsLord=3;
            player1.setIdentity(2);
        }
        else {
            whoIsLord=2;
            player1.setIdentity(2);
        }
        return true;
    }

    //接受服务器信息
    public String receiveMsg() throws IOException{
        out.writeUTF("receive message successfully");
        System.out.println("receive message successfully");
        return in.readUTF();
    }

    //出牌阶段
    public void playGame(){

    }

    //报单报双
    public int onlyOneOrTwo(Player player){
        int cardCount= player.getCardCount();
        if(cardCount==1||cardCount==2)
            return cardCount;
        return 0;
    }

    private void receiveButtonCard() throws IOException {
        bottomCards=receiveServeCard();
        //show bottomCard
        if(whoIsLord==1) {
            player1.receiveCard(bottomCards);
            numOfPlayer2+=3;numOfPlayer3+=3;
        }
        else if(whoIsLord==2)numOfPlayer2+=3;
        else numOfPlayer2+=3;
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
    private String serverMessage;

}

package com.example.fightthelandlord;

import com.example.fightthelandlord.Controllers.gameWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

import static javafx.application.Application.launch;

public class gamePage extends Application {

    //斗地主游戏界面
    public gamePage() throws IOException {
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
            connectRight=true;
        } catch (IOException e) {
            System.out.println("断开连接");//前端可以忽视
            connectRight=false;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /**
         * scene对象可获取，controller用于和前端界面交换数据
         */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameWindow.fxml"));
        //   加载 FXML 文件并返回根节点（Parent）。
        Parent root = fxmlLoader.load();
        //   通过 fxmlLoader.getController() 获取 GameWindowController 的实例。
        controller = fxmlLoader.getController();
        Scene scene = new Scene(root, 1200, 800);

        /* ******************************************* */

        /* 用于固定窗口大小，设置标题  */
        primaryStage.setTitle("gameWindow");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(scene.getWidth());
        primaryStage.setMaxWidth(scene.getWidth());
        primaryStage.setMinHeight(scene.getHeight());
        primaryStage.setMaxHeight(scene.getHeight());

        primaryStage.show();//  窗口输出


    }

    //开始游戏
    public void gameStart() throws IOException, InterruptedException {
        System.out.println("waiting to start.....");
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
        ArrayList<Card>deck=new ArrayList<>(receiveServeCard());
        player1.receiveCard(deck);
        controller.setHandCard(deck);
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
    //出牌阶段
    public void gameRound() throws IOException, InterruptedException {
        while(true){
            serverMessage = in.readUTF();
            if (serverMessage.equals("请你出牌")) {//轮到你出牌的阶段
                System.out.println("请你出牌");//阶段提示词
                ArrayList<Card> playedCard = new ArrayList<>();
                //playedCard = gamewindow.outcard();这里需要调用函数获取打出牌的链表
                sendCard(playedCard);
                for (Card c : playedCard) {
                    //player1.delectCard(c);
                }
                //--------------在这里前端重新打印牌（未完成）
                //--------------在桌面中间打印出的牌（未完成）
                //--------------将自己出的牌记录下来。。。。应该需要和别人出的牌有区别，
                // 这样可以检测到如果上一次出牌也是自己出的牌，不进行比大小，直接出牌（未完成）
                System.out.println("你打完牌了");
            } else if (serverMessage.equals("游戏结束")){
                out.writeUTF("over");//
                serverMessage = receiveMsg();

                if (serverMessage.equals(whoIsLord)){//地主赢了的逻辑
                    if (player1.getIdentity()==1) {//本机是地主
                        System.out.println("地主赢了");//前端
                        //--------------------调用前端函数打印地主赢了的场景（未完成）
                    } else {//本机是农民
                        System.out.println("农民输了");
                        //--------------------调用前端函数打印农民输了的场景（未完成）
                    }
                }else {//农民赢了的逻辑
                    if (player1.getIdentity()==1) {//本机是地主
                        System.out.println("地主输了");//前端
                        //--------------------调用前端函数打印地主输了的场景（未完成）
                    } else {//本机是农民
                        System.out.println("农民赢了");
                        //--------------------调用前端函数打印农民赢了的场景（未完成）
                    }
                }
                //------------------调用前端函数，把游戏界面关闭，回到准备界面（未完成）
                break;
            }else {//不是本机出牌的阶段
                //---------------------记录其他玩家打出的牌，方便到自己出牌时和别人出过的牌比大小（未完成）
                //----------------------------调用前端显示打出的牌（未完成）
                System.out.println("其他玩家已出牌");
                out.writeUTF("1");
            }
        }
    }
    //接受服务器信息
    public String receiveMsg() throws IOException{
        out.writeUTF("receive message successfully");
        System.out.println("receive message successfully");
        return in.readUTF();
    }

    public boolean getConnectStatus(){
        return connectRight;
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
    //向客户端发送卡牌信息
    public void sendCard(ArrayList<Card> cards) throws IOException {
        StringBuilder card_str = new StringBuilder();
//        for (String s : playerDeck){
//            str.append(s);
//        }
//        return str.toString();
        for (Card card : cards) {
            card_str.append(card.getCardInfo());
        }
        //players.get(i).sendMsg(card_str.toString());
        out.writeUTF(card_str.toString());
        System.out.println("你出了："+card_str.toString());
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
    private gameWindowController controller;
    private boolean connectRight;
}

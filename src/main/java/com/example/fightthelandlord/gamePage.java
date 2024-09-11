package com.example.fightthelandlord;

import com.example.fightthelandlord.Controllers.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
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

import com.example.fightthelandlord.Controllers.gameWindowController;
import static java.lang.Thread.sleep;

public class gamePage {
    //斗地主游戏界面
    waitingroom roomWindow;
    public gamePage() throws IOException {
        player1 = new Player();
        socket = new Socket();
        try {
//            String ip = "192.168.3.9";//服务器ip
            String ip = "127.0.0.1";//本地ip
            InetSocketAddress socketAddress = new InetSocketAddress(ip, 8888);
            this.socket.connect(socketAddress);
            System.out.println("接入成功");//前端可以忽视
            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
            connectRight = true;
        } catch (IOException e) {
            System.out.println("连接失败");//前端可以忽视
            connectRight = false;
        }
    }

    public void selectRoom() throws Exception {
        //选房逻辑

// 创建第二个后台任务
        Stage stage = new Stage();
        firstStart(stage);
//        firstController.setPage(this);
//        firstController.setStage(stage);
        Task<Void> task2 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    while (inGame) {
                        sleep(10);
                    }
                    if (!isInGame) {
                        System.out.println("大厅");
                        System.out.println("选择房间");
                        player1.setSelectRoom("-1");
                        // 与服务器交互并处理房间选择逻辑
                        serverMessage = in.readUTF();
                        System.out.println("房间信息："+serverMessage);
                        secondController.setStage(stage);
                        Platform.runLater(()->{
                            secondController.refresh(serverMessage);
                        });
                        out.writeUTF(player1.getSelectRoom());
                        while (true) {
                            serverMessage = in.readUTF(); // 服务器发过来的房间信息
                            if (serverMessage.equals("full")) {
                                System.out.println("房间人数已满");
                                player1.setSelectRoom("-1");
                            }
                            if (serverMessage.equals("ok")) {
                                out.writeUTF("1");
                                break;
                            }
                            storeRoomNum(serverMessage, secondController); // 储存大厅房间状态
                            out.writeUTF(player1.getSelectRoom());
                        }
                    }
                    if (gameReady()) {
                        break;
                    }
                }
                return null;
            }
            @Override
            protected void succeeded() {
                super.succeeded();
            }
        };
// 启动第二个后台任务
        new Thread(task2).start();
    }
    /**
     * @return 无
     * @title: storeRoomNum
     * @description: 储存房间玩家情况, 如果有变化则更新
     */
    public void storeRoomNum(String roomNum, GameLobbyControllers w) {
        if (!roomNum.equals(this.roomNum) && !roomNum.equals("full")) {
            System.out.println(roomNum);//前端,对接更新房间信息
            Platform.runLater(()->{
                w.refresh(roomNum);
            });

            this.roomNum = roomNum;
        }
    }
    //游戏准备
    public boolean gameReady() throws Exception {
        System.out.println("gameReady....");
        roomWindow = new waitingroom(1200,800);//---------------调用前端函数进行绘制房间准备阶段场景（未实现）
        roomWindow.show(player1);
        this.isInGame = false;
        player1.setState("unready");
        String message;
        while (true){
            message = this.in.readUTF();
            if(message.startsWith("ready?")){
                String[] s = message.split("\\?");
                roomWindow.refresh(s[1]);//------------------储存房间状态,并调用前端函数修改前端状态（未完成）
                out.writeUTF(player1.getState());
            }
            if (message.equals("start")) {
                out.writeUTF("start");
                System.out.println("start");
                roomWindow.close();//------------------关闭前端窗口（未完成）
                gameStart();
                return true;
            }
            if (message.equals("quit")) {
                out.writeUTF("quit");
                System.out.println("退出房间");
                roomWindow.close();
                //---------------------关闭调试窗口（未完成）
                return false;
            }
        }
    }
    //开始游戏
    public void gameStart() throws Exception {
        System.out.println("gameStarting....");
        Platform.runLater(() -> {
            try {
                start(new Stage());
                System.out.println("waiting to start.....");
                startBackgroundTask(in, out);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
//        System.out.println("waiting to start.....");
//        startBackgroundTask(in, out);
    }

    //开局发牌
    public void dealCard() throws IOException {
        player1.clearCard();
        numOfPlayer3 = numOfPlayer2 = 17;
        player1.clearCard();
        ArrayList<Card> deck = new ArrayList<>(receiveServeCard());
        player1.receiveCard(deck);
    }

    //抢地主
    public boolean snatchLandlord() throws IOException, InterruptedException {
        System.out.println("抢地主");
        //调用前端函数切换页面（未实现）
        int noBark_num = 0;//初始化不叫的人数，如果3个人不叫就重发牌
        for(int i=0;i<3;i++){
            serverMessage = receiveMsg();
            if (serverMessage.equals("抢地主")) {
                //到本玩家抢地主的逻辑
                Platform.runLater(() -> {
                    controller.setQiangButton();
                });
                //空跑会有bug
                while (!controller.isPlayed)
                    Thread.sleep(10);
                int s = controller.Point;
                Platform.runLater(() -> controller.isPlayed = false);
                //-----------------这里要通过点击前端的按钮返回点数：String s = 函数值（未实现）
                out.writeUTF(String.valueOf(s));
                System.out.println("你抢了" + s + "分");
                if (s == 0) {
                    noBark_num++;
                }
                //如果抢三分成为地主
                if (s == 3) {
                    whoIsLord = 1;
                    player1.setIdentity(1);
                    System.out.println("你成为地主");

                    Thread.sleep(2000);
                    System.out.println(111);
                    Platform.runLater(()->{controller.setLandlord('m');});
                    System.out.println(222);
                    return true;
                }
            } else {
                if (serverMessage.equals("l")) {
                    //右侧展示三分
                    Platform.runLater(() -> controller.setOtherPoint("l3"));

                    Thread.sleep(2000);
                    Platform.runLater(()->{controller.setLandlord('l');});
                    whoIsLord = 3;
                    player1.setIdentity(2);
                    System.out.println(whoIsLord);
                    return true;
                } else if (serverMessage.equals("r")) {
                    //右侧展示三分
                    Platform.runLater(() -> controller.setOtherPoint("r3"));

                    Thread.sleep(2000);
                    Platform.runLater(()->{controller.setLandlord('r');});
                    whoIsLord = 2;
                    player1.setIdentity(2);
                    System.out.println(whoIsLord);
                    return true;
                }
                //不到本玩家抢地主的逻辑
                int score = Integer.parseInt(String.valueOf(serverMessage.charAt(1)));
                Platform.runLater(() -> controller.setOtherPoint(serverMessage));
                //调用前端显示其他玩家的点数
                if (score == 0) {
                    noBark_num++;
                }
            }
        }

        if (noBark_num == 3) {
            Thread.sleep(2000);
            return false;
        }
        //抢地主且没有中间中断后的数据判断
        serverMessage = receiveMsg();
        if (serverMessage.equals("you")) {
            Platform.runLater(()->{ controller.setLandlord('m');});
            player1.setIdentity(1);
            whoIsLord = 1;
        } else if (serverMessage.equals("l")) {
            //左侧展示三分
            Platform.runLater(()->{ controller.setLandlord('l');});
            whoIsLord = 3;
            player1.setIdentity(2);
        } else {
            Platform.runLater(()->{ controller.setLandlord('r');});
            whoIsLord = 2;
            player1.setIdentity(2);
        }
        Thread.sleep(2000);
        System.out.println(whoIsLord);
        return true;
    }

    //游戏开始后后端数据处理
    private void startBackgroundTask(DataInputStream in, DataOutputStream out) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    String startMsg = in.readUTF();
                    out.writeUTF("ok");
                    System.out.println(startMsg);
                    do {
                        System.out.println("11111111111111111111111111");
                        dealCard();
                        Platform.runLater(() -> controller.setHandCard(player1.getDeck()));
                    } while (!snatchLandlord());

                    receiveButtonCard();

                    gameRound();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true); // 确保应用退出时线程自动结束
        thread.start();
    }

    /**
     * &#064;description:出牌阶段
     */
    public void gameRound() throws IOException, InterruptedException {
        Platform.runLater(()-> controller.setOtherRemain(numOfPlayer3,numOfPlayer2));
        System.out.println(numOfPlayer2+" "+numOfPlayer3);
        int num=0;

        while (true) {
            serverMessage = in.readUTF();
            if (serverMessage.equals("请你出牌")) {//轮到你出牌的阶段
                System.out.println("请你出牌");
                if(num==2){
                    Platform.runLater(()-> controller.setDeck(new Deck(0,-1,-1)));
                }
                num=0;
                Platform.runLater(() -> controller.OnTurn());
                while (!controller.isPlayed){
                    Thread.sleep(10);
                }
                ArrayList<Card> playedCard = controller.getPlayedCards();
                controller.isPlayed=false;

                sendCard(playedCard);
                //玩家移除手牌
                player1.removeCard(playedCard);
                //调试
                String msg="你出牌";
                for(Card card:playedCard)msg+=card.getCardInfo();
                System.out.println(msg);
            }
            else if (serverMessage.equals("游戏结束")) {
                System.out.println("游戏结束");
                out.writeUTF("over");//
                Thread.sleep(2000);
                if(whoIsLord==1){
                    if(player1.getCardCount()==0){
                        //1赢
                        Platform.runLater(()->{controller.gameOver(false,true,false);});
                    }
                    else {
                        //2，3赢
                        Platform.runLater(()->{controller.gameOver(true,false,true);});
                    }
                }
                else {
                    if(whoIsLord==2){
                        if(player1.getCardCount()==0){
                            //1,2赢
                            Platform.runLater(()->{controller.gameOver(false,true,true);});
                        }
                        else if(numOfPlayer2==0){
                            //2赢
                            Platform.runLater(()->{controller.gameOver(false,false,true);});
                        }
                        else {
                            //1,3赢
                            Platform.runLater(()->{controller.gameOver(true,true,false);});
                        }
                    }
                    else{
                        if(player1.getCardCount()==0){
                            //1,3赢
                            Platform.runLater(()->{controller.gameOver(true,true,false);});
                        }
                        else if(numOfPlayer2==0){
                            //1,2赢
                            Platform.runLater(()->{controller.gameOver(false,true,true);});
                        }
                        else {
                            //3赢
                            Platform.runLater(()->{controller.gameOver(true,false,false);});
                        }
                    }
                }
                return;
            }
            else {//不是本机出牌的阶段
                System.out.println(serverMessage);
                String[]cardInfo=serverMessage.split(" ");
                ArrayList<Card>cards=new ArrayList<>();

                for(int i=1;i<cardInfo.length;i+=2){
                    cards.add(new Card(Integer.parseInt(cardInfo[i]),Integer.parseInt(cardInfo[i+1])));
                }
                Platform.runLater(()->controller.setOtherPlayedCard(cardInfo[0].charAt(0),cards));
                if(cardInfo[0].charAt(0)=='l'){
                    numOfPlayer3-=cards.size();
                }else{
                    numOfPlayer2-=cards.size();
                }
                Platform.runLater(()-> controller.setOtherRemain(numOfPlayer3,numOfPlayer2));
                System.out.println(numOfPlayer2+" "+numOfPlayer3);
                Deck deck=new Deck(cards);
                //如果上一个人没有出牌,把上上个人的数据传给他
                if(deck.getLastType()==0) {
                    controller.setDeck(controller.getDeck());
                    num++;
                }
                else {
                    controller.setDeck(deck);
                    num=0;
                }
                //---------------------记录其他玩家打出的牌，方便到自己出牌时和别人出过的牌比大小（未完成）
                //----------------------------调用前端显示打出的牌（未完成）
                System.out.println("其他玩家已出牌");
            }
        }
    }

    //接受服务器信息
    public String receiveMsg() throws IOException {
        String msg = in.readUTF();
        System.out.println("receive message " + msg + " successfully");
        out.writeUTF("receive message successfully");
        return msg;
    }

    //获取连接状态
    public boolean getConnectStatus() {
        return connectRight;
    }

    //出牌阶段
    public void playGame() {

    }

    //报单报双
    public int onlyOneOrTwo(Player player) {
        int cardCount = player.getCardCount();
        if (cardCount == 1 || cardCount == 2)
            return cardCount;
        return 0;
    }

    private void receiveButtonCard() throws IOException {
        bottomCards = receiveServeCard();
        //show bottomCard
        Platform.runLater(() -> controller.setBottomCard(bottomCards));
        if (whoIsLord == 1) {
            player1.receiveCard(bottomCards);
            Platform.runLater(() -> controller.addBottomCard());
        } else if (whoIsLord == 2) numOfPlayer2 += 3;
        else numOfPlayer3 += 3;
        Platform.runLater(()->controller.showBottomCard());
        System.out.println("receive bottomCard Ok");
    }

    //接受客户端卡牌信息
    private ArrayList<Card> receiveServeCard() throws IOException {
        ArrayList<Card> deck = new ArrayList<>();
        String[] result = in.readUTF().split(" ");
        for (int i = 0; i < result.length; i += 2) {
            Card newCard = new Card(Integer.parseInt(result[i]), Integer.parseInt(result[i + 1]));
            System.out.print(newCard.getCardInfo());
            deck.add(newCard);
        }
        System.out.println();
        return deck;
    }

    //向客户端发送卡牌信息
    public void sendCard(ArrayList<Card> cards) throws IOException {
        StringBuilder card_str = new StringBuilder("");
        for (Card card : cards) {
            card_str.append(card.getSize()).append(" ").append(card.getSuit()+" ");
        }
        String cardStrTrimmed = card_str.toString().trim();
        out.writeUTF(cardStrTrimmed);

        System.out.println("你出了：" + card_str);
    }

    public void setSecondController(GameLobbyControllers controller) {
        this.secondController = controller;
    }

    public ArrayList<Card> getDeck() {
        return player1.getDeck();
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
    private boolean connectRight;
    public GameTitleControllers firstController;
    private GameLobbyControllers secondController;
    private gameWindowController controller;
    private boolean isInGame = false;
    private String state;
    public boolean inStartRoom = false;
    private String roomNum;//大厅里房间人数情况
    public Stage primaryStage;
    public boolean inGame = true;

    public void firstStart(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
        Parent root = fxmlLoader.load();
        LoginWindowControllers controller = fxmlLoader.getController();
        controller.page = this;
        controller.player = this.player1;
        // 传递 Stage
        controller.setLoginWindowStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Window");
        primaryStage.show();
    }

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
//        primaryStage.initStyle(StageStyle.UTILITY); // 这种样式可以去掉大部分窗口装饰
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);

        //  模拟发牌
        primaryStage.show();//  窗口输出
    }


}

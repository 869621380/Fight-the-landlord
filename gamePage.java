package com.example.fightthelandlord;

import com.example.fightthelandlord.Controllers.GameLobbyControllers;
import com.example.fightthelandlord.Controllers.GameTitleControllers;
import com.example.fightthelandlord.Controllers.gameWindowController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

import static java.lang.Thread.sleep;

public class gamePage {
    //斗地主游戏界面
    waitingroom roomWindow;

    public gamePage() throws IOException {//游戏连接
        player1 = new Player();
        socket = new Socket();
        try {
            //String ip = "106.13.40.227";//服务器ip
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

    public void setInStartWindow(boolean inStartWindow) {
        this.inStartRoom = inStartWindow;
    }
    //选房
    public void selectRoom() throws Exception {
        //选房逻辑

// 创建第二个后台任务
        Stage stage = new Stage();
        firstStart(stage);
        firstController.setPage(this);
        firstController.setStage(stage);
        Task<Void> task2 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    while (firstController.inStartRoom) {
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
        Stage stage = new Stage();
                start(stage);
        ;

        System.out.println("waiting to start.....");
        startBackgroundTask(in, out);

        // dealCard();
        // do {
        //    dealCard();
        // }while (!snatchLandlord());


    }

    //开局发牌
    public void dealCard() throws IOException {
        numOfPlayer3 = numOfPlayer2 = 17;
        ArrayList<Card> deck = new ArrayList<>(receiveServeCard());
        player1.receiveCard(deck);
    }

    //抢地主
    public boolean snatchLandlord() throws IOException, InterruptedException {
        System.out.println("抢地主");
        //调用前端函数切换页面（未实现）
        int noBark_num = 0;//初始化不叫的人数，如果3个人不叫就重发牌
        for (int i = 0; i < 3; i++) {
            serverMessage = receiveMsg();
            if (serverMessage.equals("抢地主")) {
                //到本玩家抢地主的逻辑
                Platform.runLater(() -> {
                    controller.OnTurn();

                });
                while (!controller.isPlayed) {
                    Thread.sleep(10);
                }
                int s = controller.Point;
                //-----------------这里要通过点击前端的按钮返回点数：String s = 函数值（未实现）
                if (s==0) {
                    noBark_num++;
                }
                out.writeUTF(String.valueOf(s));
                System.out.println("你抢了" + s + "分");
                if (s==3) return true;
            } else {
                //不到本玩家抢地主的逻辑
                int score = Integer.parseInt(String.valueOf(serverMessage.charAt(1)));
                if (serverMessage.charAt(0) == 'l') {
                    //展示左侧玩家分数
                } else {
                    //展示右侧玩家分数
                }
                //调用前端显示其他玩家的点数（未实现）
                if (score == 0) {
                    noBark_num++;
                } else if (score == 3)
                    return true;
            }
        }
        if (noBark_num == 3) {
            //调用前端抢地主页面关闭，重新发牌（未实现）
            return false;
        }
//        serverMessage = receiveMsg();
//        whoIsLord = serverMessage;//本机接受谁是地主
//        if(serverMessage.equals("you")){//你是地主的逻辑
//            System.out.println("你是地主");//在前端显示自己是地主
//            isLord = true;
//        }
        serverMessage = receiveMsg();
        if (serverMessage.equals("you")) {
            player1.setIdentity(1);
            whoIsLord = 1;
        } else if (serverMessage.equals("l")) {
            whoIsLord = 3;
            player1.setIdentity(2);
        } else {
            whoIsLord = 2;
            player1.setIdentity(2);
        }
        System.out.println(whoIsLord);

        return true;
    }

    private void startBackgroundTask(DataInputStream in, DataOutputStream out) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    String startMsg = in.readUTF();
                    out.writeUTF("ok");
                    System.out.println(startMsg);
                    do {
                        dealCard();
                        Platform.runLater(() -> {
                            controller.setHandCard(player1.getDeck());
                        });
                    } while (!snatchLandlord());
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

    //出牌阶段
    public void gameRound() throws IOException, InterruptedException {
        while (true) {
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
            } else if (serverMessage.equals("游戏结束")) {
                out.writeUTF("over");//
                serverMessage = receiveMsg();

                if (serverMessage.equals(whoIsLord)) {//地主赢了的逻辑
                    if (player1.getIdentity() == 1) {//本机是地主
                        System.out.println("地主赢了");//前端
                        //--------------------调用前端函数打印地主赢了的场景（未完成）
                    } else {//本机是农民
                        System.out.println("农民输了");
                        //--------------------调用前端函数打印农民输了的场景（未完成）
                    }
                } else {//农民赢了的逻辑
                    if (player1.getIdentity() == 1) {//本机是地主
                        System.out.println("地主输了");//前端
                        //--------------------调用前端函数打印地主输了的场景（未完成）
                    } else {//本机是农民
                        System.out.println("农民赢了");
                        //--------------------调用前端函数打印农民赢了的场景（未完成）
                    }
                }
                //------------------调用前端函数，把游戏界面关闭，回到准备界面（未完成）
                break;
            } else {//不是本机出牌的阶段
                //---------------------记录其他玩家打出的牌，方便到自己出牌时和别人出过的牌比大小（未完成）
                //----------------------------调用前端显示打出的牌（未完成）
                System.out.println("其他玩家已出牌");
                out.writeUTF("1");
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
        if (whoIsLord == 1) {
            player1.receiveCard(bottomCards);
        } else if (whoIsLord == 2) numOfPlayer2 += 3;
        else numOfPlayer2 += 3;
    }

    //接受客户端卡牌信息
    private ArrayList<Card> receiveServeCard() throws IOException {
        ArrayList<Card> deck = new ArrayList<>();
        String[] result = in.readUTF().split(" ");
        for (int i = 0; i < result.length; i += 2) {
            Card newCard = new Card(Integer.parseInt(result[i]), Integer.parseInt(result[i + 1]));
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
        System.out.println("你出了：" + card_str.toString());
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

        primaryStage.show();//  窗口输出
    }

    public void firstStart(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameTitle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        firstController = fxmlLoader.getController();
        primaryStage.setScene(scene);
        primaryStage.setTitle("GameTitle");
        primaryStage.show();
        firstController.setPlayer(player1);
    }

//    public void setSecondController(Stage primaryStage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameLobby.fxml"));//com/example/fightthelandlord/
//        Parent root = fxmlLoader.load();
//        Scene scene = new Scene(root);
//        secondController = fxmlLoader.getController();
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("GameLobby");
//        primaryStage.show();
//    }
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
    private gameWindowController controller;
    private GameTitleControllers firstController;
    private GameLobbyControllers secondController;
    private boolean isInGame;
    private String state;
    public boolean inStartRoom = false;
    private String roomNum;//大厅里房间人数情况

}

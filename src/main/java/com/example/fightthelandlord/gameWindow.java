package com.example.fightthelandlord;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.fightthelandlord.Controllers.gameWindowController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *       gameWindow仅用于前端自己调试，后端所需仅看标注段落
 */
public class gameWindow extends Application {

    ArrayList<Card> HandCards = new ArrayList<>();
    ArrayList<Card> BottomCards = new ArrayList<>();

    Scene scene;
    gameWindowController controller;

    private final Object lock = new Object();

    public static void main(String[] args) {
        launch(args);
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
        scene = new Scene(root, 1200, 800);

        /* ******************************************* */

        /* 用于固定窗口大小，设置标题  */
        primaryStage.setTitle("gameWindow");
        primaryStage.setScene(scene);
//        primaryStage.initStyle(StageStyle.UTILITY); // 这种样式可以去掉大部分窗口装饰
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);

        //  模拟发牌
        testDealCard();
        controller.setHandCard(HandCards);
        controller.setBottomCard(BottomCards);

        primaryStage.show();//  窗口输出

        //  额外新线程用于后端逻辑处理，与进行UI操作的JavaFX应用程序线程区分
        new Thread(() -> {
                try {
                    test();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }).start();
    }


    void test() throws InterruptedException {
        /*
         ************模拟进程******************
         * 因UI操作都需在JavaFX应用程序线程中进行
         * 所有UI操作都需写在Platform.runLater(() -> /具体操作/ )
         * 用以将UI操作转到JavaFX应用程序线程
         */
        System.out.println("模拟开始");
        // 模拟抢地主
        // 假设左手方抢一点
        Thread.sleep(2000);//  模拟抉择过程
        Platform.runLater(() -> controller.setOtherPoint("l2"));

        // 玩家回合开始
        Platform.runLater(() -> {
            controller.OnTurn();
            controller.setQiangButton();
        });
        // 设置抢点按钮
        // 等待isPlayed信号改变
        while (!controller.isPlayed){
            try{
                Thread.sleep(10);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        // 任务完成后重置状态
        controller.isPlayed = false;
        // 回合结束，按钮禁用
        Platform.runLater(() -> controller.setAllButtonDisable());
        Platform.runLater(() -> controller.clearButton());

        System.out.println("玩家抢点："+controller.Point);
        // 假设右手方不抢
        Platform.runLater(() -> controller.setOtherPoint("r0"));
        // 玩家固定获得地主
        Platform.runLater(() -> {
            controller.addBottomCard();
            controller.setPlayButton();
            // 刷新牌桌
            controller.refresh();
        });
        // 开始新回合
        System.out.println("Begin");
        Platform.runLater(() -> controller.OnTurn());
        // 等待isPlayed信号改变
        while (!controller.isPlayed){
            try{
                Thread.sleep(10);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        ArrayList<Card> cards = controller.getPlayedCards();
        Deck deck = controller.getDeck();
        System.out.println();
        Platform.runLater(() -> {
            controller.setOtherPlayedCard('r',cards);
            controller.setOtherPlayedCard('l',cards);
            controller.setDeck(deck);
        });
        System.out.println("again");
        Platform.runLater(() -> controller.OnTurn());

    }

    void testDealCard(){
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

        //分发手牌
        for (int i = 0; i < 51; i += 3) {
            HandCards.add(deck.get(i));
        }

        //剩余三个地主牌
        List<Card> subList = deck.subList(51, 54); // 获取子列表
        BottomCards = new ArrayList<>(subList);

        //洗牌
        cardSorted(HandCards);

    }

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
}

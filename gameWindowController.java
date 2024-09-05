package org.example.fight_the_landlord.Controllers;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.example.fight_the_landlord.Card;
import org.example.fight_the_landlord.Player;
import org.example.fight_the_landlord.gameWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class gameWindowController {


    private Player player;
    private ArrayList<Card> cards;


    //创建三个卡组
    ArrayList<Card> cards1 = new ArrayList<>();//  用作手牌
    ArrayList<Card> cards2 = new ArrayList<>();//  用作出牌
    ArrayList<Card> cards3 = new ArrayList<>();

    public org.example.fight_the_landlord.gameWindow gameWindow;

    public AnchorPane root;
    @FXML
    private ImageView userImage;
    @FXML
    private ImageView player1Image;
    @FXML
    private ImageView player2Image;
    @FXML
    private AnchorPane handCards;
    @FXML
    public AnchorPane playedCards;
    @FXML
    public AnchorPane playedCards1;
    @FXML
    public AnchorPane playedCards2;
    @FXML
    private Button passButton;
    @FXML
    private Button playButton;
    @FXML
    private ImageView bottomCard1;
    @FXML
    private ImageView bottomCard2;
    @FXML
    private ImageView bottomCard3;

    @FXML
    private void initialize() {
        // 设置背景图片
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResourceAsStream("/images/gameBackground.jpg")),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        root.setBackground(new Background(backgroundImage));

        // 设置默认图片
        userImage.setImage(new Image(getClass().getResourceAsStream("/images/defaultHeader.png")));
        player1Image.setImage(new Image(getClass().getResourceAsStream("/images/GG.png")));
        player2Image.setImage(new Image(getClass().getResourceAsStream("/images/MM.png")));
        bottomCard1.setImage(new Image(getClass().getResourceAsStream("/images/poker_back.png")));
        bottomCard2.setImage(new Image(getClass().getResourceAsStream("/images/poker_back.png")));
        bottomCard3.setImage(new Image(getClass().getResourceAsStream("/images/poker_back.png")));


        // 整理手牌
        dealCard();
        paintHandCard();
        Platform.runLater(() -> centerAnchorPane(handCards, root));


        // 调试其他玩家出牌


        // 按钮事件处理
        passButton.setOnAction(event -> System.out.println("不出"));
        playButton.setOnAction(event -> playCard());
    }

    public void setGameWindow(gameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    /**
     * &#064;description  用于水平居中手牌和自己打出的牌
     */
    private void centerAnchorPane(AnchorPane pane, AnchorPane parent) {

        double parentWidth = parent.getWidth();
        double paneWidth = pane.getWidth();

        //   在父容器中设置 AnchorPane 的位置
        double layoutX = (parentWidth - paneWidth) / 2;
        pane.setLayoutX(layoutX);
    }

     private void handelImageViewClick(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        int i = (int)(AnchorPane.getLeftAnchor(clickedImageView)/36.0);
        if(AnchorPane.getTopAnchor(clickedImageView) == 30.0) {
            // 牌上移
            AnchorPane.setTopAnchor(clickedImageView, 0.0);
            // 加入到将打出的牌
            cards2.add(cards1.get(i));
        } else {
            // 牌下移
            AnchorPane.setTopAnchor(clickedImageView, 30.0);
            // 从将打出的牌删去
            cards2.remove(cards1.get(i));
        }
    }

    private void playCard() {
        //  将要打的牌移除出手牌
        for(Card card : cards2) {
            cards1.remove(card);
        }
        //  打印出牌
        paintPlayCard();
        //  刷新手牌
        paintHandCard();
        // 小延迟,保证在 JavaFX 线程中完成所有布局计算后再执行居中操作
        PauseTransition pause = new PauseTransition(Duration.millis(10));
        pause.setOnFinished(event -> {
            centerAnchorPane(playedCards, root);
            centerAnchorPane(handCards, root);
        });
        pause.play();
    }

    void paintHandCard() {
        //  清除原有子节点，方便刷新
        handCards.getChildren().clear();
        int num = cards1.size();
        handCards.setPrefHeight(132);
        handCards.setPrefWidth(36 * (num+1));
        ImageView[] imageView = new ImageView[num];
        for(int i = 0; i < num; i++){
            int v = cards1.get(i).getSize();
            imageView[i] = new ImageView();
            imageView[i].setImage(new Image(getClass().getResourceAsStream("/images/" + (v+3) + ".jpg")));
        }
        for(int i = 0; i < num; i++){
            //  设置锚点
            AnchorPane.setTopAnchor(imageView[i],30.0);
            AnchorPane.setLeftAnchor(imageView[i],0.0+(36.0*i));
            // 加入到手牌容器
            handCards.getChildren().addAll(imageView[i]);
            // 设置点击处理事件
            imageView[i].setOnMouseClicked(this::handelImageViewClick);
        }
    }

    private void paintPlayCard() {
        //  清除原有子节点，方便刷新
        playedCards.getChildren().clear();
        int num = cards2.size();
        playedCards.setPrefHeight(102);
        playedCards.setPrefWidth(36 * (num+1));
        ImageView[] imageView = new ImageView[num];
        for(int i = 0; i < num; i++){
            int v = cards2.get(i).getSize();
            imageView[i] = new ImageView();
            imageView[i].setImage(new Image(getClass().getResourceAsStream("/images/" + (v+3) + ".jpg")));
        }
        for(int i = 0; i < num; i++){
            //  设置锚点
            AnchorPane.setTopAnchor(imageView[i],30.0);
            AnchorPane.setLeftAnchor(imageView[i],0.0+(36.0*i));
            // 加入到打出的牌容器
            playedCards.getChildren().addAll(imageView[i]);
        }

        //  打印完后清空打出的牌，方便后续接收
        cards2.clear();
    }

    void paint12PlayCard(){
        playedCards1.getChildren().clear();
        playedCards2.getChildren().clear();
        int num = cards2.size();
        int M = 5;
        if(num < M){
            playedCards.setPrefHeight(0.0+);
        }
        playedCards.setPrefHeight(0.0+);
        playedCards.setPrefWidth(36 * (num+1));
        ImageView[] imageView = new ImageView[num];
        for(int i = 0; i < num; i++){
            int v = cards2.get(i).getSize();
            imageView[i] = new ImageView();
            imageView[i].setImage(new Image(getClass().getResourceAsStream("/images/" + (v+3) + ".jpg")));
        }

    }

    //***************************************************
    //测试用
    //**************************************************
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


        //分发手牌
        for (int i = 0; i < 51; i += 3) {
            cards1.add(deck.get(i));
//            cards2.add(deck.get(i + 1));
            cards3.add(deck.get(i + 2));
        }

        //剩余三个地主牌
        List<Card> subList = deck.subList(51, 54); // 获取子列表
        ArrayList<Card> bottomCards = new ArrayList<>(subList);

        //洗牌
        cardSorted(cards1);
//        cardSorted(cards2);
        cardSorted(cards3);

        //打印玩家手牌
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

        player =new Player(cards1);
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

}

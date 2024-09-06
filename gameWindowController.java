package com.example.fightthelandlord.Controllers;

import com.example.fightthelandlord.Card;
import com.example.fightthelandlord.Deck;
import com.example.fightthelandlord.gameWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class gameWindowController {

    //   储存参数
    private Deck deck;
    public boolean isPlayed = false;
    private int NowPoint = 1;
    public int Point = -1;

    ArrayList<Card> bottomCards;

    int temType;
    int temSize;
    int temNumber;

    //创建三个卡组
    ArrayList<Card> HandCards = new ArrayList<>();//  用作手牌
    ArrayList<Card> PlayedCards = new ArrayList<>();//  用作出牌
    ArrayList<Card> OtherPlayedCards = new ArrayList<>();

    public com.example.fightthelandlord.gameWindow gameWindow;

    //  各按钮
    public ImageView passButton;
    public ImageView playButton;
    public ImageView qiangButton0;
    public ImageView qiangButton1;
    public ImageView qiangButton2;
    public ImageView qiangButton3;

    @FXML
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
    public HBox button;
    @FXML
    private ImageView bottomCard1;
    @FXML
    private ImageView bottomCard2;
    @FXML
    private ImageView bottomCard3;


    @FXML
    private void initialize() {
        deck = new Deck(0,-1,-1);
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
        userImage.setImage(new Image(getClass().getResourceAsStream("/images/Header.png")));
        player1Image.setImage(new Image(getClass().getResourceAsStream("/images/right.png")));
        player2Image.setImage(new Image(getClass().getResourceAsStream("/images/left.png")));
        bottomCard1.setImage(new Image(getClass().getResourceAsStream("/images/poker_back.png")));
        bottomCard2.setImage(new Image(getClass().getResourceAsStream("/images/poker_back.png")));
        bottomCard3.setImage(new Image(getClass().getResourceAsStream("/images/poker_back.png")));

        // 设置出牌和不出按钮备用
        passButton = new ImageView(new Image(getClass().getResourceAsStream("/images/Button/pass.png")));
        playButton = new ImageView(new Image(getClass().getResourceAsStream("/images/Button/play(false).png")));
        passButton.setOnMouseClicked(event -> Pass());
        playButton.setOnMouseClicked(event -> playCard());
        playButton.setDisable(true);

        // 设置抢地主按钮
        qiangButton0 = new ImageView(new Image(getClass().getResourceAsStream("/images/Button/0Point.png")));
        qiangButton1 = new ImageView(new Image(getClass().getResourceAsStream("/images/Button/1Point.png")));
        qiangButton2 = new ImageView(new Image(getClass().getResourceAsStream("/images/Button/2Point.png")));
        qiangButton3 = new ImageView(new Image(getClass().getResourceAsStream("/images/Button/3Point.png")));
        qiangButton0.setOnMouseClicked(event -> Qiang(0));
        qiangButton1.setOnMouseClicked(event -> Qiang(1));
        qiangButton2.setOnMouseClicked(event -> Qiang(2));
        qiangButton3.setOnMouseClicked(event -> Qiang(3));
        if(NowPoint >= 1){
            qiangButton1.setDisable(true);
            qiangButton1.setImage(new Image(getClass().getResourceAsStream("/images/Button/1Point(false).png")));
        }
        else if (NowPoint >= 2){
            qiangButton2.setDisable(true);
            qiangButton2.setImage(new Image(getClass().getResourceAsStream("/images/Button/2Point(false).png")));
        }
        else if(NowPoint >= 3){
            qiangButton3.setDisable(true);
            qiangButton3.setImage(new Image(getClass().getResourceAsStream("/images/Button/3Point(false).png")));
        }
        button.getChildren().addAll(qiangButton0,qiangButton1,qiangButton2,qiangButton3);

        // 打印手牌
        paintHandCard();
        Platform.runLater(() -> centerAnchorPane(handCards, root));

    }

    public void setAllButtonDisable(){
        for(javafx.scene.Node node : button.getChildren()){
            node.setDisable(true);
        }
    }

    private void Pass() {

    }

    private void Qiang(int i) {
        // 更改抢点数
        Point = i;

        // 更改按钮
        button.getChildren().clear();
        button.getChildren().addAll(passButton,playButton);
    }

    //            接口
    public void setHandCard(ArrayList<Card> cards) {
        HandCards.addAll(cards);
    }
    public void setBottomCard(ArrayList<Card> cards) {
        bottomCards.addAll(cards);
    }
    public ArrayList<Card> getButtonCard() {
        return deck.getDeck();
    }
    public void setPoint(int Point){
        this.Point = Point;
    }



    void refresh(){

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
            deck.add(HandCards.get(i));
            // 是否能出牌检测
            playButton.setDisable(!deck.check());
            if(deck.check()){
                playButton.setImage(new Image(getClass().getResourceAsStream("/images/Button/play.png")));
            }else {
                playButton.setImage(new Image(getClass().getResourceAsStream("/images/Button/play(false).png")));
            }
        } else {
            // 牌下移
            AnchorPane.setTopAnchor(clickedImageView, 30.0);
            // 从将打出的牌删去
            deck.delete(HandCards.get(i));
            // 是否能出牌检测
            playButton.setDisable(!deck.check());
            if(deck.check()){
                playButton.setImage(new Image(getClass().getResourceAsStream("/images/Button/play.png")));
            }else {
                playButton.setImage(new Image(getClass().getResourceAsStream("/images/Button/play(false).png")));
            }
        }
    }

    private void playCard() {
        PlayedCards = deck.getDeck();
        //  将要打的牌移除出手牌
        for(Card card : PlayedCards) {
            HandCards.remove(card);
        }
        //  打印出牌
        paintPlayCard();
        //  刷新手牌
        paintHandCard();
        //  重新计算所有布局。确保在进行布局调整之前，所有的布局都已经计算完成。
        root.layout();
        // 延迟执行,保证在 JavaFX 线程中完成所有布局计算后再执行居中操作
        Platform.runLater(() -> {
            centerAnchorPane(playedCards, root);
            centerAnchorPane(handCards, root);
        });
        isPlayed = true;
    }

    void paintHandCard() {
        //  清除原有子节点，方便刷新
        handCards.getChildren().clear();
        int num = HandCards.size();
        handCards.setPrefHeight(132);
        handCards.setPrefWidth(36 * (num+1));
        ImageView[] imageView = new ImageView[num];
        for(int i = 0; i < num; i++){
            int v = HandCards.get(i).getSize();
            int s = HandCards.get(i).getSuit();
            imageView[i] = new ImageView();
            imageView[i].setImage(new Image(getClass().getResourceAsStream("/images/Cards/" + s + (v+3) + ".png")));
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
        PlayedCards = deck.getDeck();
        int num = PlayedCards.size();
        playedCards.setPrefHeight(102);
        playedCards.setPrefWidth(36 * (num+1));
        ImageView[] imageView = new ImageView[num];
        for(int i = 0; i < num; i++){
            int v = PlayedCards.get(i).getSize();
            int s = PlayedCards.get(i).getSuit();
            imageView[i] = new ImageView();
            imageView[i].setImage(new Image(getClass().getResourceAsStream("/images/Cards/" + s + (v+3) + ".png")));
        }
        for(int i = 0; i < num; i++){
            //  设置锚点
            AnchorPane.setTopAnchor(imageView[i],30.0);
            AnchorPane.setLeftAnchor(imageView[i],0.0+(36.0*i));
            // 加入到打出的牌容器
            playedCards.getChildren().addAll(imageView[i]);
        }

        //  打印完后清空打出的牌，方便后续接收
        PlayedCards.clear();
        temType = deck.getDeckType();
        temSize = deck.getSize();
        temNumber = deck.getNumber();
        deck = new Deck(temType,temSize,temNumber);
        //重新禁用按钮
        playButton.setDisable(true);
    }

    void paint12PlayCard(AnchorPane anchorPane){
        anchorPane.getChildren().clear();
        int num = 10;
        anchorPane.setPrefWidth(72.0);
        anchorPane.setPrefHeight(68.0+34.0*num);
        ImageView[] imageView = new ImageView[num];
        for(int i = 0; i < num; i++){
            int v = OtherPlayedCards.get(i).getSize();
            int s = OtherPlayedCards.get(i).getSuit();
            imageView[i] = new ImageView();
            imageView[i].setImage(new Image(getClass().getResourceAsStream("/images/Cards/" + s + (v+3) + ".png")));
        }
        for(int i = 0; i < num; i++){
            //  设置锚点
            AnchorPane.setLeftAnchor(imageView[i],0.0);
            AnchorPane.setTopAnchor(imageView[i],0.0+(34.0*i));
            // 加入到打出的牌容器
            anchorPane.getChildren().addAll(imageView[i]);
        }
    }

}

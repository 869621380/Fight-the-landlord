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

/**
 *             通过controller实例调用
 * 公共属性：
 * isPlayed  public 是否出牌
 * Point     public 抢点数
 * 接口：
 * setHandCard 导入手牌
 * setBottomCard 导入底牌
 * showBottomCard 展示底牌
 * setOtherPoint 导入其他玩家的抢点数
 * setDeck 导入上一个人的牌型
 * setOtherPlayedCard(char,ArrayList<Card>) 导入其他玩家出的牌(导入后自动Paint）
 * setLandlord 导入谁是地主(char) 左l，右r，自己m
 * OnTurn 开始出牌阶段
 * setQiangButton 唤出抢点按钮
 * getPlayedCards  获取所出的牌
 * getDeck 获取所处牌的牌型
 * addBottomCard 获得底牌加入手牌（获取后自动Paint增加后手牌）
 * gameWin 提示游戏胜利
 * gameLose 提示游戏失败
 */

public class gameWindowController {

    //   储存参数
    private Deck deck;
    public boolean isPlayed = false;
    private int NowPoint = 0;
    public int Point = -1;
    AnchorPane OnPlayer = new AnchorPane();

    //创建四个卡组
    ArrayList<Card> HandCards = new ArrayList<>();//  用作手牌
    ArrayList<Card> PlayedCards = new ArrayList<>();//  用作出牌
    ArrayList<Card> OtherPlayedCards = new ArrayList<>();//  用作其他玩家出牌
    ArrayList<Card> BottomCards = new ArrayList<>();//   用作底牌

    //图片储存
    final Image[][] Cards = {
            {
                new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/03.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/04.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/05.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/06.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/07.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/08.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/09.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/010.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/011.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/012.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/013.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/014.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/015.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/016.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/017.png")),
            },
            {
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/13.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/14.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/15.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/16.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/17.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/18.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/19.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/110.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/111.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/112.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/113.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/114.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/115.png"))
            },
            {
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/23.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/24.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/25.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/26.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/27.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/28.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/29.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/210.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/211.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/212.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/213.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/214.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/215.png"))
            },
            {
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/33.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/34.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/35.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/36.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/37.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/38.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/39.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/310.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/311.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/312.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/313.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/314.png")),
                    new Image(getClass().getResourceAsStream("/GameWindowImages/Cards/315.png"))
            }
    };

    public com.example.fightthelandlord.gameWindow gameWindow;

    //  各按钮
    public ImageView passButton;
    public ImageView playButton;
    public ImageView qiangButton0;
    public ImageView qiangButton1;
    public ImageView qiangButton2;
    public ImageView qiangButton3;

    //  FXML中的各组件
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
    public ImageView landlord_l;
    @FXML
    public ImageView landlord_r;
    @FXML
    public ImageView landlord_m;


    @FXML
    private void initialize() {
        deck = new Deck(0,-1,-1);
        // 设置背景图片
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResourceAsStream("/GameWindowImages/gameBackground2.png")),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        root.setBackground(new Background(backgroundImage));

        // 设置默认图片
        userImage.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Header.png")));
        player1Image.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/right.png")));
        player2Image.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/left.png")));
        bottomCard1.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/poker_back.png")));
        bottomCard2.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/poker_back.png")));
        bottomCard3.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/poker_back.png")));

        // 设置出牌和不出按钮备用
        passButton = new ImageView(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/pass.png")));
        playButton = new ImageView(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/play(false).png")));
        passButton.setOnMouseClicked(event -> Pass());
        playButton.setOnMouseClicked(event -> playCard());
        playButton.setDisable(true);

        // 设置抢地主按钮
        qiangButton0 = new ImageView(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/0Point.png")));
        qiangButton1 = new ImageView(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/1Point.png")));
        qiangButton2 = new ImageView(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/2Point.png")));
        qiangButton3 = new ImageView(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/3Point.png")));
        qiangButton0.setOnMouseClicked(event -> Qiang(0));
        qiangButton1.setOnMouseClicked(event -> Qiang(1));
        qiangButton2.setOnMouseClicked(event -> Qiang(2));
        qiangButton3.setOnMouseClicked(event -> Qiang(3));

    }


    /**
     *       接口
     *
     */
    public void setHandCard(ArrayList<Card> cards) {
        HandCards.clear();
        HandCards.addAll(cards);
        // 打印手牌
        paintHandCard();
//        handCards.setLayoutX((root.getWidth() - handCards.getWidth()) / 2);
        Platform.runLater(() -> {
            root.layout();
            centerAnchorPane(handCards, root);
        });
        // 禁用手牌点击
        for(javafx.scene.Node node : handCards.getChildren()){
            node.setDisable(true);
        }
    }
    public void setBottomCard(ArrayList<Card> cards) {
        BottomCards.addAll(cards);
    }
    public void showBottomCard(){
        paintBottomCard();
        // 抢地主完毕，清空出牌窗口
        playedCards1.getChildren().clear();
        playedCards2.getChildren().clear();
        playedCards.getChildren().clear();
    }
    public ArrayList<Card> getPlayedCards() {
        ArrayList<Card> list = new ArrayList<>(PlayedCards);
        PlayedCards.clear();
        return list;
    }
    public void setOtherPoint(String point){
        if(point.charAt(0) == 'r'){
            NowPoint = point.charAt(1) - '0';
            ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("/GameWindowImages/Point/Point" + point.charAt(1) +".png")));
            playedCards1.getChildren().add(iv);
        }else {
            NowPoint = point.charAt(1) - '0';
            ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("/GameWindowImages/Point/Point" + point.charAt(1) +".png")));
            playedCards2.getChildren().add(iv);
        }
        if(NowPoint >= 1){
            qiangButton1.setDisable(true);
            qiangButton1.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/1Point(false).png")));
        }
        if (NowPoint >= 2){
            qiangButton2.setDisable(true);
            qiangButton2.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/2Point(false).png")));
        }
        if(NowPoint >= 3){
            qiangButton3.setDisable(true);
            qiangButton3.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/3Point(false).png")));
        }
    }
    public void setDeck(Deck deck) {
        this.deck = new Deck(deck.getDeckType(),deck.getSize(),deck.getNumber());
    }
    public Deck getDeck(){
        return deck;
    }
    public void addBottomCard() {
        HandCards.addAll(BottomCards);
        // 重新排序
        cardSorted(HandCards);
        // 打印手牌
        paintHandCard();
        Platform.runLater(() -> {
            root.layout();
            centerAnchorPane(handCards, root);
        });
    }
    public void setOtherPlayedCard(char who,ArrayList<Card> otherPlayedCards){
        OtherPlayedCards.addAll(otherPlayedCards);
        if(who == 'l'){
            OnPlayer = playedCards2;
        } else if (who == 'r') {
            OnPlayer = playedCards1;
        }
        paint12PlayCard();
    }
    public void OnTurn() {
        // 清空己方出牌窗口
        playedCards.getChildren().clear();
        // 更改按钮
        button.getChildren().addAll(passButton,playButton);
        // 第一个出禁用Pass
//        passButton.setDisable(false);
//        if(deck.getDeckType() == 0){
//            passButton.setDisable(true);
//            passButton.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/pass(false).png")));
//        }
        // 回合未开始，按钮禁用
        playButton.setDisable(true);
        // 即将出牌，启用手牌点击
        for(javafx.scene.Node node : handCards.getChildren()){
            node.setDisable(false);
        }
    }
    public void setQiangButton(){
        button.getChildren().addAll(qiangButton0,qiangButton1,qiangButton2,qiangButton3);
    }
    public void setLandlord(char who){
        if(who == 'l'){
            landlord_l.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Landlord.png")));
        } else if (who == 'r'){
            landlord_r.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Landlord.png")));
        } else if (who == 'm'){
            landlord_m.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Landlord.png")));
        }
    }
    public void gameOver(Boolean left,Boolean me,Boolean right){
        Image win = new Image(getClass().getResourceAsStream("/GameWindowImages/Win.png"));
        Image lose = new Image(getClass().getResourceAsStream("/GameWindowImages/Lose.png"));
        // 清空所有出牌窗口
        playedCards.getChildren().clear();
        playedCards2.getChildren().clear();
        playedCards1.getChildren().clear();
        // 根据情况打印胜利失败图片
        if(me) {
            playedCards.getChildren().add(new ImageView(win));
            playedCards.setPrefWidth(win.getWidth());
            playedCards.setPrefHeight(win.getHeight());
        }
        else{
            playedCards.getChildren().add(new ImageView(lose));
            playedCards.setPrefWidth(lose.getWidth());
            playedCards.setPrefHeight(lose.getHeight());
        }
        if(right)
            playedCards1.getChildren().add(new ImageView(win));
        else
            playedCards1.getChildren().add(new ImageView(lose));
        if(left){
            playedCards2.getChildren().add(new ImageView(win));
        }
        else{
            playedCards2.getChildren().add(new ImageView(lose));
        }
        root.layout();
        AnchorPane.setLeftAnchor(playedCards,(root.getWidth() - playedCards.getWidth()) / 2.0) ;
    }

    private void Pass() {
        deck.pass();
        //  打印出牌
        paintPlayCard();
        //  重新计算所有布局。确保在进行布局调整之前，所有的布局都已经计算完成。
        root.layout();
        // 延迟执行,保证在 JavaFX 线程中完成所有布局计算后再执行居中操作
        Platform.runLater(() -> {
            centerAnchorPane(playedCards, root);
            centerAnchorPane(handCards, root);
        });
        isPlayed = true;
        // 回合结束，禁用手牌点击
        for(javafx.scene.Node node : handCards.getChildren()){
            node.setDisable(true);
        }
        // 回合结束，清除按钮
        button.getChildren().clear();
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
        // 延迟执行,保证在 JavaFX 线程中完成所有布局计算后再执行居中操作
        Platform.runLater(() -> {
            //  重新计算所有布局。确保在进行布局调整之前，所有的布局都已经计算完成。
            root.layout();
            centerAnchorPane(playedCards, root);
            centerAnchorPane(handCards, root);
        });
        isPlayed = true;
        // 回合结束，禁用手牌点击
        for(javafx.scene.Node node : handCards.getChildren()){
            node.setDisable(true);
        }
        // 回合结束，清除按钮
        button.getChildren().clear();
    }

    private void Qiang(int i) {
        // 更改抢点数
        Point = i;
        // 显示自己抢点数
        ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("/GameWindowImages/Point/Point" + Point +".png")));
        playedCards.getChildren().add(iv);
        playedCards.setLayoutX((root.getWidth() - playedCards.getPrefWidth()) / 2);
        Platform.runLater(() -> {
            root.layout();
            centerAnchorPane(handCards, root);
        });

        // 清除抢点按钮
        button.getChildren().clear();
        isPlayed = true;
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
            boolean able = deck.check();
            playButton.setDisable(!able);
            if(able){
                playButton.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/play.png")));
            }else {
                playButton.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/play(false).png")));
            }
        } else {
            // 牌下移
            AnchorPane.setTopAnchor(clickedImageView, 30.0);
            // 从将打出的牌删去
            deck.delete(HandCards.get(i));
            // 是否能出牌检测
            boolean able = deck.check();
            playButton.setDisable(!able);
            if(able){
                playButton.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/play.png")));
            }else {
                playButton.setImage(new Image(getClass().getResourceAsStream("/GameWindowImages/Button/play(false).png")));
            }
        }
    }

    /**
     *     打印牌
     */
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
            imageView[i].setImage(Cards[s][v]);
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

        if(PlayedCards.isEmpty()){
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/GameWindowImages/OtherPass.png")));
            playedCards.getChildren().add(imageView);
        }else {
            PlayedCards = deck.getDeck();
            int num = PlayedCards.size();
            playedCards.setPrefHeight(102);
            playedCards.setPrefWidth(36 * (num+1));
            ImageView[] imageView = new ImageView[num];
            for(int i = 0; i < num; i++){
                int v = PlayedCards.get(i).getSize();
                int s = PlayedCards.get(i).getSuit();
                imageView[i] = new ImageView();
                imageView[i].setImage(Cards[s][v]);
            }
            for(int i = 0; i < num; i++){
                //  设置锚点
                AnchorPane.setTopAnchor(imageView[i],30.0);
                AnchorPane.setLeftAnchor(imageView[i],0.0+(36.0*i));
                // 加入到打出的牌容器
                playedCards.getChildren().addAll(imageView[i]);
            }
        }

        //重新禁用按钮
        playButton.setDisable(true);
    }

    void paint12PlayCard(){
        //  清除原有子节点，方便刷新
        OnPlayer.getChildren().clear();

        if(OtherPlayedCards.isEmpty()){
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/GameWindowImages/OtherPass.png")));
            OnPlayer.getChildren().add(imageView);
        }else{
            int num = OtherPlayedCards.size();
            OnPlayer.setPrefWidth(72.0);
            OnPlayer.setPrefHeight(68.0+34.0*num);
            ImageView[] imageView = new ImageView[num];
            for(int i = 0; i < num; i++){
                int v = OtherPlayedCards.get(i).getSize();
                int s = OtherPlayedCards.get(i).getSuit();
                imageView[i] = new ImageView();
                imageView[i].setImage(Cards[s][v]);
            }
            for(int i = 0; i < num; i++){
                //  设置锚点
                AnchorPane.setLeftAnchor(imageView[i],0.0);
                AnchorPane.setTopAnchor(imageView[i],0.0+(34.0*i));
                // 加入到打出的牌容器
                OnPlayer.getChildren().addAll(imageView[i]);
            }
        }

        //  打印完后清空其他玩家打出的牌，方便后续接收
        OtherPlayedCards.clear();
    }

    private void paintBottomCard() {
        int v = BottomCards.get(0).getSize();
        int s = BottomCards.get(0).getSuit();
        bottomCard1.setImage(Cards[s][v]);
        v = BottomCards.get(1).getSize();
        s = BottomCards.get(1).getSuit();
        bottomCard2.setImage(Cards[s][v]);
        v = BottomCards.get(2).getSize();
        s = BottomCards.get(2).getSuit();
        bottomCard3.setImage(Cards[s][v]);
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


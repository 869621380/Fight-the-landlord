package com.example.fightthelandlord;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
public class server_player {
    private ArrayList<Card> playerCard;//玩家的牌
    private DataInputStream in;//玩家输入流
    private DataOutputStream out;//玩家输出流
    private int score;//玩家抢地主的点数
    private boolean isInGame;//玩家是否在游戏
    private server_room room;//玩家在的房间

    public server_player(Socket socket) {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        }catch (IOException e) {
            e.printStackTrace();
        }
        //-------------------------------------------应该是false，测试用true
        this.isInGame = false;
    }

    public server_room getRoom() {
        return room;
    }

    public void setRoom(server_room room) {this.room = room;}

    public boolean isInGame() {return isInGame;}

    public void setInGame(boolean isInGame) {this.isInGame = isInGame;}

    /**

     * @title: sendMsg

     * @description: 给玩家发送消息
     *
     * @param  s String

     * @return 无

     */
    public void sendMsg(String s){
        try {
            this.out.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @title: receiveMsg
     * @description: 接受玩家发送的消息
     *
     * @return String 玩家发送的消息
     */
    public String receiveMsg(){
        try {
            return this.in.readUTF();
        } catch (IOException e) {
            System.out.println("玩家离开");
        }
        return null;
    }

    public DataInputStream getIn(){
        return in;
    }

    public DataOutputStream getOut(){
        return out;
    }

    /**
     * @title: outputPlayerDeck
     * @description: 格式化输出玩家卡牌信息
     *
     * @return String 玩家的卡牌信息
     */
    //============================================================未确定
//    public String outputPlayerDeck(){
//        StringBuilder str = new StringBuilder();
//        for (Card s : playerDeck){
//            str.append(s);
//        }
//        return str.toString();
//    }
    public void removeCard(Card card){
        playerCard.remove(card);
    }
    public ArrayList<Card> getPlayerCard() {return playerCard;}

    public int getScore() {return score;}

    public void setScore(int score) {this.score = score;}

    public void setPlayerCard(ArrayList<Card> playerCard) {
        this.playerCard = playerCard;
    }

    public void addCard(ArrayList<Card> card){
        playerCard.addAll(card);
    }
}
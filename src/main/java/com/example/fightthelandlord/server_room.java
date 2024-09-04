package com.example.fightthelandlord;
import java.util.ArrayList;
public class server_room {
    private ArrayList<server_player> players = new ArrayList<>();//在房间里的玩家
    private int num;//人数
    private boolean start;//开始游戏信号
    private ArrayList<server_player> readyPlayer = new ArrayList<>();//已经准备了的玩家
    static int n = 3;

    public void decrease() {
        synchronized (this) {
            n--;
        }
    }

    public server_room() {
        this.num = 0;
        this.start = false;
    }

    /**
     * @title: AssPlayer
     * @description: 添加加入房间的玩家
     *
     */
    public void addPlayer(server_player player) {//加入玩家
        players.add(player);
        num++;
    }

    /**

     * @title: addReady

     * @description: 加入已经准备的玩家
     *
     * @param player Player

     */
    public void addReady(server_player player) {//加入准备玩家
        if (!readyPlayer.contains(player)) {
            readyPlayer.add(player);
            System.out.println("ready");
        }
        if (readyPlayer.size() == 3) {
            this.start = true;//三人都准备则开始信号置true
        }
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public int getNum() {
        return num;
    }

    public ArrayList<server_player> getPlayers() {
        return players;
    }

    public boolean isStart() {
        return start;
    }
}

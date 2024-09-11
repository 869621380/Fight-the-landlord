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
     * @title: AddPlayer
     * @description: 添加加入房间的玩家
     *
     */
    public void addPlayer(server_player player) {//加入玩家
        players.add(player);
        num++;
    }

    //移除玩家
    public void removePlayer(server_player player) {
        players.remove(player);//删除房间里的玩家
        readyPlayer.remove(player);//准备中的玩家点击退出按钮后直接退出
        num--;
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

    //取消准备玩家
    public void removeReady(server_player player) {
        if (readyPlayer.contains(player)) {
            readyPlayer.remove(player);
            System.out.println("removed ready");
        }
    }

    public String roomInfo(server_player player) {
        if (this.num == 1) {
            return "00";
        } else if (this.num == 2) {
            String i = "10";
            for (server_player p : players) {
                if (!p.equals(player) && readyPlayer.contains(p)) {
                    i = "20";
                    break; // 如果找到一个准备好的玩家，直接返回"20"
                }
            }
            return i;
        } else {
            int n = 0;
            StringBuilder i = new StringBuilder("11");
            for (server_player p : players) {
                if (!p.equals(player) && readyPlayer.contains(p)) {
                    i.setCharAt(n++,'2');
                }
            }
            return i.toString();
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

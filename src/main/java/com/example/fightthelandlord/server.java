
package com.example.fightthelandlord;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
public class server {
    public static server_room[] rooms = new server_room[5];//创建房间
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);//服务窗口
        Socket client;
        //开多房间时再用
        for (int i = 0; i < 5; i++) {
            rooms[i] = new server_room();
        }
        while (true) {
            System.out.println("Waiting for connection...");
            client = serverSocket.accept();//等待玩家
            System.out.println("New client connected");
            new playerMsg(client).start();
        }
    }
}

/**
 * &#064;title:playerMsg
 * &#064;description:玩家和服务器交流的多线程，用于服务器与玩家交流
 */
class playerMsg extends Thread {
    server_player player;//一个玩家
    server_room[] rooms;//所有房间
    server_room room;//选择的房间

    public playerMsg(Socket client) {
        this.rooms = server.rooms;
        this.player = new server_player(client);
    }

    public playerMsg(server_player player, server_room room) {
        this.player = player;
        this.rooms = server.rooms;
        this.room = room;
    }

    /**
     * &#064;title:  run
     * <p>
     * &#064;description:  玩家与服务器交流的多线程
     */
    public void run() {
        try {
            do {
                System.out.println("等待其他玩家");
                if (!player.isInGame())
                    //选择房间
                    this.room = selectRoom();
            } while (!gameReady(room));

            this.room.decrease();
            if (server_room.n == 0) {//一人的线程启动游戏线程
                System.out.println("111111111111111111111111111");
                server_room.n = 3;
                new GameThread(room.getPlayers()).start();//start game
            }
            System.out.println("Start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //选择房间
    public server_room selectRoom() throws IOException {
        String message;
        System.out.println("selecting room...");
        while (true) {
            System.out.println(getRoomNum());
            player.sendMsg(getRoomNum());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            message = player.receiveMsg();
            if (!message.equals("-1")) {
                int choice = Integer.parseInt(message);
                if (rooms[choice].getNum() >= 3) {
                    player.sendMsg("full");//表示此房间人数已满
                    player.receiveMsg();
                } else {
                    player.sendMsg("ok");//表示可以进入此房间
                    player.receiveMsg();
                    rooms[choice].addPlayer(player);//房间记录玩家
                    player.setRoom(rooms[choice]);//记录玩家的房间
                    return rooms[choice];
                }
            }
        }
    }
    //获取所有房间人数信息
    public String getRoomNum(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            str.append(rooms[i].getNum());
        }
        return str.toString();
    }

    //进入房间后准备
    public boolean gameReady(server_room room) {
        String message;//玩家信息
        room.setStart(false);
        player.setInGame(false);//-----------调试用,应该为false

        while (true) {//与客户端交互，询问状态
            if (room.isStart()) {//房间开始
                System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
                player.sendMsg("start");//提示玩家开始游戏
                player.receiveMsg();
                player.sendMsg("start...");
                player.receiveMsg();
                return true;
            } else {
                player.sendMsg("ready?" + room.roomInfo(player));
                System.out.println("ready?" + room.roomInfo(player));
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            message = player.receiveMsg();
            if (message.equals("unready")) {
                room.removeReady(player);//设置房间的准备玩家的list
                System.out.println("unready");
            }
            if (message.equals("ready")) {
                room.addReady(player);
                System.out.println("ready");
            }
            if (message.equals("quit")) {
                player.sendMsg("quit");
                player.receiveMsg();
                room.removePlayer(player);//房间移除玩家
                player.setRoom(null);//玩家移除房间
                System.out.println("quit");
                return false;//重新进入大厅阶段,选择房间
            }
        }
    }

}

//多线程
class GameThread extends Thread {
    static int homeNum = 0;
    ArrayList<server_player> players;

    public GameThread(ArrayList<server_player> players) {
        this.players = players;
        homeNum += 1;
        System.out.println("第" + homeNum + "局游戏开始");
    }

    public void run() {
        server_gameRound game = new server_gameRound(players);
        System.out.println("222222222222222222222222222222222");
        game.gameStart();//开始一轮游戏
        for (server_player player: players){//游戏结束后,重新开三个玩家交流线程,依靠isInGame的flag跳过选择房间阶段,
            new playerMsg(player,player.getRoom()).start();//直接在房间的准备阶段
        }
    }
}



//package com.example.fightthelandlord;
//import java.io.IOException;
//import java.net.*;
//import java.util.ArrayList;
//public class server {
//    public static server_room[] rooms = new server_room[5];//创建房间
//    //public static server_room room = new server_room();
//    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = new ServerSocket(8888);//服务窗口
//        Socket client;
//        //开多房间时再用
//        for (int i = 0; i < 5; i++) {
//            rooms[i] = new server_room();
//        }
//        while (true) {
//            System.out.println("Waiting for connection...");
//            client = serverSocket.accept();//等待玩家
//            System.out.println("New client connected");
//            new playerMsg(client).start();
//        }
//    }
//}
//
///**
// * &#064;title:playerMsg
// * &#064;description:玩家和服务器交流的多线程，用于服务器与玩家交流
// */
//class playerMsg extends Thread {
//    server_player player;//一个玩家
//    server_room[] rooms;//所有房间
//    server_room room;//选择的房间
//
//    public playerMsg(Socket client) {
//
//        this.player = new server_player(client);
//    }
//    public playerMsg(server_player player, server_room room) {
//        this.player = player;
//        this.room = room;
//    }
//    /**
//
//     * &#064;title:  run
//
//     * &#064;description:  玩家与服务器交流的多线程
//     *
//
//     */
//    public void run() {
//        try {
//            //选择房间，未完成
//            this.room = rooms[0];//==========测试，记得调整
//            //
//            if (server_room.n == 0){//一人的线程启动游戏线程
//                server_room.n = 3;
//                new GameThread(room.getPlayers()).start();//start game
//            }
//            System.out.println("Start");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    //选择房间
////    public server_room selectRoom() throws IOException {
////        return rooms;
////    }
//    //进入房间后准备
//    public boolean gameReady()
//    {
//        return true;
//    }
//    //获取房间人数
//
//}
////多线程
//class GameThread extends Thread {
//    static int homeNum = 0;
//    ArrayList<server_player> players;
//
//    public GameThread(ArrayList<server_player> players) {
//        this.players = players;
//        homeNum += 1;
//        System.out.println("第" + homeNum + "局游戏开始");
//    }
//
//    public void run() {
//        server_gameRound game = new server_gameRound(players);
//        game.gameStart();//开始一轮游戏
//        for (server_player player: players){//游戏结束后,重新开三个玩家交流线程,依靠isInGame的flag跳过选择房间阶段,
//            new playerMsg(player,player.getRoom()).start();//直接在房间的准备阶段
//        }
//    }
//}
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

     * &#064;description:  玩家与服务器交流的多线程
     *

     */
    public void run() {
        try {
            //选择房间，未完成
            //rooms[0].addPlayer(player);//==========测试，记得调整
            //player.setRoom(rooms[0]);
            this.room = rooms[0];//==========测试，记得调整
            do {
                System.out.println("等待其他玩家");
                if (!player.isInGame())
                    //选择房间
                    this.room = selectRoom();
            }while (!gameReady(room));

            this.room.decrease();
            if (server_room.n == 0){//一人的线程启动游戏线程
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
        rooms[0].addPlayer(player);
        player.setRoom(rooms[0]);
        return rooms[0];
    }
    //进入房间后准备
    public boolean gameReady(server_room room)
    {
//        room.addPlayer(player);
//        while (true){
//            if(room.isStart()){
//                player.sendMsg("start");
//                player.receiveMsg();
//                return true;
//            }
//
//        }
        //room.addPlayer(player);
        while(true){
            if(room.getNum() == 3)
            {
                System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
                player.sendMsg("start");//提示玩家开始游戏
                player.receiveMsg();
                return true;
            }
            try{
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    //获取房间人数

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


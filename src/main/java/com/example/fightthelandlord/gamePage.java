package com.example.fightthelandlord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class gamePage {
    //斗地主游戏界面
    gamePage(){

    }
    //开始游戏
    void gameStart(){

    }
    //抢地主
    public void snatchLandlord(){

    }

    //出牌阶段


    //报单报双
    public int onlyOneOrTwo(Player player){
        int cardCount= player.getCardCount();
        if(cardCount==1||cardCount==2)
            return cardCount;
        return 0;
    }

    //胜利检验
    public boolean checkWin(){
        for(int i=0;i<3;i++){
            if(players[i].getCardCount()==0)
                return true;
        }
        return false;
    }

    private Player[]players;
    private ArrayList<Card> bottomCards;

}

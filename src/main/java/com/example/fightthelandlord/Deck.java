package com.example.fightthelandlord;
import java.util.TreeSet;
/*
0 首出  all
1 单张  1
2 双张  2
3 三张  3
4 三带一   4
5 三带二   5
6 顺子     5-11
7 连对     6 8 10 12 14 16
8 三三     6
9 三三带一一 8
10 三三带二二 10
11 三三三带一一一 12
12 三三三带二二二 15
13 三三三 9
14 三三三三带一一一一 14
15 三三三三 12
16 四带一 5
17 四带二 6
18 炸弹 4
19 王炸 2
*/
public class Deck {
    //传入上一出牌人出牌信息
    public Deck(int lastType,int lastSize,int lastNumber){
        deck=new TreeSet<>();
        this.lastType=lastType;
        this.lastSize=lastSize;
        this.lastNumber=lastNumber;
    }
    //更新当前可出牌参数
    void updateDate(int deckType,int Size,int number){
        this.deckType=deckType;
        this.size=size;
        this.number=number;
    }
    //检验选中卡牌能否出牌
    public boolean check(){
        //如果自己是首出牌
        if(lastType==0&& !deck.isEmpty())
            return true;
        //炸弹判断

        //如果不是炸弹只有手牌数目一样才能打出
        if(lastNumber!= deck.size())
            return false;
        //type检验
        switch (lastType){

            case 1:{
                if(size>lastSize){
                    updateDate(1,deck.first().getSize(),1);
                    return true;
                }
                return false;
            }

            case 2:{
                int firstSize=deck.first().getSize();
                int secondSize=deck.higher(deck.first()).getSize();
                if((firstSize==secondSize)&&firstSize>lastSize) {
                    updateDate(2,firstSize,2);
                    return true;
                }
                return false;
            }

            case 3:{

            }
            case 4:{

            }
            case 5:{

            }
            case 6:{

            }
            case 7:{

            }
            case 8:{

            }
            case 9:{

            }
            case 10:{

            }
            case 11:{
                

            }
            case 12:{

            }
            case 13:{

            }
            case 14:{

            }
            case 15:{

            }
            case 16:{

            }
            case 17:{

            }
            case 18:{

            }
            case 19:{

            }
        }
        return false;
    }

    //添加新卡牌
    public void add(Card card){
      deck.add(card);
    }

    //删除卡牌
    public void delete(Card card){
        deck.remove(card);
    }

    //显示出牌元素
    private  TreeSet<Card>deck;
    private final int lastType;
    private final int lastSize;
    private final int lastNumber;
    private int deckType;
    private int size;
    private int number;
}

package com.example.fightthelandlord;
import java.util.Objects;
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
    void updateDate(int deckType,int size,int number){
        this.deckType=deckType;
        this.size=size;
        this.number=number;
    }
    //检验选中卡牌能否出牌
    public boolean check(){
        //如果自己是首出牌进行首牌合理性检验
        if(lastType==0&& !deck.isEmpty())
            return reasonablenessTest();
        //如果上一个人是王炸无论如何都返回false
        if(lastType==19)
            return false;
        //炸弹判断
        if(deck.size()==4&&deck.first().getSize()==deck.last().getSize()){
            if(lastType==18){
                if(deck.first().getSize()>lastSize){
                    updateDate(18,deck.first().getSize(),4);
                    return true;
                }
                else return false;
            }
            else{
                updateDate(18,deck.first().getSize(),4);
                return true;
            }
        }
        //王炸判断
        if(deck.size()==2&&deck.first().getSize()==13){
            updateDate(19,0,2);
            return true;
        }
        //如果不是炸弹只有手牌数目一样才能打出
        if(lastNumber!= deck.size())
            return false;
        //type检验
        return typeCheck();
    }

    //添加新卡牌
    public void add(Card card){
      deck.add(card);
    }

    //删除卡牌
    public void delete(Card card){
        deck.remove(card);
    }

    //出牌后返回卡组类型
    public int getDeckType(){
        return deckType;
    }

    //出牌后返回卡组大小
    public int getSize(){
        return size;
    }

    //出牌后返回卡组卡牌数目
    public int getNumber(){
        return number;
    }
    //首发选中牌面合理性检验
    private boolean reasonablenessTest(){
        int []cardSize=new int[15];
        for(Card card:deck){
            cardSize[card.getSize()]++;
        }
        int numOfOne=0,numOfTwo=0,numOfTree=0,numOfFour=0;

        return false;
    }
    //根据类型检验能否压过上一张牌
    private boolean typeCheck(){
        int []cardSize=new int[15];
        for(Card card:deck){
            cardSize[card.getSize()]++;
        }
        switch (lastType){
            //单张
            case 1:{
                if(size>lastSize){
                    updateDate(1,deck.first().getSize(),1);
                    return true;
                }
                return false;
            }
            //对子
            case 2:{
                int firstSize=deck.first().getSize();
                int secondSize= Objects.requireNonNull(deck.higher(deck.first())).getSize();
                if((firstSize==secondSize)&&firstSize>lastSize) {
                    updateDate(2,firstSize,2);
                    return true;
                }
                return false;
            }
            //三张
            case 3:{
                int firstSize=deck.first().getSize();
                int finalSize=deck.last().getSize();
                if(firstSize==finalSize&&firstSize>lastSize){
                    updateDate(3,firstSize,3);
                    return true;
                }
                return false;
            }
            //三带一
            case 4:{
                int size=-1;
                int firstSize=deck.first().getSize();
                int finalSize=deck.last().getSize();
                //1-3元素相等或2-4元素相等即为三带一
                if(firstSize== Objects.requireNonNull(deck.lower(deck.last())).getSize()){
                    size=firstSize;
                }else if(Objects.requireNonNull(deck.higher(deck.first())).getSize()==finalSize){
                    size=finalSize;
                }
                if(size>finalSize){
                    updateDate(2,firstSize,2);
                    return true;
                }
                else return false;

            }
            //三带二
            case 5:{
                boolean twoNumRight=false;
                boolean threeNumRight=false;
                int size=-1;
                for(int i=0;i<=14;i++){
                    if(cardSize[i]==3)threeNumRight=true;
                    else if(cardSize[i]==2)twoNumRight=true;
                }
                if((threeNumRight==twoNumRight)&&twoNumRight&&size>lastSize){
                    updateDate(5,size,5);
                    return true;
                }
                return false;
            }
            //顺子5~11
            case 6:{
                int firstSize=deck.first().getSize();
                int size=deck.size();
                for(int i=firstSize;i<size+firstSize;++i){
                    if(cardSize[i]!=1){
                        return false;
                    }
                }
                if(firstSize>lastSize){
                    updateDate(6,firstSize,size);
                    return true;
                }
                return false;
            }
            //连对6、8、10、12、14、16
            case 7:{
                int firstSize=deck.first().getSize();
                int size=deck.size(),temp=firstSize;
                for(int i=0;i<size/2;i++){
                    if(cardSize[temp]!=2){
                        return false;
                    }
                    temp++;
                }
                if(firstSize>lastSize){
                    updateDate(7,firstSize,size);
                    return true;
                }
                return false;
            }
            //三三 三三带一一
            case 8: case 9:{
                for(int i=0;i<15;i++){
                    //若三张的牌有两组且相连就是三三了
                    if(cardSize[i]==3){
                        if(cardSize[i+1]!=3)return false;
                        else if(cardSize[i]>lastSize){
                            updateDate(11,i,8);
                            return true;
                        }
                    }
                }
                return false;
            }
            //三三带二二
            case 10:{
                int twoNumRight=0;
                boolean threeNumRight=false;
                int size=0;
                for(int i=0;i<14;i++){
                    if(cardSize[i]==2){
                        twoNumRight++;
                    }
                    else if(cardSize[i]==3&&!threeNumRight){
                        if(cardSize[i+1]==3){
                            threeNumRight=true;
                            size=i;
                        }
                        else return false;
                    }
                }
                if(twoNumRight==2&&threeNumRight){
                    updateDate(10,size,8);
                }
            }
            //三三三带一一一
            case 11:{
                for(int i=0;i<13;i++){
                    if(cardSize[i]==3){
                        if(cardSize[i+1]==3&&cardSize[i+2]==3&&i>lastSize){
                            updateDate(11,i,lastNumber);
                            return true;
                        }
                        else return false;
                    }
                }
                return false;
            }
            //三三三带二二二
            case 12:{
                int twoNumRight=0,size=-1;
                boolean threeNumRight=false;
                for(int i=0;i<13;i++){
                    if(cardSize[i]==3){
                        if(cardSize[i+1]==3&&cardSize[i+2]==3) {
                            threeNumRight = true;
                            size=i;
                        }
                        else return false;
                    }
                    else if(cardSize[i]==2)twoNumRight++;
                }
                if(twoNumRight==3&&threeNumRight&&size>lastSize){
                    updateDate(12,size,15);
                    return true;
                }
                return false;
            }
            //三三三
            case 13:{
                for(int i=0;i<13;i++){
                    if(cardSize[i]==3){
                        if(cardSize[i+1]==3&&cardSize[i+2]==3){
                            updateDate(13,i,9);
                            return true;
                        }
                        else return false;
                    }
                }
            }
            //三三三三带一一一一
            case 14:{
                int oneNumRight=0,size=-1;
                boolean threeNumRight=false;
                for(int i=0;i<15;i++){
                    if(cardSize[i]==3){
                        if(i>8)return false;
                        if(cardSize[i+1]==3&&cardSize[i+2]==3&&cardSize[i+3]==3){
                            size=i;
                            threeNumRight=true;
                        }
                        else return false;
                    }
                    else if(cardSize[i]==1)oneNumRight++;
                    else if(cardSize[i]==2)oneNumRight+=2;
                }
                if(oneNumRight==4&&threeNumRight&&size>lastSize){
                    updateDate(14,size,16);
                }
            }
            //三三三三
            case 15: {
                for (int i = 0; i < 15; i++) {
                    if (cardSize[i] == 3) {
                        if (i > 8) return false;
                        if (cardSize[i + 1] == 3 && cardSize[i + 2] == 3 && cardSize[i + 3] == 3 && i > lastSize) {
                            updateDate(15, i, 12);
                        }
                        else return false;
                    }
                }
            }
            //四带一
            case 16:{
                for(int i=0;i<15;i++){
                    if(cardSize[i]==4){
                        updateDate(16,i,5);
                        return true;
                    }
                }
                return false;
            }
            //四带二
            case 17:{
                boolean twoNumRight=false,fourNumRight=false;
                int size=-1;
                for(int i=0;i<15;i++){
                    if(cardSize[i]==4){
                        size=i;
                        fourNumRight=true;
                    }
                    else if(cardSize[i]==2)
                        twoNumRight=true;
                }
                if(twoNumRight&&fourNumRight&&size>lastSize){
                    updateDate(17,size,6);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    //显示出牌元素
    private final TreeSet<Card>deck;
    //传入的上一次出牌的数据
    private final int lastType;
    private final int lastSize;
    private final int lastNumber;
    //将要传出的本次出牌的数据
    private int deckType;
    private int size;
    private int number;
}

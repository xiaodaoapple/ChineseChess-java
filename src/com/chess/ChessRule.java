package com.chess;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * 象棋规则类
 */
public class ChessRule {
    /**  鼠标的坐标是从工具栏以下开始计算原点，而棋子的坐标都加上了30的工具栏高度，在编程判断时候棋子坐标要减去30
     * 炮移动规则类
     * @param play 要移动的棋子名称
     * @param playQ 所有棋子
     * @param me 鼠标事件
     */
    public void cannonRule(ChessManBehavior play, ChessManBehavior playQ[], MouseEvent me, List<ChessRegret> chessRegretList,int man){
        int count=0;
        int cx=me.getX();
        int cy=me.getY()+30;
        //上下移动   保证在炮这个棋子横坐标+宽度的57距离内
        if(cx-play.getX()>=0 && cx-play.getX()<57){
            for(int i=56;i<=569;i+=57){
                //判断鼠标处在哪个格子
                if(cy-i>=0 && cy-i<57){
                    for(int j=0;j<32;j++){
                        if(playQ[j].getX()==play.getX() && play.getName()!=playQ[j].getName() &&playQ[j].died==false){
                          for(int k=i;k<play.getY();k+=57){
                              if(playQ[j].getY()==k){
                                  count++;
                              }
                          }
                          for(int k=play.getY();k<i;k+=57){
                              if(playQ[j].getY()==k){
                                  count++;
                              }
                          }
                        }
                    }
                    if(count==0){
                        ChessRegret chessRegret=new ChessRegret();
                        chessRegret.man=man;
                        chessRegret.cx=play.getX();
                        chessRegret.cy=play.getY();
                        chessRegretList.add(chessRegret);
                        play.setBounds(play.getX(),i,55,55);
                        //播放声音
                        play.playSounds(man);
                        return;
                    }
                }
            }
        }
        //左右移动 保证在炮这个棋子的纵坐标+高度57的距离内
        if(me.getY()-play.getY()+30>=0 && me.getY()-play.getY()+30<57){
          for(int i=24;i<=480;i+=57){
              if(me.getX()-i>=0 && me.getX()-i<57){
                  for(int j=0;j<32;j++){
                      if(playQ[j].getY()==play.getY() && play.getName()!=playQ[j].getName() &&playQ[j].died==false){
                          for(int k=i;k<play.getX();k+=57){
                              if(playQ[j].getX()==k){
                                  count++;
                              }
                          }
                          for(int k=play.getX();k<i;k+=57){
                              if(playQ[j].getX()==k){
                                  count++;
                              }
                          }
                      }
                  }
                  if(count==0){
                      ChessRegret chessRegret=new ChessRegret();
                      chessRegret.man=man;
                      chessRegret.cx=play.getX();
                      chessRegret.cy=play.getY();
                      chessRegretList.add(chessRegret);
                      play.setBounds(i,play.getY(),55,55);
                      //播放声音
                      play.playSounds(man);
                      return;
                  }
              }
          }
        }
    }
    /**
     *
     * @param play
     * @param playQ
     * @param enemy
     */
    public  void cannonEat(ChessManBehavior play,ChessManBehavior playQ[], ChessManBehavior enemy,List<ChessRegret> chessRegretList,int man,int beEat){
        int count=0;
        //上下吃
        if(play.getX()==enemy.getX()){
            for(int i=0; i<32;i++){
                //往下吃
                if(play.getY()<enemy.getY()){
                    for(int j=play.getY()+57;j<enemy.getY();j=j+57){
                        if(playQ[i].getX()==play.getX()&& playQ[i].getY()==j && playQ[i].died==false)
                            count++;
                    }
                }else{ //往上吃
                    for(int j=enemy.getY()+57;j<play.getY();j=j+57){
                        if(playQ[i].getX()==play.getX()&& playQ[i].getY()==j && playQ[i].died==false)
                            count++;
                    }
                }
            }
            if(count==1){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.eatMan=beEat;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegret.man=man;
                chessRegretList.add(chessRegret);
                enemy.setVisible(false);
                enemy.died=true;
                play.setBounds(enemy.getX(),enemy.getY(),55,55);
                return;
            }
        }
        //左右吃
        if(play.getY()==enemy.getY()){
            for(int i=0;i<32;i++){
                //往左吃
                if(play.getX()>enemy.getX()){
                    for(int j=enemy.getX()+57;j<play.getX();j=j+57){
                        if(playQ[i].getY()==play.getY() && playQ[i].getX()==j && playQ[i].died==false)
                            count++;
                    }
                }else{//往右吃
                    for(int j=play.getX();j<enemy.getX();j+=57){
                        if(playQ[i].getY()==play.getY() && playQ[i].getX()==j && playQ[i].died==false)
                            count++;
                    }
                }
            }
            if(count==1){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.eatMan=beEat;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegret.man=man;
                chessRegretList.add(chessRegret);
                enemy.setVisible(false);
                enemy.died=true;
                play.setBounds(enemy.getX(),enemy.getY(),55,55);
                return;
            }
        }
    }
    /**
     * 车的移动规则
     * @param play
     * @param playQ
     * @param me
     */
    public void carRule(ChessManBehavior play, ChessManBehavior playQ[], MouseEvent me,List<ChessRegret> chessRegretList,int man){
        cannonRule(play,playQ,me,chessRegretList,man);
    }
    /**
     *
     * @param play 要做移动的棋子
     * @param playQ 所有棋子
     * @param enemy 要被吃的棋子
     */
    public void carEat(ChessManBehavior play,ChessManBehavior playQ[], ChessManBehavior enemy,List<ChessRegret> chessRegretList,int man,int beEat){
        int count=0;
        //上下吃
        if(play.getX()==enemy.getX()){
            for(int i=0; i<32;i++){
                //往下吃
                if(play.getY()<enemy.getY()){
                    for(int j=play.getY()+57;j<enemy.getY();j=j+57){
                        if(playQ[i].getX()==play.getX()&& playQ[i].getY()==j && playQ[i].died==false)
                            count++;
                    }
                }else{ //往上吃
                    for(int j=enemy.getY()+57;j<play.getY();j=j+57){
                        if(playQ[i].getX()==play.getX()&& playQ[i].getY()==j && playQ[i].died==false)
                            count++;
                    }
                }
            }
            if(count==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.eatMan=beEat;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegret.man=man;
                chessRegretList.add(chessRegret);
                enemy.setVisible(false);
                enemy.died=true;
                play.setBounds(enemy.getX(),enemy.getY(),55,55);
                return;
            }
        }
        //左右吃
        if(play.getY()==enemy.getY()){
            for(int i=0;i<32;i++){
                //往左吃
                if(play.getX()>enemy.getX()){
                    for(int j=enemy.getX()+57;j<play.getX()-57;j=j+57){
                        if(playQ[i].getY()==play.getY() && playQ[i].getX()==j && playQ[i].died==false)
                            count++;
                    }
                }else{//往右吃
                    for(int j=play.getX();j<enemy.getX();j+=57){
                        if(playQ[i].getY()==play.getY() && playQ[i].getX()==j && playQ[i].died==false)
                            count++;
                    }
                }
            }
            if(count==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.eatMan=beEat;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegret.man=man;
                chessRegretList.add(chessRegret);
                enemy.setVisible(false);
                enemy.died=true;
                play.setBounds(enemy.getX(),enemy.getY(),55,55);
                return;
            }
        }
    }
    /**
     * 马的移动规则类
     * @param play 要移动的棋子名称
     * @param playQ 所有棋子
     * @param me 鼠标事件
     */
    public void horseRule(ChessManBehavior play,ChessManBehavior playQ[],MouseEvent me, List<ChessRegret> chessRegretList,int man){
        int ex=0,ey=0,move=0;
        //棋子的坐标从工具栏下开始算，鼠标点击点的坐标从工具栏上开始算，所以需要将鼠标坐标点+30
        ex=me.getX(); ey=me.getY()+30;
        //左上坐标点
        if(play.getX()-ex>0 && play.getX()-ex<=57 && play.getY()-ey>=59 && play.getY()-ey<=114){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getX()==play.getX() && playQ[i].getY()-play.getY()==-57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-57,play.getY()-114,55,55);
                return;
            }
        }
        //右上坐标点
        if(ex-play.getX()>0 && play.getX()-ex<=57 && play.getY()-ey>=59 && play.getY()-ey<=114){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getX()==play.getX() && playQ[i].getY()-play.getY()==-57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+57,play.getY()-114,55,55);
            }

            return;
        }
        //左下坐标点
        if(play.getX()-ex>0 && play.getX()-ex<=57 && ey-play.getY()>=114 && ey-play.getY()<=170){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getX()==play.getX() && playQ[i].getY()-play.getY()==57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-57,play.getY()+114,55,55);
                return;
            }
        }
        //右下坐标点
        if(ex-play.getX()>0 && play.getX()-ex<=57 && ey-play.getY()>=114 && ey-play.getY()<=170){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getX()==play.getX() && playQ[i].getY()-play.getY()==57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+57,play.getY()+114,55,55);
                return;
            }
        }

        //左左上坐标点
        if(play.getX()-ex>0 && play.getX()-ex<=114 && play.getY()-ey>0 && play.getY()-ey<=57){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getY()==play.getY() && playQ[i].getX()-play.getX()==-57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-114,play.getY()-57,55,55);
                return;
            }
        }
        //左左下坐标点
        if(play.getX()-ex>0 && play.getX()-ex<=114 && ey-play.getY()>=57 && ey-play.getY()<114){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getY()==play.getY() && playQ[i].getX()-play.getX()==-57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-114,play.getY()+57,55,55);
                return;
            }
        }
        //右右上
        if(ex-play.getX()>=114 && ex-play.getX()<=170 && play.getY()-ey>0 && play.getY()-ey<=57){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getY()==play.getY() && playQ[i].getX()-play.getX()==57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+114,play.getY()-57,55,55);
                return;
            }
        }
        //右右下
        if(ex-play.getX()>=114 && ex-play.getX()<=170 && ey-play.getY()>=57 && ey-play.getY()<114){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getY()==play.getY() && playQ[i].getX()-play.getX()==57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+114,play.getY()+57,55,55);
                return;
            }
        }
    }
    /**
     *
     * @param play
     * @param playQ
     * @param enemy
     */
    public void horseEat(ChessManBehavior play,ChessManBehavior playQ[], ChessManBehavior enemy,List<ChessRegret> chessRegretList,int man,int beEat){
        int canGo=1; //1可以移动，0不可以移动
        //左上和右上坐标
        if((play.getX()-57==enemy.getX() || play.getX()+57==enemy.getX()) && play.getY()-114==enemy.getY()){
            //判断马眼是否有棋子
            for(int i=0;i<32;i++){
                if(playQ[i].getX()==play.getX() && playQ[i].getY()==play.getY()-57 && playQ[i].died==false)
                    canGo=0;
            }
            if(canGo==1){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.eatMan=beEat;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegret.man=man;
                chessRegretList.add(chessRegret);
                enemy.setVisible(false);
                enemy.died=true;
                play.setBounds(enemy.getX(),enemy.getY(),55,55);
                return;
            }
        }
        //左下和右下
        if((play.getX()-57==enemy.getX() || play.getX()+57==enemy.getX()) && play.getY()+114==enemy.getY()){
            //判断马眼是否有棋子
            for(int i=0;i<32;i++){
                if(playQ[i].getX()==play.getX() && playQ[i].getY()==play.getY()+57 && playQ[i].died==false)
                    canGo=0;
            }
            if(canGo==1){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.eatMan=beEat;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegret.man=man;
                chessRegretList.add(chessRegret);
                enemy.setVisible(false);
                enemy.died=true;
                play.setBounds(enemy.getX(),enemy.getY(),55,55);
                return;
            }
        }
        //左左上和左左上
        if(play.getX()-114==enemy.getX()  && (play.getY()-57==enemy.getY() || play.getY()+57==enemy.getY())){
            //判断马眼是否有棋子
            for(int i=0;i<32;i++){
                if(playQ[i].getX()-57==play.getX() && playQ[i].getY()==play.getY() && playQ[i].died==false)
                    canGo=0;
            }
            if(canGo==1){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.eatMan=beEat;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegret.man=man;
                chessRegretList.add(chessRegret);
                enemy.setVisible(false);
                enemy.died=true;
                play.setBounds(enemy.getX(),enemy.getY(),55,55);
                return;
            }
        }
        //右右上和右右下
        if(play.getX()+114==enemy.getX()  && (play.getY()-57==enemy.getY() || play.getY()+57==enemy.getY())){
            //判断马眼是否有棋子
            for(int i=0;i<32;i++){
                if(playQ[i].getX()+57==play.getX() && playQ[i].getY()==play.getY() && playQ[i].died==false)
                    canGo=0;
            }
            if(canGo==1){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.eatMan=beEat;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegret.man=man;
                chessRegretList.add(chessRegret);
                enemy.setVisible(false);
                enemy.died=true;
                play.setBounds(enemy.getX(),enemy.getY(),55,55);
                return;
            }
        }
    }
    /**
     * 马的移动规则类
     * @param play 要移动的棋子名称
     * @param playQ 所有棋子
     * @param me 鼠标事件
     */
    public void elephantRule(ChessManBehavior play,ChessManBehavior playQ[],MouseEvent me, List<ChessRegret> chessRegretList,int man){
        int ex=me.getX();
        int ey=me.getY()+30;
        int move=0;
        //大象不能过界
        if(play.getY()<=284){
            if(ey>=341)  //点击的点过了河，象不能移动
                return;
        }
        if(play.getY()>=341){
            if(ey<341)
                return;
        }

        //左上移动
        if(play.getX()-ex<=114 && play.getX()-ex>=58 && play.getY()-ey<=114 && play.getY()-ey>=58){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getX()==play.getX()-57 && playQ[i].getY()-play.getY()==-57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-114,play.getY()-114,55,55);
                return;
            }
        }
        //左下移动
        if(play.getX()-ex<=114 && play.getX()-ex>=58 && ey-play.getY()>=114 && ey-play.getY()<=170){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getX()==play.getX()-57 && playQ[i].getY()-play.getY()==57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-114,play.getY()+114,55,55);
                return;
            }
        }
        //右上移动
        if(ex-play.getX()>=114 && ex-play.getX()<=170 && play.getY()-ey<=114 && play.getY()-ey>=58){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getX()==play.getX()+57 && playQ[i].getY()-play.getY()==-57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+114,play.getY()-114,55,55);
                return;
            }
        }
        //右下移动
        if(ex-play.getX()>=114 && ex-play.getX()<=170 && ey-play.getY()>=114 && ey-play.getY()<=170){
            for(int i=0;i<32;i++){
                if(playQ[i].died==false && playQ[i].getX()==play.getX()+57 && playQ[i].getY()-play.getY()==57){
                    move=1;
                    break;
                }
            }
            if(move==0){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+114,play.getY()+114,55,55);
                return;
            }
        }
    }
    /**
     *
     * @param play
     * @param playQ
     * @param enemy
     */
    public void elephantEat(ChessManBehavior play,ChessManBehavior playQ[],ChessManBehavior enemy,List<ChessRegret> chessRegretList,int man,int beEat){
        int canGo=1; //1可以移动，0不可以移动
        int judge=play.getName().charAt(1)-'0';
        //左上
        if(play.getX()-114==enemy.getX() && play.getY()-114==enemy.getY() ){
            if((judge==1 && enemy.getY()<=455) || (judge==2 && enemy.getY()>=512)){ //判断棋子颜色，控制其移动范围
                for(int i=0;i<32;i++){
                    if(playQ[i].getX()==play.getX()-57 && playQ[i].getY()==play.getY()-57&&  playQ[i].died==false)
                        canGo=0;
                }
                if(canGo==1){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
        //右上
        if(play.getX()+114==enemy.getX() && play.getY()-114==enemy.getY() ){
            if((judge==1 && enemy.getY()<=455) || (judge==2 && enemy.getY()>=512)){ //判断棋子颜色，控制其移动范围
                for(int i=0;i<32;i++){
                    if(playQ[i].getX()==play.getX()+57 && playQ[i].getY()==play.getY()-57 &&  playQ[i].died==false)
                        canGo=0;
                }
                if(canGo==1){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
        //左下
        if(play.getX()-114==enemy.getX() && play.getY()+114==enemy.getY()){
            if((judge==1 && enemy.getY()<=455) || (judge==2 && enemy.getY()>=512)){ //判断棋子颜色，控制其移动范围
                for(int i=0;i<32;i++){
                    if(playQ[i].getX()==play.getX()-57 && playQ[i].getY()==play.getY()+57 &&  playQ[i].died==false)
                        canGo=0;
                }
                if(canGo==1){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
        //右下
        if(play.getX()+114==enemy.getX() && play.getY()+114==enemy.getY()){
            if((judge==1 && enemy.getY()<=455) || (judge==2 && enemy.getY()>=512)){ //判断棋子颜色，控制其移动范围
                for(int i=0;i<32;i++){
                    if(playQ[i].getX()==play.getX()+57 && playQ[i].getY()==play.getY()+57 &&  playQ[i].died==false)
                        canGo=0;
                }
                if(canGo==1){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
    }
    /**
     * 士的移动规则类
     * @param play 要移动的棋子名称
     * @param playQ 所有棋子
     * @param me 鼠标事件
     */
    public void chapRule(ChessManBehavior play,ChessManBehavior playQ[],MouseEvent me, List<ChessRegret> chessRegretList,int man){
        int judge=play.getName().charAt(1)-'0'; //判断是红色还是黑色士,黑色为1，红色为2
        int ex=me.getX(); int ey=me.getY()+30;
        //右上
        if(ex- play.getX()>=57 && ex-play.getX()<=113 && play.getY()-ey>0 &&play.getY()-ey<=57){
            if(judge==1 && play.getX()+57>=252 && play.getX()+57<=309){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                //通过x坐标限制了士的移动
                play.setBounds(play.getX()+57,play.getY()-57,55,55);
            }
            if(judge==2 && play.getY()-57<=512 && play.getY()-57>=455 && play.getX()+57>=252 && play.getX()+57<=309){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+57,play.getY()-57,55,55);
            }
        }
        //右下
        if(ex-play.getX()>=57 && ex-play.getX()<=113 && ey-play.getY()>0 && ey-play.getY()<=113){
            if(judge==1 && play.getX()+57<=309 && play.getX()+57>=252){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+57,play.getY()+57,55,55);
            }
            if(judge==2 && play.getY()+57<=569 && play.getY()+57>=512 && play.getX()+57>=252 && play.getX()+57<=309){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+57,play.getY()+57,55,55);
            }
        }
        //左上
        if(play.getX()-ex>0 && play.getX()-ex<=57 && play.getY()-ey>0 && play.getY()-ey<=57){
            if(judge==1 && play.getX()-57>=195 && play.getX()-57 <=252){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-57,play.getY()-57,55,55);
            }
            if(judge==2 && play.getY()-57<=512 && play.getY()-57>=455 ){
                play.setBounds(play.getX()-57,play.getY()-57,55,55);
            }
        }
        //左下
        if(play.getX()-ex>0 && play.getX()-ex<=57 && ey-play.getY()>0 && ey-play.getY()<=113){
            if(judge==1 && play.getX()-57>=195 && play.getX()-57<=252){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-57,play.getY()+57,55,55);
            }
            //这个判断方法很重要!!!!
            if(judge==2 && play.getY()+57<=569 && play.getY()+57>=512 && (play.getX()==252 || play.getX()==309)){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-57,play.getY()+57,55,55);
            }
        }
    }
    /**
     *
     * @param play
     * @param playQ
     * @param enemy
     */
    public void chapEat(ChessManBehavior play,ChessManBehavior playQ[],ChessManBehavior enemy,List<ChessRegret> chessRegretList,int man,int beEat){
        int canGo=1; //1可以移动，0不可以移动
        int judge=play.getName().charAt(1)-'0';
        //左上
        if(enemy.getX()==play.getX()-57 && enemy.getY()==play.getY()-57){
            if(judge==1){
                if((enemy.getY()==56 && enemy.getX()==195)||(enemy.getY()==113 && enemy.getX()==252)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
            if(judge==2){
                if((enemy.getY()==455 && enemy.getX()==195)||(enemy.getY()==512 && enemy.getX()==252)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
        //右上
        if(enemy.getX()==play.getX()+57 && enemy.getY()==play.getY()-57){
            if(judge==1){
                if((enemy.getY()==56 && enemy.getX()==309)||(enemy.getY()==113 && enemy.getX()==252)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
            if(judge==2){
                if((enemy.getY()==455 && enemy.getX()==309)||(enemy.getY()==512 && enemy.getX()==252)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
        //左下
        if(enemy.getX()==play.getX()-57 && enemy.getY()==play.getY()+57){
            if(judge==1){
                if((enemy.getY()==170 && enemy.getX()==195)||(enemy.getY()==113 && enemy.getX()==252)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
            if(judge==2){
                if((enemy.getY()==569 && enemy.getX()==195)||(enemy.getY()==512 && enemy.getX()==252)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
        //右下
        if(enemy.getX()==play.getX()+57 && enemy.getY()==play.getY()+57){
            if(judge==1){
                if((enemy.getY()==170 && enemy.getX()==309)||(enemy.getY()==113 && enemy.getX()==252)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
            if(judge==2){
                if((enemy.getY()==569 && enemy.getX()==309)||(enemy.getY()==512 && enemy.getX()==252)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
    }
    /**
     * 将或者帅的移动规则类
     * @param play 要移动的棋子名称
     * @param playQ 所有棋子
     * @param me 鼠标事件
     */
    public void masterRule(ChessManBehavior play,ChessManBehavior playQ[],MouseEvent me, List<ChessRegret> chessRegretList,int man){
        int ex=me.getX();
        int ey=me.getY()+30;
        int judge=play.getName().charAt(1)-'0';
        //向上移动
        if(me.getX()-play.getX()>=0 && me.getX()-play.getX()<57 && play.getY()-ey>0 && play.getY()-ey<=57){
            if(judge==1 && (play.getX()==195 || play.getX()==309 || play.getX()==252)&& (play.getY()==113 || play.getY()==170))
            {
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX(),play.getY()-57,55,55);
            }
            if(judge==2 && (play.getX()==195 || play.getX()==309 || play.getX()==252)&& (play.getY()==512 || play.getY()==569))
            {
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX(),play.getY()-57,55,55);
            }
        }
        //向下移动
        if(me.getX()-play.getX()>=0 && me.getX()-play.getX()<57 && ey-play.getY()>=57 && ey-play.getY()<=113){
            if(judge==1 && (play.getX()==195 || play.getX()==309 || play.getX()==252)&& (play.getY()==56 || play.getY()==113)){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX(),play.getY()+57,55,55);
            }
            if(judge==2 &&(play.getX()==195 || play.getX()==309 || play.getX()==252)&&(play.getY()==512 || play.getY()==455)){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX(),play.getY()+57,55,55);
            }
        }
        //向左移动
        if(ey-play.getY()>=0 && ey-play.getY()<=56 && play.getX()-ex>0 && play.getX()-ex<=57){
            if(judge==1 && (play.getX()==252 || play.getX()==309)&&(play.getY()==56 || play.getY()==113 || play.getY()==170)){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-57,play.getY(),55,55);
            }
            if(judge==2 && (play.getX()==252 || play.getX()==309) &&(play.getY()==569 || play.getY()==512 || play.getY()==455)){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-57,play.getY(),55,55);
            }
        }
        //向右移动
        if(ey-play.getY()>=0 && ey-play.getY()<=56 && ex-play.getX()>=57 && ex-play.getX()<=113){
            if(judge==1 && (play.getX()==252 || play.getX()==195)&&(play.getY()==56 || play.getY()==113 || play.getY()==170)){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+57,play.getY(),55,55);
            }
            if(judge==2 && (play.getX()==252 || play.getX()==195) &&(play.getY()==569 || play.getY()==512 || play.getY()==455)){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+57,play.getY(),55,55);
            }
        }
    }

    /**
     *
     * @param play
     * @param playQ
     * @param enemy
     */
    public void masterEat(ChessManBehavior play,ChessManBehavior playQ[],ChessManBehavior enemy,List<ChessRegret> chessRegretList,int man,int beEat){
        int judge=play.getName().charAt(1)-'0';
        //向上
        if(enemy.getX()==play.getX() && enemy.getY()==play.getY()-57){
            if(judge==1){
                if((enemy.getX()==252 && enemy.getY()==56)||(enemy.getX()==252 && enemy.getY()==113)||
                        (enemy.getX()==195 && enemy.getY()==56)||(enemy.getX()==195 && enemy.getY()==113)||
                        (enemy.getX()==309 && enemy.getY()==56)||(enemy.getX()==309 && enemy.getY()==113)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
            if(judge==2){
                if((enemy.getX()==252 && enemy.getY()==455)||(enemy.getX()==252 && enemy.getY()==512)||
                        (enemy.getX()==195 && enemy.getY()==455)||(enemy.getX()==195 && enemy.getY()==512)||
                        (enemy.getX()==309 && enemy.getY()==455)||(enemy.getX()==309 && enemy.getY()==512)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
        //向下
        if(enemy.getX()==play.getX() && enemy.getY()==play.getY()+57){
            if(judge==1){
                if((enemy.getX()==252 && enemy.getY()==170)||(enemy.getX()==252 && enemy.getY()==113)||
                        (enemy.getX()==109 && enemy.getY()==170)||(enemy.getX()==109 && enemy.getY()==113)||
                        (enemy.getX()==309 && enemy.getY()==170)||(enemy.getX()==309 && enemy.getY()==113)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
            if(judge==2){
                if((enemy.getX()==252 && enemy.getY()==569)||(enemy.getX()==252 && enemy.getY()==512)||
                        (enemy.getX()==109 && enemy.getY()==569)||(enemy.getX()==109 && enemy.getY()==512)||
                        (enemy.getX()==309 && enemy.getY()==569)||(enemy.getX()==309 && enemy.getY()==512)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
        //向左
        if(enemy.getX()==play.getX()-57 && enemy.getY()==play.getY()){
            if(judge==1){
                if((enemy.getX()==252 && enemy.getY()==56)||(enemy.getX()==195 && enemy.getY()==56)||
                        (enemy.getX()==252 && enemy.getY()==113)||(enemy.getX()==195 && enemy.getY()==113)||
                        (enemy.getX()==252 && enemy.getY()==170)||(enemy.getX()==195 && enemy.getY()==170)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
            if(judge==2){
                if((enemy.getX()==252 && enemy.getY()==455)||(enemy.getX()==195 && enemy.getY()==455)||
                        (enemy.getX()==252 && enemy.getY()==512)||(enemy.getX()==195 && enemy.getY()==512)||
                        (enemy.getX()==252 && enemy.getY()==569)||(enemy.getX()==195 && enemy.getY()==569)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
        //向右
        if(enemy.getX()==play.getX()+57 && enemy.getY()==play.getY()){
            if(judge==1){
                if((enemy.getX()==252 && enemy.getY()==56)||(enemy.getX()==309 && enemy.getY()==56)||
                        (enemy.getX()==252 && enemy.getY()==113)||(enemy.getX()==309 && enemy.getY()==113)||
                        (enemy.getX()==252 && enemy.getY()==170)||(enemy.getX()==309 && enemy.getY()==170)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
            if(judge==2){
                if((enemy.getX()==252 && enemy.getY()==455)||(enemy.getX()==309 && enemy.getY()==455)||
                        (enemy.getX()==252 && enemy.getY()==512)||(enemy.getX()==309 && enemy.getY()==512)||
                        (enemy.getX()==252 && enemy.getY()==569)||(enemy.getX()==309 && enemy.getY()==569)){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
    }
    /**
     * 兵的移动规则
     * @param play 要移动的棋子名称
     * @param playQ 所有棋子
     * @param me 鼠标事件
     */
    public void soliderRule(ChessManBehavior play,ChessManBehavior playQ[],MouseEvent me, List<ChessRegret> chessRegretList,int man){
        int judge=play.getName().charAt(1)-'0';
        int ex=me.getX();
        int ey=me.getY()+30;
        //黑色棋子
        if(judge==1){
            //向下运动
            if(me.getX()-play.getX()>=0 && me.getX()-play.getX()<57 && ey-play.getY()>=57 && ey-play.getY()<=113){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX(),play.getY()+57,55,55);
            }
            //向右运动 通过棋子的Y坐标判断是否已经过河
            if(play.getY()>=341&& ey-play.getY()>=0 && ey-play.getY()<=56 && ex-play.getX()>=57 && ex-play.getX()<=113){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+57,play.getY(),55,55);
            }
            //向左运动 通过棋子的Y坐标判断是否已经过河
            if(play.getY()>=341 && ey-play.getY()>=0 && ey-play.getY()<=56 && play.getX()-ex>0 && play.getX()-ex<=57){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-57,play.getY(),55,55);
            }
        }
        //红色棋子
        if(judge==2){
            //向上运动
            if(me.getX()-play.getX()>=0 && me.getX()-play.getX()<57 && play.getY()-ey>0 && play.getY()-ey<=57){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
               play.setBounds(play.getX(),play.getY()-57,55,55);
            }
            //向右运动
            if(play.getY()<=284 && ey-play.getY()>=0 && ey-play.getY()<=56 && ex-play.getX()>=57 && ex-play.getX()<=113){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()+57,play.getY(),55,55);
            }
            //向左运动
            if(play.getY()<=284 && ey-play.getY()>=0 && ey-play.getY()<=56 && play.getX()-ex>0 && play.getX()-ex<=57){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.man=man;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegretList.add(chessRegret);
                play.setBounds(play.getX()-57,play.getY(),55,55);
            }
        }

    }
    public void soliderEat(ChessManBehavior play,ChessManBehavior playQ[],ChessManBehavior enemy,List<ChessRegret> chessRegretList,int man,int beEat){
        int judge=play.getName().charAt(1)-'0';
        //向上
        if(enemy.getX()==play.getX() && enemy.getY()==play.getY()-57){
            //只有红色可以向上
            if(judge==2){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.eatMan=beEat;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegret.man=man;
                chessRegretList.add(chessRegret);
                enemy.setVisible(false);
                enemy.died=true;
                play.setBounds(enemy.getX(),enemy.getY(),55,55);
                return;
            }
        }
        //向下
        if(enemy.getX()==play.getX() && enemy.getY()==play.getY()+57){
            //只有黑色可以向下
            if(judge==1){
                ChessRegret chessRegret=new ChessRegret();
                chessRegret.eatMan=beEat;
                chessRegret.cx=play.getX();
                chessRegret.cy=play.getY();
                chessRegret.man=man;
                chessRegretList.add(chessRegret);
                enemy.setVisible(false);
                enemy.died=true;
                play.setBounds(enemy.getX(),enemy.getY(),55,55);
                return;
            }
        }
        //向左
        if(enemy.getX()==play.getX()-57 && enemy.getY()==play.getY()){
            if(judge==1){
                //黑兵只有过界才能左吃
                if(enemy.getY()>=341){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
            if(judge==2){
                if(enemy.getY()<341){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
        //向右
        if(enemy.getX()==play.getX()+57 && enemy.getY()==play.getY()){
            if(judge==1){
                //黑兵只有过界才能左吃
                if(enemy.getY()>=341){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
            if(judge==2){
                if(enemy.getY()<341){
                    ChessRegret chessRegret=new ChessRegret();
                    chessRegret.eatMan=beEat;
                    chessRegret.cx=play.getX();
                    chessRegret.cy=play.getY();
                    chessRegret.man=man;
                    chessRegretList.add(chessRegret);
                    enemy.setVisible(false);
                    enemy.died=true;
                    play.setBounds(enemy.getX(),enemy.getY(),55,55);
                    return;
                }
            }
        }
    }

    /**
     * 判断将和帅是否碰面
     * @param playQ
     * @return 0 没碰面，1碰面
     */
    public int masterMeet(ChessManBehavior playQ[]){
        if(playQ[30].getX()!=playQ[31].getX())
            return 0;
        int count=0;
        int cx=playQ[30].getX();
        for(int j=playQ[30].getY()+57;j<playQ[31].getY();j=j+57){
            for(int i=0;i<32;i++){
                if(playQ[i].getX()==cx && playQ[i].getY()==j && playQ[i].died==false)
                    count++;
            }
        }
        if(count>0)
            return 0;
        return 1;
    }

}

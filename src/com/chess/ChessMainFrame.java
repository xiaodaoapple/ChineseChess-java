package com.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChessMainFrame extends JFrame implements ActionListener, MouseListener,Runnable {
    ChessManBehavior play[] =new ChessManBehavior[32];  //定义32个棋子
    JLabel image; //棋盘
    Container con;
    JToolBar jmain;
    JButton anew,repent,exit;
    JLabel text;
    /**
     * true 游戏结束
     * false 游戏继续
     */
    boolean gameIsOver;
    //规则类，判断走棋和吃子
    ChessRule chessRule;
    boolean chessManClick;
    /**
     * chessPlayClick=1 黑旗走
     * chessPlayClick=2 红旗走
     * chessPlayClick=3 都不能走
     */
    int chessPlayClick=2;
    Thread tmain;
    static int Man;
    List<ChessRegret> regretList;
    public ChessMainFrame(String title){
        con =this.getContentPane(); //获得窗口的内容面板
        con.setLayout(null); //设置成绝对布局，组件位置和形状不会随着窗口改变而变化
        chessRule=new ChessRule();
        this.setTitle(title);
        jmain=new JToolBar(); //新建工具栏
        text=new JLabel("欢迎使用象棋对弈系统");
        text.setToolTipText("信息提示");
        anew=new JButton("新游戏");
        anew.setToolTipText("重新开始新的一局");
        exit=new JButton("退出");
        exit.setToolTipText("退出象棋对弈系统");
        repent=new JButton("悔棋");
        repent.setToolTipText("返回到上次走棋的位置");
        //组件添加到工具栏
        jmain.setLayout(new GridLayout(0,4));  //栅格布局，4列
        jmain.add(anew);
        jmain.add(repent);
        jmain.add(exit);
        jmain.add(text);
        jmain.setBounds(0,0,558,30);
        con.add(jmain);
        drawChessMain();   //需要先画棋子，先放的JLabel永远在最前

        for(int i=0;i<32;i++){
            con.add(play[i]);
            play[i].addMouseListener(this);
        }
        con.add(image=new JLabel(new ImageIcon("image/main.gif")));

        image.setBounds(0,30,558,620);

        image.addMouseListener(this);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("系统关闭");
                System.exit(0);
            }
        });
        //窗体居中
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize(); //得到ToolKit的抽象类对象，获得系统相关知识
        Dimension frameSize=this.getSize();
        this.setLocation((screenSize.width-frameSize.width)/2-280,(screenSize.height-frameSize.height)/2-350);
        this.setIconImage(new ImageIcon("image/红将.gif").getImage());
        this.setResizable(false);
        this.setSize(558,676); //标题栏默认高度26px
        this.setVisible(true);
        gameIsOver=false;
        regretList=new ArrayList<ChessRegret>();
        anew.addActionListener(this);
        repent.addActionListener(this);
        exit.addActionListener(this);
    }

    /**
     * 棋子重置回原来位置
     */
    public void chessReset(){
        int i,k;
        for(i=0,k=24;i<2;i++,k+=456){
            play[i].setBounds(k,56,55,55);
        }
        for(i=4,k=81;i<6;i++,k+=342){
            play[i].setBounds(k,56,55,55);
        }
        for(i=8,k=138;i<10;i++,k+=228){
            play[i].setBounds(k,56,55,55);
        }
        for(i=12,k=195;i<14;i++,k+=114){
            play[i].setBounds(k,56,55,55);
        }
        for(i=16,k=24;i<21;i++,k+=114){
            play[i].setBounds(k,227,55,55);
        }
        for(i=26,k=81;i<28;i++,k+=342){
            play[i].setBounds(k,170,55,55);
        }
        play[30].setBounds(252,56,55,55);
        //开始红方显示
        for(i=2,k=24;i<4;i++,k+=456){
            play[i].setBounds(k,569,55,55);
        }
        for(i=6,k=81;i<8;i++,k+=342){
            play[i].setBounds(k,569,55,55);
        }
        for(i=10,k=138;i<12;i++,k+=228){
            play[i].setBounds(k,569,55,55);
        }
        for(i=14,k=195;i<16;i++,k+=114){
            play[i].setBounds(k,569,55,55);
        }
        for(i=21,k=24;i<26;i++,k+=114){
            play[i].setBounds(k,398,55,55);
        }
        for(i=28,k=81;i<30;i++,k+=342){
            play[i].setBounds(k,455,55,55);
        }
        play[31].setBounds(252,569,55,55);
        for(i=0;i<32;i++){
            System.out.println(i+" "+play[i].getName());
        }
        gameIsOver=false;
        for(i=0;i<32;i++){
            play[i].setVisible(true);
            play[i].died=false;
        }
        text.setText("     红棋走棋");
        Man=0;
        regretList.clear(); //清空悔棋的list
    }
    /**
     * 画棋盘，画棋子
     */
    public void drawChessMain(){
        int i,k;
        Icon in=null;
        in=new ImageIcon("image/黑车.gif");
        for(i=0,k=24;i<2;i++,k+=456){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,56,55,55);
            play[i].setName("车1");
        }
        in=new ImageIcon("image/黑马.gif");
        for(i=4,k=81;i<6;i++,k+=342){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,56,55,55);
            play[i].setName("马1");
        }
        in=new ImageIcon("image/黑象.gif");
        for(i=8,k=138;i<10;i++,k+=228){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,56,55,55);
            play[i].setName("象1");
        }
        in=new ImageIcon("image/黑士.gif");
        for(i=12,k=195;i<14;i++,k+=114){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,56,55,55);
            play[i].setName("士1");
        }
        in=new ImageIcon("image/黑卒.gif");
        for(i=16,k=24;i<21;i++,k+=114){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,227,55,55);
            play[i].setName("卒1"+i);
        }
        in=new ImageIcon("image/黑炮.gif");
        for(i=26,k=81;i<28;i++,k+=342){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,170,55,55);
            play[i].setName("炮1"+i);
        }
        in=new ImageIcon("image/黑将.gif");
        play[30]=new ChessManBehavior(in);
        play[30].setBounds(252,56,55,55);
        play[30].setName("将1");
        //开始红方显示
        in=new ImageIcon("image/红车.gif");
        for(i=2,k=24;i<4;i++,k+=456){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,569,55,55);
            play[i].setName("车2");
        }
        in=new ImageIcon("image/红马.gif");
        for(i=6,k=81;i<8;i++,k+=342){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,569,55,55);
            play[i].setName("马2");
        }
        in=new ImageIcon("image/红象.gif");
        for(i=10,k=138;i<12;i++,k+=228){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,569,55,55);
            play[i].setName("象2");
        }
        in=new ImageIcon("image/红士.gif");
        for(i=14,k=195;i<16;i++,k+=114){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,569,55,55);
            play[i].setName("士2");
        }
        in=new ImageIcon("image/红卒.gif");
        for(i=21,k=24;i<26;i++,k+=114){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,398,55,55);
            play[i].setName("卒2"+i);
        }
        in=new ImageIcon("image/红炮.gif");
        for(i=28,k=81;i<30;i++,k+=342){
            play[i]=new ChessManBehavior(in);
            play[i].setBounds(k,455,55,55);
            play[i].setName("炮2"+i);
        }
        in=new ImageIcon("image/红将.gif");
        play[31]=new ChessManBehavior(in);
        play[31].setBounds(252,569,55,55);
        play[31].setName("帅2");
        for(i=0;i<32;i++){
            System.out.println(i+" "+play[i].getName());
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(anew)){
            System.out.println("click renew");
            chessReset();
        }
        if(e.getSource().equals(repent)){
            System.out.println("regret");
            if(regretList.isEmpty()==false){
                //得到栈顶元素
                ChessRegret chessRegret= regretList.get(regretList.size()-1);
                Man=chessRegret.man;
                play[Man].setBounds(chessRegret.cx,chessRegret.cy,55,55);
                if(chessRegret.eatMan!=-1){
                    play[chessRegret.eatMan].setVisible(true);
                }
                if(play[Man].getName().charAt(1)=='2'){
                    chessPlayClick=2;
                    text.setText("     红棋走棋");
                }else{
                    chessPlayClick=1;
                    text.setText("     黑棋走棋");
                }
                regretList.remove(regretList.size()-1); //删除栈顶元素
            }
        }
        if(e.getSource().equals(exit)){
            int j=JOptionPane.showConfirmDialog(this,"真的要退出吗？","退出",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(j==JOptionPane.YES_OPTION){
                System.exit(0);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int ex=0;
        int ey=0;
        //启动闪烁线程
        if(tmain==null){
            tmain=new Thread(this);
            tmain.start();
        }
        //如果游戏已经结束，不能移动棋子
        if(gameIsOver)
            return;
        //单击棋盘
        if(e.getSource().equals(image)){
            if(chessPlayClick==2 && play[Man].getName().charAt(1)=='2'){
                ex=play[Man].getX();
                ey=play[Man].getY();
                if(Man>=0 && Man<4){
                    System.out.println("移动红车");
                    chessRule.carRule(play[Man],play,e,regretList,Man);
                }
                if(Man>=4 && Man<8){
                    System.out.println("移动红马");
                    chessRule.horseRule(play[Man],play,e,regretList,Man);
                }
                if(Man>=8 && Man<12){
                    System.out.println("移动红象");
                    chessRule.elephantRule(play[Man],play,e,regretList,Man);
                }
                if(Man>=12 && Man<=15){
                    System.out.println("移动红士");
                    chessRule.chapRule(play[Man],play,e,regretList,Man);
                }
                if(Man>=16 && Man<26){
                    System.out.println("移动红兵");
                    chessRule.soliderRule(play[Man],play,e,regretList,Man);
                }
                if(Man>=25 && Man<30){
                    System.out.println("移动红炮");
                    chessRule.cannonRule(play[Man],play,e,regretList,Man);
                }
                if(Man==30 || Man==31){
                    System.out.println("移动红帅");
                    chessRule.masterRule(play[Man],play,e,regretList,Man);
                }
                if(ex==play[Man].getX() && ey==play[Man].getY()){
                    text.setText("     红棋走棋");  //因为棋子的走子规则不符合，没有挪动棋子
                    chessPlayClick=2;
                }else{
                    if(chessRule.masterMeet(play)==1){
                        //红棋走完，boss见面，红旗输了
                        JOptionPane.showMessageDialog(this,"游戏结束，黑棋赢了！！！");
                        gameIsOver=true;
                        return;
                    }
                    text.setText("     黑棋走棋");
                    chessPlayClick=1;
                    chessManClick=false;
                }
            }else if(chessPlayClick==1 && play[Man].getName().charAt(1)=='1'){
                ex=play[Man].getX();
                ey=play[Man].getY();
                if(Man>=0 && Man<4){
                    System.out.println("移动黑车");
                    chessRule.carRule(play[Man],play,e,regretList,Man);
                }
                if(Man>=4 && Man<8){
                    System.out.println("移动黑马");
                    chessRule.horseRule(play[Man],play,e,regretList,Man);
                }
                if(Man>=8 && Man<12){
                    System.out.println("移动黑象");
                    chessRule.elephantRule(play[Man],play,e,regretList,Man);
                }
                if(Man>=12 && Man<=15){
                    System.out.println("移动黑士");
                    chessRule.chapRule(play[Man],play,e,regretList,Man);
                }
                if(Man>=16 && Man<26){
                    System.out.println("移动黑兵");
                    chessRule.soliderRule(play[Man],play,e,regretList,Man);
                }
                if(Man>=25 && Man<30){
                    System.out.println("移动黑炮");
                    chessRule.cannonRule(play[Man],play,e,regretList,Man);
                }
                if(Man==30 || Man==31){
                    System.out.println("移动黑将");
                    chessRule.masterRule(play[Man],play,e,regretList,Man);
                }
                if(ex==play[Man].getX() && ey==play[Man].getY()){
                    text.setText("     黑棋走棋");
                    chessPlayClick=1;
                }else{
                    if(chessRule.masterMeet(play)==1){
                        //红棋走完，boss见面，红旗输了
                        JOptionPane.showMessageDialog(this,"游戏结束，黑棋赢了！！！");
                        text.setText("游戏结束，黑棋赢了！！！");
                        chessManClick=false;
                        gameIsOver=true;
                        return;
                    }
                    text.setText("     红棋走棋");
                    chessPlayClick=2;
                    chessManClick=false;
                }
            }
        }else{
            //判断单击的棋子
            int iClick=-1;
            for(int i=0;i<32;++i){
                if(e.getSource().equals(play[i])){
                    iClick=i;
                    break;
                }
            }
            //该红旗走
            if(chessPlayClick==2){
                int preX=-1;
                int preY=-1;
                //点击的是红棋，让其闪烁
                if(play[iClick].getName().charAt(1)=='2'){
                    Man=iClick;
                    preX=play[Man].getX();  //得到棋子的当前坐标
                    preY=play[Man].getY();
                    chessManClick=true;
                    return;
                }
                //通过judge判断是否已经点过红旗再点的黑旗，如果直接点的黑旗，则judge返回1，不用进行吃子判断
                int judge=play[Man].getName().charAt(1)-'0';
                //点击的是黑色棋子,进行吃子判断
                if(play[iClick].getName().charAt(1)=='1' && judge==2){
                    //开始吃子
                    if(Man>=0 && Man<4){
                        System.out.println("车吃子判断");
                        chessRule.carEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=4 && Man<8){
                        System.out.println("马吃子判断");
                        chessRule.horseEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=8 && Man<12){
                        System.out.println("象吃子判断");
                        chessRule.elephantEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=12 && Man<16){
                        System.out.println("士吃子判断");
                        chessRule.chapEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=16 && Man<26){
                        System.out.println("士兵吃子判断");
                        chessRule.soliderEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=26 && Man<30){
                        System.out.println("炮吃子判断");
                        chessRule.cannonEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=30 && Man<32){
                        System.out.println("BOSS吃子判断");
                        chessRule.masterEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    //如果吃子成功，则棋子和被吃的子的坐标会相同
                    if(play[iClick].getX()==play[Man].getX() && play[iClick].getY()==play[Man].getY()){
                        //进行得胜判断
                        if(chessRule.masterMeet(play)==1){
                            //红棋走完，boss见面，红旗输了
                            JOptionPane.showMessageDialog(this,"游戏结束，黑棋赢了！！！");
                            text.setText("游戏结束，黑棋赢了！！！");
                            chessManClick=false;
                            gameIsOver=true;
                            return;
                        }
                        if(play[30].isVisible()==false){
                            JOptionPane.showMessageDialog(this,"游戏结束，红棋赢了！！！");
                            text.setText("游戏结束，红棋赢了！！！");
                            chessManClick=false;
                            gameIsOver=true;
                            return;
                        }
                        chessPlayClick=1; //轮到黑旗走
                        text.setText("     黑棋走棋");
                        chessManClick=false;
                    }

                }

            }
            //该黑棋走
            else if(chessPlayClick==1){
                //点击的是黑棋，让其闪烁
                if(play[iClick].getName().charAt(1)=='1'){
                    Man=iClick;
                    chessManClick=true;
                }
                //通过judge判断是否已经点过红旗再点的黑旗，如果直接点的红旗旗，则judge返回2，不用进行吃子判断
                int judge=play[Man].getName().charAt(1)-'0';
                //点击的是红色棋子,进行吃子判断
                if(play[iClick].getName().charAt(1)=='2' && judge==1){
                    //开始吃子
                    if(Man>=0 && Man<4){
                        System.out.println("车吃子判断");
                        chessRule.carEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=4 && Man<8){
                        System.out.println("马吃子判断");
                        chessRule.horseEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=8 && Man<12){
                        System.out.println("象吃子判断");
                        chessRule.elephantEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=12 && Man<16){
                        System.out.println("士吃子判断");
                        chessRule.chapEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=16 && Man<26){
                        System.out.println("士兵吃子判断");
                        chessRule.soliderEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=26 && Man<30){
                        System.out.println("炮吃子判断");
                        chessRule.cannonEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    if(Man>=30 && Man<32){
                        System.out.println("BOSS吃子判断");
                        chessRule.masterEat(play[Man],play,play[iClick],regretList,Man,iClick);
                    }
                    //如果吃子成功，则棋子前后坐标会不同
                    if(play[iClick].getX()==play[Man].getX() && play[iClick].getY()==play[Man].getY()){
                        //进行得胜判断
                        if(chessRule.masterMeet(play)==1){
                            //红棋走完，boss见面，红旗输了
                            JOptionPane.showMessageDialog(this,"游戏结束，红棋赢了！！！");
                            text.setText("游戏结束，红棋赢了！！！");
                            chessManClick=false;
                            gameIsOver=true;
                            return;
                        }
                        if(play[31].isVisible()==false){
                            JOptionPane.showMessageDialog(this,"游戏结束，黑棋赢了！！！");
                            text.setText("游戏结束，黑棋赢了！！！");
                            chessManClick=false;
                            gameIsOver=true;
                            return;
                        }
                        chessPlayClick=2; //轮到红旗走
                        text.setText("     红棋走棋");
                        chessManClick=false;
                    }
                }

            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * 棋子和提示语闪烁
     */
    @Override
    public synchronized void run() {
        while (true){
            if(chessManClick){
                play[Man].setVisible(false);
                try{
                    tmain.sleep(200);
                }catch (Exception e){
                    e.printStackTrace();
                }
                play[Man].setVisible(true);
                for(int i=0;i<32;++i){
                    if(i!=Man && play[i].died==false)
                        play[i].setVisible(true);
                }
            }else{
                text.setVisible(false);
                try{
                    tmain.sleep(250);
                }catch (Exception e){
                    e.printStackTrace();
                }
                text.setVisible(true);
            }
            try{
                tmain.sleep(350);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

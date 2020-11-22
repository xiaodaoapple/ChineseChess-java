package com.chess;

public class ChessRegret {
    public int man; //走棋的棋子
    public int cx; //走到的x坐标
    public int cy; //走到的y坐标
    public int eatMan; //吃掉的棋子
    public ChessRegret(){
        eatMan=-1; //默认没有吃棋子
    }
}

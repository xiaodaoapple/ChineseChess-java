package com.chess;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.io.FileInputStream;

/**
 * 定义棋子的行为动作及声音
 */
public class ChessManBehavior extends JLabel {
    boolean died=false;
    ChessManBehavior(Icon image){
        super(image);
    }
    public void playSounds(int man) {
        if(man>=25 && man<30){
            try {
                FileInputStream fileau = new FileInputStream("sounds/cannon.wav");
                AudioStream as = new AudioStream(fileau);
                AudioPlayer.player.start(as);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

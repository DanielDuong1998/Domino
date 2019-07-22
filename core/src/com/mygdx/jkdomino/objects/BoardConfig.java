package com.mygdx.jkdomino.objects;

import com.mygdx.jkdomino.Domino;

public class BoardConfig {
    public float TW = 66;
    public float TH = 128;
    public float HPADDING = 50;
    public float VPADDING = 160;
    public float INITIAL_X = Domino.SW/2;
    public float INITIAL_Y = Domino.SH/2;

    public float PLAYER_X = (Domino.SW - 7*TW)/2;
    public float PLAYER_Y = 10;
    public float BOTUP_X = (Domino.SW - 7*TW)/2;
    public float BOTUP_Y = Domino.SH - TH/3;
    public float BOTLEFT_X = -1*TW/2;
    public float BOTLEFT_Y = (Domino.SH - 7*TW)/2 ;
    public float BOTRIGHT_X = Domino.SW - 1*TW/2;
    public float BOTRIGHT_Y = (Domino.SH - 7*TW)/2;


//    public float PLAYER_X = (Domino.SW - 7*TW)/2;
//    public float PLAYER_Y = 10;
//    public float BOTUP_X = (Domino.SW - 7*TW)/2;
//    public float BOTUP_Y = Domino.SH;
//    public float BOTLEFT_X = -1*TW/2;
//    public float BOTLEFT_Y = (Domino.SH - 7*TW)/2 - 500;
//    public float BOTRIGHT_X = Domino.SW - 1*TW/2 - 100;
//    public float BOTRIGHT_Y = (Domino.SH - 7*TW)/2;


}

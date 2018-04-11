package com.electroninc.tictactoe;

/**
 * Created by D A Victor on 09-Apr-18.
 */

public class Move {

    private int player;
    private int x;
    private int y;

    public Move(int player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
    }

    public int getPlayer() {
        return this.player;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}

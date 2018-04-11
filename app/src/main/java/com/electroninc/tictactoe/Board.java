package com.electroninc.tictactoe;

/**
 * Created by D A Victor on 09-Apr-18.
 */

public class Board {

    public static final String X = "X";
    public static final String O = "O";
    public static final String D = "-";

    private int dim;
    private String[][] markers;
    private int counter;

    public Board(int dim) {
        this.dim = dim;
        this.markers = new String[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                this.markers[i][j] = Board.D;
            }
        }
        this.counter = 0;
    }

    public static Board newInstance(Board b) {
        Board copy = new Board(b.getDim());
        copy.counter = b.counter;

        for (int i = 1; i <= b.getDim(); i++) {
            for (int j = 1; j <= b.getDim(); j++) {
                copy.setMarker(i, j, b.getMarker(i, j));
            }
        }

        return copy;
    }

    public int getDim() {
        return this.dim;
    }

    public String getMarker(int x, int y) {
        return this.markers[x - 1][y - 1];
    }

    private void setMarker(int x, int y, String marker) {
        this.markers[x - 1][y - 1] = marker;
    }

    public int getCounter() {
        return this.counter;
    }

    public void incrementCounter() {
        this.counter++;
    }

    public boolean makeMove(Move m) {
        if (this.counter < Math.pow(dim, 2) && this.getMarker(m.getX(), m.getY()).equals(Board.D)) {
            this.setMarker(m.getX(), m.getY(), ((m.getPlayer() == 1) ? Board.X : Board.O));
            this.incrementCounter();
            return true;
        } else return false;
    }

}

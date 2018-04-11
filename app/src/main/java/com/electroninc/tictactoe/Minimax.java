package com.electroninc.tictactoe;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by D A Victor on 09-Apr-18.
 */

public class Minimax {

    private static final int F = 50000;

    private static Move move;
    private static int maxDepth;
    private static int p;
    private static int initialCounter;

    public static int checkWin(Board b, int player) {
        String playerMarker = (player == 1) ? Board.X : Board.O;
        String opponentMarker = (player == 1) ? Board.O : Board.X;
        boolean flag;

        // check for wins
        for (int i = 1; i <= b.getDim(); i++) {
            flag = true;
            for (int j = 1; j <= b.getDim(); j++) {
                if (!b.getMarker(i, j).equals(playerMarker)) {
                    flag = false;
                    break;
                }
            }
            if (flag) return 1;
        }

        for (int i = 1; i <= b.getDim(); i++) {
            flag = true;
            for (int j = 1; j <= b.getDim(); j++) {
                if (!b.getMarker(j, i).equals(playerMarker)) {
                    flag = false;
                    break;
                }
            }
            if (flag) return 1;
        }

        flag = true;
        for (int i = 1; i <= b.getDim(); i++) {
            if (!b.getMarker(i, i).equals(playerMarker)) {
                flag = false;
                break;
            }
        }
        if (flag) return 1;

        flag = true;
        for (int i = 1; i <= b.getDim(); i++) {
            if (!b.getMarker(i, b.getDim() - i + 1).equals(playerMarker)) {
                flag = false;
                break;
            }
        }
        if (flag) return 1;


        // check for losses
        for (int i = 1; i <= b.getDim(); i++) {
            flag = true;
            for (int j = 1; j <= b.getDim(); j++) {
                if (!b.getMarker(i, j).equals(opponentMarker)) {
                    flag = false;
                    break;
                }
            }
            if (flag) return -1;
        }

        for (int i = 1; i <= b.getDim(); i++) {
            flag = true;
            for (int j = 1; j <= b.getDim(); j++) {
                if (!b.getMarker(j, i).equals(opponentMarker)) {
                    flag = false;
                    break;
                }
            }
            if (flag) return -1;
        }

        flag = true;
        for (int i = 1; i <= b.getDim(); i++) {
            if (!b.getMarker(i, i).equals(opponentMarker)) {
                flag = false;
                break;
            }
        }
        if (flag) return -1;

        flag = true;
        for (int i = 1; i <= b.getDim(); i++) {
            if (!b.getMarker(i, b.getDim() - i + 1).equals(opponentMarker)) {
                flag = false;
                break;
            }
        }
        if (flag) return -1;

        return 0;
    }

    public static Move getMove(Board b, int player) {
        move = null;
        p = player;
        initialCounter = b.getCounter();

        if (b.getCounter() == 0)
            return new Move(player, (int) (Math.random() * 3 + 1), (int) (Math.random() * 3 + 1));
        if (b.getCounter() == Math.pow(b.getDim(), 2)) return null;

        int c = 1;
        int i = (int) Math.pow(b.getDim(), 2) - b.getCounter();
        int depth = 0;
        while (c <= F && i > 0) {
            c *= i;
            i--;
            depth++;
        }
        maxDepth = depth - ((i == 0) ? 0 : 1);

        minimax(b, p, true);
        return move;
    }

    private static int minimax(Board b, int player, boolean root) {
        Board temp;
        ArrayList<Board> boards = new ArrayList<>();
        ArrayList<Integer> minMax = new ArrayList<>();
        int score;

        if (b.getCounter() == Math.pow(b.getDim(), 2) || b.getCounter() - initialCounter >= maxDepth)
            return checkWin(b, p);

        for (int i = 1; i <= b.getDim(); i++) {
            for (int j = 1; j <= b.getDim(); j++) {
                if (b.getMarker(i, j).equals(Board.D)) {
                    temp = Board.newInstance(b);
                    temp.makeMove(new Move(player, i, j));
                    boards.add(temp);
                }
            }
        }

        if (root) {
            for (int ind = 0; ind < boards.size(); ind++) {
                if (checkWin(boards.get(ind), p) == 1) {
                    Board finalPos = boards.get(ind);

                    for (int i = 1; i <= b.getDim(); i++) {
                        for (int j = 1; j <= b.getDim(); j++) {
                            if (!b.getMarker(i, j).equals(finalPos.getMarker(i, j))) {
                                move = new Move(p, i, j);
                                break;
                            }
                        }
                        if (move != null) break;
                    }
                    return 1;
                }
            }
        }

        for (int i = 0; i < boards.size(); i++) {
            if (checkWin(boards.get(i), p) == 0)
                minMax.add(minimax(boards.get(i), ((player == 1) ? 2 : 1), false));
            else minMax.add(checkWin(boards.get(i), p));
        }

        score = (player == p) ? Collections.max(minMax) : Collections.min(minMax);

        if (root) {
            ArrayList<Integer> indices = new ArrayList<>();
            for (int i = 0; i < minMax.size(); i++) {
                if (minMax.get(i) == score) indices.add(i);
            }
            int index = indices.get((int) (Math.random() * indices.size()));
            Board finalPos = boards.get(index);

            for (int i = 1; i <= b.getDim(); i++) {
                for (int j = 1; j <= b.getDim(); j++) {
                    if (!b.getMarker(i, j).equals(finalPos.getMarker(i, j))) {
                        move = new Move(p, i, j);
                        break;
                    }
                }
                if (move != null) break;
            }
        }

        return score;
    }

}

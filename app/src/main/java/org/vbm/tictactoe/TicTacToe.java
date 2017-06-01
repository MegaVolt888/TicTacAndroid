package org.vbm.tictactoe;

import android.view.View;
import android.widget.Toast;

import java.util.Scanner;

/**
 * Created by vbm on 29/05/2017.
 */

class TicTacToe implements View.OnClickListener{

    private final int WIN_COINT = 5;
    private final int BOARD_SIZE = 7;
    private final char USER_CHAR = 'X';
    private final char COMP_CHAR = 'O';
    private final char EMPTY_CELL_CHAR = '.';
    private final char[][] board;
    private Movecatcher movecatcher;
    /**
     * We assume the board is square, so we use only one dimension.
     */


    private int userMoves;
    private int compMoves;
    private int curX;
    private int curY;
    private boolean gameOver;


    TicTacToe( Movecatcher movecatcher) {

        // Initialize count of moves;
        userMoves = 0;
        compMoves = 0;
        gameOver = false;
        board = new char[BOARD_SIZE][BOARD_SIZE];
        initBoard();
        this.movecatcher = movecatcher;
        //printBoard();

    }

    public Boolean go(int a, int b) {


        userMove(a, b);
        printBoard();
        if (checkWin(USER_CHAR) != -1)
            gameOver = true;

        System.out.println("----------");
        if (!gameOver) {
            compMove();
            printBoard();
            if (checkWin(COMP_CHAR) != -1)
                gameOver = true;
        }


        return gameOver;

    }

    private void initBoard() {
        for (int i = 0; i < BOARD_SIZE; ++i)
            for (int j = 0; j < BOARD_SIZE; ++j)
                board[i][j] = EMPTY_CELL_CHAR;
    }

    private void printBoard() {

        System.out.print(".");
        for (int i = 1; i <= BOARD_SIZE; ++i)
            System.out.print(" " + i);
        System.out.println();
        for (int i = 0; i < BOARD_SIZE; ++i) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < BOARD_SIZE; ++j)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
        System.out.print(".");
        for (int i = 1; i <= BOARD_SIZE; ++i)
            System.out.print(" " + i);
        System.out.println();
        System.out.println("curX, curY : " + (curX + 1) + " | " + (curY + 1));
    }

    private void userMove(int a, int b) {
        Scanner sc = new Scanner(System.in);
        curY = a;
        curX = b;
        board[curY][curX] = USER_CHAR;
        userMoves++;
    }

    private void compMove() {
        double maxMove, curMove;
        int maxX, maxY;
        maxMove = 0;
        maxX = maxY = 0;
        compMoves++;
        for (int i = 0; i < BOARD_SIZE; ++i)
            for (int j = 0; j < BOARD_SIZE; ++j) {
                if (board[i][j] != EMPTY_CELL_CHAR) continue;
                if (maxMove <= (curMove = moveWeigth(j, i, COMP_CHAR))) {
                    maxMove = curMove;
                    // Check here X Y assign for i j ? Debug
                    maxX = j;
                    maxY = i;

                }
            }

        for (int i = 0; i < BOARD_SIZE; ++i)
            for (int j = 0; j < BOARD_SIZE; ++j) {
                if (board[i][j] != EMPTY_CELL_CHAR) continue;
                if (maxMove < (curMove = moveWeigth(j, i, USER_CHAR))) {
                    maxMove = curMove;
                    // Check here X Y assign for i j ? Debug
                    maxX = j;
                    maxY = i;

                }
            }

        board[maxY][maxX] = COMP_CHAR;
        curY = maxY;
        curX = maxX;
    }

    private int checkWin(char whoisChar) {
        if (userMoves + compMoves == BOARD_SIZE * BOARD_SIZE) {
            System.out.println("Withdraw");
            return 0;
        }
        int vector[][] = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};
        int t_x, t_y;
        int n;
        for (int[] aVector : vector) {
            n = 1;

            t_x = curX;
            t_y = curY;
            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (board[t_y][t_x] != whoisChar) break;
                if (t_x != curX || t_y != curY) ++n;
                t_x += aVector[0];
                t_y += aVector[1];
            }

            t_x = curX;
            t_y = curY;

            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (board[t_y][t_x] != whoisChar) break;
                if (t_x != curX || t_y != curY) ++n;
                t_x -= aVector[0];
                t_y -= aVector[1];
            }
            if (n >= WIN_COINT) {
                System.out.println("User " + whoisChar + " wins! ");
                return 1;
            }
        }
        return -1;
    }

    private double moveWeigth(int a, int b, char moveChar) {
        double moveWeigth, curWeigth = 0.0;
        int n;
        int t_x, t_y;


        int vector[][] = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};

        for (int[] aVector : vector) {
            // Compare line finishing
            t_x = a;
            t_y = b;

            n = 1;
            moveWeigth = 0.0;
            int ms = linelength(a, b, moveChar, aVector) ;
            if( ms < WIN_COINT ) continue ;

            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (t_x != a || t_y != b)
                    if (board[t_y][t_x] != moveChar) {
                        if (board[t_y][t_x] != EMPTY_CELL_CHAR) moveWeigth -= 35;
                        break;
                    }
                    else ++n;
                moveWeigth += 100;
                t_x += aVector[0];
                t_y += aVector[1];
                if (t_x < 0 || t_x == BOARD_SIZE || t_y < 0 || t_y == BOARD_SIZE) moveWeigth -= 30;
            }

            t_x = a;
            t_y = b;
            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (t_x != a || t_y != b)
                    if (board[t_y][t_x] != moveChar) {
                        if (board[t_y][t_x] != EMPTY_CELL_CHAR) moveWeigth -= 35;
                        break;
                    }
                    else ++n;
                moveWeigth += 100;
                t_x -= aVector[0];
                t_y -= aVector[1];
                if (t_x < 0 || t_x == BOARD_SIZE || t_y < 0 || t_y == BOARD_SIZE) moveWeigth -= 30;
            }

            if (n >= WIN_COINT && moveChar == COMP_CHAR) return 5000;
            if (n >= WIN_COINT && moveChar == USER_CHAR) return 2000;

            if (curWeigth < moveWeigth) curWeigth = moveWeigth;

        }
        if( a == 0 || a == BOARD_SIZE - 1|| b == 0 || b == BOARD_SIZE - 1) curWeigth -= 50;
        return curWeigth;
    }

    private int linelength(int a, int b, char moveChar, int[] aVector ){

        int t_x;
        int t_y;


        int n = 1;

        for( int k = -1 ; k < 2 ; k+=2 ) {
            t_x = a;
            t_y = b;
            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (board[t_y][t_x] != moveChar && board[t_y][t_x] != EMPTY_CELL_CHAR) break;
                if (t_x != a || t_y != b) ++n;

                t_x += aVector[0] * k;
                t_y += aVector[1] * k;
            }
        }
        return n;
    }

    @Override
    public void onClick(View view) {
        if( gameOver){
            gameOver = false;
            initBoard();
            curY = 0 ;
            curX = 0 ;
            userMoves = 0 ;
            compMoves = 0 ;
            movecatcher.clearButtons();
            return;
        }
        myButton b = (myButton) view;
        if( !b.getText().equals(" ") ) return ;
        b.setText("X");
        curY = b.getA();
        curX = b.getB();
        board[curY][curX] = USER_CHAR;
        //Toast.makeText(b.getContext(), "User move number: " + (++userMoves), Toast.LENGTH_SHORT).show();
        if (checkWin(USER_CHAR) != -1) {
            gameOver = true;
            Toast.makeText(view.getContext(),"you win!", Toast.LENGTH_LONG).show();
        }
       // System.out.println("----------");
        if (!gameOver) {
            compMove();
            Toast.makeText(view.getContext(), "Move " + (curX+1) + ", " + (curY+1), Toast.LENGTH_SHORT).show();
            movecatcher.putMove(curY, curX);
            if (checkWin(COMP_CHAR) != -1) {
                gameOver = true;
                Toast.makeText(view.getContext(),"I win!", Toast.LENGTH_LONG).show();
            }
        }

    }
}

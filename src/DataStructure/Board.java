package DataStructure;
// package Board;
import java.util.*;

public class Board {
    private int N, M;
    private char[][] board;

    public Board(int N, int M) {
        this.N = N;
        this.M = M;
        this.board = new char[N][M];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
    }
    public char getCell(int i, int j) {
        return board[i][j];
    }
    public int getN() { return this.N; }
    public int getM() { return this.M; }

    public void setCell (int i, int j, char c) {
        board[i][j] = c;
    }
    public boolean isOutOfBounds(int x, int y) {
        if (x < 0 || y < 0 || x >= N || y >= M) {
            return true;
        }
        return false;
    }

    public boolean canPlace(Block block, int startX, int startY) {
        try {
            for (int i = 0; i < block.getHeight(); i++) {
                for (int j = 0; j < block.getWidth(); j++) {
                    if (block.getShape()[i][j] != '.') {
                        if (startX + i >= N || startY + j >= M || board[startX + i][startY + j] != '.') {
                            return false;
                        }
                        if (board[startX + i][startY + j] == '*' && block.getShape()[i][j] != '*') {
                            System.out.println("Error: Cannot place block on '*' cell.");
                            return false;
                        }                
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public void placeBlock(Block block, int startX, int startY) {
        for (int i = 0; i < block.getHeight(); i++) {
            for (int j = 0; j < block.getWidth(); j++) {
                if (block.getShape()[i][j] != '.') {
                    board[startX + i][startY + j] = block.getId();
                }
            }
        }
    }

    public void removeBlock(Block block, int startX, int startY) {
        for (int i = 0; i < block.getHeight(); i++) {
            for (int j = 0; j < block.getWidth(); j++) {
                if (block.getShape()[i][j] != '.') {
                    board[startX + i][startY + j] = '.';
                }
            }
        }
    }

    public void displayBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public void setGrid(char[][] grid) {
        this.board = grid;
    }
}

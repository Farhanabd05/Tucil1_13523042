package Solver;

import java.util.List;

import DataStructure.Block;
import DataStructure.Board;

public class Solver {
    private Board board;
    private List<Block> blocks;
    private int iterations;

    public Solver(Board board, List<Block> blocks) {
        this.board = board;
        this.blocks = blocks;
        this.iterations = 0;
    }

    public boolean solve(int index) {
        if (index == blocks.size()) {
            return true; 
        }

        Block block = blocks.get(index);
        List<Block> transformations = block.getAllTransformations(); 

        for (Block transformedBlock : transformations) {
            for (int row = 0; row <= board.getN() - block.getHeight(); row++) {
                for (int col = 0; col <= board.getM() - block.getWidth(); col++) {
                    iterations++;
                    if (board.canPlace(transformedBlock, row, col)) {
                        board.placeBlock(transformedBlock, row, col);
                        if (solve(index + 1)) {
                            return true; // Solution found
                        }
                        // System.out.println("Backtracking.");board.displayBoard();
                        board.removeBlock(transformedBlock, row, col); // Backtrack
                    }
                }
            }
        }
        return false;
    }

    public int getIterations() {
        return iterations;
    }
}
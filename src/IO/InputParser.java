package IO;

import java.io.*;
import java.util.*;

import DataStructure.Block;
import DataStructure.Board;

public class InputParser {
    private int N, M, P;
    private String caseType;
    private List<List<String>> blocks;
    private Board board;

    public InputParser() {
        blocks = new ArrayList<>();
        board = new Board(N, M);
    }

    public void parseInput(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String[] dimensions = br.readLine().split(" ");
        if (dimensions.length < 3) {
            br.close();
            throw new IllegalArgumentException("Error: Dimensi papan tidak ditemukan dalam file atau tidak lengkap.");
        }
        try {
            N = Integer.parseInt(dimensions[0]);
            M = Integer.parseInt(dimensions[1]);
            P = Integer.parseInt(dimensions[2]);
        } catch (NumberFormatException e) {
            br.close();
            throw new IllegalArgumentException("Error: Nilai N, M, atau P tidak ditemukan dalam file atau bukan bilangan bulat.");
        }

        if (P > 26) {
            br.close();
            throw new IllegalArgumentException("Error: Jumlah blok (" + P + ") melebihi batas maksimum (26).");
        }
        caseType = br.readLine().replace("\r", "").replace("\0", "");
        if (!caseType.equals("DEFAULT") && !caseType.equals("CUSTOM")) {
            br.close();
            throw new IllegalArgumentException("Error: Case type tidak valid.");
        }

        Board board = new Board(N, M);
        if (caseType.equals("CUSTOM")) {
            char[][] initialGrid = new char[N][M];
            for (int i = 0; i < N; i++) {
                String line = br.readLine().replace("\r", "").replace("\0", "");
                for (int j = 0; j < M; j++) {
                    initialGrid[i][j] = (line.charAt(j) == '.') ? '*' : '.';
                }
            }

            board.setGrid(initialGrid);
        }
        this.board = board;

        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            line = line.replace("\r", "").replace("\0", "");
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        br.close();

        if (lines.isEmpty()) {
            throw new IOException("Error: Tidak ada data blok dalam file.");
        }

        char currentBlockId = getFirstValidChar(lines.get(0));
        List<String> currentBlock = new ArrayList<>();

        for (String l : lines) {
            l = l.replace("\r", "").replace("\0", "");
            if (l.isEmpty()) continue;

            for (int i = 0; i < l.length(); i++) {
                if (l.charAt(i) != currentBlockId && l.charAt(i) != ' ') {
                    if (!currentBlock.isEmpty()) {
                        blocks.add(new ArrayList<>(currentBlock));
                        currentBlock.clear();
                    }
                    currentBlockId = l.charAt(i);
                }
            }
            currentBlock.add(l); 
        }
        if (!currentBlock.isEmpty()) {
            blocks.add(currentBlock);
        }
        if (blocks.size() != P) {
            throw new IOException("Jumlah blok yang terbaca tidak sesuai dengan P");
        }
    }

    private char getFirstValidChar(String str) {
        for (char c : str.toCharArray()) {
            if (c != ' ' && c != '\n' && c != '\r' && c != '\0') {
                return c;
            }
        }
        throw new RuntimeException("Tidak dapat menemukan karakter blok yang valid.");
    }

    public int getN() { return N; }
    public int getM() { return M; }
    public int getP() { return P; }
    public String getCaseType() { return caseType; }
    public List<List<String>> getBlocks() { return blocks; }

    public Board getBoard() {
        return board;
    }

    public List<Block> getBlockObjects() {
        List<Block> blockObjects = new ArrayList<>();
        for (List<String> blockShape : blocks) {
            int height = blockShape.size();
            int max_width = blockShape.stream().mapToInt(String::length).max().orElse(0);
            char[][] shape = new char[height][max_width];
            char blockId = getBlockId(blockShape);
    
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < max_width; j++) {
                    if (j < blockShape.get(i).length() && blockShape.get(i).charAt(j) == ' ') {
                        shape[i][j] = '.';
                    }
                    else if (j < blockShape.get(i).length() && blockShape.get(i).charAt(j) == blockId) {
                        shape[i][j] = blockShape.get(i).charAt(j);
                    } else {
                        shape[i][j] = '.';
                    }
                }
            }
    
            blockObjects.add(new Block(blockId, shape));
        }
        return blockObjects;
    }
    
    private char getBlockId(List<String> blockShape) {
        for (String row : blockShape) {
            for (char c : row.toCharArray()) {
                if (c != ' ' && c != '.') {
                    return c;
                }
            }
        }
        throw new RuntimeException("Tidak dapat menemukan karakter blok");
    }
    
}

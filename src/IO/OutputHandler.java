package IO;
import javax.imageio.ImageIO;

import DataStructure.Board;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OutputHandler {
    // ANSI color codes for terminal output
    private static final String RESET = "\u001B[0m";
    private static final Map<Character, String> BLOCK_COLORS = new HashMap<>();
    private static final Map<Character, Color> IMAGE_COLORS = new HashMap<>();
    private static final int CELL_SIZE = 50; // Size of each cell in pixels
    
    static {
        // Initialize terminal colors (ANSI escape codes)
        BLOCK_COLORS.put('A', "\u001B[31m"); 
        BLOCK_COLORS.put('B', "\u001B[34m"); 
        BLOCK_COLORS.put('C', "\u001B[32m"); 
        BLOCK_COLORS.put('D', "\u001B[35m"); 
        BLOCK_COLORS.put('E', "\u001B[33m"); 
        BLOCK_COLORS.put('F', "\u001B[36m"); 
        BLOCK_COLORS.put('G', "\u001B[95m"); 
        BLOCK_COLORS.put('H', "\u001B[94m"); 
        BLOCK_COLORS.put('I', "\u001B[92m"); 
        BLOCK_COLORS.put('J', "\u001B[93m"); 
        BLOCK_COLORS.put('K', "\u001B[91m"); 
        BLOCK_COLORS.put('L', "\u001B[96m"); 
        BLOCK_COLORS.put('M', "\u001B[90m"); 
        BLOCK_COLORS.put('N', "\u001B[97m"); 
        BLOCK_COLORS.put('O', "\u001B[37m"); 
        BLOCK_COLORS.put('P', "\u001B[30m"); 
        BLOCK_COLORS.put('Q', "\u001B[38;5;208m"); 
        BLOCK_COLORS.put('R', "\u001B[38;5;202m"); 
        BLOCK_COLORS.put('S', "\u001B[38;5;129m"); 
        BLOCK_COLORS.put('T', "\u001B[38;5;93m"); 
        BLOCK_COLORS.put('U', "\u001B[38;5;27m"); 
        BLOCK_COLORS.put('V', "\u001B[38;5;28m"); 
        BLOCK_COLORS.put('W', "\u001B[38;5;226m"); 
        BLOCK_COLORS.put('X', "\u001B[38;5;87m"); 
        BLOCK_COLORS.put('Y', "\u001B[38;5;141m"); 
        BLOCK_COLORS.put('Z', "\u001B[38;5;203m"); 
    
        
        IMAGE_COLORS.put('A', new Color(255, 0, 0));     
        IMAGE_COLORS.put('B', new Color(0, 0, 255));     
        IMAGE_COLORS.put('C', new Color(0, 255, 0));     
        IMAGE_COLORS.put('D', new Color(128, 0, 128));   
        IMAGE_COLORS.put('E', new Color(255, 255, 0));   
        IMAGE_COLORS.put('F', new Color(0, 255, 255));   
        IMAGE_COLORS.put('G', new Color(255, 192, 203)); 
        IMAGE_COLORS.put('H', new Color(173, 216, 230)); 
        IMAGE_COLORS.put('I', new Color(144, 238, 144)); 
        IMAGE_COLORS.put('J', new Color(255, 228, 181)); 
        IMAGE_COLORS.put('K', new Color(220, 20, 60));   
        IMAGE_COLORS.put('L', new Color(70, 130, 180));  
        IMAGE_COLORS.put('M', new Color(47, 79, 79));    
        IMAGE_COLORS.put('N', new Color(245, 245, 245)); 
        IMAGE_COLORS.put('O', new Color(169, 169, 169)); 
        IMAGE_COLORS.put('P', new Color(0, 0, 0));       
        IMAGE_COLORS.put('Q', new Color(255, 140, 0));   
        IMAGE_COLORS.put('R', new Color(255, 69, 0));    
        IMAGE_COLORS.put('S', new Color(255, 105, 180)); 
        IMAGE_COLORS.put('T', new Color(148, 0, 211));   
        IMAGE_COLORS.put('U', new Color(25, 25, 112));   
        IMAGE_COLORS.put('V', new Color(34, 139, 34));   
        IMAGE_COLORS.put('W', new Color(255, 255, 102)); 
        IMAGE_COLORS.put('X', new Color(0, 206, 209));   
        IMAGE_COLORS.put('Y', new Color(216, 191, 216)); 
        IMAGE_COLORS.put('Z', new Color(250, 128, 114)); 
    }
    

    public static void displayColoredBoard(Board board) {
        for (int i = 0; i < board.getN(); i++) {
            for (int j = 0; j < board.getM(); j++) {
                char cell = board.getCell(i, j);
                if (cell != '.') {
                    System.out.print(BLOCK_COLORS.getOrDefault(cell, RESET) + cell + " " + RESET);
                } else {
                    System.out.print(cell + " ");
                }
            }
            System.out.println();
        }
    }

    public static void saveBoardAsImage(Board board, String filePath, long time, long cases) throws IOException {
        int width = board.getM() * CELL_SIZE;
        int height = board.getN() * CELL_SIZE + 100;
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        for (int i = 0; i < board.getN(); i++) {
            for (int j = 0; j < board.getM(); j++) {
                char cell = board.getCell(i, j);
                if (cell != '.') {
                    g2d.setColor(IMAGE_COLORS.getOrDefault(cell, Color.GRAY));
                    g2d.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                
                g2d.setColor(Color.BLACK);
                g2d.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        
        g2d.setFont(new Font("Arial", Font.BOLD, CELL_SIZE/2));
        for (int i = 0; i < board.getN(); i++) {
            for (int j = 0; j < board.getM(); j++) {
                char cell = board.getCell(i, j);
                if (cell != '.') {
                    g2d.setColor(Color.BLACK);
                    String label = String.valueOf(cell);
                    FontMetrics metrics = g2d.getFontMetrics();
                    int x = j * CELL_SIZE + (CELL_SIZE - metrics.stringWidth(label)) / 2;
                    int y = i * CELL_SIZE + ((CELL_SIZE + metrics.getHeight()) / 2) - metrics.getDescent();
                    g2d.drawString(label, x, y);
                }
            }
        }
        
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(Color.BLACK);
        String timeLabel = "Time: " + time + " ms";
        String iterationsLabel = "Iterations: " + cases;
        g2d.drawString(timeLabel, 10, board.getN() * CELL_SIZE + 30);
        g2d.drawString(iterationsLabel, 10, board.getN() * CELL_SIZE + 60);
        
        g2d.dispose();
        
        File outputFile = new File(filePath);
        ImageIO.write(image, "png", outputFile);
        System.out.println("Image saved to " + filePath);
    }

    public static void saveBoardAsText(Board board, String filePath, long time, long cases) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (int i = 0; i < board.getN(); i++) {
                for (int j = 0; j < board.getM(); j++) {
                    char cell = board.getCell(i, j);
                    writer.write(cell + " ");
                }
                writer.write("\n");
            }
            writer.write("Time execution: " + time + " ms\n");
            writer.write("Iterations: " + cases + "\n\n");
        }
        System.out.println("Text file saved to " + filePath);
    }
}
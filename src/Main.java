import java.io.*;
import java.util.*;

import DataStructure.*;
import IO.*;
import Solver.*;
public class Main {
    public static void main(String[] args) {
        InputParser parser = new InputParser();
        try {
            String currentDir = System.getProperty("user.dir");
            String testPath = currentDir.substring(0, currentDir.lastIndexOf(File.separator)) + File.separator + "test" + File.separator + "testcase"; 
            Scanner scanner = new Scanner(System.in);
            System.out.println("Masukkan nama file: ");
            String fileName = scanner.nextLine();
            String filePath = testPath + File.separator + fileName + ".txt";

            parser.parseInput(filePath);
            Board board = new Board(parser.getN(), parser.getM());
            if (parser.getCaseType().equals("CUSTOM")) {
                board = parser.getBoard();
            }
            List<Block> blocks = parser.getBlockObjects();

            System.out.println("Starting IQ Puzzler Pro Solver");
            long startTime = System.currentTimeMillis();
            Solver solver = new Solver(board, blocks);
            
            if (!solver.solve(0)) {
                System.out.println("No solution found.");
            } else {
                System.out.println("\nSolusi ditemukan:");
                OutputHandler.displayColoredBoard(board);
                
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                System.out.println("\nWaktu pencarian: " + executionTime + " ms");
                System.out.println("Banyak kasus yang ditinjau: " + solver.getIterations());
                
                System.out.print("Apakah anda ingin menyimpan solusi? (ya/tidak) ");
                String response = scanner.nextLine().trim().toLowerCase();
                
                if (response.equals("ya")) {
                    System.out.print("Apa format file yang anda pilih? (image/text) ");
                    String response2 = scanner.nextLine().trim().toLowerCase();
                    if (response2.equals("image")) {
                        String filename = "solution_" + executionTime+ "_" + solver.getIterations() + ".png";
                        OutputHandler.saveBoardAsImage(board, filename, executionTime, solver.getIterations());   
                    } else if (response2.equals("text")) {
                        String filename = "solution.txt";
                        OutputHandler.saveBoardAsText(board, filename, executionTime, solver.getIterations());
                    } else {
                        System.out.println("Format file tidak valid. Silakan coba lagi.");
                    }
                }
                scanner.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
package DataStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Block {
    private char id;
    private char[][] shape;
    
    public Block(char id, char[][] shape) {
        this.id = id;
        this.shape = shape;
        // System.out.println("Block initialized with ID: " + id);
    }

    public char getId() { return id; }
    public char[][] getShape() { return shape; }
    public int getHeight() { return shape.length; }
    public int getWidth() { return shape[0].length; }

    public Block rotate() {
        // System.out.println("Rotating block " + id);
        int rows = shape.length, cols = shape[0].length;
        char[][] rotated = new char[cols][rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                rotated[j][rows - 1 - i] = shape[i][j];
        return new Block(id, rotated);
    }

    public Block mirror() {
        // System.out.println("Mirroring block " + id);
        int rows = shape.length, cols = shape[0].length;
        char[][] mirrored = new char[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                mirrored[i][cols - 1 - j] = shape[i][j];
        return new Block(id, mirrored);
    }

    public void displayBlock() {
        // System.out.println("Displaying block " + id);
        for (char[] row : shape) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public List<Block> getAllTransformations() {
        List<Block> transformations = new ArrayList<>();
        Block current = this;
        for (int i = 0; i < 4; i++) {
            transformations.add(current);
            Block mirrored = current.mirror();
            transformations.add(mirrored);
            current = current.rotate();
        }
        return transformations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Block{");
        sb.append("id='").append(id).append("', ");
        sb.append("shape=");
        for (char[] row : shape) {
            sb.append(Arrays.toString(row)).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length()); // hapus koma terakhir
        sb.append("}");
        return sb.toString();
    }
}
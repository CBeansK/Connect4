package core;

import java.util.ArrayList;

public class Column {
    private final int height;
    private final ArrayList<Character> column;
    private int lastTop;

    // creates a new column of specified height
    // could potentially create non-square boards with varying column sizes
    public Column(int height) {
        this.height = height;
        this.column = new ArrayList<>(height);

        // populate columns
        for (int i = 0; i < height; i++) {
            this.column.add(' ');
        }
        this.lastTop = 0;
    }

    // returns the column for proper encapsulation
    public ArrayList<Character> getColumn() {
        return this.column;
    }

    // Places a piece inside the column
    public boolean placePiece(char piece) {
        if (lastTop + 1 > height) {
            System.out.println("Cannot add a piece to full column.");
            return false;
        }

        column.set((height - 1) - lastTop, piece);
        this.lastTop++;
        return true;
    }

    public Character removePiece(){
        for(int i = 0; i < column.size(); i++){
            if(column.get(i) == ' '){
               continue;
            }
            Character temp = column.get(i);
            column.set(i, ' ');
            this.lastTop--;
            return temp;
        }

        return null;
    }
}

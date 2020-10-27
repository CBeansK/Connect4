package core;

import java.util.ArrayList;
import java.util.Objects;

public class Connect4 {
    private class Column{
        private final int height;
        private final ArrayList<Character> column;
        private int lastTop;

        // creates a new column of specified height
        // could potentially create non-square boards with varying column sizes
        public Column(int height){
            this.height = height;
            this.column = new ArrayList<>(height);

            // populate columns
            for(int i = 0; i < height; i++){
                this.column.add(' ');
            }
            this.lastTop = 0;
        }
        // returns the column for proper encapsulation
        public ArrayList<Character> getColumn(){
            return this.column;
        }

        // Places a piece inside the column
        public boolean placePiece(char piece){
            if (lastTop + 1 > height){
                System.out.println("Cannot add a piece to full column.");
                return false;
            }

            column.set((height - 1) - lastTop, piece);
            this.lastTop++;
            return true;
        }
    }

    private final Column[] board;

    /**
     *  @constructor    Connect4
     *
     *  Initializes a new Connect4 Object that creates a new board
     *  based on the size given.
     *
     *  @param size (int)   size of board (nxn rows,columns)
     */
    public Connect4(int size){
        this.board = new Column[size];

        // initialize columns
        for(int i = 0; i < size; i++){
            this.board[i] = new Column(size);
        }
    }
    /**
     *  @method checkForWin
     *
     *  Checks in 4 different directions (horizontal, vertical,
     *  forwards-diagonal, and backwards-diagonal to see if 4 pieces are aligned,
     *  starting from the last-placed piece.
     *
     * @param  col (int)    column of last-placed piece
     * @param  piece (char) id of last placed piece
     *
     * @return true         piece fulfills winning conditions
     * @return false        piece does not fulfill winning conditions
     */
    public boolean checkForWin(int col, char piece){
        return (horizontalWin(piece, col) ||
                verticalWin(piece, col) ||
                checkForwardsDiagonalWin(piece, col) ||
                checkBackwardsDiagonalWin(piece, col));
    }
    //TODO: refactor winning conditions to separate function
    public boolean placePiece(int col, char piece){
        if(board[col].placePiece(piece)){
            return true;
        }

        System.out.println("Failed to place piece");
        return false;
    }
    // spooooooky 100 untested lines of code
    // could do this faster
    // pick last piece, and do counts for (left-right), (up-down), (leftup, rightdown), (rightup, leftdown)
    /**
        @method     horizontalWin
        Finds the left-most occurrence of a piece in a row
        and iterates to the right to find if 4 pieces are adjacent

        @param  piece (char)  piece to check
        @param  row (int      row of last-placed piece
        @return true        4 pieces are horizontally aligned
                false       4 pieces are not horizontally aligned
     */
    private boolean horizontalWin(char piece, int row){
        // find current height
        int currentHeight = board[row].column.lastIndexOf(piece);

        // get left-most index
        int minIndex = getLeftMostIndex(piece, currentHeight, row);

        // left most piece
        char currentPiece = board[minIndex].column.get(currentHeight);
        assert(currentPiece == piece) : "Failed to get left-most piece";

        // iterate to find horizontal row of 4
        //TODO: could refactor this into a function to reuse?
        int counter = 1;
        while (counter < 4){
            // make sure we don't go out of bounds
            if (minIndex + counter >= board.length){
                return false;
            }
            // get next piece
            currentPiece = board[minIndex + counter].column.get(currentHeight);

            // check if next piece is equal to the piece we put
            if (currentPiece != piece){
                return false;
            } else {
                counter++;
            }
        }

        // if counter has reached 4, we know we're at the right piece
        // assert here for debugging
        assert(counter == 4) : "Failed to increment counter properly";
        return true;
    }

    private boolean verticalWin(char piece, int col){
        // get current column and height
        // current column is col
        // current height is the last piece placed in the column
        int currentHeight = board[col].getColumn().indexOf(piece);
        // get bottom-most index
        int bottomHeight = getBottomIndex(piece, currentHeight, col);
        // check pieces above

        int counter = 1;
        while(counter < 4){
            // make sure we don't go out of bounds
            if (bottomHeight - counter < 0){
                return false;
            }
            // get next piece
            char currentPiece = board[col].getColumn().get(bottomHeight - counter);

            // check if next piece is equal to the piece we put
            if (currentPiece != piece){
                return false;
            } else {
                counter++;
            }
        }

        assert (counter == 4) : "Failed to increment counter properly";
        return true;
    }

    private int getLeftMostIndex(char piece, int currentHeight, int rowIndex) {
        // track piece to the left
        // track row index
        char leftPiece = piece;
        while (leftPiece == piece && rowIndex > 0){
            // check if leftmost piece is the same
            leftPiece = board[rowIndex - 1].column.get(currentHeight);
            if(leftPiece == piece){
                //leftPiece = board[rowIndex - 1].column.get(currentHeight);
                rowIndex = rowIndex - 1;
            }
            // if it is, update pointers
            // if it isn't, we're done
        }
        return rowIndex;
    }

    private int getBottomIndex(char piece, int height, int currentCol){

        char bottomPiece = piece;
        while(bottomPiece == piece && height < board.length - 1){

            // get piece below current one
            bottomPiece = board[currentCol].getColumn().get(height + 1);

            // increment height if we are good to keep moving
            if (bottomPiece == piece){
                height += 1;
            }
        }

        return height;
    }

    private boolean checkForwardsDiagonalWin(char piece, int col){
        // get current height in col
        int currentHeight = board[col].getColumn().indexOf(piece);
        // get bottom-left diagonal piece
        int[] minIndices = getBottomDiagonal(piece, col, currentHeight);
        int minCol = minIndices[0];
        int minRow = minIndices[1];


        // do counter to check pieces
        int counter = 1;
        while (counter < 4){
            if ((minCol + 1) > board.length - 1 || minRow - 1 < 0) return false;
            char nextPiece = board[minCol + counter].getColumn().get(minRow - counter);

            if (nextPiece == piece){
                counter++;
            } else {
                return false;
            }
        }
        // check board[col + 1][row - 1]

        return true;
    }

    private boolean checkBackwardsDiagonalWin(char piece, int col){
        // get current height in col
        int currentHeight = board[col].getColumn().indexOf(piece);
        // get bottom-left diagonal piece
        int[] minIndices = getBottomRightDiagonal(piece, col, currentHeight);
        int minCol = minIndices[0];
        int minRow = minIndices[1];


        // do counter to check pieces
        int counter = 1;
        while (counter < 4){
            if ((minCol + counter) > board.length - 1 || minRow + counter > board.length - 1) return false;
            char nextPiece = board[minCol + counter].getColumn().get(minRow + counter);

            if (nextPiece == piece){
                counter++;
            } else {
                return false;
            }
        }
        // check board[col + 1][row - 1]

        return true;
    }



    private int[] getBottomDiagonal(char piece, int col, int currentHeight) {
        // pointer to current piece
        char nextPiece = piece;

        // pointers to last-placed piece
        int minColIndex = col;
        int minRowIndex = currentHeight;


        while(nextPiece == piece && (minColIndex > 0 && minRowIndex < board.length - 1)){

            // get the piece to the bottom left
            nextPiece = board[minColIndex - 1].getColumn().get(minRowIndex + 1);

            // if its the same we can iterate to that piece and check again
            if (nextPiece == piece){
                minColIndex--;
                minRowIndex++;
            }
        }
        return new int[]{minColIndex, minRowIndex};
    }

    private int[] getBottomRightDiagonal(char piece, int col, int currentHeight) {
        // get the next (col - 1) (row - 1) top left diagonal piece
        char nextPiece = piece;

        // start at current index
        int minColIndex = col;
        int minRowIndex = currentHeight;

        // while we're in bounds
        while(nextPiece == piece && (minColIndex > 0 && minRowIndex > 0)){

            // get the top left diagonal piece
            nextPiece = board[minColIndex - 1].getColumn().get(minRowIndex - 1);

            // if its the same then set the pointers to it and check until done
            if (nextPiece == piece){

                // update current ptr
                minColIndex--;
                minRowIndex--;
            }
        }
        return new int[]{minColIndex, minRowIndex};
    }

    /**
     *  @method printBoard
     *
     *  Prints the current state of the board.
     */
    public void printBoard(){
        // print 0th element of each column
        // then print 1st element of each column
        // etc etc...
        for(int i = 0; i < board.length; i++){
            for (Column column : board) {
                System.out.printf("| %c |", column.column.get(i));
            }
            System.out.println();
        }
    }
}

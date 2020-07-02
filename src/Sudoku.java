import java.util.concurrent.ThreadLocalRandom;

/**
 * Name: Danny Nguyen
 * Description: A simple Sudoku game
 * Date: July 2nd, 2020
 * Revision: 1.0
 */

public class Sudoku {
    private static final int MAX_ROWCOL = 9;
    private static final int SUB_BOARD = 3;
    private static int[][] board;
    private static int[][] hiddenBoard;
    private static int[][] unsolvedBoard;
    private final GUI gui;

    public Sudoku(){
        gui = new GUI();
        board = new int[MAX_ROWCOL][MAX_ROWCOL];
        hiddenBoard = cloneBoard(board);
        unsolvedBoard = cloneBoard(board);
    }

    /**
     * A helper method that returns the corresponding board to the input
     * @param type - the chosen board
     * @return - the chosen board
     */
    public static int[][] getBoard(int type){
        return switch (type) {
            case 1 -> board;
            case 2 -> hiddenBoard;
            case 3 -> unsolvedBoard;
            default -> null;
        };
    }

    /**
     * A method that returns an entry of a board based on the given input.
     * @param board - the chosen board/2d array
     * @param row - row number
     * @param col - col number
     * @return an entry of the chosen board/2d array
     */
    public static int getNum(int[][] board, int row , int col){ return board[row][col]; }

    /**
     * A helper method that generates a random integer between a range specified by the input
     * @param min - the minimum integer
     * @param max - th maximum integer
     * @return - a random integer between min and max
     */
    private static int randomGenerator(int min, int max){ return ThreadLocalRandom.current().nextInt(min, max); }

    /**
     * A helper method that writes numbers on all of the left to right diagonal subboard in a sudoku game
     * @param board - the chosen board/2d array
     */
    private static void setDiagonal(int[][] board){
        for(int i=0; i<MAX_ROWCOL; i+=SUB_BOARD){
            setSubBoard(board, i, i);
        }
    }

    /**
     * Methods that set the selected boards (board, hidden and unsolved) to the chosen board
     * @param board1 - the chosen board/2d array
     */
    public static void setBoard(int[][] board1){
        board = cloneBoard(board1);
    }
    public static void setHiddenBoard(int[][] board){
        hiddenBoard = cloneBoard(board);
    }
    public static void setUnsolvedBoard(int[][] board){
        unsolvedBoard = cloneBoard(board);
    }

    /**
     * Method that writes in random integer to a subBoard in the chosen board/2d array.
     * @param board - the chosen board/2d array
     * @param rowStart - the starting row of the subBoard
     * @param colStart - the starting col of the subBoard
     */
    private static void setSubBoard(int[][] board, int rowStart, int colStart){
        int num;
        for(int i=0; i<SUB_BOARD; i++){
            for(int j=0; j<SUB_BOARD; j++){
                do{
                    num = randomGenerator(1,MAX_ROWCOL+1);
                }while(!checkValid(board, rowStart, colStart, num));
                board[rowStart+i][colStart+j] = num;
            }
        }
    }

    /**
     * This method fills in random integer to the chosen board/2d array
     * @param board - the chosen board/2d array
     */
    public static void randomBoard(int[][] board){
        setDiagonal(board);
        solveBoard(board);
        hiddenBoard = cloneBoard(board);                //hidden board contains the solution
        int row, col;
        for(int times=0; times<(MAX_ROWCOL*MAX_ROWCOL)- randomGenerator(MAX_ROWCOL, MAX_ROWCOL*3); times++){
            row = randomGenerator(0, MAX_ROWCOL);       //delete a random entry on the board to create a new game
            col = randomGenerator(0, MAX_ROWCOL);
            if(board[row][col]==0){
                times--;
            }else {
                board[row][col] = 0;
            }
        }
        unsolvedBoard = cloneBoard(board);              //unsolved board contains the original board that has not been solved
    }

    /**
     * This helper method copies board to another board an return the copy
     * @param board - the chosen board/2d array
     * @return - the copy board
     */
    private static int[][] cloneBoard(int[][] board){
        int[][] temp = new int[board.length][board.length];
        for (int row = 0; row<board.length; row++){
            System.arraycopy(board[row], 0, temp[row], 0, board.length);
        }
        return temp;
    }

    /**
     * This method reset the board and hidden board to a new ones with not null entries.
     * Unsolved board is not reset since players might want to redo the game after they delete the old one.
     * The unsolved board will be renew when players select new game.
     */
    public static void resetBoard(){
        board = new int[MAX_ROWCOL][MAX_ROWCOL];
        hiddenBoard = cloneBoard(board);
    }

    /**
     * This method solves the current board using backtracking that try out all possibilities. Return yes if the board
     * is solvable, no otherwise.
     * @param board - the chosen board/2d array
     * @return - yes if the board is solvable, no otherwise.
     */
    public static boolean solveBoard(int[][] board){
        for(int row=0; row<MAX_ROWCOL; row++){
            for(int col=0; col<MAX_ROWCOL; col++){
                if(board[row][col]==0){
                    for(int num=1; num<=MAX_ROWCOL; num++){
                        if(checkValid(board, row, col, num)){
                            board[row][col] = num;
                            if(solveBoard(board)){
                                return true;
                            }else{
                                board[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method check if putting an integer in a specified entry is safe or not.
     * @param board - the chosen board/2d array
     * @param row - the chosen row
     * @param col - the chose col
     * @param num - the number that will be put in
     * @return - yes if safe, no otherwise.
     */
    public static boolean checkValid(int[][] board, int row, int col, int num){
        for(int i=0; i<MAX_ROWCOL; i++){
            if(board[row][i]==num){
                return false;
            }
        }

        for(int i=0; i<MAX_ROWCOL; i++){
            if(board[i][col]==num){
                return false;
            }
        }

        int subColStart = col - col%SUB_BOARD;
        int subRowStart = row - row%SUB_BOARD;

        for(int i=subRowStart; i<subRowStart+SUB_BOARD; i++){
            for(int j=subColStart; j<subColStart+SUB_BOARD; j++){
                if(board[i][j]==num){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main (String[] args){
        Sudoku a = new Sudoku();
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

/**
 * This class represent a gui for the sudoku board and other buttons for interaction
 */
public class GUI {
    private static final int MAX_ROWCOL = 9;
    private static final int SUB_BOARD = 3;
    private final JPanel theBoard = new JPanel();
    private JButton[][] theButtons;                                 //JButton array to access individual buttons easier
    private final JPanel numbers = new JPanel();
    private final JPanel buttons = new JPanel();
    private int currNum = 0;                                        //variable to keep track of which integer the play
                                                                    //selected
    private int currRow, currCol = -1;                              //variables to keep track of the current entry for hints

    public GUI(){
        setTheBoard();
        setButtons();
        setNumbers();

        JFrame frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(600,650);
        frame.getContentPane().setBackground(Color.red);
        frame.setResizable(false);

        frame.add(theBoard, BorderLayout.NORTH);
        frame.add(numbers, BorderLayout.CENTER);
        frame.add(buttons, BorderLayout.SOUTH);
    }

    /**
     * This method displays the board onto the buttons.
     * @param type - the chosen board/2d array
     */
    private void displayBoard(int type){
        for(int row=0; row<MAX_ROWCOL; row++){
            for(int col=0; col<MAX_ROWCOL; col++){
                if(Sudoku.getNum(Sudoku.getBoard(type), row, col) == 0){
                    theButtons[row][col].setText(null);
                }else {
                    theButtons[row][col].setText(String.valueOf(Sudoku.getNum(Sudoku.getBoard(type), row, col)));
                }
            }
        }
    }

    /**
     * This method setups buttons for interactions.
     */
    private void setButtons(){
        buttons.setLayout( new FlowLayout(FlowLayout.CENTER));
        JButton newG, solveG, hintG, deleteG, restartG;

        newG = new JButton("New Game");
        newG.addActionListener(e -> {
            deleteBoard();
            Sudoku.randomBoard(Sudoku.getBoard(1));
            displayBoard(1);
        });
        buttons.add(newG);

        solveG = new JButton("Solve Game");
        buttons.add(solveG);
        solveG.addActionListener(e -> {
            Sudoku.solveBoard(Sudoku.getBoard(1));
            displayBoard(1);
        });

        hintG = new JButton("Hint");
        hintG.addActionListener(e -> {
            if(Sudoku.solveBoard(Sudoku.getBoard(1))) {
                Sudoku.setHiddenBoard(Sudoku.getBoard(1));
                theButtons[currRow][currCol].setText(String.valueOf(Sudoku.getNum(Sudoku.getBoard(2), currRow, currCol)));

            }
        });
        buttons.add(hintG);

        deleteG = new JButton("Delete Board");
        deleteG.addActionListener(e -> deleteBoard());
        buttons.add(deleteG);

        restartG = new JButton("Restart Game");
        buttons.add(restartG);
        restartG.addActionListener(e -> {
            deleteBoard();
            displayBoard(1);
            displayBoard(3);
            Sudoku.setBoard(Sudoku.getBoard(3));
            Sudoku.solveBoard(Sudoku.getBoard(1));
            Sudoku.setHiddenBoard(Sudoku.getBoard(1));
        });
    }

    /**
     * This method deletes the number of the buttons and reset the currNum, currRow and currCol
     */
    private void deleteBoard(){
        Sudoku.resetBoard();
        for(int row=0; row<MAX_ROWCOL; row++){
            for(int col=0; col<MAX_ROWCOL; col++){
                theButtons[row][col].setText("");
            }
        }
        currNum = 0;
        currRow = -1;
        currCol = -1;
    }

    /**
     * This method setups the numbers buttons that act as option for players to choose.
     */
    private void setNumbers(){
        numbers.setLayout( new FlowLayout(FlowLayout.CENTER));
        for(int i=0; i<MAX_ROWCOL; i++){
            JButton numButtons = new JButton(String.valueOf(i+1));
            numButtons.addActionListener(e -> currNum = Integer.parseInt(numButtons.getText()));
            numbers.add(numButtons);
        }
    }

    /**
     * This method setups the buttons that act as a sudoku board
     */
    private void setTheBoard(){
        theButtons = new JButton[MAX_ROWCOL][MAX_ROWCOL];
        theBoard.setLayout(new GridLayout(MAX_ROWCOL,MAX_ROWCOL));
        for(int row=0; row<MAX_ROWCOL; row++){
            for (int col=0; col<MAX_ROWCOL; col++) {
                JButton a = new JButton();
                a.setPreferredSize(new Dimension(20,60));
                a.setFont(new Font("Arial",Font.PLAIN, 40));

                if(row==SUB_BOARD-1 || row==(SUB_BOARD*2)-1 ){
                    a.setBorder(BorderFactory.createMatteBorder(1,1,3,1, Color.BLACK));
                    if((row==col) || ((col+1)%SUB_BOARD==0 && (row+1==2*(col+1) || col+1==2*(row+1)))){
                        a.setBorder(BorderFactory.createMatteBorder(1,1,3,3, Color.BLACK));
                    }
                }else if (col==SUB_BOARD-1 || col==(SUB_BOARD*2)-1 ){
                    a.setBorder(BorderFactory.createMatteBorder(1,1,1,3, Color.BLACK));
                }else{

                    a.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK));
                }
                int staticRow, staticCol;
                staticRow = row;
                staticCol = col;
                theButtons[row][col] = a;
                theBoard.add(theButtons[row][col]);

                a.addActionListener(e -> {
                    if (currNum != 0 && Sudoku.checkValid(Sudoku.getBoard(1), staticRow, staticCol, currNum)) {
                        a.setText(String.valueOf(currNum));
                        currNum = 0;
                    }else if(currNum == 0 && a.getText() != null) {
                        currRow = staticRow;
                        currCol = staticCol;
                    }else{
                            a.setBackground(Color.RED);
                            new java.util.Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    a.setBackground(null);
                                }
                            }, 1000);
                        currRow = -1;
                        currCol = -1;
                    }
                });

            }
        }
    }
}

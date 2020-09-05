# Sudoku
A sudoku game with simple GUI that allows player to play new random game, insert new games and solve the current game.

![gui-pic](https://imgur.com/gallery/gAEZbYP)

##How to Play
In order to "write" down a number on the grid, players have to click on one of the number buttons first, then click on the chosen square to register their moves. 
Some assistances players can use to have a better experience:
- If the player's move is invalid (identical number in the same rowm, column, or subgrid), the square is highlighted in red for 5s and the move will not be registered.
- Player can alter their number by clicking another number option before choosing a square.

There are several option that players can ultilize during the game:
1. New Game button: generate a new Sudoku game in display it on the board. The old board is deleted.
2. Solve Game button: completely solve the current board and display the answer. (Check Structure for more details)
3. Hint button: press hint and another square to review the answer of that square. Currently, there isn't any limit.
4. Delete button: completely delete the current board.
5. Restart Game button: delete all of the moves of the board and allow players to re-play. If the board is deleted, the deleted board is restored.

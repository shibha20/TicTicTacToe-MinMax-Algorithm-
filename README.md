# TicTicTacToe-MinMax-Algorithm-

Rules for “TicTicTacToe”

TicTicTacToe is basically a variation of Tic-Tac-Toe, in that it is a 2D grid in which players alternate moves, placing X's and O's in vacant squares, with the goal of getting either three or four pieces in a row (horizontally, vertically, or diagonally). 
The major differences are:
•	The board is a 4 x 4 grid.
•	If either player gets four in a row, the game ends immediately and that player wins.
•	If all 16 squares are filled without either player getting four in a row, the winner is the player who gets the most sets of three pieces in a row.
For example, suppose the board looks like the following at the end: 
X	O	O	X
X	X	O	O
X	X	X	O
O	O	X	O
Then X would have 3 rows of 3 (1 horizontal, 1 vertical, 1 diagonal), and O would have 2 rows of 3 (1 diagonal and 1 vertical). Therefore, X would win.


My only goal was to provide a function called evaluate which returns a number between 0 and 1000. This number should be a heuristic evaluation of how good the given board is for ai (that is, the computer) It will be used by the 2-level MINMAX code to choose move of the computer. The better the heuristic used by this function, the more likely the overall program will be to win. This is why the search itself is rather shallow, as this will emphasize the quality of the heuristic over brute search. The main purpose of this problem is to design the best possible heuristic. 

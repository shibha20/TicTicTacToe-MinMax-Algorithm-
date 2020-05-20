
package tictictactoe;
import java.util.*;
/**
 *
 * @author jrsullins
 */
public class TicTicTacToe {

   /* 1. First the heuristic checks whether the tempboard ensures the victory of the player or ai 
      2. If not, then the heuristic chooses the board which prevents the player from winning if the player already has 3 consecutive moves in any rows 
      3. If not, Part i, ii and iii: This part of the heurisitc makes ai choose the moves which allows ai to prevent player from progressing if player has two 
         consecutive moves in any row, column or the main diagonal.  This part makes it it difficult for the player to win by having 4 consecutive of their moves in
        the same row, column or main diagonals. This part also contributes to reducing the number of three consecutive player moves 
         in any row/column/main diagonals, which is significant if there is a tie.
         Part iv: This part contributes to reducing the number of three consecutive player moves in ANY DIAGONALS other than the main diagonals, 
         which is significant if there is a tie. 
    */
    public static float evaluate(char[][] tempboard, char ai , char initialPlayer,char [][] initialboard){
        int row,col;
        int value =0; 
        char comp; 
        if (initialPlayer == 'X') comp = 'O'; else comp = 'X'; 
        
        boolean aiWin =winner(tempboard,comp);
        boolean playerWin = winner(tempboard,initialPlayer); 
        
        
        int numOf3PlayerMovesinARow =getScore(initialboard,initialPlayer);
                
        //if the board assures ai's win, choose it 
        if (aiWin==true){
            value =1000;
        }
        //if the board assures player's win, avoid it 
        else if (playerWin== true){
            value = 0;
        }
        /*if there are 3 consecutive moves of the player in a same row (i.e. if player is on the way to win the game),
        then call the function which chooses the board in which the ai obstructs the player from winning by putting ai move in the remaining slot*/
        else if (numOf3PlayerMovesinARow >=1){ 
            value = threeInARow(tempboard,comp,initialPlayer,initialboard);          
        }
        /*This is the main part of how the AI makes it difficult for the player to progress and win.*/
        else{
            
            /* Part i : Every time the player puts two of its move consecutive to each other in a row, 
            the ai obstructs player from creating a row with 3 consecutive moves by immediately adding its move next to the player's two consecutive moves */
            for (int i = 0; i < 4; i++) {
                if ( (tempboard[i][0] ==initialPlayer) &&  (tempboard[i] [1] == initialPlayer)  && (tempboard[i][2]==comp) && (initialboard[i][2] == ' ')){
                    value=1000; 
                    break;
                }else if ((tempboard[i][1] ==initialPlayer) &&  (tempboard[i] [2] == initialPlayer)  && (tempboard[i][3]==comp) && (initialboard[i][3] ==' ')){
                    value =1000; 
                    break; 
                }else if ((tempboard[i][3] ==initialPlayer) &&  (tempboard[i] [2] == initialPlayer)  && (tempboard[i][1]==comp) && (initialboard[i][1] == ' ')){
                    value =1000; 
                    break; 
                }else if((tempboard[i][2] ==initialPlayer) &&  (tempboard[i] [1] == initialPlayer)  && (tempboard[i][0]==comp) && (initialboard[i][0] == ' ')){
                    value =1000; 
                    break; 
                }
            } 
            /* Part ii : Every time the player puts two of its move consecutive to each other in a column, 
            the ai obstructs player from creating a column with 3 consecutive moves by immediately adding its move next to the player's two consecutive moves*/
            for (int i = 0; i < 4; i++) {
                    if ( (tempboard[0][i] ==initialPlayer) &&  (tempboard[1] [i] == initialPlayer)  && (tempboard[2][i]==comp) && (initialboard[2][i] == ' ')){
                        value=1000; 
                        break; 
                    }else if ((tempboard[1][i] ==initialPlayer) &&  (tempboard[2] [i] == initialPlayer)  && (tempboard[3][i]==comp) && (initialboard[3][i] ==' ')){
                        value =1000; 
                        break;
                    }else if ((tempboard[3][i] ==initialPlayer) &&  (tempboard[2] [i] == initialPlayer)  && (tempboard[1][i]==comp) && (initialboard[1][i] == ' ')){
                        value =1000; 
                        break;
                    }else if((tempboard[2][i] ==initialPlayer) &&  (tempboard[1] [i] == initialPlayer)  && (tempboard[0][1]==comp) && (initialboard[0][i] == ' ')){
                        value =1000; 
                        break;
                    }
            } 
        
            /* Part iii : Every time the player puts two of its move consecutive to each other in a diagonal, 
            the ai obstructs player from creating a diagonals with 3 consecutive moves by immediately adding its move next to the player's two consecutive moves */
            for (int i = 0; i < 4; i++) {
                if (i <2){
                    if ( (tempboard[i][i] ==initialPlayer) &&  (tempboard[i+1] [i+1] == initialPlayer)  && (tempboard[i+2][i+2]==comp) && (initialboard[i+2][i+2] == ' ') ){
                        value = 1000; 
                        break;
                    }
                }else if (i >=2){
                    if ( (tempboard[i][i] ==initialPlayer) &&  (tempboard[i-1] [i-1] == initialPlayer)  && (tempboard[i-2][i-2]==comp) && (initialboard[i-2][i-2] == ' ') ){
                        value = 1000; 
                        break;
                    }    
                }
            }

            if ( (tempboard[3][0] ==initialPlayer) &&  (tempboard[2] [1] == initialPlayer)  && (tempboard[1][2]==comp) && (initialboard[1][2] == ' ')){
                value=1000; 
           }else if ((tempboard[2][1] ==initialPlayer) &&  (tempboard[1] [2] == initialPlayer)  && (tempboard[0][3]==comp) && (initialboard[0][3] ==' ')){
                value =1000; 
            }else if ((tempboard[0][3] ==initialPlayer) &&  (tempboard[1] [2] == initialPlayer)  && (tempboard[2][1]==comp) && (initialboard[2][1] == ' ')){
                value =1000; 
            }else if((tempboard[2][1] ==initialPlayer) &&  (tempboard[1] [2] == initialPlayer)  && (tempboard[3][0]==comp) && (initialboard[3][0] == ' ')){
                value =1000; 
            }

            //Part iv : This part prohibits 3 consecutive player moves in any diagonal. This part is important because it helps the ai win in case of a tie. 
            for (row = 0; row < 2; row++)
                for (col = 0; col < 2; col++)
                    if (tempboard[row][col] == initialPlayer &&
                        tempboard[row+1][col+1] == initialPlayer &&
                        tempboard[row+2][col+2] == comp && initialboard[row+2][col+2] ==' ') {
                        value =1000; 
                        break; 
                    }  
            for (row = 0; row < 2; row++)
                for (col = 2; col < 4; col++)
                    if (tempboard[row][col] == initialPlayer &&
                        tempboard[row+1][col-1] == initialPlayer &&
                        tempboard[row+2][col-2] == comp && initialboard[row+2][col-2]==' ') {
                            value =1000;
                            break; 
                } 
               
        }
         
       return value;    
                
       
}   


/***************** END OF YOUR CODE ********************************/

    
    /**
     * Determines the player in question (X or 0) a winner by having 4 in a row.
     * @param board The game board
     * @param who Which character we are checking (ether 'X' or 'O')
     * @return true if who has 4 in a row, false otherwise
     */
    public static boolean winner(char[][] board, char who) {
        for (int i = 0; i < 4; i++) {
            if (board[i][0] == who && 
                board[i][1] == who &&
                board[i][2] == who &&
                board[i][3] == who) {
                //cout << "Win in row " << i << "\n";
                return true;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (board[0][i] == who && 
                board[1][i] == who &&
                board[2][i] == who &&
                board[3][i] == who) {            
                //cout << "Win in row " << i << "\n";
                return true;
            }   
        }
        if (board[0][0] == who && 
            board[1][1] == who &&
            board[2][2] == who &&
            board[3][3] == who) {
            // cout << "Win along main diagonal\n";
            return true;
            }
        
        if (board[0][3] == who && 
            board[1][2] == who &&
            board[2][1] == who &&
            board[3][0] == who) {
            // cout << "Win along other diagonal\n";
            return true;
            }
        return false;
    }


    /**
     * Exhaustively searches the current board to count the sets of 3 this character has in a row.
     * @param board The game board
     * @param who Which character we are checking (ether 'X' or 'O')
     * @return The number of 3 in a row that character has.
     */
    public static int getScore(char[][] board, char who) {
      int row, col;
      int X, O;
      int score = 0;

      /* check all rows */
      for (row = 0; row < 4; row++)
        for (col = 0; col < 2; col++)
          if (board[row][col] == who &&
              board[row][col+1] == who &&
              board[row][col+2] == who) score++;

      /* check all columns */
      for (row = 0; row < 2; row++)
        for (col = 0; col < 4; col++)
          if (board[row][col] == who &&
              board[row+1][col] == who &&
              board[row+2][col] == who) score++;

      /* check all diagonals */
      for (row = 0; row < 2; row++)
        for (col = 0; col < 2; col++)
          if (board[row][col] == who &&
              board[row+1][col+1] == who &&
              board[row+2][col+2] == who) score++;
      
      for (row = 0; row < 2; row++)
        for (col = 2; col < 4; col++)
          if (board[row][col] == who &&
              board[row+1][col-1] == who &&
              board[row+2][col-2] == who) score++;
      return score;
      }
  
    
    static final int MAXLEVEL = 2;
    
 
/* This is the main function for playing the game. It alternatively
   prompts the user for a move, and uses the minmax algorithm in 
   conjunction with the given evaluation function to determine the
   opposing move. This continues until the board is full. It returns
   the number scored by X minus the number scored by O. */

    public static boolean run(int[] scores, char who,char initialPlayer) { 
        int i, j;
        char current, other;
        int playerrow, playercol;
        int[] location = new int[2];  // Allows us to pass row, col by reference
        int move = 1;

        /* Initialize the board */
        char[][] board = new char[4][4];
        for (i = 0; i < 4; i++) { 
            for (j = 0; j < 4; j++) {
                board[i][j] = ' ';
            }
        }

        if (who == 'O') display(board);

        while (move <= 16) {
            if (move % 2 == 1) {
                current = 'X';
                other = 'O';
            }
            else {                
                current = 'O';
                other = 'X';
            }

            if (current == who) {        /* The computer's move */
                choose(location, board, who,initialPlayer);  /* Call function to compute move */
                System.out.println("Computer chooses " + (location[0]+1) + ", " + (location[1]+1));
                if (board[location[0]][location[1]] == ' ') 
                    board[location[0]][location[1]] = current;
                else {
                    System.out.println("BUG! " + (location[0]+1) + ", " + (location[1]+1) + " OCCUPIED!!!");
                    System.exit(0);
                }
                if (winner(board, who)) {
                    System.out.println("Computer has 4 in a row! Computer wins!");
                    display(board);
                    return true;
                }
            }

            else {                       /* Ask for player's move */
                Scanner in = new Scanner(System.in);
                System.out.print("Player " + current + ", enter row: ");
                playerrow = in.nextInt();
                System.out.print("Player " + current + ", enter column: ");
                playercol = in.nextInt();
                while (board[playerrow-1][playercol-1] != ' ' ||
                    playerrow < 1 || playerrow > 4 ||
                    playercol < 1 || playercol > 4) {
                        System.out.println("Illegal move! You cannot use that square!");
                
                        System.out.print("Player " + current + ", enter row: ");
                        playerrow = in.nextInt();
                        System.out.print("Player " + current + ", enter column: ");
                        playercol = in.nextInt();
                }
                playercol--; playerrow--;
                board[playerrow][playercol] = current;
                if (winner(board, current)) {
                    System.out.println("Player has 4 in a row! Player wins!");
                    display(board);
                    return true;            
                }
            }
 
            display(board);    /* Redisplay board to show the move */

            move++; /* Increment the move number and do next move. */
        }
        scores[0] = getScore(board, 'X');
        scores[1] = getScore(board, 'O');
        
        return false;
    }


/* This displays the current configuration of the board. */

    public static void display(char[][] board) {
        int row, col;  
        int scores[] = new int[4];
        System.out.print("\n");
        for (row = 3; row >= 0; row--) {
            System.out.print("  +-+-+-+-+\n");
            System.out.print((row+1) + " ");
            for (col = 0; col < 4; col++) {
            if (board[row][col] == 'X')  /* if contents are 0, print space */
                System.out.print("|X");
            else if (board[row][col] == 'O')
                System.out.print("|0");
            else System.out.print("| ");
            }
            System.out.print("|\n");
        }
        System.out.print("  +-+-+-+-+\n");  /* print base, and indices */
        System.out.print("   1 2 3 4\n");
       
    }
   
/* Basic function for choosing the computer's move. It essentially
   initiates the first level of the MINMAX algorithm, and returns
   the column number it chooses. */

    public static void choose(int[] location, char[][] board, char who,char initialPlayer) {
        int move;  
        float value;
        char[][] initialboard = new char[4][4];
        copy(initialboard, board); 
        getmax(location, board, 1, who,initialPlayer,initialboard);
    }

/* This handles any MAX level of a MINMAX tree. This essentially handles moves for the computer. */

    public static float getmax(int[] location, char[][] board, int level, char who,char initialPlayer,char[][] initialboard) {
        char[][] tempboard = new char[4][4];
        
        
        int r,c = 0;
        float max = -1;
        float val;
        int[] tempLocation = new int[2];
        for (r = 0; r < 4; r++)
            for (c = 0; c < 4; c++) {  /* Try each row and column in board */
                if (board[r][c] == ' ') {     /* Make sure square not full */
                              
                /* To avoid changing original board  during tests, make a copy */
                copy(tempboard, board); 
                

                /* Find out what would happen if we chose this column */
                tempboard[r][c] = who;

                
                /* If this is the bottom of the search tree (that is, a leaf) we need
                    to use the evaluation function to decide how good the move is */
                if (level == MAXLEVEL) 
                    val = evaluate(tempboard, who,initialPlayer,initialboard);

                /* Otherwise, this move is only as good as the worst thing our
                    opponent can do to us. */
                else
                    val = getmin(tempLocation, tempboard, level+1, who,initialPlayer,initialboard);

                /* Return the highest evaluation, and set call by ref. parameter
                    "move" to the corresponding column */
                if (val > max) {
                    max = val;
                    if (level==1) {location[0] = r; location[1] = c;}
                 }

            }
        }
        return max;
    }

/* This handles any MIN level of a MINMAX tree. This essentially handles moves for the player. */

    public static float getmin(int[] location, char[][] board, int level, char who,char initialPlayer,char [] [] initialboard) {
        char[][] tempboard = new char[4][4]; 
        int r,c = 0;   
        int[] tempLocation = new int[2];
        float min = 10001;
        float val;

        /* Since this is opponent's move, we need to figure out which they are */
        char other;
        if (who == 'X') other = 'O'; else other = 'X'; 

        for (r = 0; r < 4; r++)
            for (c = 0; c < 4; c++) {  /* Try each row and column in board */
                if (board[r][c] == ' ') {     /* Make sure square not full */

                    /* To avoid changing original board  during tests, make a copy */
                    copy(tempboard, board);

                    /* Find out what would happen if opponent chose this column */
                    tempboard[r][c] = other;

                    /* If this is the bottom of the search tree (that is, a leaf) we need
                    to use the evaluation function to decide how good the move is */
                    if (level == MAXLEVEL)  
                        val = evaluate(tempboard, who,initialPlayer,initialboard);

                    /* Otherwise, find the best thing that we can do if opponent
                        chooses this move. */
                    else
                        val = getmax(tempLocation, tempboard, level+1, who,initialPlayer,initialboard);

                    /* Return the lowest evaluation (which we will assume will be 
                        chosen by opponent, and set call by ref. parameter
                        "move" to the corresponding column */
                    if (val < min) {
                        min = val;
                        // *move = col;
                    }
                }
            }
        return min;
   }


/* This function makes a copy of a given board. This is necessary to be
   able to "try out" the effects of different moves without messing up
   the actual current board. */

    public static void copy(char[][] a, char[][] b) {
        int i, j;
        for (i = 0; i < 4; i++) { 
            for (j = 0; j < 4; j++) {
                a[i][j] = b[i][j];  
            }
        }
    }
    
    //check for 3 consecutives move of the player in a sane  row,column and diagonal
    public static int threeInARow(char[][]board, char ai , char initialPlayer,char [][] initialboard) {
      int value=0; 
      int row, col;
      int X, O;
      
      /* check all rows */
      //if any row has 3 consecutive move of the player, place a move of the ai in the remaining slot 
      for (row = 0; row < 4; row++)
        for (col = 0; col < 2; col++)
          if (board[row][col] == initialPlayer &&
              board[row][col+1] == initialPlayer &&
              board[row][col+2] == initialPlayer) {
              if (col==0 && board[row][col+3] ==ai && initialboard[row][col+3]==' '){
                  value = 1000; 
                  break; 
              }else if (col==1 && board[row][0]==ai && initialboard[row][0]==' '){
                  value=1000;
                  break; 
              }
          }

      /* check all columns */
      //if any column has 3 consecutive move of the player, place a move of the ai in the remaining slot 
      for (row = 0; row < 2; row++)
        for (col = 0; col < 4; col++)
          if (board[row][col] == initialPlayer &&
              board[row+1][col] == initialPlayer &&
              board[row+2][col] == initialPlayer){
              if (row==0 && board[row+3][col] ==ai && initialboard[row+3][col]==' '){
                  value = 1000; 
                  break; 
              }else if (row==1 && board[0][col]==ai && initialboard[0][col]==' '){
                  value=1000;
                  break;
              }
          }

      /* check all diagonals */
      //if any of the main diagonal has 3 consecutive move of the player,place a move of the ai in the remaining slot 
      for (row = 0; row < 2; row++)
        for (col = 0; col < 2; col++)
          if (board[row][col] == initialPlayer &&
              board[row+1][col+1] == initialPlayer &&
              board[row+2][col+2] == initialPlayer) {
               if (row==0 && board[row+3][col+3] ==ai && initialboard[row+3][col+3]==' '){
                  value = 1000; 
                  break;
              }else if (row==1 && board[0][0]==ai && initialboard[0][0]==' '){
                  value=1000;
                  break; 
              }
          }
      if ( (board[3][0] ==initialPlayer) &&  (board[2] [1] == initialPlayer)  && (board[1][2]==initialPlayer) && (board[0][3] == ai) && (initialboard[0][3] == ' ')){
                value=1000; 
        }else if ((board[0][3] ==initialPlayer) &&  (board[2] [1] == initialPlayer)  && (board[1][2]==initialPlayer) && (board[3][0] == ai) && (initialboard[3][0] == ' ')){
                value =1000;    
        } 
        
      
      return value;
      }
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        char player, computer;
        int[] scores = new int[2];
        /* Decide who goes first */
        System.out.print("Do you want to play X or O: ");
        Scanner in = new Scanner(System.in);
        player = in.nextLine().charAt(0);
        if (player == 'X') computer = 'O';
        else computer = 'X';
        boolean win = false;
        win = run(scores, computer,player);
        if (!win)
            System.out.println("\nFinal score: \nX: " + scores[0] + "\nO: " + scores[1] + "\n");
        }
}


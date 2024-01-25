import java.util.*;

public class ConnectFour implements AbstractStrategyGame {
    private char[][] board;
    private boolean isPlayerOneTurn;
    private int winner;
    private char PLAYER_1_SYMBOL = 'X';
    private char PLAYER_2_SYMBOL = 'O';

    // Constructs a new Connect four game.
    public ConnectFour() {
        // 7 columns x 6 rows
        board = new char[6][7];
        isPlayerOneTurn = true;
        winner = -1;

        populateNewBoard();
    }

    // Fills the new board with empty values
    private void populateNewBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = '-';
            }
        }
    }

    // Returns whether or not the game is over.
    public boolean isGameOver() {
        return getWinner() >= 0;
    }

    // Returns the index of the winner of the game.
    // 1 if player 1 (X), 2 if player 2 (O), 0 if a tie occurred,
    // and -1 if the game is not over.
    public int getWinner() {
        return winner;
    }

    // Private helper function for getWinner(). Checks horizontal,
    // vertical, and both diagonals for a winner. 1 if player
    // 1 (X), 2 if player 2 (O), 0 if a tie occurred,
    // and -1 if the game is not over.
    private void getWinner(int row, int col, char player) {
        // Check for a winner
        if (checkRow(row, col) ||
                checkColumn(row, col) ||
                checkDiagonalOne(row, col) ||
                checkDiagonalTwo(row, col)) {
            this.winner = isPlayerOneTurn ? 1 : 2;
        } else if (checkTie()) { // Check for a tie
            this.winner = 0;
        }
    }

    // Checks whether or not there is a tie; i.e. there are no empty values in the
    // grid.
    private boolean checkTie() {
        boolean tie = true;
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i] == '-') {
                tie = false; // There's an empty space, game continues
            }
        }

        return tie;
    }

    // Returns the index of which player's turn it is.
    // 1 if player 1 (X), 2 if player 2 (O), -1 if the game is over
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }
        return isPlayerOneTurn ? 1 : 2;
    }

    // Given the input, places an piece at the column where the player specifies.
    // Determines the row by looking at the lowest row that is empty.
    // Throws an IllegalArgumentException if the position is
    // invalid, whether that be out of bounds or already occupied.
    // Board bounds are [0, 2] for both rows and cols.
    public void makeMove(Scanner input) {
        char currPlayer = isPlayerOneTurn ? PLAYER_1_SYMBOL : PLAYER_2_SYMBOL;

        System.out.print("Column? ");
        int col = input.nextInt();

        makeMove(col, currPlayer);
        isPlayerOneTurn = !isPlayerOneTurn;
    }

    // Private helper method for makeMove.
    // Given a column, as well as player index,
    // calculates the row number and places an X or an O in that row and col.
    // Throws an IllegalArgumentException if the row and col is
    // invalid, whether that be out of bounds or fully occupied.
    // Board bounds are [0, 5] for rows and [0, 6] for cols.
    private void makeMove(int col, char player) {

        if (col < 0 || col >= board[0].length) {
            throw new IllegalArgumentException("Invalid board position: " + col);
        }
        
        int row = findRowFromCol(col);

        if (row < 0 || row >= board.length) {
            throw new IllegalArgumentException("Invalid board position: " + row + "," + col);
        }

        if (board[row][col] != '-') {
            throw new IllegalArgumentException("Space already occupied: " + row + "," + col);
        }

        board[row][col] = player;
        getWinner(row, col, player); // checks everything
    }

    // Private helper method for makeMove.
    // Finds and returns the row closest to the bottom that is empty based on given
    // column. If all rows are occupied, return -1 and there will be an error.
    private int findRowFromCol(int col) {
        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i][col] == '-') {
                return i;
            }
        }
        return -1;
    }

    // Returns a String containing instructions to play the game.
    public String instructions() {
        String result = "";
        result += "Connect Four: A duo player strategy game.\n";
        result += "Two players take turns dropping their respective\n";
        result += "tokens into the 7 x 6 standing grid  [width x height],\n";
        result += "trying to connect four of their tokens horizontally,\n";
        result += "vertically, or diagonally. If the board is full and there\n";
        result += "is no winner, then there is a tie.\n";
        return result;
    }

    // Returns a String representation of the current state of the board.
    public String toString() {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                result += board[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }

    // Returns whether or not the horizontal axis of a token contains a connect
    // four.
    private boolean checkRow(int row, int col) {
        int connections = 1;
        char target = board[row][col];

        // check to the right
        for (int i = 1; (col + i < board[row].length)
                && (board[row][col + i] == target)
                && (connections < 4); i++) {
            connections += 1;
        }

        // check to the left
        for (int i = 1; (col - i >= 0)
                && (board[row][col - i] == target)
                && (connections < 4); i++) {
            connections += 1;
        }

        return connections == 4;
    }

    // Returns whether or not the vertical axis of a token contains a connect four.
    private boolean checkColumn(int row, int col) {
        int connections = 1;
        char target = board[row][col];

        // check above
        for (int i = 1; (row + i < board.length)
                && (board[row + i][col] == target)
                && (connections < 4); i++) {
            connections += 1;
        }

        // check below
        for (int i = 1; (row - i >= 0)
                && (board[row - i][col] == target)
                && (connections < 4); i++) {
            connections += 1;
        }

        return connections == 4;
    }

    // Returns whether or not the top-left to bottom-right diagonal of a
    // token contains a connect four.
    private boolean checkDiagonalOne(int row, int col) {
        int connections = 1;
        char target = board[row][col];

        // check above : row - i, col - i
        for (int i = 1; (row - i >= 0)
                && (col - i >= 0)
                && (board[row - i][col - i] == target)
                && (connections < 4); i++) {
            connections += 1;
        }

        // check below : row + i, col + i
        for (int i = 1; (row + i < board.length)
                && (col + i < board[0].length)
                && (board[row + i][col + i] == target)
                && (connections < 4); i++) {
            connections += 1;
        }
        return connections == 4;
    }

    // Returns whether or not the top-right to bottom-left diagonal of a
    // token contains a connect four.
    private boolean checkDiagonalTwo(int row, int col) {
        int connections = 1;
        char target = board[row][col];

        // check above : row + i, col - i
        for (int i = 1; (row + i < board.length)
                && (col - i >= 0)
                && (board[row + i][col - i] == target)
                && (connections < 4); i++) {
            connections += 1;
        }

        // check below : row - i, col + i
        for (int i = 1; (row - i >= 0)
                && (col + i < board[0].length)
                && (board[row - i][col + i] == target)
                && (connections < 4); i++) {
            connections += 1;
        }
        return connections == 4;

    }

}

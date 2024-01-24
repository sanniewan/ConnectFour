// Sannie Wan
// CSE 123
// C1: Abstract Strategy Games
//
// A class to represent a game of connect four that implements the 
// AbstractStrategyGame interface.

import java.util.*;

public class ConnectFour implements AbstractStrategyGame {
    private char[][] board;
    private boolean isXTurn;
    private int winner;

    // Constructs a new Connect four game.
    public ConnectFour() {
        // 7 columns x 6 rows
        board = new char[6][7];
        isXTurn = true;
        winner = -1;

        populateNewBoard();
    }

    public void populateNewBoard() {
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
        if (checkRow(row, col) ||
                checkColumn(row, col) ||
                checkDiagonalOne(row, col) ||
                checkDiagonalTwo(row, col)) {
            this.winner = isXTurn ? 1 : 2;
        }

        // Check for a tie
        if (checkTie()) {
            this.winner = 0;
        }
        
    }

    public boolean checkTie() {
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
        return isXTurn ? 1 : 2;
    }

    // Given the input, places an X or an O where
    // the player specifies.
    // Throws an IllegalArgumentException if the position is
    // invalid, whether that be out of bounds or already occupied.
    // Board bounds are [0, 2] for both rows and cols.
    public void makeMove(Scanner input) {
        char currPlayer = isXTurn ? 'X' : 'O';

        System.out.print("Column? ");
        int col = input.nextInt();
        int row = findRowFromCol(col);

        makeMove(row, col, currPlayer);
        isXTurn = !isXTurn;
    }

    public int findRowFromCol(int col) {
        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i][col] == '-') {
                return i;
            }
        }
        return 0;
    }

    private void makeMove(int row, int col, char player) {
        if (row < 0 || row >= board.length ||
                col < 0 || col >= board[0].length) {
            throw new IllegalArgumentException("Invalid board position: " + row + "," + col);
        }

        if (board[row][col] != '-') {
            throw new IllegalArgumentException("Space already occupied: " + row + "," + col);
        }

        board[row][col] = player;
        getWinner(row, col, player); // checks everything
    }

    // Returns a String containing instructions to play the game.
    public String instructions() {
        String result = "";
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

    // Checks the horizontal axis of a token for a connect four.
    public boolean checkRow(int row, int col) {
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

    // Checks the vertical axis of a token for a connect four.
    public boolean checkColumn(int row, int col) {
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

    // Checks the top-left to bottom-right diagonal of a token for a connect four.
    public boolean checkDiagonalOne(int row, int col) {
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

    // Checks the top-right to bottom-left diagonal of a token for a connect four.
    public boolean checkDiagonalTwo(int row, int col) {
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

/*
 * int i = 1;
 * int connections = 1;
 * int target = board[row][col];
 * boolean leftEdgeCheck = col - i >= 0;
 * boolean rightEdgeCheck = col + i + 1 < board[0].length;
 * 
 * while ((leftEdgeCheck || rightEdgeCheck) && connections < 4) {
 * if (board[row][col + i] == target && rightEdgeCheck) {
 * connections += 1;
 * i += 1;
 * rightEdgeCheck = col + i + 1 < board[0].length;
 * } else {
 * rightEdgeCheck = false;
 * }
 * 
 * if (board[row][col - i] == target && leftEdgeCheck) {
 * connections += 1;
 * i += 1;
 * leftEdgeCheck = col - i >= 0;
 * } else {
 * leftEdgeCheck = false;
 * }
 * }
 * return connections == 4;
 */
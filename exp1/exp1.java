import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class TicTacToe {
    static char[][] board = new char[3][3];

    public static void main(String[] args) {
        initializeBoard();
        printBoard();
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver()) {
            // User's move
            System.out.println("Enter your move (1-9): ");
            int userMove = scanner.nextInt();
            while (!isValidMove(userMove)) {
                System.out.println("Invalid move. Please enter a valid move (1-9): ");
                userMove = scanner.nextInt();
            }
            makeMove(userMove, 'x');
            printBoard();

            if (isGameOver()) {
                scanner.close();
                break;
                }

            // Computer's move
            System.out.println("Computer's move: ");
            int computerMove = getComputerMove();
            makeMove(computerMove, 'o');
            printBoard();
        }

        // Game over
        char winner = getWinner();
        if (winner == 'x') {
            System.out.println("Congratulations! You win!");
        } else if (winner == 'o') {
            System.out.println("Computer wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '_';
            }
        }
    }

    static void printBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static boolean isValidMove(int move) {
        return move >= 1 && move <= 9 && board[(move - 1) / 3][(move - 1) % 3] == '_';
    }

    static void makeMove(int move, char player) {
        int row = (move - 1) / 3;
        int col = (move - 1) % 3;
        System.out.println("Row: " + row + ", Column: " + col); // Debugging
        board[row][col] = player;
    }
    

    static boolean isGameOver() {
        return getWinner() != '_' || isBoardFull();
    }

    static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    return false;
                }
            }
        }
        return true;
    }

    static char getWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '_' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != '_' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return board[0][j];
            }
        }
        // Check diagonals
        if (board[0][0] != '_' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != '_' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }
        return '_'; // No winner
    }

    static int getComputerMove() {
        String currentBoard = convertBoardToString(board);
        String nb = getNextBoard(currentBoard);
        String cb = convertToTernary(currentBoard);
        // String nb = nextBoard.toLowerCase();// converting to lower case
        // String cb = currentBoard.toLowerCase(); // same as above
        //  // variables to compare specific char in the strings
        // int nbInt,cbInt;
        for (int i = 0; i < 9; i++) {
            // nbInt = Character.getNumericValue(nb.charAt(i));
            // System.out.println("nb char at "+i+"="+nbInt);
           

            // cbInt = Character.getNumericValue(cb.charAt(i));
            // System.out.println("cb char at "+i+"="+cbInt);
            if (nb.charAt(i) != cb.charAt(i)) {
                System.out.println(i+1);
                return i + 1;
            }
        }
        return -1; // Should not happen
    }
    

    static String convertBoardToString(char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char cell : row) {
                sb.append(cell);
            }
        }
        return sb.toString();
    }

    static String getNextBoard(String currentBoard) {
        String nextBoard = null;
        String ternaryBoard = convertToTernary(currentBoard);
        try (BufferedReader br = new BufferedReader(new FileReader("output.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(ternaryBoard)) {
                    nextBoard = parts[1];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Next board for " + ternaryBoard + ": " + nextBoard); // Debugging
        return nextBoard;
    }
    
    static String convertToTernary(String boardState) {
        StringBuilder ternaryBoard = new StringBuilder();
        for (char c : boardState.toCharArray()) {
            if (c == '_') {
                ternaryBoard.append('0'); // Empty cell
            } else if (c == 'x') {
                ternaryBoard.append('1'); // Cell filled by 'x'
            } else if (c == 'o') {
                ternaryBoard.append('2'); // Cell filled by 'o'
            }
        }
        return ternaryBoard.toString();
    }
    
    
}

// PS C:\Users\Admin\c\ai> java TicTacToe
// Current Board:
// _ _ _ 
// _ _ _ 
// _ _ _ 
// Enter your move (1-9): 
// 5
// Row: 1, Column: 1
// Current Board:   
// _ _ _
// _ x _
// _ _ _
// Computer's move: 
// Next board for 000010000: 200010000
// 1
// Row: 0, Column: 0
// Current Board:
// o _ _
// _ x _
// _ _ _
// Enter your move (1-9):
// 3
// Row: 0, Column: 2
// Current Board:
// o _ x
// _ x _
// _ _ _
// Computer's move:
// Next board for 201010000: 201010200
// 7
// Row: 2, Column: 0
// Current Board:
// o _ x
// _ x _
// o _ _
// Enter your move (1-9):
// 4
// Row: 1, Column: 0
// Current Board:
// o _ x
// x x _
// o _ _
// Computer's move:
// Next board for 201110200: 201112200
// 6
// Row: 1, Column: 2
// Current Board:
// o _ x
// x x o
// o _ _
// Enter your move (1-9):
// 2
// Row: 0, Column: 1
// Current Board:
// o x x
// x x o
// o _ _
// Computer's move:
// Next board for 211112200: 211112220
// 8
// Row: 2, Column: 1
// Current Board:
// o x x
// x x o
// o o _ 
// Enter your move (1-9):
// 9
// Row: 2, Column: 2
// Current Board:
// o x x
// x x o
// o o x
// It's a draw!

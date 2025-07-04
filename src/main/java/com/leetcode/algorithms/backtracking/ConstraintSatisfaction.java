package com.leetcode.algorithms.backtracking;

import java.util.*;

/**
 * Constraint Satisfaction Problems (CSP) using Backtracking
 *
 * This class implements solutions for classic constraint satisfaction problems
 * using backtracking with various optimization techniques.
 *
 * Key Concepts:
 * - Variables: What we're assigning values to
 * - Domain: Possible values for each variable
 * - Constraints: Rules that valid solutions must satisfy
 * - Backtracking: Systematic exploration with pruning
 *
 * Optimization Techniques:
 * - Forward checking: Eliminate values that violate constraints
 * - Arc consistency: Ensure pairwise consistency
 * - Variable ordering heuristics (MRV, Degree heuristic)
 * - Value ordering heuristics (LCV)
 *
 * Classic Problems:
 * - N-Queens
 * - Sudoku
 * - Graph Coloring
 * - Word Search
 * - Crossword Puzzles
 */
public class ConstraintSatisfaction {

    // =========================== N-Queens Problem ===========================

    /**
     * Solve N-Queens problem - place N queens on NxN board such that no two attack each other
     * Time Complexity: O(N!) with pruning, O(N^N) worst case
     * Space Complexity: O(N) for recursion stack and board state
     *
     * @param n size of the board (N x N)
     * @return list of all solutions, each solution is a list of queen positions
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        int[] queens = new int[n]; // queens[i] = column position of queen in row i

        solveNQueensHelper(0, n, queens, result);
        return result;
    }

    private void solveNQueensHelper(int row, int n, int[] queens, List<List<String>> result) {
        // Base case: all queens placed
        if (row == n) {
            result.add(generateBoard(queens, n));
            return;
        }

        // Try placing queen in each column of current row
        for (int col = 0; col < n; col++) {
            if (isSafeQueen(row, col, queens)) {
                queens[row] = col;
                solveNQueensHelper(row + 1, n, queens, result);
                // No need to explicitly backtrack as we overwrite queens[row]
            }
        }
    }

    /**
     * Check if placing a queen at (row, col) is safe
     *
     * @param row    current row
     * @param col    current column
     * @param queens current queen positions
     * @return true if safe to place queen
     */
    private boolean isSafeQueen(int row, int col, int[] queens) {
        for (int i = 0; i < row; i++) {
            int prevCol = queens[i];

            // Check column conflict
            if (prevCol == col) {
                return false;
            }

            // Check diagonal conflicts
            if (Math.abs(i - row) == Math.abs(prevCol - col)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generate board representation from queen positions
     *
     * @param queens array of queen column positions
     * @param n      board size
     * @return list of strings representing the board
     */
    private List<String> generateBoard(int[] queens, int n) {
        List<String> board = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if (queens[i] == j) {
                    row.append('Q');
                } else {
                    row.append('.');
                }
            }
            board.add(row.toString());
        }

        return board;
    }

    /**
     * Count total number of N-Queens solutions
     * Time Complexity: O(N!)
     * Space Complexity: O(N)
     *
     * @param n board size
     * @return number of solutions
     */
    public int totalNQueens(int n) {
        return countNQueensHelper(0, n, new int[n]);
    }

    private int countNQueensHelper(int row, int n, int[] queens) {
        if (row == n) {
            return 1;
        }

        int count = 0;
        for (int col = 0; col < n; col++) {
            if (isSafeQueen(row, col, queens)) {
                queens[row] = col;
                count += countNQueensHelper(row + 1, n, queens);
            }
        }

        return count;
    }

    /**
     * Optimized N-Queens using bit manipulation
     * Time Complexity: O(N!)
     * Space Complexity: O(N)
     *
     * @param n board size
     * @return number of solutions
     */
    public int totalNQueensBitwise(int n) {
        return solveNQueensBitwise(0, 0, 0, n);
    }

    private int solveNQueensBitwise(int cols, int diag1, int diag2, int n) {
        if (cols == (1 << n) - 1) { // All columns filled
            return 1;
        }

        int count = 0;
        int available = ((1 << n) - 1) & (~(cols | diag1 | diag2));

        while (available != 0) {
            int pos = available & (-available); // Get rightmost set bit
            available &= (available - 1);       // Remove rightmost set bit

            count += solveNQueensBitwise(cols | pos,
                    (diag1 | pos) << 1,
                    (diag2 | pos) >> 1,
                    n);
        }

        return count;
    }

    // =========================== Sudoku Solver ===========================

    /**
     * Solve 9x9 Sudoku puzzle using backtracking
     * Time Complexity: O(9^(n*n)) where n=9, with pruning much better
     * Space Complexity: O(n*n) for recursion stack
     *
     * @param board 9x9 character array, '.' represents empty cell
     * @return true if solvable, board is modified in place
     */
    public boolean solveSudoku(char[][] board) {
        return solveSudokuHelper(board, 0, 0);
    }

    private boolean solveSudokuHelper(char[][] board, int row, int col) {
        // Move to next row if end of current row reached
        if (col == 9) {
            return solveSudokuHelper(board, row + 1, 0);
        }

        // Base case: all rows processed
        if (row == 9) {
            return true;
        }

        // If cell already filled, move to next cell
        if (board[row][col] != '.') {
            return solveSudokuHelper(board, row, col + 1);
        }

        // Try digits 1-9
        for (char digit = '1'; digit <= '9'; digit++) {
            if (isValidSudoku(board, row, col, digit)) {
                board[row][col] = digit;

                if (solveSudokuHelper(board, row, col + 1)) {
                    return true;
                }

                board[row][col] = '.'; // Backtrack
            }
        }

        return false;
    }

    /**
     * Check if placing digit at (row, col) is valid in Sudoku
     *
     * @param board current board state
     * @param row   target row
     * @param col   target column
     * @param digit digit to place
     * @return true if placement is valid
     */
    private boolean isValidSudoku(char[][] board, int row, int col, char digit) {
        // Check row
        for (int j = 0; j < 9; j++) {
            if (board[row][j] == digit) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == digit) {
                return false;
            }
        }

        // Check 3x3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == digit) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Validate if Sudoku board is valid (may be incomplete)
     * Time Complexity: O(1) since board size is fixed
     * Space Complexity: O(1)
     *
     * @param board 9x9 character array
     * @return true if current state is valid
     */
    public boolean isValidSudokuBoard(char[][] board) {
        // Check rows
        for (int i = 0; i < 9; i++) {
            if (!isValidGroup(board, i, 0, 0, 1)) {
                return false;
            }
        }

        // Check columns
        for (int j = 0; j < 9; j++) {
            if (!isValidGroup(board, 0, j, 1, 0)) {
                return false;
            }
        }

        // Check 3x3 boxes
        for (int box = 0; box < 9; box++) {
            int startRow = (box / 3) * 3;
            int startCol = (box % 3) * 3;
            if (!isValidBox(board, startRow, startCol)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidGroup(char[][] board, int startRow, int startCol, int deltaRow, int deltaCol) {
        boolean[] used = new boolean[10]; // digits 1-9

        for (int i = 0; i < 9; i++) {
            int row = startRow + i * deltaRow;
            int col = startCol + i * deltaCol;
            char digit = board[row][col];

            if (digit != '.') {
                int num = digit - '0';
                if (num < 1 || num > 9 || used[num]) {
                    return false;
                }
                used[num] = true;
            }
        }

        return true;
    }

    private boolean isValidBox(char[][] board, int startRow, int startCol) {
        boolean[] used = new boolean[10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char c = board[startRow + i][startCol + j];
                if (c != '.') {
                    int num = c - '0';
                    if (used[num]) {
                        return false; // Trùng số trong box
                    }
                    used[num] = true;
                }
            }
        }
        return true;
    }

}
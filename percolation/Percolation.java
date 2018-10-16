/* *****************************************************************************
 * grid.lengthame:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private static final byte OPEN = 1;
    private static final byte CONNECT_TO_TOP = 2;
    private static final byte CONNECT_TO_BOTH = 3;
    private static final byte CONNECT_TO_BOTTOM = 4;

    private boolean[][] grid;
    private int[][] status;
    private int openNum;
    private WeightedQuickUnionUF connection;
    private int top = 0;
    private int bottom;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Negative number");
        grid = new boolean[n][n];
        status = new int[n][n];
        openNum = 0;
        bottom = n * n + 1;
        connection = new WeightedQuickUnionUF(n * n);
    }

    private boolean helper(int row, int col) {
        if (row <= 0 || row > grid.length) {
            throw new IllegalArgumentException("Row out of bound");
        }
        if (col <= 0 || col > grid.length) {
            throw new IllegalArgumentException("Col out of bound");
        }
        return true;
    }

    public void open(int row, int col) {
        int currentStatus = 0;
        if (helper(row, col)) {
            if (!isOpen(row, col)) {
                openNum++;
                status[row - 1][col - 1] = OPEN;
            }

            grid[row - 1][col - 1] = true;
            if (row == 1) {
                status[row - 1][col - 1] = CONNECT_TO_TOP;
            }
            //     findRoot = connection.find(grid.length * (row - 1) + col);
            //     if (findRoot < grid.length)
            //         status[row - 1][col - 1] = CONNECT_TO_TOP;
            //     connection.union(grid.length * (row - 1) + col, top);
            // }
            // if (row == grid.length) {
            //     connection.union(grid.length * (row - 1) + col, bottom);
            // }

            if (col - 2 >= 0 && isOpen(row, col - 1)) {
                int leftStatus = findStatus(row, col - 1);
                connection.union(grid.length * (row - 1) + col - 2,
                                 grid.length * (row - 1) + col - 1);

                int newStatus = findStatus(row, col);
                if (newStatus == CONNECT_TO_TOP || leftStatus == CONNECT_TO_TOP
                        || status[row - 1][col - 1] == CONNECT_TO_TOP
                        || status[row - 1][col - 2] == CONNECT_TO_TOP) {
                    status[row - 1][col - 2] = CONNECT_TO_TOP;
                    status[row - 1][col - 1] = CONNECT_TO_TOP;
                }

            }

            if (col < grid.length && isOpen(row, col + 1)) {
                int rightStatus = findStatus(row, col + 1);
                connection.union(grid.length * (row - 1) + col,
                                 grid.length * (row - 1) + col - 1);

                int newStatus = findStatus(row, col);
                if (newStatus == CONNECT_TO_TOP || rightStatus == CONNECT_TO_TOP
                        || status[row - 1][col - 1] == CONNECT_TO_TOP
                        || status[row - 1][col] == CONNECT_TO_TOP) {
                    status[row - 1][col] = CONNECT_TO_TOP;
                    status[row - 1][col - 1] = CONNECT_TO_TOP;
                }
            }

            if (row - 2 >= 0 && isOpen(row - 1, col)) {
                int topStatus = findStatus(row - 1, col);
                connection.union(grid.length * (row - 1) + col - 1,
                                 grid.length * (row - 2) + col - 1);

                int newStatus = findStatus(row, col);
                if (newStatus == CONNECT_TO_TOP || topStatus == CONNECT_TO_TOP
                        || status[row - 1][col - 1] == CONNECT_TO_TOP
                        || status[row - 2][col - 1] == CONNECT_TO_TOP) {
                    status[row - 1][col - 1] = CONNECT_TO_TOP;
                    status[row - 2][col - 1] = CONNECT_TO_TOP;
                }
            }

            if (row < grid.length && isOpen(row + 1, col)) {
                int bottomStatus = findStatus(row + 1, col);
                connection.union(grid.length * (row - 1) + col - 1,
                                 grid.length * row + col - 1);

                int newStatus = findStatus(row, col);
                if (newStatus == CONNECT_TO_TOP || bottomStatus == CONNECT_TO_TOP
                        || status[row - 1][col - 1] == CONNECT_TO_TOP
                        || status[row][col - 1] == CONNECT_TO_TOP) {
                    status[row - 1][col - 1] = CONNECT_TO_TOP;
                    status[row][col - 1] = CONNECT_TO_TOP;
                }
            }

        }

    }

    public boolean isOpen(int row, int col) {
        if (helper(row, col))
            return grid[row - 1][col - 1];
        else
            throw new IllegalArgumentException();
    }

    public boolean isFull(int row, int col) {
        // if (!percolates()) {
        //     if (0 < row && row <= grid.length && 0 < col && col <= grid.length) {
        //         return connection.connected(top, grid.length * (row - 1) + col);
        //     }
        // }
        // else
        //     throw new IllegalArgumentException();
        // return false;
        int root = connection.find(grid.length * (row - 1) + col - 1);
        if (status[root / grid.length][root % grid.length] == CONNECT_TO_TOP) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openNum;
    }

    public boolean percolates() {
        // return connection.connected(top, bottom);
        // int k = grid.length;
        // for (int j = k; j > 0; j--) {
        //     if (connection.connected(top, k * (k - 1) + j))
        //         return true;
        // }
        // return false;
        for (int j = 0; j < grid.length; j++) {
            if (status[grid.length - 1][j] == CONNECT_TO_TOP)
                return true;
        }
        return false;
    }

    private int findStatus(int row, int col) {
        int root = connection.find(grid.length * (row - 1) + col - 1);
        if (root < grid.length) {
            status[root / grid.length][root % grid.length] = CONNECT_TO_TOP;
            return CONNECT_TO_TOP;
        }

        if (root >= grid.length * (grid.length - 1))
            return CONNECT_TO_BOTTOM;
        else
            return OPEN;
    }


}

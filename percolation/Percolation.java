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
    private byte[] siteState;
    private int openNum;
    private WeightedQuickUnionUF connection;
    private int top = 0;
    private int bottom;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Negative number");
        grid = new boolean[n][n];
        siteState = new byte[n * n];
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
        int currentRoot = findStatus(row, col);
        int leftRoot = -1, rightRoot = -1, topRoot = -1, bottomRoot = -1;
        if (helper(row, col)) {
            if (!isOpen(row, col)) {
                openNum++;
            }

            grid[row - 1][col - 1] = true;
            if (row == 1) {
                siteState[currentRoot] = CONNECT_TO_TOP;
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
                leftRoot = findStatus(row, col - 1);
                connection.union(grid.length * (row - 1) + col - 2,
                                 grid.length * (row - 1) + col - 1);


                // if (siteState[leftRoot] == CONNECT_TO_TOP) {
                //     siteState[grid.length * (row - 1) + col - 1] = CONNECT_TO_TOP;
                //     siteState[grid.length * (row - 1) + col - 2] = CONNECT_TO_TOP;
                // }

            }

            if (col < grid.length && isOpen(row, col + 1)) {
                rightRoot = findStatus(row, col + 1);
                connection.union(grid.length * (row - 1) + col,
                                 grid.length * (row - 1) + col - 1);

                // int newRoot = findStatus(row, col);
                // if (siteState[rightRoot] == CONNECT_TO_TOP
                //         || siteState[newRoot] == CONNECT_TO_TOP) {
                //     siteState[grid.length * (row - 1) + col] = CONNECT_TO_TOP;
                //     siteState[grid.length * (row - 1) + col - 1] = CONNECT_TO_TOP;
                // }
            }

            if (row - 2 >= 0 && isOpen(row - 1, col)) {
                topRoot = findStatus(row - 1, col);
                connection.union(grid.length * (row - 1) + col - 1,
                                 grid.length * (row - 2) + col - 1);

                // int newRoot = findStatus(row, col);
                // if (siteState[topRoot] == CONNECT_TO_TOP || siteState[newRoot] == CONNECT_TO_TOP) {
                //     siteState[grid.length * (row - 1) + col - 1] = CONNECT_TO_TOP;
                //     siteState[grid.length * (row - 2) + col - 1] = CONNECT_TO_TOP;
                // }
            }

            if (row < grid.length && isOpen(row + 1, col)) {
                bottomRoot = findStatus(row + 1, col);
                connection.union(grid.length * (row - 1) + col - 1,
                                 grid.length * row + col - 1);

                // int newRoot = findStatus(row, col);
                // if (siteState[bottomRoot] == CONNECT_TO_TOP
                //         || siteState[newRoot] == CONNECT_TO_TOP) {
                //     siteState[grid.length * (row - 1) + col - 1] = CONNECT_TO_TOP;
                //     siteState[grid.length * row + col - 1] = CONNECT_TO_TOP;
                // }
            }
            int newRoot = findStatus(row, col);
            if (siteState[leftRoot] == CONNECT_TO_TOP || siteState[rightRoot] == CONNECT_TO_TOP
                    || siteState[topRoot] == CONNECT_TO_TOP
                    || siteState[bottomRoot] == CONNECT_TO_TOP
                    || siteState[newRoot] == CONNECT_TO_TOP)


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
        if (siteState[root] == CONNECT_TO_TOP) {
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
        for (int j = 1; j <= grid.length; j++) {
            if (siteState[grid.length * (grid.length - 1) + j - 1] == CONNECT_TO_TOP)
                return true;
        }
        return false;
    }

    private int findStatus(int row, int col) {
        int root = connection.find(grid.length * (row - 1) + col - 1);
        if (root < grid.length) {
            siteState[root] = CONNECT_TO_TOP;
        }

        // if (root >= grid.length * (grid.length - 1))
        //     siteState[root] = CONNECT_TO_BOTTOM;
        // else
        //     siteState[root] = OPEN;
        return root;
    }


}

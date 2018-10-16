/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double[] probArray;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Error");

        int repeat = 0;
        probArray = new double[trials];
        while (repeat < trials) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }

            }
            probArray[repeat] = (double) percolation.numberOfOpenSites() / (double) (n * n);
            // fraction += probArray[repeat];
            repeat++;
        }
        mean = StdStats.mean(probArray);
        stddev = StdStats.stddev(probArray);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean - (CONFIDENCE_95 * stddev) / Math.sqrt(probArray.length);
    }

    public double confidenceHi() {
        return mean + (CONFIDENCE_95 * stddev) / Math.sqrt(probArray.length);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trail = Integer.parseInt(args[1]);
        PercolationStats per = new PercolationStats(n, trail);
        StdOut.println("mean                    = " + per.mean());
        StdOut.println("stddev                  = " + per.stddev());
        StdOut.println(
                "95% confidence interval = " + "[" + per.confidenceLo() + "," + per.confidenceHi()
                        + "]");
    }
}

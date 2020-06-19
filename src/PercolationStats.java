import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private final int trials;
    private final double[] fractionOpenSites;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("must be > 0");
        }
        fractionOpenSites = new double[trials];
        this.trials = trials;
        for (int i = 0; i < this.trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);

                }
            }
            fractionOpenSites[i] = (double) percolation.numberOfOpenSites() / (n * n);

        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractionOpenSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractionOpenSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean\t\t\t\t\t = %f\n", percolationStats.mean());
        StdOut.printf("stddev\t\t\t\t\t = %f\n", percolationStats.stddev());
        StdOut.printf("95%% confidence interval\t = [%f, %f]\n", percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }
}

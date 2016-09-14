import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater that 0");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials must be greater that 0");
        }
        double[] fractions = new double[trials];
        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);

            int[] sites = new int[n * n];
            for (int i = 1; i <= n * n; i++) sites[i - 1] = i;
            StdRandom.shuffle(sites);

            int i = 0;
            while (!percolation.percolates()) {
                percolation.open((sites[i] - 1) / n + 1, (sites[i] - 1) % n + 1);
                i++;
            }
            fractions[t] = (double) i / (n * n);
        }
        mean = StdStats.mean(fractions);
        stddev = StdStats.stddev(fractions);
        confidenceLo = mean - (1.96 * stddev) / Math.sqrt(trials);
        confidenceHi = mean + (1.96 * stddev) / Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("arguments: <n> <trials>");
            return;
        }
        PercolationStats stats = new PercolationStats(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = " +
                stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}


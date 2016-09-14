import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final int topIndex; // virtual top site index
    private final int bottomIndex; // virtual bottom site index
    private final WeightedQuickUnionUF uf;
    private int backwash[];
    private final boolean[] opened;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater then 0");
        }
        this.n = n;
        topIndex = 0;
        bottomIndex = n * n + 1;

        // N by N grid plus 2 virtual sites
        uf = new WeightedQuickUnionUF(n * n + 2);

        // all sites are closed by default except two virtual sites
        opened = new boolean[n * n + 2];
        opened[topIndex] = true;
        opened[bottomIndex] = true;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validateIndexes(i, j);
        int index  = n * (i - 1) + j;

        if (opened[index]) return;
        opened[index] = true;

        if (i != 1) {
            if (opened[index - n]) {
                // connect to the top neighbor
                uf.union(index - n, index);
            }
        } else {
            // connect top row site to the virtual top site
            uf.union(topIndex, index);
        }
        if (i != n) {
            if (opened[index + n]) {
                // connect to the bottom neighbor
                uf.union(index + n, index);
            }
        } else
            // connect bottom row site to the virtual bottom site
            uf.union(bottomIndex, index);

        if (j != 1 && opened[index - 1]) {
            // connect to the left neighbour
            uf.union(index - 1, index);
        }
        if (j != n && opened[index + 1]) {
            // connect to the right neighbour
            uf.union(index + 1, index);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validateIndexes(i, j);
        return opened[n * (i - 1) + j];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validateIndexes(i, j);
        return uf.connected(topIndex, n * (i - 1) + j);
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(topIndex, bottomIndex);
    }

    private void validateIndexes(int i, int j) {
        validateIndex(i);
        validateIndex(j);
    }

    private void validateIndex(int i) {
        if (i < 1 || i > n) {
            throw new IndexOutOfBoundsException("index is '" + i + "' " +
                    "but must be between 1 and " + n);
        }
    }

    public static void main(String[] args) {
    }
}
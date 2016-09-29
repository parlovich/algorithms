import edu.princeton.cs.algs4.*;

public class Board {
    private int[][] blocks;
    int n; // dimension

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = blocks;
        n = blocks.length;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int r = 0;
        for (int i = 0; i < n; i++) {
            int k = i == n - 1 ? n - 1 : n;
            for (int j = 0; j < k; j++) {
                if (blocks[i][j] != i * n + j + 1) r++;
            }
        }
        return r;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int r = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * n + j + 1) {
                    r += Math.abs(i - blocks[i][j] / n) + Math.abs(j + 1 - blocks[i][j] % n);
                }
            }
        }
        return r;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        //TODO
        return null;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (!(y instanceof Board)) return false;
        if (n != ((Board) y).dimension()) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != ((Board) y).blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        //TODO
        return null;
    }

    // string representation of this board (in the output format specified below)
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i != 0) s.append("\n");
            for (int j = 0; j < n; j++) {
                if (j != 0) s.append(" ");
                s.append(blocks[i][j] == 0 ? " " : blocks[i][j]);
            }
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] initial = new int [][] {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5},
        };
        Board b = new Board(initial);
        assert b.hamming() == 5;
        assert b.manhattan() == 10;
        StdOut.println(b);
    }
}
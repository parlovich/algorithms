import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] blocks;
    private int n; // dimension

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
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
                    r += Math.abs(i - blocks[i][j] / n) +
                            Math.abs(j + 1 - blocks[i][j] % n);
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
        int i = StdRandom.uniform(n * n - 1);
        while (blocks[i / n][i % n] == 0) i = StdRandom.uniform(n * n - 1);
        int j = StdRandom.uniform(n * n - 1);
        while (i == j || blocks[j / n][j % n] == 0) j = StdRandom.uniform(n * n - 1);
        return createBoard(i / n, i % n, j / n, j % n);
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
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
        List<Board> neighbours = new ArrayList<>();
        int i, j = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n && blocks[i][j] != 0; j++);
            if (j < n) break;
        }

        if (i > 0) neighbours.add(createBoard(i, j, i - 1, j));
        if (i < n - 1) neighbours.add(createBoard(i, j, i + 1, j));
        if (j > 0) neighbours.add(createBoard(i, j, i, j - 1));
        if (j < n - 1) neighbours.add(createBoard(i, j, i, j + 1));

        return neighbours;
    }

    // Create new board from the original one by exchanging to blocks
    private Board createBoard(int i0, int j0, int i1, int j1) {
        int[][] aux = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                aux[i][j] = blocks[i][j];
        int v = aux[i0][j0];
        aux[i0][j0] = aux[i1][j1];
        aux[i1][j1] = v;
        return new Board(aux);
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

        for (Board board : b.neighbors()) {
            StdOut.println("----------");
            StdOut.println(board);
        }

        StdOut.println("Twin:");
        StdOut.println(b.twin());
    }
}
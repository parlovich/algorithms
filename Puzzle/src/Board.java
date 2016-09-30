import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] blocks;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            System.arraycopy(blocks[i], 0, this.blocks[i], 0, blocks.length);
        }
    }

    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int h = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != i * blocks.length + j + 1
                        && blocks[i][j] != 0) h++;
            }
        }
        return h;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int m = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * blocks.length + j + 1) {
                    m += Math.abs(i - (blocks[i][j] - 1) / blocks.length) +
                            Math.abs(j - (blocks[i][j] - 1) % blocks.length);
                }
            }
        }
        return m;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int i = StdRandom.uniform(blocks.length * blocks.length - 1);
        while (blocks[i / blocks.length][i % blocks.length] == 0)
            i = StdRandom.uniform(blocks.length * blocks.length - 1);

        int j = StdRandom.uniform(blocks.length * blocks.length - 1);
        while (i == j || blocks[j / blocks.length][j % blocks.length] == 0)
            j = StdRandom.uniform(blocks.length * blocks.length - 1);

        return createBoard(i / blocks.length, i % blocks.length,
                j / blocks.length, j % blocks.length);
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        if (!y.getClass().equals(Board.class)) return false;
        if (blocks.length != ((Board) y).dimension()) return false;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
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
        for (i = 0; i < blocks.length; i++) {
            j = 0;
            while (j < blocks.length && blocks[i][j] != 0)
                j++;
            if (j < blocks.length) break;
        }

        if (i > 0)
            neighbours.add(createBoard(i, j, i - 1, j));
        if (i < blocks.length - 1)
            neighbours.add(createBoard(i, j, i + 1, j));
        if (j > 0)
            neighbours.add(createBoard(i, j, i, j - 1));
        if (j < blocks.length - 1)
            neighbours.add(createBoard(i, j, i, j + 1));

        return neighbours;
    }

    // Create new board from the original one by exchanging to blocks
    private Board createBoard(int i0, int j0, int i1, int j1) {
        int v0 = blocks[i0][j0];
        int v1 = blocks[i1][j1];
        blocks[i0][j0] = v1;
        blocks[i1][j1] = v0;
        Board board = new Board(blocks);
        blocks[i0][j0] = v0;
        blocks[i1][j1] = v1;
        return board;
    }

    // string representation of this board (in the output format specified below)
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(blocks.length + "\n");
        for (int i = 0; i < blocks.length; i++) {
            if (i != 0) s.append("\n");
            for (int j = 0; j < blocks.length; j++) {
                if (j != 0) s.append(" ");
                s.append(String.format("%2d", blocks[i][j]));
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

        testManhattan();
    }

    private static Board readBoard(String file) {
        In in = new In(file);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        return new Board(blocks);
    }

    private static void testManhattan() {
        assert readBoard("Puzzle/testing/puzzle17.txt").manhattan() == 13;
        assert readBoard("Puzzle/testing/puzzle27.txt").manhattan() == 17;
        assert readBoard("Puzzle/testing/puzzle2x2-unsolvable1.txt").manhattan() == 3;
    }
}
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    private MinPQ<SearchNode> pq;
    SearchNode solution;

    private class SearchNode {
        SearchNode parent;
        Board board;
        int moves;

        public SearchNode(SearchNode parent, Board board, int moves) {
            this.parent = parent;
            this.board = board;
            this.moves = moves;
        }

        public int getPriority() {
            return moves + board.hamming();
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        pq = new MinPQ<>(100, new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return ((Integer)o1.getPriority()).compareTo(o2.getPriority());
            }
        });
        pq.insert(new SearchNode(null, initial, 0));

        solution =  pq.delMin();
        while (!solution.board.isGoal()) {
            for (Board board : solution.board.neighbors()) {
                if (solution.parent != null && solution.parent.board.equals(board))
                    continue;
                pq.insert(new SearchNode(solution, board, solution.moves + 1));
            }
            solution = pq.delMin();
        }
    }

    // is the initial board solvable?
    public boolean isSolvable(){
        //TODO
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        //TODO
        return 0;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        ArrayList<Board> boards = new ArrayList<>();
        SearchNode node = solution;
        while (node != null) {
            boards.add(node.board);
            node = node.parent;
        }
        return boards;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

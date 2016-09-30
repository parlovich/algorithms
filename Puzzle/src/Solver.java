import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    private SearchNode solution = null;

    private final Comparator<SearchNode> nodePriorityComparatorHamming = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int p1 = o1.moves + o1.board.hamming();
            int p2 = o2.moves + o2.board.hamming();
            return Integer.compare(p1, p2);
        }
    };

    private final Comparator<SearchNode> nodePriorityComparatorManhattan = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int p1 = o1.moves + o1.board.manhattan();
            int p2 = o2.moves + o2.board.manhattan();
            return Integer.compare(p1, p2);
        }
    };

    private class SearchNode {
        private SearchNode parent;
        private Board board;
        private int moves;

        public SearchNode(SearchNode parent, Board board, int moves) {
            this.parent = parent;
            this.board = board;
            this.moves = moves;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException("Initial board can't be null");

        MinPQ<SearchNode> mainPq = new MinPQ<>(
                initial.dimension() * initial.dimension() * 4,
                nodePriorityComparatorHamming);
        mainPq.insert(new SearchNode(null, initial, 0));

        MinPQ<SearchNode> auxPq = new MinPQ<>(
                initial.dimension() * initial.dimension() * 4,
                nodePriorityComparatorHamming);
        auxPq.insert(new SearchNode(null, initial.twin(), 0));

        SearchNode mainNode =  mainPq.delMin();
        SearchNode auxNode =  auxPq.delMin();
        while (!mainNode.board.isGoal() && !auxNode.board.isGoal()) {
            for (Board board : mainNode.board.neighbors()) {
                if (mainNode.parent != null && mainNode.parent.board.equals(board))
                    continue;
                mainPq.insert(new SearchNode(mainNode, board, mainNode.moves + 1));
            }
            for (Board board : auxNode.board.neighbors()) {
                if (auxNode.parent != null && auxNode.parent.board.equals(board))
                    continue;
                auxPq.insert(new SearchNode(auxNode, board, auxNode.moves + 1));
            }
            mainNode = mainPq.delMin();
            auxNode = auxPq.delMin();
        }
        if (mainNode.board.isGoal()) solution = mainNode;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution == null ? -1 : solution.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solution == null) return null;

        ArrayList<Board> boards = new ArrayList<>();
        SearchNode node = solution;
        while (node != null) {
            boards.add(0, node.board);
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

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.In;

public class Test1 {

    public static void main(String[] args) {
        In in = new In(args[0]); // open input file
        int n = in.readInt(); // read number of members
        int m = in.readInt(); // read number of timestamps;
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
        String timestamp = null;
        for (int i = 0; i < m; i++) {
            String ts = in.readString();
            int m1 = in.readInt();
            int m2 = in.readInt();
            uf.union(m1, m2);
            // All members are connected when they are all
            // members of one union
            if (uf.count() == 1) {
                timestamp = ts;
                break;
            }
        }
        StdOut.println(timestamp == null ?
                "members haven't gotten connected" :
                "all members get connected at " + timestamp);
    }
}

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Subset {

    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("arguments: <k>");
            return;
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if ("-".equals(s)) break;
            q.enqueue(s);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}

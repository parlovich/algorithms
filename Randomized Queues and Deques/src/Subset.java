import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("arguments: <k>");
            return;
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();

        // j - number of strings read from input
        int j = 1;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if ("-".equals(s)) break;

            if (j <= k) q.enqueue(s);
            else {
                // The likelihood of the strings to be in the queue should be equal to K / N
                // likelihood of the string not be dequeued from the queue on j iteration is 1 - (1/k)(k/j) = (j-1)/j
                // likelihood of the new string to be queued is k/j
                // so after N iterations the strings will stay in the queue with likelihood:
                //          ((n - 1)! / (k - 1)!) / (n! / k!) = k / n
                if (StdRandom.uniform(j) < k) {
                    q.dequeue();
                    q.enqueue(s);
                }
            }
            j++;
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}

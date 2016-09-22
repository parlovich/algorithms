import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

    // finds all line segments containing 4 or more p
    public FastCollinearPoints(Point[] points) {
        //TODO
    }

    // the number of line segments
    public int numberOfSegments() {
            //TODO
        return 0;
    }

    // the line segments
    public LineSegment[] segments() {
        //TODO
        return null;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

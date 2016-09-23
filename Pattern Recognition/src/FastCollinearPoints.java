import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> lineSegments = new ArrayList<>();

    // finds all line segments containing 4 or more p
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("point must not be null");
        }

        Point[] aux = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException("p must not contain null");
            aux[i] = points[i];
        }

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(aux, new SlopComparator(p));

            // Collinear for point p
            ArrayList<Point> collinear = new ArrayList<>();
            // first point should be p itself
            double slop = points[i].slopeTo(aux[0]);
            for (int j = 1; j < aux.length; j++) {

                double s = points[i].slopeTo(aux[j]);

                if (Double.compare(s, Double.NEGATIVE_INFINITY) == 0) {
                    throw new IllegalArgumentException("two identical points");
                }

                if (Double.compare(s, slop) != 0) {
                    if (collinear.size() >= 3) {
                        addLineSegment(points[i], collinear);
                    }
                    collinear.clear();
                    slop = s;
                }

                collinear.add(aux[j]);
            }
            if (collinear.size() >= 3) {
                addLineSegment(points[i], collinear);
            }
        }
    }

    // To avoid duplications
    // add line segment only if p is the first point in the segment
    private void addLineSegment(Point p, List<Point> sortedCollinear) {
        if (p.compareTo(sortedCollinear.get(0)) < 0)
            lineSegments.add(new LineSegment(
                    p, sortedCollinear.get(sortedCollinear.size() - 1)));
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    private class SlopComparator implements Comparator<Point> {

        private Point p;

        public SlopComparator(Point p) {
            this.p = p;
        }

        @Override
        public int compare(Point o1, Point o2) {
            double slop1 = p.slopeTo(o1);
            double slop2 = p.slopeTo(o2);
            if (Double.compare(slop1, slop2) == 0) return o1.compareTo(o2);
            return Double.compare(slop1, slop2);
        }
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
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        StdDraw.setPenColor(StdDraw.BLUE);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> lineSegments = new ArrayList<>();

    // finds all line segments containing 4 p
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("point must not be null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException("p must not contain null");
        }

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                double slop1 = points[i].slopeTo(points[j]);
                if (Double.compare(slop1, Double.NEGATIVE_INFINITY) == 0) {
                    throw new IllegalArgumentException("two identical points");
                }
                for (int k = j + 1; k < points.length - 1; k++) {
                    double slop2 = points[i].slopeTo(points[k]);
                    if (Double.compare(slop2, Double.NEGATIVE_INFINITY) == 0) {
                        throw new IllegalArgumentException("two identical points");
                    }
                    if (Double.compare(slop1, slop2) != 0) continue;
                    for (int t = k + 1; t < points.length; t++) {
                        double slop3 = points[i].slopeTo(points[t]);
                        if (Double.compare(slop3, Double.NEGATIVE_INFINITY) == 0) {
                            throw new IllegalArgumentException("two identical points");
                        }
                        if (Double.compare(slop2, slop3) == 0) {
                            Point[] segmentPoints = new Point[]{
                                    points[i], points[j], points[k], points[t]};
                            Arrays.sort(segmentPoints);
                            lineSegments.add(new LineSegment(
                                    segmentPoints[0], segmentPoints[3]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    Point[] p;
    List<LineSegment> lineSegments = new ArrayList<>();

    // finds all line segments containing 4 p
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("point must not be null");
        }
        p = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException("p must not contain null");
            p[i] = points[i];
        }

        Arrays.sort(p);

        for (int i = 0; i < p.length - 3; i++) {
            for (int j = i + 1; j < p.length - 2; j++) {
                double slop1 = p[i].slopeTo(p[j]);
                for (int k = j + 1; k < p.length - 1; k++) {
                    double slop2 = p[i].slopeTo(p[k]);
                    if (slop1 != slop2) continue;
                    for (int t = k + 1; t < p.length; t++) {
                        double slop3 = p[i].slopeTo(p[t]);
                        if (slop2 == slop3) {
                            lineSegments.add(new LineSegment(p[i], p[t]));
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

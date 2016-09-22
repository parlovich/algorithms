
public class BruteCollinearPoints {

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("point must not be null");
        }
        for (int i = 0; i <= points.length; i++) {
            if (points[i] == null) throw new NullPointerException("points must not contain null");
        }

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
}

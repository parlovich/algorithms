import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    // is the set empty?
    public boolean isEmpty() {
        //TODO
        return true;
    }

    // number of points in the set
    public int size() {
        //TODO
        return 0;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        //TODO
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        //TODO
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        //TODO
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        //TODO
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        //TODO
        return null;
    }

    public static void main(String[] args) {

    }

}

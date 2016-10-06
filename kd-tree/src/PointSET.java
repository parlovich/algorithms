import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> tree;

    // construct an empty set of points
    public PointSET() {
        tree = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    // number of points in the set
    public int size() {
        return tree.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        tree.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return tree.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for(Point2D p : tree) p.draw();
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        List<Point2D> points = new ArrayList<>();
        for (Point2D p : tree) {
            if (rect.contains(p)) points.add(p);
        }
        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (tree.isEmpty()) return null;
        Iterator<Point2D> i = tree.iterator();
        Point2D nrst = i.next();
        double distance = p.distanceTo(nrst);
        while(i.hasNext()) {
            Point2D aux = i.next();
            if (p.distanceTo(aux) < distance) {
                nrst = aux;
                distance = p.distanceTo(aux);
            }
        }
        return nrst;
    }

    public static void main(String[] args) {

    }
}


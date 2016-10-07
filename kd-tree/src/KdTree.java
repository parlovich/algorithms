import edu.princeton.cs.algs4.*;

import java.awt.*;

public class KdTree {

    private Node root = null;

    private class Node {
        private Point2D key;        // sorted by key
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree
        private boolean vertical;

        public Node(Point2D key, int size, boolean vertical) {
            this.key = key;
            this.size = size;
            this.vertical = vertical;
        }
    }

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        root = put(root, p, true);
    }

    private Node put(Node x, Point2D key, boolean vertical) {
        if (x == null) return new Node(key, 1, vertical);

        int cmp = compare(key, x.key, vertical);
        if (cmp < 0)
            x.left = put(x.left, key, !vertical);
        else if (cmp > 0)
            x.right = put(x.right, key, !vertical);
//        else
//            x.val = val;

        x.size = 1 + size(x.left) + size(x.right);

        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return get(p) != null;
    }

    public Node get(Point2D p) {
        return get(root, p);
    }

    private Node get(Node n, Point2D key) {
        if (n == null) return null;

        int cmp = compare(key, n.key, n.vertical);
        if (cmp < 0)
            return get(n.left, key);
        else if (cmp > 0)
            return get(n.right, key);
        else
            return n;
    }

    private int compare(Point2D p1, Point2D p2, boolean vertical) {
        if (vertical) {
            if (p1.x() < p2.x()) return -1;
            if (p1.x() > p2.x()) return +1;
            if (p1.y() < p2.y()) return -1;
            if (p1.y() > p2.y()) return +1;
        } else {
            if (p1.y() < p2.y()) return -1;
            if (p1.y() > p2.y()) return +1;
            if (p1.x() < p2.x()) return -1;
            if (p1.x() > p2.x()) return +1;
        }
        return 0;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, new RectHV(0, 0, 1, 1));
    }

    private void draw(Node n, RectHV r) {
        if (n == null) return;

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(Color.BLACK);
        n.key.draw();

        StdDraw.setPenRadius(0.001);
        if (n.vertical) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.line(n.key.x(), r.ymin(), n.key.x(), r.ymax());
            draw(n.left, new RectHV(r.xmin(), r.ymin(), n.key.x(), r.ymax()));
            draw(n.right, new RectHV(n.key.x(), r.ymin(), r.xmax(), r.ymax()));
        } else {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.line(r.xmin(), n.key.y(), r.xmax(), n.key.y());
            draw(n.left, new RectHV(r.xmin(), r.ymin(), r.xmax(), n.key.y()));
            draw(n.right, new RectHV(r.xmin(), n.key.y(), r.xmax(), r.ymax()));
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();

        Queue<Point2D> queue = new Queue<Point2D>();
        range(root, queue, new RectHV(0, 0, 1, 1), rect);
        return queue;
    }

    private void range(Node n, Queue<Point2D> queue, RectHV curRect, RectHV qRect) {
        if (n == null) return;
        if (qRect.contains(n.key)) queue.enqueue(n.key);

        RectHV aux;
        if (n.vertical) {
            aux = new RectHV(curRect.xmin(), curRect.ymin(), n.key.x(), curRect.ymax());
            if (qRect.intersects(aux))
                range(n.left, queue, aux, qRect);
            aux = new RectHV(n.key.x(), curRect.ymin(), curRect.xmax(), curRect.ymax());
            if (qRect.intersects(aux))
                range(n.right, queue, aux, qRect);
        } else {
            aux = new RectHV(curRect.xmin(), curRect.ymin(), curRect.xmax(), n.key.y());
            if (qRect.intersects(aux))
                range(n.left, queue, aux, qRect);
            aux = new RectHV(curRect.xmin(), n.key.y(), curRect.xmax(), curRect.ymax());
            if (qRect.intersects(aux))
                range(n.right, queue, aux, qRect);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (root == null) return null;
        return nearest(root, p, new RectHV(0, 0, 1, 1));
    }

    private Point2D nearest(Node n, Point2D q, RectHV curRect) {
        if (n == null) return null;
        Point2D closest = n.key;

        RectHV lRec;
        RectHV rRec;
        if (n.vertical) {
            lRec = new RectHV(curRect.xmin(), curRect.ymin(), n.key.x(), curRect.ymax());
            rRec = new RectHV(n.key.x(), curRect.ymin(), curRect.xmax(), curRect.ymax());
        } else {
            lRec = new RectHV(curRect.xmin(), curRect.ymin(), curRect.xmax(), n.key.y());
            rRec = new RectHV(curRect.xmin(), n.key.y(), curRect.xmax(), curRect.ymax());
        }

        if (lRec.contains(q)) {
            if (lRec.distanceTo(q) < closest.distanceTo(q)) {
                Point2D aux = nearest(n.left, q, lRec);
                if (aux != null && aux.distanceSquaredTo(q) < closest.distanceSquaredTo(q)) closest = aux;
            }
            if (rRec.distanceTo(q) < closest.distanceTo(q)) {
                Point2D aux = nearest(n.right, q, rRec);
                if (aux != null && aux.distanceSquaredTo(q) < closest.distanceSquaredTo(q)) closest = aux;
            }
        } else {
            if (rRec.distanceTo(q) < closest.distanceTo(q)) {
                Point2D aux = nearest(n.right, q, rRec);
                if (aux != null && aux.distanceSquaredTo(q) < closest.distanceSquaredTo(q)) closest = aux;
            }
            if (lRec.distanceTo(q) < closest.distanceTo(q)) {
                Point2D aux = nearest(n.left, q, lRec);
                if (aux != null && aux.distanceSquaredTo(q) < closest.distanceSquaredTo(q)) closest = aux;
            }
        }

        return closest;
    }



    public static void main(String[] args) {
    }
}

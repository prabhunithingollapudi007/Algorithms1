import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

	private KdTreeNode root;
	private int size;

	private class KdTreeNode implements Comparable<KdTreeNode> {
		private boolean isX;
		private Point2D point2d;
		private KdTreeNode left;
		private KdTreeNode right;
		private int count;

		@Override
		public String toString() {
			return "KdTreeNode [isX=" + isX + ", point2d=" + point2d + ", left=" + left + ", right=" + right
					+ ", count=" + count + "]";
		}

		public KdTreeNode(boolean isX, Point2D point2d) {
			this.isX = isX;
			this.point2d = point2d;
			this.left = null;
			this.right = null;
			this.count = 0;
		}

		@Override
		public int compareTo(KdTreeNode o) {

			if (this.isX) {
				if (this.point2d.x() < o.point2d.x())
					return -1;
				else if (this.point2d.x() > o.point2d.x())
					return 1;
				return 0;
			}

			if (this.point2d.y() < o.point2d.y())
				return -1;
			else if (this.point2d.y() > o.point2d.y())
				return 1;
			return 0;

		}
	}

	public KdTree() {
		this.root = null;
		this.size = 0;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return this.size;
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		root = insertAtNode(root, p, true);
		this.size += 1;
	}

	private KdTreeNode insertAtNode(KdTreeNode x, Point2D point2d, boolean b) {

		KdTreeNode newNode = new KdTreeNode(b, point2d);

		if (x == null) {
			return newNode;
		}
		int cmp = newNode.compareTo(x);
		if (cmp < 0) {
			x.left = insertAtNode(x.left, newNode.point2d, !b);
		} else if (cmp > 0) {
			x.right = insertAtNode(x.right, newNode.point2d, !b);
		} else {
			int internalCmp = point2d.compareTo(x.point2d);
			if (internalCmp < 0)
				x.left = insertAtNode(x.left, newNode.point2d, !b);
			else if (internalCmp > 0)
				x.right = insertAtNode(x.right, newNode.point2d, !b);
			else {
				x.point2d = point2d;
				this.size -= 1;
			}

		}

		x.count = 1 + nodeSize(x.left) + nodeSize(x.right);
		return x;
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		return contains(root, p, true);
	}

	private boolean contains(KdTreeNode curr, Point2D p, boolean b) {
		if (curr == null)
			return false;
		KdTreeNode newNode = new KdTreeNode(b, p);

		int cmp = newNode.compareTo(curr);
		if (cmp == 0) {
			int internalCmp = p.compareTo(curr.point2d);
			if (internalCmp < 0)
				return contains(curr.left, p, !b);
			else if (internalCmp > 0)
				return contains(curr.right, p, !b);
			return true;
		} else if (cmp < 0)
			return contains(curr.left, p, !b);
		return contains(curr.right, p, !b);
	}

	private int nodeSize(KdTreeNode kdTreeNode) {
		if (kdTreeNode == null)
			return 0;
		return kdTreeNode.count;
	}

	public void draw() {
		KdTreeNode curr = root;
		RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
		drawNode(curr, rect);
	}

	private void drawNode(KdTreeNode curr, RectHV rect) {
		if (curr == null)
			return;
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.03);

		// curr.point2d.draw();
		StdDraw.point(curr.point2d.x(), curr.point2d.y());
		StdDraw.setPenRadius(0.01);
		if (curr.isX) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(curr.point2d.x(), rect.ymin(), curr.point2d.x(), rect.ymax());
			drawNode(curr.left, new RectHV(rect.xmin(), rect.ymin(), curr.point2d.x(), rect.ymax()));
			drawNode(curr.right, new RectHV(curr.point2d.x(), rect.ymin(), rect.xmax(), rect.ymax()));
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(rect.xmin(), curr.point2d.y(), rect.xmax(), curr.point2d.y());
			drawNode(curr.left, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), curr.point2d.y()));
			drawNode(curr.right, new RectHV(rect.xmin(), curr.point2d.y(), rect.xmax(), rect.ymax()));

		}

	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new IllegalArgumentException();

		ArrayList<Point2D> tempList = new ArrayList<>();
		tempList = getNodesRange(root, rect, tempList);
		return new PointIterator(tempList);

	}

	private ArrayList<Point2D> getNodesRange(KdTreeNode curr, RectHV rect, ArrayList<Point2D> tempList) {

		if (curr == null)
			return tempList;
		if (rect.contains(curr.point2d)) {
			tempList.add(curr.point2d);
			tempList = getNodesRange(curr.left, rect, tempList);
			tempList = getNodesRange(curr.right, rect, tempList);
		} else {
			if (curr.isX) {
				if (rect.xmax() < curr.point2d.x()) {
					tempList = getNodesRange(curr.left, rect, tempList);
					return tempList;
				}
				if (rect.xmin() > curr.point2d.x()) {
					tempList = getNodesRange(curr.right, rect, tempList);
					return tempList;
				}
				tempList = getNodesRange(curr.left, rect, tempList);
				tempList = getNodesRange(curr.right, rect, tempList);
			} else {
				if (rect.ymax() < curr.point2d.y()) {
					tempList = getNodesRange(curr.left, rect, tempList);
					return tempList;
				}
				if (rect.ymin() > curr.point2d.y()) {
					tempList = getNodesRange(curr.right, rect, tempList);
					return tempList;
				}
				tempList = getNodesRange(curr.left, rect, tempList);
				tempList = getNodesRange(curr.right, rect, tempList);
			}

		}
		return tempList;
	}

	private class PointIterator implements Iterable<Point2D> {

		private ArrayList<Point2D> tempList = new ArrayList<>();

		public PointIterator(ArrayList<Point2D> tempList2) {
			this.tempList = tempList2;
		}

		@Override
		public Iterator<Point2D> iterator() {

			return tempList.iterator();
		}

	}

	/*
	 * public void dummy() { dummy(root); }
	 * 
	 * private void dummy(KdTreeNode curr) { if (curr == null) return;
	 * 
	 * dummy(curr.left); System.out.println(" points" + curr.point2d);
	 * dummy(curr.right); }
	 */

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		return nearest(p, root, root);
	}

	private Point2D nearest(Point2D p, KdTreeNode curr, KdTreeNode nearestSoFar) {
		if (curr == null)
			return nearestSoFar.point2d;
		double tempDistance = p.distanceSquaredTo(curr.point2d);
		double ref = p.distanceSquaredTo(nearestSoFar.point2d);
		if (tempDistance <= ref) {
			nearestSoFar = curr;
		}
		Point2D p1 = nearest(p, curr.left, nearestSoFar);
		Point2D p2 = nearest(p, curr.right, nearestSoFar);
		if (p1.distanceSquaredTo(p) < p2.distanceSquaredTo(p)) {
			return p1;
		}
		return p2;
	}

	public static void main(String[] args) {

	}
}

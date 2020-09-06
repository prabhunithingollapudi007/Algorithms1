import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	private Point[] points;
	private LineSegment[] lineSegments;

	public FastCollinearPoints(Point[] points) {
		if (points == null)
			throw new IllegalArgumentException();

		this.points = points;
		this.lineSegments = null;
		checkNulls();
		Arrays.sort(points);
		this.checkDuplicates();
		// Arrays.sort(points, points[0].slopeOrder());
		this.mapPoints();
	}

	private void mapPoints() {
		int n = points.length;
		ArrayList<LineSegment> al = new ArrayList<LineSegment>();
		Point[] copyPoints = points.clone();
		// Arrays.sort(points);
		for (int curr = 0; curr < n - 3; curr++) {
			Arrays.sort(copyPoints);
			Arrays.sort(copyPoints, copyPoints[curr].slopeOrder());

			for (int first = 1, last = 2; last < copyPoints.length; last++) {
				while (last < n
						&& Double.compare(copyPoints[0].slopeTo(copyPoints[first]), copyPoints[0].slopeTo(copyPoints[last])) == 0) {
					last++;
				}
				if (last - first >= 3 && copyPoints[0].compareTo(copyPoints[first]) < 0) {
					al.add(new LineSegment(copyPoints[0], copyPoints[last - 1]));
				}
				// Try to find next
				first = last;
			}
		}
		this.lineSegments = al.toArray(new LineSegment[al.size()]);
	}

	private void checkNulls() {
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new IllegalArgumentException();
		}
	}

	private void checkDuplicates() {
		Point temp = points[0];
		for (int i = 1; i < points.length; i++) {
			if (points[i].compareTo(temp) == 0)
				throw new IllegalArgumentException();
			temp = points[i];
		}
	}

	public int numberOfSegments() {
		return lineSegments.length;

	}

	public LineSegment[] segments() {
		return lineSegments;
	}
}

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int BLOCKED = 0;
    private static final int OPEN = 1;
    private static final int TOP = 0;
    private final int bottom;
    private final WeightedQuickUnionUF sites;
    private final int[] openCloseTableOfSites;
    private final int count;
    private int countOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("invalid count, count must be > 0");
        }
        this.sites = new WeightedQuickUnionUF(n * n + 2);
        this.count = n;
        this.bottom = n * n + 1;
        openCloseTableOfSites = new int[count * count + 2];
        for (int i = 1; i <= count * count; i++)
            openCloseTableOfSites[i] = BLOCKED;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            openCloseTableOfSites[transform2Dto1DIndices(row, col, 0, 0)] = OPEN;
            if (row == 1) {
                sites.union(transform2Dto1DIndices(row, col, 0, 0), TOP);
            }
            if (row == count) {
                sites.union(transform2Dto1DIndices(row, col, 0, 0), bottom);
            }
            if (openCloseTableOfSites[transform2Dto1DIndices(row, col, -1, 0)] == OPEN) {
                sites.union(transform2Dto1DIndices(row, col, -1, 0), transform2Dto1DIndices(row, col, 0, 0));
            }
            if (openCloseTableOfSites[transform2Dto1DIndices(row, col, 1, 0)] == OPEN) {
                sites.union(transform2Dto1DIndices(row, col, 1, 0), transform2Dto1DIndices(row, col, 0, 0));
            }
            if (openCloseTableOfSites[transform2Dto1DIndices(row, col, 0, 1)] == OPEN) {
                sites.union(transform2Dto1DIndices(row, col, 0, 1), transform2Dto1DIndices(row, col, 0, 0));
            }
            if (openCloseTableOfSites[transform2Dto1DIndices(row, col, 0, -1)] == OPEN) {
                sites.union(transform2Dto1DIndices(row, col, 0, -1), transform2Dto1DIndices(row, col, 0, 0));
            }
            ++countOpenSites;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openCloseTableOfSites[transform2Dto1DIndices(row, col, 0, 0)] == OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return sites.find(TOP) == sites.find(transform2Dto1DIndices(row, col, 0, 0));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return sites.find(TOP) == sites.find(bottom);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > this.count || col < 1 || col > this.count) {
            throw new IllegalArgumentException("invalid index");
        }
    }

    private int transform2Dto1DIndices(int row, int col, int deltaRow, int deltaCol) {
        int tempRow = row - 1 + deltaRow;
        int tempCol = col + deltaCol;
        if (tempRow < 0 || tempRow >= count || tempCol < 1 || tempCol > count) {
            return (row - 1) * count + col;
        }
        return tempRow * count + tempCol;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}


import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TvarBloku {
    private boolean[][] shape;
    private int rotacia;
    private String typ;
    private Color farba;
    private int poziciaX;
    private int poziciaY;


    public static final List<ShapeDef> SHAPES = Arrays.asList(
            new ShapeDef("I", new boolean[][]{
                    { false, false, false, false },
                    { true,  true,  true,  true  },
                    { false, false, false, false },
                    { false, false, false, false }
            }, Color.CYAN),
            new ShapeDef("J", new boolean[][]{
                    { true,  false, false },
                    { true,  true,  true  },
                    { false, false, false }
            }, Color.BLUE),
            new ShapeDef("L", new boolean[][]{
                    { false, false, true },
                    { true,  true,  true  },
                    { false, false, false }
            }, Color.ORANGE),
            new ShapeDef("O", new boolean[][]{
                    { true, true },
                    { true, true }
            }, Color.YELLOW),
            new ShapeDef("S", new boolean[][]{
                    { false, true,  true  },
                    { true,  true,  false },
                    { false, false, false }
            }, Color.GREEN),
            new ShapeDef("T", new boolean[][]{
                    { false, true,  false },
                    { true,  true,  true  },
                    { false, false, false }
            }, new Color(128, 0, 128)),
            new ShapeDef("Z", new boolean[][]{
                    { true,  true,  false },
                    { false, true,  true  },
                    { false, false, false }
            }, Color.RED)
    );

    public static class ShapeDef {
        public final String name;
        public final boolean[][] mask;
        public final Color color;
        public ShapeDef(String n, boolean[][] m, Color c) { name = n; mask = m; color = c; }
    }

    public TvarBloku(ShapeDef def) {
        int h = def.mask.length;
        int w = def.mask[0].length;
        int size = Math.max(4, Math.max(w, h));
        shape = new boolean[size][size];
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                shape[r][c] = def.mask[r][c];
            }
        }
        this.rotacia = 0;
        this.typ = def.name;
        this.farba = def.color;
        this.poziciaX = 0;
        this.poziciaY = 0;
    }

    public static TvarBloku randomTvar(Random rnd) {
        ShapeDef d = SHAPES.get(rnd.nextInt(SHAPES.size()));
        return new TvarBloku(d);
    }

    public void setPozicia(int x, int y) { poziciaX = x; poziciaY = y; }
    public int getX() { return poziciaX; }
    public int getY() { return poziciaY; }
    public Color getFarba() { return farba; }
    public String getTyp() { return typ; }

    public void otocVpravo() {
        shape = rotateRight(shape);
        rotacia = (rotacia + 1) % 4;
    }

    public void otocVlavo() {
        shape = rotateLeft(shape);
        rotacia = (rotacia + 3) % 4;
    }

    private boolean[][] rotateRight(boolean[][] mat) {
        int n = mat.length;
        boolean[][] res = new boolean[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                res[c][n - 1 - r] = mat[r][c];
            }
        }
        return res;
    }

    private boolean[][] rotateLeft(boolean[][] mat) {
        int n = mat.length;
        boolean[][] res = new boolean[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                res[n - 1 - c][r] = mat[r][c];
            }
        }
        return res;
    }

    public void posunVpravo() { poziciaX++; }
    public void posunVlavo() { poziciaX--; }
    public void posunVdole() { poziciaY++; }
    public void posunHore() { poziciaY--; }

    public java.util.List<Point> getBlocksAbsolute() {
        java.util.List<Point> out = new ArrayList<>();
        int n = shape.length;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (shape[r][c]) {
                    int x = poziciaX + c;
                    int y = poziciaY + r;
                    out.add(new Point(x, y));
                }
            }
        }
        return out;
    }

    public TvarBloku copy() {
        TvarBloku t = new TvarBloku(new ShapeDef(this.typ, extractMask(), this.farba));
        t.poziciaX = this.poziciaX;
        t.poziciaY = this.poziciaY;
        t.rotacia = this.rotacia;
        return t;
    }

    private boolean[][] extractMask() {
        int n = shape.length;
        boolean[][] m = new boolean[n][n];
        for (int r = 0; r < n; r++) m[r] = Arrays.copyOf(shape[r], n);
        return m;
    }


}

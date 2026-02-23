import java.awt.Point;
import java.util.Arrays;

public class HernaPlocha {
    private int sirka;
    private int vyska;
    private int pocetPlnychRiadkov;
    private boolean[][] plocha;

    public HernaPlocha(int sirka, int vyska) {
        this.sirka = sirka;
        this.vyska = vyska;
        this.plocha = new boolean[vyska][sirka];
        this.pocetPlnychRiadkov = 0;
    }

    public int getSirka() { return sirka; }
    public int getVyska() { return vyska; }

    public boolean koliziaNadHernouPlochou(TvarBloku tvar) {
        for (Point p : tvar.getBlocksAbsolute()) {
            if (p.y < 0) return true;
        }
        return false;
    }

    public boolean kolizia(TvarBloku tvar) {
        for (Point p : tvar.getBlocksAbsolute()) {
            int x = p.x;
            int y = p.y;
            if (x < 0 || x >= sirka) return true;
            if (y >= vyska) return true;
            if (y < 0) continue;
            if (plocha[y][x]) return true;
        }
        return false;
    }

    public void uzamknutTvar(TvarBloku tvar) {
        for (Point p : tvar.getBlocksAbsolute()) {
            int x = p.x;
            int y = p.y;
            if (y >= 0 && y < vyska && x >= 0 && x < sirka) {
                plocha[y][x] = true;
            }
        }
    }

    public int odstranitPlneRiadky() {
        int removed = 0;
        for (int r = vyska - 1; r >= 0; r--) {
            boolean plny = true;
            for (int c = 0; c < sirka; c++) {
                if (!plocha[r][c]) { plny = false; break; }
            }
            if (plny) {
                for (int rr = r; rr > 0; rr--) {
                    plocha[rr] = Arrays.copyOf(plocha[rr - 1], sirka);
                }
                plocha[0] = new boolean[sirka];
                removed++;
                r++;
            }
        }
        this.pocetPlnychRiadkov = removed;
        return removed;
    }

    public boolean jeRiadokPlny(int r) {
        if (r < 0 || r >= vyska) return false;
        for (int c = 0; c < sirka; c++) if (!plocha[r][c]) return false;
        return true;
    }

    public boolean[][] getPlochaSnapshot() {
        boolean[][] copy = new boolean[vyska][sirka];
        for (int r = 0; r < vyska; r++) copy[r] = Arrays.copyOf(plocha[r], sirka);
        return copy;
    }

    public int getPocetPlnychRiadkov() { return pocetPlnychRiadkov; }

    public void reset() {
        this.plocha = new boolean[vyska][sirka];
        this.pocetPlnychRiadkov = 0;
    }
}

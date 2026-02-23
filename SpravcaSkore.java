public class SpravcaSkore {
    private int skore;
    private int pocetRiadkov;


    public SpravcaSkore() { reset(); }

    public void pridajRiadky(int pocet) {
        if (pocet <= 0) return;
        int pridane;
        switch (pocet) {
            case 1: pridane = 100; break;
            case 2: pridane = 300; break;
            case 3: pridane = 700; break;
            default: pridane = 1500 * (pocet / 4); break;
        }
        skore += pridane;
        pocetRiadkov += pocet;
    }

    public void reset() {
        skore = 0;
        pocetRiadkov = 0;
    }

    public int getSkore() { return skore; }
    public int getPocetRiadkov() { return pocetRiadkov; }


}

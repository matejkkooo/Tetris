/*
HraTetris.java
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;

public class HraTetris extends JPanel implements ActionListener {
    private TvarBloku aktualnyTvar;
    private TvarBloku dalsiTvar;
    private HernaPlocha plocha;
    private OvladanieHry ovladanie;
    private SpravcaSkore spravcaSkore;
    private boolean hraBezi;
    private int rychlostPadu;
    private Timer tikovac;
    private Random rnd = new Random();


    private final int BLOCK = 30;
    private final int PANEL_PADDING = 20;

    public HraTetris() {
        plocha = new HernaPlocha(10, 20);
        ovladanie = new OvladanieHry();
        spravcaSkore = new SpravcaSkore();
        rychlostPadu = 500;
        hraBezi = false;

        setPreferredSize(new Dimension(plocha.getSirka() * BLOCK + 200, plocha.getVyska() * BLOCK + PANEL_PADDING * 2));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        initKeyBindings();

        aktualnyTvar = null;
        dalsiTvar = TvarBloku.randomTvar(rnd);

        tikovac = new Timer(rychlostPadu, this);
    }

    public void spustit() {
        plocha.reset();
        spravcaSkore.reset();
        aktualnyTvar = null;
        dalsiTvar = TvarBloku.randomTvar(rnd);
        hraBezi = true;
        tikovac.setDelay(rychlostPadu);
        tikovac.start();
        spawnNext();
    }

    public void pozastavit() {
        hraBezi = false;
        tikovac.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!hraBezi) return;
        if (ovladanie.isVavo()) {
            attemptMove(-1, 0);
            ovladanie.pustVlavo();
        }
        if (ovladanie.isVpravo()) {
            attemptMove(1, 0);
            ovladanie.pustVpravo();
        }
        if (ovladanie.isOtocenie()) {
            attemptRotate();
            ovladanie.pustOtocenie();
        }

        if (ovladanie.isDrop()) {
            while (true) {
                if (!attemptMove(0, 1)) {
                    lockCurrent();
                    break;
                }
            }
            ovladanie.pustDrop();
        } else {
            if (!attemptMove(0, 1)) {
                lockCurrent();
            }
        }

        repaint();
    }

    public void spawnNext() {
        aktualnyTvar = dalsiTvar;
        int startX = (plocha.getSirka() - 4) / 2;
        aktualnyTvar.setPozicia(startX, -1);
        dalsiTvar = TvarBloku.randomTvar(rnd);

        if (plocha.kolizia(aktualnyTvar)) {
            tikovac.stop();
            hraBezi = false;
            SwingUtilities.invokeLater(() -> {
                int opt = JOptionPane.showOptionDialog(this,
                        "Koniec hry. Skóre: " + spravcaSkore.getSkore() + "\nSpustiť znova?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null, null, null);
                if (opt == JOptionPane.YES_OPTION) {
                    spustit();
                } else {
                    System.exit(0);
                }
            });
        }
    }

    private void lockCurrent() {
        plocha.uzamknutTvar(aktualnyTvar);
        int removed = plocha.odstranitPlneRiadky();
        if (removed > 0) {
            spravcaSkore.pridajRiadky(removed);
            int level = spravcaSkore.getPocetRiadkov() / 10;
            int newDelay = Math.max(100, rychlostPadu - level * 50);
            tikovac.setDelay(newDelay);
        }
        spawnNext();
    }

    private boolean attemptMove(int dx, int dy) {
        if (aktualnyTvar == null) return false;
        TvarBloku copy = aktualnyTvar.copy();
        copy.setPozicia(aktualnyTvar.getX() + dx, aktualnyTvar.getY() + dy);
        if (!plocha.kolizia(copy)) {
            aktualnyTvar.setPozicia(copy.getX(), copy.getY());
            return true;
        }
        return false;
    }

    private void attemptRotate() {
        if (aktualnyTvar == null) return;
        TvarBloku copy = aktualnyTvar.copy();
        copy.otocVpravo();
        if (!plocha.kolizia(copy)) {
            aktualnyTvar.otocVpravo();
            return;
        }
        copy.setPozicia(copy.getX() + 1, copy.getY());
        if (!plocha.kolizia(copy)) {
            aktualnyTvar.setPozicia(aktualnyTvar.getX() + 1, aktualnyTvar.getY());
            aktualnyTvar.otocVpravo();
            return;
        }
        copy.setPozicia(aktualnyTvar.getX() - 1, aktualnyTvar.getY());
        if (!plocha.kolizia(copy)) {
            aktualnyTvar.setPozicia(aktualnyTvar.getX() - 1, aktualnyTvar.getY());
            aktualnyTvar.otocVpravo();
            return;
        }
    }

    private void initKeyBindings() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "leftPressed");
        getActionMap().put("leftPressed", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { ovladanie.stlacVlavo(); }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "rightPressed");
        getActionMap().put("rightPressed", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { ovladanie.stlacVpravo(); }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "rotate");
        getActionMap().put("rotate", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { ovladanie.stlacOtocenie(); }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "drop");
        getActionMap().put("drop", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { ovladanie.stlacDrop(); }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "leftReleased");
        getActionMap().put("leftReleased", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { ovladanie.pustVlavo(); }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "rightReleased");
        getActionMap().put("rightReleased", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { ovladanie.pustVpravo(); }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released UP"), "rotateReleased");
        getActionMap().put("rotateReleased", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { ovladanie.pustOtocenie(); }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released SPACE"), "dropReleased");
        getActionMap().put("dropReleased", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { ovladanie.pustDrop(); }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('P'), "pause");
        getActionMap().put("pause", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (hraBezi) pozastavit(); else spustit();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        int offsetX = PANEL_PADDING;
        int offsetY = PANEL_PADDING;

        g2.setColor(Color.BLACK);
        g2.fillRect(offsetX, offsetY, plocha.getSirka() * BLOCK, plocha.getVyska() * BLOCK);

        boolean[][] snap = plocha.getPlochaSnapshot();
        for (int r = 0; r < plocha.getVyska(); r++) {
            for (int c = 0; c < plocha.getSirka(); c++) {
                int x = offsetX + c * BLOCK;
                int y = offsetY + r * BLOCK;
                if (snap[r][c]) {
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillRect(x+1, y+1, BLOCK-2, BLOCK-2);
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawRect(x, y, BLOCK, BLOCK);
                } else {
                    g2.setColor(Color.GRAY);
                    g2.drawRect(x, y, BLOCK, BLOCK);
                }
            }
        }

        if (aktualnyTvar != null) {
            g2.setColor(aktualnyTvar.getFarba());
            for (Point p : aktualnyTvar.getBlocksAbsolute()) {
                int x = offsetX + p.x * BLOCK;
                int y = offsetY + p.y * BLOCK;
                if (p.y >= 0) {
                    g2.fillRect(x+1, y+1, BLOCK-2, BLOCK-2);
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawRect(x, y, BLOCK, BLOCK);
                    g2.setColor(aktualnyTvar.getFarba());
                }
            }
        }

        int panelX = offsetX + plocha.getSirka() * BLOCK + 20;
        int panelY = offsetY;
        g2.setColor(Color.WHITE);
        g2.drawString("Skóre: " + spravcaSkore.getSkore(), panelX, panelY + 20);
        g2.drawString("Riadky: " + spravcaSkore.getPocetRiadkov(), panelX, panelY + 40);
        g2.drawString("Rýchlosť: " + tikovac.getDelay() + " ms", panelX, panelY + 60);
        g2.drawString("Next:", panelX, panelY + 100);

        if (dalsiTvar != null) {
            int nx = panelX;
            int ny = panelY + 110;
            TvarBloku t2 = dalsiTvar.copy();
            t2.setPozicia(0,0);
            g2.setColor(t2.getFarba());
            for (Point p2 : t2.getBlocksAbsolute()) {
                int x = nx + (p2.x + 1) * (BLOCK/2);
                int y = ny + (p2.y + 1) * (BLOCK/2);
                g2.fillRect(x, y, BLOCK/2 - 2, BLOCK/2 - 2);
            }
        }
        g2.dispose();
    }

    public boolean kontrolaKoniecHry() {
        for (int c = 0; c < plocha.getSirka(); c++) {
            if (plocha.getPlochaSnapshot()[0][c] || plocha.getPlochaSnapshot()[1][c]) return true;
        }
        return false;
    }

    public void startGame() { spustit(); }
    public void stopGame() { pozastavit(); }


}

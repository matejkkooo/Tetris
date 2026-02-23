import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow() {
        super("TETRIS");

        HraTetris hra = new HraTetris();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(hra);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        int opt = JOptionPane.showOptionDialog(
                this,
                "Spustiť hru TETRIS?",
                "TETRIS",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null
        );

        if (opt == JOptionPane.YES_OPTION) {
            hra.startGame();
        } else {
            System.exit(0);
        }
    }
}

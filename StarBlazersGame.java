import javax.swing.*;

public class StarBlazersGame extends JFrame {

    public StarBlazersGame() {
        setTitle("StarBlazers Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(new GamePanel());
        pack(); // Adjusts window size to fit GamePanel
        setLocationRelativeTo(null); // Center on screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StarBlazersGame game = new StarBlazersGame();
            game.setVisible(true);
        });
    }
}

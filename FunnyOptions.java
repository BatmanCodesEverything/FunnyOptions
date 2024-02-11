import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class FunnyOptions {
    private static final int MAX_ATTEMPTS = 20;
    private static int attempts = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BatmanCodes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            JLabel questionLabel = new JLabel("Are you stupid?");
            
            
            Font boldFont = new Font(questionLabel.getFont().getName(), Font.BOLD, 24); // Adjust the font size as needed
            questionLabel.setFont(boldFont);

            JButton yesButton = createRoundedButton("Yes");
            JButton noButton = createRoundedButton("No");

            panel.setLayout(new FlowLayout());
            panel.add(questionLabel);
            panel.add(yesButton);
            panel.add(noButton);

            yesButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "You really are stupid, hehehe!!!");
                System.exit(0);
            });

            noButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (attempts < MAX_ATTEMPTS) {
                        int newSize = noButton.getWidth() - 5;
                        if (newSize > 0) {
                            noButton.setSize(newSize, noButton.getHeight());
                            int x = (int) (Math.random() * (panel.getWidth() - newSize));
                            int y = (int) (Math.random() * (panel.getHeight() - newSize));
                            noButton.setLocation(x, y);
                            attempts++;
                        } else {
                            noButton.setVisible(false);
                        }
                    }
                }
            });

            // Set panel background color to change gradually
            Timer timer = new Timer(200, new ActionListener() {
                private Color currentColor = panel.getBackground();
                private Color targetColor;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (targetColor == null || currentColor.equals(targetColor)) {
                        targetColor = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
                    }

                    int deltaRed = targetColor.getRed() - currentColor.getRed();
                    int deltaGreen = targetColor.getGreen() - currentColor.getGreen();
                    int deltaBlue = targetColor.getBlue() - currentColor.getBlue();

                    int step = 10; // Adjust the step for smoother or faster transition

                    int newRed = currentColor.getRed() + (deltaRed > 0 ? Math.min(step, deltaRed) : Math.max(-step, deltaRed));
                    int newGreen = currentColor.getGreen() + (deltaGreen > 0 ? Math.min(step, deltaGreen) : Math.max(-step, deltaGreen));
                    int newBlue = currentColor.getBlue() + (deltaBlue > 0 ? Math.min(step, deltaBlue) : Math.max(-step, deltaBlue));

                    currentColor = new Color(newRed, newGreen, newBlue);
                    panel.setBackground(currentColor);

                    if (currentColor.equals(targetColor)) {
                        targetColor = null; // Reset target color for the next transition
                    }
                }
            });
            timer.start();

            frame.add(panel);
            frame.setSize(400, 250);
            frame.setVisible(true);
        });
    }

    private static JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(0, 0, width, height, 20, 20);
                g2.setColor(getBackground());
                g2.fill(roundedRectangle);

                g2.setColor(getForeground());
                g2.draw(roundedRectangle);

                g2.dispose();

                super.paintComponent(g);
            }
        };

        button.setPreferredSize(new Dimension(100, 50));
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }
}

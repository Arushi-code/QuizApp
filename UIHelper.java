import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class UIHelper {
    public static final Color BG = new Color(240, 242, 245);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color PRIMARY = new Color(103, 58, 183);
    public static final Color PRIMARY_HOVER = new Color(81, 45, 153);
    public static final Color PRIMARY_LIGHT = new Color(237, 231, 246);
    public static final Color SUCCESS = new Color(46, 160, 67);
    public static final Color SUCCESS_HOVER = new Color(36, 130, 52);
    public static final Color WARNING = new Color(255, 140, 0);
    public static final Color DANGER = new Color(220, 53, 69);
    public static final Color DANGER_HOVER = new Color(185, 40, 55);
    public static final Color TEXT_DARK = new Color(30, 30, 30);
    public static final Color TEXT_GRAY = new Color(120, 130, 140);
    public static final Color TEXT_LIGHT = new Color(180, 190, 200);
    public static final Color TABLE_BORDER = new Color(220, 225, 235);
    public static final Color SHADOW = new Color(0, 0, 0, 30);
    public static final Color CORRECT = new Color(46, 160, 67);
    public static final Color INCORRECT = new Color(220, 53, 69);
    public static final Color TIMER_BG = new Color(255, 235, 238);
    public static final Color TIMER_TEXT = new Color(211, 47, 47);

    public static JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(BG);
        return frame;
    }

    public static Font getFont(int style, int size) {
        return new Font("Segoe UI", style, size);
    }

    public static JPanel createGradientHeader(String icon, String title) {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY, getWidth(), getHeight(), new Color(156, 39, 176));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new GridBagLayout());

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        iconLabel.setForeground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(getFont(Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(0, 0, 0, 10);
        header.add(iconLabel, gbc);
        gbc.gridx = 1;
        header.add(titleLabel, gbc);

        return header;
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(getFont(Font.PLAIN, 14));
        label.setForeground(TEXT_DARK);
        return label;
    }

    public static JLabel createBoldLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(getFont(Font.BOLD, size));
        label.setForeground(TEXT_DARK);
        return label;
    }

    public static JTextField createTextField(int cols) {
        JTextField field = new JTextField(cols);
        field.setFont(getFont(Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(220, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(TABLE_BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return field;
    }

    public static JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2d.setColor(bgColor.darker().darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(bgColor.brighter());
                } else {
                    g2d.setColor(bgColor);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                super.paintComponent(g);
            }
        };
        btn.setFont(getFont(Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return btn;
    }

    public static JPanel createCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(SHADOW);
                g2d.fillRoundRect(3, 3, getWidth() - 3, getHeight() - 3, 15, 15);
                g2d.setColor(CARD_BG);
                g2d.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 15, 15);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        return card;
    }

    public static JPanel createOptionButton(String text, int index, ActionListener listener) {
        String[] labels = {"A", "B", "C", "D"};
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(TABLE_BORDER, 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel(labels[index] + ".  " + text);
        label.setFont(getFont(Font.PLAIN, 14));
        label.setForeground(TEXT_DARK);

        panel.add(label, BorderLayout.CENTER);
        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(PRIMARY_LIGHT);
                label.setForeground(PRIMARY);
            }
            public void mouseExited(MouseEvent e) {
                panel.setBackground(CARD_BG);
                label.setForeground(TEXT_DARK);
            }
            public void mouseClicked(MouseEvent e) {
                listener.actionPerformed(null);
            }
        });

        return panel;
    }

    public static JPanel createTimerPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(TIMER_BG);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(100, 40));
        return panel;
    }
}

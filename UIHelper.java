import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class UIHelper {
    public static boolean darkMode = false;

    public static final Color LIGHT_BG = new Color(245, 247, 250);
    public static final Color LIGHT_CARD_BG = Color.WHITE;
    public static final Color LIGHT_TEXT_DARK = new Color(35, 35, 40);
    public static final Color LIGHT_TEXT_GRAY = new Color(130, 140, 150);

    public static final Color DARK_BG = new Color(25, 25, 30);
    public static final Color DARK_CARD_BG = new Color(40, 40, 48);
    public static final Color DARK_TEXT_DARK = new Color(235, 235, 240);
    public static final Color DARK_TEXT_GRAY = new Color(155, 155, 165);

    public static Color BG = LIGHT_BG;
    public static Color CARD_BG = LIGHT_CARD_BG;
    public static Color TEXT_DARK = LIGHT_TEXT_DARK;
    public static Color TEXT_GRAY = LIGHT_TEXT_GRAY;

    public static final Color PRIMARY = new Color(99, 102, 241);
    public static final Color PRIMARY_HOVER = new Color(79, 82, 221);
    public static final Color PRIMARY_LIGHT = new Color(238, 238, 255);
    public static final Color SUCCESS = new Color(34, 197, 94);
    public static final Color SUCCESS_HOVER = new Color(22, 163, 74);
    public static final Color WARNING = new Color(245, 158, 11);
    public static final Color DANGER = new Color(239, 68, 68);
    public static final Color DANGER_HOVER = new Color(220, 38, 38);
    public static final Color TABLE_BORDER = new Color(229, 231, 235);
    public static final Color SHADOW = new Color(0, 0, 0, 25);
    public static final Color CORRECT = new Color(34, 197, 94);
    public static final Color INCORRECT = new Color(239, 68, 68);
    public static final Color TIMER_BG = new Color(254, 242, 242);
    public static final Color TIMER_TEXT = new Color(220, 38, 38);

    public static void toggleDarkMode() {
        darkMode = !darkMode;
        if (darkMode) {
            BG = DARK_BG;
            CARD_BG = DARK_CARD_BG;
            TEXT_DARK = DARK_TEXT_DARK;
            TEXT_GRAY = DARK_TEXT_GRAY;
        } else {
            BG = LIGHT_BG;
            CARD_BG = LIGHT_CARD_BG;
            TEXT_DARK = LIGHT_TEXT_DARK;
            TEXT_GRAY = LIGHT_TEXT_GRAY;
        }
    }

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
                GradientPaint gp = new GradientPaint(0, 0, new Color(99, 102, 241), 0, getHeight(), new Color(139, 92, 246));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(0, 85));
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        leftPanel.setOpaque(false);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 34));
        iconLabel.setForeground(Color.WHITE);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(getFont(Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        leftPanel.add(iconLabel);
        leftPanel.add(titleLabel);

        header.add(leftPanel, BorderLayout.CENTER);
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
        JTextField field = new JTextField(cols) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                super.paintComponent(g);
            }
        };
        field.setFont(getFont(Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(220, 42));
        field.setBackground(CARD_BG);
        field.setForeground(TEXT_DARK);
        field.setCaretColor(TEXT_DARK);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(TABLE_BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)));
        field.setOpaque(false);
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
        btn.setPreferredSize(new Dimension(140, 42));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        return btn;
    }

    public static JPanel createCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(SHADOW);
                g2d.fillRoundRect(4, 4, getWidth() - 4, getHeight() - 4, 16, 16);
                g2d.setColor(CARD_BG);
                g2d.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 16, 16);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return card;
    }

    public static JPanel createOptionButton(String text, int index, ActionListener listener) {
        String[] labels = {"A", "B", "C", "D"};
        JPanel panel = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(TABLE_BORDER, 1, true),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel badge = new JLabel(labels[index]) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(PRIMARY_LIGHT);
                g2d.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        badge.setFont(getFont(Font.BOLD, 13));
        badge.setForeground(PRIMARY);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setPreferredSize(new Dimension(30, 30));
        badge.setOpaque(false);

        JLabel label = new JLabel(text);
        label.setFont(getFont(Font.PLAIN, 14));
        label.setForeground(TEXT_DARK);

        panel.add(badge, BorderLayout.WEST);
        panel.add(label, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(PRIMARY_LIGHT);
                label.setForeground(PRIMARY);
                badge.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                panel.setBackground(CARD_BG);
                label.setForeground(TEXT_DARK);
                badge.setForeground(PRIMARY);
            }
            public void mouseClicked(MouseEvent e) {
                listener.actionPerformed(null);
            }
        });

        return panel;
    }

    public static JProgressBar createProgressBar(int max) {
        JProgressBar bar = new JProgressBar(0, max) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(UIHelper.TABLE_BORDER);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                if (getValue() > 0) {
                    int width = (int) ((double) getValue() / getMaximum() * getWidth());
                    GradientPaint gp = new GradientPaint(0, 0, PRIMARY, width, 0, new Color(139, 92, 246));
                    g2d.setPaint(gp);
                    g2d.fillRoundRect(0, 0, width, getHeight(), 10, 10);
                }
            }
        };
        bar.setStringPainted(true);
        bar.setFont(getFont(Font.BOLD, 11));
        bar.setForeground(PRIMARY);
        bar.setBackground(UIHelper.TABLE_BORDER);
        bar.setPreferredSize(new Dimension(0, 24));
        bar.setBorder(BorderFactory.createEmptyBorder());
        bar.setOpaque(false);
        return bar;
    }
}

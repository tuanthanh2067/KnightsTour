package Board;

import javax.swing.*;
import java.awt.*;

public class Cell {
    private int row;
    private int col;
    private final JButton button;

    public Cell(Color color, int row, int col) {
        this.row = row;
        this.col = col;
        button = new JButton("");
        button.setBackground(color);
        button.setForeground(Color.RED);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addActionListener(e -> {
            System.out.println(row);
            System.out.println(col);
        });
    }

    public JButton get() {
        return this.button;
    }
}

package Board;

import javax.swing.*;
import java.awt.*;

public class Cell {
    private int row;
    private int col;
    private boolean selected;
    private boolean accessible;
    private String value;
    private final JButton button;

    public Cell(Color color, int row, int col) {
        this.row = row;
        this.col = col;
        this.selected = false;
        this.accessible = false;
        button = new JButton("");
        button.setBackground(color);
        button.setForeground(Color.RED);
        button.setFont(new Font("Arial", Font.PLAIN, 35));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setHorizontalAlignment(SwingConstants.CENTER);

        button.addActionListener(e -> {
            if (accessible && !selected) {
                accessible = false;
                selected = true;
                button.setText(value);
                ChessBoard.updateCurrent(row, col);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid selection",
                        "Error!!!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JButton get() {
        return this.button;
    }

    public void setSelected() {
        this.selected = true;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setAccessible() {
        this.accessible = true;
    }

    public void setInaccessible() {
        this.accessible = false;
    }


    public void setValue(String value) {
        this.value = value;
    }

    public void setButtonTextToValue() {
        button.setText(value);
    }

    public void setAvailableColor() {
        button.setBackground(Color.green);
    }

    public void setColorBlack() {button.setBackground(Color.black);}

    public void setColorWhite() {button.setBackground(Color.white);}

}

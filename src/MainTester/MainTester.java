package MainTester;


import Board.ChessBoard;

import javax.swing.*;
import java.awt.*;

public class MainTester extends JFrame {

    public MainTester() {
        super("Menu");
        JButton Manual = new JButton("Manual method");
        JButton Random = new JButton("Random method");
        JButton Heuristic = new JButton("Heuristic method");

        Manual.addActionListener(e -> {
            ChessBoard chessBoard = new ChessBoard(1);
        });
        add(Manual);
        add(Random);
        add(Heuristic);

    }

    public static void main(String[] args) {
        MainTester main = new MainTester();
        Dimension boardSize = new Dimension(300, 200);
        main.setLayout(new FlowLayout());
        main.setSize(boardSize);
        main.setVisible(true);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

package MainTester;


import Board.ChessBoard;

import javax.swing.*;
import java.awt.*;

public class MainTester extends JFrame {

    public MainTester() {
        super("Menu");
        JButton Manual = new JButton("Manual method");
        JButton Random = new JButton("Random method");
        JButton Smart = new JButton("Smart method");
        JButton All64Tours = new JButton("All 64 tours");

        Manual.addActionListener(e -> {
            ChessBoard chessBoard = new ChessBoard(1);
        });

        Random.addActionListener(e -> {
            ChessBoard chessBoard = new ChessBoard(2);
        });

        Smart.addActionListener(e -> {
            ChessBoard chessBoard = new ChessBoard(3);
        });

        All64Tours.addActionListener(e -> {
            ChessBoard chessBoard = new ChessBoard(4);
        });

        add(Manual);
        add(Random);
        add(Smart);

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

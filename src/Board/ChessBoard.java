package Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessBoard extends JFrame {

    public ChessBoard(int option) {
        super("Chess board");
        Dimension boardSize = new Dimension(600, 600);
        setSize(boardSize);
        setVisible(true);

        JPanel mainChessBoard = new JPanel();
        mainChessBoard.setLayout(new GridLayout(8, 8));
        mainChessBoard.setPreferredSize(boardSize);
        mainChessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        add(mainChessBoard);
        ArrayList<JPanel> cells = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            cells.add(new JPanel(new BorderLayout()));
            mainChessBoard.add(cells.get(i));

            int row = (i / 8) % 2;
            if (row == 0) {
                cells.get(i).setBackground(i % 2 == 0 ?  Color.black : Color.white);
            } else {
                cells.get(i).setBackground(i % 2 == 0 ?  Color.white : Color.black);

            }
        }
    }
}

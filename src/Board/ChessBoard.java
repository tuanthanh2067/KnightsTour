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
        ArrayList<Cell> cells = new ArrayList<>();
        int row = 0;
        int col = 0;
        for (int i = 0; i < 64; i++) {
            Cell cell;
            if (col == 8) {
                row++;
                col = 0;
            }
            if ((i / 8) % 2 == 0) {
                if (i % 2 == 0) {
                    cell = new Cell(Color.black, row, col);
                } else {
                    cell = new Cell(Color.white, row, col);
                }
            } else {
                if (i % 2 == 0) {
                    cell = new Cell(Color.white, row, col);
                } else {
                    cell = new Cell(Color.black, row, col);
                }
            }
            col++;
            cells.add(cell);
            mainChessBoard.add(cells.get(i).get());
        }
    }

}

package Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessBoard extends JFrame {
    static final int horizontal[] = {2, 1, -1, -2, -2, -1, 1, 2};
    static final int  vertical[] = {-1, -2, -2, -1, 1, 2, 2, 1};

    static int currentRow;
    static int currentCol;

    static int initialValue = 1;

    static ArrayList<ArrayList<Cell>> cells;

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
        cells = new ArrayList<>(8);
        Cell cell;

        initialValue = 1;
        for (int i = 0; i < 8; i++) {
            cells.add(new ArrayList<Cell>(8));
            for (int j = 0; j < 8; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        cell = new Cell(Color.black, i, j);
                    } else {
                        cell = new Cell(Color.white, i, j);
                    }
                } else {
                    if (j % 2 == 0) {
                        cell = new Cell(Color.white, i, j);
                    } else {
                        cell = new Cell(Color.black, i, j);
                    }
                }
                cells.get(i).add(cell);
                mainChessBoard.add(cells.get(i).get(j).get());
            }
        }

        // start playing
        String num1 = JOptionPane.showInputDialog("Enter row number");
        String num2 = JOptionPane.showInputDialog("Enter second integer");
        currentRow = Integer.parseInt(num1) - 1;
        currentCol = Integer.parseInt(num2) - 1;
        cells.get(currentRow).get(currentCol).setValue(Integer.toString(initialValue));
        cells.get(currentRow).get(currentCol).get().setText(Integer.toString(initialValue));
        cells.get(currentRow).get(currentCol).setSelected();
        initialValue++;
        drawPossibleRoutes();

        if (option == 1) {
            // option 1

        } else if (option == 2) {

        }


    }

    static void drawPossibleRoutes() {
        for (int i = 0; i < 8; i++) {
            int possibleRow = currentRow + vertical[i];
            int possibleCol = currentCol + horizontal[i];

            if (possibleRow >= 0 && possibleRow < 8 && possibleCol >= 0 && possibleCol < 8) {
                Cell possibleCell = cells.get(possibleRow).get(possibleCol);
                if (!possibleCell.getSelected()) {
                    possibleCell.setAccessible();
                    possibleCell.setAvailableColor();
                    possibleCell.setValue(Integer.toString(initialValue));
                }
            }
        }
    }

    static void removePossibleRoutes() {
        for (int i = 0; i < 8; i++) {
            int possibleRow = currentRow + vertical[i];
            int possibleCol = currentCol + horizontal[i];

            if (possibleRow >= 0 && possibleRow < 8 && possibleCol >= 0 && possibleCol < 8) {
                Cell possibleCell = cells.get(possibleRow).get(possibleCol);
                possibleCell.setInaccessible();
                if (possibleRow % 2 == 0) {
                    if (possibleCol % 2 == 0) {
                        possibleCell.setColorBlack();
                    } else {
                        possibleCell.setColorWhite();
                    }
                } else {
                    if (possibleCol % 2 == 0) {
                        possibleCell.setColorWhite();
                    } else {
                        possibleCell.setColorBlack();
                    }
                }
            }
        }
    }

    static void updateCurrent(int row, int col) {
        removePossibleRoutes();
        currentRow = row;
        currentCol = col;
        initialValue++;
        drawPossibleRoutes();
    }

}

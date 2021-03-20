package Board;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;

public class ChessBoard extends JFrame {
    static final int horizontal[] = {2, 1, -1, -2, -2, -1, 1, 2};
    static final int  vertical[] = {-1, -2, -2, -1, 1, 2, 2, 1};

    static final int accessibilityRow[][] = {
            {2, 3, 4, 4, 4, 4, 3, 2},
            {3, 4, 6, 6, 6, 6, 4, 3},
            {4, 6, 8, 8, 8, 8, 6, 4},
            {4, 6, 8, 8, 8, 8, 6, 4},
            {4, 6, 8, 8, 8, 8, 6, 4},
            {4, 6, 8, 8, 8, 8, 6, 4},
            {3, 4, 6, 6, 6, 6, 4, 3},
            {2, 3, 4, 4, 4, 4, 3, 2}
    };


    static int currentRow;
    static int currentCol;

    static int initialValue = 1;

    static ArrayList<ArrayList<Cell>> cells;

    static ScheduledExecutorService executorService;

    static int initCount = 0;
    static boolean tourSuccessful = false;

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

        if (option == 1 || option == 2 || option == 3) {
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
        }

        if (option == 2) {
            noBrainAutoPlay();
        } else if (option == 3) {
            smartAutoPlay();
        } else if (option == 4) {
            all64Tours();
        }
    }

    static ArrayList<Integer> selectPossibleRoutes() {
        ArrayList<Integer> routes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int possibleRow = currentRow + vertical[i];
            int possibleCol = currentCol + horizontal[i];
            if (possibleRow >= 0 && possibleRow < 8 && possibleCol >= 0 && possibleCol < 8) {
                routes.add(possibleRow);
                routes.add(possibleCol);
            }
        }
        return routes;
    }

    static ArrayList<Integer> selectPossiblesRoutesWithParams(int row, int col) {
        ArrayList<Integer> routes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int possibleRow = row + vertical[i];
            int possibleCol = col + horizontal[i];
            if (possibleRow >= 0 && possibleRow < 8 && possibleCol >= 0 && possibleCol < 8) {
                routes.add(possibleRow);
                routes.add(possibleCol);
            }
        }
        return routes;
    }

    static boolean movable(ArrayList<Integer> routes) {
        for (int i = 0; i < routes.size(); i = i + 2) {
            int possibleRow = routes.get(i);
            int possibleCol = routes.get(i + 1);
            if (cells.get(possibleRow).get(possibleCol).getAccessible() && !cells.get(possibleRow).get(possibleCol).getSelected()) {
                return true;
            }
        }
        return false;
    }

    static void drawPossibleRoutes() {
        ArrayList<Integer> possibleRoutes = selectPossibleRoutes();
        for (int i = 0; i < possibleRoutes.size(); i = i + 2) {
            int possibleRow = possibleRoutes.get(i);
            int possibleCol = possibleRoutes.get(i + 1);
            Cell possibleCell = cells.get(possibleRow).get(possibleCol);
            if (!possibleCell.getSelected()) {
                possibleCell.setAccessible();
                possibleCell.setAvailableColor();
                possibleCell.setValue(Integer.toString(initialValue));
            }
        }
    }

    static void removePossibleRoutes() {
        ArrayList<Integer> possibleRoutes = selectPossibleRoutes();
        for (int i = 0; i < possibleRoutes.size(); i = i + 2) {
            int possibleRow = possibleRoutes.get(i);
            int possibleCol = possibleRoutes.get(i + 1);

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

    static void updateCurrent(int row, int col) {
        removePossibleRoutes();
        currentRow = row;
        currentCol = col;
        initialValue++;
        drawPossibleRoutes();
    }

    static void noBrainAutoPlay() {
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            ArrayList<Integer> possibleRoutes = selectPossibleRoutes();
            if (!movable(possibleRoutes)) {
                JOptionPane.showMessageDialog(null, "Out of available moves", "Message", JOptionPane.ERROR_MESSAGE);
                executorService.shutdown();
                return;
            }
            int length = possibleRoutes.size() / 2;
            boolean cont = true;
            int random;
            int selectedRow = 0;
            int selectedCol = 0;
            while (cont) {
                random = (int) (Math.floor(Math.random() * length));
                selectedRow = possibleRoutes.get(random * 2);
                selectedCol = possibleRoutes.get(random * 2 + 1);

                if (!cells.get(selectedRow).get(selectedCol).getSelected()
                        && cells.get(selectedRow).get(selectedCol).getAccessible())
                    cont = false;
            }

            // simulate user's click
            cells.get(selectedRow).get(selectedCol).setInaccessible();
            cells.get(selectedRow).get(selectedCol).setSelected();
            cells.get(selectedRow).get(selectedCol).setValue(Integer.toString(initialValue));
            cells.get(selectedRow).get(selectedCol).setButtonTextToValue();

            updateCurrent(selectedRow, selectedCol);
        }, 500, 200, TimeUnit.MILLISECONDS);

    }

    // pick the next move where the next move has a lower number
    static ArrayList<Integer> pickNextMoveForSmartPlayFunc(ArrayList<Integer> possibleRoutes) {
        int selectedRow = 0;
        int selectedCol = 0;
        int choseAccessibility = 9;
        ArrayList<Integer> answer = new ArrayList<>();
        for (int i = 0; i < possibleRoutes.size(); i = i + 2) {
            selectedRow = possibleRoutes.get(i);
            selectedCol = possibleRoutes.get(i + 1);
            ArrayList<Integer> nextPossibleRoutes = selectPossiblesRoutesWithParams(selectedRow, selectedCol);
            for (int j = 0; j < nextPossibleRoutes.size(); j = j + 2) {
                int nextPossibleRow = nextPossibleRoutes.get(j);
                int nextPossibleCol = nextPossibleRoutes.get(j + 1);
                if (!cells.get(nextPossibleRow).get(nextPossibleCol).getSelected()) {
                    if (accessibilityRow[nextPossibleRow][nextPossibleCol] < choseAccessibility) {
                        choseAccessibility = accessibilityRow[nextPossibleRow][nextPossibleCol];
                        answer = new ArrayList<>();
                        answer.add(selectedRow);
                        answer.add(selectedCol);
                    }
                }
            }
        }

        if (answer.size() == 0) {
            answer.add(possibleRoutes.get(0));
            answer.add(possibleRoutes.get(1));
        }
        return answer;

    }

    static void smartPlayFunc() {
        ArrayList<Integer> possibleRoutes = selectPossibleRoutes();
        if (!movable(possibleRoutes)) {
            JOptionPane.showMessageDialog(null, "Out of available moves", "Message", JOptionPane.ERROR_MESSAGE);
            executorService.shutdown();
            return;
        }

        // choose route to go
        // this will run every time to select the least accessible
        // find the lowest route
        // move to that spot
        // deduct the previous spot by 1

        // part 2
        // if cells that are tied, next move would have a lower cell number
        int choseAccessibility = 9;
        int selectedRow = 0;
        int selectedCol = 0;

        ArrayList<Integer> possibleNextMoves = new ArrayList<>();
        for (int i = 0; i < possibleRoutes.size(); i = i + 2) {
            selectedRow = possibleRoutes.get(i);
            selectedCol = possibleRoutes.get(i + 1);
            if (!cells.get(selectedRow).get(selectedCol).getSelected()
                    && cells.get(selectedRow).get(selectedCol).getAccessible()) {
                if (accessibilityRow[selectedRow][selectedCol] <= choseAccessibility) {
                    if (accessibilityRow[selectedRow][selectedCol] < choseAccessibility) {
                        // if a accessibility number smaller than the one that we have
                        possibleNextMoves = new ArrayList<>();
                    }
                    // if it's equal
                    // only add to the array list
                    possibleNextMoves.add(selectedRow);
                    possibleNextMoves.add(selectedCol);
                    choseAccessibility = accessibilityRow[selectedRow][selectedCol];
                }
            }
        }

        ArrayList<Integer> returnNextMove = pickNextMoveForSmartPlayFunc(possibleNextMoves);
        
        int choseRow = returnNextMove.get(0);
        int choseCol = returnNextMove.get(1);
        // simulate user's click

        cells.get(choseRow).get(choseCol).setInaccessible();
        cells.get(choseRow).get(choseCol).setSelected();
        cells.get(choseRow).get(choseCol).setValue(Integer.toString(initialValue));
        cells.get(choseRow).get(choseCol).setButtonTextToValue();

        accessibilityRow[currentRow][currentCol]--;
        initCount++;
        if (initCount == 64) tourSuccessful = true;
        updateCurrent(choseRow, choseCol);

    }

    static void smartAutoPlay() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(ChessBoard::smartPlayFunc,
                100, 50, TimeUnit.MILLISECONDS);
    }

    static void all64Tours() {
        // generate 64 tours
        // add to files which one run successfully
    }
}

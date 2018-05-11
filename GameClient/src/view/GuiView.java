package view;

import controller.Controller;
import model.Callback;
import model.CustomButton;
import model.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class GuiView extends JFrame implements View, Callback {

    private JFrame frame;
    private Controller controller;
    private CustomButton[] fields;
    private ArrayList<Integer> selected;
    private ArrayList<Integer> opponentSelected;

    GuiView() {
        initialize();
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void init() {
        EventQueue.invokeLater(() -> {
            try {
                sideChooser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        //frame
        frame = new JFrame("TicTacToe");
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(400, 450));
        frame.setResizable(false);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.sendToServer("exit");
                controller.closeConnection();
                System.exit(0);
            }
        });

        //main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));
        frame.add(mainPanel);

        //buttons
        fields = new CustomButton[9];
        for (int btnNumber = 0; btnNumber < 9; btnNumber++) {
            fields[btnNumber] = new CustomButton(btnNumber, "");
            fields[btnNumber].setFont(new Font("Arial", Font.PLAIN, 70));
        }
        for (CustomButton btn : fields) {
            btn.addActionListener(e -> controller.onCheck(btn.getButtonID()));
            mainPanel.add(btn);
        }

        //menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        menuPanel.setMaximumSize(new Dimension(400, 50));
        frame.add(menuPanel);

        //menu panel buttons
        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> {
            onReset();
            controller.sendToServer("reset");
        });
        menuPanel.add(resetBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> {
            controller.sendToServer("exit");
            controller.closeConnection();
            System.exit(0);
        });
        menuPanel.add(exitBtn);

        selected = new ArrayList<>();
        opponentSelected = new ArrayList<>();
    }

    @Override
    public void onClick(int btnID) {
        selected.add(btnID);
        fields[btnID].setText(String.valueOf(controller.getSide()));
        disableButtons();
        controller.sendToServer(String.valueOf(btnID));
        controller.sendToServer("unlock");
        Integer[] win1, win2, win3, win4, win5, win6, win7, win8;
        win1 = new Integer[]{0, 1, 2};
        win2 = new Integer[]{3, 4, 5};
        win3 = new Integer[]{6, 7, 8};
        win4 = new Integer[]{0, 3, 6};
        win5 = new Integer[]{1, 4, 7};
        win6 = new Integer[]{2, 5, 8};
        win7 = new Integer[]{0, 4, 8};
        win8 = new Integer[]{2, 4, 6};
        if (selected.containsAll(Arrays.asList(win1)) ||
                selected.containsAll(Arrays.asList(win2)) ||
                selected.containsAll(Arrays.asList(win3)) ||
                selected.containsAll(Arrays.asList(win4)) ||
                selected.containsAll(Arrays.asList(win5)) ||
                selected.containsAll(Arrays.asList(win6)) ||
                selected.containsAll(Arrays.asList(win7)) ||
                selected.containsAll(Arrays.asList(win8))) {
            win();
        }
    }

    @Override
    public void onUnlockButtons() {
        int index = 0;
        for (CustomButton btn : fields) {
            if (!selected.contains(index) && !opponentSelected.contains(index)) {
                btn.setEnabled(true);
            }
            index++;
        }
    }

    @Override
    public void onOpponentClick(int btnID) {
        fields[btnID].setText(String.valueOf(controller.getOpponentSide()));
        fields[btnID].setEnabled(false);
        opponentSelected.add(btnID);
    }

    @Override
    public void onReset() {
        for (JButton btn : fields) {
            btn.setText("");
            btn.setEnabled(true);
        }
        for (int index = 0; index < selected.size(); index++) {
            selected.set(index, -1);
        }
        for (int index = 0; index < opponentSelected.size(); index++) {
            opponentSelected.set(index, -1);
        }
    }

    private void sideChooser() {
        JDialog choiceDialog = new JDialog();

        JButton btnX = new JButton("X");
        btnX.addActionListener(e -> {
            controller.setSide('X');
            controller.setOpponentSide('O');
            choiceDialog.setVisible(false);
            GuiView.this.frame.setVisible(true);
        });
        JButton btnO = new JButton("O");
        btnO.addActionListener(e -> {
            controller.setSide('O');
            controller.setOpponentSide('X');
            choiceDialog.setVisible(false);
            GuiView.this.frame.setVisible(true);
        });

        choiceDialog.setTitle("Choose your side");
        choiceDialog.setLocationRelativeTo(null);
        choiceDialog.setLayout(new GridLayout(1, 2));
        choiceDialog.setSize(50, 100);
        choiceDialog.setResizable(false);
        choiceDialog.add(btnX);
        choiceDialog.add(btnO);
        choiceDialog.setVisible(true);
    }

    private void disableButtons() {
        for (CustomButton btn : fields) {
            btn.setEnabled(false);
        }
    }

    @Override
    public void onGameOver() {
        disableButtons();

        JDialog winDialog = new JDialog(frame);
        winDialog.setSize(new Dimension(250, 80));
        winDialog.setLocationRelativeTo(frame);
        winDialog.setResizable(false);
        JLabel lostLabel = new JLabel("Game Over, you lost!");
        winDialog.add(lostLabel);
        winDialog.setVisible(true);
    }

    private void win() {
        disableButtons();
        controller.sendToServer("over");

        JDialog winDialog = new JDialog(frame);
        winDialog.setSize(new Dimension(250, 80));
        winDialog.setLocationRelativeTo(frame);
        winDialog.setResizable(false);
        JLabel winLabel = new JLabel("Congratulations! You've won!");
        winDialog.add(winLabel);
        winDialog.setVisible(true);
    }

}

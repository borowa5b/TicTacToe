package view;

import controller.Controller;
import model.Callback;
import model.View;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class GuiView extends JFrame implements View, Callback {

    private Controller controller;
    private JFrame frame;
    private JTextArea consoleArea;

    GuiView() {
        initialize();
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void init() {
        EventQueue.invokeLater(() -> GuiView.this.frame.setVisible(true));
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Game Server");
        frame.setSize(new Dimension(350, 250));
        frame.setResizable(false);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        frame.add(mainPanel);
        JPanel consolePanel = new JPanel(new GridLayout(1, 1));
        consolePanel.setPreferredSize(new Dimension(350, 150));
        frame.add(consolePanel);

        consoleArea = new JTextArea();
        consoleArea.setEditable(false);
        consoleArea.setMinimumSize(new Dimension(300, 150));
        DefaultCaret caret = (DefaultCaret)consoleArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane(consoleArea);
        consolePanel.add(scrollPane);

        JButton startServerButton = new JButton("Start server");
        JButton stopServerButton = new JButton("Stop server");

        startServerButton.addActionListener(e -> controller.startServer());
        stopServerButton.addActionListener(e -> controller.stopServer());

        mainPanel.add(startServerButton);
        mainPanel.add(stopServerButton);
    }

    @Override
    public void onLog(String msg) {
        consoleArea.append(msg + "\n");
    }

}

package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static assignments.assignment3.nota.NotaManager.toNextDay;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        GridBagConstraints constr = new GridBagConstraints();

        titleLabel = new JLabel("Selamat Datang di CuciCuci System!");
        constr.gridx = 2;
        constr.gridy = 0;
        constr.gridwidth = 2;
        mainPanel.add(titleLabel);

        loginButton = new JButton("Login");
        constr.gridx = 2;
        constr.gridy = 2;
        constr.gridwidth = 2;
        mainPanel.add(loginButton);

        registerButton = new JButton("Register");
        constr.gridx = 2;
        constr.gridy = 4;
        constr.gridwidth = 2;
        mainPanel.add(registerButton);

        toNextDayButton = new JButton("Next Day");
        constr.gridx = 2;
        constr.gridy = 6;
        constr.gridwidth = 2;
        mainPanel.add(toNextDayButton);

        dateLabel = new JLabel(NotaManager.fmt.format(NotaManager.cal.getTime()));
        constr.gridx = 2;
        constr.gridy = 8;
        constr.gridwidth = 2;
        mainPanel.add(dateLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleToRegister();
            }
        });
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(LoginGUI.KEY);
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        JOptionPane optionPane = new JOptionPane();
        optionPane.showMessageDialog(mainPanel,"Kamu tidur hari ini... zzzz",
                "Next Day",JOptionPane.INFORMATION_MESSAGE);
        toNextDay();
        dateLabel.setText(NotaManager.fmt.format(NotaManager.cal.getTime()));
    }
}

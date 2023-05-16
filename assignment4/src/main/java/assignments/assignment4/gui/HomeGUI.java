package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

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
        super(new BorderLayout()); // Setup layout

        // Set up main panel dan inisiasi component-nya
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * */
    private void initGUI() {
        // Membuat Panel untuk component Button
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        mainPanel.add(buttonPanel,BorderLayout.CENTER);

        // Menambahkan semua component ke mainPanel
        titleLabel = new JLabel("Selamat Datang di CuciCuci System!");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel,BorderLayout.NORTH);

        dateLabel = new JLabel("Hari ini:" + NotaManager.fmt.format(NotaManager.cal.getTime()));
        dateLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(dateLabel,BorderLayout.SOUTH);

        GridBagConstraints constr = new GridBagConstraints();
        constr.gridwidth = 2;
        constr.gridx = 0;
        constr.gridy = GridBagConstraints.RELATIVE;
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.insets = new Insets(20,10,20,10);

        loginButton = new JButton("Login");
        buttonPanel.add(loginButton,constr);

        registerButton = new JButton("Register");
        buttonPanel.add(registerButton,constr);

        toNextDayButton = new JButton("Next Day");
        buttonPanel.add(toNextDayButton,constr);

        // Menambahkan listener pada setiap Button
        loginButton.addActionListener( e -> handleToLogin());
        registerButton.addActionListener(e -> handleToRegister());
        toNextDayButton.addActionListener(e -> handleNextDay());

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
        JOptionPane.showMessageDialog(mainPanel,"Kamu tidur hari ini... zzzz",
                "Next Day",JOptionPane.INFORMATION_MESSAGE);
        toNextDay();
        dateLabel.setText("Hari ini:" + NotaManager.fmt.format(NotaManager.cal.getTime()));
    }
}

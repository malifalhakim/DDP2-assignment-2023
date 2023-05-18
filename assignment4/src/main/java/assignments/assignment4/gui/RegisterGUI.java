package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout
        this.loginManager = loginManager;

        // Set up main panel dan component-nya
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Menambahkan component yang diperlukan pada mainPanel
     * */
    private void initGUI() {
        // Menambahkan component pada mainPanel
        GridBagConstraints constr = new GridBagConstraints();
        constr.gridx = 0;
        constr.gridy = GridBagConstraints.RELATIVE;
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.anchor = GridBagConstraints.CENTER;
        constr.weightx = 1.0;
        constr.weighty = 1.0;
        constr.insets = new Insets(10,10,10,10);

        nameLabel = new JLabel("Masukan nama Anda:");
        mainPanel.add(nameLabel,constr);

        nameTextField = new JTextField();
        mainPanel.add(nameTextField,constr);

        phoneLabel = new JLabel("Masukan nomor handphone Anda:");
        mainPanel.add(phoneLabel,constr);

        phoneTextField = new JTextField();
        mainPanel.add(phoneTextField,constr);

        passwordLabel = new JLabel("Masukan password Anda:");
        mainPanel.add(passwordLabel,constr);

        passwordField = new JPasswordField();
        mainPanel.add(passwordField,constr);

        constr.fill = GridBagConstraints.NONE;

        registerButton = new JButton("Register");
        mainPanel.add(registerButton,constr);

        backButton = new JButton("Kembali");
        mainPanel.add(backButton,constr);

        // Menambahkan listener pada button
        registerButton.addActionListener(e -> handleRegister());
        backButton.addActionListener(e -> handleBack());
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(HomeGUI.KEY);
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        String name = nameTextField.getText().trim();
        String phoneNumber = phoneTextField.getText();
        String password = String.valueOf(passwordField.getPassword());

        // Jika terdapat field kosong
        if (name.length() == 0 | phoneNumber.length() == 0 | password.length() == 0){
            JOptionPane.showMessageDialog(mainPanel,"Semua field di atas wajib diisi","Empty Field",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Jika phoneNumber berisi selain angka
        if (!isNumeric(phoneNumber)){
            JOptionPane.showMessageDialog(mainPanel,"Nomor handphone harus berisi angka!","Invalid Phone Number",
                    JOptionPane.ERROR_MESSAGE);
            phoneTextField.setText("");
            return;
        }

        // Menambahkan member ke memberSystem jika bukan null
        Member registeredMember = loginManager.register(name,phoneNumber,password);
        MainFrame mainFrame = MainFrame.getInstance();

        if (registeredMember == null){
            // jika member sudah ada ketika register
            JOptionPane.showMessageDialog(mainPanel,String.format("User dengan nama %s dan nomor handphone %s " +
                            "sudah ada",name,phoneNumber), "Registration Failed",JOptionPane.ERROR_MESSAGE);
            mainFrame.navigateTo(HomeGUI.KEY);
        } else{
            String id = generateId(name,phoneNumber); // generate ID
            JTextField pesanId = new JTextField(String.format("Berhasil membuat user dengan ID %s!",id));
            pesanId.setEditable(false);
            JOptionPane.showMessageDialog(mainPanel,pesanId,"Berhasil",JOptionPane.INFORMATION_MESSAGE);
            mainFrame.navigateTo(HomeGUI.KEY);
        }

        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
    }

    /**
     * Mengecek semua char di string adalah digit atau bukan
     */
    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    /**
     * Generate ID berdasarkan nama dan noHp member
     */
    public static String generateId(String nama, String nomorHP) {
        String id = "";
        id += (nama.split(" ")[0] + "-").toUpperCase();
        id += nomorHP;

        int checksum = 0;
        for (char c : id.toCharArray()) {
            if (Character.isDigit(c))
                checksum += c - '0';
            else if (Character.isLetter(c))
                checksum += (c - 'A') + 1;
            else
                checksum += 7;
        }
        id += String.format("-%02d", checksum % 100);
        return id;
    }
}

package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private JPanel mainPanel;
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        super(new BorderLayout());
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Set up main panel dan component-nya
        initGUI();
        add(mainPanel,BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * */
    private void initGUI() {
        // TODO
        // Menambahkan component pada mainPanel
        GridBagConstraints constr = new GridBagConstraints();
        constr.anchor = GridBagConstraints.CENTER;
        constr.weighty = 1.0;
        constr.insets = new Insets(5,5,5,5);
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = GridBagConstraints.RELATIVE;

        paketLabel = new JLabel("Paket Laundry:");
        constr.gridy = 0;
        constr.gridwidth = 3;
        constr.weightx = 3.0;
        mainPanel.add(paketLabel,constr);

        String[] paket = {"Express","Fast","Reguler"};
        paketComboBox = new JComboBox<>(paket);
        constr.gridwidth = 1;
        constr.weightx = 1.0;
        mainPanel.add(paketComboBox,constr);
        paketComboBox.setSelectedIndex(0);

        showPaketButton = new JButton("Show Paket");
        mainPanel.add(showPaketButton,constr);

        beratLabel = new JLabel("Berat Cucian (Kg):");
        constr.gridy = 1;
        constr.gridwidth = 3;
        constr.weightx = 1.0;
        mainPanel.add(beratLabel,constr);

        beratTextField = new JTextField();
        constr.gridwidth = 1;
        mainPanel.add(beratTextField,constr);

        constr.gridwidth = 5;

        setrikaCheckBox = new JCheckBox("Tambah setrika service (1000/kg)");
        constr.gridy = 2;
        mainPanel.add(setrikaCheckBox,constr);

        antarCheckBox = new JCheckBox("Tambah antar service (2000/4kg pertama, kemudian 500/kg)");
        constr.gridy = 3;
        mainPanel.add(antarCheckBox,constr);

        createNotaButton = new JButton("Buat Nota");
        constr.gridy = 4;
        mainPanel.add(createNotaButton,constr);

        backButton = new JButton("Kembali");
        constr.gridy = 5;
        mainPanel.add(backButton,constr);

        // Menambahkan Listener pada setiap Button
        showPaketButton.addActionListener(e -> showPaket());
        createNotaButton.addActionListener(e -> createNota());
        backButton.addActionListener(e -> handleBack());
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        // TODO
        String paket = String.valueOf(paketComboBox.getSelectedItem());

        // Mengambil dan mengecek input berat
        int berat;
        try{
            berat = Integer.parseInt(beratTextField.getText());
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Berat Cucian harus berisi angka","Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            beratTextField.setText("");
            return;
        }

        if (berat <= 0){
            JOptionPane.showMessageDialog(this,"Berat cucian harus lebih besar dari 0 kg","Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            beratTextField.setText("");
            return;
        }

        if (berat == 1){
            JOptionPane.showMessageDialog(this,"Berat cucian kurang dari 2kg, maka cucian akan dianggap 2 kg",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            berat = 2;
        }


        Member loggedInMember = this.memberSystemGUI.getLoggedInMember();
        Nota notaBaru = new Nota(loggedInMember,berat,paket,this.fmt.format(this.cal.getTime()));


        if (setrikaCheckBox.isSelected())
            notaBaru.addService(new SetrikaService());
        if (antarCheckBox.isSelected())
            notaBaru.addService(new AntarService());

        loggedInMember.addNota(notaBaru);
        NotaManager.addNota(notaBaru);

        JOptionPane.showMessageDialog(this,"Nota berhasil dibuat","Berhasil!",JOptionPane.INFORMATION_MESSAGE);
        paketComboBox.setSelectedIndex(0);
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        // TODO
        paketComboBox.setSelectedIndex(0);
        beratTextField.setText("");
        antarCheckBox.setSelected(false);
        setrikaCheckBox.setSelected(false);
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(MemberSystemGUI.KEY);
    }
}

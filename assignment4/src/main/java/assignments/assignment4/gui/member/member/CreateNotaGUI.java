package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
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
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Set up main panel, Feel free to make any changes
        initGUI();
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // TODO
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        GridBagConstraints constr = new GridBagConstraints();
        constr.insets = new Insets(10,10,10,10);

        paketLabel = new JLabel("Paket Laundry:");
        constr.gridx = 0;
        constr.gridy = 0;
        constr.gridwidth = 3;
        mainPanel.add(paketLabel,constr);

        String[] paket = {"Express","Fast","Reguler"};
        paketComboBox = new JComboBox(paket);
        constr.gridx = 4;
        constr.gridy = 0;
        constr.gridwidth = 1;
        mainPanel.add(paketComboBox,constr);
        paketComboBox.setSelectedIndex(0);

        showPaketButton = new JButton("Show Paket");
        constr.gridx = 5;
        constr.gridy = 0;
        constr.gridwidth = 1;
        mainPanel.add(showPaketButton,constr);

        beratLabel = new JLabel("Berat Cucian (Kg):");
        constr.gridx = 0;
        constr.gridy = 1;
        constr.gridwidth = 3;
        mainPanel.add(beratLabel,constr);

        beratTextField = new JTextField();
        constr.gridx = 4;
        constr.gridy = 1;
        constr.gridwidth = 1;
        mainPanel.add(beratTextField,constr);

        setrikaCheckBox = new JCheckBox("Tambah setrika service (1000/kg)");
        constr.gridx = 0;
        constr.gridy = 2;
        constr.gridwidth = 5;
        mainPanel.add(setrikaCheckBox,constr);

        antarCheckBox = new JCheckBox("Tambah antar service (2000/4kg pertama, kemudian 500/kg)");
        constr.gridx = 0;
        constr.gridy = 3;
        constr.gridwidth = 5;
        mainPanel.add(antarCheckBox,constr);

        createNotaButton = new JButton("Buat Nota");
        constr.gridx = 0;
        constr.gridy = 4;
        constr.gridwidth = 5;
        mainPanel.add(createNotaButton,constr);

        backButton = new JButton("Kembali");
        constr.gridx = 0;
        constr.gridy = 5;
        constr.gridwidth = 5;
        mainPanel.add(backButton,constr);

        showPaketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPaket();
            }
        });

        createNotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNota();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });
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

        Integer berat;
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
            JOptionPane.showMessageDialog(this,"Berat cucian kurang dari 2kg, maka cucian akan dianggap 2 kg","Info",
                    JOptionPane.INFORMATION_MESSAGE);
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
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        // TODO
        beratTextField.setText("");
        antarCheckBox.setSelected(false);
        setrikaCheckBox.setSelected(false);
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(MemberSystemGUI.KEY);
    }
}

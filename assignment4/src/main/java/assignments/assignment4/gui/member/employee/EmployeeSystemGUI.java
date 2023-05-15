package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }


    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        // TODO
        return new JButton[]{
                new JButton("It's Nyuci time!"),
                new JButton("Display List Nota")
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {
        // TODO
        Nota[] listOfNota = NotaManager.notaList;
        String pesan = "";
        for (Nota nota:listOfNota){
            pesan += nota.getNotaStatus();
            pesan += "\n";
        }

        JOptionPane.showMessageDialog(this,pesan,"List Nota",JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {
        // TODO
        Nota[] listOfNota = NotaManager.notaList;
        String pesan = "";
        for (Nota nota:listOfNota){
            pesan += nota.kerjakan();
            pesan += "\n";
        }

        JOptionPane.showMessageDialog(this,String.format("Stand back! %s beginning to nyuci!",loggedInMember.getNama()),
                "Nyuci time",JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(this,pesan,"Nyuci Result",JOptionPane.INFORMATION_MESSAGE);
    }
}

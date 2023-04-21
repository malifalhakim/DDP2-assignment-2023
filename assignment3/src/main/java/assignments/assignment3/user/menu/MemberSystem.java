package assignments.assignment3.user.menu;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;

import static assignments.assignment1.NotaGenerator.getBerat;
import static assignments.assignment1.NotaGenerator.showPaket;
import static assignments.assignment3.nota.NotaManager.*;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        // TODO
        if (choice == 1){
            System.out.println("Masukan paket laundry:");
            showPaket();
            String paket = in.nextLine();

            int berat = getBerat();

            Nota notaMember = new Nota(this.loginMember,berat,paket,fmt.format(cal.getTime()));
            notaMember.addService(new CuciService());

            System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?\n" +
                    "Hanya tambah 1000 / kg :0\n" +
                    "[Ketik x untuk tidak mau]: ");
            String pilihan = in.nextLine();
            if (!pilihan.equals("x"))
                notaMember.addService(new SetrikaService());

            System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!\n" +
                    "Cuma 2000 / 4kg, kemudian 500 / kg\n" +
                    "[Ketik x untuk tidak mau]:\n");
            pilihan = in.nextLine();
            if (!pilihan.equals("x")){
                notaMember.addService(new AntarService());
            }

            this.loginMember.addNota(notaMember);
            addNota(notaMember);

            System.out.println("Nota berhasil dibuat!");
        } else if (choice == 2){
            Nota[] notaMember = this.loginMember.getNotaList();
            for (int i = 0; i < notaMember.length; i++){
                System.out.printf("Nota %d: %s",i,notaMember[i].getNotaStatus());
            }
        } else {
            logout = true;
        }

        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        // TODO
        Member[] newArrayMember = new Member[memberList.length + 1];
        for (int i = 0; i < memberList.length; i++){
            newArrayMember[i] = memberList[i];
        }
        newArrayMember[memberList.length] = member;
        memberList = newArrayMember;
    }
}
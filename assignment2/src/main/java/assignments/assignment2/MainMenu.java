package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> listNota = new ArrayList<>();
    private static ArrayList<Member> listMember= new ArrayList<>();

    public static void main(String[] args) {

        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleGenerateUser() {
        // TODO: handle generate user
        System.out.println("Masukkan nama Anda: ");
        String nama = input.nextLine();

        String nomorHandphone = askNomorHandphone();

        Member member = new Member(nama,nomorHandphone);
        if (isNewMember(member)){
            System.out.println("Berhasil membuat member dengan ID " + member.getId() + "!");
            listMember.add(member);
        } else {
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n",nama,nomorHandphone);
        }

    }

    public static boolean isNewMember(Member member){
        // Mengecek apakah member tersebut member yang sudah ada atau tidak
        for (Member otherMember : listMember){
            if (member.equals(otherMember)){
                return false;
            }
        }
        return true;
    }

    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
        System.out.println("Masukkan ID Member: ");
        String id = input.nextLine();

        Member member = getMember(id);
        if (!(member == null)){
            String paketLaundry = askPaket();
            int berat = askBerat();
            Nota notaPelanggan = new Nota(member,paketLaundry,berat,fmt.format(cal.getTime()));
            System.out.println("Berhasil menambahkan nota!");
            listNota.add(notaPelanggan);
            System.out.println(notaPelanggan);
        } else {
            System.out.printf("Member dengan ID %s tidak ditemukan!\n",id);
        }
    }

    public static Member getMember(String id){
        for (Member member : listMember){
            if (member.getId().equals(id))
                return member;
        }
        return null;
    }

    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
        System.out.printf("Terdaftar %d nota dalam sistem.\n",listNota.size());
        for (Nota nota: listNota){
            System.out.printf("- [%d] Status          : %s\n",nota.getIdNote(),nota.getStatus());
        }
    }

    private static void handleListUser() {
        // TODO: handle list semua user pada sistem
        System.out.printf("Terdaftar %d member dalam sistem.\n",listMember.size());
        for (Member member:listMember){
            System.out.printf("- %s : %s\n",member.getId(),member.getNama());
        }
    }

    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
        // Buang nota dengan id yang diminta dari listNota
        System.out.println("Masukan ID nota yang akan diambil:");
        String idNotaStr = input.nextLine();

        // Validasi Input
        int idNota;
        while (true){
            try{
                idNota = Integer.parseInt(idNotaStr);
                if (idNota < 0)
                    throw new NumberFormatException();
                break;
            } catch (NumberFormatException e){
                System.out.println("ID nota berbentuk angka!");
                idNotaStr = input.nextLine();
            }
        }

        // Menghapus nota
        boolean isExist = false;
        Nota deletedNota = null;
        for (Nota nota:listNota){
            if (nota.getIdNote() == idNota){
                if (nota.getIsReady()){
                    deletedNota = nota;
                    System.out.printf("Nota dengan ID %d berhasil diambil!\n",idNota);

                } else {
                    System.out.printf("Nota dengan ID %d gagal diambil!\n",idNota);
                }
                isExist = true;
            }
        }
        if (!(deletedNota == null))
            listNota.remove(deletedNota);
        if (!isExist)
            System.out.printf("Nota dengan ID %d tidak ditemukan!\n",idNota);

    }

    private static void handleNextDay() {
        // TODO: handle ganti hari
        System.out.println("Dek Depe tidur hari ini... zzz...");

        // Update tanggal sistem dan status nota
        cal.add(Calendar.DATE,1);
        for (Nota nota: listNota){
            nota.updateSisaPengerjaan(1);
            if (nota.getIsReady())
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n",nota.getIdNote());
        }

        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

}

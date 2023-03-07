package assignments.assignment1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        // Program utama yang mengatur semua alur program

        // Mencetak halaman input pilihan
        printMenu();
        System.out.print("Pilihan : ");
        String pilihan = input.nextLine();
        System.out.println("================================");

        while (!pilihan.equals("0")) {

            // Cek apakah pilihan yang dimasukkan selain 1 dan 2
            if (!(pilihan.equals("1") || pilihan.equals("2")))
                System.out.println("Perintah tidak diketahui, silakan periksa kembali");

            else {
                System.out.print("Masukkan nama Anda: ");
                String namaPelanggan = input.nextLine();

                System.out.print("Masukkan nomor handphone Anda: ");
                String nomorHandphone = input.nextLine();

                // Memastikan nomorHandphone hanya terdiri dari angka
                while (!nomorHandphone.matches("[0-9]+")) {
                    System.out.println("Nomor hp hanya menerima digit");
                    nomorHandphone = input.nextLine();
                }

                String idPelanggan = generateId(namaPelanggan, nomorHandphone);

                if (pilihan.equals("1"))
                    System.out.println("ID Anda : " + idPelanggan);

                else {
                    System.out.print("Masukkan tanggal terima: ");
                    String tanggalTerima = input.nextLine();

                    String[] paketTersedia = new String[]{"express", "fast", "reguler"};
                    System.out.print("Masukkan paket laundry: ");
                    String paketLaundry = input.nextLine();

                    // Memastikan paket Laudry yang dipilih sudah sesuai dengan yang tersedia
                    while (Arrays.stream(paketTersedia).noneMatch(paketLaundry.toLowerCase()::equals)) {
                        if (paketLaundry.equals("?"))
                            showPaket();
                        else {
                            System.out.println("Paket " + paketLaundry + " tidak diketahui");
                            System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                        }
                        System.out.print("Masukkan paket laundry: ");
                        paketLaundry = input.nextLine();
                    }

                    System.out.print("Masukkan berat cucian Anda [Kg]: ");
                    String beratCucianStr = input.nextLine();

                    // Memastikan berat cucian dalam bentuk angka
                    while (!beratCucianStr.matches("[0-9]+")) {
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif");
                        beratCucianStr = input.nextLine();
                    }
                    int beratCucian = Integer.parseInt(beratCucianStr);
                    if (beratCucian < 2){
                        System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                        beratCucian = 2;
                    }

                    String notaPelanggan = generateNota(idPelanggan, paketLaundry, beratCucian, tanggalTerima);
                    System.out.println("Nota Laundry");
                    System.out.println(notaPelanggan);
                }
            }

            // Menampilkan halaman input pilihan lagi
            System.out.println();
            printMenu();
            System.out.print("Pilihan : ");
            pilihan = input.nextLine();
            System.out.println("================================");
        }

        // Program telah berakhir dan keluar dari while loop
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
        input.close();
    }

    private static void printMenu() {
        // Method untuk menampilkan menu di NotaGenerator.
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    private static void showPaket() {
        // Method untuk menampilkan paket laundry yang tersedia
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    public static String generateId(String nama, String nomorHP){
        // Method untuk mengenerate Id dari pelanggan
        // format namaDepan - nomorHP - CHECKSUM

        String result = nama.split(" ")[0].toUpperCase() + "-" + nomorHP;
        String checkSum = getCheckSum(result);
        result = result + "-" +checkSum;

        return result;
    }

    public static String getCheckSum(String tempResult){
        // Method untuk menghitung check sum berdasarkan ketentuan di soal
        // nilai huruf A = 1, B = 2, dst hingga Z
        // nilai digit sama dengan nilai angka itu sendiri dan selain huruf dan digit nilainya 7

        int result = 0;
        for (int i = 0;i<tempResult.length();i++){
            char charNow = tempResult.charAt(i);
            if (charNow >= 'A' && charNow <= 'Z')
                result +=  charNow - 64; // result ditambah nilai dari huruf
            else if (Character.isDigit(charNow))
                result += Character.getNumericValue(charNow);
            else
                result += 7;
        }

        String strResult = result + "";
        if (strResult.length() == 1)
            strResult = "0" + result;

        return strResult.substring(strResult.length() - 2);
    }

    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        // Method yang mereturn string nota pelanggan

        int durasiPaket;
        int hargaPerPaket;
        if (paket.equalsIgnoreCase("express")){
            durasiPaket = 1;
            hargaPerPaket = 12000;
        }else if (paket.equalsIgnoreCase("fast")){
            durasiPaket = 2;
            hargaPerPaket = 10000;
        } else {
            durasiPaket = 3;
            hargaPerPaket = 7000;
        }

        String tanggalSelesai = getTanggalSelesai(durasiPaket,tanggalTerima);
        int totalHarga = hargaPerPaket * berat;

        String line1 = String.format("ID    : %s",id);
        String line2 = String.format("Paket : %s",paket);
        String line3 = "Harga :";
        String line4 = String.format("%d kg x %d = %d",berat,hargaPerPaket,totalHarga);
        String line5 = String.format("Tanggal Terima  : %s",tanggalTerima);
        String line6 = String.format("Tanggal Selesai : %s",tanggalSelesai);

        return line1 +"\n"+line2+"\n"+line3+"\n"+line4+"\n"+line5+"\n"+line6;
    }

    public static String getTanggalSelesai(int durasiPaket,String tanggalTerima){
        // Method untuk menghitung tanggal selesai laundry

        String[] detailTanggalTerima = tanggalTerima.split("/");
        LocalDate tanggalSelesai = LocalDate.of(Integer.parseInt(detailTanggalTerima[2]),
                Integer.parseInt(detailTanggalTerima[1]),Integer.parseInt(detailTanggalTerima[0])).plusDays(durasiPaket);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(tanggalSelesai);
    }
}

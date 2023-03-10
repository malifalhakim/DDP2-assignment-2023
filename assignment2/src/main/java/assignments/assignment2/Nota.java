package assignments.assignment2;
import static assignments.assignment1.NotaGenerator.generateNota;
import static assignments.assignment1.NotaGenerator.getInfoPaket;


public class Nota {
    // DataFields
    private int idNote;
    private String paket;
    private Member member;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady;
    private static int counter = 0;

    // Constructor
    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        this.idNote = counter;
        this.paket = paket;
        this.member = member;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.sisaHariPengerjaan =getInfoPaket(paket)[0];
        this.isReady = false;

        counter++;
        // Meningkatkan bonus count setiap di generate Nota
        this.member.increaseBonusCount(1);
    }

    // Methods
    public int getIdNote() {
        return idNote;
    }

    public boolean isReady() {
        return this.isReady;
    }

    /*
     * Me-return pesan yang sesuai untuk status nota yang sudah dapat diambil atau tidak
     */
    public String getPesanStatus(){
        return (this.isReady)? "Sudah dapat diambil!" : "Belum bisa diambil :(";
    }

    /*
     * Mengupdate status dari nota setelah berganti hari
     */
    public void updateSisaPengerjaan(int banyakPenguranganHari){
        this.sisaHariPengerjaan = this.sisaHariPengerjaan - banyakPenguranganHari;
        if (this.sisaHariPengerjaan <= 0)
            this.isReady = true;
    }

    /*
     * Mengecek apakah member mendapatkan diskon untuk transaksi saat ini
     */
    public boolean isDiscount(){
        if(this.member.getBonusCounter() == 3){
            this.member.setBonusCounter(0);
            return true;
        }
        return false;
    }

    public String toString(){
        String line1 = String.format("[ID Nota = %d]",this.idNote);
        String line2 = generateNota(this.member.getId(),this.paket,this.berat,this.tanggalMasuk,this.isDiscount());
        String line3 = "Status          : " + this.getPesanStatus();
        return line1+"\n"+line2+"\n"+line3;
    }
}

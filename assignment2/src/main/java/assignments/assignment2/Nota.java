package assignments.assignment2;
import static assignments.assignment1.NotaGenerator.generateNota;


public class Nota {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
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
        // TODO: buat constructor untuk class ini
        this.idNote = counter;
        this.paket = paket;
        this.member = member;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.sisaHariPengerjaan =getHariPengerjaan(paket);
        this.isReady = false;

        counter++;
        this.member.increaseBonusCount(1);
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    // Method


    public int getIdNote() {
        return idNote;
    }

    public String getPaket() {
        return paket;
    }

    public Member getMember() {
        return member;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggalMasuk() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan() {
        return sisaHariPengerjaan;
    }

    public boolean getIsReady() {
        return this.isReady;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public void setTanggalMasuk(String tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public void setSisaHariPengerjaan(int sisaHariPengerjaan) {
        this.sisaHariPengerjaan = sisaHariPengerjaan;
    }

    public void setIsReady(boolean ready) {
        this.isReady = ready;
    }

    public static int getHariPengerjaan(String paket){
        if (paket.equalsIgnoreCase("express"))
            return 1;
        else if (paket.equalsIgnoreCase("fast"))
            return 2;
        else
            return 3;
    }
    public String getStatus(){
        return (this.isReady)? "Sudah dapat diambil!" : "Belum bisa diambil :(";
    }

    public void updateSisaPengerjaan(int banyakPenguranganHari){
        this.sisaHariPengerjaan = this.sisaHariPengerjaan - banyakPenguranganHari;
        if (this.sisaHariPengerjaan <= 0)
            this.isReady = true;
    }

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
        String line3 = "Status          : " + this.getStatus();
        return line1+"\n"+line2+"\n"+line3;
    }
}

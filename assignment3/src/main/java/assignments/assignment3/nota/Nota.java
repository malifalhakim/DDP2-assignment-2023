package assignments.assignment3.nota;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import java.util.ArrayList;

import static assignments.assignment1.NotaGenerator.*;

public class Nota {
    // Data Fields
    private Member member;
    private String paket;
    private ArrayList<LaundryService> services; // List Service yang digunakan
    private long baseHarga; // Harga per-kg dari setiap paket
    private int sisaHariPengerjaan;
    private  int berat;
    private int id;
    private String tanggalMasuk;
    private boolean isDone;
    private long hargaKompensasi;
    static public int totalNota;

    // Constructor
    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        this.services = new ArrayList<LaundryService>();
        this.sisaHariPengerjaan = toHariPaket(paket);
        this.id = totalNota;
        this.baseHarga = toHargaPaket(paket);
        this.addService(new CuciService()); // Setiap Nota yang dibuat memiliki service jenis Cuci
        totalNota++;
    }

    // Methods

    /**
     * Menambahkan service (cuci/setrika/antar) pada nota yang dibuat
     *
     * @param service -> Jenis service yang ditambahkan pada nota
     */
    public void addService(LaundryService service){
        this.services.add(service);
    }

    /**
     * Mengerjakan service yang belum selesai pada nota
     *
     * @return String yang memberi tahu service yang sedang dikerjakan
     */
    public String kerjakan(){
        for (LaundryService service:this.services){
            if (!service.isDone()){
                return service.doWork();
            }
        }
        return "Sudah selesai.";
    }

    /**
     * Mengupdate nota ke hari berikutnya
     * Jika nota belum selesai sebelum tanggal yang seharusnya, diberikan uang kompensasi sebesar Rp.2000/hari
     *
     */
    public void toNextDay() {
        this.sisaHariPengerjaan--;
        if ((this.sisaHariPengerjaan < 0) && (this.getNotaStatus().equals("Belum selesai.")) ){
            this.hargaKompensasi += 2000;
        }
    }

    /**
     * Menghitung harga yang perlu dibayar member untuk suatu nota
     *
     * @return harga dalam long. harga merupakan penambahan semua service dan dikurangi harga kompensasi jika ada.
     */
    public long calculateHarga(){
        long harga = this.baseHarga * this.berat;
        for (LaundryService service:this.services){
            harga += service.getHarga(this.berat);
        }
        harga = harga - this.hargaKompensasi;
        return (harga >= 0)? harga: 0;
    }

    /**
     * Mengecek status nota. Jika semua service telah selesai maka nota telah selesai
     *
     * @return "Sudah selesai." jika status nota telah selesai. Jika belum mengembalikan "Belum selesai."
     */
    public String getNotaStatus(){
        for (LaundryService service:this.services){
            if (!service.isDone()){
                this.isDone = false;
                break;
            }
            this.isDone = true;
        }
        return (this.isDone) ? "Sudah selesai.":"Belum selesai.";
    }

    @Override
    public String toString(){
        // TODO
        String result = "";
        result += String.format("[ID Nota = %d]\n",this.id);
        result += generateNota(this.member.getId(),this.paket,this.berat,this.tanggalMasuk);
        result += "\n";
        result += "--- SERVICE LIST ---\n";
        for (LaundryService service: this.services){
            result += String.format("- %s @ Rp.%d\n",service.getServiceName(),service.getHarga(this.berat));
        }
        result += String.format("Harga Akhir: %d",this.calculateHarga());
        String messageHasilAkhir = (this.hargaKompensasi > 0) ?
                String.format(" Ada kompensasi keterlambatan %d * 2000 hari",(this.hargaKompensasi / 2000)): "";
        result += messageHasilAkhir;
        result += "\n";
        return result;
    }

    // Dibawah ini adalah getter

    public String getPaket() {
        return this.paket;
    }

    public int getBerat() {
        return this.berat;
    }

    public String getTanggal() {
        return this.tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return this.sisaHariPengerjaan;
    }
    public boolean isDone() {
        return this.isDone;
    }

    public int getId() {
        return this.id;
    }

    public LaundryService[] getServices(){
        LaundryService[] arrayService = new LaundryService[this.services.size()];
        for (int i = 0; i < this.services.size(); i++){
            arrayService[i] = this.services.get(i);
        }
        return arrayService;
    }
}

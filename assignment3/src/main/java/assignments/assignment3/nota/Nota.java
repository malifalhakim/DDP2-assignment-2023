package assignments.assignment3.nota;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import java.util.ArrayList;

import static assignments.assignment1.NotaGenerator.*;

public class Nota {
    private Member member;
    private String paket;
    private ArrayList<LaundryService> services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private  int berat;
    private int id;
    private String tanggalMasuk;
    private boolean isDone;
    private long hargaKompensasi;
    static public int totalNota;

    public Nota(Member member, int berat, String paket, String tanggal) {
        //TODO
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        this.services = new ArrayList<LaundryService>();
        this.sisaHariPengerjaan = toHariPaket(paket);
        this.id = totalNota;
        this.baseHarga = toHargaPaket(paket);
        totalNota++;
    }

    public void addService(LaundryService service){
        //TODO
        this.services.add(service);
    }

    public String kerjakan(){
        // TODO
        for (LaundryService service:this.services){
            if (!service.isDone()){
                return service.doWork();
            }
        }
        return "Sudah selesai.";
    }
    public void toNextDay() {
        // TODO
        this.sisaHariPengerjaan--;
        if (this.sisaHariPengerjaan <= 0){
            for (LaundryService service:this.services){
                this.isDone = true & service.isDone();
            }
        }
        if (this.sisaHariPengerjaan < 0){
            this.hargaKompensasi += 2000;
        }
    }

    public long calculateHarga(){
        // TODO
        long harga = (this.berat >= 2) ? this.baseHarga * this.berat: this.baseHarga * 2;
        for (LaundryService service:this.services){
            harga += service.getHarga(this.berat);
        }
        harga = harga - this.hargaKompensasi;
        return (harga >= 0)? harga: 0;
    }

    public String getNotaStatus(){
        // TODO
        String message = this.isDone? "Sudah selesai.":"Belum selesai.";
        return String.format("Nota %d: %s",this.id,message);
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

    public LaundryService[] getServices(){
        LaundryService[] arrayService = new LaundryService[this.services.size()];
        for (int i = 0; i < this.services.size(); i++){
            arrayService[i] = this.services.get(i);
        }
        return arrayService;
    }
}

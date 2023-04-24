package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{
    private boolean hasWork;
    @Override
    public String doWork() {
        hasWork = true;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        return hasWork;
    }

    @Override
    public long getHarga(int berat) {
        // Harga yang dikembalikan 0, karena harganya sama dengan harga dari paket yang dipilih
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}

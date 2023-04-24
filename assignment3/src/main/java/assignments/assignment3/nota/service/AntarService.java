package assignments.assignment3.nota.service;


public class AntarService implements LaundryService{
    private boolean hasWork;

    @Override
    public String doWork() {
        hasWork = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return hasWork;
    }

    @Override
    public long getHarga(int berat) {
        // Harga 500 per kg tetapi dengan harga minimal 2000
        long harga = 500 * berat;
        return (harga >= 2000) ? harga: 2000;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}

package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    private boolean hasWork;
    @Override
    public String doWork() {
        hasWork = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        return hasWork;
    }

    @Override
    public long getHarga(int berat) {
        // Harga setrika 1000 per kg
        return 1000 * berat;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}

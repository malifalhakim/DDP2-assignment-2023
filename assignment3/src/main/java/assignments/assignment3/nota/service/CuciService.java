package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{
    private static boolean hasWork;
    @Override
    public String doWork() {
        // TODO
        hasWork = true;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        // TODO
        return hasWork;
    }

    @Override
    public long getHarga(int berat) {
        // TODO
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}

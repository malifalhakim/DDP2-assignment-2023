package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    private static boolean hasWork;
    @Override
    public String doWork() {
        // TODO
        hasWork = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        // TODO
        return hasWork;
    }

    @Override
    public long getHarga(int berat) {
        // TODO
        return 1000 * berat;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}

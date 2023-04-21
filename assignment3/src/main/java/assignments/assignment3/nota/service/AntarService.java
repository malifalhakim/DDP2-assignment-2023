package assignments.assignment3.nota.service;

import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{
    private static boolean hasWork;

    @Override
    public String doWork() {
        // TODO
        hasWork = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        // TODO
        return hasWork;
    }

    @Override
    public long getHarga(int berat) {
        // TODO
        long harga = 500 * berat;
        return (harga >= 2000) ? harga: 2000;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}

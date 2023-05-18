package assignments.assignment3.nota.service;

public interface LaundryService {
    String doWork();
    boolean isDone();
    long getHarga(int berat);
    String getServiceName();
}

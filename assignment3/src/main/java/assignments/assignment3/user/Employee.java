package assignments.assignment3.user;

public class Employee extends Member {
    // Data Fields
    public static int employeeCount;

    // Constructor
    public Employee(String nama, String password) {
        super(nama, generateId(nama), password);
    }

    // Methods
    /**
     * Membuat ID employee dari nama employee dengan format
     * NAMA_DEPAN-[jumlah employee, mulai dari 0]
     * Contoh: Dek Depe adalah employee pertama yang dibuat, sehingga IDnya adalah DEK-0
     *
     * @param nama -> Nama lengkap dari employee
     */
    private static String generateId(String nama) {
        return String.format("%s-%d",nama.split(" ")[0].toUpperCase(),employeeCount++);
    }
}

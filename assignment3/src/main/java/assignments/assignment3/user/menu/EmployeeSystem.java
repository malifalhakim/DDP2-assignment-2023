package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.Member;


public class EmployeeSystem extends SystemCLI {

    /**
     * Membuat object baru EmployeeSystem dan mendaftarkan Employee pada CuciCuci
     */
    public EmployeeSystem() {
        memberList = new Member[]{
                new Employee("Dek Depe", "akuDDP"),
                new Employee("Depram", "musiktualembut"),
                new Employee("Lita Duo", "gitCommitPush"),
                new Employee("Ivan Hoshimachi", "SuamiSahSuisei"),
        };
    }

    /**
     * Memproses pilihan dari employee yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        Nota[] notaList = NotaManager.notaList;
        if (choice == 1){
            // Menampilkan pesan berupa service yang sedang dikerjakan pada nota
            System.out.printf("Stand back! %s beginning to nyuci!\n",this.loginMember.getNama());
            for (Nota nota:notaList){
                System.out.println(nota.kerjakan());
            }
        } else if (choice == 2){
            // Menampilkan status dari setiap nota
            for (Nota nota:notaList){
                System.out.println(nota.getNotaStatus());
            }
        } else if (choice == 3){
            logout = true;
        } else {
            System.out.println("Pilihan tidak valid, silahkan coba lagi.");
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Employee.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. It's nyuci time");
        System.out.println("2. Display List Nota");
        System.out.println("3. Logout");
    }

    // Untuk TP4
    public void addEmployee(Employee[] employees) {
        Member[] result = new Member[employees.length + memberList.length];


        System.arraycopy(memberList, 0, result, 0, memberList.length);
        System.arraycopy(employees, 0, result, memberList.length, employees.length);

        memberList = result;
    }

}
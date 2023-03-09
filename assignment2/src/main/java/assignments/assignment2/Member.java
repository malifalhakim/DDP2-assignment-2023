package assignments.assignment2;
import static assignments.assignment1.NotaGenerator.generateId;

public class Member {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    // DataFields
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;

    // Constructor
    public Member(String nama, String noHp) {
        // TODO: buat constructor untuk class ini
        this.nama = nama;
        this.noHp = noHp;
        this.id = generateId(nama,noHp);
        this.bonusCounter = 0;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    // Method
    public String getNama(){
        return this.nama;
    }
    public String getId() {
        return this.id;
    }
    public String getNoHp(){
        return this.noHp;
    }
    public int getBonusCounter() {
        return this.bonusCounter;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
    public void setBonusCounter(int bonusCounter) {
        this.bonusCounter = bonusCounter;
    }
    public void increaseBonusCount(int increment){
        this.bonusCounter = this.bonusCounter + increment;
    }
    public boolean equals(Member other){
        return this.id.equals(other.id);
    }
}

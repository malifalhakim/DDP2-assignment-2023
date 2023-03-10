package assignments.assignment2;
import static assignments.assignment1.NotaGenerator.generateId;

public class Member {
    // DataFields
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;

    // Constructor
    public Member(String nama, String noHp) {
        this.nama = nama;
        this.noHp = noHp;
        this.id = generateId(nama,noHp);
        this.bonusCounter = 0;
    }

    // Method
    public String getNama(){
        return this.nama;
    }
    public String getId() {
        return this.id;
    }
    public int getBonusCounter() {
        return this.bonusCounter;
    }
    public void setBonusCounter(int bonusCounter) {
        this.bonusCounter = bonusCounter;
    }
    public void increaseBonusCount(int increment){
        this.bonusCounter = this.bonusCounter + increment;
    }

    /*
     * Method yang mengecek apakah 2 member sama atau tidak
     * 2 Member sama jika id mereka sama
     */
    public boolean equals(Member other){
        return this.id.equals(other.id);
    }
}

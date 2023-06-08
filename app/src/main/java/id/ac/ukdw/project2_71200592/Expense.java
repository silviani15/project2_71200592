package id.ac.ukdw.project2_71200592;

public class Expense {
    private int id;
    private String keterangan;
    private double nominal;

    public Expense(int id, String keterangan, double nominal) {
        this.id = id;
        this.keterangan = keterangan;
        this.nominal = nominal;
    }
//    COBA
    public int getId() {
        return id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public double getNominal() {
        return nominal;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setNominal(double nominal) {
        this.nominal = nominal;

    }

    @Override
    public String toString() {
        return keterangan + " - Rp " + nominal;
    }
}


package gravedigger.example.ra127_2014.com.taskmanager;

import android.widget.CheckBox;

import java.io.Serializable;

/**
 * Created by Jelena on 4/18/2017.
 */

public class Zadatak implements Serializable {

    private String ime;
    private String opis;
    private boolean podsetnik = false;
    private String vaznost;
    private String datum;
    private String vreme;
    private boolean zavrsen;
    private int id;

    public Zadatak(String ime, String opis, boolean podsetnik, String vaznost, String datum, String vreme, boolean zavrsen, int id) {
        this.ime = ime;
        this.opis = opis;
        this.podsetnik = podsetnik;
        this.vaznost = vaznost;
        this.datum = datum;
        this.vreme = vreme;
        this.zavrsen = zavrsen;
        this.id = id;
    }

    public Zadatak() {
        this.ime = "";
        this.opis = "";
        this.podsetnik = false;
        this.vaznost = "";
        this.datum = "";
        this.vreme = "";
        this.zavrsen = false;
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isZavrsen() {
        return zavrsen;
    }

    public void setZavrsen(boolean zavrsen) {
        this.zavrsen = zavrsen;
    }

    public String getIme() {
        return ime;
    }

    public String getOpis() {
        return opis;
    }

    public boolean isPodsetnik() {
        return podsetnik;
    }

    public void setPodsetnik(boolean podsetnik) {
        this.podsetnik = podsetnik;
    }

    public String getVaznost() {
        return vaznost;
    }

    public String getDatum() {
        return datum;
    }

    public String getVreme() {
        return vreme;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setVaznost(String vaznost) {
        this.vaznost = vaznost;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }
}

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

    public Zadatak(String ime, String opis, boolean podsetnik, String vaznost, String datum, String vreme) {
        this.ime = ime;
        this.opis = opis;
        this.podsetnik = podsetnik;
        this.vaznost = vaznost;
        this.datum = datum;
        this.vreme = vreme;
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
}

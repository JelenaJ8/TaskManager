package gravedigger.example.ra127_2014.com.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import java.util.Objects;

public class Statistika extends AppCompatActivity {

    Button nazadDugme;
    public float brojCrvenih = 0, brojZelenih = 0, brojZutih = 0, uradjeniCrveni = 0, uradjeiZeleni = 0, uradjeniZuti = 0;
    public float sweepAngelVP, sweepAngelSP, sweepAngelNP, procentiNP, procentiVP, procentiSP;
    CustomView customView;
    TaskDatabase tdb;
    NativeClass nativeClass;
    final static String TAG = "Statistika";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistika);

        nazadDugme = (Button) findViewById(R.id.nazadDugme);
        customView = (CustomView) findViewById(R.id.cv);
        tdb = new TaskDatabase(this);
        nativeClass = new NativeClass();

        Zadatak[] zadaci = tdb.readTasks();
        if(zadaci != null) {
            for (Zadatak z : zadaci) {
                Log.d(TAG, "zadatak " + z.getIme() + z.getVaznost() + z.getDatum() + z.getOpis() + z.isZavrsen());
                if (z.isZavrsen()) {
                    Log.d(TAG, "zavrsen " + z.isZavrsen());
                    if (Objects.equals(z.getVaznost(), "RED")) {
                        uradjeniCrveni++;
                        brojCrvenih++;
                    } else if (Objects.equals(z.getVaznost(), "GREEN")) {
                        uradjeiZeleni++;
                        brojZelenih++;
                    } else {
                        uradjeniZuti++;
                        brojZutih++;
                    }
                } else {
                    if (Objects.equals(z.getVaznost(), "RED")) {
                        brojCrvenih++;
                    } else if (Objects.equals(z.getVaznost(), "GREEN")) {
                        brojZelenih++;
                    } else {
                        brojZutih++;
                    }
                }
            }
        }

        Log.d(TAG, "crveni " + brojCrvenih);
        Log.d(TAG, "zeleni " + brojZelenih);
        Log.d(TAG, "zuti " + brojZutih);

        /*procentiVP = (uradjeniCrveni / brojCrvenih) * 100;
        procentiNP = (uradjeiZeleni / brojZelenih) * 100;
        procentiSP = (uradjeniZuti / brojZutih) * 100;*/

        procentiVP = nativeClass.izracunajProcente(uradjeniCrveni, brojCrvenih);
        procentiNP = nativeClass.izracunajProcente(uradjeiZeleni, brojZelenih);
        procentiSP = nativeClass.izracunajProcente(uradjeniZuti, brojZutih);

        Log.d(TAG, "procenti crvenih " + procentiVP);
        Log.d(TAG, "procenti zelenih " + procentiNP);
        Log.d(TAG, "procenti zutih " + procentiSP);

        sweepAngelVP = (360 * procentiVP) / 100;
        sweepAngelNP = (360 * procentiNP) / 100;
        sweepAngelSP = (360 * procentiSP) / 100;

        customView.startAnimation(sweepAngelVP, sweepAngelNP, sweepAngelSP);

        nazadDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });
    }
}

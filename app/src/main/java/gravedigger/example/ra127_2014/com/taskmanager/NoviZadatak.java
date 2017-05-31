package gravedigger.example.ra127_2014.com.taskmanager;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.renderscript.Sampler;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

import static android.R.attr.button;
import static android.R.attr.color;
import static gravedigger.example.ra127_2014.com.taskmanager.R.id.ime;
import static gravedigger.example.ra127_2014.com.taskmanager.R.id.time;
import static gravedigger.example.ra127_2014.com.taskmanager.R.id.vreme;

public class NoviZadatak extends AppCompatActivity {

    Button crvenoDugme, zutoDugme, zelenoDugme, dodajDugme, otkaziDugme;
    Intent in1;
    EditText imeZadatka, opisZadatka;
    DatePicker datePicker;
    TimePicker timePicker;
    CheckBox podsetnik;
    public String TAG = "NoviZadatak";
    String ime, opis, dugme, datum, vreme, time_now;
    boolean checked = false;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novi_zadatak);
        Log.d(TAG, "onCreate: usao");

        crvenoDugme = (Button) findViewById(R.id.b1);
        zutoDugme = (Button) findViewById(R.id.b2);
        zelenoDugme = (Button) findViewById(R.id.b3);
        dodajDugme = (Button) findViewById(R.id.b4);
        otkaziDugme = (Button) findViewById(R.id.b5);

        imeZadatka = (EditText) findViewById(R.id.ime);
        opisZadatka = (EditText) findViewById(R.id.opis);

        datePicker = (DatePicker) findViewById(R.id.date);
        timePicker = (TimePicker) findViewById(R.id.time);

        podsetnik = (CheckBox) findViewById(R.id.box);

        in1 = getIntent();

        if(in1.hasExtra("azuriranje")){
            Zadatak zadatak = (Zadatak) in1.getSerializableExtra("azuriranje");

            dodajDugme.setText(R.string.sacuvaj_dugme);
            otkaziDugme.setText(R.string.obrisi_dugme);

            imeZadatka.setText(zadatak.getIme());
            opisZadatka.setText(zadatak.getOpis());

            if(zadatak.isPodsetnik()){
                podsetnik.setChecked(true);
            }else {
                podsetnik.setChecked(false);
            }

            vreme = zadatak.getVreme();
            String sat = vreme.substring(0, 2);
            String min = vreme.substring(3, 5);
            int minut = Integer.parseInt(min);
            int sati = Integer.parseInt(sat);
            timePicker.setMinute(minut);
            timePicker.setHour(sati);

            datum = zadatak.getDatum();
            String dan = datum.substring(0, 2);
            String mesec = datum.substring(3, 5);
            String godina = datum.substring(6, 10);
            int d = Integer.parseInt(dan);
            int m = Integer.parseInt(mesec);
            int g = Integer.parseInt(godina);
            calendar.set(g, m, d);
            int calDay = calendar.get(Calendar.DAY_OF_MONTH);
            int calMonth = calendar.get(Calendar.MONTH)-1;
            int calYear = calendar.get(Calendar.YEAR);
            datePicker.init(calYear, calMonth, calDay, null);

            if(zadatak.getVaznost().equals("RED")){
                crvenoDugme.setTextSize(14);
                zutoDugme.setTextSize(10);
                zelenoDugme.setTextSize(10);
                crvenoDugme.getBackground().setAlpha(255);
                zutoDugme.getBackground().setAlpha(100);
                zelenoDugme.getBackground().setAlpha(100);
                crvenoDugme.setSelected(true);
                zutoDugme.setSelected(false);
                zelenoDugme.setSelected(false);
            }else if(zadatak.getVaznost().equals("YELLOW")){
                crvenoDugme.setTextSize(10);
                zutoDugme.setTextSize(14);
                zelenoDugme.setTextSize(10);
                crvenoDugme.getBackground().setAlpha(100);
                zutoDugme.getBackground().setAlpha(255);
                zelenoDugme.getBackground().setAlpha(100);
                zutoDugme.setSelected(true);
                crvenoDugme.setSelected(false);
                zelenoDugme.setSelected(false);
            }else{
                crvenoDugme.setTextSize(10);
                zutoDugme.setTextSize(10);
                zelenoDugme.setTextSize(14);
                crvenoDugme.getBackground().setAlpha(100);
                zutoDugme.getBackground().setAlpha(100);
                zelenoDugme.getBackground().setAlpha(255);
                zelenoDugme.setSelected(true);
                zutoDugme.setSelected(false);
                crvenoDugme.setSelected(false);
            }
        }

        crvenoDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crvenoDugme.setTextSize(14);
                zutoDugme.setTextSize(10);
                zelenoDugme.setTextSize(10);
                crvenoDugme.getBackground().setAlpha(255);
                zutoDugme.getBackground().setAlpha(100);
                zelenoDugme.getBackground().setAlpha(100);
                crvenoDugme.setSelected(true);
                zutoDugme.setSelected(false);
                zelenoDugme.setSelected(false);
            }
        });

        zutoDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crvenoDugme.setTextSize(10);
                zutoDugme.setTextSize(14);
                zelenoDugme.setTextSize(10);
                crvenoDugme.getBackground().setAlpha(100);
                zutoDugme.getBackground().setAlpha(255);
                zelenoDugme.getBackground().setAlpha(100);
                zutoDugme.setSelected(true);
                crvenoDugme.setSelected(false);
                zelenoDugme.setSelected(false);
            }
        });

        zelenoDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crvenoDugme.setTextSize(10);
                zutoDugme.setTextSize(10);
                zelenoDugme.setTextSize(14);
                crvenoDugme.getBackground().setAlpha(100);
                zutoDugme.getBackground().setAlpha(100);
                zelenoDugme.getBackground().setAlpha(255);
                zelenoDugme.setSelected(true);
                zutoDugme.setSelected(false);
                crvenoDugme.setSelected(false);
            }
        });

        dodajDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imeZadatka.getText().toString().trim().equals("")){
                    imeZadatka.setError("Ime zadatka je obavezno!");
                }else if(opisZadatka.getText().toString().trim().equals("")){
                    opisZadatka.setError("Opis zadatka je obavezan!");
                }else if(!crvenoDugme.isSelected() && !zutoDugme.isSelected() && !zelenoDugme.isSelected()){
                    crvenoDugme.setError("Važnost je obavezna!");
                    zutoDugme.setError("Važnost je obavezna!");
                    zelenoDugme.setError("Važnost je obavezna!");
                }else{
                    Log.d(TAG, "onClick: usao");
                    ime = imeZadatka.getText().toString().trim();
                    opis = opisZadatka.getText().toString().trim();
                    if(crvenoDugme.isSelected()){
                        dugme = "RED";
                    }else if(zutoDugme.isSelected()){
                        dugme = "YELLOW";
                    }else{
                        dugme = "GREEN";
                    }
                    Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                    datum = sdf.format(date);
                    int min = timePicker.getCurrentMinute();
                    int hour = timePicker.getCurrentHour();
                    vreme = String.format("%02d:%02d", hour, min);
                    time_now = sdf_time.format(calendar.getTime());
                    int hour_now = Integer.parseInt(time_now.substring(0, 2));
                    int min_now = Integer.parseInt(time_now.substring(3, 5));
                    int millisec_now = hour_now * 60 * 60 * 1000 + min_now * 60 * 1000;
                    int millisec_task = hour * 60 * 60 * 1000 + min * 60 * 1000;
                    if(podsetnik.isChecked()) {
                        if(millisec_task - millisec_now < 14 * 60 * 1000) {
                            checked = false;
                            Toast.makeText(getApplication().getBaseContext(), "Ne moze podsetnik, vreme isticanja zadatka je manje od 15 minuta!", Toast.LENGTH_LONG).show();
                        }
                        else
                            checked = true;
                    }
                    in1.putExtra("ime", ime);
                    in1.putExtra("opis", opis);
                    in1.putExtra("checkBox", checked);
                    in1.putExtra("vaznost", dugme);
                    in1.putExtra("datum", datum);
                    in1.putExtra("vreme", vreme);
                    setResult(RESULT_OK, in1);
                    finish();
                }
            }
        });

        otkaziDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });

    }
}

package gravedigger.example.ra127_2014.com.taskmanager;

import android.app.DownloadManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.renderscript.Sampler;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TimePicker;

import static android.R.attr.button;
import static android.R.attr.color;

public class NoviZadatak extends AppCompatActivity {

    Button crvenoDugme, zutoDugme, zelenoDugme, dodajDugme, otkaziDugme;
    Intent in;
    EditText imeZadatka, opisZadatka;
    DatePicker datePicker;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novi_zadatak);

        crvenoDugme = (Button) findViewById(R.id.b1);
        zutoDugme = (Button) findViewById(R.id.b2);
        zelenoDugme = (Button) findViewById(R.id.b3);
        dodajDugme = (Button) findViewById(R.id.b4);
        otkaziDugme = (Button) findViewById(R.id.b5);

        imeZadatka = (EditText) findViewById(R.id.ime);
        opisZadatka = (EditText) findViewById(R.id.opis);

        datePicker = (DatePicker) findViewById(R.id.date);
        timePicker = (TimePicker) findViewById(R.id.time);

        in = new Intent(NoviZadatak.this, MainActivity.class);

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
            }
        });

        dodajDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imeZadatka.getText().toString().trim().equals("")){
                    imeZadatka.setError("Ime zadatka je obavezno!");
                }else if(opisZadatka.getText().toString().trim().equals("")){
                    opisZadatka.setError("Opis zadatka je obavezan!");
                }else if(crvenoDugme.isSelected() == false && zutoDugme.isSelected() == false && zelenoDugme.isSelected() == false){
                    crvenoDugme.setError("Važnost je obavezna!");
                    zutoDugme.setError("Važnost je obavezna!");
                    zelenoDugme.setError("Važnost je obavezna!");
                }else{
                    startActivity(in);
                }
            }
        });

        otkaziDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(in);
            }
        });

    }
}

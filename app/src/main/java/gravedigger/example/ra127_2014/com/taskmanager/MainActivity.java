package gravedigger.example.ra127_2014.com.taskmanager;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {

    public String TAG = "MainActivity";
    Button noviZadatak, statistika, sacuvaj, obrisi;
    String imeZadatka, opisZadatka, dugmeVaznosti, datum, vreme;
    boolean checkBox = false;
    ListView lista;
    MojAdapter adapter = new MojAdapter(this);
    Intent in1, in2, in3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Usao");

        noviZadatak = (Button) findViewById(R.id.b1);
        statistika = (Button) findViewById(R.id.b2);
        lista = (ListView) findViewById(R.id.lista);
        sacuvaj = (Button) findViewById(R.id.b4);
        obrisi = (Button) findViewById(R.id.b5);

        in1 = new Intent(MainActivity.this, NoviZadatak.class);
        in2 = new Intent(MainActivity.this, Statistika.class);
        lista.setAdapter(adapter);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemLongClick: usao");
                in3 = new Intent(MainActivity.this, NoviZadatak.class);
                Zadatak zadatak = (Zadatak) lista.getItemAtPosition(position);
                in3.putExtra("azuriranje", zadatak);
                startActivityForResult(in3, 1);
                return true;
            }
        });

        noviZadatak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(in1, 1);
            }
        });

        statistika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(in2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: usao");
        if(requestCode == 1){
            if(resultCode == RESULT_OK) {
                imeZadatka = data.getStringExtra("ime");
                opisZadatka = data.getStringExtra("opis");
                checkBox = data.getBooleanExtra("checkBox", false);
                dugmeVaznosti = data.getStringExtra("vaznost");
                datum = data.getStringExtra("datum");
                vreme = data.getStringExtra("vreme");

                in1 = new Intent(MainActivity.this, NoviZadatak.class);

                adapter.addZadatak(new Zadatak(imeZadatka, opisZadatka, checkBox, dugmeVaznosti, datum, vreme));
            }
        }
    }
}

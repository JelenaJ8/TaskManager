package gravedigger.example.ra127_2014.com.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button noviZadatak, statistika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noviZadatak = (Button) findViewById(R.id.b1);
        statistika = (Button) findViewById(R.id.b2);

        noviZadatak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(MainActivity.this, NoviZadatak.class);
                startActivity(in1);
            }
        });

        statistika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in2 = new Intent(MainActivity.this, Statistika.class);
                startActivity(in2);
            }
        });
    }
}

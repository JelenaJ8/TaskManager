package gravedigger.example.ra127_2014.com.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

public class Statistika extends AppCompatActivity {

    Button nazadDugme;
    public int procentiVP = 68, procentiSP = 90, procentiNP = 30;
    public float sweepAngelVP, sweepAngelSP, sweepAngelNP;
    CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistika);

        nazadDugme = (Button) findViewById(R.id.nazadDugme);
        customView = (CustomView) findViewById(R.id.cv);

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

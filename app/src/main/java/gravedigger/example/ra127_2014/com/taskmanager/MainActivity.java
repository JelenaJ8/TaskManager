package gravedigger.example.ra127_2014.com.taskmanager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {

    public String TAG = "MainActivity";
    Button noviZadatak, statistika, sacuvaj, obrisi;
    ListView lista;
    MojAdapter adapter;
    Intent in1, in2, in3;
    boolean bound = false;
    TaskService taskService;
    TaskDatabase tdb;
    int id_inc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Usao");

        adapter = new MojAdapter(this);
        tdb = new TaskDatabase(this);
        //tdb.deleteAll();

        noviZadatak = (Button) findViewById(R.id.b1);
        statistika = (Button) findViewById(R.id.b2);
        lista = (ListView) findViewById(R.id.lista);
        sacuvaj = (Button) findViewById(R.id.b4);
        obrisi = (Button) findViewById(R.id.b5);

        lista.setAdapter(adapter);

        in1 = new Intent(MainActivity.this, NoviZadatak.class);
        in2 = new Intent(MainActivity.this, Statistika.class);
        Intent i = new Intent(MainActivity.this, TaskService.class);
        bindService(i, mConnection, BIND_AUTO_CREATE);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemLongClick: usao");
                in3 = new Intent(MainActivity.this, NoviZadatak.class);
                Log.d(TAG, "onItemLongClick: " + position);
                in3.putExtra("id", position);
                startActivity(in3);
                return true;
            }
        });

        noviZadatak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in1.putExtra("id", -1);
                Zadatak[] zadaci = tdb.readTasks();
                if(zadaci == null){
                    id_inc = 0;
                } else {
                    for (Zadatak z : zadaci){
                        id_inc = z.getId();
                        Log.d(TAG, "id " + z.getId());
                    }
                }
                Log.d(TAG, "id_inc " + id_inc);
                in1.putExtra("id_inc", id_inc);
                startActivity(in1);
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
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume: usao");

        Zadatak[] zadaci = tdb.readTasks();
        adapter.update(zadaci);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: usao");
        Intent in = new Intent(this, TaskService.class);
        bindService(in, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: usao");
        if(bound) {
            unbindService(mConnection);
            bound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: usao");
            TaskService.LocalBinder binder = (TaskService.LocalBinder) service;
            taskService = binder.getService();
            binder.setTasks(adapter.getZadaci());
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };
}

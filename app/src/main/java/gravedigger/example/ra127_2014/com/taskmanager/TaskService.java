package gravedigger.example.ra127_2014.com.taskmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jelena on 5/28/2017.
 */

public class TaskService extends Service {

    final private String TAG = "TaskService";
    private final IBinder binder = new LocalBinder();
    private ArrayList<Zadatak> zadaci = null;
    String time_now, task_time, date_now, task_date;
    SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
    NotificationManager notificationManager = null;
    NotificationCompat.Builder builder = null;
    int id = 0;

    public TaskService(){
        final Handler handler = new Handler();
        final Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    Calendar calendar = Calendar.getInstance();
                    int godina = calendar.get(Calendar.YEAR);
                    int mesec = calendar.get(Calendar.MONTH) + 1;
                    int dan = calendar.get(Calendar.DAY_OF_MONTH);
                    date_now = String.format("%02d.%02d.", dan, mesec) + godina;
                    time_now = sdf_time.format(calendar.getTime());
                    int hour_now = Integer.parseInt(time_now.substring(0, 2));
                    int min_now = Integer.parseInt(time_now.substring(3, 5));
                    int millisec_now = hour_now * 60 * 60 * 1000 + min_now * 60 * 1000;
                    if(zadaci != null){
                        for(Zadatak z : zadaci){
                            if(z.isPodsetnik()){
                                task_time = z.getVreme();
                                int task_hour = Integer.parseInt(task_time.substring(0, 2));
                                int task_min = Integer.parseInt(task_time.substring(3, 5));
                                int millisec_task = task_hour * 60 * 60 * 1000 + task_min * 60 * 1000;
                                task_date = z.getDatum();
                                if(task_date.equals(date_now)){
                                    if(millisec_task - millisec_now < 16 * 60 * 1000){
                                        notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
                                        builder = new NotificationCompat.Builder(TaskService.this).setContentTitle("Task manager")
                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                .setContentText("15 minuta do isteka zadatka " + z.getIme() + " " + z.getVreme())
                                                .setWhen(System.currentTimeMillis())
                                                .setColor(Color.BLUE);

                                        Intent i = new Intent(TaskService.this, MainActivity.class);
                                        PendingIntent pi = PendingIntent.getActivity(TaskService.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                                        builder.setContentIntent(pi);

                                        notificationManager.notify(id, builder.build());
                                        id++;
                                        z.setPodsetnik(false);
                                    }
                                }
                            }
                        }
                    }
                } finally {
                    handler.postDelayed(this,60000);
                }
            }
        }; thread.run();
    }

    public class LocalBinder extends Binder{
        TaskService getService(){
            Log.d(TAG, "getService: usao");
            return TaskService.this;
        }
        void setTasks(ArrayList<Zadatak> z){
            zadaci = z;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}

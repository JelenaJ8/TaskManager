package gravedigger.example.ra127_2014.com.taskmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jelena on 4/21/2017.
 */

public class CustomView extends View {

    Paint paintCircle = new Paint();
    Paint redPie = new Paint();
    Paint yellowPie = new Paint();
    Paint greenPie = new Paint();
    Paint paintText = new Paint();
    final RectF rect = new RectF();
    public float currentAngleVP = 0, currentAngleSP = 0, currentAngleNP = 0;
    private String procenatVP, procenatSP, procenatNP;
    public int procentiVP = 0, procentiNP = 0, procentiSP = 0;
    public String TAG = "CustomView";

    public CustomView(Context context) {
        super(context);
        init(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


   private void init(Context context, AttributeSet attrs){
       Log.d(TAG, "init: usao");

       paintCircle.setColor(Color.BLUE);
       paintCircle.setTextSize(30);
       paintText.setTextSize(100);
       paintText.setColor(Color.BLACK);

       redPie.setColor(Color.RED);
       greenPie.setColor(Color.GREEN);
       yellowPie.setColor(Color.YELLOW);
   }

    public void startAnimation(final float sweepAngelVP, final float sweepAngelNP, final float sweepAngelSP){

        Log.d(TAG, "startAnimationVP: usao");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "runVP: usao");
                while (currentAngleVP < sweepAngelVP || currentAngleSP < sweepAngelSP || currentAngleNP < sweepAngelNP){
                    postInvalidate();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(currentAngleVP < sweepAngelVP) {
                        procentiVP = (int) (currentAngleVP * 100) / 360;
                        procentiVP++;
                        currentAngleVP++;
                    }
                    if(currentAngleSP < sweepAngelSP){
                        procentiSP = (int)(currentAngleSP * 100) / 360;
                        procentiSP++;
                        currentAngleSP++;
                    }
                    if(currentAngleNP < sweepAngelNP){
                        procentiNP = (int)(currentAngleNP * 100) / 360;
                        procentiNP++;
                        currentAngleNP++;
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: usao");

        canvas.drawCircle(getWidth()/2, getHeight()/6, 150, paintCircle);
        canvas.drawText(getResources().getString(R.string.visoki_prioritet), getWidth()/2 - 150, getHeight()/6 + 200, paintCircle);
        rect.set(getWidth()/2 - 150, getHeight()/6 - 150, getWidth()/2 + 150, getHeight()/6 + 150);
        canvas.drawArc(rect, 270, currentAngleVP, true, redPie);
        procenatVP = Integer.toString(procentiVP) + "%";
        canvas.drawText(procenatVP, getWidth()/2 - 75, getHeight()/6 + 25, paintText);

        canvas.drawCircle(3*getWidth()/4, 2*getHeight()/3, 150, paintCircle);
        canvas.drawText(getResources().getString(R.string.niski_prioritet), 3*getWidth()/4 - 140, 2*getHeight()/3 + 200, paintCircle);
        rect.set(3*getWidth()/4 - 150, 2*getHeight()/3 - 150, 3*getWidth()/4 + 150, 2*getHeight()/3 + 150);
        canvas.drawArc(rect, 270, currentAngleNP, true, greenPie);
        procenatNP = Integer.toString(procentiNP) + "%";
        canvas.drawText(procenatNP, 3*getWidth()/4 - 75, 2*getHeight()/3 + 25, paintText);

        canvas.drawCircle(getWidth()/4, 2*getHeight()/3, 150, paintCircle);
        canvas.drawText(getResources().getString(R.string.srednji_prioritet), getWidth()/4 - 165, 2*getHeight()/3 + 200, paintCircle);
        rect.set(getWidth()/4 - 150, 2*getHeight()/3 - 150, getWidth()/4 + 150, 2*getHeight()/3 + 150);
        canvas.drawArc(rect, 270, currentAngleSP, true, yellowPie);
        procenatSP = Integer.toString(procentiSP) + "%";
        canvas.drawText(procenatSP, getWidth()/4 - 75, 2*getHeight()/3 + 25, paintText);
    }
}

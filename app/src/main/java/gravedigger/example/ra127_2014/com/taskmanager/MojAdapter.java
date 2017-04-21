package gravedigger.example.ra127_2014.com.taskmanager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableWrapper;
import android.provider.CalendarContract;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * Created by Jelena on 4/15/2017.
 */

public class MojAdapter extends BaseAdapter {

    ArrayList<Zadatak> zadaci;
    private Context mContext;
    public String TAG = "MojAdapter";
    Calendar calendar = Calendar.getInstance();
    int godina = calendar.get(Calendar.YEAR);
    int mesec = calendar.get(Calendar.MONTH) + 1;
    int dan = calendar.get(Calendar.DAY_OF_MONTH);
    int danUnedelji = calendar.get(Calendar.DAY_OF_WEEK);
    String dateDanas, dateSutra, datePrekosutra, dateZa3Dana, dateZa4Dana, dateZa5Dana, dateZa6Dana, dateZa7Dana;
    int sutra, prekosutra, za3dana, za4dana, za5dana, za6dana, za7dana;

    public void addZadatak(Zadatak zadatak){
        Log.d(TAG, "addZadatak: usao");
        zadaci.add(zadatak);
        notifyDataSetChanged();
    }

    public void removeZadatak(Zadatak zadatak){
        Log.d(TAG, "removeZadatak: usao");
        zadaci.remove(zadatak);
        notifyDataSetChanged();
    }

    public MojAdapter(Context context){
        mContext = context;
        zadaci = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return zadaci.size();
    }

    @Override
    public Object getItem(int position) {
        return zadaci.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView datum = null, imeZadatka = null, vaznost = null, vreme = null;
        public CheckBox uradjen = null;
        public RadioButton podsetnik = null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "getView: usao");
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task_element, null);
            ViewHolder holder = new ViewHolder();
            holder.vaznost = (TextView) view.findViewById(R.id.prioritet);
            holder.datum = (TextView) view.findViewById(R.id.datum);
            holder.vreme = (TextView) view.findViewById(R.id.vreme);
            holder.imeZadatka = (TextView) view.findViewById(R.id.nazivZadatka);
            holder.uradjen = (CheckBox) view.findViewById(R.id.boxZadatka);
            holder.podsetnik = (RadioButton) view.findViewById(R.id.radioButton);
            view.setTag(holder);
        }

        Zadatak zadaci = (Zadatak) getItem(position);
        final ViewHolder holder = (ViewHolder) (view.getTag());
        switch (zadaci.getVaznost()) {
            case "RED":
                holder.vaznost.setBackgroundResource(R.drawable.red_rounded_background);
                break;
            case "YELLOW":
                holder.vaznost.setBackgroundResource(R.drawable.yellow_rounded_background);
                break;
            default:
                holder.vaznost.setBackgroundResource(R.drawable.green_rounded_background);
                break;
        }
        holder.vreme.setText(zadaci.getVreme());
        holder.imeZadatka.setText(zadaci.getIme());
        holder.podsetnik.setChecked(false);
        if(zadaci.isPodsetnik()) {
            holder.podsetnik.setChecked(true);
        }

        holder.uradjen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.uradjen.isChecked()){
                    holder.imeZadatka.setPaintFlags(holder.imeZadatka.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.imeZadatka.setPaintFlags(holder.imeZadatka.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });

        if(mesec == Calendar.JANUARY || mesec == Calendar.MARCH || mesec == Calendar.MAY || mesec == Calendar.JULY
                || mesec == Calendar.AUGUST || mesec == Calendar.OCTOBER || mesec == Calendar.DECEMBER){
            if(dan == 31) {
                if(mesec == Calendar.DECEMBER){
                    sutra = 1;
                    prekosutra = 2;
                    za3dana = 3;
                    za4dana = 4;
                    za5dana = 5;
                    za6dana = 6;
                    za7dana = 7;
                    mesec++;
                    godina++;
                }else {
                    sutra = 1;
                    prekosutra = 2;
                    za3dana = 3;
                    za4dana = 4;
                    za5dana = 5;
                    za6dana = 6;
                    za7dana = 7;
                    mesec++;
                }
            }else {
                sutra = dan + 1;
                prekosutra = dan + 2;
                za3dana = dan + 3;
                za4dana = dan + 4;
                za5dana = dan + 5;
                za6dana = dan + 6;
                za7dana = dan + 7;
            }
        }else if(mesec == Calendar.APRIL || mesec == Calendar.JUNE || mesec == Calendar.SEPTEMBER || mesec == Calendar.NOVEMBER){
            if(dan == 30) {
                sutra = 1;
                prekosutra = 2;
                za3dana = 3;
                za4dana = 4;
                za5dana = 5;
                za6dana = 6;
                za7dana = 7;
                mesec++;
            }else {
                sutra = dan + 1;
                prekosutra = dan + 2;
                za3dana = dan + 3;
                za4dana = dan + 4;
                za5dana = dan + 5;
                za6dana = dan + 6;
                za7dana = dan + 7;
            }
        }else{
            if(((godina % 4 == 0) && !(godina % 100 == 0)) || (godina % 400 == 0)){
                if(dan == 29){
                    sutra = 1;
                    prekosutra = 2;
                    za3dana = 3;
                    za4dana = 4;
                    za5dana = 5;
                    za6dana = 6;
                    za7dana = 7;
                    mesec++;
                }else {
                    sutra = dan + 1;
                    prekosutra = dan + 2;
                    za3dana = dan + 3;
                    za4dana = dan + 4;
                    za5dana = dan + 5;
                    za6dana = dan + 6;
                    za7dana = dan + 7;
                }
            }else {
                if (dan == 28) {
                    sutra = 1;
                    prekosutra = 2;
                    za3dana = 3;
                    za4dana = 4;
                    za5dana = 5;
                    za6dana = 6;
                    za7dana = 7;
                    mesec++;
                } else {
                    sutra = dan + 1;
                    prekosutra = dan + 2;
                    za3dana = dan + 3;
                    za4dana = dan + 4;
                    za5dana = dan + 5;
                    za6dana = dan + 6;
                    za7dana = dan + 7;
                }
            }
        }
        dateDanas = String.format("%02d.%02d.", dan, mesec) + godina;
        dateSutra = String.format("%02d.%02d.", sutra, mesec) + godina;
        datePrekosutra = String.format("%02d.%02d.", prekosutra, mesec) + godina;
        dateZa3Dana = String.format("%02d.%02d.", za3dana, mesec) + godina;
        dateZa4Dana = String.format("%02d.%02d.", za4dana, mesec) + godina;
        dateZa5Dana = String.format("%02d.%02d.", za5dana, mesec) + godina;
        dateZa6Dana = String.format("%02d.%02d.", za6dana, mesec) + godina;
        dateZa7Dana = String.format("%02d.%02d.", za7dana, mesec) + godina;

        if(zadaci.getDatum().equals(dateDanas)){
            holder.datum.setText(R.string.danas);
        }else if(zadaci.getDatum().equals(dateSutra)){
            holder.datum.setText(R.string.sutra);
        }else if(zadaci.getDatum().equals(datePrekosutra)){
            switch (danUnedelji){
                case 1 :
                    holder.datum.setText(R.string.utorak);
                    break;
                case 2 :
                    holder.datum.setText(R.string.sreda);
                    break;
                case 3 :
                    holder.datum.setText(R.string.cetvrtak);
                    break;
                case 4 :
                    holder.datum.setText(R.string.petak);
                    break;
                case 5 :
                    holder.datum.setText(R.string.subota);
                    break;
                case 6 :
                    holder.datum.setText(R.string.nedelja);
                    break;
                case 7 :
                    holder.datum.setText(R.string.ponedeljak);
            }
        }else if(zadaci.getDatum().equals(dateZa3Dana)){
            switch (danUnedelji){
                case 1 :
                    holder.datum.setText(R.string.sreda);
                    break;
                case 2 :
                    holder.datum.setText(R.string.cetvrtak);
                    break;
                case 3 :
                    holder.datum.setText(R.string.petak);
                    break;
                case 4 :
                    holder.datum.setText(R.string.subota);
                    break;
                case 5 :
                    holder.datum.setText(R.string.nedelja);
                    break;
                case 6 :
                    holder.datum.setText(R.string.ponedeljak);
                    break;
                case 7 :
                    holder.datum.setText(R.string.utorak);
            }
        }else if(zadaci.getDatum().equals(dateZa4Dana)){
            switch (danUnedelji){
                case 1 :
                    holder.datum.setText(R.string.cetvrtak);
                    break;
                case 2 :
                    holder.datum.setText(R.string.petak);
                    break;
                case 3 :
                    holder.datum.setText(R.string.subota);
                    break;
                case 4 :
                    holder.datum.setText(R.string.nedelja);
                    break;
                case 5 :
                    holder.datum.setText(R.string.ponedeljak);
                    break;
                case 6 :
                    holder.datum.setText(R.string.utorak);
                    break;
                case 7 :
                    holder.datum.setText(R.string.sreda);
            }
        }else if(zadaci.getDatum().equals(dateZa5Dana)){
            switch (danUnedelji){
                case 1 :
                    holder.datum.setText(R.string.petak);
                    break;
                case 2 :
                    holder.datum.setText(R.string.subota);
                    break;
                case 3 :
                    holder.datum.setText(R.string.nedelja);
                    break;
                case 4 :
                    holder.datum.setText(R.string.ponedeljak);
                    break;
                case 5 :
                    holder.datum.setText(R.string.utorak);
                    break;
                case 6 :
                    holder.datum.setText(R.string.sreda);
                    break;
                case 7 :
                    holder.datum.setText(R.string.cetvrtak);
            }
        }else if(zadaci.getDatum().equals(dateZa6Dana)){
            switch (danUnedelji){
                case 1 :
                    holder.datum.setText(R.string.subota);
                    break;
                case 2 :
                    holder.datum.setText(R.string.nedelja);
                    break;
                case 3 :
                    holder.datum.setText(R.string.ponedeljak);
                    break;
                case 4 :
                    holder.datum.setText(R.string.utorak);
                    break;
                case 5 :
                    holder.datum.setText(R.string.sreda);
                    break;
                case 6 :
                    holder.datum.setText(R.string.cetvrtak);
                    break;
                case 7 :
                    holder.datum.setText(R.string.petak);
            }
        }else if(zadaci.getDatum().equals(dateZa7Dana)){
            switch (danUnedelji){
                case 1 :
                    holder.datum.setText(R.string.nedelja);
                    break;
                case 2 :
                    holder.datum.setText(R.string.ponedeljak);
                    break;
                case 3 :
                    holder.datum.setText(R.string.utorak);
                    break;
                case 4 :
                    holder.datum.setText(R.string.sreda);
                    break;
                case 5 :
                    holder.datum.setText(R.string.cetvrtak);
                    break;
                case 6 :
                    holder.datum.setText(R.string.petak);
                    break;
                case 7 :
                    holder.datum.setText(R.string.subota);
            }
        }else{
            holder.datum.setText(zadaci.getDatum());
        }

        return view;
    }
}

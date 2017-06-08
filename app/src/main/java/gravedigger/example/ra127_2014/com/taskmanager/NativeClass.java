package gravedigger.example.ra127_2014.com.taskmanager;

/**
 * Created by Jelena on 6/5/2017.
 */

public class NativeClass {


    public native float izracunajProcente(float zavrseni, float svi);

    static {
        System.loadLibrary("RacunanjeProcenata");
    }
}

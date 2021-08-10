package lunga.com.buddielam;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExecutableService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //this message will be displayed during the intervals
        Ringtone ringtone = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        ringtone.play();
        Toast.makeText(context, "ringtone sounds " + Calendar.getInstance().getTime().toString(), Toast.LENGTH_SHORT).show();
        SystemClock.sleep(9000);
        ringtone.stop();

    }
}



/*
* Ringtone ringtone = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
            ringtone.play();
            Toast.makeText(context, "ringtone sounds " + Calendar.getInstance().getTime().toString(), Toast.LENGTH_SHORT).show();
            SystemClock.sleep(9000);
            ringtone.stop();
*
*
*
* */

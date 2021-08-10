package lunga.com.buddielam;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AalarmHandler {

     Context context;

    public AalarmHandler(Context context) {
        this.context = context;
    }

    //to set the alarm
    public void setAlarmManager()
    {
        Intent intent = new Intent(context, ExecutableService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (am != null)
        {
            //this time is calculated in milliseconds / 60 minutes * 60 seconds * 1000 milliseconds = 1 hour
            long triggerAfter = 60000; //60 * 60 * 1000; //<- this will trigger after 1 hour
            long triggerEvery = 60000;//60 * 60 * 1000; //<- this will repeat after an hour
            am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAfter, triggerEvery,sender);
        }
    }

    //to cancel the alarm
    public void cancelAalarmManager()
    {
        Intent intent = new Intent(context, ExecutableService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (am != null)
        {
            am.cancel(sender);
        }

    }
}

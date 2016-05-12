package edu.stanford.parkle;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by juanj on 5/5/16.
 */
public class ParkLE extends Application {

    protected static final long ALARM_INTERVAL = 20*1000;
    protected static final long SCAN_PERIOD = 2*1000;

    protected static final String INTENT_ACTION_CHECK_BEACON = "edu.parkle.CHECK_BEACON";

    protected static final String USER_INFO = "userInfo";
    protected static final String CAR_STATE_INFO = "CURRENT_STATE" ;
//    protected static final String BEACON_STATE_INFO = "BEACON_STATE";
    protected static final String BEACON_ADDRESS_INFO = "BEACON_ADDRESS";
//    protected static final String CAR_MODULE_STATE_INFO = "carModuleState";

    protected static final String CAR_MODULE_ADDRESS = "E9:40:B9:B9:C0:05";
    protected static final String LOT_A_BEACON_ADDRESS = "FA:AD:C0:21:19:3C";
    protected static final String LOT_B_BEACON_ADDRESS = "C8:05:62:94:0C:10";
    protected static final String LOT_C_BEACON_ADDRESS = "D4:85:90:D6:9A:CD";

    protected static final int CAR_NOT_IN_LOT = 0;
    protected static final int CAR_IDLE_IN_LOT = 1;
    protected static final int CAR_PARKED_IN_LOT = 2;
    protected static final int NOT_CONNECTED = 0;
    protected static final int CONNECTED = 1;

    protected static SharedPreferences sharedPreferences;

    protected static final Map<String, String> lotNames = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        sharedPreferences = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CAR_STATE_INFO, CAR_IDLE_IN_LOT);
//        editor.putInt(BEACON_STATE_INFO, CONNECTED);
        editor.putString(BEACON_ADDRESS_INFO, "FA:AD:C0:21:19:3C");
//        editor.putInt(CAR_MODULE_STATE_INFO, CONNECTED);
        editor.commit();

        lotNames.put(LOT_A_BEACON_ADDRESS, getString(R.string.lot_a_name));
        lotNames.put(LOT_B_BEACON_ADDRESS, getString(R.string.lot_b_name));
        lotNames.put(LOT_C_BEACON_ADDRESS, getString(R.string.lot_c_name));


        boolean userExists = true;
        if (userExists) {
            Intent checkBeaconAlarm = new Intent(this, BeaconWakefulReceiver.class);
            checkBeaconAlarm.setAction(INTENT_ACTION_CHECK_BEACON);
            PendingIntent pendingCheckBeaconAlarm = PendingIntent.getBroadcast(this, 0, checkBeaconAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarms = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarms.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_INTERVAL, pendingCheckBeaconAlarm);

            Log.e("ME202", "Setting alarm");
        }
    }

}
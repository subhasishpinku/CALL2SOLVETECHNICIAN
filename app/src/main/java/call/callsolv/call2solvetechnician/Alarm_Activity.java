package call.callsolv.call2solvetechnician;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper;
import call.callsolv.call2solvetechnician.Alam.AlarmNotificationService;
import call.callsolv.call2solvetechnician.Alam.AlarmReceiver;
import call.callsolv.call2solvetechnician.Alam.AlarmSoundService;
import call.callsolv.call2solvetechnician.ServiceActivity.GPSTracker;
import call.callsolv.call2solvetechnician.ServiceActivity.MyService;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
import pl.droidsonroids.gif.GifImageView;

import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_FLAGTABLE;
import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_ID;
import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_PENDINGCOUNT;

public class Alarm_Activity extends AppCompatActivity {
    Button start,stop;
    private PendingIntent pendingIntent;
    private static final int ALARM_REQUEST_CODE = 133;
    int totpendingcall1;
    String techid;
    private DatabaseHelper db;
    String flagId,pendingcallDB;
    TextView jobId,cusnameId,proId,serviceId,chargenamId;
    String SystemTime,datee;
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_permission;
    Intent myIntent = null;
    MyMainReceiver myMainReceiver;
    Toolbar toolbar;
    GifImageView imgId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
          //    WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.alam_activity);
        stop = (Button)findViewById(R.id.stop);
        jobId = (TextView)findViewById(R.id.jobId);
        cusnameId = (TextView)findViewById(R.id.cusnameId);
        proId = (TextView)findViewById(R.id.proId);
        serviceId = (TextView)findViewById(R.id.serviceId);
        chargenamId = (TextView)findViewById(R.id.chargenamId);
        imgId = (GifImageView)findViewById(R.id.imgId);
        initToolBar();
        Calendar cal = Calendar.getInstance();
        Date sdate=cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        SystemTime=dateFormat.format(sdate);
        SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date =new Date();
        datee= parseFormat.format(date);
        Log.e("Sys"," "+SystemTime+" "+datee);
        //currenttimeID.setText(SystemTime);
       // closetimeID.setText(datee);
        Intent alarmIntent = new Intent(Alarm_Activity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(Alarm_Activity.this, ALARM_REQUEST_CODE, alarmIntent, 0);
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        setAlarm();
        cusditaleshow(techid);
        alarm(techid);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarmManager();
            }
        });
        db = new DatabaseHelper(getApplicationContext());


    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("New Call Found");
        setSupportActionBar(toolbar);
      //  toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent intent = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
                        // startActivity(intent);
                    }
                }
        );
    }
    public void setAlarm() {
        int alarmTriggerTime = 1000;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, alarmTriggerTime);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmTriggerTime, pendingIntent);
        //Toast.makeText(this, "Alarm Set for " + alarmTriggerTime + " seconds.", Toast.LENGTH_SHORT).show();
    }
    public void stopAlarmManager() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent
        stopService(new Intent(Alarm_Activity.this, AlarmSoundService.class));
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);
        // Toast.makeText(this, "Alarm Canceled/Stop by User.", Toast.LENGTH_SHORT).show();
        startService();
        Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
    }
    public void alarm(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TOTALCAKLLCOUNTALAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TotalCallCount2", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            totpendingcall1 = obj.getInt("res");
                            Cursor res = db.getAllLoginData();
                            if(res.getCount() == 0) {
                                Log.e("Error","Nothing found");
                                return;
                            }
                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()) {
                                flagId =  res.getString(0);
                                pendingcallDB =  res.getString(1);
                                Log.e("FLAGID"," "+flagId+" "+pendingcallDB);
                                SQLiteDatabase database = db.getReadableDatabase();
                                database.execSQL( "UPDATE "+TABLE_FLAGTABLE +" SET " + TABLE_PENDINGCOUNT+ " = '"+totpendingcall1+"' WHERE "+TABLE_ID+ " = "+flagId);

                            }
                            setAlarm();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "UserName Password Not Valid", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tech_id", techid);
                params.put("flag","1");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    private void startService(){
        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        if (gpsTracker.getIsGPSTrackingEnabled()){
            myIntent = new Intent(Alarm_Activity.this, MyService.class);
            startService(myIntent);
        }
        else {
            gpsTracker.showSettingsAlert();
        }
    }
    private class MyMainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(MyService.ACTION_UPDATE_CNT)){
                int int_from_service = intent.getIntExtra(MyService.KEY_INT_FROM_SERVICE, 0);
                // textViewCntReceived.setText(String.valueOf(int_from_service));
            }else if(action.equals(MyService.ACTION_UPDATE_MSG)){
                String string_from_service = intent.getStringExtra(MyService.KEY_STRING_FROM_SERVICE);
                //  textViewMsgReceived.setText(String.valueOf(string_from_service));
            }


        }

    }
    public void cusditaleshow(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CUSTOMERDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CUSTOMERdITAILS", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String jobid = obj.getString("job_id");
                            String customername = obj.getString("customer_name");
                            String proname = obj.getString("pro_name");
                            String servcadrs = obj.getString("servc_adrs");
                            String chargename = obj.getString("charge_name");
                            jobId.setText(jobid);
                            cusnameId.setText(customername);
                            proId.setText(proname);
                            serviceId.setText(servcadrs);
                            chargenamId.setText(chargename);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "UserName Password Not Valid", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tech_id", techid);
                Log.e("CUSTOMERdITAILS",techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    @Override
    protected void onStart() {
        myMainReceiver = new MyMainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.ACTION_UPDATE_CNT);
        intentFilter.addAction(MyService.ACTION_UPDATE_MSG);
        registerReceiver(myMainReceiver, intentFilter);
        super.onStart();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("Inside onResume");
    }

//    @Override
//    public void onStart(){
//        super.onStart();
//        System.out.println("Inside onStart");
//    }

    @Override
    public void onRestart(){
        super.onRestart();
        System.out.println("Inside onReStart");
    }

    @Override
    public void onPause(){
        super.onPause();
        System.out.println("Inside onPause");
    }

    @Override
    public void onStop(){
        super.onStop();
        System.out.println("Inside onStop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        System.out.println("Inside onDestroy");
        stopAlarmManager();
    }
}

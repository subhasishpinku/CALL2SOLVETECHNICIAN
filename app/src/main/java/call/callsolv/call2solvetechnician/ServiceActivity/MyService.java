package call.callsolv.call2solvetechnician.ServiceActivity;
import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper;
import call.callsolv.call2solvetechnician.Alarm_Activity;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
import call.callsolv.call2solvetechnician.SharedPrefManagerProfile;
import call.callsolv.call2solvetechnician.TabsAdapter;
import call.callsolv.call2solvetechnician.URLs;
import call.callsolv.call2solvetechnician.VolleySingleton;

import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_FLAGTABLE1;
import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_ID1;
import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_PENDINGCOUNT1;

public class MyService extends Service  implements LocationListener , OnMapReadyCallback {
    boolean isGPSEnable=false, isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private Handler myHandler = new Handler();
    private Handler myHandlerr = new Handler();
    private Timer myTimer = null;//60000 300000 900000
    long timeInterval = 60000;
    private Timer mmyTimer = null;//60000 300000 900000
    long ttimeInterval = 60000;
    public static String str_receiver = "com.bracesmedia.tracking";
    Intent intent;
    public static final String SERVICE = "http://call2solv.com/cal2solv-service/track_det_flag_update.php";
    //from MyService to Profile_Activity
    public final static String KEY_INT_FROM_SERVICE = "KEY_INT_FROM_SERVICE";
    public  final static String KEY_STRING_FROM_SERVICE = "KEY_STRING_FROM_SERVICE";
    public  final static String ACTION_UPDATE_CNT = "UPDATE_CNT";
    public final static String ACTION_UPDATE_MSG = "UPDATE_MSG";
    //from Profile_Activity to MyService
    final static String KEY_MSG_TO_SERVICE = "KEY_MSG_TO_SERVICE";
    final static String ACTION_MSG_TO_SERVICE = "MSG_TO_SERVICE";
    MyServiceReceiver myServiceReceiver;
    MyServiceThread myServiceThread;
    int cnt;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    String  address,stateName,countryName;
    private int PERMISSION_REQUEST_CODE = 100;
    String stringLatitude;
    String stringLongitude;
    String country;
    String city;
    String postalCode;
    String addressLine;
    String time1,Date,bookingcode,trackflag,techid;
    String addressLinee;
    CountDownTimer timer;
    Timer Endtimer;
    SharedPreferences sp;
    ////////sync////////////////////////////////////
    public static final int NAME_SYNCED_WITH_SERVERRUN = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVERRUN = 0;
    private BroadcastReceiver broadcastReceiver;
    public static final String RUNDATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";
 //   private DatabaseHelper db;
 //   DatabaseHelper DB = null;
    ////////////////////////////////////////////////
    String EMPODIN ="1";
    String EMPODINN;
    String  time;
    int hour,mintes,mint,mintesss;
    String offlineAddress,offlineAddress1;
    boolean mAllowRebind;
    ExampleService exampleService;
  //  private NetworkStateCheckerRunTime networkStateCheckerRunTime;
    String runtime,SystemTime,Time,Time1;
    private static final int ALARM_REQUEST_CODE = 133;
    PendingIntent pendingIntent;
    private NotificationManagerCompat notificationManager;
    String FLAG = "0";
    Timer timerr;
    Timer loguot;
    int hourr = 0;
    int mintess =0;
    int second =0;
    String DATETIME,FGG;
    String FLAGG = "1";
    int MIN;
    String FGF = "1";
    String dbfg= "1";
    int dd,mm,yy,ddd,mmm,yyy;
    String sdd,smm,syy,dddd,dmmm,dyyy;
    String formattedDate,checkdate;
    boolean state;
    boolean mobileDataEnabled = false;
    boolean wifidata;
    String ServiceOff= "00";
    String totpendingcall,totacptcall,totacptcall1,flag;
    int totpendingcall1;
    int totpendingcalll;
    int pendingcallDB;
    int pendingcallDB1;
    String flagId,flagId1;
    private DatabaseHelper db;
    @Override
    public void onCreate() {
        super.onCreate();
        //   Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_LONG).show();
        myServiceReceiver = new MyServiceReceiver();
        myTimer = new Timer();
        myTimer.schedule(new TimerTaskToGetLocation(), 1, timeInterval);
        mmyTimer = new Timer();
        mmyTimer.schedule(new Logg(), 1, ttimeInterval);
        intent = new Intent(str_receiver);
        exampleService =new ExampleService();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
            }
        }
        else{

        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        db = new DatabaseHelper(getApplicationContext());
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //   Toast.makeText(getApplicationContext(),"onStartCommand", Toast.LENGTH_LONG).show();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_MSG_TO_SERVICE);
        registerReceiver(myServiceReceiver, intentFilter);
        myServiceThread = new MyServiceThread();
        myServiceThread.start();
        bookingcode = intent.getStringExtra("bookingcode");
        trackflag = intent.getStringExtra("track_flag");
        techid = intent.getStringExtra("tech_id");
        Log.e("bookingcode",bookingcode+" "+trackflag);
       // alam();
        return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("EXIT", "onBind!");
        return null;

    }
    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("onDestroy", "onUnbind!");
        return mAllowRebind;
    }
    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {
        Log.e("onDestroy", "onRebind!");
    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("onDestroy", "onStart!");
    }
    public class ExampleService extends IntentService {

        // Must create a default constructor
        public ExampleService() {
            super("example-service");
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
            // This describes what will happen when service is triggered
            Log.e("onDestroy", "onHandleIntent!");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
           //Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_LONG).show();
        Log.e("rundate","onDestroy");
        myServiceThread.setRunning(false);
        unregisterReceiver(myServiceReceiver);
        ServiceOff= "1";

    }

    public boolean isNetworkAvailable() {
        boolean connect=false;
        ConnectivityManager conMgr =  (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            connect=false;
        }else{
            connect= true;
        }
        return connect;
    }

    public class MyServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(ACTION_MSG_TO_SERVICE)){
                String msg = intent.getStringExtra(KEY_MSG_TO_SERVICE);
                msg = new StringBuilder(msg).reverse().toString();
                //send back to Profile_Activity
                Intent i = new Intent();
                i.setAction(ACTION_UPDATE_MSG);
                i.putExtra(KEY_STRING_FROM_SERVICE, msg);
                sendBroadcast(i);
            }
        }
    }
    private class MyServiceThread extends Thread{
        private boolean running;
        public void setRunning(boolean running){
            this.running = running;
        }
        @Override
        public void run() {
            cnt = 0;
            running = true;
            while (running){
                try {
                    Thread.sleep(1000);
                    Intent intent = new Intent();
                    intent.setAction(ACTION_UPDATE_CNT);
                    intent.putExtra(KEY_INT_FROM_SERVICE, cnt);
                    sendBroadcast(intent);
                    cnt++;
                    Log.e("value","LoopLoop");
                    if (isNetworkAvailable()){
                        alam();
                    }
                    else {

                    }
                    //mobilecheack();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void alam(){
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
       // Log.e("Value",techid);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TOTALCAKLLCOUNT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TotalCallCount", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            totpendingcall = obj.getString("tot_pending_call");
                            totacptcall = obj.getString("tot_acpt_call");
                            flag = obj.getString("flag");
                            totpendingcalll = Integer.parseInt(totpendingcall);
                            ALAR(totpendingcalll);
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
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void ALAR(int totpendingcalll){
        Cursor res = db.getAllLoginData();
        if(res.getCount() == 0) {
            Log.e("Error","Nothing found");
            db.addpending(totpendingcalll);
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            flagId =  res.getString(0);
            pendingcallDB =  res.getInt(1);
            Log.e("FLAGID"," "+flagId+" "+pendingcallDB);
        }
        /////////////////////////
        Cursor ress = db.getAllLoginData1();
        if(ress.getCount() == 0) {
            Log.e("Error1","Nothing found");
            db.addpending1(totpendingcalll);
            return;
        }
        StringBuffer bufferr = new StringBuffer();
        while (ress.moveToNext()) {
            flagId1 = ress.getString(0);
            pendingcallDB1 = ress.getInt(1);
            Log.e("FLAGID1", " " + flagId + " " + pendingcallDB1);
            Log.e("Value", " " + pendingcallDB1 + " " + pendingcallDB);
            if (pendingcallDB == 0) {
                db.addpending(totpendingcalll);

            } else {
                Log.e("Error1", "Ok");
                if (pendingcallDB1 > pendingcallDB) {
                    Log.e("Error1", "Start");
                    Intent intent = new Intent(getApplicationContext(), Alarm_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Log.e("FLAGID1", " " + "1");
                } else {
                    Log.e("FLAGID1", " " + "0");
                }
            }
        }
        if(ress.getCount() == 1){
            Log.e("Error1","Yes found");
            SQLiteDatabase database = db.getReadableDatabase();
            database.execSQL( "UPDATE "+TABLE_FLAGTABLE1 +" SET " + TABLE_PENDINGCOUNT1+ " = '"+totpendingcalll+"' WHERE "+TABLE_ID1+ " = "+flagId1);
            return;
        }
        ////////////////////////////
    }
    private void getLocationInfo(){
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnable && !isNetworkEnable){

        } else {
            if (isNetworkEnable) {
//                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 0, (LocationListener) this);
                if (locationManager != null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {

                        Log.e("latitude", location.getLatitude() + "");
                        Log.e("longitude", location.getLongitude() + "");
                        //  Toast.makeText(this,"Location"+location.getLatitude()+" "+location.getLongitude(),Toast.LENGTH_SHORT).show();
                        time1 = new SimpleDateFormat("HH:mm").format(location.getTime());
                        Date = new SimpleDateFormat("yyyy-MM-dd").format(location.getTime());
                        GPSTracker gpsTracker = new GPSTracker(this);
                        stringLatitude = String.valueOf(gpsTracker.latitude);
                        stringLongitude = String.valueOf(gpsTracker.longitude);
                        country = gpsTracker.getCountryName(this);
                        city = gpsTracker.getLocality(this);
                        postalCode = gpsTracker.getPostalCode(this);
                        addressLine = gpsTracker.getAddressLine(this);
                        //  Toast.makeText(getApplicationContext()," " +stringLatitude+ ""+stringLongitude+ ""+country+""+city+""+postalCode+ ""+addressLine+ "service lati", Toast.LENGTH_SHORT).show();
                        Log.e("gps",stringLatitude+ "," +stringLongitude+ ","+country+ ","+city+""+postalCode+""+addressLine);
                        //   addressLinee =  country+ ","+city+", "+postalCode+", "+addressLine;
                        addressLinee =  addressLine;
                        if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER))
                            Log.e("Location1", "Time GPS: " + time1); // This is what we want!
                        else
                            Log.e("Location1", "Time Device (" + location.getProvider() + "): " + time1);
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                        runtime = time.format(c.getTime());
                        Log.e("Rtime",runtime);
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Log.e("Service",", "+latitude +longitude);
                        //  Toast.makeText(getApplicationContext(),"SpAddress" +addressLinee, Toast.LENGTH_SHORT).show();
                        serviceUpdateLocation(location);
                        getCompleteAddressString(latitude,longitude);
                    }
                }
                if (isGPSEnable) {
                    //   location = null;
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, (LocationListener) this);
                    if (locationManager!=null){
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location!=null){
                            Log.e("latitude",location.getLatitude()+"");
                            Log.e("longitude",location.getLongitude()+"");
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.e("Service1",", "+location.getLatitude()+" " +location.getLongitude()+" "+"Loc"+latitude+" "+","+longitude);
                            //  getAddressFromLocation(latitude,longitude, new GeocoderHandler());
                            final String time2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(location.getTime());
                            if( location.getProvider().equals(LocationManager.GPS_PROVIDER))
                                Log.e("Location", "Time GPS: " + time2); // This is what we want!
                            else
                            Log.e("Location", "Time Device (" + location.getProvider() + "): " + time2);
                            serviceUpdateLocation(location);
                            getCompleteAddressString(latitude,longitude);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVICE,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject obj = new JSONObject(response);
                                                Log.e("RUNTIME"," "+obj);
                                                if (!obj.getBoolean("error")) {

                                                }else {


                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
//                                    if (ServiceOff.equals("1")){
//
//                                    }
//                                    else {
//                                      }
                                    params.put("job_id", bookingcode);
                                    params.put("date", Date);
                                    params.put("time", runtime);
                                    params.put("actual_loc", addressLinee);
                                    params.put("latitude", String.valueOf(latitude));
                                    params.put("longitude",String.valueOf(longitude));
                                    params.put("track_flag", trackflag);
                                    params.put("tech_id",techid);
                                    Log.e("ValueStart",bookingcode+" "
                                            +Date+" "+runtime+" "+addressLinee
                                            +" "+String.valueOf(latitude)+" "+String.valueOf(longitude)+" "+trackflag+" "+techid);
                                    return params;
                                }
                            };
                            //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
                            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
                            stringRequest.setShouldCache(false);
                            volleySingleton.addToRequestQueue(stringRequest);
                        }
                    }
                }
            }
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
    private class Logg extends TimerTask{
        @Override
        public void run() {
            myHandlerr.post(new Runnable() {
                @Override
                public void run() {
                }
            }); }}
    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (isNetworkAvailable()){
                       // myServiceThread = new MyServiceThread();
                     //   myServiceThread.start();
                        alam();
                        getLocationInfo();
                    }
                    else {

                    }

                }
            });

        }
    }
    private void serviceUpdateLocation(Location location){
        intent.putExtra("latutide",location.getLatitude()+"");
        intent.putExtra("longitude",location.getLongitude()+"");
        sendBroadcast(intent);
        Log.e("cityyy"," "+location.getLatitude()+" "+location.getLongitude());
        getCompleteAddressString(location.getLatitude(),location.getLongitude());
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.e("My Current loction"+"Time"+" "+time1, strReturnedAddress.toString());
            } else {
                Log.e("My Current loction"+"Time"+" "+time1, "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("My Current loction"+"Time"+" "+time1, "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    public void onLocationChanged(Location loc){
        locationManager.removeUpdates(this);
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
        getCompleteAddressString(loc.getLatitude(),loc.getLongitude());
        Log.e("LOCATIONCHANGE"," "+loc.getLatitude()+" "+loc.getLongitude());
        String cityName = null;
        String locality = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0)
//                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            locality = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = longitude + " " + latitude + "My Currrent City is:"
                + cityName;
        addressLinee = cityName+" "+locality;
        Log.e("city"," "+s+" "+addressLinee);
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    public void onProviderDisabled(String provider) {

    }
}
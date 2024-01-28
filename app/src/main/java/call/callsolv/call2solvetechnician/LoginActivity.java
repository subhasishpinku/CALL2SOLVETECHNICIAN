package call.callsolv.call2solvetechnician;
import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import call.callsolv.call2solvetechnician.ServiceActivity.GPSTracker;
import call.callsolv.call2solvetechnician.ServiceActivity.MyService;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
public class LoginActivity extends AppCompatActivity implements  View.OnClickListener {
    LinearLayout loginID;
    EditText useID, passwordID;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private int inter = 0;
    String currentVersion;
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_permission;
    Intent myIntent = null;
    MyMainReceiver myMainReceiver;
    private static final int STORAGE_PERMISSION_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        useID = (EditText) findViewById(R.id.useID);
        passwordID = (EditText) findViewById(R.id.passwordID);
       // useID.setText("IS037T037");
        //passwordID.setText("8420028193");
        loginID = (LinearLayout) findViewById(R.id.loginID);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        loginID.setOnClickListener(this);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            useID.setText(loginPreferences.getString("username", ""));
            passwordID.setText(loginPreferences.getString("pwd", ""));
            saveLoginCheckBox.setChecked(true);
        }
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //new GetVersionCode().execute();
        checkServiceStatus();
        if (SharedPrefManagerProfile.getInstance(this).isLoggedIn()) {
            startService();
            finish();
            startActivity(new Intent(this, NavigationDrawerActivity.class));
            return;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginID:
                startService();
                break;
            default:
        }
    }
    private void startService(){
        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.getIsGPSTrackingEnabled()){
            checkServiceStatus();
            myIntent = new Intent(LoginActivity.this, MyService.class);
            startService(myIntent);
         // ContextCompat.startForegroundService(LoginActivity.this, new Intent(getApplicationContext(), MyService.class));
            new checkconne().execute();
        }
        else {
            gpsTracker.showSettingsAlert();
        }
    }
    public void login(){
        final String username = useID.getText().toString();
        final String password = passwordID.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(username)) {
            useID.setError("Please enter your username");
            useID.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordID.setError("Please enter your password");
            passwordID.requestFocus();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ResponseLogin", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg = obj.getString("msg");
                            String Message = obj.getString("Message");
                            String loginid = obj.getString("login_id");
                            String hubid = obj.getString("hub_id");
                            String spokeid = obj.getString("spoke_id");
                            String techid = obj.getString("tech_id");
                            Log.e("login", " " + msg + " " + Message + " " + loginid + " " + hubid + " " + spokeid + " " + techid);
                            if (saveLoginCheckBox.isChecked()) {
                                loginPrefsEditor.putBoolean("saveLogin", true);
                                loginPrefsEditor.putString("username", useID.getText().toString());
                                loginPrefsEditor.putString("pwd", passwordID.getText().toString());
                                loginPrefsEditor.commit();
                            } else {
                                loginPrefsEditor.clear();
                                loginPrefsEditor.commit();
                            }
                            ProfileSetGet profileSetGet = new ProfileSetGet(
                                    obj.getString("msg"),
                                    obj.getString("Message"),
                                    obj.getString("login_id"),
                                    obj.getString("hub_id"),
                                    obj.getString("spoke_id"),
                                    obj.getString("tech_id")
                            );
                            if (msg.equals("1")){
                                SharedPrefManagerProfile.getInstance(getApplicationContext()).userProfile(profileSetGet);
                                Intent intent_info = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                                startActivity(intent_info);
                                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                                Toast.makeText(getApplicationContext(), obj.getString("Message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("Message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(getApplicationContext(), "UserName Password Not Valid", Toast.LENGTH_SHORT).show();
                      Log.e("ResponseLogin"," "+error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_name", username);
                params.put("user_password", password);
                return params;
            }
        };
       // stringRequest.setRetryPolicy(new DefaultRetryPolicy(3*DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(5*DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    class checkconne extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... args) {
            int kk = 0;
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                kk = urlc.getResponseCode();
            } catch (IOException e) {

                Log.e("qweqwe", "Error checking internet connection", e);
            }
            inter = kk;
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            if (inter == 204) {
                try {
                    currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                new GetVersionCode().execute();
                login();
            } else {
                final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), "No internet connection!", Snackbar.LENGTH_LONG);
                snackBar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Call your action method here
                        snackBar.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                });
                snackBar.setActionTextColor(Color.RED);
                View sbView = snackBar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackBar.show();
            }
        }
    }
    class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + LoginActivity.this.getPackageName()  + "&hl=en")
                        .timeout(1000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;
        }
        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                    if (onlineVersion.equals(currentVersion)) {
                        loginID.setVisibility(View.VISIBLE);
                    } else {
                        loginID.setVisibility(View.GONE);
                        AlertDialog alertDialog = new AlertDialog.Builder( LoginActivity.this).create();
                        alertDialog.setTitle("Update");
                        alertDialog.setIcon(R.mipmap.logo1);
                        alertDialog.setMessage("New Update is available");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + LoginActivity.this.getPackageName())));
                                    Log.e("U","U");
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + LoginActivity.this.getPackageName())));
                                    Log.e("U","U1");
                                }
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alertDialog.show();
                    }
                }
                else {
                    loginID.setVisibility(View.VISIBLE);

                }
            }
            Log.e("update", "Current version " + currentVersion + "playstore version "+" " + onlineVersion);

        }
    }
    public boolean isNetworkAvailable() {
        boolean connect=false;
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            connect=false;
        }else{
            connect= true;
        }
        return connect;
    }

    private void checkServiceStatus() {

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION))) {

            } else {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION

                        },
                        REQUEST_PERMISSIONS);
            }
        } else {
            boolean_permission = true;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                return;
            }
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean_permission = true;
                    requestStoragePermission();

                } else {
                 //   Toast.makeText(getApplicationContext(), "Please enable services to get gps", Toast.LENGTH_LONG).show();
                }
            }
            case STORAGE_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                    } else {
                    }
                //    Toast.makeText(getApplicationContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
                } else {
                //    Toast.makeText(getApplicationContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
                }
            }
        }
           }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        //Checking the request code of our request
//        if (requestCode == STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getApplicationContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
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
    @Override
    protected void onStart() {
        myMainReceiver = new MyMainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.ACTION_UPDATE_CNT);
        intentFilter.addAction(MyService.ACTION_UPDATE_MSG);
        registerReceiver(myMainReceiver, intentFilter);
        super.onStart();
    }
}

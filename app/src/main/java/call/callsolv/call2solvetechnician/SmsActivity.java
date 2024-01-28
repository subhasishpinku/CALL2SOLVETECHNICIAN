package call.callsolv.call2solvetechnician;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.goodiebag.pinview.Pinview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import call.callsolv.call2solvetechnician.ServiceActivity.GPSTracker;
import call.callsolv.call2solvetechnician.ServiceActivity.MyService;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

public class SmsActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    String bookid, techid, bId, bookingcode;
    String otp;
    Button subID;
    Pinview pinview1;
    TextView usernameId, userjobId, usercontracId, serviceId,chargetypeId;
    String prdctcategoryname, prdctname, prdctcmpny, prdctmodel, prdcticon, bookingplaceon, customername, customerphn, customeradrs, productissue, preferdatetime, totalchrg, servicephn;
    Intent myIntent = null;
    String Cusrate,Cuschargeid,Cuschargename,subproid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.sms_activity);
        myIntent = new Intent(getApplicationContext(), MyService.class);
        stopService(myIntent);
        Intent intent = getIntent();
        bookid = intent.getStringExtra("bookid");
        bId = intent.getStringExtra("bId");
        prdctcategoryname = intent.getStringExtra("prdctcategoryname");
        prdctname = intent.getStringExtra("prdctname");
        prdctcmpny = intent.getStringExtra("prdctcmpny");
        prdctmodel = intent.getStringExtra("prdctmodel");
        prdcticon = intent.getStringExtra("prdcticon");
        bookingplaceon = intent.getStringExtra("bookingplaceon");
        customername = intent.getStringExtra("customername");
        customerphn = intent.getStringExtra("customerphn");
        customeradrs = intent.getStringExtra("customeradrs");
        productissue = intent.getStringExtra("productissue");
        preferdatetime = intent.getStringExtra("preferdatetime");
        totalchrg = intent.getStringExtra("totalchrg");
        servicephn = intent.getStringExtra("servicephn");
        bookingcode = intent.getStringExtra("bookingcode");
        Log.e("SMSVALUE", " " + bookid + " " + bId + " " + prdctcategoryname + " "
                + prdctname + " " + prdctcmpny + " " + " " + prdctmodel + " "
                + prdcticon + " " + bookingplaceon + " " + customername + " "
                + customerphn + " " + customeradrs + " " + productissue + " " + preferdatetime + " " + totalchrg + " " + servicephn + " " + bookingcode);
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        initToolBar();
        pinview1 = findViewById(R.id.pinview1);
        subID = (Button) findViewById(R.id.subID);
        subID.setOnClickListener(this);
        usernameId = (TextView) findViewById(R.id.usernameId);
        userjobId = (TextView) findViewById(R.id.userjobId);
        usercontracId = (TextView) findViewById(R.id.usercontracId);
        serviceId = (TextView)findViewById(R.id.serviceId);
        chargetypeId =(TextView)findViewById(R.id.chargetypeId);
        usernameId.setText(customername);
        userjobId.setText("JOB ID :" + " " + bookingcode);
        usercontracId.setText("CONTACT NO :" + " " + customerphn);
        serviceId.setText("Product : "+" "+prdctname);
        pinview1.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                Toast.makeText(SmsActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
                otp = pinview.getValue();
                StartJobOTP(bId, otp, techid);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

        chechradiobutton(bId);
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("OTP");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_sms_black_24dp);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
//                        startActivity(intent);
                    }
                }
        );
    }
    public void chechradiobutton(final String bId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CUSSELECTCHARGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CUSSELETCHARGE", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            Cusrate = obj.getString("rate");
                            Cuschargeid = obj.getString("chrg_typ");
                            Cuschargename = obj.getString("charge_name");
                            subproid = obj.getString("sub_pro_id");
                            chargetypeId.setText(Cuschargename+" : "+Cusrate);
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
                params.put("b_id", bId);
                Log.e("CUSSELETCHARGE", " " + bId+ " ");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    //read otp///
    //https://androidwave.com/automatic-sms-verification-android/
    public void StartJobOTP(final String bId, final String otp, final String techid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CHECKOTPTOSTART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CHECKOTPTOSTARTRESPONSE", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg = obj.getString("msg");
                            String bookid = obj.getString("book_id");
                            String Message = obj.getString("Message");
                            if (msg.equals("1")) {
                                Intent intent = new Intent(getApplicationContext(), Updatebookingproductdetails.class);
                                intent.putExtra("bookid", bookid);
                                intent.putExtra("bId", bId);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                                Toast.makeText(getApplicationContext(), obj.getString("Message"), Toast.LENGTH_SHORT).show();
                            } else {
                                pinview1.clearValue();
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
                        //Toast.makeText(getApplicationContext(), "UserName Password Not Valid", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("book_id", bId);
                params.put("otp", otp);
                params.put("tech_id", techid);
                Log.e("CHECKOTPTOSTART", " " + bId + " " + otp + " " + techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public void resendotpstart(final String bId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.RESENDOTOSTART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("resendotpstart", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            if (status.equals("1")){
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }
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
                params.put("b_id", bId);
                Log.e("resendotpstartview", " " + bId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.subID:
                resendotpstart(bId);
                break;
            default:
        }
    }



    public void onBackPressed() {
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("Inside onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Inside onStart");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        System.out.println("Inside onReStart");
    }

    @Override
    public void onPause(){
        super.onPause();
        System.out.println("Inside onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("Inside onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Inside onDestroy");

    }

}
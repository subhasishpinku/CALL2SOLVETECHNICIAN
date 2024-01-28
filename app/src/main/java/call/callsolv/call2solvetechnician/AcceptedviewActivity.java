package call.callsolv.call2solvetechnician;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import call.callsolv.call2solvetechnician.ServiceActivity.GPSTracker;
import call.callsolv.call2solvetechnician.ServiceActivity.MyService;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
public class AcceptedviewActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    Button subID,trackmeId;
    String bId,techid,trackidjob;
    TextView nameId,jonId,time,asyenbyId,amountID,userNameId,phoneId,phoneIdd,placeId,productNameId,CategoryNameID,serviceNameId,productdateID;
    TextView CompanyId,modelId;
    ImageView imgID;
    Button imgId,imgIdd;
    String customerphone,customerphonee;
    String bookingcode,prdctcategoryname,prdctname,prdctcmpny,prdctmodel,prdcticon,bookingplaceon,customername,customerphn,customeradrs,productissue,preferdatetime,totalchrg,servicephn;
    String bookid;
    MyMainReceiver myMainReceiver;
    Intent myIntent = null;
    String Cusrate,Cuschargeid,Cuschargename,subproid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.acceptedview_activity);
        CompanyId =(TextView) findViewById(R.id.CompanyId);
        modelId =(TextView) findViewById(R.id.modelId);
        subID =(Button)findViewById(R.id.subID);
        trackmeId =(Button)findViewById(R.id.trackmeId);
        nameId = (TextView)findViewById(R.id.nameId);
        jonId = (TextView)findViewById(R.id.jonId);
        time = (TextView)findViewById(R.id.time);
        asyenbyId = (TextView)findViewById(R.id.asyenbyId);
        amountID = (TextView)findViewById(R.id.amountID);
        userNameId = (TextView)findViewById(R.id.userNameId);
        phoneId = (TextView)findViewById(R.id.phoneId);
        phoneIdd = (TextView)findViewById(R.id.phoneIdd);
        placeId = (TextView)findViewById(R.id.placeId);
        productNameId = (TextView)findViewById(R.id.productNameId);
        CategoryNameID = (TextView)findViewById(R.id.CategoryNameID);
        serviceNameId = (TextView)findViewById(R.id.serviceNameId);
        productdateID = (TextView)findViewById(R.id.productdateID);
        imgID = (ImageView)findViewById(R.id.imgID);
        imgId = (Button)findViewById(R.id.imgId);
        imgId.setOnClickListener(this);
        imgIdd =(Button)findViewById(R.id.imgIdd);
        imgIdd.setOnClickListener(this);
        subID.setOnClickListener(this);
        trackmeId.setOnClickListener(this);
        initToolBar();
        Intent intent = getIntent();
        bId = intent.getStringExtra("bId");
        trackidjob = intent.getStringExtra("trackidjob");
        Log.e("bId",bId+" "+trackidjob);
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        acceptedcalllistView(bId);
        if (trackidjob.equals("0")){
            trackmeId.setVisibility(View.VISIBLE);
            subID.setVisibility(View.GONE);
        }
        else if (trackidjob.equals("1")){
            trackmeId.setVisibility(View.GONE);
            subID.setVisibility(View.VISIBLE);
        }
        else if (trackidjob.equals("2")){
            trackmeId.setVisibility(View.GONE);
            subID.setVisibility(View.GONE);
        }
        else if (trackidjob.equals("3")){
            trackmeId.setVisibility(View.GONE);
            subID.setVisibility(View.GONE);
        }
        else {

        }
        chechradiobutton(bId);
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("View Details");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_home_black);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
                        startActivity(intent);
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
                            amountID.setText(Cuschargename+"@"+Cusrate);

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
    public void acceptedcalllistView(final String bId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ACCEPETEDCALLLISTVIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("acceptcalllistView", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String chrgname = obj.getString("chrg_name");
                            bookingcode = obj.getString("booking_code");
                            prdctcategoryname = obj.getString("prdct_category_name");
                            prdctname = obj.getString("prdct_name");
                            prdctcmpny = obj.getString("prdct_cmpny");
                            prdctmodel = obj.getString("prdct_model");
                            prdcticon = obj.getString("prdct_icon");
                            bookingplaceon = obj.getString("booking_place_on");
                            customername = obj.getString("customer_name");
                            customerphn = obj.getString("customer_phn");
                            customeradrs = obj.getString("customer_adrs");
                            productissue = obj.getString("product_issue");
                            preferdatetime = obj.getString("prefer_date_time");
                            totalchrg = obj.getString("total_chrg");
                            servicephn = obj.getString("service_phn");
                            String serialno = obj.getString("serial_no");
                            String modelpicture = obj.getString("model_picture");
                            String serialpicture = obj.getString("serial_picture");
                            String picturestatus = obj.getString("picture_status");
                            Log.e("ACEPTVIEW"," "+bookingcode+" "+prdctcategoryname+" "+prdctname+" "+prdctcmpny+" "+prdcticon+" "+bookingplaceon+" "
                                    +customername+" "+customerphn+" "
                                    +customeradrs+" "+productissue+" "+preferdatetime+" "+totalchrg+" "+prdctmodel+" "+servicephn+" ");
                                    nameId.setText(prdctname);
                                    jonId.setText("JOB ID :"+" "+bookingcode);
                                    time.setText(bookingplaceon);
                                    asyenbyId.setText("");
                                   // amountID.setText(totalchrg);
                                    userNameId.setText(customername);
                                    phoneId.setText(customerphn);
                                    phoneIdd.setText(servicephn);
                                    placeId.setText(customeradrs);
                                    productNameId.setText(prdctname);
                                    CategoryNameID.setText(prdctcategoryname);
                                    serviceNameId.setText(productissue);
                                    productdateID.setText(preferdatetime);
                                    CompanyId.setText(prdctcmpny);
                                    modelId.setText(prdctmodel);
                                    Glide.with(getApplicationContext())
                                    .load(prdcticon)
                                    .into(imgID);

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
                Log.e("BOOKING"," "+bId);
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
        switch (v.getId()){
            case R.id.subID:
               //stopService(new Intent(AcceptedviewActivity.this, MyService.class));
                GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                if (gpsTracker.getIsGPSTrackingEnabled()){
                    myIntent = new Intent(getApplicationContext(), MyService.class);
                    myIntent.putExtra("bookingcode", bookingcode);
                    myIntent.putExtra("track_flag", "0");
                    myIntent.putExtra("tech_id", techid);
                    startService(myIntent);
                    myIntent = new Intent(getApplicationContext(), MyService.class);
                    stopService(myIntent);
                    //ContextCompat.startForegroundService(LoginActivity.this, new Intent(getApplicationContext(), MyService.class));
                    StartJobOTP(bId);
                }
                else {
                    gpsTracker.showSettingsAlert();
                }

                break;
            case R.id.imgId:
                customerphone = phoneId.getText().toString();
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customerphone, null));
                startActivity(intent1);
                break;
            case R.id.imgIdd:
                customerphonee = phoneIdd.getText().toString();
                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customerphonee, null));
                startActivity(intent2);
                break;
            case R.id.trackmeId:
                String title = "Message Box";
                String msg = "Do you want to start tracking?";
                finalsubmit(title,msg);
                //startService();
                break;
                default:
        }
    }
    public void StartJobOTP(final String bId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.STARTJOBOTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("StartJob", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg = obj.getString("msg");
                             bookid = obj.getString("book_id");
                            String Message = obj.getString("Message");
                            Log.e("STARTVALUEOTP"," "+msg+" "+bookid+" "+Message);
                            if (msg.equals("1")){
                                myIntent = new Intent(getApplicationContext(), MyService.class);
                                myIntent.putExtra("bookingcode", bookingcode);
                                myIntent.putExtra("track_flag", "0");
                                myIntent.putExtra("tech_id", techid);
                                startService(myIntent);
                                Intent intent = new Intent(getApplicationContext(), SmsActivity.class);
                                intent.putExtra("bookid",bookid);
                                intent.putExtra("bId",bId);
                                intent.putExtra("prdctcategoryname",prdctcategoryname);
                                intent.putExtra("prdctname",prdctname);
                                intent.putExtra("prdctcmpny",prdctcmpny);
                                intent.putExtra("prdctmodel",prdctmodel);
                                intent.putExtra("prdcticon",prdcticon);
                                intent.putExtra("bookingplaceon",bookingplaceon);
                                intent.putExtra("customername",customername);
                                intent.putExtra("customerphn",customerphn);
                                intent.putExtra("customeradrs",customeradrs);
                                intent.putExtra("productissue",productissue);
                                intent.putExtra("preferdatetime",preferdatetime);
                                intent.putExtra("totalchrg",totalchrg);
                                intent.putExtra("servicephn",servicephn);
                                intent.putExtra("bookingcode",bookingcode);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                                Toast.makeText(getApplicationContext(),obj.getString("Message"),Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),obj.getString("Message"),Toast.LENGTH_SHORT).show();
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
                params.put("tech_id", techid);
                Log.e("STARTJOBID"," "+bId+" "+" "+techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    private void startService(){
        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.getIsGPSTrackingEnabled()){
            myIntent = new Intent(AcceptedviewActivity.this, MyService.class);
            myIntent.putExtra("bookingcode", bookingcode);
            myIntent.putExtra("track_flag", "1");
            myIntent.putExtra("tech_id", techid);
            startService(myIntent);
            // ContextCompat.startForegroundService(LoginActivity.this, new Intent(getApplicationContext(), MyService.class));
            STARTJOB(bookingcode,techid);

        }
        else {
            gpsTracker.showSettingsAlert();
        }
    }
    public void STARTJOB(final String bookingcode,final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TRACKJOV,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TRACKJOB", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            if (status.equals("1")){
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

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
                params.put("job_id", bookingcode);
                params.put("tech_id", techid);
                Log.e("TRACKJOB"," "+bookingcode+" "+" "+techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
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
    @Override
    protected void onStart() {
        myMainReceiver = new MyMainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.ACTION_UPDATE_CNT);
        intentFilter.addAction(MyService.ACTION_UPDATE_MSG);
        registerReceiver(myMainReceiver, intentFilter);
        super.onStart();
    }

    public AlertDialog finalsubmit(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.finalsubmitdiolog, null);
        ((TextView)dialogView.findViewById(R.id.dialog_title)).setText(title);
        ((TextView)dialogView.findViewById(R.id.dialog_msg)).setText(msg);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        ((Button)dialogView.findViewById(R.id.yId)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startService();

            }
        });
        ((Button)dialogView.findViewById(R.id.nId)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dialog.dismiss();

            }
        });
        builder.setView(dialogView);
        dialog.show();
        return dialog;
    }
}

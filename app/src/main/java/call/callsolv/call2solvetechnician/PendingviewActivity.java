package call.callsolv.call2solvetechnician;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_FLAGTABLE;
import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_ID;
import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_PENDINGCOUNT;


public class PendingviewActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    String bId,techid;
    TextView nameId,jonId,time,asyenbyId,totalId,amountID,userNameId,phoneId,phoneIdd,placeId,productNameId,CategoryNameID,serviceNameId,productdateID;
    TextView CompanyId,modelId;
    ImageView imgID;
    Button imgId,imgIdd;
    String customerphone,customerphonee;
    String bookingcode;
    String bookid;
    TextView acceptId,regectId;
    AlertDialog.Builder builder;
    private AlertDialog alertDialog = null;
    int totpendingcall1;
    private DatabaseHelper db;
    String flagId,pendingcallDB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.pendingacceptedview_activity);
        CompanyId =(TextView) findViewById(R.id.CompanyId);
        modelId =(TextView) findViewById(R.id.modelId);
        nameId = (TextView)findViewById(R.id.nameId);
        jonId = (TextView)findViewById(R.id.jonId);
        time = (TextView)findViewById(R.id.time);
        asyenbyId = (TextView)findViewById(R.id.asyenbyId);
        totalId = (TextView)findViewById(R.id.totalId);
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
        acceptId = (TextView)findViewById(R.id.acceptId);
        regectId = (TextView)findViewById(R.id.regectId);
        acceptId.setOnClickListener(this);
        regectId.setOnClickListener(this);
        initToolBar();
        db = new DatabaseHelper(getApplicationContext());
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        Log.e("TechId",techid);
        Intent intent = getIntent();
        bId = intent.getStringExtra("bId");
        Log.e("bId",bId);
        acceptedcalllistView(bId);
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
    public void acceptedcalllistView(final String bId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ACCEPETEDCALLLISTVIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("pendingcalllistView", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String chrgname = obj.getString("chrg_name");
                            bookingcode = obj.getString("booking_code");
                            String prdctcategoryname = obj.getString("prdct_category_name");
                            String prdctname = obj.getString("prdct_name");
                            String prdctcmpny = obj.getString("prdct_cmpny");
                            String prdctmodel = obj.getString("prdct_model");
                            String serialno = obj.getString("serial_no");
                            String modelpicture = obj.getString("model_picture");
                            String serialpicture = obj.getString("serial_picture");
                            String picturestatus = obj.getString("picture_status");
                            String prdcticon = obj.getString("prdct_icon");
                            String bookingplaceon = obj.getString("booking_place_on");
                            String customername = obj.getString("customer_name");
                            String customerphn = obj.getString("customer_phn");
                            String customeradrs = obj.getString("customer_adrs");
                            String productissue = obj.getString("product_issue");
                            String totalchrg = obj.getString("total_chrg");
                            String servicephnNumber = obj.getString("service_phn");
                            String preferdatetime = obj.getString("prefer_date_time");
                            Log.e("PENDINGVIEW"," "+bookingcode+" "
                                    +prdctcategoryname+" "+prdctname+" "
                                    +prdctcmpny+" "+prdcticon+" "+bookingplaceon+" "
                                    +customername+" "+customerphn+" "
                                    +customeradrs+" "
                                    +productissue+" "+preferdatetime+" "
                                    +totalchrg+" "+prdctmodel+" "
                                    +servicephnNumber+" ");
                            Log.e("PHONENO",customerphn+" "+" "+servicephnNumber);
                            nameId.setText(prdctname);
                            jonId.setText("JOB ID :"+" "+bookingcode);
                            time.setText(bookingplaceon);
                            asyenbyId.setText("");
                           // totalId.setText("Amount :" +chrgname);
                            amountID.setText(chrgname+"@"+totalchrg);
                            userNameId.setText(customername);
                            phoneId.setText(customerphn);
                            phoneIdd.setText(servicephnNumber);
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
            case R.id.acceptId:
                acceptcalllist(bId,techid);

                break;
            case R.id.regectId:
                builder = new AlertDialog.Builder(v.getContext());
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final View loginFormView = getLayoutInflater().inflate(R.layout.diologbox_rejectview, null);
                LinearLayout submit =(LinearLayout) loginFormView.findViewById(R.id.submitID);
                final EditText command = (EditText)loginFormView.findViewById(R.id.cmdinformation);
                toolbar = (Toolbar)loginFormView.findViewById(R.id.toolbar);
                toolbar.setTitle("Reason for reject job");
                setSupportActionBar(toolbar);
                toolbar.setNavigationIcon(R.drawable.ic_home_black);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rejectjob(bId,command.getText().toString(),techid);
                        Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }

                });
                builder.setView(loginFormView);
                alertDialog = builder.create();
                alertDialog.show();
                break;
            default:
        }
    }


    public void rejectjob(final String bId,final String rejetmsg,final String techid){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.REJECTCALLLIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("RejectedCalllist", response);
                            try {
                                JSONObject obj = new JSONObject(response);


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
                    params.put("book_id",bId);
                    params.put("reject_resn",rejetmsg);
                    params.put("tech_id", techid);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
            stringRequest.setShouldCache(false);
            volleySingleton.addToRequestQueue(stringRequest);
        }

    public void acceptcalllist(final String bId,final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ACCEPTCALLLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("acceptCalllist", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String flag = obj.getString("flag");
                            if (flag.equals("1")){
                                alarm(techid);

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
                params.put("b_id",bId);
                params.put("tech_id", techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
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
                            senddatat(totpendingcall1);
                            Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
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

    public void senddatat(final int totpendingcall1){
        Cursor res = db.getAllLoginData();
        if(res.getCount() == 0) {
            Log.e("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            flagId =  res.getString(0);
            pendingcallDB =  res.getString(1);
            Log.e("senddatat"," "+flagId+" "+pendingcallDB);
            SQLiteDatabase database = db.getReadableDatabase();
            database.execSQL( "UPDATE "+TABLE_FLAGTABLE +" SET " + TABLE_PENDINGCOUNT+ " = '"+totpendingcall1+"' WHERE "+TABLE_ID+ " = "+flagId);

        }
    }
}

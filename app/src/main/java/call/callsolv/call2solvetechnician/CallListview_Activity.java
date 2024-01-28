package call.callsolv.call2solvetechnician;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

public class CallListview_Activity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    String bIdd,techid;
    TextView nameId,jonId,time,asyenbyId,amountID,userNameId,phoneId,phoneIdd,placeId,productNameId,CategoryNameID,serviceNameId,productdateID;
    TextView CompanyId,modelId;
    ImageView imgID;
    Button imgId,imgIdd;
    String customerphone,customerphonee;
    String bookingcode;
    String bookid;
    TextView acceptId,regectId;
    AlertDialog.Builder builder;
    private AlertDialog alertDialog = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calllist_view_activity);
        CompanyId =(TextView) findViewById(R.id.CompanyId);
        modelId =(TextView) findViewById(R.id.modelId);
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
        initToolBar();
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        Log.e("TechId",techid);
        Intent intent = getIntent();
        bIdd = intent.getStringExtra("bIdd");
        Log.e("bId",bIdd);
        acceptedcalllistView(bIdd);

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
    public void acceptedcalllistView(final String bIdd){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ACCEPETEDCALLLISTVIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("pendingcalllistView", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            bookingcode = obj.getString("booking_code");
                            String prdctcategoryname = obj.getString("prdct_category_name");
                            String prdctname = obj.getString("prdct_name");
                            String prdctcmpny = obj.getString("prdct_cmpny");
                            String prdctmodel = obj.getString("prdct_model");
                            String prdcticon = obj.getString("prdct_icon");
                            String bookingplaceon = obj.getString("booking_place_on");
                            String customername = obj.getString("customer_name");
                            String customerphn = obj.getString("customer_phn");
                            String customeradrs = obj.getString("customer_adrs");
                            String productissue = obj.getString("product_issue");
                            String preferdatetime = obj.getString("prefer_date_time");
                            String totalchrg = obj.getString("total_chrg");
                            String servicephn = obj.getString("service_phn");
                            Log.e("PENDINGVIEW"," "+bookingcode+" "+prdctcategoryname+" "+prdctname+" "+prdctcmpny+" "+prdcticon+" "+bookingplaceon+" "
                                    +customername+" "+customerphn+" "
                                    +customeradrs+" "+productissue+" "+preferdatetime+" "+totalchrg+" "+prdctmodel+" "+servicephn+" ");
                            nameId.setText(prdctname);
                            jonId.setText("JOB ID :"+" "+bookingcode);
                            time.setText(bookingplaceon);
                            asyenbyId.setText("");
                            amountID.setText(totalchrg);
                            userNameId.setText(customername);
                            phoneId.setText(customerphn);
                            phoneIdd.setText(customerphn);
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
                params.put("b_id", bIdd);
                Log.e("BOOKING"," "+bIdd);
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
                default:
        }
    }




    public void acceptcalllist(final String bId,final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ACCEPTCALLLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("acceptCalllist", response);
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
}

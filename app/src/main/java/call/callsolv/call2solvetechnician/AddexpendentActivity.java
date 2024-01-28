package call.callsolv.call2solvetechnician;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import call.callsolv.call2solvetechnician.SetGetActivity.MyexpendentSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;


public class AddexpendentActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    String techid;
    String jobcode;
    EditText fdNameId,amountId;
    ProfileSetGet profileSetGet;
    TextView cusNameId,cusnumberId,cusaddressId,productId;
    TableLayout tableId;
    AppCompatAutoCompleteTextView techniNameId;
    String bid,custname,srvcphn,srvcadrs,prdctname;
    TextView FdateId;
    ImageView searchView;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    String startdate;
    LinearLayout subId;
    String status = "2";
    String techeditd;
    SimpleDateFormat currentdate;
    String curdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.addexpendent_activity);
        techniNameId = (AppCompatAutoCompleteTextView)findViewById(R.id.techniNameId);
        fdNameId = (EditText)findViewById(R.id.fdNameId);
        //dateId = (EditText)findViewById(R.id.dateId);
        amountId =(EditText)findViewById(R.id.amountId);
        cusNameId =(TextView)findViewById(R.id.cusNameId);
        cusnumberId =(TextView)findViewById(R.id.cusnumberId);
        cusaddressId =(TextView)findViewById(R.id.cusaddressId);
        productId = (TextView)findViewById(R.id.productId);
        tableId =(TableLayout)findViewById(R.id.tableId);
        FdateId = (TextView)findViewById(R.id.FdateId);
        searchView = (ImageView)findViewById(R.id.searchView);
        searchView.setOnClickListener(this);
        tableId.setVisibility(View.GONE);
        subId = (LinearLayout)findViewById(R.id.subId);
        subId.setVisibility(View.GONE);
        subId.setOnClickListener(this);
        initToolBar();
        // jobcode = "191017006";
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Fromdate();
            }
        };
        techniNameId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
                techid = String.valueOf(profileSetGet.getTechid());
                Log.e("Tech"," "+techid);
                techeditd = techniNameId.getText().toString();
                if (techeditd.matches("")) {
                    return;
                }
                else {
                    srchjobdtlbybookid(techniNameId.getText().toString(),techid);
                }


            }
        });
        techniNameId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
                techid = String.valueOf(profileSetGet.getTechid());
                Log.e("Tech"," "+techid);
                techeditd = techniNameId.getText().toString();
                if (techeditd.matches("")) {
                    return;
                }
                else {
                    srchjobdtlbybookid(techniNameId.getText().toString(),techid);
                }


            }
        });

    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Customer Information");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_home_black);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                }
        );
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchView:
                new DatePickerDialog(AddexpendentActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.subId:
                if (status.equals("1")) {
                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());
                    currentdate = new SimpleDateFormat("dd-MM-yyyy");
                    curdate = String.valueOf(currentdate);
                    if (FdateId.getText().toString().equals("Selected date")){
                        Toast.makeText(getApplicationContext(),"Select Date",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        Date strDate = null;
                        Date strDatee = null;
                        try {
                            strDate = sdf.parse(FdateId.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (curdate.equals(FdateId.getText().toString()) || new Date().after(strDate)) {
                            TECHNEWEXPENDENTER(bid,techid);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Invalid Date Range",Toast.LENGTH_SHORT).show();
                        }

                    }

                }
                 if (status.equals("0")){
                    techniNameId.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_close_black_24dp), null);
                    Toast.makeText(AddexpendentActivity.this, "No Record Foundd", Toast.LENGTH_SHORT).show();
                }
//                if (status.equals("2")){
//                    techniNameId.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_close_black_24dp), null);
//                    Toast.makeText(AddexpendentActivity.this, "No Record Foundd", Toast.LENGTH_SHORT).show();
//                    if (TextUtils.isEmpty(jobcode)) {
//                        techniNameId.setError("Please enter your JobId");
//                        techniNameId.requestFocus();
//                        return;
//                    }
//                }

                break;
            default:
        }
    }
    private void Fromdate() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        FdateId.setText(sdf.format(myCalendar.getTime()));
        startdate = FdateId.getText().toString();
    }
    public void  srchjobdtlbybookid(final String jobcode,final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SRCHJOBDTLBYBOOKID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("srchjobdtlbybookid", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            status = obj.getString("status");
                            bid = obj.getString("b_id");
                            custname = obj.getString("cust_name");
                            srvcphn = obj.getString("srvc_phn");
                            srvcadrs = obj.getString("srvc_adrs");
                            prdctname = obj.getString("prdct_name");
                            Log.e("Search", " " + status + " " + bid + " " + custname + " " + srvcphn + " " + srvcadrs+" "+prdctname);
                            if (status.equals("1")){
                                cusNameId.setText(custname);
                                cusnumberId.setText(srvcphn);
                                cusaddressId.setText(srvcadrs);
                                productId.setText(prdctname);
                                tableId.setVisibility(View.VISIBLE);
                                subId.setVisibility(View.VISIBLE);
                                //techniNameId.setBackgroundResource(R.drawable.ic_check_green);
                                techniNameId.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_check_green), null);

                             }
                            else {
                                tableId.setVisibility(View.GONE);
                                subId.setVisibility(View.GONE);
                               // techniNameId.setBackgroundResource(R.drawable.ic_close_black_24dp);
                                techniNameId.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_close_black_24dp), null);
                                Toast.makeText(AddexpendentActivity.this, "Job not found", Toast.LENGTH_SHORT).show();
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
                params.put("job_code",jobcode);
                params.put("tech_id", techid);
                Log.e("srchjobdtlbybookidview", " "+jobcode+" "+" "+techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    public void TECHNEWEXPENDENTER(final String bid,final String techid){
        final String expndtrfr = fdNameId.getText().toString();
        final String dateexpen = FdateId.getText().toString();
        final String amount = amountId.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(expndtrfr)) {
            fdNameId.setError("Please enter your Expndtur For");
            fdNameId.requestFocus();
            return;
        }
//        if (TextUtils.isEmpty(dateexpen)) {
//            dateId.setError("Please enter your Date");
//            dateId.requestFocus();
//            return;
//        }
        if (TextUtils.isEmpty(amount)) {
            amountId.setError("Please enter your amount");
            amountId.requestFocus();
            return;
        }
        if (dateexpen.equals("Selected date")) {
            Toast.makeText(this, "Selected date", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TECHNEWEXPENDENTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("searchview", response);
                            try {
                                JSONObject obj = new JSONObject(response);
                                String msg = obj.getString("msg");
                                Toast.makeText(AddexpendentActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                    params.put("b_id", bid);
                    params.put("expndtr_fr", expndtrfr);
                    params.put("exp_dt", dateexpen);
                    params.put("amnt", amount);
                    Log.e("searchsubmit", " " + techid + bid + expndtrfr + dateexpen+amount);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
            stringRequest.setShouldCache(false);
            volleySingleton.addToRequestQueue(stringRequest);
        }
    }


}

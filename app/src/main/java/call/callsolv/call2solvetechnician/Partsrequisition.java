package call.callsolv.call2solvetechnician;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

public class Partsrequisition extends AppCompatActivity implements View.OnClickListener {
    Button subID;
    EditText partsnameId,modelId,quantityId,brandId,detailsId;
    String bookid,bId,techid,otp,prdctname,prdctcmpny,prdctmodel,productissue;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parts_requation);
        initToolBar();
        Intent intent = getIntent();
        bookid = intent.getStringExtra("bookid");
        bId  = intent.getStringExtra("bId");
//        prdctname = intent.getStringExtra("prdctname");
//        prdctcmpny  = intent.getStringExtra("prdctcmpny");
//        prdctmodel = intent.getStringExtra("prdctmodel");
//        productissue  = intent.getStringExtra("productissue");
        Log.e("PartRqu"," "+bookid+" "+bId+" ");
        partsnameId = (EditText)findViewById(R.id.partsnameId);
        modelId = (EditText)findViewById(R.id.modelId);
        quantityId = (EditText)findViewById(R.id.quantityId);
        brandId = (EditText)findViewById(R.id.brandId);
        detailsId =(EditText)findViewById(R.id.detailsId);
        subID = (Button)findViewById(R.id.subID);
        subID.setOnClickListener(this);
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Parts Requisition");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_home_black);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Intent intent = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
                      //   startActivity(intent);
                    }
                }
        );
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.subID:
                PARTSREQUATION(bId,techid);
                break;
            default:
        }
    }
    public void PARTSREQUATION(final String bId,final String techid){
        final String partsname = partsnameId.getText().toString();
        final String modelno = modelId.getText().toString();
        final String qnty = quantityId.getText().toString();
        final String brnd = brandId.getText().toString();
        final String detail = detailsId.getText().toString();
        if (TextUtils.isEmpty(partsname)) {
            partsnameId.setError("Please enter your Parts Name");
            partsnameId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(modelno)) {
            modelId.setError("Please enter your Model No");
            modelId.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(qnty)) {
            quantityId.setError("Please enter your Quantity");
            quantityId.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(brnd)) {
            brandId.setError("Please enter your Brand");
            brandId.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(detail)) {
            detailsId.setError("Please enter your Details");
            detailsId.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PARTSREQUATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("PARTSPDETAILS", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("Message");
                            if (status.equals("1")){
                                Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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
                params.put("parts_name",partsname);
                params.put("model_no", modelno);
                params.put("qnty", qnty);
                params.put("brnd", brnd);
                params.put("detail", detail);
                Log.e("PARTSVIEW"," "+bId+" "+techid+" "+ " "+partsname+" "+modelno+" "+" "+qnty+" "+brnd+" "+detail);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
//    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("Inside onResume");
    }

    @Override
    public void onStart(){
        super.onStart();
        System.out.println("Inside onStart");
    }

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
    }

}

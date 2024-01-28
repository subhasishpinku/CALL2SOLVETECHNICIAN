package call.callsolv.call2solvetechnician;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

public class JobpendingActivity extends AppCompatActivity
        implements Spinner.OnItemSelectedListener,View.OnClickListener{
    TextInputLayout otherId;
    LinearLayout lveditId;
    EditText othernameId;
    Spinner spID;
    String resonid,bookid,bId,techid,timee,datee,otp,prdctname,prdctcmpny,prdctmodel,serialno,productissue;
    ArrayList<String> Reson;
    JSONArray array;
    String resonId ="0";
    String resonName;
    Button subID;
    String othervalname ="";
    RadioGroup pendingGpId;
    RadioButton pendingpartId,pendingothetId;
    LinearLayout lvpartsId,lvv,llvpendingotherId;
    EditText PnameId,pmodelNoId,pbrandId,quantrityId,CompanyId,modelId,serialnoID;
    String proName ="NNA";
    String productmodelnoBrand = "NNA";
    String productbrandBrand = "NNA";
    String quantity = "NNA";
    String pendingfor;
    Toolbar toolbar;
    ImageView modelImageId,modelImageIdd,scanerId,scanerIdd,imageView,imageView1;
    private IntentIntegrator qrScan;
    private IntentIntegrator qrScann;
    SharedPreferences sp;
    String SCANNER= "1";
    String SCANNERR = "2";
    String SCANNER1,SCANNERR2;
    private static final String TAG = "IMAGEUPLOAD";
    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_IMAGE_REQUEST1 = 2;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Bitmap bitmap1;
    private Uri filePath;
    String filePathImg;
    String file_extn = "pinku";
    String file_extn1 = "pinku1";
    String url;
    public static final String UPLOAD_KEY = "tmp_name";
    private File actualImage;
    private File actualImage1;
    private static final int Image_Capture_Code = 2;
    private static final int Image_Capture_Code1 = 3;
    String UPLOADIMAGE,UPLOADIMAGE1;
    String UPLOADIMAGEVALUE ="1";
    String UPLOADIMAGEVALUE1 ="1";
    public static final String MODELPIC = "model_picture";
    public static final String SERIALPIC = "serial_picture";
    boolean isImageFitToScreen;
    boolean isImageFitToScreenn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
    //            WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.jobpending_activity);
        otherId = (TextInputLayout)findViewById(R.id.otherId);
        othernameId = (EditText)findViewById(R.id.othernameId);
        lveditId =(LinearLayout)findViewById(R.id.lvsp);
        subID = (Button)findViewById(R.id.subID);
        subID.setOnClickListener(this);
        spID =(Spinner)findViewById(R.id.spID);
        pendingGpId = (RadioGroup)findViewById(R.id.pendingGpId);
        pendingpartId = (RadioButton)findViewById(R.id.pendingpartId);
        pendingothetId = (RadioButton)findViewById(R.id.pendingothetId);
        lvpartsId = (LinearLayout)findViewById(R.id.lvpartsId);
        lvv = (LinearLayout)findViewById(R.id.lvv);
        llvpendingotherId = (LinearLayout)findViewById(R.id.llvpendingotherId);
        PnameId = (EditText)findViewById(R.id.PnameId);
        pmodelNoId = (EditText)findViewById(R.id.pmodelNoId);
        pbrandId = (EditText)findViewById(R.id.pbrandId);
        quantrityId = (EditText)findViewById(R.id.quantrityId);
        CompanyId = (EditText)findViewById(R.id.CompanyId);
        modelId = (EditText)findViewById(R.id.modelId);
        serialnoID = (EditText)findViewById(R.id.serialnoID);
        modelImageId = (ImageView)findViewById(R.id.modelImageId);
        modelImageId.setOnClickListener(this);
        modelImageIdd = (ImageView)findViewById(R.id.modelImageIdd);
        modelImageIdd.setOnClickListener(this);
        scanerId = (ImageView)findViewById(R.id.scanerId);
        scanerId.setOnClickListener(this);
        scanerIdd = (ImageView)findViewById(R.id.scanerIdd);
        scanerIdd.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        spID.setOnItemSelectedListener(this);
        initToolBar();
        Intent intent = getIntent();
        bookid = intent.getStringExtra("bookid");
        bId  = intent.getStringExtra("bId");
//      prdctname = intent.getStringExtra("prdctname");
//      prdctcmpny  = intent.getStringExtra("prdctcmpny");
//      prdctmodel = intent.getStringExtra("prdctmodel");
//      productissue  = intent.getStringExtra("productissue");
        SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date =new Date();
        datee= parseFormat.format(date);
        Log.e("PartRqu"," "+bookid+" "+bId+" "+" "+datee);
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        Calendar c = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        timee = time.format(c.getTime());
        othernameId.setVisibility(View.GONE);
        Reson =new ArrayList<>();
//        subID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pendingpartId.isChecked() || pendingothetId.isChecked()){
//                    submit_resondata();
//                }
//                else {
//                     Toast.makeText(getApplicationContext(),"Select pending for part or other",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//        CompanyId.setImeOptions(EditorInfo.IME_ACTION_NEXT);
//        CompanyId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if(i== EditorInfo.IME_ACTION_NEXT){
//                }
//                return false;
//            }
//        });
//        CompanyId.setImeOptions(EditorInfo.IME_ACTION_NEXT);
//        modelId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if(i== EditorInfo.IME_ACTION_NEXT){
//                }
//                return false;
//            }
//        });
//        serialnoID.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if(i== EditorInfo.IME_ACTION_NEXT){
//                }
//                return false;
//            }
//        });
        lvpartsId.setVisibility(View.GONE);
       // lvv.setVisibility(View.GONE);
        llvpendingotherId.setVisibility(View.GONE);
        pendingGpId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId== R.id.pendingpartId){
                    lvpartsId.setVisibility(View.VISIBLE);
                 //   lvv.setVisibility(View.VISIBLE);
                    llvpendingotherId.setVisibility(View.GONE);
                    pendingfor = "p";
                }
                else if(checkedId== R.id.pendingothetId){
                     lvpartsId.setVisibility(View.GONE);
                 //    lvv.setVisibility(View.GONE);
                     llvpendingotherId.setVisibility(View.VISIBLE);
                     pendingfor = "o";
                     PnameId.setText("");
                     pmodelNoId.setText("");
                     pbrandId.setText("");
                     Reson.clear();
                     getjobdeatails();
                     Reson.clear();
//                     CompanyId.setText("");
//                     modelId.setText("");
//                     serialnoID.setText("");
//                     quantrityId.setText("");
//                     imageView.setImageBitmap(null);
//                     imageView1.setImageBitmap(null);
                }
            }
        });

        qrScan = new IntentIntegrator(this);
        sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.SCANNER, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("SCANNER","0");
        edit.commit();
        sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.SCANNERR, MODE_PRIVATE);
        SharedPreferences.Editor editt = sp.edit();
        editt.putString("SCANNERR","0");
        editt.commit();
        sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.SCANNER, MODE_PRIVATE);
        SCANNER1 = sp.getString("SCANNER","");
        sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.SCANNERR, MODE_PRIVATE);
        SCANNERR2 = sp.getString("SCANNERR","");
        Log.e("SCANNERRRRRR"," "+" "+SCANNER1+" "+SCANNERR2);
        sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE, MODE_PRIVATE);
        SharedPreferences.Editor IMG = sp.edit();
        IMG.putString("IMAGE","0");
        IMG.commit();
        //////////////
        sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE1, MODE_PRIVATE);
        SharedPreferences.Editor IMG1 = sp.edit();
        IMG1.putString("IMAGE1","0");
        IMG1.commit();
        requestStoragePermission();
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Job Pending");
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
    public void getjobdeatails(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.JOBPENDING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   progressBar.setVisibility(View.GONE);
                        Log.e("SP11",response);
                        array = null;
                        try {
                            array = new JSONArray(response);
                            for (int i = 0;i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String reasondetails = jsonObject.getString("reason_details");
                                Log.e("jobjob"," "+id+" "+reasondetails);
                            }
                            getdis(array);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    private void getdis(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                Reson.add(json.getString(URLs.TAG_RES));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spID.setAdapter(new ArrayAdapter<String>(JobpendingActivity.this, android.R.layout.simple_spinner_dropdown_item, Reson));
    }
    private String getID(int position){
        try {
            JSONObject json = array.getJSONObject(position);
            resonid = json.getString(URLs.TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resonid;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        resonId = getID(position);
        resonName = spID.getSelectedItem().toString();
       // Toast.makeText(getApplicationContext(),resonId+" "+resonName,Toast.LENGTH_SHORT).show();
        if (resonName.equals("Other")){
            othernameId.setVisibility(View.VISIBLE);
         //   Toast.makeText(getApplicationContext(),"0",Toast.LENGTH_SHORT).show();
        }
        else {
         //   Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
            othernameId.setVisibility(View.GONE);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.subID:
             if (pendingpartId.isChecked() || pendingothetId.isChecked()){
                 submit_resondata(actualImage,actualImage1);
             }
             else {
                 Toast.makeText(getApplicationContext(),"Select pending for part or other",Toast.LENGTH_SHORT).show();
             }
             break;
         case R.id.scanerId:
             sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.SCANNER, MODE_PRIVATE);
             SharedPreferences.Editor edit = sp.edit();
             edit.putString("SCANNER","1");
             edit.commit();
             qrScan.initiateScan();
             break;
         case R.id.scanerIdd:
             sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.SCANNERR, MODE_PRIVATE);
             SharedPreferences.Editor editt = sp.edit();
             editt.putString("SCANNERR","2");
             editt.commit();
             qrScan.initiateScan();
             break;
         case R.id.modelImageId:
             sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE, MODE_PRIVATE);
             SharedPreferences.Editor IMG = sp.edit();
             IMG.putString("IMAGE","1");
             IMG.commit();
             if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                 ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
             } else {
                 takePhotoFromCamera();
             }
             break;
         case R.id.modelImageIdd:
             sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE1, MODE_PRIVATE);
             SharedPreferences.Editor IMG1 = sp.edit();
             IMG1.putString("IMAGE1","1");
             IMG1.commit();
             if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                 ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
             } else {
                 takePhotoFromCamera1();
             }
             break;

         default:
     }
    }
    public void submit_resondata(final File actualImage,final File actualImage1){
        if (pendingothetId.isChecked()) {
            prdctcmpny = CompanyId.getText().toString();
            prdctmodel = modelId.getText().toString();
            serialno = serialnoID.getText().toString();
            if (TextUtils.isEmpty(prdctcmpny)) {
                CompanyId.setError("Please enter your company Name");
                CompanyId.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(prdctmodel)) {
                modelId.setError("Please enter your model");
                modelId.requestFocus();
                return;
            }
            ; if (TextUtils.isEmpty(serialno)) {
                serialnoID.setError("Please enter your Serial");
                serialnoID.requestFocus();
                return;
            }
            sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE, MODE_PRIVATE);
            UPLOADIMAGE = sp.getString("IMAGE","");
            sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE1, MODE_PRIVATE);
            UPLOADIMAGE1 = sp.getString("IMAGE1","");
            Log.e("PsaveDat",UPLOADIMAGE+" "+UPLOADIMAGE1);
            if (UPLOADIMAGE.equals(UPLOADIMAGEVALUE)){
                if (UPLOADIMAGE1.equals(UPLOADIMAGEVALUE1)){
                    if (actualImage != null){
                        if (actualImage1 != null) {


                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Missing Picture missing for serial No",Toast.LENGTH_SHORT).show();
                         return;
                        }
                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Missing Picture missing for Model No",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Missing Picture missing for serial No",Toast.LENGTH_SHORT).show();
                return;
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"Missing Picture missing for Model No",Toast.LENGTH_SHORT).show();
               return;
            }
            ////////////////////////////////////////////
            if (resonName.equals("Other")) {
                othervalname = othernameId.getText().toString();
                if (TextUtils.isEmpty(othervalname)) {
                    othernameId.setError("Please enter Other Name");
                    othernameId.requestFocus();
                    return;
                }
            }


        }
        if (pendingpartId.isChecked()) {
            prdctcmpny = CompanyId.getText().toString();
            prdctmodel = modelId.getText().toString();
            serialno = serialnoID.getText().toString();
            if (TextUtils.isEmpty(prdctcmpny)) {
                CompanyId.setError("Please enter your company Name");
                CompanyId.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(prdctmodel)) {
                modelId.setError("Please enter your model");
                modelId.requestFocus();
                return;
            }
            ; if (TextUtils.isEmpty(serialno)) {
                serialnoID.setError("Please enter your Serial");
                serialnoID.requestFocus();
                return;
            }
            sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE, MODE_PRIVATE);
            UPLOADIMAGE = sp.getString("IMAGE","");
            sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE1, MODE_PRIVATE);
            UPLOADIMAGE1 = sp.getString("IMAGE1","");
            Log.e("PsaveDat",UPLOADIMAGE+" "+UPLOADIMAGE1);
            if (UPLOADIMAGE.equals(UPLOADIMAGEVALUE)){
                if (UPLOADIMAGE1.equals(UPLOADIMAGEVALUE1)){
                    if (actualImage != null){
                        if (actualImage1 != null) {

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Missing Picture missing for serial No",Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Missing Picture missing for Model No",Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Missing Picture missing for serial No",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"Missing Picture missing for Model No",Toast.LENGTH_SHORT).show();
            }

            /////////////////////////////////////////////
            proName = PnameId.getText().toString();
            productmodelnoBrand = pmodelNoId.getText().toString();
            productbrandBrand = pbrandId.getText().toString();
            quantity = quantrityId.getText().toString();
            if (TextUtils.isEmpty(proName)) {
                PnameId.setError("Please enter Product Name");
                PnameId.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(productmodelnoBrand)) {
                pmodelNoId.setError("Please enter Product Model No");
                pmodelNoId.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(productbrandBrand)) {
                pbrandId.setError("Please enter Product Brand");
                pbrandId.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(quantity)) {
                quantrityId.setError("Please enter Quantity");
                quantrityId.requestFocus();
                return;
            }

        }

//            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.JOBSENDDITAILS,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.e("rex",response);
//
//                            //progressBar.setVisibility(View.GONE);
//                            try {
//                                JSONObject obj = new JSONObject(response);
//                                String status = obj.getString("status");
//                                String msg = obj.getString("msg");
//                                String statusupdt = obj.getString("status_updt");
//                                String msgupdt = obj.getString("msg_updt");
//                                if (status.equals("1")){
//                                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
//                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
//                                    Toast.makeText(getApplicationContext(),msgupdt,Toast.LENGTH_SHORT).show();
//
//                                }
//                                else {
//                                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("job_id",bookid);
//                    params.put("booking_id",bId);
//                    params.put("tech_id",techid);
//                    params.put("reason_id",resonId);
//                    params.put("reason",othervalname);
//                    params.put("date",datee);
//                    params.put("time",timee);
//                    params.put("pending_for",pendingfor);
//                    params.put("part_name",proName);
//                    params.put("part_model_no",productmodelnoBrand);
//                    params.put("part_brand",productbrandBrand);
//                    params.put("quantity",quantity);
//                    Log.e("Datevalue"," "+bookid+" "
//                            +bId+" "+techid+" "+resonId+" "
//                            +othervalname+" "+datee+" "+timee+" "+proName+" "
//                            +productmodelnoBrand+" "+productbrandBrand+" "+pendingfor+" "+quantity);
//                    return params;
//                }
//            };
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
//            stringRequest.setShouldCache(false);
//            volleySingleton.addToRequestQueue(stringRequest);
            AndroidNetworking.upload(URLs.JOBSENDDITAILS)
                    // .addMultipartFile(UPLOAD_KEY,imagePath)
                    //.addMultipartParameter("book_id",bId)
                    .addMultipartParameter("item_cmpny",prdctcmpny)
                    .addMultipartParameter("model_no",prdctmodel)
                    .addMultipartParameter("serial_no",serialno)
                    .addMultipartFile(MODELPIC,actualImage)
                    .addMultipartFile(SERIALPIC,actualImage1)
                    .addMultipartParameter("job_id",bookid)
                    .addMultipartParameter("booking_id",bId)
                    .addMultipartParameter("tech_id",techid)
                    .addMultipartParameter("reason_id",resonId)
                    .addMultipartParameter("reason",othervalname)
                    .addMultipartParameter("date",datee)
                    .addMultipartParameter("time",timee)
                    .addMultipartParameter("pending_for",pendingfor)
                    .addMultipartParameter("part_name",proName)
                    .addMultipartParameter("part_model_no",productmodelnoBrand)
                    .addMultipartParameter("part_brand",productbrandBrand)
                    .addMultipartParameter("quantity",quantity)
                    .setTag("uploadTest")
                    .setPriority(Priority.HIGH)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            // do anything with progress
                            Log.e(TAG, "onProgress: called..." );
                           Log.e("JOBDATA"," "
                                   +"item_cmpny"+" "+prdctcmpny+" "+"model_no"+" "+prdctmodel+" "
                            +" "+"serial_no"+" "+serialno+" "+" "+"actualImage"+" "+actualImage+" "
                           +" "+"actualImage1"+" "+" "+actualImage1+" "+"job_id"+" "+bookid+" "+
                                   " "+"booking_id"+" "+" "+bId+" "+" "+"tech_id"+" "+techid+" "+
                                   " "+"reason_id"+" "+resonId+" "+" "+"reason"+" "+othervalname
                           +" "+"date"+" "+datee+" "+" "+"time"+" "+timee+" "+" "+"pending_for"+pendingfor
                           +" "+" "+proName+" "+" "+"part_model_no"+" "+productmodelnoBrand+" "+"part_brand"
                           +" "+productbrandBrand+" "+"quantity"+" "+quantity);

                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            Log.e(TAG, "onResponse: "+response );
                            String res = String.valueOf(response);
                            Log.e("rex",res);
                            try {
                                JSONObject obj = new JSONObject(res);
                                String status = obj.getString("status");
                                String msg = obj.getString("msg");
                                String statusupdt = obj.getString("status_updt");
                                String msgupdt = obj.getString("msg_updt");
                                if (status.equals("1")){
                                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                    if (statusupdt.equals("1")){
                                        Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                                        Toast.makeText(getApplicationContext(),msgupdt,Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),msgupdt,Toast.LENGTH_SHORT).show();

                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.e(TAG, "onError: "+error.getErrorDetail() );
                        }
                    });




    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    String contents = result.getContents();
                    String format = result.getFormatName();
                    Log.e("BARCODE", " " + obj + " " + contents + " " + format);
                    //CompanyId.setText(obj.getString("name"));
//                        modelId.setText(obj.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.SCANNER, MODE_PRIVATE);
                    SCANNER1 = sp.getString("SCANNER","");
                    if(SCANNER.equals(SCANNER1)){
                        sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.SCANNER, MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("SCANNER","0");
                        edit.commit();
                        modelId.setText(result.getContents());
                        Toast.makeText(this, "1"+result.getContents(), Toast.LENGTH_LONG).show();
                    }
                    else {
                    }
                    sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.SCANNERR, MODE_PRIVATE);
                    SCANNERR2 = sp.getString("SCANNERR","");
                    Log.e("SCANNERRRRRR"," "+" "+SCANNER1+" "+SCANNERR2);
                    if (SCANNERR.equals(SCANNERR2)){
                        sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.SCANNERR, MODE_PRIVATE);
                        SharedPreferences.Editor editt = sp.edit();
                        editt.putString("SCANNERR","0");
                        editt.commit();
                        serialnoID.setText(result.getContents());
                        Toast.makeText(this, "2"+result.getContents(), Toast.LENGTH_LONG).show();
                    }
                    else {
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                actualImage = FileUtil.from(getApplicationContext(),data.getData());
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(actualImage));
                String fileImage = String.valueOf(actualImage);
                file_extn = fileImage.substring(fileImage.lastIndexOf("/") + 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                File finalFile = new File(getRealPathFromURII(tempUri));
                url = String.valueOf(finalFile);
                actualImage = finalFile;
                Log.e("pathImage", String.valueOf(actualImage));
                String fileImage = String.valueOf(actualImage);
                file_extn = fileImage.substring(fileImage.lastIndexOf("/") + 1);
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
        if (requestCode == Image_Capture_Code1) {
            if (resultCode == RESULT_OK) {
                bitmap1 = (Bitmap) data.getExtras().get("data");
                imageView1.setImageBitmap(bitmap1);
                Uri tempUri = getImageUri(getApplicationContext(), bitmap1);
                File finalFile = new File(getRealPathFromURII(tempUri));
                url = String.valueOf(finalFile);
                actualImage1 = finalFile;
                Log.e("pathImage1", String.valueOf(actualImage1));
                String fileImage = String.valueOf(actualImage1);
                file_extn1 = fileImage.substring(fileImage.lastIndexOf("/") + 1);
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURII(Uri uri) {
        String path = "";
        if (getApplicationContext().getContentResolver() != null) {
            Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public String getPath(Uri uri) {
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getApplicationContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            return path;
        }
        return null;
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showFileChooser() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);

    }

    @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
    private void takePhotoFromCamera() {
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, Image_Capture_Code);
        } else {

        }
    }
    @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
    private void takePhotoFromCamera1() {
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, Image_Capture_Code1);
        } else {

        }
    }
    @Override
    public void onBackPressed() {
        //Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
        //startActivity(intent);
       // overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
    }

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

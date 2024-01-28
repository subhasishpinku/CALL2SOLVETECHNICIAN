package call.callsolv.call2solvetechnician;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
public class Updatebookingproductdetails extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    ImageView scanerId,scanerIdd,imageView2,imageView3,imageView4,imageView5;
    private IntentIntegrator qrScan;
    private IntentIntegrator qrScann;
    Button subID,closejobID;
    String bId,techid,serialno;
    TextView nameId,jonId,time,asyenbyId,amountID,userNameId,phoneId,phoneIdd,placeId,productNameId,CategoryNameID,serviceNameId,productdateID;
    EditText CompanyId,modelId,serialnoID;
    ImageView imgID;
    Button imgId,imgIdd;
    String customerphone,customerphonee;
    String bookingcode;
    String bookid;
    String prdctname,prdctcmpny,prdctmodel,productissue,picturestatus;
    SharedPreferences sp;
    String SCANNER= "1";
    String SCANNERR = "2";
    String SCANNER1,SCANNERR2;
    CheckBox reassmentId;
    private static final String TAG = "IMAGEUPLOAD";
    ImageView modelImageId,modelImageIdd,imageView,imageView1;
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
    LinearLayout plv,olv;
    TextView dateId,timeId,pronameId,modelnoId,brandnameId,quntyId,
            odateId,otimeId,opronameId,pendingRsgId,comId,modId,seriId,comId1,modId1,seriId1;
    TableLayout tb1,tb2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
       //         WindowManager.LayoutParams.FLAG_SECURE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatebookingproductdetails);
        scanerId = (ImageView) findViewById(R.id.scanerId);
        scanerId.setOnClickListener(this);
        scanerIdd = (ImageView)findViewById(R.id.scanerIdd);
        scanerIdd.setOnClickListener(this);
        CompanyId =(EditText) findViewById(R.id.CompanyId);
        modelId =(EditText) findViewById(R.id.modelId);
        serialnoID = (EditText)findViewById(R.id.serialnoID);
        subID =(Button)findViewById(R.id.subID);
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
        closejobID = (Button)findViewById(R.id.closejobID);
        reassmentId = (CheckBox)findViewById(R.id.reassmentId);
        modelImageId = (ImageView)findViewById(R.id.modelImageId);
        modelImageId.setOnClickListener(this);
        modelImageIdd = (ImageView)findViewById(R.id.modelImageIdd);
        modelImageIdd.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        imageView4 = (ImageView)findViewById(R.id.imageView4);
        imageView5 = (ImageView)findViewById(R.id.imageView5);
        plv =(LinearLayout)findViewById(R.id.plv);
        olv =(LinearLayout)findViewById(R.id.olv);
        tb1 =(TableLayout) findViewById(R.id.tb1);
        tb2 =(TableLayout)findViewById(R.id.tb2);
        plv.setVisibility(View.GONE);
        olv.setVisibility(View.GONE);
        tb1.setVisibility(View.GONE);
        tb2.setVisibility(View.GONE);
//        plv.setVisibility(View.GONE);
//        olv.setVisibility(View.GONE);
        dateId = (TextView)findViewById(R.id.dateId);
        timeId = (TextView)findViewById(R.id.timeId);
        pronameId = (TextView)findViewById(R.id.pronameId);
        modelnoId = (TextView)findViewById(R.id.modelnoId);
        brandnameId = (TextView)findViewById(R.id.brandnameId);
        quntyId = (TextView)findViewById(R.id.quntyId);
        odateId = (TextView)findViewById(R.id.odateId);
        otimeId = (TextView)findViewById(R.id.otimeId);
        opronameId = (TextView)findViewById(R.id.opronameId);
        pendingRsgId  = (TextView)findViewById(R.id.pendingRsgId);
        comId  = (TextView)findViewById(R.id.comId);
        modId  = (TextView)findViewById(R.id.modId);
        seriId  = (TextView)findViewById(R.id.seriId);
        comId1  = (TextView)findViewById(R.id.comId1);
        modId1  = (TextView)findViewById(R.id.modId1);
        seriId1  = (TextView)findViewById(R.id.seriId1);
        pendingRsgId.setVisibility(View.GONE);
        imgId.setOnClickListener(this);
        imgIdd =(Button)findViewById(R.id.imgIdd);
        imgIdd.setOnClickListener(this);
        subID.setOnClickListener(this);
        closejobID.setOnClickListener(this);
        Intent intent = getIntent();
        bookid = intent.getStringExtra("bookid");
        bId  = intent.getStringExtra("bId");
        Log.e("BOOkUpdate"," "+bookid+" "+bId);
        acceptedcalllistView(bId);
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        MethodAsyenbyId(bId,techid);
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
        initToolBar();
        reassmentId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    prdctcmpny = CompanyId.getText().toString();
                    prdctmodel = modelId.getText().toString();
                    serialno = serialnoID.getText().toString();
                    if (TextUtils.isEmpty(prdctcmpny)) {
                        CompanyId.setError("Please enter your company Name");
                        CompanyId.requestFocus();
                        reassmentId.setChecked(false);
                        return;
                    }
                    if (TextUtils.isEmpty(prdctmodel)) {
                        modelId.setError("Please enter your model");
                        modelId.requestFocus();
                        reassmentId.setChecked(false);
                        return;
                    }
                     if (TextUtils.isEmpty(serialno)) {
                    serialnoID.setError("Please enter your Serial");
                    serialnoID.requestFocus();
                    reassmentId.setChecked(false);
                    return;
                }
                    sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE, MODE_PRIVATE);
                    UPLOADIMAGE = sp.getString("IMAGE","");
                    sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE1, MODE_PRIVATE);
                    UPLOADIMAGE1 = sp.getString("IMAGE1","");
                    Log.e("PsaveDat",UPLOADIMAGE+" "+UPLOADIMAGE1+" "+picturestatus);
                    if (picturestatus.equals("0")) {
                        if (UPLOADIMAGE.equals(UPLOADIMAGEVALUE)) {
                            if (UPLOADIMAGE1.equals(UPLOADIMAGEVALUE1)) {
                                if (actualImage != null) {
                                    if (actualImage1 != null) {
                                        requestforreassement();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Missing Picture missing for serial No", Toast.LENGTH_SHORT).show();
                                        reassmentId.setChecked(false);
                                        return;
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Missing Picture missing for Model No", Toast.LENGTH_SHORT).show();
                                    reassmentId.setChecked(false);
                                    return;
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Missing Picture missing for serial No", Toast.LENGTH_SHORT).show();
                                reassmentId.setChecked(false);
                                return;
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Missing Picture missing for Model No", Toast.LENGTH_SHORT).show();
                            reassmentId.setChecked(false);
                            return;
                        }
                    }
                    if (picturestatus.equals("1")) {
                        requestforreassementtwo();
                    }
                }
                else {
                    compoundButton.setChecked(false);

                }
            }
        });
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(isImageFitToScreen) {
//                    isImageFitToScreen=false;
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    imageView.setAdjustViewBounds(true);
//                }else{
//                    isImageFitToScreen=true;
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(isImageFitToScreenn) {
//                    isImageFitToScreenn=false;
//                    imageView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    imageView1.setAdjustViewBounds(true);
//                }else{
//                    isImageFitToScreenn=true;
//                    imageView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                    imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
            }
        });
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Technically Closed");
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
            case R.id.scanerId:
                sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.SCANNER, MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("SCANNER","1");
                edit.commit();
                qrScan.initiateScan();
                break;
            case R.id.subID:
//              Intent intent = new Intent(getApplicationContext(), Partsrequisition.class);
                Intent intent = new Intent(getApplicationContext(), JobpendingActivity.class);
                intent.putExtra("bookid",bookid);
                intent.putExtra("bId",bId);
//                intent.putExtra("prdctname",prdctname);
//                intent.putExtra("prdctcmpny",CompanyId.getText().toString());
//                intent.putExtra("prdctmodel",modelId.getText().toString());
//                intent.putExtra("productissue",productissue);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                break;
            case R.id.closejobID:
                sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE, MODE_PRIVATE);
                UPLOADIMAGE = sp.getString("IMAGE","");
                sp = getApplicationContext().getSharedPreferences(SharePreferanceSaveData.IMAGE1, MODE_PRIVATE);
                UPLOADIMAGE1 = sp.getString("IMAGE1","");
                Log.e("PsaveDat",UPLOADIMAGE+" "+UPLOADIMAGE1+" "+picturestatus);
                if (picturestatus.equals("0")) {
                    if (UPLOADIMAGE.equals(UPLOADIMAGEVALUE)) {
                        if (UPLOADIMAGE1.equals(UPLOADIMAGEVALUE1)) {
                            if (actualImage != null) {
                                if (actualImage1 != null) {
                                    Updatebookingproductdetails(bId, actualImage, actualImage1);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Missing Picture missing for serial No", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Missing Picture missing for Model No", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Missing Picture missing for serial No", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Missing Picture missing for Model No", Toast.LENGTH_SHORT).show();
                    }
                }
                if (picturestatus.equals("1")) {
                    Updatebookingproductdetails_two(bId);
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
    protected static void qrDroidRequired(final Updatebookingproductdetails activity) {
        // TODO Auto-generated method stub
        AlertDialog.Builder AlertBox = new AlertDialog.Builder(activity);
        AlertBox.setMessage("QRDroid Missing");
        AlertBox.setPositiveButton("Direct Download", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

                activity.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://droid.la/apk/qr/")));
            }
        });
    }
    public void  MethodAsyenbyId(final String bId,final String techid){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ASYANBY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("MethodAsyenbyId", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String chrgnm = obj.getString("chrg_nm");
                            String actualrate = obj.getString("actual_rate");
                            String assignby = obj.getString("assign_by");
                            String pendingflag = obj.getString("pending_flag");
                            String date = obj.getString("date");
                            String time = obj.getString("time");
                            String partname = obj.getString("part_name");
                            String partbrand = obj.getString("part_brand");
                            String partmodelno = obj.getString("part_model_no");
                            String reason = obj.getString("reason");
                            String quantity = obj.getString("quantity");
                            String companyname = obj.getString("company_name");
                            String modelno = obj.getString("model_no");
                            String serialno = obj.getString("serial_no");
                            String modelpicture = obj.getString("model_picture");
                            String serialpicture = obj.getString("serial_picture");
                            amountID.setText(chrgnm+"@"+actualrate);
                            asyenbyId.setText(assignby);
                            if (pendingflag.equals("p")){
                                pendingRsgId.setVisibility(View.VISIBLE);
                                plv.setVisibility(View.VISIBLE);
                                olv.setVisibility(View.GONE);
                                tb1.setVisibility(View.VISIBLE);
                                tb2.setVisibility(View.GONE);
                                dateId.setText(date);
                                timeId.setText(time);
                                pronameId.setText(partname);
                                modelnoId.setText(partmodelno);
                                brandnameId.setText(partbrand);
                                quntyId.setText(quantity);
                                comId.setText(companyname);
                                modId.setText(modelno);
                                seriId.setText(serialno);
                                Picasso.with(getApplicationContext())
                                        .load(modelpicture)
                                        .placeholder(R.drawable.priviewno)   // optional
                                        .error(R.drawable.priviewno)      // optional
                                        .resize(200,200)                        // optional
                                        .into(imageView2);
                                Picasso.with(getApplicationContext())
                                        .load(serialpicture)
                                        .placeholder(R.drawable.priviewno)   // optional
                                        .error(R.drawable.priviewno)      // optional
                                        .resize(200,200)                        // optional
                                        .into(imageView3);
                                pendingRsgId.setText("Pending For Part");
                            }
                            else if (pendingflag.equals("o")){
                                pendingRsgId.setVisibility(View.VISIBLE);
                                plv.setVisibility(View.GONE);
                                olv.setVisibility(View.VISIBLE);
                                tb1.setVisibility(View.GONE);
                                tb2.setVisibility(View.VISIBLE);
                                odateId.setText(date);
                                otimeId.setText(time);
                                opronameId.setText(reason);
                                comId1.setText(companyname);
                                modId1.setText(modelno);
                                seriId1.setText(serialno);
                                Picasso.with(getApplicationContext())
                                        .load(modelpicture)
                                        .placeholder(R.drawable.priviewno)   // optional
                                        .error(R.drawable.priviewno)      // optional
                                        .resize(200,200)                        // optional
                                        .into(imageView4);
                                Picasso.with(getApplicationContext())
                                        .load(serialpicture)
                                        .placeholder(R.drawable.priviewno)   // optional
                                        .error(R.drawable.priviewno)      // optional
                                        .resize(200,200)                        // optional
                                        .into(imageView5);
                                pendingRsgId.setText("Pending For Other");
                            }
                            else if (pendingflag.equals("NA")) {
                                plv.setVisibility(View.GONE);
                                olv.setVisibility(View.GONE);
                                tb1.setVisibility(View.GONE);
                                tb2.setVisibility(View.GONE);

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
                params.put("tech_id", techid);
                Log.e("MethodAsyenbyId",bId+" "+techid);
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
                            bookingcode = obj.getString("booking_code");
                            bookid = obj.getString("booking_code");
                            String prdctcategoryname = obj.getString("prdct_category_name");
                            prdctname = obj.getString("prdct_name");
                            prdctcmpny = obj.getString("prdct_cmpny");
                            prdctmodel = obj.getString("prdct_model");
                            String prdcticon = obj.getString("prdct_icon");
                            String bookingplaceon = obj.getString("booking_place_on");
                            String customername = obj.getString("customer_name");
                            String customerphn = obj.getString("customer_phn");
                            String customeradrs = obj.getString("customer_adrs");
                            productissue = obj.getString("product_issue");
                            String preferdatetime = obj.getString("prefer_date_time");
                            String totalchrg = obj.getString("total_chrg");
                            String servicephn = obj.getString("service_phn");
                            String serialno = obj.getString("serial_no");
                            String modelpicture = obj.getString("model_picture");
                            String serialpicture = obj.getString("serial_picture");
                             picturestatus = obj.getString("picture_status");
                            Log.e("SER",serialno+" "+picturestatus);
                           // actualImage = new File(obj.getString("model_picture"));
                          //  actualImage1 = new File(obj.getString("serial_picture"));
                            Log.e("ACEPTVIEW"," "+bookingcode+" "+prdctcategoryname+" "+prdctname+" "+prdctcmpny+" "+prdcticon+" "+bookingplaceon+" "
                                    +customername+" "+customerphn+" "
                                    +customeradrs+" "+productissue+" "+preferdatetime+" "+totalchrg+" "+prdctmodel+" "+servicephn+" ");
                            nameId.setText(prdctname);
                            jonId.setText("JOB ID :"+" "+bookingcode);
                            time.setText(bookingplaceon);
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
                            serialnoID.setText(serialno);
                            Glide.with(getApplicationContext())
                                    .load(prdcticon)
                                    .into(imgID);
                            Picasso.with(getApplicationContext())
                                    .load(modelpicture)
                                    .placeholder(R.drawable.priviewno)   // optional
                                    .error(R.drawable.priviewno)      // optional
                                    .resize(200,200)                        // optional
                                    .into(imageView);
                            Picasso.with(getApplicationContext())
                                    .load(serialpicture)
                                    .placeholder(R.drawable.priviewno)   // optional
                                    .error(R.drawable.priviewno)      // optional
                                    .resize(200,200)                        // optional
                                    .into(imageView1);

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
    public void Updatebookingproductdetails(final String bId,final File actualImage,final File actualImage1){
        Log.e("SHOWDATA",bId+" "+actualImage+" "+" "+actualImage1);
        final String prdctcmpny = CompanyId.getText().toString();
        final String prdctmodel = modelId.getText().toString();
        final String serialno = serialnoID.getText().toString();
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
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPDATEPRODUCTOTPDETAILS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("UPDATEPRODUCTOTPDETAILS", response);
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            String msg = obj.getString("msg");
//                            String bookid = obj.getString("book_id");
//                            String Message = obj.getString("Message");
//                            if (msg.equals("1")){
//                                closejob(bId,techid);
//                                Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //Toast.makeText(getApplicationContext(), "UserName Password Not Valid", Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("book_id", bId);
//                params.put("item_cmpny", prdctcmpny);
//                params.put("model_no", prdctmodel);
//                params.put("serial_no", serialno);
//                Log.e("UPDATEPRODUDVIEW"," "+bId+" "+prdctcmpny+" "+" "+prdctmodel+" "+serialno);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
//        stringRequest.setShouldCache(false);
//        volleySingleton.addToRequestQueue(stringRequest);
        //////
         final String  image1 = String.valueOf(actualImage);
         final String  image2 = String.valueOf(actualImage1);
        AndroidNetworking.upload(URLs.UPDATEPRODUCTOTPDETAILS)
               // .addMultipartFile(UPLOAD_KEY,imagePath)
                .addMultipartParameter("book_id",bId)
                .addMultipartParameter("item_cmpny",prdctcmpny)
                .addMultipartParameter("model_no",prdctmodel)
                .addMultipartParameter("serial_no",serialno)
                .addMultipartFile(MODELPIC,actualImage)
                .addMultipartFile(SERIALPIC,actualImage1)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        Log.e(TAG, "onProgress: called..." );
                        Log.e("SHOWDATA"," "+bId+" "+" "
                                +prdctcmpny+" "+prdctmodel+" "+serialno+" "+actualImage+" "+actualImage1);
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
                            String msg = obj.getString("msg");
                            String bookid = obj.getString("book_id");
                            String Message = obj.getString("Message");
                            if (msg.equals("1")){
                                closejob(bId,techid);
                                Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
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
    public void Updatebookingproductdetails_two(final String bId){
        final String prdctcmpny = CompanyId.getText().toString();
        final String prdctmodel = modelId.getText().toString();
        final String serialno = serialnoID.getText().toString();
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPDATEPRODUCTOTPDETAILSTWO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Updatebotwo", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg = obj.getString("msg");
                            String bookid = obj.getString("book_id");
                            String Message = obj.getString("Message");
                            if (msg.equals("1")){
                                closejob(bId,techid);
                                Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
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
                params.put("item_cmpny", prdctcmpny);
                params.put("model_no",prdctmodel);
                params.put("serial_no",serialno);
                Log.e("Updatebotwo"," "+bId+" "
                        +prdctcmpny+" "+prdctmodel+" "+serialno);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);


    }
    public void closejob(final String bId,final  String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CLOSEJOBOTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CLOSEJOBOTP", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg = obj.getString("msg");
                            String bookidd = obj.getString("book_id");
                            String Message = obj.getString("Message");
                            String subprodid = obj.getString("sub_prod_id");
                            String status = obj.getString("status");
                            String approvemsg = obj.getString("approve_msg");
                            String partnames = obj.getString("part_names");
                            String partmodelnos = obj.getString("part_model_nos");
                            String partbrands = obj.getString("part_brands");
                            String partquantitys = obj.getString("part_quantitys");
                            if (status.equals("y")){
                                if (msg.equals("1")){
                                    Intent intent = new Intent(getApplicationContext(), CloaseSms.class);
                                    intent.putExtra("bookid",bookid);
                                    intent.putExtra("bId",bId);
                                    intent.putExtra("prdctname",prdctname);
                                    intent.putExtra("prdctcmpny",CompanyId.getText().toString());
                                    intent.putExtra("prdctmodel",modelId.getText().toString());
                                    intent.putExtra("productissue",productissue);
                                    intent.putExtra("productissue",productissue);
                                    intent.putExtra("subprodid",subprodid);
                                    intent.putExtra("partnames",partnames);
                                    intent.putExtra("partmodelnos",partmodelnos);
                                    intent.putExtra("partbrands",partbrands);
                                    intent.putExtra("partquantitys",partquantitys);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                                    Toast.makeText(getApplicationContext(),obj.getString("Message"),Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),obj.getString("Message"),Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if (status.equals("n1")){
                                if (msg.equals("1")){
                                    Intent intent = new Intent(getApplicationContext(), CloaseSms.class);
                                    intent.putExtra("bookid",bookid);
                                    intent.putExtra("bId",bId);
                                    intent.putExtra("prdctname",prdctname);
                                    intent.putExtra("prdctcmpny",CompanyId.getText().toString());
                                    intent.putExtra("prdctmodel",modelId.getText().toString());
                                    intent.putExtra("productissue",productissue);
                                    intent.putExtra("productissue",productissue);
                                    intent.putExtra("subprodid",subprodid);
                                    intent.putExtra("partnames",partnames);
                                    intent.putExtra("partmodelnos",partmodelnos);
                                    intent.putExtra("partbrands",partbrands);
                                    intent.putExtra("partquantitys",partquantitys);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                                    Toast.makeText(getApplicationContext(),obj.getString("Message"),Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),obj.getString("Message"),Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if (status.equals("n2")){
                                Toast.makeText(getApplicationContext(),obj.getString("approve_msg"),Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //Toast.makeText(getApplicationContext(),obj.getString("approve_msg"),Toast.LENGTH_SHORT).show();
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
                Log.e("CLOSEJOBOTPVIEW"," "+bId+" "+techid+" ");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public void requestforreassement(){
        AndroidNetworking.upload(URLs.REQUESTFORREASSETMENT2)
                // .addMultipartFile(UPLOAD_KEY,imagePath)
                //.addMultipartParameter("book_id",bId)
                .addMultipartParameter("item_cmpny",prdctcmpny)
                .addMultipartParameter("model_no",prdctmodel)
                .addMultipartParameter("serial_no",serialno)
                .addMultipartFile(MODELPIC,actualImage)
                .addMultipartFile(SERIALPIC,actualImage1)
                .addMultipartParameter("book_id",bId)
                .addMultipartParameter("tech_id",techid)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        Log.e(TAG, "onProgress: called..." );
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: "+response );
                        String res = String.valueOf(response);
                        Log.e("RexAss",res);
                        try {
                            JSONObject obj = new JSONObject(res);
                            String status = obj.getString("status_updt");
                            String Message = obj.getString("msg_updt");
                            if (status.equals("1")){
                                requestreNext();
                                Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
                            }
                            else {
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
    public void requestforreassementtwo(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.REQUESTFORREASSETMENT3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("requestforreassementtwo", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status_updt");
                            String Message = obj.getString("msg_updt");
                            if (status.equals("1")){
                                requestreNext();
                                Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
                            }
                            else {
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
                params.put("item_cmpny",prdctcmpny);
                params.put("model_no",prdctmodel);
                params.put("serial_no",serialno);
                params.put("book_id",bId);
                params.put("tech_id",techid);
                Log.e("requestforreassementtwo"," "+prdctcmpny+" "
                        +prdctmodel+" "+serialno+" "+bId+" "+techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public void requestreNext(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.REQUESTFORREASSETMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("REQUESTFORREASSETMENT", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String Message = obj.getString("msg");
                            if (status.equals("1")){
                                Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                                Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
                            }
                            else {
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
                Log.e("PATRS",bId+" "+techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);


    }

//    public void onActivityResult (int requestCode, int resultCode, Intent data){
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            try {
//                actualImage = FileUtil.from(getContext(),data.getData());
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(actualImage));
//                String fileImage = String.valueOf(actualImage);
//                file_extn = fileImage.substring(fileImage.lastIndexOf("/") + 1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//
//        }
//        if (requestCode == Image_Capture_Code) {
//            if (resultCode == RESULT_OK) {
//                bitmap = (Bitmap) data.getExtras().get("data");
//                imageview_account_profile.setImageBitmap(bitmap);
//                Uri tempUri = getImageUri(getContext(), bitmap);
//                File finalFile = new File(getRealPathFromURII(tempUri));
//                url = String.valueOf(finalFile);
//                actualImage = finalFile;
//                Log.e("path", String.valueOf(actualImage));
//                String fileImage = String.valueOf(actualImage);
//                file_extn = fileImage.substring(fileImage.lastIndexOf("/") + 1);
//            } else if (resultCode == RESULT_CANCELED) {
//
//            }
//        }
//    }
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
        Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
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

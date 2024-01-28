package call.callsolv.call2solvetechnician;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.goodiebag.pinview.Pinview;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import call.callsolv.call2solvetechnician.SetGetActivity.AddchargeValue;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.VisitsetgetValue;
public class CloaseSms extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    String bookid,bId,techid,otp,prdctname,prdctcmpny,
            prdctmodel,productissue,subprodid,partnames,partmodelnos,partbrands,partquantitys;
    Pinview pinview1;
    TextView pnameID,cnameID,mnameID,pissuedID,VchargeId,totalId;
    Button subID;
    RecyclerView recyclerView,recyclerVieww;
    List<AddchargeValue> addchargeValueList;
    List<VisitsetgetValue> visitsetgetValues;
    RadioGroup chagegroudId;
    RadioButton onlychageId,addchageId;
    LinearLayout onlyLvId,addlvId;
    ArrayList<VisitsetgetValue> arrlist = new ArrayList<VisitsetgetValue>();
    ArrayList<AddchargeValue> arrlist1 = new ArrayList<AddchargeValue>();
    VisitingCharge adapter;
    Addchage adapter1;
    int addvalue =  0;
    int addvalue1 = 0;
    int addvalue2 = 0;
    int addvalue3 = 0;
    TextView addvalueId;
    EditText PnameId,amuId,pmodelNoId,pbrandId,quntyId;
    String pname;
    int pamount = 0;
    View VbarId;
    String vtype;
    String otype;
    String vchagetype = "0";
    String vvaddvalue1 = "0";
    String ochagetype1 = "0";
    String ochagetype2 = "0";
    String ochagetype3 = "0";
    String proName = "NA";
    String proamount = "0";
    String productmodelnoBrand ="NA";
    String productbrandBrand ="NA";
    String qtrst = "NA";
    String chargeId;
    String chargename;
    Button calculateId;
    String Cusrate,Cuschargeid,Cuschargename,subproid;
    RatingBar ratingBar;
    EditText reviewId;
    String reatvalue ="0";
    boolean rateval = true;
    boolean rateval1;
    boolean checkVal = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
         //       WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.closesms);
        initToolBar();
        pinview1 = findViewById(R.id.pinview1);
        Intent intent = getIntent();
        bookid =intent.getStringExtra("bookid");
        bId  =intent.getStringExtra("bId");
        prdctname =intent.getStringExtra("prdctname");
        prdctcmpny  =intent.getStringExtra("prdctcmpny");
        prdctmodel = intent.getStringExtra("prdctmodel");
        productissue  =intent.getStringExtra("productissue");
        subprodid  = intent.getStringExtra("subprodid");
        ///////////////////////////
        partnames = intent.getStringExtra("partnames");
        partmodelnos = intent.getStringExtra("partmodelnos");
        partbrands = intent.getStringExtra("partbrands");
        partquantitys = intent.getStringExtra("partquantitys");
        ////////////////////////////////
        pnameID = (TextView)findViewById(R.id.pnameID);
        cnameID = (TextView)findViewById(R.id.cnameID);
        mnameID = (TextView)findViewById(R.id.mnameID);
        pissuedID = (TextView)findViewById(R.id.pissuedID);
        VchargeId = (TextView)findViewById(R.id.VchargeId);
        totalId = (TextView)findViewById(R.id.totalId);
        subID =(Button)findViewById(R.id.subID);
        PnameId =(EditText) findViewById(R.id.PnameId);
        amuId =(EditText) findViewById(R.id.amuId);
        pmodelNoId = (EditText)findViewById(R.id.pmodelNoId);
        pbrandId = (EditText)findViewById(R.id.pbrandId);
        quntyId = (EditText)findViewById(R.id.quntyId);
        reviewId = (EditText)findViewById(R.id.reviewId);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        VbarId =(View)findViewById(R.id.VbarId);
        calculateId =(Button)findViewById(R.id.calculateId);
        PnameId.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        PnameId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_NEXT){
                    pname = PnameId.getText().toString();
                    //Log.e("childenrolled", childenrolled);
                }
                return false;
            }
        });
        amuId.setImeOptions(EditorInfo.IME_ACTION_DONE);
        amuId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
//              int childenrollvalue = amuId.getText().length();
                    if (onlychageId.isChecked() || addchageId.isChecked()) {
                        if (onlychageId.isChecked()) {
                            //pamount = amuId.getText().toString();
                            pamount = Integer.parseInt(amuId.getText().toString());
                            String amo = String.valueOf(addvalue + pamount);
                            addvalueId.setText(amo);
                            Log.e("addvalue1"," "+amo);
                        }
                        if (addchageId.isChecked()) {
                            //pamount = amuId.getText().toString();
                            pamount = Integer.parseInt(amuId.getText().toString());
                            String amo = String.valueOf(addvalue1+addvalue2+addvalue3+ pamount);
                            addvalueId.setText(amo);
                            Log.e("addvalue1"," "+amo);

                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Select Charge Details",Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        amuId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onlychageId.isChecked() || addchageId.isChecked()) {
                    if (onlychageId.isChecked()) {
                        //pamount = amuId.getText().toString();
                        int childenrollvalue = amuId.getText().length();
                        if (childenrollvalue>0){
                            pamount = Integer.parseInt(amuId.getText().toString());
                            String amo = String.valueOf(addvalue + pamount);
                            addvalueId.setText(amo);
                        }
                    }
                    if (addchageId.isChecked()) {
                        //pamount = amuId.getText().toString();
                        int childenrollvalue = amuId.getText().length();
                        if (childenrollvalue>0) {
                            pamount = Integer.parseInt(amuId.getText().toString());
                            Log.e("addvalue1"," "+addvalue1+" "+pamount);
                            String amo = String.valueOf(addvalue1+addvalue2+addvalue3+ pamount);
                            addvalueId.setText(amo);
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Select Charge Details",Toast.LENGTH_SHORT).show();
                }

            }
        });
        amuId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onlychageId.isChecked() || addchageId.isChecked()) {
                    if (onlychageId.isChecked()) {
                        //pamount = amuId.getText().toString();
                        int childenrollvalue = amuId.getText().length();
                        if (childenrollvalue>0){
                            pamount = Integer.parseInt(amuId.getText().toString());
                            String amo = String.valueOf(addvalue + pamount);
                            addvalueId.setText(amo);
                        }
                    }
                    if (addchageId.isChecked()) {
                        //pamount = amuId.getText().toString();
                        int childenrollvalue = amuId.getText().length();
                        if (childenrollvalue>0) {
                            pamount = Integer.parseInt(amuId.getText().toString());
                            String amo = String.valueOf(addvalue1+addvalue2+addvalue3+ pamount);
                            addvalueId.setText(amo);
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Select Charge Details",Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerVieww =(RecyclerView)findViewById(R.id.recyclerVieww);
        recyclerVieww.setHasFixedSize(true);
        recyclerVieww.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addchargeValueList = new ArrayList<>();
        visitsetgetValues = new ArrayList<>();
        chagegroudId = (RadioGroup)findViewById(R.id.chagegroudId);
        onlychageId = (RadioButton)findViewById(R.id.onlychageId);
        addchageId = (RadioButton)findViewById(R.id.addchageId);
        onlyLvId = (LinearLayout)findViewById(R.id.onlyLvId);
        addlvId = (LinearLayout)findViewById(R.id.addlvId);
        addvalueId = (TextView)findViewById(R.id.addvalueId);
        subID.setOnClickListener(this);
        pnameID.setText("PRODUCT NAME"+":"+" "+prdctname);
        cnameID.setText("COMPANY NAME"+":"+" "+prdctcmpny);
        mnameID.setText("MODEL NO"+":"+" "+prdctmodel);
        pissuedID.setText("PRODUCT ISSUED"+":"+" "+productissue);
       // pissuedID.setText(bId+" "+otp+" "+techid);
        Log.e("bId",bId+" "+bookid+" "+subprodid);
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        pinview1.setPinViewEventListener(new Pinview.PinViewEventListener(){
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                //Toast.makeText(CloaseSms.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
                otp = pinview.getValue();
                if (addvalueId.getText().toString().equals("0")){
                    Toast.makeText(getApplicationContext(),"Please select any charge to continue",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (rateval1 == true) {
                        CheckOTPToCloseJob(bId, otp, techid);
                    }
                    else {
                        pinview1.clearValue();
                        Toast.makeText(getApplicationContext(),"Please Select Rating Star",Toast.LENGTH_SHORT).show();
                    }
                }
            }});
        onlyLvId.setVisibility(View.VISIBLE);
        VbarId.setVisibility(View.GONE);
        PnameId.setVisibility(View.GONE);
        amuId.setVisibility(View.GONE);
        pmodelNoId.setVisibility(View.GONE);
        pbrandId.setVisibility(View.GONE);
        quntyId.setVisibility(View.GONE);
        chagegroudId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId== R.id.onlychageId){
                    onlyLvId.setVisibility(View.VISIBLE);
                    addlvId.setVisibility(View.GONE);
                    visitsetgetValues.clear();
                    getchargesetaup(subprodid,bId);
                    addvalueId.setText("");
                    PnameId.setText("");
                    amuId.setText("");
                    pmodelNoId.setText("");
                    pbrandId.setText("");
                    quntyId.setText("");
                    vtype = "v";
                    VbarId.setVisibility(View.GONE);
                    PnameId.setVisibility(View.GONE);
                    amuId.setVisibility(View.GONE);
                    pmodelNoId.setVisibility(View.GONE);
                    pbrandId.setVisibility(View.GONE);
                    quntyId.setVisibility(View.GONE);
                    ochagetype1 = "0";
                    ochagetype2 = "0";
                    ochagetype3 = "0";
                    addvalueId.setText("0");
//                    if (pinview1.getPinLength() == 1){
//                        pinview1.clearValue();
//                    }
//                   else if (pinview1.getPinLength() == 2){
//                        pinview1.clearValue();
//                    }
//                   else if (pinview1.getPinLength() == 3){
//                        pinview1.clearValue();
//                    }
//                   else if (pinview1.getPinLength() == 4){
//                        pinview1.clearValue();
//                    }
                    ratingBar.setRating(0F);
                }
               else if (checkedId== R.id.addchageId){
                    onlyLvId.setVisibility(View.GONE);
                    addlvId.setVisibility(View.VISIBLE);
                    addchargeValueList.clear();
                    getAddcharge(subprodid,bId);
                    addvalueId.setText("");
                    PnameId.setText("");
                    amuId.setText("");
                    pmodelNoId.setText("");
                    pbrandId.setText("");
                    quntyId.setText("");
                    otype = "o";
                    vchagetype = "0";
//                    if (pinview1.getPinLength() == 1){
//                        pinview1.clearValue();
//                    }
//                   else if (pinview1.getPinLength() == 2){
//                        pinview1.clearValue();
//                    }
//                    else if (pinview1.getPinLength() == 3){
//                        pinview1.clearValue();
//                    }
//                    else if (pinview1.getPinLength() == 4){
//                        pinview1.clearValue();
//                    }
                    ratingBar.setRating(0F);
                    if (partnames.equals("NA")){

                    }
                    else {
                        PnameId.setText(partnames);
                        pmodelNoId.setText(partmodelnos);
                        pbrandId.setText(partbrands);
                        quntyId.setText(partquantitys);
                    }
               }
            }
        });
        //adding some items to our list
//        visitsetgetValues.add(
//                new VisitsetgetValue("1","Visiting Charge","200"
//                        ));
//        VisitingCharge adapter = new VisitingCharge(this, visitsetgetValues);
//        recyclerView.setAdapter(adapter);
//        addchargeValueList.add(
//                new AddchargeValue("1","Service Charge","200"
//                ));
//        addchargeValueList.add(
//                new AddchargeValue("2","Installation Charge","400"
//                ));
//        addchargeValueList.add(
//                new AddchargeValue("3","UnInstallation Charge","600"
//                ));
//        Addchage adapter1 = new Addchage(this, addchargeValueList);
//        recyclerVieww.setAdapter(adapter1);
      //  getchargesetaup(subprodid);
        calculateId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addchageId.isChecked()) {
                    //pamount = amuId.getText().toString();
                    int childenrollvalue = amuId.getText().length();
                    if (childenrollvalue>0) {
                        pamount = Integer.parseInt(amuId.getText().toString());
                        Log.e("addvalue1"," "+addvalue1+" "+pamount);
                        String amo = String.valueOf(addvalue1+addvalue2+addvalue3+ pamount);
                        addvalueId.setText(amo);
                    }
                }
            }
        });
        //getAddcharge(subprodid);
        visitcharge(subprodid);
        chechradiobutton(bId);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                reviewId.setText(String.valueOf(v));
                rateval1 = b;
                Log.e("BOLL"," "+b);
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        reviewId.setText("Very bad");
                        break;
                    case 2:
                        reviewId.setText("Need some improvement");
                        break;
                    case 3:
                        reviewId.setText("Good");
                        break;
                    case 4:
                        reviewId.setText("Great");
                        break;
                    case 5:
                        reviewId.setText("Awesome. I love it");
                        break;
                    default:
                        reviewId.setText("");
                }
            }
        });

    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("OTP");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent intent = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
                       // startActivity(intent);
                    }
                }
        );
    }
    //read otp///
    //https://androidwave.com/automatic-sms-verification-android/
    public void CheckOTPToCloseJob(final String bId,final String otp,final String techid){
        int productnamelenthcount = PnameId.getText().length();
        int amountnamelenthcount = amuId.getText().length();
        int modelnolenthcount = pmodelNoId.getText().length();
        int probrandlenthcount = pbrandId.getText().length();
        int qty = quntyId.getText().length();
        if (productnamelenthcount>1){
            pinview1.clearValue();
            Log.e("CheckOTPToCloseJobview","LEN");
            proName = PnameId.getText().toString();
            proamount = amuId.getText().toString();
            productmodelnoBrand =  pmodelNoId.getText().toString();
            productbrandBrand = pbrandId.getText().toString();
            qtrst = quntyId.getText().toString();
            if (TextUtils.isEmpty(proName)) {
                PnameId.setError("Please enter Product Name");
                PnameId.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(proamount)) {
                amuId.setError("Please enter Amount");
                amuId.requestFocus();
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
            if (TextUtils.isEmpty(qtrst)) {
                quntyId.setError("Please enter quantity");
                quntyId.requestFocus();
                return;
            }
          }
//        else {
//            PnameId.setText("");
//            amuId.setText("");
//            pmodelNoId.setText("");
//            pbrandId.setText("");
//            Log.e("CheckOTPToCloseJobview","LEN1");
//
//        }
        ///////
        else if(amountnamelenthcount>1){
            Log.e("CheckOTPToCloseJobview","LEN1");
            pinview1.clearValue();
            proName = PnameId.getText().toString();
            proamount = amuId.getText().toString();
            productmodelnoBrand =  pmodelNoId.getText().toString();
            productbrandBrand = pbrandId.getText().toString();
            qtrst = quntyId.getText().toString();
            if (TextUtils.isEmpty(proName)) {
                PnameId.setError("Please enter Product Name");
                PnameId.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(proamount)) {
                amuId.setError("Please enter Amount");
                amuId.requestFocus();
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

            if (TextUtils.isEmpty(qtrst)) {
                quntyId.setError("Please enter quantity");
                quntyId.requestFocus();
                return;
            }
        }
//        else {
//            PnameId.setText("");
//            amuId.setText("");
//            pmodelNoId.setText("");
//            pbrandId.setText("");
//            Log.e("CheckOTPToCloseJobview","LEN3");
//        }
        //////

        else if (modelnolenthcount>1){
            Log.e("CheckOTPToCloseJobview","LEN2");
            pinview1.clearValue();
            proName = PnameId.getText().toString();
            proamount = amuId.getText().toString();
            productmodelnoBrand =  pmodelNoId.getText().toString();
            productbrandBrand = pbrandId.getText().toString();
            qtrst = quntyId.getText().toString();
            if (TextUtils.isEmpty(proName)) {
                PnameId.setError("Please enter Product Name");
                PnameId.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(proamount)) {
                amuId.setError("Please enter Amount");
                amuId.requestFocus();
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
            if (TextUtils.isEmpty(qtrst)) {
                quntyId.setError("Please enter quantity");
                quntyId.requestFocus();
                return;
            }
        }
//        else {
//            PnameId.setText("");
//            amuId.setText("");
//            pmodelNoId.setText("");
//            pbrandId.setText("");
//            Log.e("CheckOTPToCloseJobview","LEN4");
//        }
        ////////////////////////
        else if (probrandlenthcount>1){
            Log.e("CheckOTPToCloseJobview","LEN3");
            proName = PnameId.getText().toString();
            proamount = amuId.getText().toString();
            productmodelnoBrand =  pmodelNoId.getText().toString();
            productbrandBrand = pbrandId.getText().toString();
            qtrst = quntyId.getText().toString();
            if (TextUtils.isEmpty(proName)) {
                PnameId.setError("Please enter Product Name");
                PnameId.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(proamount)) {
                amuId.setError("Please enter Amount");
                amuId.requestFocus();
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
            if (TextUtils.isEmpty(qtrst)) {
                quntyId.setError("Please enter quantity");
                quntyId.requestFocus();
                return;
            }
        }
        else if (qty>1){
            Log.e("CheckOTPToCloseJobview","LEN3");
            proName = PnameId.getText().toString();
            proamount = amuId.getText().toString();
            productmodelnoBrand =  pmodelNoId.getText().toString();
            productbrandBrand = pbrandId.getText().toString();
            qtrst = quntyId.getText().toString();
            if (TextUtils.isEmpty(proName)) {
                PnameId.setError("Please enter Product Name");
                PnameId.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(proamount)) {
                amuId.setError("Please enter Amount");
                amuId.requestFocus();
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
            if (TextUtils.isEmpty(qtrst)) {
                quntyId.setError("Please enter quantity");
                quntyId.requestFocus();
                return;
            }
        }
        else {
            PnameId.setText("");
            amuId.setText("");
            pmodelNoId.setText("");
            pbrandId.setText("");
            quntyId.setText("");
            Log.e("CheckOTPToCloseJobview","LEN4");
        }
        if (checkVal == true){

        }
        else {
            Toast.makeText(getApplicationContext(),"Please select any charge to continue",Toast.LENGTH_SHORT).show();
            return;
        }

        final String review = reviewId.getText().toString();
        if (TextUtils.isEmpty(review)) {
            reviewId.setError("Please enter review");
            reviewId.requestFocus();
            return;
        }

        if (review.equals("Very bad")) {
            reatvalue = "1";
        }
        else if (review.equals("Need some improvement")){
            reatvalue = "2";
        }
        else if (review.equals("Good")){
            reatvalue = "3";
        }
        else if (review.equals("Great")){
            reatvalue = "4";
        }
        else if (review.equals("Awesome. I love it")){
            reatvalue = "5";
        }
        else {
            reatvalue = "0";
        }
        chargeId = ochagetype1+","+ochagetype2+","+ochagetype3;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CHECKOTPTOCLOSE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CheckOTPToCloseJob", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg = obj.getString("msg");
                            String bookid = obj.getString("book_id");
                            String Message = obj.getString("Message");
                            if (msg.equals("1")){
                                Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                                Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
                            }
                            else {
                                pinview1.clearValue();
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
                params.put("satisfy_code", otp);
                params.put("tech_id", techid);
                params.put("job_id",bookid);
                params.put("product_name",proName);
                params.put("product_price",proamount);
                if (onlychageId.isChecked()) {
                    params.put("ids", vchagetype);
                    params.put("type", vtype);
                }
                if (addchageId.isChecked()) {
                    params.put("ids", chargeId);
                    params.put("type", otype);
                }
                params.put("sub_pro_id",subprodid);
                params.put("total",addvalueId.getText().toString());
               // params.put("product_model_no",subprodid);
              //  params.put("product_brand",addvalueId.getText().toString());
                params.put("product_model_no",productmodelnoBrand);
                params.put("product_brand",productbrandBrand);
                params.put("quantity",qtrst);
                params.put("tech_rating",reatvalue);
                params.put("tech_comment",review);
                Log.e("CheckOTPToCloseJobview"," "+"book_id"+bId+" "+"satisfy_code"+otp+" "
                        +"tech_id"+techid+" "+"job_id"+bookid+" "
                        +"product_name"+proName+" "+"product_price"+proamount+" "+"ids"+vchagetype+" "
                        +"type"+vtype+" "+"ids"+chargeId+" "+
                        "type"+otype+" "
                        +"sub_pro_id"+subprodid+" "
                        +addvalueId.getText().toString()+" "+"product_model_no"+productmodelnoBrand+" "
                +"product_brand"+productbrandBrand+" "+reatvalue+ " "+qtrst+  " "+" "+review);
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
                resendotpstart(bId,techid);
                break;
            default:
        }
    }
    public void getchargesetaup(final String subprodid,final String bId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CHARGESETUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SUBPRODUCTID", response);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){
                                JSONObject  jsonObject = array.getJSONObject(i);
                                String rate = jsonObject.getString("rate");
                                String chargename = jsonObject.getString("charge_name");
                                String subproid = jsonObject.getString("sub_pro_id");
                                String chrgtyp = jsonObject.getString("chrg_typ");
                                if (chargename.equals("Visiting Charge")) {
                                    visitsetgetValues.add(new VisitsetgetValue(
                                            jsonObject.getString("rate"),
                                            jsonObject.getString("charge_name"),
                                            jsonObject.getString("sub_pro_id"),
                                            jsonObject.getString("chrg_typ")
                                    ));
                                }
                            }
                            arrlist.addAll(visitsetgetValues);
                            adapter = new VisitingCharge(getApplicationContext(), visitsetgetValues);
                            recyclerView.setAdapter(adapter);
//                            String status = obj.getString("status");
//                            String msg = obj.getString("msg");
//                            if (status.equals("1")){
//                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//                            }
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
               // params.put("sub_pro_id", subprodid);
                params.put("book_id", bId);
                Log.e("SUBPRODUCTID", " " + subprodid+ " "+bId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public void getAddcharge(final String subprodid,final String bId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CHARGESETUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SUBPRODUCTID1", response);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){
                                JSONObject  jsonObject = array.getJSONObject(i);
                                String rate = jsonObject.getString("rate");
                                String chargename = jsonObject.getString("charge_name");
                                String subproid = jsonObject.getString("sub_pro_id");
                                String chrgtyp = jsonObject.getString("chrg_typ");
                                if (chargename.equals("Visiting Charge :")) {

                                }
                                else if (chargename.equals("Service")){
                                    addchargeValueList.add(new AddchargeValue(
                                            jsonObject.getString("rate"),
                                            jsonObject.getString("charge_name"),
                                            jsonObject.getString("sub_pro_id"),
                                            jsonObject.getString("chrg_typ")
                                    ));
                                }
                                else if (chargename.equals("Uninstallation")){
                                    addchargeValueList.add(new AddchargeValue(
                                            jsonObject.getString("rate"),
                                            jsonObject.getString("charge_name"),
                                            jsonObject.getString("sub_pro_id"),
                                            jsonObject.getString("chrg_typ")
                                    ));

                                }
                                else if (chargename.equals("Installation")){
                                    addchargeValueList.add(new AddchargeValue(
                                            jsonObject.getString("rate"),
                                            jsonObject.getString("charge_name"),
                                            jsonObject.getString("sub_pro_id"),
                                            jsonObject.getString("chrg_typ")
                                    ));
                                }
                            }
                            arrlist1.addAll(addchargeValueList);
                            adapter1 = new Addchage(getApplicationContext(), addchargeValueList);
                            recyclerVieww.setAdapter(adapter1);
//                            String status = obj.getString("status");
//                            String msg = obj.getString("msg");
//                            if (status.equals("1")){
//                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//                            }
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
               // params.put("sub_pro_id", subprodid);
                params.put("book_id", bId);
                Log.e("SUBPRODUCTID", " " + subprodid+ " "+" "+bId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void visitcharge(final String subprodid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.VISITCHARGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("VISITCHARGE", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String visitingcharge = obj.getString("visiting_charge");
                            chargename = obj.getString("charge_name");
//                            VchargeId.setText(chargename+" "+visitingcharge);
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
                params.put("sub_pro_id", subprodid);
                Log.e("SUBPRODUCTID", " " + subprodid+ " ");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
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
                            totalId.setText(Cuschargename+" : ");
                            VchargeId.setText(Cusrate);
                            if (Cuschargename.equals("Visiting Charge")) {
                                onlychageId.setChecked(true);
                            }
                            else if (Cuschargename.equals("Service")){
                                addchageId.setChecked(true);
                            }
                            else if (Cuschargename.equals("Uninstallation")){
                                addchageId.setChecked(true);
                            }
                            else if (Cuschargename.equals("Installation")){
                                addchageId.setChecked(true);
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
    public void resendotpstart(final String bId,final String techid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.RESENDOTOCLOSE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("resendotpstartclose", response);
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
                params.put("t_id", techid);
                Log.e("resendotpstartviewclose", " " + bId+ " "+techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public class VisitingCharge extends RecyclerView.Adapter<VisitingCharge.VisitingViewHolder> {
        private Context mCtx;
        private  List<VisitsetgetValue> visitsetgetValues;
        ArrayList<VisitsetgetValue> list;
        public VisitingCharge(Context mCtx, List<VisitsetgetValue> visitsetgetValues) {
            this.mCtx = mCtx;
            this.visitsetgetValues = visitsetgetValues;
        }
        @Override
        public VisitingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.calldetails_charge, null);
            return new VisitingViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final VisitingViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            final VisitsetgetValue visitsetgetValue = visitsetgetValues.get(position);
            holder.ChaddchageId.setText(visitsetgetValue.getVisitname());
            holder.chagevalueId.setText(visitsetgetValue.getVisitvalue());
            holder.chargetypeId.setText(visitsetgetValue.getChrgtyp());
            if (Cuschargeid.equals(visitsetgetValue.getChrgtyp())){
                //1
                holder.ChaddchageId.setChecked(true);
                addvalue = Integer.parseInt(holder.chagevalueId.getText().toString());
                vchagetype = holder.chargetypeId.getText().toString();
                vvaddvalue1 = String.valueOf(addvalue);
                addvalueId.setText(vvaddvalue1);
                Log.e("positionV", "0"+" "+addvalue+" "+vchagetype);
            }
//        if (visitsetgetValue.getChrgtyp().equals("1")){
//                //1
//                holder.ChaddchageId.setChecked(true);
//
//            }
            holder.ChaddchageId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addvalue = Integer.parseInt(holder.chagevalueId.getText().toString());
                    vchagetype = holder.chargetypeId.getText().toString();
                   // addvalueId.setText(addvalue+pamount);
                    VbarId.setVisibility(View.GONE);
                    PnameId.setVisibility(View.GONE);
                    amuId.setVisibility(View.GONE);
                    pmodelNoId.setVisibility(View.GONE);
                    pbrandId.setVisibility(View.GONE);
                    quntyId.setVisibility(View.GONE);
                }
            });
            holder.ChaddchageId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked())
                    {
                        if (position==0) {
                            addvalue = Integer.parseInt(holder.chagevalueId.getText().toString());
                            vchagetype = holder.chargetypeId.getText().toString();
                            vvaddvalue1 = String.valueOf(addvalue);
                            addvalueId.setText(vvaddvalue1);
                            Log.e("positionV", "0"+" "+addvalue+" "+vchagetype);
                        }
                    }
                    else
                    {
                        compoundButton.setChecked(false);
                        if (position==0) {
                            addvalue = 0;
                            vchagetype ="0";
                            vvaddvalue1 = "0";
                            addvalueId.setText(vvaddvalue1);
                            Log.e("position", "0"+" "+addvalue+" "+vchagetype);
                        }
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return visitsetgetValues.size();
        }
        class VisitingViewHolder extends RecyclerView.ViewHolder {
            CheckBox ChaddchageId;
            TextView chagevalueId,chargetypeId;
            public VisitingViewHolder(View itemView) {
                super(itemView);
                ChaddchageId =(CheckBox) itemView.findViewById(R.id.ChaddchageId);
                chagevalueId =(TextView) itemView.findViewById(R.id.chagevalueId);
                chargetypeId =(TextView) itemView.findViewById(R.id.chargetypeId);
            }
        }
    }

    public class Addchage extends RecyclerView.Adapter<Addchage.AddchageViewHolder> {
        private Context mCtx;
        private List<AddchargeValue> addchargeValueList;
        public Addchage(Context mCtx, List<AddchargeValue> addchargeValueList) {
            this.mCtx = mCtx;
            this.addchargeValueList = addchargeValueList;
        }
        @Override
        public AddchageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.calldetails_charge, null);
            return new AddchageViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final AddchageViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            final AddchargeValue addchargeValue = addchargeValueList.get(position);
            holder.ChaddchageId.setText(addchargeValue.getAddchagename());
            holder.chagevalueId.setText(addchargeValue.getAddchagevalue());
            holder.chargetypeId.setText(addchargeValue.getChrgtyp());
            Log.e("positionn",Cuschargeid);
            if (Cuschargeid.equals(addchargeValue.getChrgtyp())){
           // if (Cuschargeid.equals("2")){
                //2
                holder.ChaddchageId.setChecked(true);
                if (addchargeValue.getChrgtyp().equals("2")){
                    addvalue1 = Integer.parseInt(holder.chagevalueId.getText().toString());

                }
                else {
                    addvalue1 = 0;
                }
                if (addchargeValue.getChrgtyp().equals("3")){
                    addvalue2 = Integer.parseInt(holder.chagevalueId.getText().toString());

                }
                else {
                    addvalue2 = 0;
                }
                if (addchargeValue.getChrgtyp().equals("6")){
                    addvalue3 = Integer.parseInt(holder.chagevalueId.getText().toString());
                }
                else {
                    addvalue3 = 0;
                }
                ochagetype1 = holder.chargetypeId.getText().toString();
                Log.e("position", "00"+" "+addvalue1+" "+ochagetype1);
                if (addchageId.isChecked()) {
                    // pamount = Integer.parseInt(amuId.getText().toString());
                    checkVal = true;
                    Log.e("addvalue1"," "+addvalue1+" "+pamount);
                    String amo = String.valueOf(addvalue1+addvalue2+addvalue3);
                    addvalueId.setText(amo);

                }
                VbarId.setVisibility(View.VISIBLE);
                PnameId.setVisibility(View.VISIBLE);
                amuId.setVisibility(View.VISIBLE);
                pmodelNoId.setVisibility(View.VISIBLE);
                pbrandId.setVisibility(View.VISIBLE);
                quntyId.setVisibility(View.VISIBLE);
            }
//           else if (Cuschargeid.equals(addchargeValue.getChrgtyp())){
//           //if (Cuschargeid.equals("6")){
//                //6
//                holder.ChaddchageId.setChecked(true);
//                addvalue2 = Integer.parseInt(holder.chagevalueId.getText().toString());
//                ochagetype2 = holder.chargetypeId.getText().toString();
//                Log.e("position", "01"+" "+addvalue2+" "+ochagetype2);
//                if (addchageId.isChecked()) {
//                    // pamount = Integer.parseInt(amuId.getText().toString());
//                    Log.e("addvalue1"," "+addvalue1+" "+pamount);
//                    String amo = String.valueOf(addvalue1+addvalue2+addvalue3);
//                    addvalueId.setText(amo);
//
//                }
//                VbarId.setVisibility(View.VISIBLE);
//                PnameId.setVisibility(View.VISIBLE);
//                amuId.setVisibility(View.VISIBLE);
//                pmodelNoId.setVisibility(View.VISIBLE);
//                pbrandId.setVisibility(View.VISIBLE);
//
//            }
//           else if (Cuschargeid.equals(addchargeValue.getChrgtyp())){
//         //   else if (Cuschargeid.equals("3")){
//                //3
//                holder.ChaddchageId.setChecked(true);
//                addvalue3 = Integer.parseInt(holder.chagevalueId.getText().toString());
//                ochagetype3 = holder.chargetypeId.getText().toString();
//                Log.e("position", "02"+" "+addvalue3+" "+ochagetype3);
//                if (addchageId.isChecked()) {
//                    // pamount = Integer.parseInt(amuId.getText().toString());
//                    Log.e("addvalue1"," "+addvalue1+" "+pamount);
//                    String amo = String.valueOf(addvalue1+addvalue2+addvalue3);
//                    addvalueId.setText(amo);
//
//                }
//                VbarId.setVisibility(View.VISIBLE);
//                PnameId.setVisibility(View.VISIBLE);
//                amuId.setVisibility(View.VISIBLE);
//                pmodelNoId.setVisibility(View.VISIBLE);
//                pbrandId.setVisibility(View.VISIBLE);
//            }
            holder.ChaddchageId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //addvalue1 = Integer.parseInt(holder.chagevalueId.getText().toString());
                   // addvalueId.setText(addvalue1+pamount);
                    Log.e("addvalue1"," "+addvalue1);
                    VbarId.setVisibility(View.VISIBLE);
                    PnameId.setVisibility(View.VISIBLE);
                    amuId.setVisibility(View.VISIBLE);
                    pmodelNoId.setVisibility(View.VISIBLE);
                    pbrandId.setVisibility(View.VISIBLE);
                    quntyId.setVisibility(View.VISIBLE);
                }
            });
            holder.ChaddchageId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked())
                    {
                        if (position==0) {
                            checkVal = true;
                          addvalue1 = Integer.parseInt(holder.chagevalueId.getText().toString());
                          ochagetype1 = holder.chargetypeId.getText().toString();
                            Log.e("position", "0"+" "+addvalue1+" "+ochagetype1);
                            if (addchageId.isChecked()) {
                                // pamount = Integer.parseInt(amuId.getText().toString());
                                Log.e("addvalue1"," "+addvalue1+" "+pamount);
                                String amo = String.valueOf(addvalue1+addvalue2+addvalue3);
                                addvalueId.setText(amo);
                            }
                        }
                        else if (position==1){
                            checkVal = true;
                            addvalue2 = Integer.parseInt(holder.chagevalueId.getText().toString());
                            ochagetype2 = holder.chargetypeId.getText().toString();
                            Log.e("position", "1"+" "+addvalue2+" "+ochagetype2);
                            if (addchageId.isChecked()) {
                                // pamount = Integer.parseInt(amuId.getText().toString());
                                Log.e("addvalue1"," "+addvalue1+" "+pamount);
                                String amo = String.valueOf(addvalue1+addvalue2+addvalue3);
                                addvalueId.setText(amo);
                            }
                        }
                        else if (position==2){
                            checkVal = true;
                            addvalue3 = Integer.parseInt(holder.chagevalueId.getText().toString());
                            ochagetype3 = holder.chargetypeId.getText().toString();
                            Log.e("position", "2"+" "+addvalue3+" "+ochagetype3);
                            if (addchageId.isChecked()) {
                                // pamount = Integer.parseInt(amuId.getText().toString());
                                Log.e("addvalue1"," "+addvalue1+" "+pamount);
                                String amo = String.valueOf(addvalue1+addvalue2+addvalue3);
                                addvalueId.setText(amo);
                            }
                        }

                    }
                    else
                    {
                        compoundButton.setChecked(false);
                        if (position==0) {
                            checkVal = false;
                            addvalue1 = 0;
                            ochagetype1 ="0";
                            Log.e("position", "0"+" "+addvalue1+" "+ochagetype1);
                            if (addchageId.isChecked()) {
                               // pamount = Integer.parseInt(amuId.getText().toString());
                                Log.e("addvalue1"," "+addvalue1+" "+pamount);
                                String amo = String.valueOf(addvalue1+addvalue2+addvalue3);
                                addvalueId.setText(amo);
                            }
                        }
                        else if (position==1){
                            checkVal = false;
                            addvalue2 = 0;
                            ochagetype2 = "0";
                            Log.e("position", "01"+" "+addvalue2+" "+ochagetype2);
                            if (addchageId.isChecked()) {
                                // pamount = Integer.parseInt(amuId.getText().toString());
                                Log.e("addvalue1"," "+addvalue1+" "+pamount);
                                String amo = String.valueOf(addvalue1+addvalue2+addvalue3);
                                addvalueId.setText(amo);
                            }
                        }
                        else if (position==2){
                            checkVal = false;
                            addvalue3 = 0;
                            ochagetype3 = "0";
                            Log.e("position", "02"+" "+addvalue3+" "+ochagetype3);
                            if (addchageId.isChecked()) {
                                // pamount = Integer.parseInt(amuId.getText().toString());
                                Log.e("addvalue1"," "+addvalue1+" "+pamount);
                                String amo = String.valueOf(addvalue1+addvalue2+addvalue3);
                                addvalueId.setText(amo);
                            }
                        }

                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return addchargeValueList.size();
        }
        class AddchageViewHolder extends RecyclerView.ViewHolder {
            CheckBox ChaddchageId;
            TextView chagevalueId,chargetypeId;
            public AddchageViewHolder(View itemView) {
                super(itemView);
                ChaddchageId =(CheckBox) itemView.findViewById(R.id.ChaddchageId);
                chagevalueId =(TextView) itemView.findViewById(R.id.chagevalueId);
                chargetypeId =(TextView) itemView.findViewById(R.id.chargetypeId);
            }
        }
    }
    @Override
    public void onBackPressed() {
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

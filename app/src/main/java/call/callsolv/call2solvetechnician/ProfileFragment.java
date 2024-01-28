package call.callsolv.call2solvetechnician;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import call.callsolv.call2solvetechnician.ApplicationActivity.Application;
import call.callsolv.call2solvetechnician.SetGetActivity.Kycsetget;
import call.callsolv.call2solvetechnician.SetGetActivity.PendingSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.ProductSpecification;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.Qulificationsetget;
public class ProfileFragment extends Fragment implements View.OnClickListener,OnBackClickListener{
    TextView plusId,educationId,productID;
    LinearLayout docId,qualiId,lvproductId;
    Menu menu;
    RecyclerView recyclerViewProductId;
    RecyclerView recyclerkycId;
    RecyclerView recyclerViewqulificationId;
    List<Kycsetget> kycsetgets;
    ArrayList<Kycsetget> arrlist = new ArrayList<Kycsetget>();
    List<Qulificationsetget>  qulificationsetgets;
    ArrayList<Qulificationsetget> arrlist1 = new ArrayList<Qulificationsetget>();
    List<ProductSpecification> productSpecifications;
    ArrayList<ProductSpecification> arrlist2 = new ArrayList<ProductSpecification>();
    RelativeLayout savedetaildID;
    String techid;
    EditText techniNameId,fdNameId,mailId;
    TextView contractnoId,whatsappnoId,AddressId,blockID,postofficeID,psstationID,pincodeID,bnknameID,branchnameId,accountnoId,accholderID,icefcodeID;
    Kycdocument adapter;
    qulification qulification;
    ProductSpsification productSpsification;
    TextView spID,distId;
    CircularImageView imageview_account_profile;
    private BackButtonHandlerInterface backButtonHandler;
    boolean doubleBackToExitPressedOnce = false;
    RadioGroup GrentedbuildigID;
    RadioButton blockId,ulbId;
    String techbdoulb,techbdoulbtype,techpolice;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        backButtonHandler = (BackButtonHandlerInterface) activity;
        backButtonHandler.addBackClickListener(this);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        docId = (LinearLayout)rootView.findViewById(R.id.docId);
        qualiId = (LinearLayout)rootView.findViewById(R.id.qualiId);
        lvproductId = (LinearLayout)rootView.findViewById(R.id.lvproductId);
        //docId.setVisibility(View.GONE);
        //qualiId.setVisibility(View.GONE);
        //lvproductId.setVisibility(View.GONE);
        plusId = (TextView)rootView.findViewById(R.id.plusId);
        educationId = (TextView)rootView.findViewById(R.id.educationId);
        productID = (TextView)rootView.findViewById(R.id.productID);
        techniNameId = (EditText)rootView.findViewById(R.id.techniNameId);
        fdNameId = (EditText)rootView.findViewById(R.id.fdNameId);
        contractnoId = (TextView) rootView.findViewById(R.id.contractnoId);
        whatsappnoId = (TextView)rootView.findViewById(R.id.whatsappnoId);
        mailId = (EditText)rootView.findViewById(R.id.mailId);
        AddressId = (TextView)rootView.findViewById(R.id.AddressId);
        blockID = (TextView)rootView.findViewById(R.id.blockID);
        postofficeID = (TextView)rootView.findViewById(R.id.postofficeID);
        psstationID = (TextView)rootView.findViewById(R.id.psstationID);
        pincodeID = (TextView)rootView.findViewById(R.id.pincodeID);
        bnknameID = (TextView)rootView.findViewById(R.id.bnknameID);
        branchnameId = (TextView)rootView.findViewById(R.id.branchnameId);
        accountnoId = (TextView)rootView.findViewById(R.id.accountnoId);
        accholderID = (TextView)rootView.findViewById(R.id.accholderID);
        icefcodeID = (TextView)rootView.findViewById(R.id.icefcodeID);
        spID = (TextView)rootView.findViewById(R.id.spID);
        GrentedbuildigID = (RadioGroup)rootView.findViewById(R.id.GrentedbuildigID);
        distId =(TextView)rootView.findViewById(R.id.distId);
        //blockId = (RadioButton)rootView.findViewById(R.id.blockId);
        //ulbId = (RadioButton)rootView.findViewById(R.id.ulbId);
        imageview_account_profile = (CircularImageView) rootView.findViewById(R.id.imageview_account_profile);
        savedetaildID = (RelativeLayout)rootView.findViewById(R.id.savedetaildID);
        savedetaildID.setOnClickListener(this);
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        Log.e("techhId",techid);
        ShowProfile(techid);
//        plusId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                docId.setVisibility(View.VISIBLE);
//
//            }
//        });
//        educationId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                qualiId.setVisibility(View.VISIBLE);
//
//            }
//        });
//        productID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lvproductId.setVisibility(View.VISIBLE);
//
//            }
//        });
        AppBarLayout mAppBarLayout = (AppBarLayout)rootView.findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;

                } else if (isShow) {
                    isShow = false;

                }
            }
        });
        recyclerkycId = (RecyclerView)rootView.findViewById(R.id.recyclerkycId);
        recyclerkycId.setHasFixedSize(true);
        recyclerkycId.setLayoutManager(new LinearLayoutManager(getContext()));
        kycsetgets =new ArrayList<>();
        recyclerViewqulificationId = (RecyclerView)rootView.findViewById(R.id.recyclerViewqulificationId);
        recyclerViewqulificationId.setHasFixedSize(true);
        recyclerViewqulificationId.setLayoutManager(new LinearLayoutManager(getContext()));
        qulificationsetgets = new ArrayList<>();
        recyclerViewProductId = (RecyclerView)rootView.findViewById(R.id.recyclerViewProductId);
       // recyclerViewProductId.setHasFixedSize(true);
      //  recyclerViewProductId.setLayoutManager(new LinearLayoutManager(getContext()));
        productSpecifications = new ArrayList<>();
        recyclerViewProductId.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),4);
        recyclerViewProductId.setLayoutManager(layoutManager);
//        GrentedbuildigID.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//            }
//        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.savedetaildID:
                UpdateProfile(techid);
                break;
                default:
        }
    }

    public void ShowProfile(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ProfileLogin", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String techcode = obj.getString("tech_code");
                            String techname = obj.getString("tech_name");
                            String techfather = obj.getString("tech_father");
                            String techphn = obj.getString("tech_phn");
                            String techwatsapp = obj.getString("tech_watsapp");
                            String techmail = obj.getString("tech_mail");
                            String techadrs = obj.getString("tech_adrs");
                            String techdist = obj.getString("tech_dist");
                            techbdoulb = obj.getString("tech_bdo_ulb");
                            techbdoulbtype = obj.getString("tech_bdo_ulb_typ");
                            String techpo = obj.getString("tech_po");
                            techpolice = obj.getString("tech_police");
                            String techimg = obj.getString("tech_img");
                            String bankname = obj.getString("bank_name");
                            String brnchnm = obj.getString("brnch_nm");
                            String acntno = obj.getString("acnt_no");
                            String acntname = obj.getString("acnt_name");
                            String acntifsc = obj.getString("acnt_ifsc");
                            Log.e("Value_Pro"," "+techcode+" "+techname+" "+techfather+" "
                                    +techphn+" "+techwatsapp+" "+techmail+" "+techadrs+" "
                                    +techdist+" "+techbdoulb+" "+techpo+" "+techpolice+" "+techimg+" "+bankname+" "+brnchnm+" "+acntno+" "+acntname);
                            techniNameId.setText(techname);
                            fdNameId.setText(techfather);
                            contractnoId.setText(techphn);
                            whatsappnoId.setText(techwatsapp);
                            mailId.setText(techmail);
                            AddressId.setText(techadrs);
                            distId.setText(techdist);
                            blockID.setText(techbdoulb);
                            postofficeID.setText(techpo);
                            if (techpolice.equals("null")){
                                psstationID.setText("NA");
                            }
                            else {
                                psstationID.setText(techpolice);
                            }
                            pincodeID.setText(techpo);
                            bnknameID.setText(bankname);
                            branchnameId.setText(brnchnm);
                            accountnoId.setText(acntno);
                            accholderID.setText(acntname);
                            icefcodeID.setText(acntifsc);
                            Log.e("DBO",techbdoulb);
//                            if (techbdoulbtype.equals("BDO")){
//                                blockId.setChecked(true);
//                                ulbId.setChecked(false);
//                            }
//                            if (techbdoulbtype.equals("ULB")){
//                                ulbId.setChecked(true);
//                                blockId.setChecked(false);
//                            }
                            ImageLoader imageLoader = Application.getInstance().getImageLoader();
                            imageview_account_profile.setImageUrl(techimg,imageLoader);
                            JSONArray jsonArray = obj.getJSONArray("document");
                            for (int i =0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String docfound = jsonObject.getString("doc_found");
                                String docname = jsonObject.getString("doc_name");
                                String docno = jsonObject.getString("doc_no");
                                String filename = jsonObject.getString("file_name");
                                kycsetgets.add(new Kycsetget(
                                        jsonObject.getString("doc_found"),
                                        jsonObject.getString("doc_name"),
                                        jsonObject.getString("doc_no"),
                                        jsonObject.getString("file_name")
                                        ));
                                arrlist.addAll(kycsetgets);
                                adapter = new Kycdocument(getContext(), kycsetgets);
                                recyclerkycId.setAdapter(adapter);
                                Log.e("document"," "+docfound+" "+docname+" "+docno+" "+filename);
                            }
                            JSONArray jsonArray1 = obj.getJSONArray("education");
                            for (int j = 0; j<jsonArray1.length(); j++){
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                String educationfound = jsonObject1.getString("education_found");
                                String examname = jsonObject1.getString("exam_name");
                                String boardname = jsonObject1.getString("board_name");
                                String examyear = jsonObject1.getString("exam_year");
                                String examprcnt = jsonObject1.getString("exam_prcnt");
                                Log.e("education"," "+educationfound+" "+examname+" "+boardname+" "+examyear+" "+examprcnt);
                                qulificationsetgets.add(new Qulificationsetget(
                                        jsonObject1.getString("education_found"),
                                        jsonObject1.getString("exam_name"),
                                        jsonObject1.getString("board_name"),
                                        jsonObject1.getString("exam_year"),
                                        jsonObject1.getString("exam_prcnt")
                                ));
                                arrlist1.addAll(qulificationsetgets);
                                qulification = new qulification(getContext(), qulificationsetgets);
                                recyclerViewqulificationId.setAdapter(qulification);
                            }
                            JSONArray jsonArray2 = obj.getJSONArray("product");
                            for (int k = 0; k<jsonArray2.length(); k++){
                                JSONObject jsonObject2 = jsonArray2.getJSONObject(k);
                                String productfound = jsonObject2.getString("product_found");
                                String productcategory = jsonObject2.getString("product_category");
                                String product = jsonObject2.getString("product");
                                String img = jsonObject2.getString("img");
                                Log.e("product"," "+productfound+" "+productcategory+" "+product+" "+img+" ");
                                productSpecifications.add(new ProductSpecification(
                                        jsonObject2.getString("product_found"),
                                        jsonObject2.getString("product_category"),
                                        jsonObject2.getString("product"),
                                        jsonObject2.getString("img")
                                ));
                                arrlist2.addAll(productSpecifications);
                                productSpsification = new ProductSpsification(getContext(), productSpecifications);
                                recyclerViewProductId.setAdapter(productSpsification);
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
                params.put("tech_id", techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    public void UpdateProfile(final String techid){
        final String techname = techniNameId.getText().toString().trim();
        final String techfather = fdNameId.getText().toString().trim();
        final String techmail = mailId.getText().toString().trim();
        if (TextUtils.isEmpty(techname)) {
            techniNameId.setError("Please enter Technician name");
            techniNameId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(techfather)) {
            fdNameId.setError("Please enter Technician Father name");
            fdNameId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(techmail)) {
            fdNameId.setError("Please enter Technician Mail");
            fdNameId.requestFocus();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPDATEPROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("UpdateProfileLogin", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg = obj.getString("msg");
                            String Message = obj.getString("Message");
                            if (msg.equals("1")){
                                Intent intent = new Intent(getContext(), NavigationDrawerActivity.class);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                                Toast.makeText(getContext(),obj.getString("Message"),Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getContext(),obj.getString("Message"),Toast.LENGTH_SHORT).show();
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
                params.put("tech_id", techid);
                params.put("tech_name", techname);
                params.put("tech_father", techfather);
                params.put("tech_mail", techmail);
                Log.e("TechUpdate"," "+techid+" "+techname+" "+techfather+" "+techmail);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public class Kycdocument extends RecyclerView.Adapter<Kycdocument.ProductViewHolder> {
        private Context mCtx;
        private List<Kycsetget> kycsetgets;
        public Kycdocument(Context mCtx, List<Kycsetget> kycsetgets) {
            this.mCtx = mCtx;
            this.kycsetgets = kycsetgets;
        }
        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.kyc_documet, null);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ProductViewHolder holder, int position) {
            final Kycsetget kycsetget = kycsetgets.get(position);
            holder.documetnameIdd.setText(kycsetget.getDocname());
            holder.documetnoIdd.setText(kycsetget.getDocno());
            holder.trueId.setText(kycsetget.getDocfound());
            holder.filename.setText(kycsetget.getFilename());
            if (kycsetget.getDocfound().equals("true")){
                holder.icon.setVisibility(View.VISIBLE);
            }
            else {
                holder.icon.setVisibility(View.GONE);
            }
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = holder.filename.getText().toString();
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
//                    Intent intent = new Intent(getContext(), DocumetViewActivity.class);
//                    intent.putExtra("url",url);
//                    startActivity(intent);
//                    getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);

                }
            });
        }
        @Override
        public int getItemCount() {
            return  kycsetgets.size();
        }
        class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView documetnameIdd,documetnoIdd,trueId,filename;
            ImageView icon;
            public ProductViewHolder(View itemView) {
                super(itemView);
                documetnameIdd =(TextView)itemView.findViewById(R.id.documetnameIdd);
                documetnoIdd = (TextView)itemView.findViewById(R.id.documetnoIdd);
                trueId = (TextView)itemView.findViewById(R.id.trueId);
                filename = (TextView)itemView.findViewById(R.id.filename);
                icon = (ImageView)itemView.findViewById(R.id.icon);
            }
        }}
    public class qulification extends RecyclerView.Adapter<qulification.ProductViewHolder> {
        private Context mCtx;
        private List<Qulificationsetget> qulificationsetgets;
        public qulification(Context mCtx, List<Qulificationsetget> qulificationsetgets) {
            this.mCtx = mCtx;
            this.qulificationsetgets = qulificationsetgets;
        }
        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.qulificationlist, null);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            final Qulificationsetget qulificationsetget = qulificationsetgets.get(position);
            holder.examIdd.setText(qulificationsetget.getExamname());
            holder.boardIdd.setText(qulificationsetget.getBoardname());
            holder.yearIdd.setText(qulificationsetget.getExamyear());
            holder.persenIdd.setText(qulificationsetget.getExamprcnt());
            holder.trueId.setText(qulificationsetget.getEducationfound());
        }
        @Override
        public int getItemCount() {
            return  qulificationsetgets.size();
        }
        class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView examIdd,boardIdd,yearIdd,persenIdd,trueId;
            public ProductViewHolder(View itemView) {
                super(itemView);
                examIdd =(TextView)itemView.findViewById(R.id.examIdd);
                boardIdd = (TextView)itemView.findViewById(R.id.boardIdd);
                yearIdd = (TextView)itemView.findViewById(R.id.yearIdd);
                persenIdd = (TextView)itemView.findViewById(R.id.persenIdd);
                trueId = (TextView)itemView.findViewById(R.id.trueId);
            }
        }}

    public class ProductSpsification extends RecyclerView.Adapter<ProductSpsification.ProductViewHolder> {
        private Context mCtx;
        private List<ProductSpecification> productSpecificationList;
        public ProductSpsification(Context mCtx, List<ProductSpecification> productSpecificationList) {
            this.mCtx = mCtx;
            this.productSpecificationList = productSpecificationList;
        }
        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.product_spacification, null);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            final ProductSpecification productSpecification = productSpecificationList.get(position);
            holder.itemname.setText(productSpecification.getProduct());
            Glide.with(getContext())
                    .load(productSpecification.getImg())
                    .into(holder.imageItem);
        }
        @Override
        public int getItemCount() {
            return  productSpecificationList.size();
        }
        class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView itemname;
            ImageView imageItem;
            public ProductViewHolder(View itemView) {
                super(itemView);
                itemname = (TextView)itemView.findViewById(R.id.itemname);
                imageItem = (ImageView)itemView.findViewById(R.id.imageItem);
            }
        }}


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //    @Override
//    public void onDetach() {
//        super.onDetach();
//
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        backButtonHandler.removeBackClickListener(this);
        backButtonHandler = null;
    }
    @Override
    public boolean onBackClick() {
        Intent intent_info = new Intent(getContext(),NavigationDrawerActivity.class);
        startActivity(intent_info);
        getActivity().overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
        return true;
    }
}

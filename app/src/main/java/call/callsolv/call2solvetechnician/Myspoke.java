package call.callsolv.call2solvetechnician;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


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

import call.callsolv.call2solvetechnician.SetGetActivity.ContactpersonSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.DistricpinSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.Hubdetailssetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ProductSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Myspoke extends Fragment implements OnBackClickListener{
    RecyclerView recyclerView,recyclerId,recyclerViewproductId;
    List<ContactpersonSetGet> contactpersonSetGets;
    List<DistricpinSetget> districpinSetgets;
    List<ProductSetget> productSetgets;
    String techid;
    TextView nameId,sptype,addressID;
    MySpok adapter;
    DistricPin adap;
    product productadapter;
    ArrayList<ContactpersonSetGet> arrlist = new ArrayList<ContactpersonSetGet>();
    ArrayList<DistricpinSetget> arrlist1 = new ArrayList<DistricpinSetget>();
    ArrayList<ProductSetget> arrlist2 = new ArrayList<ProductSetget>();
    private BackButtonHandlerInterface backButtonHandler;
    boolean doubleBackToExitPressedOnce = false;
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
        View rootView = inflater.inflate(R.layout.my_spokes, container, false);
        recyclerView =(RecyclerView)rootView.findViewById(R.id.recyclerView);
        nameId =(TextView)rootView.findViewById(R.id.nameId);
        sptype =(TextView)rootView.findViewById(R.id.sptype);
        addressID =(TextView)rootView.findViewById(R.id.addressID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerId =(RecyclerView)rootView.findViewById(R.id.recyclerId);
        recyclerId.setHasFixedSize(true);
        recyclerId.setLayoutManager(new LinearLayoutManager(getContext()));
        contactpersonSetGets = new ArrayList<>();
        districpinSetgets = new ArrayList<>();
        recyclerViewproductId =(RecyclerView)rootView.findViewById(R.id.recyclerViewproductId);
        recyclerViewproductId.setHasFixedSize(true);
       // recyclerViewproductId.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),4);
        recyclerViewproductId.setLayoutManager(layoutManager);
        productSetgets = new ArrayList<>();
//        hubdetailssetgets.add(new Hubdetailssetget(
//                "Subhasish Singha","Android Developer","9163752468","pinku.subhasish@gmail.com"
//        ));
//
//        hubdetailssetgets.add(new Hubdetailssetget(
//                "Sidhertha paul","Php Developer","9163752468","pinku.subhasish@gmail.com"
//        ));
//        districpinSetgets = new ArrayList<>();
//        districpinSetgets.add(new DistricpinSetget(
//                "P.O. : Ghoshpara (711227)"
//        ));

        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        spokdetails(techid);
        return rootView;
    }

    public void spokdetails(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SPOKEDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SPOKDETAILS", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String spokename = obj.getString("spoke_name");
                            String spokecode = obj.getString("spoke_code");
                            String spoketyp = obj.getString("spoke_typ");
                            String spokeadrs = obj.getString("spoke_adrs");
                            nameId.setText(spokename+" "+"("+spokecode+")");
                            sptype.setText(spoketyp);
                            addressID.setText(spokeadrs);
                            JSONArray jsonArray = obj.getJSONArray("contact_person");
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String spokecntctname = jsonObject.getString("spoke_cntct_name");
                                String spokecntctdesg = jsonObject.getString("spoke_cntct_desg");
                                String spokecntctphn = jsonObject.getString("spoke_cntct_phn");
                                String spokecntctmail = jsonObject.getString("spoke_cntct_mail");
                                contactpersonSetGets.add(new ContactpersonSetGet(
                                        jsonObject.getString("spoke_cntct_name"),
                                        jsonObject.getString("spoke_cntct_desg"),
                                        jsonObject.getString("spoke_cntct_phn"),
                                        jsonObject.getString("spoke_cntct_mail")
                                ));
                                arrlist.addAll(contactpersonSetGets);
                                adapter = new MySpok(getContext(), contactpersonSetGets);
                                recyclerView.setAdapter(adapter);
                            }
                            JSONArray jsonArray1 = obj.getJSONArray("pincode");
                            for (int j = 0; j<jsonArray1.length(); j++){
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                String area = jsonObject1.getString("area");
                                String pincode = jsonObject1.getString("pin_code");
                                districpinSetgets.add(new DistricpinSetget(
                                        jsonObject1.getString("area"),
                                        jsonObject1.getString("pin_code")
                                ));
                                arrlist1.addAll(districpinSetgets);
                                adap = new DistricPin(getContext(), districpinSetgets);
                                recyclerId.setAdapter(adap);
                            }

                            JSONArray jsonArray2 = obj.getJSONArray("product");
                            for (int k = 0; k<jsonArray2.length(); k++){
                                JSONObject jsonObject2 = jsonArray2.getJSONObject(k);
                                String categoryname = jsonObject2.getString("category_name");
                                String productname = jsonObject2.getString("product_name");
                                String product_img = jsonObject2.getString("product_img");
                                Log.e("VVVVVVV",categoryname+" "+productname);
                                productSetgets.add(new ProductSetget(
                                        jsonObject2.getString("category_name"),
                                        jsonObject2.getString("product_name"),
                                        jsonObject2.getString("product_img")
                                ));
                                arrlist2.addAll(productSetgets);
                                productadapter = new product(getContext(), productSetgets);
                                recyclerViewproductId.setAdapter(productadapter);
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
                Log.e("SPOKVIEW"," "+techid+" ");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public class MySpok extends RecyclerView.Adapter<MySpok.ViewHolder> {
        private Context mCtx;
        String customerphone;
        private List<ContactpersonSetGet> contactpersonSetGets;
        public MySpok(Context mCtx, List<ContactpersonSetGet> contactpersonSetGets) {
            this.mCtx = mCtx;
            this.contactpersonSetGets = contactpersonSetGets;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.my_hubdeatails_view, null);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            ContactpersonSetGet contactpersonSetGet = contactpersonSetGets.get(position);
            holder.userId.setText(contactpersonSetGet.getSpokecntctname());
            holder.desigId.setText(contactpersonSetGet.getSpokecntctdesg());
            holder.poneId.setText(contactpersonSetGet.getSpokecntctphn());
            holder.emailId.setText(contactpersonSetGet.getSpokecntctmail());
            holder.juestId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customerphone = holder.poneId.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customerphone, null));
                    startActivity(intent);
                }
            });
            holder.callnowId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customerphone = holder.poneId.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customerphone, null));
                    startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return contactpersonSetGets.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView userId,desigId,poneId,emailId,callnowId;
            ImageView juestId;
            public ViewHolder(View itemView) {
                super(itemView);
                userId = itemView.findViewById(R.id.userId);
                desigId = itemView.findViewById(R.id.desigId);
                poneId = itemView.findViewById(R.id.poneId);
                emailId = itemView.findViewById(R.id.emailId);
                juestId = itemView.findViewById(R.id.juestId);
                callnowId = itemView.findViewById(R.id.callnowId);
            }
        }
    }

    public class DistricPin extends RecyclerView.Adapter<DistricPin.ViewHolder>{
        private Context mCtx;
        private List<DistricpinSetget> districpinSetgets;
        public DistricPin(Context mCtx, List<DistricpinSetget> districpinSetgets) {
            this.mCtx = mCtx;
            this.districpinSetgets = districpinSetgets;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.districpin_view, null);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DistricpinSetget districpinSetget = districpinSetgets.get(position);
            holder.ckId.setText(districpinSetget.getArea());
            holder.pincodeId.setText("Pin Code-"+" "+districpinSetget.getCheckname());
        }
        @Override
        public int getItemCount() {
            return districpinSetgets.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            TextView ckId,pincodeId;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ckId = itemView.findViewById(R.id.ckId);
                pincodeId = itemView.findViewById(R.id.pincodeId);
            }
        }

    }

    public class product extends RecyclerView.Adapter<product.ViewHolder>{
        private Context mCtx;
        private List<ProductSetget> productSetgets;
        public product(Context mCtx, List<ProductSetget> productSetgets) {
            this.mCtx = mCtx;
            this.productSetgets = productSetgets;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.spock_product_tableview, null);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProductSetget productSetget = productSetgets.get(position);
            holder.itemname.setText(productSetget.getProductname());
            holder.productId.setText(productSetget.getProductname());
            Glide.with(getContext())
                    .load(productSetget.getProductimage())
                    .into(holder.imageItem);
        }
        @Override
        public int getItemCount() {
            return productSetgets.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            TextView itemname,productId;
            ImageView imageItem;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemname = itemView.findViewById(R.id.itemname);
                productId = itemView.findViewById(R.id.productId);
                imageItem = itemView.findViewById(R.id.imageItem);
            }
        }

    }
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

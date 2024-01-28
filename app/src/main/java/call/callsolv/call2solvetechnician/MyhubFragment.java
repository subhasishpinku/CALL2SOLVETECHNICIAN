package call.callsolv.call2solvetechnician;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import call.callsolv.call2solvetechnician.SetGetActivity.Hubdetailssetget;
import call.callsolv.call2solvetechnician.SetGetActivity.PendingSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyhubFragment extends Fragment implements OnBackClickListener{
    RecyclerView recyclerView;
    List<Hubdetailssetget> hubdetailssetgets;
    ArrayList<Hubdetailssetget> arrlist = new ArrayList<Hubdetailssetget>();
    MyHub adapter;
    String techid;
    TextView hubnameID,addressID;
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
        View rootView = inflater.inflate(R.layout.my_hubdeatails, container, false);
        recyclerView =(RecyclerView)rootView.findViewById(R.id.recyclerView);
        hubnameID = (TextView)rootView.findViewById(R.id.hubnameID);
        addressID = (TextView)rootView.findViewById(R.id.addressID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        hubdetailssetgets = new ArrayList<>();
//        hubdetailssetgets.add(new Hubdetailssetget(
//                "Subhasish Singha","Android Developer","9163752468","pinku.subhasish@gmail.com"
//        ));
//        hubdetailssetgets.add(new Hubdetailssetget(
//                "Sidhertha paul","Php Developer","9163752468","pinku.subhasish@gmail.com"
//        ));
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        hubdeatils(techid);
        return rootView;
    }
    public class MyHub extends RecyclerView.Adapter<MyHub.ProductViewHolder> {
        private Context mCtx;
        String customerphone;
        private List<Hubdetailssetget> hubdetailssetgets;
        public MyHub(Context mCtx, List<Hubdetailssetget> hubdetailssetgets) {
            this.mCtx = mCtx;
            this.hubdetailssetgets = hubdetailssetgets;
        }
        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.my_hubdeatails_view, null);
            return new ProductViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ProductViewHolder holder, int position) {
            Hubdetailssetget hubdetailssetget = hubdetailssetgets.get(position);
            holder.userId.setText(hubdetailssetget.getHubcntctname());
            holder.desigId.setText(hubdetailssetget.getHubcntctdesg());
            holder.poneId.setText(hubdetailssetget.getHubcntctphn());
            holder.emailId.setText(hubdetailssetget.getHubcntctmail());
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
            return hubdetailssetgets.size();
        }
        class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView userId,desigId,poneId,emailId,callnowId;
            ImageView juestId;
            public ProductViewHolder(View itemView) {
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
    public void hubdeatils(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.HUBDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HUBDETAILS", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String hubname = obj.getString("hub_name");
                            String hubcode = obj.getString("hub_code");
                            String hubadrs = obj.getString("hub_adrs");
                            hubnameID.setText(hubname+" "+"("+hubcode+")");
                            addressID.setText(hubadrs);
                            JSONArray jsonArray = obj.getJSONArray("contact_person");
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String hubcntctname = jsonObject.getString("hub_cntct_name");
                                String hubcntctdesg = jsonObject.getString("hub_cntct_desg");
                                String hubcntctphn = jsonObject.getString("hub_cntct_phn");
                                String hubcntctmail = jsonObject.getString("hub_cntct_mail");
                                hubdetailssetgets.add(new Hubdetailssetget(
                                        jsonObject.getString("hub_cntct_name"),
                                        jsonObject.getString("hub_cntct_desg"),
                                        jsonObject.getString("hub_cntct_phn"),
                                        jsonObject.getString("hub_cntct_mail")
                                ));
                                arrlist.addAll(hubdetailssetgets);
                                adapter = new MyHub(getContext(), hubdetailssetgets);
                                recyclerView.setAdapter(adapter);
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
                params.put("tech_id",techid);
                Log.e("HUBVIEW"," "+techid+" ");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

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

package call.callsolv.call2solvetechnician;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


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

import call.callsolv.call2solvetechnician.SetGetActivity.AcceptedSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.PendingSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PandingCall extends Fragment implements OnBackClickListener{
    List<PendingSetGet> pendingSetGets;
    ArrayList<PendingSetGet> arrlist = new ArrayList<PendingSetGet>();
    RecyclerView recyclerView;
    String techid;
    Pandingadpter adapter;
    AlertDialog.Builder builder;
    private AlertDialog alertDialog = null;
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
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e("TABB","00");
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
            techid = String.valueOf(profileSetGet.getTechid());
            pendingcalllist(techid);

        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.call_panding, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pendingSetGets = new ArrayList<>();
        return rootView;
    }
    public void alarm(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TOTALCAKLLCOUNTALAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TotalCallCount2", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                           int totpendingcall1 = obj.getInt("res");

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
                params.put("flag","2");
                Log.e("Tech",techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void pendingcalllist(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PENDINGCALLLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("pendingcalllist", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("pndng_call");
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String bookcode = jsonObject.getString("book_code");
                                String bookrowid = jsonObject.getString("book_row_id");
                                String prdctname = jsonObject.getString("prdct_name");
                                String prdcticon = jsonObject.getString("prdct_icon");
                                String customername = jsonObject.getString("customer_name");
                                String customerphn = jsonObject.getString("customer_phn");
                                String customeradrs = jsonObject.getString("customer_adrs");
                                String timeage = jsonObject.getString("time_age");
                                String asigndate = jsonObject.getString("asign_date");
                                String asigntime = jsonObject.getString("asign_time");
                                Log.e("PENDINGCALLLIST", " "+bookcode+" "+bookrowid+" "+
                                        prdctname+" "+prdcticon+" "+customername+" "+
                                        customerphn+" "+customeradrs+" "+timeage+" "+asigndate+" "+asigntime);
                                pendingSetGets.add(new PendingSetGet(
                                        jsonObject.getString("book_code"),
                                        jsonObject.getString("book_row_id"),
                                        jsonObject.getString("prdct_name"),
                                        jsonObject.getString("prdct_icon"),
                                        jsonObject.getString("customer_name"),
                                        jsonObject.getString("customer_phn"),
                                        jsonObject.getString("customer_adrs"),
                                        jsonObject.getString("time_age"),
                                        jsonObject.getString("asign_date"),
                                        jsonObject.getString("asign_time")
                                ));
                            }
                            arrlist.addAll(pendingSetGets);
                            adapter = new Pandingadpter(getContext(), pendingSetGets);
                            recyclerView.setAdapter(adapter);
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
    public class Pandingadpter extends RecyclerView.Adapter<Pandingadpter.ProductViewHolder> {
        private Context mCtx;
        private List<PendingSetGet> pendingSetGets;
        String bId;
        int countt;
        public Pandingadpter(Context mCtx, List<PendingSetGet> pendingSetGets) {
            this.mCtx = mCtx;
            this.pendingSetGets = pendingSetGets;
        }
        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.pandingcall_view, null);
            return new ProductViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ProductViewHolder holder, int position) {
            final PendingSetGet pendingSetGet = pendingSetGets.get(position);
            holder.nameId.setText(String.valueOf(pendingSetGet.getPrdctname()+" "+"("+pendingSetGet.getBookcode()+")"));
            holder.userId.setText(String.valueOf(pendingSetGet.getCustomername()));
            holder.phoneId.setText(String.valueOf(pendingSetGet.getCustomerphn()));
            holder.locationId.setText(String.valueOf(pendingSetGet.getCustomeradrs()));
            holder.hourtimeId.setText(String.valueOf(pendingSetGet.getTimeage()));
            holder.bookId.setText(String.valueOf(pendingSetGet.getBookrowid()));
            holder.dateId.setText(pendingSetGet.getAsigndate());
            holder.timeId.setText(pendingSetGet.getAsigntime());
            Glide.with(mCtx)
                   .load(pendingSetGet.getPrdcticon())
                   .into(holder.imgID);
          //  holder.imgID.setImageDrawable(mCtx.getResources().getDrawable(pendingSetGet.getImage()));
            holder.callId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               // Intent intent = new Intent(getContext(),Routemap_Activity.class);
               // startActivity(intent);
                    String phone = pendingSetGet.getCustomerphn();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            });
            holder.viewID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bId =  holder.bookId.getText().toString();
                    Intent intent = new Intent(getContext(), PendingviewActivity.class);
                    intent.putExtra("bId",bId);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                }
            });
            holder.btncallId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = pendingSetGet.getCustomerphn();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            });
            holder.btndetailsId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bId =  holder.bookId.getText().toString();
                    Intent intent = new Intent(getContext(), PendingviewActivity.class);
                    intent.putExtra("bId",bId);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);

                }
            });
            for (int i = 0; i<pendingSetGets.size(); i++)
            {
               // Log.e("Listsize"," "+Integer.toString(position+1));
                countt = position+1;
                Log.e("Listsize"," "+countt);

            }
            int count = 1 ;            // LINE 1
            while ( count <= countt )  // LINE 2
            {
                Log.e( "Printing "," "+  count ) ; // LINE 3
                count++ ; // LINE 4
            }
        }
        @Override
        public int getItemCount() {

            return pendingSetGets.size();
        }
        class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView nameId,userId,phoneId,locationId,hourtimeId,bookId,dateId,timeId;
            LinearLayout callId,viewID;
            ImageView imgID;
            Button btncallId,btndetailsId;
            public ProductViewHolder(View itemView) {
                super(itemView);
                nameId = itemView.findViewById(R.id.nameId);
                userId = itemView.findViewById(R.id.userId);
                phoneId = itemView.findViewById(R.id.phoneId);
                locationId = itemView.findViewById(R.id.locationId);
                imgID = itemView.findViewById(R.id.imgID);
                callId = itemView.findViewById(R.id.callId);
                viewID = itemView.findViewById(R.id.viewID);
                hourtimeId = itemView.findViewById(R.id.hourtimeId);
                bookId = itemView.findViewById(R.id.bookId);
                btncallId = itemView.findViewById(R.id.btncallId);
                btndetailsId = itemView.findViewById(R.id.btndetailsId);
                dateId = itemView.findViewById(R.id.dateId);
                timeId = itemView.findViewById(R.id.timeId);


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
        //Toast.makeText(getContext(), "Do not exit", Toast.LENGTH_SHORT).show();
        return true;
    }

}
package call.callsolv.call2solvetechnician;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


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
import call.callsolv.call2solvetechnician.SetGetActivity.Kycsetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
import pl.droidsonroids.gif.GifImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Acceptedcallfragment extends Fragment implements OnBackClickListener{
    List<AcceptedSetget> acceptedSetgets;
    ArrayList<AcceptedSetget> arrlist = new ArrayList<AcceptedSetget>();
    RecyclerView recyclerView;
    String techid;
    Acceptedadapter adapter;
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
            Log.e("TABB","11");
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.call_accepting, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        acceptedSetgets = new ArrayList<>();
//        acceptedSetgets.add(new AcceptedSetget(
//                "Microwave Oven Repair","Subhasish Singha","9163752468","Kolkata",R.mipmap.microwavegrey
//        ));
//        acceptedSetgets.add(new AcceptedSetget(
//                "Air Conditioner Repair","Bijoy Das","980404940","Kolkata",R.mipmap.ac_grey
//        ));
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        Log.e("TechId",techid);
        acceptedcalllist(techid);
        return rootView;
    }
    public void acceptedcalllist(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ACCEPETEDCALLLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("acceptcalllist", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("acpt_call");
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String bookcode = jsonObject.getString("book_code");
                                String bookrowid = jsonObject.getString("book_row_id");
                                String trackflag = jsonObject.getString("track_flag");
                                String prdctname = jsonObject.getString("prdct_name");
                                String prdcticon = jsonObject.getString("prdct_icon");
                                String customername = jsonObject.getString("customer_name");
                                String customerphn = jsonObject.getString("customer_phn");
                                String customeradrs = jsonObject.getString("customer_adrs");
                                String timeage = jsonObject.getString("time_age");
                                String startjob = jsonObject.getString("start_job");
                                String asigndate = jsonObject.getString("asign_date");
                                String asigntime = jsonObject.getString("asign_time");
                                Log.e("ACCEPTCALLLISTDETAIL", " "+bookcode+" "
                                        +bookrowid+" "+prdctname+" "+prdcticon+" "
                                        +customername+" "+customerphn+" "
                                        +customeradrs+" "+timeage+" "
                                        +trackflag+" "+startjob+" "+asigndate+" "+asigntime);
                                acceptedSetgets.add(new AcceptedSetget(
                                             jsonObject.getString("book_code"),
                                             jsonObject.getString("book_row_id"),
                                             jsonObject.getString("prdct_name"),
                                             jsonObject.getString("prdct_icon"),
                                             jsonObject.getString("customer_name"),
                                             jsonObject.getString("customer_phn"),
                                             jsonObject.getString("customer_adrs"),
                                             jsonObject.getString("time_age"),
                                             jsonObject.getString("start_job"),
                                             jsonObject.getString("track_flag"),
                                             jsonObject.getString("asign_date"),
                                             jsonObject.getString("asign_time")
                                ));
                                arrlist.addAll(acceptedSetgets);
                                adapter = new Acceptedadapter(getContext(), acceptedSetgets);
                                recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } }},
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
                Log.e("techid",techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public class Acceptedadapter extends RecyclerView.Adapter<Acceptedadapter.ProductViewHolder> {
        //this context we will use to inflate the layout
        private Context mCtx;
        //we are storing all the products in a list
        private List<AcceptedSetget> acceptedSetgets;
        //getting the context and product list with constructor
        String customerphone,bId,startjob,trackidjob,bookid;
        ObjectAnimator textColorAnim;
        public Acceptedadapter(Context mCtx, List<AcceptedSetget> acceptedSetgets) {
            this.mCtx = mCtx;
            this.acceptedSetgets = acceptedSetgets;
        }
        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.accptedcall_view, null);
            return new ProductViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ProductViewHolder holder, int position) {
            //getting the product of the specified position
            AcceptedSetget acceptedSetgett = acceptedSetgets.get(position);
//           holder.imgID.setImageDrawable(mCtx.getResources().getDrawable(orderSetGet.getImgid()));
            Glide.with(mCtx)
                   .load(acceptedSetgett.getPrdcticon())
                   .into(holder.imgID);
            holder.nameId.setText(String.valueOf(acceptedSetgett.getPrdctname()+" "+"("+acceptedSetgett.getBookcode()+")"));
            holder.bokId.setText(acceptedSetgett.getBookcode());
            holder.userId.setText(acceptedSetgett.getCustomername());
            holder.phoneId.setText(acceptedSetgett.getCustomerphn());
            holder.locationId.setText(acceptedSetgett.getCustomeradrs());
            holder.hourtimeId.setText(acceptedSetgett.getTimeage());
            holder.bookId.setText(acceptedSetgett.getBookrowid());
            holder.statjobId.setText(acceptedSetgett.getStartjob());
            holder.trackid.setText(acceptedSetgett.getTrackflag());
            holder.dateId.setText(acceptedSetgett.getAsigndate());
            holder.timeId.setText(acceptedSetgett.getAsigntime());
            String FGTAG = acceptedSetgett.getTrackflag();
            if (FGTAG.equals("0")){
                holder.special.setVisibility(View.GONE);
            }
            else if (FGTAG.equals("1")){
                holder.special.setVisibility(View.VISIBLE);

            }
            else if (FGTAG.equals("2")){
                holder.special.setVisibility(View.GONE);

            }
            else if (FGTAG.equals("3")){
                holder.special.setVisibility(View.GONE);

            }
            else if (FGTAG.equals("4")){
                holder.special.setVisibility(View.GONE);

            }
  //            Glide.with(mCtx)
//                   .load(pendingSetGet.getImage())
//                   .into(holder.imgID);
            //holder.imgID.setImageDrawable(mCtx.getResources().getDrawable(acceptedSetgett.getImage()));
            holder.callId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customerphone = holder.phoneId.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customerphone, null));
                    startActivity(intent);
                }});
            holder.viewID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startjob = holder.statjobId.getText().toString();
//                    trackidjob = holder.trackid.getText().toString();
//                    Log.e("flag",trackidjob);
//                    if (trackidjob.equals("0")){
//                        bId =  holder.bookId.getText().toString();
//                        Intent intent = new Intent(getContext(), AcceptedviewActivity.class);
//                        intent.putExtra("bId",bId);
//                        intent.putExtra("trackidjob",trackidjob);
//                        startActivity(intent);
//                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
//                    }
//                    else if (trackidjob.equals("1")){
//                        bId =  holder.bookId.getText().toString();
//                        Intent intent = new Intent(getContext(), Updatebookingproductdetails.class);
//                        intent.putExtra("bId",bId);
//                        startActivity(intent);
//                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
//                    }
//                    else if (trackidjob.equals("2")){
//                        bId =  holder.bookId.getText().toString();
//                        Intent intent = new Intent(getContext(), AcceptedviewActivity.class);
//                        intent.putExtra("bId",bId);
//                        intent.putExtra("trackidjob",trackidjob);
//                        startActivity(intent);
//                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
//                    }
//                    else {
//
//                    }
                    startjob = holder.statjobId.getText().toString();
                    trackidjob = holder.trackid.getText().toString();
                    Log.e("flag",trackidjob);
                    if (trackidjob.equals("0")){
                        bId =  holder.bookId.getText().toString();
                        Intent intent = new Intent(getContext(), AcceptedviewActivity.class);
                        intent.putExtra("bId",bId);
                        intent.putExtra("trackidjob",trackidjob);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                    else if (trackidjob.equals("1")){
                        bId =  holder.bookId.getText().toString();
                        Intent intent = new Intent(getContext(), AcceptedviewActivity.class);
                        intent.putExtra("bId",bId);
                        intent.putExtra("trackidjob",trackidjob);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                    else if (trackidjob.equals("2")){
                        bId =  holder.bookId.getText().toString();
                        Intent intent = new Intent(getContext(), AcceptedviewActivity.class);
                        intent.putExtra("bId",bId);
                        intent.putExtra("trackidjob",trackidjob);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                    else if (trackidjob.equals("3")){
                        bId =  holder.bookId.getText().toString();
                        Intent intent = new Intent(getContext(), Updatebookingproductdetails.class);
                        intent.putExtra("bId",bId);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                    else if (trackidjob.equals("4")){
                        bId =  holder.bookId.getText().toString();
                        Intent intent = new Intent(getContext(), Updatebookingproductdetails.class);
                        intent.putExtra("bId",bId);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                    else {

                    }
                }});
            holder.btnCallId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customerphone = holder.phoneId.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customerphone, null));
                    startActivity(intent);
                }
            });
            holder.btnDetailsId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startjob = holder.statjobId.getText().toString();
                    trackidjob = holder.trackid.getText().toString();
                    Log.e("flag",trackidjob);
                    if (trackidjob.equals("0")){
                        bId =  holder.bookId.getText().toString();
                        Intent intent = new Intent(getContext(), AcceptedviewActivity.class);
                        intent.putExtra("bId",bId);
                        intent.putExtra("trackidjob",trackidjob);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                    else if (trackidjob.equals("1")){
                        bId =  holder.bookId.getText().toString();
                        Intent intent = new Intent(getContext(), AcceptedviewActivity.class);
                        intent.putExtra("bId",bId);
                        intent.putExtra("trackidjob",trackidjob);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                    else if (trackidjob.equals("2")){
                        bId =  holder.bookId.getText().toString();
                        Intent intent = new Intent(getContext(), AcceptedviewActivity.class);
                        intent.putExtra("bId",bId);
                        intent.putExtra("trackidjob",trackidjob);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                    else if (trackidjob.equals("3")){
                        bookid = holder.bokId.getText().toString();
                        bId =  holder.bookId.getText().toString();
                        Intent intent = new Intent(getContext(), Updatebookingproductdetails.class);
                        intent.putExtra("bId",bId);
                        intent.putExtra("bookid",bookid);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                    else if (trackidjob.equals("4")){
                        bookid = holder.bokId.getText().toString();
                        bId =  holder.bookId.getText().toString();
                        Intent intent = new Intent(getContext(), Updatebookingproductdetails.class);
                        intent.putExtra("bId",bId);
                        intent.putExtra("bookid",bookid);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                    else {

                    }
                }
            });
//            textColorAnim = ObjectAnimator.ofInt(holder.special, "textColor", Color.RED, Color.TRANSPARENT);
//            textColorAnim.setDuration(1000);
//            textColorAnim.setEvaluator(new ArgbEvaluator());
//            textColorAnim.setRepeatCount(ValueAnimator.INFINITE);
//            textColorAnim.setRepeatMode(ValueAnimator.REVERSE);
//            textColorAnim.start();
        }
        @Override
        public int getItemCount() {
            return acceptedSetgets.size();
        }
        class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView nameId,userId,phoneId,locationId,hourtimeId,bookId,statjobId,trackid,bokId,dateId,timeId;
            LinearLayout callId,viewID;
            ImageView imgID;
            Button btnCallId,btnDetailsId;
            //  CircularImageView PimgId;
            GifImageView special;
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
                statjobId = itemView.findViewById(R.id.statjobId);
                btnCallId = itemView.findViewById(R.id.btnCallId);
                btnDetailsId = itemView.findViewById(R.id.btnDetailsId);
                special = itemView.findViewById(R.id.special);
                trackid = itemView.findViewById(R.id.trackid);
                bokId = itemView.findViewById(R.id.bokId);
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

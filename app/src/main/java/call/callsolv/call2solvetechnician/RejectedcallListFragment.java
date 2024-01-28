package call.callsolv.call2solvetechnician;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
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

import call.callsolv.call2solvetechnician.SetGetActivity.AcceptedSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ComplitedcalllistSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.RejectcallListSetGet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RejectedcallListFragment extends Fragment implements View.OnClickListener,OnBackClickListener{
    List<RejectcallListSetGet> rejectcallListSetGets;
    ImageView searchID,searchIDD;
    TextView FdateId,TdateId;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendarr;
    DatePickerDialog.OnDateSetListener datee;
    Button findID;
    String startdate,enddate,techid;
    RecyclerView recyclerView;
    RejectCallAdapter adapter;
    ArrayList<RejectcallListSetGet> arrlist = new ArrayList<RejectcallListSetGet>();
    private BackButtonHandlerInterface backButtonHandler;
    boolean doubleBackToExitPressedOnce = false;
    SimpleDateFormat currentdate;
    String curdate;
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
        View rootView = inflater.inflate(R.layout.rejectcall_list_fragment_view, container, false);
        searchID = (ImageView)rootView.findViewById(R.id.searchID);
        searchID.setOnClickListener(this);
        searchIDD = (ImageView)rootView.findViewById(R.id.searchIDD);
        searchIDD.setOnClickListener(this);
        FdateId = (TextView) rootView.findViewById(R.id.FdateId);
        TdateId = (TextView) rootView.findViewById(R.id.TdateId);
        findID =(Button)rootView.findViewById(R.id.findID);
        findID.setOnClickListener(this);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rejectcallListSetGets = new ArrayList<>();
//        rejectcallListSetGets.add(new RejectcallListSetGet(
//                R.mipmap.microwavegrey,"Microwave Oven Repair","Friday, 23rd April 2019 at 10:30am","Bijoy Roy","923145684","Sodhpur","200"
//        ));
//        rejectcallListSetGets.add(new RejectcallListSetGet(
//                R.mipmap.ac_grey,"Air Conditioner Repair","Friday, 24rd April 2019 at 10:30am","Sujoy Roy","923145684","Sodhpur","200"
//        ));

        myCalendar = Calendar.getInstance();
        myCalendarr = Calendar.getInstance();
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
        datee = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarr.set(Calendar.YEAR, year);
                myCalendarr.set(Calendar.MONTH, monthOfYear);
                myCalendarr.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Todate();
            }
        };
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        return rootView;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchID:
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.searchIDD:
                new DatePickerDialog(getContext(), datee, myCalendarr
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendarr.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.findID:
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                currentdate = new SimpleDateFormat("dd-MM-yyyy");
                curdate = String.valueOf(currentdate);
                if (FdateId.getText().toString().equals("From Date") || TdateId.getText().toString().equals("To Date")){
                    Toast.makeText(getContext(),"Select Date",Toast.LENGTH_SHORT).show();
                }
                else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date strDate = null;
                    Date strDatee = null;
                    try {
                        strDate = sdf.parse(FdateId.getText().toString());
                        strDatee = sdf.parse(TdateId.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (curdate.equals(FdateId.getText().toString()) || new Date().after(strDate)) {
//                        catalog_outdated = 1;
                        if (curdate.equals(TdateId.getText().toString()) || new Date().after(strDatee)) {
                            rejectcallListSetGets.clear();
                            RejectedCall(FdateId.getText().toString(),TdateId.getText().toString(),techid);
                            rejectcallListSetGets.clear();
                        }
                        else {
                            Toast.makeText(getContext(),"No Valid To Date",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(),"No Valid From Date",Toast.LENGTH_SHORT).show();
                    }

                }
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
    private void Todate() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        TdateId.setText(sdf.format(myCalendarr.getTime()));
        enddate = TdateId.getText().toString();
    }
    public void  RejectedCall(final String startdate,final String enddate,final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.REJECTCALLLISTJOB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("REJECTCALLLIVIEW", response);
                        if (response.equals("null")){
                            Log.e("CALLLISTVIEW", "0");
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(RejectedcallListFragment.this).attach(RejectedcallListFragment.this).commit();
                            //progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("rjct_cl");
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String sl = jsonObject.getString("sl");
                                String bid = jsonObject.getString("b_id");
                                String bookid = jsonObject.getString("book_id");
                                String calldatetime = jsonObject.getString("call_date_time");
                                String srvcchrg = jsonObject.getString("srvc_chrg");
                                String cncldt = jsonObject.getString("cncl_dt");
                                String cnclresn = jsonObject.getString("cncl_resn");
                                String chrgtypname = jsonObject.getString("chrg_typ_name");
                                Log.e("REJECTCALLLIVIEWLOGLOG", " "+sl+" "+bid+" "+bookid+" "+calldatetime+" "+srvcchrg+" "+cncldt+" "+cnclresn+" "+chrgtypname);
                                rejectcallListSetGets.add(new RejectcallListSetGet(
                                        jsonObject.getString("sl"),
                                        jsonObject.getString("b_id"),
                                        jsonObject.getString("book_id"),
                                        jsonObject.getString("call_date_time"),
                                        jsonObject.getString("srvc_chrg"),
                                        jsonObject.getString("cncl_dt"),
                                        jsonObject.getString("cncl_resn"),
                                        jsonObject.getString("chrg_typ_name")
                                ));
                                arrlist.addAll(rejectcallListSetGets);
                                adapter = new RejectCallAdapter(getContext(), rejectcallListSetGets);
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
                params.put("start_dt",startdate);
                params.put("end_dt",enddate);
                params.put("tech_id", techid);
                Log.e("REJECTCALLLISTJOB", " "+startdate+" "+enddate+" "+techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public class RejectCallAdapter extends RecyclerView.Adapter<RejectCallAdapter.ViewHolder> {
        private Context mCtx;
        private List<RejectcallListSetGet> rejectcallListSetGets;
        public RejectCallAdapter(Context mCtx, List<RejectcallListSetGet> rejectcallListSetGets) {
            this.mCtx = mCtx;
            this.rejectcallListSetGets = rejectcallListSetGets;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
          //  View view = inflater.inflate(R.layout.rejectcall_list_fragment, null);
            View view = inflater.inflate(R.layout.rejectcall_list_fragmentt, null);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RejectcallListSetGet rejectcallListSetGet = rejectcallListSetGets.get(position);
//            holder.nameId.setText(rejectcallListSetGet.getPname());
//            holder.time.setText(rejectcallListSetGet.getdName());
//            holder.userId.setText(rejectcallListSetGet.getUsername());
//            holder.phoneId.setText(rejectcallListSetGet.getPhone());
//            holder.locationId.setText(rejectcallListSetGet.getPlace());
//            holder.rupeesId.setText(rejectcallListSetGet.getAmount());
//            holder.imgID.setImageDrawable(mCtx.getResources().getDrawable(rejectcallListSetGet.getImage()));
//            final Animation animation = new AlphaAnimation((float) 0.5, 0);
//            animation.setDuration(500);
//            animation.setInterpolator(new LinearInterpolator());
//            animation.setRepeatCount(Animation.INFINITE);
//            animation.setRepeatMode(Animation.REVERSE);
//            holder.callId.startAnimation(animation);
//            holder.viewID.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(),RejectcalllistviewActivity.class);
//                    startActivity(intent);
//                }
//            });

            String jobid = rejectcallListSetGet.getBookid();
            holder.nameId.setText("JOB ID :"+" "+jobid);
          //  String amount = String.valueOf(rejectcallListSetGet.getSrvcchrg());
            holder.amountId.setText(rejectcallListSetGet.getSrvcchrg());
            holder.calldatetimeId.setText(rejectcallListSetGet.getCalldatetime());
            holder.jobasdateId.setText(rejectcallListSetGet.getCncldt());
            holder.timeasId.setText(rejectcallListSetGet.getCalldatetime());
            holder.serviceId.setText(rejectcallListSetGet.getChrgtypname());
            holder.bId.setText(rejectcallListSetGet.getBid());
            holder.resonId.setText(rejectcallListSetGet.getCnclresn());
            int totalPrice = 0;
            for (int i = 0; i<rejectcallListSetGets.size(); i++)
            {
                //totalPrice += rejectcallListSetGets.get(i).getSrvcchrg();
               // Log.e("SERVICETOTAL", " "+totalPrice);
               // Log.e("Listsize"," "+rejectcallListSetGets.size());
                holder.imgID.setText(Integer.toString(position+1));
               // totalprice = String.valueOf(totalPrice);
            }
          //  totalId.setText("TOTAL :"+totalprice);
        }
        @Override
        public int getItemCount() {
            return rejectcallListSetGets.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
//            TextView nameId,time,userId,phoneId,locationId,rupeesId;
//            ImageView imgID,callId;
//            Button viewID;
              TextView imgID,nameId,amountId,calldatetimeId,bId,jobasdateId,timeasId,serviceId,resonId;
            public ViewHolder(View itemView) {
                super(itemView);
//                imgID = itemView.findViewById(R.id.imgID);
//                callId = itemView.findViewById(R.id.callId);
//                nameId = itemView.findViewById(R.id.nameId);
//                time = itemView.findViewById(R.id.time);
//                userId = itemView.findViewById(R.id.userId);
//                phoneId = itemView.findViewById(R.id.phoneId);
//                locationId = itemView.findViewById(R.id.locationId);
//                rupeesId = itemView.findViewById(R.id.rupeesId);
//                viewID = itemView.findViewById(R.id.viewID);

                imgID = itemView.findViewById(R.id.imgID);
                nameId = itemView.findViewById(R.id.nameId);
                amountId = itemView.findViewById(R.id.amountId);
                calldatetimeId = itemView.findViewById(R.id.calldatetimeId);
                bId = itemView.findViewById(R.id.bId);
                jobasdateId = itemView.findViewById(R.id.jobasdateId);
                timeasId = itemView.findViewById(R.id.timeasId);
                serviceId = itemView.findViewById(R.id.serviceId);
                resonId = itemView.findViewById(R.id.resonId);
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

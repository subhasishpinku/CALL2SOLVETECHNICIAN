package call.callsolv.call2solvetechnician;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import call.callsolv.call2solvetechnician.SetGetActivity.ComplitedcalllistSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.Datacollectionsetget;
import call.callsolv.call2solvetechnician.SetGetActivity.MyexpendentSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.RejectcallListSetGet;


public class MyexpendentFragment extends Fragment implements View.OnClickListener,OnBackClickListener {
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendarr;
    DatePickerDialog.OnDateSetListener datee;
    ImageView searchID,searchIDD;
    TextView FdateId,TdateId;
    Button findID;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<MyexpendentSetget> myexpendentSetgets;
    String techid;
    String startdate,enddate;
    Myexpendent adapter;
    ArrayList<MyexpendentSetget> arrlist = new ArrayList<MyexpendentSetget>();
    private BackButtonHandlerInterface backButtonHandler;
    boolean doubleBackToExitPressedOnce = false;
    SimpleDateFormat currentdate;
    String curdate;
    TextView totalId;
    LinearLayout footer;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myexpendent_fragment, container, false);
        searchID = (ImageView)rootView.findViewById(R.id.searchID);
        searchID.setOnClickListener(this);
        searchIDD = (ImageView)rootView.findViewById(R.id.searchIDD);
        searchIDD.setOnClickListener(this);
        FdateId = (TextView) rootView.findViewById(R.id.FdateId);
        TdateId = (TextView) rootView.findViewById(R.id.TdateId);
        findID =(Button)rootView.findViewById(R.id.findID);
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        totalId = (TextView)rootView.findViewById(R.id.totalId);
        footer = (LinearLayout)rootView.findViewById(R.id.footer);
        footer.setVisibility(View.GONE);
        fab.setOnClickListener(this);
        findID.setOnClickListener(this);
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
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myexpendentSetgets = new ArrayList<>();
//        myexpendentSetgets.add(new MyexpendentSetget(
//             "1","JOB ID: 423622633","Friday, 23rd April 2019 at 10:30am","Demo1","Demo2","500"
//        ));
//
//        myexpendentSetgets.add(new MyexpendentSetget(
//                "2","JOB ID: 423622633","Sunday, 23rd April 2019 at 11:30am","Demo2","Demo3","600"
//        ));
//        adapter = new Myexpendent(getContext(),myexpendentSetgets);
//        recyclerView.setAdapter(adapter);
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
                            myexpendentSetgets.clear();
                            expendent(FdateId.getText().toString(),TdateId.getText().toString(),techid);
                            myexpendentSetgets.clear();
                        }
                        else {
                            Toast.makeText(getContext(),"No Valid To Date",Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(),"Invalid Date Range",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(),"No Valid From Date",Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(),"Invalid Date Range",Toast.LENGTH_SHORT).show();
                    }

                }

                break;
            case R.id.fab:
                Intent intent = new Intent(getContext(),AddexpendentActivity.class);
                startActivity(intent);

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
    public void  expendent(final String startdate,final String enddate,final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TECHEXPNDITR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("expendentview", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String exptot = obj.getString("exp_tot");
                            Log.e("TOTALAMOUNT"," "+exptot);
                            JSONArray jsonArray = obj.getJSONArray("exp");
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String sl = jsonObject.getString("sl");
                                String jobcode = jsonObject.getString("job_code");
                                String calldate = jsonObject.getString("call_date");
                                int amnt = jsonObject.getInt("amnt");
                                String expndfr = jsonObject.getString("expnd_fr");
                                Log.e("expendentlog"," "+sl+" "+jobcode+" "+" "+calldate+" "+amnt+" "+expndfr);
                                footer.setVisibility(View.VISIBLE);
                                myexpendentSetgets.add(new MyexpendentSetget(
                                        jsonObject.getString("sl"),
                                        jsonObject.getString("job_code"),
                                        jsonObject.getString("call_date"),
                                        jsonObject.getInt("amnt"),
                                        jsonObject.getString("expnd_fr")
                                ));
                                arrlist.addAll(myexpendentSetgets);
                                adapter = new Myexpendent(getContext(),myexpendentSetgets);
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
                params.put("strt_dt",startdate);
                params.put("end_dt",enddate);
                params.put("tech_id", techid);
                Log.e("expendentviewlog", " "+startdate+" "+enddate+" "+techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    public class Myexpendent extends RecyclerView.Adapter<Myexpendent.ViewHolder> {
        private Context mCtx;
        private List<MyexpendentSetget> myexpendentSetgets;
        int listview = -1;
        String totalprice;
        public Myexpendent(Context mCtx, List<MyexpendentSetget> myexpendentSetgets) {
            this.mCtx = mCtx;
            this.myexpendentSetgets = myexpendentSetgets;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.myexpendentview, null);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MyexpendentSetget myexpendentSetget = myexpendentSetgets.get(position);
            listview = position;
            //holder.imgID.setText(myexpendentSetget.getJobcode());
            String jobid = myexpendentSetget.getJobcode();
            holder.nameId.setText("JOB ID :"+" "+jobid);
            holder.time.setText(myexpendentSetget.getCalldate());
            holder.expendForId.setText(myexpendentSetget.getExpndfr());
            //holder.travelmodeId.setText(myexpendentSetget.getExpndfr());
            String amount = String.valueOf(myexpendentSetget.getAmnt());
            holder.amountID.setText(amount);

            int totalPrice = 0;
            for (int i = 0; i<myexpendentSetgets.size(); i++)
            {
                totalPrice += myexpendentSetgets.get(i).getAmnt();
                Log.e("SERVICETOTAL", " "+totalPrice);
                Log.e("Listsize"," "+myexpendentSetgets.size());
                holder.imgID.setText(Integer.toString(position+1));
                totalprice = String.valueOf(totalPrice);

            }
            totalId.setText("TOTAL :"+" "+totalprice);

        }
        @Override
        public int getItemCount() {
            return myexpendentSetgets.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView imgID,nameId,time,expendForId,travelmodeId,amountID;
            public ViewHolder(View itemView) {
                super(itemView);
                imgID = itemView.findViewById(R.id.imgID);
                nameId = itemView.findViewById(R.id.nameId);
                time = itemView.findViewById(R.id.time);
                expendForId = itemView.findViewById(R.id.expendForId);
                travelmodeId = itemView.findViewById(R.id.travelmodeId);
                amountID = itemView.findViewById(R.id.amountID);
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

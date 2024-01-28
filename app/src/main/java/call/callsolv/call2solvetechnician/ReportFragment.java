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
import android.widget.Button;
import android.widget.DatePicker;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import call.callsolv.call2solvetechnician.SetGetActivity.CallListviewSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.Reportsetget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReportFragment extends Fragment implements View.OnClickListener,OnBackClickListener{
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendarr;
    DatePickerDialog.OnDateSetListener datee;
    ImageView searchID,searchIDD;
    TextView FdateId,TdateId;
    Button findID;
    RecyclerView recyclerView;
    List<Reportsetget> reportsetgets;
    String startdate,enddate,techid;
    TextView assennameId,assenamountID,pendingnameId,pendingamountID,complitameId,complitamountID,rejectnameId,rejectamountID,priceID,performId;
    private BackButtonHandlerInterface backButtonHandler;
    boolean doubleBackToExitPressedOnce = false;
    SimpleDateFormat currentdate;
    String curdate;
    LinearLayout PerLvId;
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
        View rootView = inflater.inflate(R.layout.report_fragment, container, false);
        searchID = (ImageView)rootView.findViewById(R.id.searchID);
        searchID.setOnClickListener(this);
        searchIDD = (ImageView)rootView.findViewById(R.id.searchIDD);
        searchIDD.setOnClickListener(this);
        FdateId = (TextView) rootView.findViewById(R.id.FdateId);
        TdateId = (TextView) rootView.findViewById(R.id.TdateId);
        findID =(Button)rootView.findViewById(R.id.findID);
        assennameId = (TextView)rootView.findViewById(R.id.assennameId);
        assenamountID = (TextView)rootView.findViewById(R.id.assenamountID);
        pendingnameId = (TextView)rootView.findViewById(R.id.pendingnameId);
        pendingamountID = (TextView)rootView.findViewById(R.id.pendingamountID);
        complitameId = (TextView)rootView.findViewById(R.id.complitameId);
        complitamountID = (TextView)rootView.findViewById(R.id.complitamountID);
        rejectnameId = (TextView)rootView.findViewById(R.id.rejectnameId);
        rejectamountID = (TextView)rootView.findViewById(R.id.rejectamountID);
        priceID = (TextView)rootView.findViewById(R.id.priceID);
        performId = (TextView)rootView.findViewById(R.id.performId);
        PerLvId = (LinearLayout)rootView.findViewById(R.id.PerLvId);
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
//        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        reportsetgets = new ArrayList<>();
//        reportsetgets.add(new Reportsetget(
//                R.mipmap.assign," Assign Call","10"
//        ));
//        reportsetgets.add(new Reportsetget(
//                R.mipmap.pend,"Pending Call","20"
//        ));
//        reportsetgets.add(new Reportsetget(
//                R.mipmap.complete_call,"Completed Call","30"
//        ));
//        reportsetgets.add(new Reportsetget(
//                R.mipmap.rejected,"Rejected Call","40"
//        ));
//        ReportAdapter adapter = new ReportAdapter(getContext(),reportsetgets);
//        recyclerView.setAdapter(adapter);
        //  assennameId.setText("");
        PerLvId.setVisibility(View.GONE);
        assenamountID.setText("0");
        // pendingnameId.setText("");
        pendingamountID.setText("0");
        // complitameId.setText("");
        complitamountID.setText("0");
        // rejectnameId.setText("");
        rejectamountID.setText("0");
        priceID.setText("0");
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
                            REPORTVIEW(FdateId.getText().toString(), TdateId.getText().toString(), techid);
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

    public void REPORTVIEW(final String startdate,final String enddate,final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.REPORTJOB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("REPORTVIEW", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String asigncall = obj.getString("asign_call");
                            String compltcall = obj.getString("complt_call");
                            String rejectcall = obj.getString("reject_call");
                            String pendingcall = obj.getString("pending_call");
                            String perfrmnc = obj.getString("perfrmnc");
                            Log.e("REPORTVIEW"," "+asigncall+" "+compltcall+" "+rejectcall+" "+pendingcall+" "+perfrmnc);
                          //  assennameId.setText("");
                            assenamountID.setText(asigncall);
                           // pendingnameId.setText("");
                            pendingamountID.setText(pendingcall);
                           // complitameId.setText("");
                            complitamountID.setText(compltcall);
                           // rejectnameId.setText("");
                            rejectamountID.setText(rejectcall);
                            PerLvId.setVisibility(View.VISIBLE);
                            performId.setText("PERFORMANCE :"+" "+perfrmnc+"%");
                            priceID.setText("");
                            if (rejectcall.equals("null")) {

                            }
                            else {
                                String mysum = assenamountID.getText().toString();
                                final int mnum = Integer.parseInt(mysum);
                                String mysum2 = pendingamountID.getText().toString();
                                final int mnum2 = Integer.parseInt(mysum2);
                                String mysum3 = complitamountID.getText().toString();
                                final int mnum3 = Integer.parseInt(mysum3);
                                String mysum4 = rejectamountID.getText().toString();
                                final int mnum4 = Integer.parseInt(mysum4);
                                int res = mnum + mnum2 + mnum3 + mnum4;
                                String Sumofvalue = String.valueOf(res);
                                priceID.setText(Sumofvalue);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("start_dt", startdate);
                params.put("end_dt", enddate);
                params.put("tech_id", techid);
                Log.e("REPORTJOB", " "+startdate+" "+enddate+" "+techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
        private Context mCtx;
        private List<Reportsetget> reportsetgets;
        public ReportAdapter(Context mCtx, List<Reportsetget> reportsetgets) {
            this.mCtx = mCtx;
            this.reportsetgets = reportsetgets;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.report_view, null);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Reportsetget reportsetget = reportsetgets.get(position);
            holder.nameId.setText(reportsetget.getName());
            holder.amountID.setText(reportsetget.getTotal());
            holder.imgID.setImageDrawable(mCtx.getResources().getDrawable(reportsetget.getImage()));

        }
        @Override
        public int getItemCount() {
            return reportsetgets.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameId,amountID;
            ImageView imgID;
            public ViewHolder(View itemView) {
                super(itemView);
                imgID = itemView.findViewById(R.id.imgID);
                nameId = itemView.findViewById(R.id.nameId);
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

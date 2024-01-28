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
import android.widget.ProgressBar;
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

import call.callsolv.call2solvetechnician.SetGetActivity.CallListviewSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.Qulificationsetget;

public class CallListFragment extends Fragment implements  View.OnClickListener,OnBackClickListener{
    String techid;
    Calendar fromCalendar;
    DatePickerDialog.OnDateSetListener fromdate;
    Calendar toCalendar;
    DatePickerDialog.OnDateSetListener todate;
    Button searchID,searchIDD;
    String startdate,enddate;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    TextView f_date,t_date;
    ImageView ff_date,tt_date;
    String formattedDate3,day;
    List<CallListviewSetget> callListviewSetgets;
    ArrayList<CallListviewSetget> arrlist = new ArrayList<CallListviewSetget>();
    CalllistView calllistView;
    private BackButtonHandlerInterface backButtonHandler;
    boolean doubleBackToExitPressedOnce = false;
    String curdate;
    SimpleDateFormat currentdate;
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
        View rootView = inflater.inflate(R.layout.my_calllist, container, false);
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        f_date = (TextView)rootView.findViewById(R.id.f_date);
        t_date = (TextView)rootView.findViewById(R.id.t_date);
        ff_date =(ImageView)rootView.findViewById(R.id.ff_date);
        tt_date =(ImageView)rootView.findViewById(R.id.tt_date);
        searchID =(Button) rootView.findViewById(R.id.searchID);
        searchIDD =(Button)rootView.findViewById(R.id.searchIDD);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        searchID.setOnClickListener(this);
        searchIDD.setOnClickListener(this);
        ff_date.setOnClickListener(this);
        tt_date.setOnClickListener(this);
        callListviewSetgets = new ArrayList<>();
        fromCalendar = Calendar.getInstance();
        fromdate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                fromCalendar.set(Calendar.YEAR, year);
                fromCalendar.set(Calendar.MONTH, monthOfYear);
                fromCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                //   SearchList();

            }
        };
        toCalendar = Calendar.getInstance();
        todate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                toCalendar.set(Calendar.YEAR, year);
                toCalendar.set(Calendar.MONTH, monthOfYear);
                toCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelto();
                //  SearchList();

            }
        };
        dateTime();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }
    private void dateTime(){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df3 = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate3 = df3.format(c.getTime());
        SimpleDateFormat parseFormat = new SimpleDateFormat("EEEE");
        Date date =new Date();
        day= parseFormat.format(date);
      //  f_date.setText(formattedDate3);
      //  t_date.setText(formattedDate3);
        //CALLLISTVIEW(f_date.getText().toString(),f_date.getText().toString(),techid);
    }
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        f_date.setText(sdf.format(fromCalendar.getTime()));
        startdate = f_date.getText().toString();
       // CALLLISTVIEW(f_date.getText().toString(),f_date.getText().toString(),techid);
        callListviewSetgets.clear();

      }
      private void updateLabelto() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        t_date.setText(sdf.format(toCalendar.getTime()));
        enddate = t_date.getText().toString();
       // CALLLISTVIEW(f_date.getText().toString(),f_date.getText().toString(),techid);
        callListviewSetgets.clear();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ff_date:
                showdate();
                break;
            case R.id.tt_date:
                showdateto();
                break;
            case R.id.searchID:
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                currentdate = new SimpleDateFormat("dd-MM-yyyy");
                curdate = String.valueOf(currentdate);
                if (f_date.getText().toString().equals("From Date") || t_date.getText().toString().equals("To Date")){
                    Toast.makeText(getContext(),"Select Date",Toast.LENGTH_SHORT).show();
                }
                else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date strDate = null;
                    Date strDatee = null;
                    try {
                        strDate = sdf.parse(f_date.getText().toString());
                        strDatee = sdf.parse(t_date.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (curdate.equals(f_date.getText().toString()) || new Date().after(strDate)) {
//                        catalog_outdated = 1;
                        if (curdate.equals(t_date.getText().toString()) || new Date().after(strDatee)) {
                            callListviewSetgets.clear();
                            CALLLISTVIEW(f_date.getText().toString(),t_date.getText().toString(),techid);
                            callListviewSetgets.clear();
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
            case R.id.searchIDD:

                break;
            default:
        }
    }
    private void showdate(){
        new DatePickerDialog(getContext(), fromdate, fromCalendar
                .get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH),
                fromCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }
    private void showdateto(){
        new DatePickerDialog(getContext(), todate, toCalendar
                .get(Calendar.YEAR), toCalendar.get(Calendar.MONTH),
                toCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }
    public void CALLLISTVIEW(final String startdate,final String enddate,final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CALLLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CALLLISTVIEW", response);
                        if (response.equals("null")){
                            Log.e("CALLLISTVIEW", "0");
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(CallListFragment.this).attach(CallListFragment.this).commit();
                            //progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("call_list");
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //progressBar.setVisibility(View.GONE);
                                String slno = jsonObject.getString("sl_no");
                                String bid = jsonObject.getString("b_id");
                                String bookid = jsonObject.getString("book_id");
                                String calldatetime = jsonObject.getString("call_date_time");
                                String srvcchrg = jsonObject.getString("srvc_chrg");
                                String jobasigndt = jsonObject.getString("job_asign_dt");
                                String jobasigntime = jsonObject.getString("job_asign_time");
                                String chrgtypname = jsonObject.getString("chrg_typ_name");
                                Log.e("CALLLIST", " "+slno+" "+bid+" "+bookid+" "+calldatetime+" "+srvcchrg+" "+jobasigndt+" "+jobasigntime+" "+chrgtypname);
                                callListviewSetgets.add(new CallListviewSetget(
                                        jsonObject.getString("sl_no"),
                                        jsonObject.getString("b_id"),
                                        jsonObject.getString("book_id"),
                                        jsonObject.getString("call_date_time"),
                                        jsonObject.getString("srvc_chrg"),
                                        jsonObject.getString("job_asign_dt"),
                                        jsonObject.getString("job_asign_time"),
                                        jsonObject.getString("chrg_typ_name")
                                ));
                                arrlist.addAll(callListviewSetgets);
                                calllistView = new CalllistView(getContext(), callListviewSetgets);
                                recyclerView.setAdapter(calllistView);
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
                Log.e("STARTDATE", " "+startdate+" "+enddate+" "+techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public class CalllistView extends RecyclerView.Adapter<CalllistView.ProductViewHolder> {
        private Context mCtx;
        private List<CallListviewSetget> callListviewSetgets;
        String bIdd;
        public CalllistView(Context mCtx, List<CallListviewSetget> callListviewSetgets) {
            this.mCtx = mCtx;
            this.callListviewSetgets = callListviewSetgets;
        }
        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.calllistview, null);
            return new ProductViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ProductViewHolder holder, int position) {
            CallListviewSetget callListviewSetget = callListviewSetgets.get(position);
            holder.imgID.setText(callListviewSetget.getSlno());
            String jobid = callListviewSetget.getBookid();
            holder.nameId.setText("JOB ID :"+" "+jobid);
            holder.amountId.setText(callListviewSetget.getSrvcchrg());
            holder.calldatetimeId.setText(callListviewSetget.getCalldatetime());
            holder.jobasdateId.setText(callListviewSetget.getJobasigndt());
            holder.timeasId.setText(callListviewSetget.getJobasigntime());
            holder.serviceId.setText(callListviewSetget.getChrgtypname());
            holder.bId.setText(callListviewSetget.getBid());
            holder.viewID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bIdd =  holder.bId.getText().toString();
                    Intent intent = new Intent(getContext(), CallListview_Activity.class);
                    intent.putExtra("bIdd",bIdd);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                }
            });
            holder.viewIDD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bIdd =  holder.bId.getText().toString();
                    Intent intent = new Intent(getContext(), CallListview_Activity.class);
                    intent.putExtra("bIdd",bIdd);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);

                }
            });
        }
        @Override
        public int getItemCount() {
            return callListviewSetgets.size();

        }
        public void clear() {
            int size = callListviewSetgets.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    callListviewSetgets.remove(0);
                }

                notifyItemRangeRemoved(0, size);
            }
        }
        class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView imgID,nameId,amountId,calldatetimeId,jobasdateId,timeasId,serviceId,bId;
            LinearLayout viewID;
            Button viewIDD;
            public ProductViewHolder(View itemView) {
                super(itemView);
                imgID = itemView.findViewById(R.id.imgID);
                nameId = itemView.findViewById(R.id.nameId);
                amountId = itemView.findViewById(R.id.amountId);
                calldatetimeId = itemView.findViewById(R.id.calldatetimeId);
                jobasdateId = itemView.findViewById(R.id.jobasdateId);
                timeasId = itemView.findViewById(R.id.timeasId);
                serviceId = itemView.findViewById(R.id.serviceId);
                bId = itemView.findViewById(R.id.bId);
                viewID = itemView.findViewById(R.id.viewID);
                viewIDD = itemView.findViewById(R.id.viewIDD);
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
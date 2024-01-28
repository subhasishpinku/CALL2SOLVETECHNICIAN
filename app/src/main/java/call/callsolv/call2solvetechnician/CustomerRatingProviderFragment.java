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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
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
import call.callsolv.call2solvetechnician.SetGetActivity.CustomerRating;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.RejectcallListSetGet;

public class CustomerRatingProviderFragment extends Fragment implements OnBackClickListener,View.OnClickListener {
    ImageView searchID,searchIDD;
    TextView FdateId,TdateId;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendarr;
    DatePickerDialog.OnDateSetListener datee;
    Button findID;
    String startdate,enddate,techid;
    RecyclerView recyclerView;
    List<ComplitedcalllistSetget> complitedcalllistSetgets;
    float curRate;
    int count;
    ProgressBar progressBar;
    private BackButtonHandlerInterface backButtonHandler;
    boolean doubleBackToExitPressedOnce = false;
    Spinner providerId;
    String[] providervalue = { "Select", "Provided", "Not Provided"};
    String providername;
    String providerdata;
    List<CustomerRating> customerRatings;
    ArrayList<CustomerRating> arrlist = new ArrayList<CustomerRating>();
    Customerreating adapter;
    RadioGroup providercheckboxId;
    RadioButton providercheckId,nonproviderId;
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
        View rootView = inflater.inflate(R.layout.customer_rating_provider_fragment, container, false);
        searchID = (ImageView)rootView.findViewById(R.id.searchID);
        searchID.setOnClickListener(this);
        searchIDD = (ImageView)rootView.findViewById(R.id.searchIDD);
        searchIDD.setOnClickListener(this);
        FdateId = (TextView) rootView.findViewById(R.id.FdateId);
        TdateId = (TextView) rootView.findViewById(R.id.TdateId);
        findID =(Button)rootView.findViewById(R.id.findID);
        findID.setOnClickListener(this);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
       // providerId = (Spinner)rootView.findViewById(R.id.providerId);
        providercheckboxId = (RadioGroup)rootView.findViewById(R.id.providercheckboxId);
        providercheckId = (RadioButton)rootView.findViewById(R.id.providercheckId);
        nonproviderId = (RadioButton)rootView.findViewById(R.id.nonproviderId);
       // rate = "5.0";
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        customerRatings =new ArrayList<>();
//        providerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                providername  = providerId.getItemAtPosition(providerId.getSelectedItemPosition()).toString();
//                //Toast.makeText(getContext(),providername,Toast.LENGTH_LONG).show();
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        providerId.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, providervalue));

        providercheckboxId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.providercheckId){
                    providername = "p";
                    Log.e("providercheckboxId",providername);
                }
                else if (checkedId==R.id.nonproviderId){
                    providername = "np";
                    Log.e("providercheckboxId",providername);
                }
                else {
                    Toast.makeText(getActivity(), "No Selected",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
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
//                   if (providername.equals("Provided")) {
//                        providerdata = "p";
//                        customerRatings.clear();
//                        CUSTREATING(FdateId.getText().toString(), TdateId.getText().toString(), techid, providerdata);
//                        customerRatings.clear();
//                    }
//                    if (providername.equals("Not Provided")) {
//                        providerdata = "np";
//                        customerRatings.clear();
//                        CUSTREATING(FdateId.getText().toString(), TdateId.getText().toString(), techid, providerdata);
//                        customerRatings.clear();
//                    }

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
                            if (providercheckId.isChecked() || nonproviderId.isChecked()){
                                customerRatings.clear();
                                CUSTREATING(FdateId.getText().toString(), TdateId.getText().toString(), techid, providername);
                                customerRatings.clear();
                            }
                            else {
                                Toast.makeText(getContext(),"SELECT PROVIDER",Toast.LENGTH_SHORT).show();
                            }
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
    public void CUSTREATING(final String startdate,final String enddate,final String techid,final String ratingtype){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CUSTREATING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CUSTREATING", response);
                        if (response.equals("null")){
                            Log.e("CALLLISTVIEW", "0");
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(CustomerRatingProviderFragment.this).attach(CustomerRatingProviderFragment.this).commit();
                            //progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("rate");
                            for (int i =0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String sl = jsonObject.getString("sl");
                                String bid = jsonObject.getString("b_id");
                                String bookid = jsonObject.getString("book_id");
                                String rate = jsonObject.getString("rate");
                                String customername = jsonObject.getString("customer_name");
                                String customerphn = jsonObject.getString("customer_phn");
                                String productname = jsonObject.getString("product_name");
                                String jobprcsdt = jsonObject.getString("job_prcs_dt");
                                String chrgtype = jsonObject.getString("chrg_type");
                                Log.e("CUSTREATINGVIEW"," "+sl+" "+bid+" "+bookid+" "+rate+" "+customername+" "+customerphn+" "+productname+" "+jobprcsdt+" "+chrgtype);
                                customerRatings.add(new CustomerRating(
                                        jsonObject.getString("sl"),
                                        jsonObject.getString("b_id"),
                                        jsonObject.getString("book_id"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("customer_name"),
                                        jsonObject.getString("customer_phn"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("job_prcs_dt"),
                                        jsonObject.getString("chrg_type")
                                ));
                                arrlist.addAll(customerRatings);
                                adapter = new Customerreating(getContext(), customerRatings);
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

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("start_dt", startdate);
                params.put("end_dt", enddate);
                params.put("tech_id", techid);
                params.put("rtng_type", ratingtype);
                Log.e("CUSTREATING", " "+startdate+" "+enddate+" "+techid+" "+ratingtype);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    public class Customerreating extends RecyclerView.Adapter<Customerreating.ViewHolder> {
        private Context mCtx;
        private List<CustomerRating> customerRatings;
        String totalprice;
        String bIdd;
        public Customerreating(Context mCtx, List<CustomerRating> customerRatings) {
            this.mCtx = mCtx;
            this.customerRatings = customerRatings;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            // View view = inflater.inflate(R.layout.complited_calllist__fragment_view, null);
            View view = inflater.inflate(R.layout.customer_reating_provider, null);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            CustomerRating customerRating = customerRatings.get(position);
            String jobid = customerRating.getBookid();
            holder.jobcodeId.setText("JOB ID :"+" "+jobid);
            holder.rateId.setText(customerRating.getRate());
            holder.bId.setText(customerRating.getBid());
            holder.cusnameId.setText(customerRating.getCustomername());
            holder.cusnumberId.setText(customerRating.getCustomerphn());
            holder.servicenumId.setText(customerRating.getCustomerphn());
            holder.productId.setText(customerRating.getProductname());
            holder.serviceId.setText(customerRating.getChrgtype());
            holder.jobId.setText(customerRating.getJobprcsdt());
            int totalPrice = 0;
            for (int i = 0; i<customerRatings.size(); i++)
            {
                //totalPrice += myexpendentSetgets.get(i).getAmnt();
               // Log.e("SERVICETOTAL", " "+totalPrice);
               // Log.e("Listsize"," "+myexpendentSetgets.size());
                holder.imgID.setText(Integer.toString(position+1));
               // totalprice = String.valueOf(totalPrice);
            }
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
            String rate = customerRating.getRate();
            holder.ratingBar.setEnabled(false);
            holder.ratingBar.setStepSize(Float.parseFloat("0.5"));
            holder.ratingBar.setNumStars(5);
            holder.ratingBar.setRating(Float.parseFloat(rate));
           // holder.ratvalue.setText(""+holder.ratingBar.getRating());
        }
        @Override
        public int getItemCount() {
            return customerRatings.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView imgID,jobcodeId,rateId,bId,cusnameId,cusnumberId,servicenumId,productId,serviceId,jobId;
            LinearLayout viewID;
            Button viewIDD;
            RatingBar ratingBar;
            public ViewHolder(View itemView) {
                super(itemView);
                imgID = itemView.findViewById(R.id.imgID);
                jobcodeId = itemView.findViewById(R.id.jobcodeId);
                rateId = itemView.findViewById(R.id.rateId);
                bId = itemView.findViewById(R.id.bId);
                cusnameId = itemView.findViewById(R.id.cusnameId);
                cusnumberId = itemView.findViewById(R.id.cusnumberId);
                servicenumId = itemView.findViewById(R.id.servicenumId);
                productId = itemView.findViewById(R.id.productId);
                serviceId = itemView.findViewById(R.id.serviceId);
                jobId = itemView.findViewById(R.id.jobId);
                viewID = itemView.findViewById(R.id.viewID);
                viewIDD = itemView.findViewById(R.id.viewIDD);
                ratingBar =(RatingBar)itemView.findViewById(R.id.ratingBar);

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
    //@Override
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

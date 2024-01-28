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
import android.widget.ProgressBar;
import android.widget.RatingBar;
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

import call.callsolv.call2solvetechnician.SetGetActivity.CallListviewSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ComplitedcalllistSetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ComplitedCallListFreagment extends Fragment implements View.OnClickListener,OnBackClickListener{
    ImageView searchID,searchIDD;
    TextView FdateId,TdateId,printId,totalId;
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
    ComplitedCallAdapter adapter;
    ArrayList<ComplitedcalllistSetget> arrlist = new ArrayList<ComplitedcalllistSetget>();
    ProgressBar progressBar;
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
        View rootView = inflater.inflate(R.layout.complited_calllist__fragment, container, false);
        searchID = (ImageView)rootView.findViewById(R.id.searchID);
        searchID.setOnClickListener(this);
        searchIDD = (ImageView)rootView.findViewById(R.id.searchIDD);
        searchIDD.setOnClickListener(this);
        FdateId = (TextView) rootView.findViewById(R.id.FdateId);
        TdateId = (TextView) rootView.findViewById(R.id.TdateId);
        findID =(Button)rootView.findViewById(R.id.findID);
        printId =(TextView)rootView.findViewById(R.id.printId);
        totalId = (TextView)rootView.findViewById(R.id.totalId);
        printId.setVisibility(View.GONE);
        totalId.setVisibility(View.GONE);
        findID.setOnClickListener(this);
        printId.setOnClickListener(this);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        complitedcalllistSetgets =new ArrayList<>();
//        complitedcalllistSetgets.add(new ComplitedcalllistSetget(
//                R.mipmap.microwavegrey,"Microwave Oven Repair","Friday, 23rd April 2019 at 10:30am","Bijoy Roy","923145684","Sodhpur","Good"
//        ));
//
//        complitedcalllistSetgets.add(new ComplitedcalllistSetget(
//                R.mipmap.ac_grey,"Air Conditioner Repair","Friday, 24rd April 2019 at 10:30am","Amit Roy","9804044940","Sodhpur","Great"
//        ));
//
//        complitedcalllistSetgets.add(new ComplitedcalllistSetget(
//                R.mipmap.fridgegrey,"Refrigerator Repair","Friday, 25rd April 2019 at 10:30am","Chunku Ghosh","9163752468","Sayambazer","Need some improvement"
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
              Log.e("datttt",curdate);
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
                          complitedcalllistSetgets.clear();
                          COMPLETEDCALLLIST(FdateId.getText().toString(),TdateId.getText().toString(),techid);
                          complitedcalllistSetgets.clear();
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
          case R.id.printId:
              Calendar cc = Calendar.getInstance();
              System.out.println("Current time => " + cc.getTime());
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
                          String fromdate =FdateId.getText().toString();
                          String s[] = fromdate.split("-");
                          String fomdt = s[2]+"-"+s[1]+"-"+s[0];
                          String todate =TdateId.getText().toString();
                          String ss[] = todate.split("-");
                          String todt = ss[2]+"-"+ss[1]+"-"+ss[0];
                          Log.e("DATEFFF",fomdt+" "+" "+todt);
                          Intent intent = new Intent(getContext(), CompilitedcalllistWebview.class);
                          intent.putExtra("frmdate",fomdt);
                          intent.putExtra("todate",todt);
                          startActivity(intent);
                          getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
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
    public void COMPLETEDCALLLIST(final String startdate,final String enddate,final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.COMPLETEDCALLLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("COMPLETEDCALLLIST", response);
                        if (response.equals("null")){
                            Log.e("COMPLETEDCALLLIST", "0");
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ComplitedCallListFreagment.this).attach(ComplitedCallListFreagment.this).commit();
                            //progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("cmplt_cl");
                            for (int i =0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                               // progressBar.setVisibility(View.GONE);
                                String bid = jsonObject.getString("b_id");
                                String bookid = jsonObject.getString("book_id");
                                String calldatetime = jsonObject.getString("call_date_time");
                                int srvcchrg = jsonObject.getInt("srvc_chrg");
                                String jobcmpltdt = jsonObject.getString("job_cmplt_dt");
                                String jobcmplttm = jsonObject.getString("job_cmplt_tm");
                                String chrgtypname = jsonObject.getString("chrg_typ_name");
                                Log.e("COMPILITEDCALLLIST"," "+bid+" "+bookid+" "+calldatetime+" "+srvcchrg+" "+jobcmpltdt+" "+jobcmplttm+" "+chrgtypname);
                                printId.setVisibility(View.VISIBLE);
                                totalId.setVisibility(View.VISIBLE);
                                complitedcalllistSetgets.add(new ComplitedcalllistSetget(
                                        jsonObject.getString("b_id"),
                                        jsonObject.getString("book_id"),
                                        jsonObject.getString("call_date_time"),
                                        jsonObject.getInt("srvc_chrg"),
                                        jsonObject.getString("job_cmplt_dt"),
                                        jsonObject.getString("job_cmplt_tm"),
                                        jsonObject.getString("chrg_typ_name")
                                ));
                                arrlist.addAll(complitedcalllistSetgets);
                                adapter = new ComplitedCallAdapter(getContext(), complitedcalllistSetgets);
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
                        // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("start_dt", startdate);
                params.put("end_dt", enddate);
                params.put("tech_id", techid);
                Log.e("COMPLETEDCALLLISTJOB", " "+startdate+" "+enddate+" "+techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public class ComplitedCallAdapter extends RecyclerView.Adapter<ComplitedCallAdapter.ViewHolder> {
        private Context mCtx;
        private List<ComplitedcalllistSetget> complitedcalllistSetgets;
        String totalprice;
        public ComplitedCallAdapter(Context mCtx, List<ComplitedcalllistSetget> complitedcalllistSetgets) {
            this.mCtx = mCtx;
            this.complitedcalllistSetgets = complitedcalllistSetgets;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
           // View view = inflater.inflate(R.layout.complited_calllist__fragment_view, null);
            View view = inflater.inflate(R.layout.complited_calllist__fragment_vieww, null);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            ComplitedcalllistSetget complitedcalllistSetget = complitedcalllistSetgets.get(position);
//            holder.nameId.setText(complitedcalllistSetget.getPname());
//            holder.time.setText(complitedcalllistSetget.getdName());
//            holder.userId.setText(complitedcalllistSetget.getUsername());
//            holder.phoneId.setText(complitedcalllistSetget.getPhone());
//            holder.locationId.setText(complitedcalllistSetget.getPlace());
//            holder.imgID.setImageDrawable(mCtx.getResources().getDrawable(complitedcalllistSetget.getImage()));
//            if ("Very bad".equals(complitedcalllistSetget.getCastomerreview())){
//                holder.ratingBar.setRating(1);
//                holder.ratingNewsId.setText(complitedcalllistSetget.getCastomerreview()+"/"+"1");
//            }
//            else if ("Need some improvement".equals(complitedcalllistSetget.getCastomerreview())){
//                holder.ratingBar.setRating(2);
//                holder.ratingNewsId.setText(complitedcalllistSetget.getCastomerreview()+"/"+"2");
//            }
//            else if ("Good".equals(complitedcalllistSetget.getCastomerreview())){
//                holder.ratingBar.setRating(3);
//                holder.ratingNewsId.setText(complitedcalllistSetget.getCastomerreview()+"/"+"3");
//            }
//            else if ("Great".equals(complitedcalllistSetget.getCastomerreview())){
//                holder.ratingBar.setRating(4);
//                holder.ratingNewsId.setText(complitedcalllistSetget.getCastomerreview()+"/"+"4");
//            }
//            else if ("Awesome. I love it".equals(complitedcalllistSetget.getCastomerreview())){
//                holder.ratingBar.setRating(5);
//                holder.ratingNewsId.setText(complitedcalllistSetget.getCastomerreview()+"/"+"5");
//            }
//            else {
//
//            }
//            final Animation animation = new AlphaAnimation((float) 0.15, 0);
//            animation.setDuration(500);
//            animation.setInterpolator(new LinearInterpolator());
//            animation.setRepeatCount(Animation.INFINITE);
//            animation.setRepeatMode(Animation.REVERSE);
//            holder.callId.startAnimation(animation);

//            holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                @Override
//                public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
//                    String ratedValue = String.valueOf(rating);
//                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
//                    curRate = Float.valueOf(decimalFormat.format((curRate
//                            * count + rating) / ++count));
//                    Log.e("ratingBar"," "+curRate);
//                    Log.e("rating"," "+ratedValue);
//
//                }
//            });

//            holder.viewID.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(),CompleteviewActivity.class);
//                    startActivity(intent);
//                }
//            });
            String jobid = complitedcalllistSetget.getBookid();
            String amount = String.valueOf(complitedcalllistSetget.getSrvcchrg());
            holder.nameId.setText("JOB ID :"+" "+jobid);
            holder.amountId.setText(amount);
            holder.calldatetimeId.setText(complitedcalllistSetget.getCalldatetime());
            holder.jobasdateId.setText(complitedcalllistSetget.getJobcmpltdt());
            holder.timeasId.setText(complitedcalllistSetget.getJobcmplttm());
            holder.serviceId.setText(complitedcalllistSetget.getChrgtypname());
            holder.bId.setText(complitedcalllistSetget.getBid());
            int totalPrice = 0;
            for (int i = 0; i<complitedcalllistSetgets.size(); i++)
            {
                totalPrice += complitedcalllistSetgets.get(i).getSrvcchrg();
                Log.e("SERVICETOTAL", " "+totalPrice);
                Log.e("Listsize"," "+complitedcalllistSetgets.size());
                holder.imgID.setText(Integer.toString(position+1));
                totalprice = String.valueOf(totalPrice);
            }
            totalId.setText("TOTAL :"+totalprice);

        }
        @Override
        public int getItemCount() {
            return complitedcalllistSetgets.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
         //   TextView nameId,time,userId,phoneId,locationId,ratingNewsId;
           // ImageView imgID,callId;
          //  RatingBar ratingBar;
          //  Button viewID;
            TextView imgID,nameId,amountId,calldatetimeId,bId,jobasdateId,timeasId,serviceId;
            public ViewHolder(View itemView) {
                super(itemView);
//                imgID = itemView.findViewById(R.id.imgID);
//                callId = itemView.findViewById(R.id.callId);
//                nameId = itemView.findViewById(R.id.nameId);
//                time = itemView.findViewById(R.id.time);
//                userId = itemView.findViewById(R.id.userId);
//                phoneId = itemView.findViewById(R.id.phoneId);
//                locationId = itemView.findViewById(R.id.locationId);
//                ratingNewsId = itemView.findViewById(R.id.ratingNewsId);
//                ratingBar = itemView.findViewById(R.id.ratingBar);
//                viewID  = itemView.findViewById(R.id.viewID);
                  imgID = itemView.findViewById(R.id.imgID);
                  nameId = itemView.findViewById(R.id.nameId);
                  amountId = itemView.findViewById(R.id.amountId);
                  calldatetimeId = itemView.findViewById(R.id.calldatetimeId);
                  bId = itemView.findViewById(R.id.bId);
                  jobasdateId = itemView.findViewById(R.id.jobasdateId);
                  timeasId = itemView.findViewById(R.id.timeasId);
                  serviceId = itemView.findViewById(R.id.serviceId);
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

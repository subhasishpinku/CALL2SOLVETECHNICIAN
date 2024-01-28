package call.callsolv.call2solvetechnician;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



import call.callsolv.call2solvetechnician.SetGetActivity.Datacollectionsetget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Datawishecollection extends Fragment implements View.OnClickListener,OnBackClickListener{
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendarr;
    DatePickerDialog.OnDateSetListener datee;
    ImageView searchID,searchIDD;
    TextView FdateId,TdateId;
    Button findID;
    RecyclerView recyclerView;
    List<Datacollectionsetget> datacollectionsetgets;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.datawisecollection_fragment, container, false);
        searchID = (ImageView)rootView.findViewById(R.id.searchID);
        searchID.setOnClickListener(this);
        searchIDD = (ImageView)rootView.findViewById(R.id.searchIDD);
        searchIDD.setOnClickListener(this);
        FdateId = (TextView) rootView.findViewById(R.id.FdateId);
        TdateId = (TextView) rootView.findViewById(R.id.TdateId);
        findID =(Button)rootView.findViewById(R.id.findID);;
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
        datacollectionsetgets = new ArrayList<>();
        datacollectionsetgets.add(new Datacollectionsetget(
                R.mipmap.microwavegrey,"Air Conditioner Repair","Friday, 23rd April 2019 at 10:30am","JOB ID: 423622633","500"
        ));
        datacollectionsetgets.add(new Datacollectionsetget(
                R.mipmap.ac_grey,"Microwave Oven Repair","Friday, 24rd April 2019 at 10:30am","JOB ID: 213462","600"
        ));
        datacollectionsetgets.add(new Datacollectionsetget(
                R.mipmap.fridgegrey,"Refrigerator Repair","Friday, 25rd April 2019 at 10:30am","JOB ID: 4599623","800"
        ));
        datacollectionsetgets.add(new Datacollectionsetget(
                R.mipmap.microwavegrey,"Microwave Oven Repair","Friday, 26rd April 2019 at 10:30am","JOB ID: 234568","900"
        ));
        Datawishcollection adapter = new Datawishcollection(getContext(),datacollectionsetgets);
        recyclerView.setAdapter(adapter);
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

                break;

            default:
        }
    }

    private void Fromdate() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        FdateId.setText(sdf.format(myCalendar.getTime()));
    }
    private void Todate() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        TdateId.setText(sdf.format(myCalendarr.getTime()));
    }

    public class Datawishcollection extends RecyclerView.Adapter<Datawishcollection.ViewHolder> {
        private Context mCtx;
        private List<Datacollectionsetget> datacollectionsetgets;
        int listview = -1;
        public Datawishcollection(Context mCtx, List<Datacollectionsetget> datacollectionsetgets) {
            this.mCtx = mCtx;
            this.datacollectionsetgets = datacollectionsetgets;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.datawish_collection_view, null);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Datacollectionsetget datacollectionsetget = datacollectionsetgets.get(position);
            listview = position;
            holder.nameId.setText(datacollectionsetget.getPname());
            holder.time.setText(datacollectionsetget.getDname());
            holder.userId.setText(datacollectionsetget.getJobid());
            holder.imgID.setImageDrawable(mCtx.getResources().getDrawable(datacollectionsetget.getImage()));
            holder.relativeLayoutID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(getContext(), InvoiceActivity.class);
                startActivity(intent);
                }
            });

        }
        @Override
        public int getItemCount() {
            return datacollectionsetgets.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameId,time,userId,amountID;
            ImageView imgID;
            RelativeLayout relativeLayoutID;
            public ViewHolder(View itemView) {
                super(itemView);
                imgID = itemView.findViewById(R.id.imgID);
                nameId = itemView.findViewById(R.id.nameId);
                time = itemView.findViewById(R.id.time);
                userId = itemView.findViewById(R.id.userId);
                amountID = itemView.findViewById(R.id.amountID);
                relativeLayoutID = itemView.findViewById(R.id.relativeLayoutID);

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

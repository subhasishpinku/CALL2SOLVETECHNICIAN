package call.callsolv.call2solvetechnician;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.RatingBar;
import android.widget.TextView;



import call.callsolv.call2solvetechnician.SetGetActivity.PendingratingComplitesetget;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PendingratingComplited extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    List<PendingratingComplitesetget> pendingratingComplitesetgets;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    TextView txt_tgl;
    Button findId;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pending_rating_complited, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        txt_tgl = (TextView)rootView.findViewById(R.id.txt_tgl);
        findId = (Button)rootView.findViewById(R.id.findId);
        findId.setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pendingratingComplitesetgets =new ArrayList<>();
        pendingratingComplitesetgets.add(new PendingratingComplitesetget(
                R.mipmap.microwavegrey,"Microwave Oven Repair","Friday, 23rd April 2019 at 10:30am","Bijoy Roy","923145684","Sodhpur","200","Good"
        ));
        pendingratingComplitesetgets.add(new PendingratingComplitesetget(
                R.mipmap.ac_grey,"Air Conditioner Repair","Friday, 24rd April 2019 at 10:30am","Amit Roy","9804044940","Sodhpur","500","Great"
        ));
        pendingratingComplitesetgets.add(new PendingratingComplitesetget(
                R.mipmap.fridgegrey,"Refrigerator Repair","Friday, 25rd April 2019 at 10:30am","Chunku Ghosh","9163752468","Sayambazer","800","Need some improvement"
        ));
        PendingratingComplitedAdapter adapter = new PendingratingComplitedAdapter(getContext(), pendingratingComplitesetgets);
        recyclerView.setAdapter(adapter);
        myCalendar = Calendar.getInstance();
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
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        txt_tgl.setText(formattedDate);
        return rootView;
    }
    private void Fromdate() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_tgl.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.findId:
            new DatePickerDialog(getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            break;
            default:
    }
    }
    public class PendingratingComplitedAdapter extends RecyclerView.Adapter<PendingratingComplitedAdapter.ViewHolder> {
        private Context mCtx;
        private List<PendingratingComplitesetget> pendingratingComplitesetgets;
        public PendingratingComplitedAdapter(Context mCtx, List<PendingratingComplitesetget> pendingratingComplitesetgets) {
            this.mCtx = mCtx;
            this.pendingratingComplitesetgets = pendingratingComplitesetgets;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.rating_complit_view, null);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            PendingratingComplitesetget pendingratingComplitesetget = pendingratingComplitesetgets.get(position);
            holder.nameId.setText(pendingratingComplitesetget.getPname());
            holder.time.setText(pendingratingComplitesetget.getdName());
            holder.userId.setText(pendingratingComplitesetget.getUsername());
            holder.phoneId.setText(pendingratingComplitesetget.getPhone());
            holder.locationId.setText(pendingratingComplitesetget.getPlace());
            holder.imgID.setImageDrawable(mCtx.getResources().getDrawable(pendingratingComplitesetget.getImage()));
            if ("Very bad".equals(pendingratingComplitesetget.getCastomerreview())){
                holder.ratingBar.setRating(1);
                holder.ratingNewsId.setText(pendingratingComplitesetget.getCastomerreview()+"/"+"1");

            }
            else if ("Need some improvement".equals(pendingratingComplitesetget.getCastomerreview())){
                holder.ratingBar.setRating(2);
                holder.ratingNewsId.setText(pendingratingComplitesetget.getCastomerreview()+"/"+"2");
            }
            else if ("Good".equals(pendingratingComplitesetget.getCastomerreview())){
                holder.ratingBar.setRating(3);
                holder.ratingNewsId.setText(pendingratingComplitesetget.getCastomerreview()+"/"+"3");
            }
            else if ("Great".equals(pendingratingComplitesetget.getCastomerreview())){
                holder.ratingBar.setRating(4);
                holder.ratingNewsId.setText(pendingratingComplitesetget.getCastomerreview()+"/"+"4");
            }
            else if ("Awesome. I love it".equals(pendingratingComplitesetget.getCastomerreview())){
                holder.ratingBar.setRating(5);
                holder.ratingNewsId.setText(pendingratingComplitesetget.getCastomerreview()+"/"+"5");
            }
            else {

            }


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

        }
        @Override
        public int getItemCount() {
            return pendingratingComplitesetgets.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameId,time,userId,phoneId,locationId,ratingNewsId;
            ImageView imgID,callId;
            RatingBar ratingBar;
            public ViewHolder(View itemView) {
                super(itemView);
                imgID = itemView.findViewById(R.id.imgID);
                callId = itemView.findViewById(R.id.callId);
                nameId = itemView.findViewById(R.id.nameId);
                time = itemView.findViewById(R.id.time);
                userId = itemView.findViewById(R.id.userId);
                phoneId = itemView.findViewById(R.id.phoneId);
                locationId = itemView.findViewById(R.id.locationId);
                ratingNewsId = itemView.findViewById(R.id.ratingNewsId);
                ratingBar = itemView.findViewById(R.id.ratingBar);

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

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

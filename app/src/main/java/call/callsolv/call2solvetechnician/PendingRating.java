package call.callsolv.call2solvetechnician;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;



import call.callsolv.call2solvetechnician.SetGetActivity.pending_rating_setget;

import java.util.ArrayList;
import java.util.List;

public class PendingRating extends Fragment {
    List<pending_rating_setget> pending_rating_setgets;
    RecyclerView recyclerView;
    AlertDialog.Builder builder;
    private AlertDialog alertDialog = null;
    Toolbar toolbar;
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
        View rootView = inflater.inflate(R.layout.pending_rating, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pending_rating_setgets = new ArrayList<>();
        pending_rating_setgets.add(new pending_rating_setget(
                R.mipmap.microwavegrey,"Microwave Oven Repair","Friday, 23rd April 2019 at 10:30am","Amit Roy","9163752468","Kolkata","500"
        ));
        pending_rating_setgets.add(new pending_rating_setget(
                R.mipmap.ac_grey,"Air Conditioner Repair","Friday, 23rd April 2019 at 10:30am","Sumit Roy","9163752468","Kolkata","600"
        ));
        PandingAdapter adapter = new PandingAdapter(getContext(), pending_rating_setgets);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
    public class PandingAdapter extends RecyclerView.Adapter<PandingAdapter.ViewHolder> {
        private Context mCtx;
        private List<pending_rating_setget> pending_rating_setgets;
        CheckBox goodID,averageID,badID,otherID;
        public PandingAdapter(Context mCtx, List<pending_rating_setget> pending_rating_setgets) {
            this.mCtx = mCtx;
            this.pending_rating_setgets = pending_rating_setgets;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.pending_rating_view, null);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final pending_rating_setget pending_rating_setget = pending_rating_setgets.get(position);
            holder.nameId.setText(pending_rating_setget.getPname());
            holder.time.setText(pending_rating_setget.getdName());
            holder.userId.setText(pending_rating_setget.getUsername());
            holder.phoneId.setText(pending_rating_setget.getPhone());
            holder.locationId.setText(pending_rating_setget.getPlace());
            holder.amountID.setText(pending_rating_setget.getAmount());
            holder.imgID.setImageDrawable(mCtx.getResources().getDrawable(pending_rating_setget.getImage()));
            holder.ratingID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder = new AlertDialog.Builder(v.getContext());
                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    final View loginFormView = getLayoutInflater().inflate(R.layout.diologbox, null);
                    LinearLayout submit =(LinearLayout) loginFormView.findViewById(R.id.submitID);
                    final EditText command = (EditText)loginFormView.findViewById(R.id.cmdinformation);
                    toolbar = (Toolbar)loginFormView.findViewById(R.id.toolbar);
                    RatingBar ratingBar = (RatingBar)loginFormView.findViewById(R.id.ratingBar);
                    //  toolbar.setTitle(mImageNames.get(i));
                    goodID = (CheckBox)loginFormView.findViewById(R.id.goodID);
                    averageID = (CheckBox)loginFormView.findViewById(R.id.averageID);
                    badID = (CheckBox)loginFormView.findViewById(R.id.badID);
                    otherID = (CheckBox)loginFormView.findViewById(R.id.otherID);
                    toolbar.setTitle(" Rating For"+" "+pending_rating_setget.getUsername());
                    ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
                    toolbar.setNavigationIcon(R.drawable.ic_home_black);
                    toolbar.setNavigationOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(),NavigationDrawerActivity.class);
                                    startActivity(intent);
                                }
                            }
                    );
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }

                    });
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                            command.setText(String.valueOf(v));
                            switch ((int) ratingBar.getRating()) {
                                case 1:
                                    command.setText("Good customer");
                                    break;
                                case 2:
                                    command.setText("Average customer");
                                    break;
                                case 3:
                                    command.setText("Very bad behaviour");
                                    break;
                                case 4:
                                    command.setText("Great");
                                    break;
                                case 5:
                                    command.setText("Other");
                                    break;
                                default:
                                    command.setText("");
                            }
                        }
                    });
                    builder.setView(loginFormView);
                    alertDialog = builder.create();
                    alertDialog.show();

                }
            });
        }
        @Override
        public int getItemCount() {
            return pending_rating_setgets.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameId,time,userId,phoneId,locationId,amountID;
            ImageView imgID;
            LinearLayout ratingID;
            public ViewHolder(View itemView) {
                super(itemView);
                imgID = itemView.findViewById(R.id.imgID);
                nameId = itemView.findViewById(R.id.nameId);
                time = itemView.findViewById(R.id.time);
                userId = itemView.findViewById(R.id.userId);
                phoneId = itemView.findViewById(R.id.phoneId);
                locationId = itemView.findViewById(R.id.locationId);
                amountID = itemView.findViewById(R.id.amountID);
                ratingID = (LinearLayout)itemView.findViewById(R.id.ratingID);
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

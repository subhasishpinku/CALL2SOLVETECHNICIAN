package call.callsolv.call2solvetechnician;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import call.callsolv.call2solvetechnician.ApplicationActivity.Application;
import call.callsolv.call2solvetechnician.SetGetActivity.Kycsetget;
import call.callsolv.call2solvetechnician.SetGetActivity.ProductSpecification;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.Qulificationsetget;


public class FragmentDrawer extends Fragment {
    private static String TAG = FragmentDrawer.class.getSimpleName();
    private TextView  dashboardId,profileId,hubId,myspokId,calllistID,regectcalllistID,complitedlistId,ratingId,expaned,earing,reportId,logoutId,changePassId;
    private CircularImageView profileImageView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    private SharedPreferences sp;
    String imageUrl,username,desig,email;
    String techid;
    TextView nameTextView;
    public FragmentDrawer() {

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e("TABB","00");
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
            techid = String.valueOf(profileSetGet.getTechid());
            ShowProfile(techid);
        }
    }
    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sp  =   this.getActivity().getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        profileImageView=   (CircularImageView) layout.findViewById(R.id.profileImageView);
        dashboardId = (TextView)layout.findViewById(R.id.dashboardId);
        profileId = (TextView)layout.findViewById(R.id.profileId);
        hubId = (TextView)layout.findViewById(R.id.hubId);
        myspokId = (TextView)layout.findViewById(R.id.myspokId);
        calllistID = (TextView)layout.findViewById(R.id.calllistID);
        regectcalllistID = (TextView)layout.findViewById(R.id.regectcalllistID);
        complitedlistId = (TextView)layout.findViewById(R.id.complitedlistId);
        ratingId = (TextView)layout.findViewById(R.id.ratingId);
        expaned = (TextView)layout.findViewById(R.id.expaned);
        earing = (TextView)layout.findViewById(R.id.earing);
        reportId = (TextView)layout.findViewById(R.id.reportId);
        logoutId = (TextView)layout.findViewById(R.id.logoutId);
        nameTextView = (TextView)layout.findViewById(R.id.nameTextView);
        changePassId = (TextView)layout.findViewById(R.id.changePassId);
        String imageUrl =   sp.getString("USERPHOTO","http://gshandicraftfashion.com/wp-content/themes/sw_chamy/assets/img/no-thumbnail.png");
        System.out.println("Image Tag- "+imageUrl);
       // ImageLoader imageLoader = Application.getInstance().getImageLoader();
       // profileImageView.setImageUrl(imageUrl,imageLoader);
        dashboardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.DASBOARD);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        profileId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.PROFILE);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        hubId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYHUM);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        myspokId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYSPOK);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        calllistID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.CALLLIST);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        regectcalllistID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.REJECTCALLLIST);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        complitedlistId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.COMPLITEDCALLLIST);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        ratingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.CUSTOMERRATIONG);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        expaned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYEXPANED);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        earing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.DATAWISHCOLLECTION);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        reportId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.REPORT);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        changePassId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.CHANGEPASSWORD);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        logoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout(techid);
            }
        });
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        ShowProfile(techid);
        return layout;
    }
    public  void Logout(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.LOGOUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("logout", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            if (status.equals("1")){
                                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                                SharedPrefManagerProfile.getInstance(getContext()).logout();
                            }
                            else {
                                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
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
                params.put("tech_id", techid);
                params.put("flag", "0");
                Log.e("trechId",techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    public void ShowProfile(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ProfileLogin", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String techcode = obj.getString("tech_code");
                            String techname = obj.getString("tech_name");
                            String techfather = obj.getString("tech_father");
                            String techphn = obj.getString("tech_phn");
                            String techwatsapp = obj.getString("tech_watsapp");
                            String techmail = obj.getString("tech_mail");
                            String techadrs = obj.getString("tech_adrs");
                            String techdist = obj.getString("tech_dist");
                            String techbdoulb = obj.getString("tech_bdo_ulb");
                            String techpo = obj.getString("tech_po");
                            String techpolice = obj.getString("tech_police");
                            String techimg = obj.getString("tech_img");
                            String bankname = obj.getString("bank_name");
                            String brnchnm = obj.getString("brnch_nm");
                            String acntno = obj.getString("acnt_no");
                            Log.e("Value_Pro"," "+techcode+" "+techname+" "+techfather+" "
                                    +techphn+" "+techwatsapp+" "+techmail+" "+techadrs+" "
                                    +techdist+" "+techbdoulb+" "+techpo+" "+techpolice+" "+techimg+" "+bankname+" "+brnchnm+" "+acntno);
                            ImageLoader imageLoader = Application.getInstance().getImageLoader();
                            profileImageView.setImageUrl(techimg,imageLoader);
                            nameTextView.setText(techname);

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
                params.put("tech_id", techid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
}

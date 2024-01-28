package call.callsolv.call2solvetechnician;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.dynamic.IFragmentWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper;
import call.callsolv.call2solvetechnician.ServiceActivity.MyService;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_FLAGTABLE1;
import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_ID1;
import static call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper.TABLE_PENDINGCOUNT1;


public class DasboardFragment extends Fragment implements TabHost.OnTabChangeListener {
    TabLayout tabLayout;
    TabHost tabHost;
    String techid;
    String totpendingcall,totacptcall,totacptcall1,flag;
    int totpendingcall1;
    int totpendingcalll;
    int pendingcallDB;
    int pendingcallDB1;
    String flagId,flagId1;
    private DatabaseHelper db;
    ViewPager viewPager;
    private int[] tabicons ={
            R.mipmap.pending,
            R.mipmap.accept
    };
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e("TABB","000");
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
            techid = String.valueOf(profileSetGet.getTechid());
            totalcallcount(techid);
           // ALAR();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dasboard_fragment, container, false);
        tabLayout = (TabLayout)rootView.findViewById(R.id.tab_layout);
        viewPager =(ViewPager)rootView.findViewById(R.id.view_pager);
        db = new DatabaseHelper(getContext());
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        totalcallcount(techid);
      //  ALAR();
        return rootView;
    }

    public void totalcallcount(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TOTALCAKLLCOUNT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TotalCallCount", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            totpendingcall = obj.getString("tot_pending_call");
                            totacptcall = obj.getString("tot_acpt_call");
                            flag = obj.getString("flag");
                            totpendingcalll = Integer.parseInt(totpendingcall);
                            Log.e("callcount"," "+totpendingcall+" "+totacptcall+" "+flag);
                            alarm(techid,totpendingcalll);
                            tabLayout.addTab(tabLayout.newTab().setText("PENDING CALL"+" "+"("+totpendingcall+")"));
                            tabLayout.addTab(tabLayout.newTab().setText("ACCEPTED CALL"+" "+"("+totacptcall+")"));
                            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                            if (getActivity() != null) {
                                TabsAdapter tabsAdapter = new TabsAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
                                viewPager.setAdapter(tabsAdapter);
                            }
                            // tabLayout = getHost();
                            // tabLayout.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.ab);
                            // tabLayout.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.ab);
                            // tabLayout.getTabWidget().setCurrentTab(0);
                            // tabLayout.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.abch);
                            tabLayout.getTabAt(0).setIcon(tabicons[0]);
                            tabLayout.getTabAt(1).setIcon(tabicons[1]);
                            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabLayout.Tab tab) {
                                    viewPager.setCurrentItem(tab.getPosition());
                                    if (tab.getPosition()==0){
                                      Log.e("TABB","0");
                                    }
                                    else if (tab.getPosition()==1){
                                        Log.e("TABB","1");
                                    }
                                    else {

                                    }
                                }
                                @Override
                                public void onTabUnselected(TabLayout.Tab tab) {

                                }
                                @Override
                                public void onTabReselected(TabLayout.Tab tab) {

                                }

                            });
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
    public void alarm(final String techid,final int totpendingcalll){
        Cursor res = db.getAllLoginData();
        if(res.getCount() == 0) {
            Log.e("Error","Nothing found");
            db.addpending(totpendingcalll);
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            flagId =  res.getString(0);
            pendingcallDB =  res.getInt(1);
            Log.e("FLAGID"," "+flagId+" "+pendingcallDB);
        }
        /////////////////////////
        Cursor ress = db.getAllLoginData1();
        if(ress.getCount() == 0) {
            Log.e("Error1","Nothing found");
            db.addpending1(totpendingcalll);
            return;
        }
        StringBuffer bufferr = new StringBuffer();
        while (ress.moveToNext()) {
            flagId1 =  ress.getString(0);
            pendingcallDB1 =  ress.getInt(1);
            Log.e("FLAGID1"," "+flagId+" "+pendingcallDB1);
            Log.e("Value"," "+pendingcallDB1+" "+pendingcallDB);
            if (pendingcallDB == 0) {
                //db.addpending(totpendingcalll);

            } else {
                Log.e("Error1", "Ok");
                if (pendingcallDB1 > pendingcallDB) {
                    Log.e("Error1", "Start");
                    Intent intent = new Intent(getContext(), Alarm_Activity.class);
                   // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Log.e("FLAGID1", " " + "1");
                } else {
                    Log.e("FLAGID1", " " + "0");
                }
            }
        }
        if(ress.getCount() == 1){
            Log.e("Error1","Yes found");
            SQLiteDatabase database = db.getReadableDatabase();
            database.execSQL( "UPDATE "+TABLE_FLAGTABLE1 +" SET " + TABLE_PENDINGCOUNT1+ " = '"+totpendingcalll+"' WHERE "+TABLE_ID1+ " = "+flagId1);
            return;
        }
        ////////////////////////////
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TOTALCAKLLCOUNTALAM,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("TotalCallCount2", response);
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                             totpendingcall1 = obj.getInt("res");
//                            Log.e("callcount2"," "+totpendingcalll+" "+totpendingcall1);
////                            if (totpendingcalll>totpendingcall1){
////                               // Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
////                                Intent intent = new Intent(getContext(),Alarm_Activity.class);
////                                startActivity(intent);
////                            }
////                            else {
////                                //Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT).show();
////                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //Toast.makeText(getApplicationContext(), "UserName Password Not Valid", Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("tech_id", techid);
//                if (flag.equals("0")){
//                    params.put("flag","0");
//                    Log.e("TechFlag0",techid+" "+"0");
//                }
//                if (flag.equals("1")){
//                    params.put("flag","1");
//                    Log.e("TechFlag1",techid+" "+"1");
//                }
//                if (flag.equals("00")){
//                    params.put("flag","1");
//                    Log.e("TechFlag00",techid+" "+flag);
//                }
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
//        stringRequest.setShouldCache(false);
//        volleySingleton.addToRequestQueue(stringRequest);
    }

//    public void ALAR(){
//        Cursor res = db.getAllLoginData();
//        if(res.getCount() == 0) {
//            Log.e("Error","Nothing found");
//            db.addpending(totpendingcalll);
//            return;
//        }
//        StringBuffer buffer = new StringBuffer();
//        while (res.moveToNext()) {
//            flagId =  res.getString(0);
//            pendingcallDB =  res.getInt(1);
//            Log.e("FLAGID"," "+flagId+" "+pendingcallDB);
//        }
//        /////////////////////////
//        Cursor ress = db.getAllLoginData1();
//        if(ress.getCount() == 0) {
//            Log.e("Error1","Nothing found");
//            db.addpending1(totpendingcalll);
//            return;
//        }
//        StringBuffer bufferr = new StringBuffer();
//        while (ress.moveToNext()) {
//            flagId1 =  ress.getString(0);
//            pendingcallDB1 =  ress.getInt(1);
//            Log.e("FLAGID1"," "+flagId+" "+pendingcallDB1);
//        }
//        if(ress.getCount() == 1) {
//            Log.e("Error1","Yes found");
//            SQLiteDatabase database = db.getReadableDatabase();
//            database.execSQL( "UPDATE "+TABLE_FLAGTABLE1 +" SET " + TABLE_ID1+ " = '"+flagId1+"' WHERE "+TABLE_PENDINGCOUNT1+ " = "+totpendingcalll);
//            return;
//        }
//
//        if (pendingcallDB1>pendingcallDB){
//            Log.e("Error1","Start");
//            Intent intent = new Intent(getContext(),Alarm_Activity.class);
//            startActivity(intent);
//        }
//        ////////////////////////////
//    }
    @Override
    public void onTabChanged(String tabId) {

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

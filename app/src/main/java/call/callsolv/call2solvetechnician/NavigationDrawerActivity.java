package call.callsolv.call2solvetechnician;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import call.callsolv.call2solvetechnician.ActivityDatabase.DatabaseHelper;
import call.callsolv.call2solvetechnician.ServiceActivity.MyService;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

public class NavigationDrawerActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, View.OnClickListener,BackButtonHandlerInterface  {
    private static String TAG = NavigationDrawerActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private SharedPreferences sp;
    ImageButton profileID;
    boolean doubleBackToExitPressedOnce = false;
    private ArrayList<WeakReference<OnBackClickListener>> backClickListenersList = new ArrayList<>();
    MyMainReceiver myMainReceiver;
    String techid;
    String totpendingcall,totacptcall,totacptcall1,flag;
    int totpendingcall1;
    int totpendingcalll;
    int pendingcallDB;
    int pendingcallDB1;
    String flagId,flagId1;
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//               WindowManager.LayoutParams.FLAG_SECURE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_main);
        System.out.println("Inside onCreate");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        profileID  = (ImageButton)findViewById(R.id.profileID);
        profileID.setOnClickListener(this);
        sp  =   getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        displayView(Consts.DASBOARD);
        db = new DatabaseHelper(getApplicationContext());
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        totalcallcount(techid);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profileID:
                Logout(techid);
                break;
            default:
        }
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
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                finish();
                                SharedPrefManagerProfile.getInstance(getApplicationContext()).logout();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("PRE_VAL","RESTRO");
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("tag", "On config changed");
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logoutID) {
            finish();
            SharedPrefManagerProfile.getInstance(getApplicationContext()).logout();
            return true;
        }
        if (id == R.id.refressId){
            finish();
            startActivity(getIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getResources().getString(R.string.app_name);
        switch (position) {
            case Consts.DASBOARD:
                //title = getResources().getString(R.string.app_name);
                fragment = new DasboardFragment();
              //  Intent intent = new Intent(getApplicationContext(),DasboardAcivity.class);
              //  startActivity(intent);
                break;
            case Consts.PROFILE:
                title = getResources().getString(R.string.Profile);
                fragment = new ProfileFragment();
              // Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                //startActivity(intent);
                break;
            case Consts.MYHUM:
                title = getResources().getString(R.string.MyHUBDetails);
                fragment = new MyhubFragment();
                break;
            case Consts.MYSPOK:
                title = getResources().getString(R.string.MySpokeDetails);
                fragment = new Myspoke();
                break;
            case Consts.CALLLIST:
                title = getResources().getString(R.string.calllist);
                fragment = new CallListFragment();
                break;
            case Consts.REJECTCALLLIST:
                title = getResources().getString(R.string.rejectcall);
                fragment = new RejectedcallListFragment();
                break;
            case Consts.COMPLITEDCALLLIST:
                title = getResources().getString(R.string.compilted);
                fragment = new ComplitedCallListFreagment();
                break;
            case Consts.CUSTOMERRATIONG:
                title = getResources().getString(R.string.rating);
               // fragment = new CustomerRatingFragment();
                fragment = new CustomerRatingProviderFragment();
                break;
            case Consts.MYEXPANED:
                title = getResources().getString(R.string.myexpend);
                fragment = new MyexpendentFragment();
                break;
            case Consts.DATAWISHCOLLECTION:
                title = getResources().getString(R.string.datewisecollection);
                fragment = new Datawishecollection();
                break;
            case Consts.REPORT:
                title = getResources().getString(R.string.report);
                fragment = new ReportFragment();
                break;
            case Consts.CHANGEPASSWORD:
                title = getResources().getString(R.string.password);
                fragment = new ChangePassword();
                break;
                default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title);
        }
    }
//    @Override
//    public void onBackPressed() {
//    }

    private class MyMainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(MyService.ACTION_UPDATE_CNT)){
                int int_from_service = intent.getIntExtra(MyService.KEY_INT_FROM_SERVICE, 0);
                // textViewCntReceived.setText(String.valueOf(int_from_service));
            }else if(action.equals(MyService.ACTION_UPDATE_MSG)){
                String string_from_service = intent.getStringExtra(MyService.KEY_STRING_FROM_SERVICE);
                //  textViewMsgReceived.setText(String.valueOf(string_from_service));
            }


        }

    }
    @Override
    protected void onStart() {
        myMainReceiver = new MyMainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.ACTION_UPDATE_CNT);
        intentFilter.addAction(MyService.ACTION_UPDATE_MSG);
        registerReceiver(myMainReceiver, intentFilter);
        super.onStart();
    }
    public void totalcallcount(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TOTALCAKLLCOUNT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TotalCallCountTech", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            totpendingcall = obj.getString("tot_pending_call");
                            totacptcall = obj.getString("tot_acpt_call");
                            flag = obj.getString("flag");
                            totpendingcalll = Integer.parseInt(totpendingcall);
                            Log.e("callcount"," "+totpendingcall+" "+totacptcall+" "+flag);
                            Cursor res = db.getAllLoginData();
//                            if(res.getCount() == 0) {
//                                db.addpending(totpendingcalll);
//                                Log.e("InsertVAlue"," "+totpendingcalll);
//                                return;
//                            }
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
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(myMainReceiver);
        super.onStop();
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("Inside onResume");
    }

//    @Override
//    public void onStart(){
//        super.onStart();
//        System.out.println("Inside onStart");
//    }

    @Override
    public void onRestart(){
        super.onRestart();
        System.out.println("Inside onReStart");
    }

    @Override
    public void onPause(){
        super.onPause();
        System.out.println("Inside onPause");
    }

//    @Override
//    public void onStop(){
//        super.onStop();
//        System.out.println("Inside onStop");
//    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        System.out.println("Inside onDestroy");
    }

    @Override
    public void addBackClickListener(OnBackClickListener onBackClickListener) {
        backClickListenersList.add(new WeakReference<>(onBackClickListener));
    }

    @Override
    public void removeBackClickListener(OnBackClickListener onBackClickListener) {
        for (Iterator<WeakReference<OnBackClickListener>> iterator = backClickListenersList.iterator();
             iterator.hasNext();){
            WeakReference<OnBackClickListener> weakRef = iterator.next();
            if (weakRef.get() == onBackClickListener){
                iterator.remove();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(!fragmentsBackKeyIntercept()){
            super.onBackPressed();
        }
    }

    private boolean fragmentsBackKeyIntercept() {
        boolean isIntercept = false;
        for (WeakReference<OnBackClickListener> weakRef : backClickListenersList) {
            OnBackClickListener onBackClickListener = weakRef.get();
            if (onBackClickListener != null) {
                boolean isFragmIntercept = onBackClickListener.onBackClick();
                if (!isIntercept) isIntercept = isFragmIntercept;
            }
        }
        return isIntercept;
    }
}

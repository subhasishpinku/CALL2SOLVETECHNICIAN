package call.callsolv.call2solvetechnician;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

import call.callsolv.call2solvetechnician.SetGetActivity.PendingSetGet;
import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;
public class SharedPrefManagerProfile {
    private static final String SHARED_PREF_NAME ="call2solvetechnician";
    private static final String KEY_MSG = "msg";
    private static final String KEY_MESSAGE = "Message";
    private static final String KEY_LOGINID = "loginid";
    private static final String KEY_HUBID = "hubid";
    private static final String KEY_SPOKEID = "spokeid";
    private static final String KEY_TECHID = "techid";
    private static SharedPrefManagerProfile mInstance;
    private static Context mCtx;
    private SharedPrefManagerProfile(Context context) {
        mCtx = context;
    }
    String techid;
    public static synchronized SharedPrefManagerProfile getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerProfile(context);
        }
        return mInstance;
    }
    public void userProfile(ProfileSetGet profileSetGet) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MSG, profileSetGet.getMsg());
        editor.putString(KEY_MESSAGE, profileSetGet.getMessage());
        editor.putString(KEY_LOGINID, profileSetGet.getLoginid());
        editor.putString(KEY_HUBID, profileSetGet.getHubid());
        editor.putString(KEY_SPOKEID, profileSetGet.getSpokeid());
        editor.putString(KEY_TECHID, profileSetGet.getTechid());
        editor.apply();
      //  techid = profileSetGet.getTechid();
      //  Logout(techid);
    }
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LOGINID, null) != null;
    }
    public ProfileSetGet profileSetGet() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ProfileSetGet(
                sharedPreferences.getString(KEY_MSG, null),
                sharedPreferences.getString(KEY_MESSAGE, null),
                sharedPreferences.getString(KEY_LOGINID, null),
                sharedPreferences.getString(KEY_HUBID, null),
                sharedPreferences.getString(KEY_SPOKEID, null),
                sharedPreferences.getString(KEY_TECHID, null)
                );
    }
    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LOGINID, null);
    }
    public void clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(mCtx, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
        // mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
        // ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(mCtx).profileSetGet();
      //  techid = String.valueOf(profileSetGet.getTechid());
       // ProfileSetGet profileSetGet = new ProfileSetGet();

    }
//    public  void Logout(final String techid){
//        Log.e("trechId",techid);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.LOGOUT,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("logout", response);
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            String status = obj.getString("status");
//                            String msg = obj.getString("msg");
//                            if (status.equals("1")){
//                                Toast.makeText(mCtx,msg,Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(mCtx, LoginActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                mCtx.startActivity(intent);
//                                // mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
//                            }
//                            else {
//                                Toast.makeText(mCtx,msg,Toast.LENGTH_SHORT).show();
//                            }
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
//                params.put("flag", "0");
//                Log.e("trechId",techid);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton volleySingleton = VolleySingleton.getInstance(mCtx);
//        stringRequest.setShouldCache(false);
//        volleySingleton.addToRequestQueue(stringRequest);
//    }
}

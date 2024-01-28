package call.callsolv.call2solvetechnician;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class ChangePassword  extends Fragment implements View.OnClickListener,OnBackClickListener{
    private BackButtonHandlerInterface backButtonHandler;
    boolean doubleBackToExitPressedOnce = false;
    EditText usernameId,oldpassId,newpassId,comfrompassId;
    Button subID;
    String techid,techcode,techpass;
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
        View rootView = inflater.inflate(R.layout.changepassword_fragment, container, false);
        subID = (Button)rootView.findViewById(R.id.subID);
        subID.setOnClickListener(this);
        usernameId = (EditText)rootView.findViewById(R.id.usernameId);
        oldpassId = (EditText)rootView.findViewById(R.id.oldpassId);
        newpassId = (EditText)rootView.findViewById(R.id.newpassId);
        comfrompassId = (EditText)rootView.findViewById(R.id.comfrompassId);
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        Log.e("TechId",techid);
        ShowProfile(techid);
        return rootView;
    }
    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.subID:
             changepass(techid);
             break;
             default:
     }
    }
    public void ShowProfile(final String techid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ProfileLogin", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            techcode = obj.getString("tech_code");
                            usernameId.setText(techcode);
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
    public void changepass(final String techid){
        final String username = usernameId.getText().toString();
        final String oldpass = oldpassId.getText().toString();
        final String newpass = newpassId.getText().toString();
        final String confrom = comfrompassId.getText().toString();
        if (TextUtils.isEmpty(username)) {
            usernameId.setError("Please enter your Username");
            usernameId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(oldpass)) {
            oldpassId.setError("Please enter your Old Password");
            oldpassId.requestFocus();
            return;
        }
        ; if (TextUtils.isEmpty(newpass)) {
            newpassId.setError("Please enter your New password");
            newpassId.requestFocus();
            return;
        }
        ; if (TextUtils.isEmpty(confrom)) {
            comfrompassId.setError("Please enter your confirm password");
            comfrompassId.requestFocus();
            return;
        }
        if (newpass.equals(confrom)){
            techpass = comfrompassId.getText().toString();
        }
        else {
            Toast.makeText(getContext(),"Password does not match",Toast.LENGTH_SHORT).show();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CONFIRMPASS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CONFIRMPASS", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            if (status.equals("1")){
                                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                                Logout(techid);
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
                params.put("t_id", techid);
                params.put("t_old_pwd", oldpass);
                params.put("t_pwd", techpass);
                //Log.e("CONFIRMPASSVIEW"," "+bId+" "+prdctcmpny+" "+" "+prdctmodel+" "+serialno);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
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

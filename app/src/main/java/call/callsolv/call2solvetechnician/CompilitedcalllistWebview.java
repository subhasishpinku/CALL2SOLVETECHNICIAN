package call.callsolv.call2solvetechnician;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import call.callsolv.call2solvetechnician.SetGetActivity.ProfileSetGet;

public class CompilitedcalllistWebview extends AppCompatActivity {
    Toolbar toolbar;
    WebView webView;
    WebChromeClient ChromeView;
    //  public static final String url ="http://wbmdfcscholarship.in/aikya_app/";
    public static final String url = "http://www.call2solv.com/cal2solv/Tech/print_completed_list?frm_date=2019-11-01&&to_date=2019-11-18&&t_id=1";
    String frmdate,todate,techid;
    boolean isHomePage = false;
    String  listurl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.compilitedcalllist_webview);
        initToolBar();
        Intent intent = getIntent();
        frmdate = intent.getStringExtra("frmdate");
        todate = intent.getStringExtra("todate");
        ProfileSetGet profileSetGet = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        techid = String.valueOf(profileSetGet.getTechid());
        Log.e("PRINTLIST",frmdate+" "+frmdate+" "+techid);
        webView = (WebView) findViewById(R.id.webView);
//        webView.setWebViewClient(new myWebClient());
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(url+recodId);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("android_asset")) {
                    isHomePage = false;
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });
        listurl = "http://www.call2solv.com/cal2solv/Tech/print_completed_list?frm_date="+frmdate+"&&"+"to_date="+todate+"&&"+"t_id="+techid;
        webView.loadUrl(listurl);
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("PRINT LIST");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_home_black);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent intent = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
                       startActivity(intent);
                    }
                }
        );
    }
//    public class myWebClient extends WebViewClient
//    {
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            // TODO Auto-generated method stub
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // TODO Auto-generated method stub
//
//            view.loadUrl(url);
//            return true;
//
//        }
//    }
//    // To handle &quot;Back&quot; key press event for WebView to go back to previous screen.
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) webView.canGoBack();
//        {
//            webView.goBack();
//            return true;
//        }
//        // return super.onKeyDown(keyCode, event);
//    }
//    @Override
//    public void onBackPressed() {
//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            super.onBackPressed();
//
//        }
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        // if not home page go to home page.
        if(isHomePage == false){
            webView.loadUrl(listurl);
            isHomePage = true;

            // if home page exit app.
            Log.d("CDA", "onBackPressed");
        }else {
            Log.d("CDA", "onBackPressed Called");
            Intent intent_info = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
            startActivity(intent_info);
            overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
        }
    }
}
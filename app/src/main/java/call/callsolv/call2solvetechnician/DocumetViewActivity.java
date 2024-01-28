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
public class DocumetViewActivity extends AppCompatActivity {
    Toolbar toolbar;
    WebView webView;
    boolean isHomePage = false;
    String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.documentview);
        initToolBar();
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        Log.e("url",url);
        webView = (WebView)findViewById(R.id.webView);
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

        webView.loadUrl(url);
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("DOCUMENT VIEW");
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
            webView.loadUrl(url);
            isHomePage = true;

            // if home page exit app.
            Log.d("CDA", "onBackPressed");
        }else {
            Log.d("CDA", "onBackPressed Called");
            Intent intent_info = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
            startActivity(intent_info);
            overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
        }
    }
}

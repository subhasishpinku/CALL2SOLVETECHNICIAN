package call.callsolv.call2solvetechnician;

import android.app.TabActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;



public class DasboardAcivity extends TabActivity implements TabHost.OnTabChangeListener, View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dasboard_fragment);


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTabChanged(String tabId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

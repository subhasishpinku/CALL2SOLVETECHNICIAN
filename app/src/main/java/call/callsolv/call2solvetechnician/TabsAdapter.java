package call.callsolv.call2solvetechnician;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                PandingCall home = new PandingCall();
                return home;
            case 1:
                Acceptedcallfragment about = new Acceptedcallfragment();
                return about;
            default:
                return null;
        }
    }
}
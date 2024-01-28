package call.callsolv.call2solvetechnician;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapterview  extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapterview(FragmentManager fm, int NoofTabs){
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
                PendingRating pendingRating = new PendingRating();
                return pendingRating;
            case 1:
                PendingratingComplited pendingratingComplited = new PendingratingComplited();
                return pendingratingComplited;
            default:
                return null;
        }
    }
}
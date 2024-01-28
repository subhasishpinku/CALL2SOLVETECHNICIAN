package call.callsolv.call2solvetechnician;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;



public class CustomerRatingFragment extends Fragment implements OnBackClickListener {
    TabLayout tabLayout;
    TabHost tabHost;
    private int[] tabicons = {
            R.mipmap.pending,
            R.mipmap.accept
    };
    private BackButtonHandlerInterface backButtonHandler;
    boolean doubleBackToExitPressedOnce = false;

    @Override
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.customer_rating_fragment, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("PENDING RATING"));
        tabLayout.addTab(tabLayout.newTab().setText("RATING COMPLITED"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        TabsAdapterview tabsAdapter = new TabsAdapterview(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
//        tabLayout = getHost();
//        tabLayout.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.ab);
//        tabLayout.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.ab);
//        tabLayout.getTabWidget().setCurrentTab(0);
//        tabLayout.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.abch);
        tabLayout.getTabAt(0).setIcon(tabicons[0]);
        tabLayout.getTabAt(1).setIcon(tabicons[1]);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {

                } else if (tab.getPosition() == 1) {

                } else {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
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

    //    @Override
//    public void onDetach() {
//        super.onDetach();
//
//    }

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
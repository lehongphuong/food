package xmart.com.xmart_android.activity.user.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.extral.SortListener;
import xmart.com.xmart_android.fragment.user.Order.CancelFragment;
import xmart.com.xmart_android.fragment.user.Order.CompleteFragment;
import xmart.com.xmart_android.fragment.user.Order.OrderFragment;
import xmart.com.xmart_android.fragment.user.Order.ProcessFragment;
import xmart.com.xmart_android.logging.L;


public class OrderFormActivity extends AppCompatActivity implements
        BoomMenuButton.OnSubButtonClickListener,
        BoomMenuButton.AnimatorListener,
        View.OnClickListener {
    public static final int TAB_COUNT = 4;
    public static final int ORDER = 0;
    public static final int PROCESS = 1;
    public static final int CANCEL = 2;
    public static final int COMPLETE = 3;

    private int mSettingIsExists = 0;
    //notification
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ViewPager mPager;

    private PagerSlidingTabStrip mTabs;
    private PagerSlidingTabStrip mTabSub;

    private ViewPagerAdapter adapter;
    private BoomMenuButton menuBoomColor;
    private Context mContext;

    ArrayList<Integer> listTab = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_form);

        setTitle("Quản lý đơn hàng");

//        android:fitsSystemWindows="true"
        toolbar = (Toolbar) findViewById(R.id.app_bar_order);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPager = (ViewPager) findViewById(R.id.pager_order);
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_order);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);


        mTabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


        mTabs.setViewPager(mPager);

    }

    public void login(){
        L.T(getApplicationContext(),"Có ai đó đăng nhập tài khoản của bạn..");
        startActivity(new Intent(OrderFormActivity.this,UserLoginActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onClick(int buttonIndex) {
        if (menuBoomColor.isOpen()) {
        }
    }


    @Override
    public void toShow() {

    }

    @Override
    public void showing(float fraction) {

    }

    @Override
    public void showed() {

    }

    @Override
    public void toHide() {

    }

    @Override
    public void hiding(float fraction) {

    }

    @Override
    public void hided() {

    }


    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);

    }

    //tao menu cho menu main
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.order_menu, menu);
        return true;
    }


    //xu ly su kien cho menu main
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            //get id
            finish();
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
//        registerForContextMenu(button);

    }

    public void show(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        Fragment fragment = (Fragment) adapter.instantiateItem(mPager, mPager.getCurrentItem());
        if (fragment instanceof SortListener) {

        }

    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {


        int icons[] = {R.drawable.ic_all, R.drawable.ic_action_recent, R.drawable.ic_action_not, R.drawable.ic_add_subject};

        FragmentManager fragmentManager;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        @Override
        public Fragment getItem(int num) {
//            mPager.setCurrentItem(num);
            Fragment fragment = null;
            switch (num){
                case ORDER:
                    fragment = OrderFragment.newInstance("", "", num);
                    break;
                case PROCESS:
                    fragment = ProcessFragment.newInstance("", "", num);
                    break;
                case CANCEL:
                    fragment = CancelFragment.newInstance("", "", num);
                    break;
                case COMPLETE:
                    fragment = CompleteFragment.newInstance("", "", num);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        //        chi hien chu thoi
        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabOrser)[position];
        }

        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }
    }

}

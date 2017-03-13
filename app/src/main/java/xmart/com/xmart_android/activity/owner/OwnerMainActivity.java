package xmart.com.xmart_android.activity.owner;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import xmart.com.xmart_android.activity.user.main.CartItemActivity;
import xmart.com.xmart_android.activity.user.main.InformationUserActivity;
import xmart.com.xmart_android.activity.user.main.MainActivity;
import xmart.com.xmart_android.activity.user.main.OrderFormActivity;
import xmart.com.xmart_android.activity.user.main.UserLoginActivity;
import xmart.com.xmart_android.db.Categories;
import xmart.com.xmart_android.extral.SortListener;
import xmart.com.xmart_android.fragment.user.main_fragment.FragmentHome;
import xmart.com.xmart_android.fragment.user.main_fragment.FragmentProduct;
import xmart.com.xmart_android.navigation.NavigationDrawerFragment;
import xmart.com.xmart_android.service.CategoriesService;


public class OwnerMainActivity extends AppCompatActivity implements
        BoomMenuButton.OnSubButtonClickListener,
        BoomMenuButton.AnimatorListener,
        View.OnClickListener {
    public static final int TAB_COUNT = 7;

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

    CategoriesService categoriesService;

    ArrayList<Categories> listTab = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MultiDex.install(this);
//        setContentView(R.layout.activity_main_appbar);
        setContentView(R.layout.activity_owner_main);
        categoriesService = new CategoriesService(getApplicationContext());

        mContext = this;
        getDataListTab();



//        android:fitsSystemWindows="true"
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawbar);
        navigationDrawerFragment.setUP(R.id.fragment_navigation_drawbar, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);

        mTabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //set page 1 hien thi
//        mPager.setCurrentItem(3);
        mTabs.setViewPager(mPager);

    }

    private void getDataListTab() {
        Categories categories=new Categories("0","Trang chủ","","");
        listTab.add(categories);
        if(!categoriesService.isEmpty()){
            ArrayList<Categories> arrayList=categoriesService.selectAllCategories();
            for (Categories c: arrayList) {
                listTab.add(c);
            }
        }
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
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //xu ly su kien cho menu main
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            //get id
        }
        if (id == R.id.action_cart) {
            //get id
            startActivity(new Intent(OwnerMainActivity.this,CartItemActivity.class));
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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

    ///click drawer navigation
    public void onDrawerItemClick(int index) {
        //create subject custom
        if (index == 0) {
            startActivity(new Intent(OwnerMainActivity.this,InformationUserActivity.class));
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
        //order item
        if(index==2){
            startActivity(new Intent(OwnerMainActivity.this,OrderFormActivity.class));
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

        if(index==3){
            startActivity(new Intent(OwnerMainActivity.this,CartItemActivity.class));
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
        //exit = 9
        if (index == 5) {
            startActivity(new Intent(OwnerMainActivity.this,UserLoginActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
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
            if(num==0){
                fragment = FragmentHome.newInstance("", "", num);
            }else{
                fragment = FragmentProduct.newInstance("", "", num);
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return listTab.size();
        }

        //        chi hien chu thoi
        @Override
        public CharSequence getPageTitle(int position) {
            return listTab.get(position).getName();
        }

        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }
    }

}

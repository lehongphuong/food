package xmart.com.xmart_android.activity.owner.main;

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
import xmart.com.xmart_android.activity.user.main.InformationUserActivity;
import xmart.com.xmart_android.activity.user.main.MainActivity;
import xmart.com.xmart_android.activity.user.main.UserLoginActivity;
import xmart.com.xmart_android.db.Categories;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.extral.SortListener;
import xmart.com.xmart_android.fragment.owner.OwnerCancelFragment;
import xmart.com.xmart_android.fragment.owner.OwnerCompleteFragment;
import xmart.com.xmart_android.fragment.owner.OwnerOrderFragment;
import xmart.com.xmart_android.fragment.owner.OwnerProcessFragment;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.navigation.owner.NavigationDrawerOwnerFragment;
import xmart.com.xmart_android.service.CategoriesService;
import xmart.com.xmart_android.service.NguoiDungService;


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
        Menu menu1=null;

        //badge icon
        private int badgeCount = 0;
        private NguoiDung nguoiDung;
        private NguoiDungService nguoiDungService;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//        MultiDex.install(this);
//        setContentView(R.layout.activity_main_appbar);
            setContentView(R.layout.activity_owner_main);
            categoriesService = new CategoriesService(getApplicationContext());

            nguoiDungService = new NguoiDungService(getApplicationContext());
            nguoiDung = nguoiDungService.selectAllNguoiDung().get(0);


            mContext = this;
            getDataListTab();


//        android:fitsSystemWindows="true"
            toolbar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            NavigationDrawerOwnerFragment navigationDrawerFragment = (NavigationDrawerOwnerFragment)
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
            Categories categories = new Categories("0", "ORDERED", "", "");
            listTab.add(categories);

            categories = new Categories("1", "PROCESSED", "", "");
            listTab.add(categories);
            categories = new Categories("2", "CANCELED", "", "");
            listTab.add(categories);
            categories = new Categories("3", "COMPLETED", "", "");
            listTab.add(categories);
        }

        @Override
        protected void onResume() {
            super.onResume();

        }

        @Override
        protected void onRestart() {
            super.onRestart();

        }
        public void updateMenu(){

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



            return true;
        }

        //xu ly su kien cho menu main
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

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
                startActivity(new Intent(OwnerMainActivity.this, InformationUserActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            //exit = 1
            if (index == 1) {
                startActivity(new Intent(OwnerMainActivity.this, UserLoginActivity.class));
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
                L.m("num "+num);
                Fragment  fragment=null;
                switch (num){
                    case 0:
                           fragment = OwnerOrderFragment.newInstance("", "", num);
                        break;
                    case 1:
                           fragment = OwnerProcessFragment.newInstance("", "", num);
                        break;
                    case 2:
                           fragment = OwnerCancelFragment.newInstance("", "", num);
                        break;
                    case 3:
                           fragment = OwnerCompleteFragment.newInstance("", "", num);
                        break;

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

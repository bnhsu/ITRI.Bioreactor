package org.itri.bioreactor2.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.autocontrol.AutomationController;
import org.itri.bioreactor2.controller.BioreactorController;
import org.itri.bioreactor2.data.database.DBHandler;
import org.itri.bioreactor2.data.tools.DataRecorder;
import org.itri.bioreactor2.ui.fragment.AutoControlFragment;
import org.itri.bioreactor2.ui.fragment.MonitorUIFragment;
import org.itri.bioreactor2.ui.fragment.ChartFragment;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar toolbar;
    private android.support.design.widget.TabLayout mTabs;
    private DataRecorder dataRecorder;
    static DBHandler db;
    static BioreactorController reactor;
    static AutomationController automationController;

    // String buffer for outgoing messages
    private SharedPreferences settings;
    public Context context;
    boolean isDebuging = false;

    private ViewPager mViewPager;
    private BottomNavigationView mNavigationView;
    private MenuItem prevMenuItem;
//    private SlidingTabLayout mSlidingTabLayout;
//    private TabsPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initViewPager();
        db = DBHandler.getInstance(this);
        reactor = BioreactorController.getInstance(this);
/*        try {
            automationController = AutomationController.getInstance(this, "cell1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("Error", "automation no action");
         }
 */
        // Initilization
        settings = this.getSharedPreferences("setting", 0);
        context = this;

        //init data recorder
        dataRecorder = new DataRecorder();
        dataRecorder.dataHeader = "[header] \r\n";

    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
                return true;
            }
        });
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

    }

    private void initViewPager(){

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        mNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_monitor:
                                mViewPager.setCurrentItem(0);
                                break;
                            case R.id.action_autocontrol:
                                mViewPager.setCurrentItem(1);
                                break;
                            case R.id.action_chart:
                                mViewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    mNavigationView.getMenu().getItem(0).setChecked(false);
                }
                mNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = mNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final String[] tabTitles = {
                getString(R.string.main_tab_monitor),
                getString(R.string.main_tan_autocontrol),
                getString(R.string.main_tab_history)
                //getString(R.string.main_tab_preference)
                //"流程測試"
        };
        private Fragment[] fragments = {
                //new AutoControlFragment(),
                new MonitorUIFragment(),
                new AutoControlFragment(),
                new ChartFragment(),
                //new AllSettingFragment()
        };

        public ViewPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            //return TabFragment.newInstance(position);
            return fragments[position];
        }
/*
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }
*/
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

/*
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.pager_item,
                    container, false);
            container.addView(view);
            TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText(String.valueOf(position + 1));
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
 */
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem menuSearchItem = menu.findItem(R.id.my_search);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menuSearchItem.getActionView();
        SearchView searchView = (SearchView) menuSearchItem.getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                // About option clicked.
                return true;
            case R.id.action_exit:
                // Exit option clicked.
                return true;
            case R.id.action_settings:
                // Settings option clicked.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startAutoControl(View view){
        //automationController = AutomationController.getInstance(this);
        Log.d("activty","start to AutoControl");
    }
}

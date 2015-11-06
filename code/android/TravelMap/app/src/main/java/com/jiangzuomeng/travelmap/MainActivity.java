package com.jiangzuomeng.travelmap;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.internal.view.menu.ListMenuPresenter;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.Toast;
import com.jiangzuomeng.Adapter.DrawerAdapter;
import com.jiangzuomeng.MyLayout.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

enum State{
        OnTrip,NotOnTrip
        }
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    CollectionPagerAdapter pagerAdapter;
    ViewPager viewPager;
    ActionBar actionBar;
    TabLayout tabLayout;
    State state;
    FloatingActionButton fab;
    FloatingActionButton tag;
    String []strs = new String[] {
            "遇见","美食","酒店"
    };
    ListView.OnItemClickListener onItemClickListener = null;
    PopupWindow setTagPopUpWindow;
    TabLayout.OnTabSelectedListener onTabSelectedListener;
    ViewPager.SimpleOnPageChangeListener simpleOnPageChangeListener;
    Button.OnClickListener btnOnclickListener;
    Button.OnLongClickListener btnOnLongClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("漫旅图");


        state = State.NotOnTrip;

        initMyListener();

        pagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager(),2);
        viewPager = (CustomViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        tabLayout.setOnTabSelectedListener(onTabSelectedListener);
        viewPager.setOnPageChangeListener(simpleOnPageChangeListener);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setLongClickable(true);
        fab.setOnClickListener(btnOnclickListener);
        fab.setOnLongClickListener(btnOnLongClickListener);

        tag = (FloatingActionButton) findViewById(R.id.set_tag);
        tag.setOnClickListener(btnOnclickListener);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ListView listView_drawer = (ListView)findViewById(R.id.drawer_listview);
        DrawerAdapter drawerAdapter = new DrawerAdapter(this);
        listView_drawer.setAdapter(drawerAdapter);
        listView_drawer.setOnItemClickListener(onItemClickListener);
    }

    private void initMyListener() {
        onItemClickListener = new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,SingleTravelActivity.class);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent);
            }
        };

        onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };

        simpleOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                tab.select();
            }

        };

        btnOnclickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fab:
                        fab_short_click(v);
                        break;
                    case R.id.set_tag:
                        set_tag_click(v);
                        break;
                }
            }
        };

        btnOnLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fab_long_click(v);
                return true;
            }
        };
    }

    private void set_tag_click(View v) {
        View popView = getLayoutInflater().inflate(R.layout.popup_set_tag, null);
        setTagPopUpWindow = new PopupWindow(popView, ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, true);
        setTagPopUpWindow.setTouchable(true);
        setTagPopUpWindow.setOutsideTouchable(true);
        setTagPopUpWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        ListView listView = (ListView)popView.findViewById(R.id.tag_listView);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_single_text,strs));
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tag_on_click_listener(position);
            }
        });
        setTagPopUpWindow.showAsDropDown(v);
    }

    private void tag_on_click_listener(int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.setText(strs[position]);
        setTagPopUpWindow.dismiss();
    }

    private void fab_long_click(View v) {
        if (state == State.OnTrip) {
            //stop the trip
//            Snackbar.make(v,"stop the trip", Snackbar.LENGTH_SHORT).show();
            Toast toast = Toast.makeText(getApplicationContext(), "the trip has stopped", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
            toast.show();
            state = State.NotOnTrip;
            fab.setBackgroundResource(R.mipmap.ic_note_add_black_24dp);
        } else {

        }
    }

    private void fab_short_click(View view) {
        if (state == State.NotOnTrip) {
        View popView= getLayoutInflater().inflate(R.layout.popup_create_travel,null);
        final PopupWindow popupWindow = new PopupWindow(popView, ActionBar.LayoutParams.WRAP_CONTENT,
                                                        ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        Button button = (Button)popView.findViewById(R.id.start_trip_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //fab.setBackgroundResource(R.mipmap.ic_camera_enhance_black_48dp);
                fab.setBackground(getResources().getDrawable(R.mipmap.ic_camera_enhance_black_48dp));
                state = State.OnTrip;
            }
        });
//        popupWindow.showAsDropDown(view, 0, 0, Gravity.TOP|Gravity.CENTER);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }
        else {
            //// TODO: 2015/10/27 camera
            Toast toast = Toast.makeText(getApplicationContext(), "start the camera", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("搜索地点,标签或用户");
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, Search_Result_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Search_Result_Activity.KEYWORDS, query);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:

                return true;
            case R.id.action_notification:

                return true;
            case R.id.action_settings:

                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        return true;
    }

}

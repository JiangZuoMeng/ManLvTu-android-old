package com.jiangzuomeng.travelmap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.app.ActionBar;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.jiangzuomeng.Adapter.DrawerAdapter;
import com.jiangzuomeng.Adapter.SetTagAdapter;
import com.jiangzuomeng.MyLayout.CustomViewPager;
import com.jiangzuomeng.dataManager.DataManager;
import com.jiangzuomeng.database.DBManager;
import com.jiangzuomeng.module.Travel;
import com.jiangzuomeng.module.TravelItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;

enum State{
        OnTrip,NotOnTrip
        }
public class MainActivity extends AppCompatActivity implements AMapFragment.MainActivityListener {
    public  static final String LOCATION_LNG_KEY = "locationlng";
    public  static final String LOCATION_LAT_KEY = "locationlat";
    int userId;
    String userName;
    CollectionPagerAdapter pagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    State state;
    double locationLng = 0;
    double locationLat = 0;
    FloatingActionButton fab;
    FloatingActionButton tag;
    String []strs = new String[] {
            "遇见","美食","酒店"
    };
    ListView.OnItemClickListener onItemClickListener = null;
    ListView.OnItemLongClickListener onItemLongClickListener;
    PopupWindow setTagPopUpWindow;
    TabLayout.OnTabSelectedListener onTabSelectedListener;
    ViewPager.SimpleOnPageChangeListener simpleOnPageChangeListener;
    Button.OnClickListener btnOnclickListener;
    Button.OnLongClickListener btnOnLongClickListener;

    Button addOtherTagBtn;
    EditText addTagEditText;
    SetTagAdapter setTagAdapter;

    DrawerAdapter drawerAdapter;

    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataManager = DataManager.getInstance(getApplication());
        state = State.NotOnTrip;
        userName = getIntent().getStringExtra(LoginActivity.INTENT_USER_NAME_KEY);
        userId = dataManager.queryUserByUserName(userName).id;
        initMyListener();
        initMyAdapter();
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
        listView_drawer.setAdapter(drawerAdapter);
        listView_drawer.setOnItemClickListener(onItemClickListener);

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.setText("No Travel On");

    }

    private void initMyAdapter() {
        List<String> imageList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();



        drawerAdapter = new DrawerAdapter(imageList, titleList, this);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            strings.add(Integer.toString(i));
        setTagAdapter = new SetTagAdapter(strings, this);
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
                    case R.id.addOtherTagBtn:
                        addOtherTag();
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
        onItemLongClickListener = new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setTagAdapter.removeAt(position);
                setTagAdapter.notifyDataSetChanged();
                return true;
            }
        };
    }

    private void addOtherTag() {
        if(!addTagEditText.getText().toString().equals("")) {
            setTagAdapter.getStrings().add(addTagEditText.getText().toString());
            setTagAdapter.getIsSelectList().add(setTagAdapter.getStrings().size() - 1, true);
            setTagAdapter.notifyDataSetChanged();
            addTagEditText.setText("");
        }
    }

    private void set_tag_click(View v) {
        View popView = getLayoutInflater().inflate(R.layout.popup_set_tag, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popView);
        builder.setTitle("请选择筛选标签");
        ListView listView = (ListView)popView.findViewById(R.id.tag_listView);
        addOtherTagBtn = (Button)popView.findViewById(R.id.addOtherTagBtn);
        addOtherTagBtn.setOnClickListener(btnOnclickListener);
        addTagEditText = (EditText)popView.findViewById(R.id.AddTagEditText);
        listView.setAdapter(setTagAdapter);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SetTagAdapter.ViewHolder viewHolder = (SetTagAdapter.ViewHolder) view.getTag();
                viewHolder.checkBox.toggle();
                setTagAdapter.getIsSelectList().set(position, viewHolder.checkBox.isChecked());
//                tag_on_click_listener(position);
            }
        });
        listView.setOnItemLongClickListener(onItemLongClickListener);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
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
            fab.setBackgroundResource(R.drawable.ic_note_add_black_24dp);
        } else {

        }
    }

    private void fab_short_click(View view) {
        if (state == State.NotOnTrip) {
        View popView= getLayoutInflater().inflate(R.layout.popup_create_travel,null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText nameEdittext = (EditText)popView.findViewById(R.id.travelNameEditText);
            builder.setView(popView);
            builder.setIcon(R.mipmap.apple_touch_icon);
            builder.setTitle(R.string.dialogTitle);
            builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (nameEdittext.getText().toString().equals("")) {
                        Toast toast = Toast.makeText(MainActivity.this, "请输入旅途名称", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        toast.show();
                        return;
                    }
                    state = State.OnTrip;
                    Travel travel = new Travel();
                    travel.userId = userId;
                    travel.name = nameEdittext.getText().toString();
                    long temp = dataManager.addNewTravel(travel);
                    TabLayout.Tab tab = tabLayout.getTabAt(0);
                    tab.setText(nameEdittext.getText().toString());
                }
            });
            builder.show();
        }
        else {
            //// TODO: 2015/10/27 camera
            Intent intent = new Intent(this, CreateNewItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble(LOCATION_LAT_KEY, locationLat);
            bundle.putDouble(LOCATION_LNG_KEY, locationLng);
            intent.putExtras(bundle);
            startActivity(intent);
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
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void notifyLocation(double locationLng, double locationLat) {
        this.locationLat = locationLat;
        this.locationLng = locationLng;
    }
}

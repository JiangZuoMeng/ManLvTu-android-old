package com.jiangzuomeng.travelmap;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiangzuomeng.dataManager.DataManager;
import com.jiangzuomeng.modals.TravelItem;
import com.jiangzuomeng.modals.User;
import com.jiangzuomeng.networkManager.NetWorkManager;
import com.jiangzuomeng.modals.StaticStrings;

public class TestNetworkActivity extends AppCompatActivity {
    public static final int UPDATE = 121323;
    public static final String UPDATESTRING = "updateString";
    Button button;
    TextView textView;
    NetWorkManager netWorkManager;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    Bundle bundle = msg.getData();
                    String string = bundle.getString(UPDATESTRING);
                    textView.setText(string);
                    break;
                case StaticStrings.NETWORK_OPERATION:
                    Bundle bundle1 = msg.getData();
                    String s = bundle1.getString(StaticStrings.NETWORK_RESULT_KEY);
                    Log.v("wilbert", s);
            }
        }
    };
    DataManager dataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_network);

        button = (Button)findViewById(R.id.testNetworkbutton);
        textView = (TextView)findViewById(R.id.testNetworktextView);
        netWorkManager = new NetWorkManager();
        dataManager = DataManager.getInstance(getApplicationContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.username = "asdadf";
                user.password = "sadfawewwea";
                user.id = 232;
                dataManager.addNewUser(user, handler);
            }
        });
    }
}

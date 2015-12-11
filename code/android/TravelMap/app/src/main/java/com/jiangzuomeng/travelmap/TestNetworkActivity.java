package com.jiangzuomeng.travelmap;

import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiangzuomeng.module.Comment;
import com.jiangzuomeng.module.Travel;
import com.jiangzuomeng.module.TravelItem;
import com.jiangzuomeng.module.User;
import com.jiangzuomeng.networkManager.NetWorkManager;

import java.io.IOException;

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
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_network);

        button = (Button)findViewById(R.id.testNetworkbutton);
        textView = (TextView)findViewById(R.id.testNetworktextView);
        netWorkManager = new NetWorkManager();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            netWorkManager.addNewUser(new User());
                            netWorkManager.addNewComment(new Comment());
                            netWorkManager.addNewTravel(new Travel());
                            netWorkManager.addNewTravelItem(new TravelItem());

                            netWorkManager.queryTravelByTravelId(10);
                            netWorkManager.queryCommentByCommentId(10);
                            netWorkManager.queryTravelItemByTravelItemId(10);
                            netWorkManager.queryUserById(10);
                            netWorkManager.queryUserByUsername("etes");

                            netWorkManager.removeCommentByCommentId(10);
                            netWorkManager.removeTravelByTravelId(10);
                            netWorkManager.removeTravelItemByTravelItemId(10);
                            netWorkManager.removeUserByUserId(10);

                            netWorkManager.updateComment(new Comment());
                            netWorkManager.updateTravel(new Travel());
                            netWorkManager.updateTravelItem(new TravelItem());
                            netWorkManager.updateUser(new User());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
    }
}

package com.jiangzuomeng.travelmap;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jiangzuomeng.dataManager.DataManager;
import com.jiangzuomeng.modals.Comment;
import com.jiangzuomeng.modals.TravelItem;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumDetailsActivity extends AppCompatActivity {
    ListView commentsView;
    TravelItem travelItem;
    DataManager dataManager;
    SimpleAdapter adapter;
    List<Map<String, Object>> cmmtsData;

    private final static int MSG_COMMENT_GOT = 1;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handler = new CommentHandler();
        dataManager = DataManager.getInstance(this);

        commentsView = (ListView) findViewById(R.id.comments);
        cmmtsData = new ArrayList<>();
        adapter = new SimpleAdapter(AlbumDetailsActivity.this, cmmtsData, R.layout.album_item,
                new String[]{"album_item_userlogo", "cmmnt_user", "cmmnt_text", "cmmnt_time"},
                new int[]{R.id.album_item_userlogo, R.id.cmmnt_user, R.id.cmmnt_text, R.id.cmmnt_time});
        commentsView.setAdapter(adapter);

        Intent intent = getIntent();
        final Bundle bun = intent.getExtras();
        if (bun != null) {
            travelItem = ((TravelItem) bun.getSerializable(AlbumViewerActivity.INTERT_TRAVEL_ITEM_OBJECT));
            if (travelItem != null) {
                // TODO queryCommentListBy 添加handler参数
                TextView userState = (TextView) findViewById(R.id.album_details_user_state);
                userState.setText(travelItem.text);

                TextView dateText = (TextView) findViewById(R.id.album_time);
                dateText.setText(travelItem.time);

                dataManager.queryCommentListByTravelItemId(travelItem.travelId);
            }
        }
        final EditText myComment = (EditText) findViewById(R.id.album_my_comments);

        commentsView = (ListView) findViewById(R.id.comments);
        Button sendCmmtBtn = (Button) findViewById(R.id.send_cmmnt_btn);
        sendCmmtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment newComment = new Comment();
                newComment.text = myComment.getText().toString();
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                newComment.time = android.text.format.DateFormat.format("MM/dd hh:mm", date).toString();
                newComment.userId = MainActivity.userId;
                dataManager.addNewComment(newComment, handler);
                myComment.setText("");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    private class CommentHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_COMMENT_GOT:
                    List<Comment> cmmts = (List<Comment>) msg.obj;
                    onCommentsUpdate(cmmts);
                    break;
            }
        }

        private void onCommentsUpdate(List<Comment> cmmts) {
            List<Map<String, Object>> data = new ArrayList<>();
            for (Comment cmt : cmmts) {
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("album_item_userlogo", R.drawable.photo0);
                itemData.put("cmmnt_user", cmt.userId);
                itemData.put("cmmnt_text", cmt.text);
                itemData.put("cmmnt_time", cmt.time);
                data.add(itemData);
            }
            cmmtsData = data;
            adapter.notifyDataSetChanged();
        }
    }


}

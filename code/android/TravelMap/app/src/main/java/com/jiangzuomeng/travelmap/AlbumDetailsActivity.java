package com.jiangzuomeng.travelmap;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jiangzuomeng.dataManager.DataManager;
import com.jiangzuomeng.module.Comment;
import com.jiangzuomeng.module.TravelItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumDetailsActivity extends AppCompatActivity {
    ListView commentsView;
    TravelItem travelItem;

    private final static int MSG_COMMENT_GOT = 1;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        final Bundle bun = intent.getExtras();
        if (bun != null) {
            travelItem = ((TravelItem) bun.getSerializable(AlbumViewerActivity.INTERT_TRAVEL_ITEM_OBJECT));
            if (travelItem != null) {

            }
        }

        handler = new CommentHandler();

        commentsView = (ListView) findViewById(R.id.comments);

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
                    List<Comment> cmts = (List<Comment>) msg.obj;
                    onCommentsUpdate(cmts);
                    break;
            }
        }

        private void onCommentsUpdate(List<Comment> cmmts) {
            List<Map<String, Object>> data = new ArrayList<>();
            for (Comment cmt : cmmts) {
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("album_item_userlogo", R.drawable.photo0);
                itemData.put("cmmnt_user", "Chrom");
                itemData.put("cmmnt_text", "内存不够吃");
                itemData.put("cmmnt_time", "2014.12.Nov");
                data.add(itemData);
            }
            SimpleAdapter adapter = new SimpleAdapter(AlbumDetailsActivity.this, data, R.layout.album_item,
                new String[]{"album_item_userlogo", "cmmnt_user", "cmmnt_text", "cmmnt_time"},
                new int[]{R.id.album_item_userlogo, R.id.cmmnt_user, R.id.cmmnt_text, R.id.cmmnt_time});
            commentsView.setAdapter(adapter);


        }
    }
    private class GetCommentTask extends AsyncTask<Integer, Comment, Boolean> {
        @Override
        protected Boolean doInBackground(Integer ... traveItemIds) {
            int traveitemId = traveItemIds[0];
            DataManager dataManager = DataManager.getInstance(AlbumDetailsActivity.this);
            dataManager.queryCommentListByTravelItemId(traveitemId);
            return true;
        }
    }

//    private List<Map<String, Object>> getTmpData() {
//        List<Map<String, Object>> data = new ArrayList<>();
//        Map<String, Object> itemData = new HashMap<>();
//        itemData.put("album_item_userlogo", R.drawable.photo0);
//        itemData.put("cmmnt_user", "Chrom");
//        itemData.put("cmmnt_text", "内存不够吃");
//        itemData.put("cmmnt_time", "2014.12.Nov");
//        data.add(itemData);
//
//        itemData = new HashMap<>();
//        itemData.put("album_item_userlogo", R.drawable.photo1);
//        itemData.put("cmmnt_user", "天凉好个秋");
//        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
//        itemData.put("cmmnt_time", "2015.12.23 Nov");
//        data.add(itemData);
//        itemData = new HashMap<>();
//        itemData.put("album_item_userlogo", R.drawable.photo1);
//        itemData.put("cmmnt_user", "天凉好个秋");
//        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
//        itemData.put("cmmnt_time", "2015.12.23 Nov");
//        data.add(itemData);
//        itemData = new HashMap<>();
//        itemData.put("album_item_userlogo", R.drawable.photo1);
//        itemData.put("cmmnt_user", "天凉好个秋");
//        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
//        itemData.put("cmmnt_time", "2015.12.23 Nov");
//        data.add(itemData);
//        itemData = new HashMap<>();
//        itemData.put("album_item_userlogo", R.drawable.photo1);
//        itemData.put("cmmnt_user", "天凉好个秋");
//        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
//        itemData.put("cmmnt_time", "2015.12.23 Nov");
//        data.add(itemData);
//        itemData = new HashMap<>();
//        itemData.put("album_item_userlogo", R.drawable.photo1);
//        itemData.put("cmmnt_user", "天凉好个秋");
//        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
//        itemData.put("cmmnt_time", "2015.12.23 Nov");
//        data.add(itemData);
//        itemData = new HashMap<>();
//        itemData.put("album_item_userlogo", R.drawable.photo1);
//        itemData.put("cmmnt_user", "天凉好个秋");
//        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
//        itemData.put("cmmnt_time", "2015.12.23 Nov");
//        data.add(itemData);
//        return data;
//    }


}

package com.jiangzuomeng.travelmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumDetailsActivity extends AppCompatActivity {
    ListView commentsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        commentsView = (ListView) findViewById(R.id.comments);

        SimpleAdapter adapter = new SimpleAdapter(this, getTmpData(), R.layout.album_item,
                            new String[]{"album_item_userlogo", "cmmnt_user", "cmmnt_text", "cmmnt_time"},
                            new int[]{R.id.album_item_userlogo, R.id.cmmnt_user, R.id.cmmnt_text, R.id.cmmnt_time});
        commentsView.setAdapter(adapter);
        commentsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AlbumDetailsActivity.this, String.valueOf(position) + " " + String.valueOf(id), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Map<String, Object>> getTmpData() {
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> itemData = new HashMap<>();
        itemData.put("album_item_userlogo", R.drawable.photo0);
        itemData.put("cmmnt_user", "Chrom");
        itemData.put("cmmnt_text", "内存不够吃");
        itemData.put("cmmnt_time", "2014.12.Nov");
        data.add(itemData);

        itemData = new HashMap<>();
        itemData.put("album_item_userlogo", R.drawable.photo1);
        itemData.put("cmmnt_user", "天凉好个秋");
        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
        itemData.put("cmmnt_time", "2015.12.23 Nov");
        data.add(itemData);
        itemData = new HashMap<>();
        itemData.put("album_item_userlogo", R.drawable.photo1);
        itemData.put("cmmnt_user", "天凉好个秋");
        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
        itemData.put("cmmnt_time", "2015.12.23 Nov");
        data.add(itemData);
        itemData = new HashMap<>();
        itemData.put("album_item_userlogo", R.drawable.photo1);
        itemData.put("cmmnt_user", "天凉好个秋");
        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
        itemData.put("cmmnt_time", "2015.12.23 Nov");
        data.add(itemData);
        itemData = new HashMap<>();
        itemData.put("album_item_userlogo", R.drawable.photo1);
        itemData.put("cmmnt_user", "天凉好个秋");
        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
        itemData.put("cmmnt_time", "2015.12.23 Nov");
        data.add(itemData);
        itemData = new HashMap<>();
        itemData.put("album_item_userlogo", R.drawable.photo1);
        itemData.put("cmmnt_user", "天凉好个秋");
        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
        itemData.put("cmmnt_time", "2015.12.23 Nov");
        data.add(itemData);
        itemData = new HashMap<>();
        itemData.put("album_item_userlogo", R.drawable.photo1);
        itemData.put("cmmnt_user", "天凉好个秋");
        itemData.put("cmmnt_text", "少吃点会死吗，少吃点又不会死");
        itemData.put("cmmnt_time", "2015.12.23 Nov");
        data.add(itemData);
        return data;
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

    private class DownloadAlbum extends {
    }
}

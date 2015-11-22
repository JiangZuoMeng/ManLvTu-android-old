package com.jiangzuomeng.travelmap;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.jiangzuomeng.Adapter.SetTagAdapter;
import com.jiangzuomeng.dataManager.DataManager;
import com.jiangzuomeng.module.TravelItem;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CreateNewItemActivity extends AppCompatActivity {
    private static final int CAMERA = 1000;
    private static final int PICK_IMAGE_REQUEST = 1234;
    private static  final int ID_START = 234;
    public static final int TRAVEL_ITEM_RESULT_SUCCESS_CODE = 4354;
    public static  final int TRAVEL_ITEM_RESULT_FAIL_CODE = 4355;
    ImageView image;
    View.OnClickListener onClickListener;
    AdapterView.OnItemClickListener onItemClickListener;
    ImageView.OnLongClickListener onLongClickListener;
    ListView.OnItemLongClickListener onItemLongClickListener;
    LinearLayout pictureLinearLayout;
    Uri fileUri;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    List<Integer> imageIds = new ArrayList<>();
    Button setTagBtn;
    PopupWindow setTagPopUpWindow;
    Button addOtherTagBtn;
    SetTagAdapter setTagAdapter;
    String tagTemp;
    EditText addTagEditText;
    ListView tagListView;
    EditText itemTextEditText;

    Double locationLng;
    Double locationLat;
    DataManager dataManager;
    List<String> imageStringList;
    List<String> labelStringList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_item);

        dataManager = DataManager.getInstance(getApplication());
        imageStringList = new ArrayList<>();
        labelStringList = new ArrayList<>();
        initMyListener();
        initMyAdapter();
        image = (ImageView)findViewById(R.id.Select_image);
        image.setOnClickListener(onClickListener);
        pictureLinearLayout = (LinearLayout)findViewById(R.id.createItemPictureLinearLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        itemTextEditText = (EditText)findViewById(R.id.ItemTexteditText);
        setTagBtn = (Button)findViewById(R.id.set_Tag_Btn);
        setTagBtn.setOnClickListener(onClickListener);

        Bundle bundle = getIntent().getExtras();
        locationLat = bundle.getDouble(MainActivity.LOCATION_LAT_KEY);
        locationLng = bundle.getDouble(MainActivity.LOCATION_LNG_KEY);
    }

    private void initMyAdapter() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            strings.add(Integer.toString(i));
        setTagAdapter = new SetTagAdapter(strings, this);
    }

    private void initMyListener() {
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.Select_image:
                        showSelectionDialog();
                        break;
                    case R.id.set_Tag_Btn:
                        showSelectionTagPop(v);
                    case R.id.addOtherTagBtn:
                        addOtherTag(v);
                }
            }
        };


        onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("wilbert", "item click");
                switch (position) {
                    case 0:
                        //start camera
                        fileUri = getOutImageFileUri();
                        Log.v("wilbert", fileUri.toString());
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA);
                        dialog.dismiss();
                        break;
                    case 1:
                        //start choose
                        Intent chooseintent = new Intent();
                        chooseintent.setType("image/*");
                        chooseintent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(chooseintent, "choose a picture"), PICK_IMAGE_REQUEST);
                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        };
        onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pictureLinearLayout.removeView(v);
                return true;
            }
        };
        onItemLongClickListener = new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int delete = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewItemActivity.this);
                builder.setMessage("确认删除吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setTagAdapter.removeAt(delete);
                        Log.v("wilbert", "remove map strings");
                        setTagAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                //tagListView.setAdapter(setTagAdapter);
                return true;
            }
        };
    }

    private Uri getOutImageFileUri() {
        return Uri.fromFile(getOutputImageFile());
    }

    private File getOutputImageFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "TravelMap");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
        return mediaFile;
    }

    private void addOtherTag(View v) {
        if(!addTagEditText.getText().toString().equals("")) {
            setTagAdapter.getStrings().add(addTagEditText.getText().toString());
            setTagAdapter.getIsSelectList().add(setTagAdapter.getStrings().size()-1, false);
            setTagAdapter.notifyDataSetChanged();
            addTagEditText.setText("");
        }
    }

    private void showSelectionTagPop(View v) {
        View popView = getLayoutInflater().inflate(R.layout.popup_set_tag, null);
        setTagPopUpWindow = new PopupWindow(popView, ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, true);
        setTagPopUpWindow.setTouchable(true);
        setTagPopUpWindow.setOutsideTouchable(true);
        setTagPopUpWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        tagListView = (ListView)popView.findViewById(R.id.tag_listView);
        addOtherTagBtn = (Button)popView.findViewById(R.id.addOtherTagBtn);
        addOtherTagBtn.setOnClickListener(onClickListener);
        addTagEditText = (EditText)popView.findViewById(R.id.AddTagEditText);
        tagListView.setAdapter(setTagAdapter);
        tagListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SetTagAdapter.ViewHolder viewHolder = (SetTagAdapter.ViewHolder) view.getTag();
                viewHolder.checkBox.toggle();
                setTagAdapter.getIsSelectList().set(position, viewHolder.checkBox.isChecked());
            }
        });
        tagListView.setOnItemLongClickListener(onItemLongClickListener);
        setTagPopUpWindow.showAsDropDown(v);
    }


    private void showSelectionDialog() {
        View popView= getLayoutInflater().inflate(R.layout.dialog_camera_select,null);

        builder = new AlertDialog.Builder(this);
        ListView listView = (ListView)popView.findViewById(R.id.camera_select_listview);
        builder.setView(popView);
        String string[] = new String[] {"拍照", "选择照片"};
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, string));
        listView.setOnItemClickListener(onItemClickListener);
        dialog = builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_confirm:
                String editText = itemTextEditText.getText().toString();
                String imageString = null;
                if (imageStringList.size() > 0)
                    imageString = imageStringList.get(0).toString();
                for (int i = 1; i < imageStringList.size(); i++) {
                    imageString = ";"+imageStringList.get(i).toString();
                }
                labelStringList = getLabelStringList();
                String labelString = null;
                if (labelStringList.size() > 0)
                    labelString = labelStringList.get(0).toString();
                for (int i = 1; i < labelStringList.size(); i++) {
                    labelString = ";" + labelStringList.get(i).toString();
                }
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                TravelItem travelItem = new TravelItem();
                travelItem.text = editText;
                travelItem.label = labelString;
                travelItem.locationLat = locationLat;
                travelItem.locationLng = locationLng;
                travelItem.media = imageString;
                travelItem.time = timeStamp;
                long temp = dataManager.addNewTravelItem(travelItem);
                Log.v("wilbert", "travel item " + temp);
                finish();
                break;
        }
        return true;
    }

    private List<String> getLabelStringList() {
        List<String> tagString = new ArrayList<>();
        for (int i = 0; i < setTagAdapter.getIsSelectList().size() && i < setTagAdapter
                .getStrings().size(); i++) {
            if (setTagAdapter.getIsSelectList().get(i)) {
                tagString.add(setTagAdapter.getStrings().get(i));
            }
        }
        return tagString;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_new_item, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode  == CAMERA) {
            if(resultCode == RESULT_OK) {
                try {
                    imageStringList.add(fileUri.toString());
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
                    ImageView imageView = new ImageView(this);
                    Resources resources = getResources();
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    float px = 90 * (displayMetrics.densityDpi / 160f);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams((int)px, (int)px));
                    imageView.setImageBitmap(bitmap);
                    imageView.setPadding(5, 5, 5, 5);
                    imageView.setId(ID_START+imageIds.size()+1);
                    pictureLinearLayout.addView(imageView);
                    imageView.setOnLongClickListener(onLongClickListener);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                imageStringList.add(uri.toString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Resources resources = getResources();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                float px = 90 * (displayMetrics.densityDpi / 160f);
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams((int) px, (int) px));
                imageView.setImageBitmap(bitmap);
                imageView.setId(ID_START+imageIds.size()+1);

                imageView.setPadding(5, 5, 5, 5);
                pictureLinearLayout.addView(imageView);
                imageView.setOnLongClickListener(onLongClickListener);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

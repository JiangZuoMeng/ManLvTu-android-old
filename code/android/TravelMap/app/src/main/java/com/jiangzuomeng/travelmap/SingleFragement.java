package com.jiangzuomeng.travelmap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;


/**
 * Created by wilbert on 2015/10/27.
 */
public class SingleFragement extends Fragment implements View.OnClickListener{
    public static final String TYPE = "type";
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //case: R.id.button
        }
    }

    public interface ButtonOnclick {
        public void test_onclick();
    }
    ButtonOnclick btnclick;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        btnclick = (ButtonOnclick)activity;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_single, container, false);
        Bundle args = getArguments();
                ((TextView) (view.findViewById(R.id.testtextView))).setText(args.getString(TYPE));
        Button button = (Button)view.findViewById(R.id.buttonTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnclick.test_onclick();
            }
        });

        //mapView = (MapView)view.findViewById(R.id.mapView);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}

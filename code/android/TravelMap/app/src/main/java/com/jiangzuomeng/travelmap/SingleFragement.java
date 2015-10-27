package com.jiangzuomeng.travelmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wilbert on 2015/10/27.
 */
public class SingleFragement extends Fragment {
    public static final String TYPE = "type";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single, container,false);
        Bundle args = getArguments();
                ((TextView) (view.findViewById(R.id.testtextView))).setText(args.getString(TYPE));
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}

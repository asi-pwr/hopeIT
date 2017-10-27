package com.asi.hopeitapp.MainPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asi.hopeitapp.Main.BaseFragment;
import com.asi.hopeitapp.R;

import butterknife.ButterKnife;

public class MainPageFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.main_page_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}

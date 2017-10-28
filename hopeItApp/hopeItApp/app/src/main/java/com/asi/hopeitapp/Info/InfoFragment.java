package com.asi.hopeitapp.Info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asi.hopeitapp.Main.BaseFragment;

import com.asi.hopeitapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoFragment extends BaseFragment {

    @BindView(R.id.info_image)
    ImageView image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.info_fragment, container, false);
        ButterKnife.bind(this, view);

        Glide.with(this)
                .load("https://scontent.fbud2-1.fna.fbcdn.net/v/t1.0-9/21317783_287967174944017_6080547494431629676_n.png?oh=1bd0b43fda6227221465b49ca7c4df35&oe=5A79252D")
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.heart_place_holder)
                .into(image);

        return view;
    }

}

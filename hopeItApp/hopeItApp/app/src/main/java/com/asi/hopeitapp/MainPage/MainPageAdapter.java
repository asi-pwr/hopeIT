package com.asi.hopeitapp.MainPage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.hopeitapp.Model.Patient;
import com.asi.hopeitapp.R;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.Holder> {

    private List<Patient> patients;
    private RequestManager glide;

    MainPageAdapter(List<Patient> patients, RequestManager glide) {
        this.patients = patients;
        this.glide = glide;
    }

    @Override
    public MainPageAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_patient, parent, false);

        return new MainPageAdapter.Holder(inflatedView);
    }

    @Override
    public void onBindViewHolder(MainPageAdapter.Holder holder, int position) {
        Patient patient = patients.get(position);

        holder.name.setText(patient.getName());
        holder.description.setText(patient.getDescription());

        glide.load(patient.getPhoto())
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.picture);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.patientItemName)
        TextView name;
        @BindView(R.id.patientItemDescription)
        TextView description;
        @BindView(R.id.patientItemPicture)
        ImageView picture;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
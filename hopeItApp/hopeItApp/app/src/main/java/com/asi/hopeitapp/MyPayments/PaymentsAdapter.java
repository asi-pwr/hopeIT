package com.asi.hopeitapp.MyPayments;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.hopeitapp.Model.Patient;
import com.asi.hopeitapp.Model.Payment;
import com.asi.hopeitapp.Payments.CurrencyFormatter;
import com.asi.hopeitapp.R;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.payu.android.sdk.payment.model.Currency;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.Holder> {

    private List<Payment> payments;
    private RequestManager glide;
    private Context context;

    PaymentsAdapter(List<Payment> payments, RequestManager glide, Context context) {
        this.payments = payments;
        this.glide = glide;
        this.context = context;
    }

    @Override
    public PaymentsAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_payment, parent, false);

        return new PaymentsAdapter.Holder(inflatedView);
    }

    @Override
    public void onBindViewHolder(PaymentsAdapter.Holder holder, int position) {
        Payment payment = payments.get(position);
        List<Patient> patients = Patient.listAll(Patient.class);
        Patient patient = patients.get(payment.getPatientId()-1);

        holder.name.setText(patient.getName());
        holder.cash.setText(CurrencyFormatter.format(payment.getAmount(), Currency.PLN));


        glide.load(patient.getPhoto())
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.heart_place_holder)
                .into(holder.picture);


        holder.name.setOnClickListener(view -> showDetailsDialog(patient));
        holder.cash.setOnClickListener(view -> showDetailsDialog(patient));
        holder.picture.setOnClickListener(view -> showDetailsDialog(patient));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.payment_patient_name)
        TextView name;
        @BindView(R.id.payment_value)
        TextView cash;
        @BindView(R.id.payment_image)
        ImageView picture;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void showDetailsDialog(Patient patient){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_patinet_dialog);

        dialog.show();

        TextView name = dialog.findViewById(R.id.patientName);
        name.setText(patient.getName());

        TextView description = dialog.findViewById(R.id.patientDescription);
        description.setText(patient.getDescription());

        ImageView picture = dialog.findViewById(R.id.patientPicture);

        Button payButton = dialog.findViewById(R.id.patientPayButton);
        payButton.setVisibility(View.GONE);

        glide.load(patient.getPhoto())
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.heart_place_holder)
                .into(picture);
    }
}
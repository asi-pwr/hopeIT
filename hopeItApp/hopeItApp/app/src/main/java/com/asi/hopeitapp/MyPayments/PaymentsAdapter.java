package com.asi.hopeitapp.MyPayments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asi.hopeitapp.Model.Payment;
import com.asi.hopeitapp.R;
import com.bumptech.glide.RequestManager;

import java.util.List;

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

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
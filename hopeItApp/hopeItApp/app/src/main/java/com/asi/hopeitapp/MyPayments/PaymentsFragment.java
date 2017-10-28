package com.asi.hopeitapp.MyPayments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asi.hopeitapp.Main.BaseFragment;
import com.asi.hopeitapp.Model.Patient;
import com.asi.hopeitapp.Model.Payment;
import com.asi.hopeitapp.Payments.CurrencyFormatter;
import com.asi.hopeitapp.R;
import com.bumptech.glide.Glide;
import com.payu.android.sdk.payment.model.Currency;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentsFragment extends BaseFragment {

    @BindView(R.id.mainPageRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.stats)
    CardView statsCard;
    @BindView(R.id.donates)
    TextView donates;
    @BindView(R.id.cash)
    TextView cash;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.my_payments_fragment, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    protected void loadContent() {
        super.loadContent();
        loadRecyclerView();
    }

    private void loadRecyclerView(){
        List<Payment> payments;

        try {
            payments = Patient.listAll(Payment.class);
        }
        catch (Exception e){
            recyclerView.setVisibility(View.GONE);
            return;
        }

        if(payments.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            return;
        }

        PaymentsAdapter adapter = new PaymentsAdapter(payments, Glide.with(this), getContext());
        recyclerView.setAdapter(adapter);

        int cashSum = 0;
        for (Payment payment : payments){
            cashSum += payment.getAmount();
        }

        donates.setText(String.format(Locale.getDefault(), "Wpłaty: %d", payments.size()));
        cash.setText(CurrencyFormatter.format(cashSum, Currency.PLN));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dx != 0){
                    statsCard.setVisibility(View.GONE);
                }
                else {
                    statsCard.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

package com.asi.hopeitapp.Messages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asi.hopeitapp.Main.BaseFragment;
import com.asi.hopeitapp.Model.Message;
import com.asi.hopeitapp.Model.Patient;
import com.asi.hopeitapp.Model.Payment;
import com.asi.hopeitapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesFragment extends BaseFragment {

    @BindView(R.id.messagesRecyclerView)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.messages_fragment, container, false);
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
        List<Message> messages;

        try {
            messages = Patient.listAll(Message.class);
        }
        catch (Exception e){
            recyclerView.setVisibility(View.GONE);
            return;
        }

        if(messages.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            return;
        }

        MessagesAdapter adapter = new MessagesAdapter(messages, Glide.with(this), getContext());
        recyclerView.setAdapter(adapter);
    }
}

package com.example.noworderfoodapp.view.act;

import android.net.Uri;
import android.view.View;
import android.widget.ScrollView;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.ChatFragmentBinding;
import com.example.noworderfoodapp.entity.Message;
import com.example.noworderfoodapp.entity.User;
import com.example.noworderfoodapp.view.adapter.ChatAdapter;
import com.example.noworderfoodapp.viewmodel.ChatViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends BaseActivity<ChatFragmentBinding, ChatViewModel> {
    private User receiverUser;
    private String senderUserName;
    private ChatAdapter chatAdapter;


    @Override
    protected Class<ChatViewModel> getViewModelClass() {
        return ChatViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.chat_fragment;
    }

    @Override
    protected void initViews() {
        User receiverUser = (User) getIntent().getSerializableExtra("RECEIVER_USER");
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        };

        binding.scrollView.post(runnable);
        senderUserName = String.valueOf(App.getInstance().getUser().getUsername());
        viewModel.setChatRoom(senderUserName, receiverUser.getUsername()+"");
        viewModel.loadMessage();

        Glide.with(this).load(Uri.parse(receiverUser.getAvatarUrl())).into(binding.receiveUserAvatar);
        binding.receiveUserName.setText(receiverUser.getName());
        binding.sendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messText = binding.sendingMess.getText().toString();
                if(!messText.equals("")){
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    viewModel.sendMessage(senderUserName, receiverUser.getName()+"", messText, date);
                    binding.sendingMess.setText("");
                    scrollToBottom();
                }
            }
        });
        binding.messageRecycleView.setHasFixedSize(true);
        binding.messageRecycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.messageRecycleView.setNestedScrollingEnabled(false);
        chatAdapter = new ChatAdapter(viewModel.getMessages(), receiverUser.getAvatarUrl());
        binding.messageRecycleView.setAdapter(chatAdapter);

        binding.sendingMess.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scrollToBottom();
            }
        });

        binding.sendingMess.setOnClickListener(v -> scrollToBottom());


        viewModel.getMessageLiveData().observe(this, new Observer<ArrayList<Message>>() {
            @Override
            public void onChanged(ArrayList<Message> messages) {
                chatAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.seenMess();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void scrollToBottom() {
        binding.scrollView.smoothScrollTo(0, binding.scrollView.getBottom());
    }

    public void getUser(User user){
        this.receiverUser = user;
    }


    @Override
    public void callBack(String key, Object data) {

    }
}

package com.example.noworderfoodapp.viewmodel;

import android.util.Base64;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.EncryptionUtils;
import com.example.noworderfoodapp.entity.Message;
import com.example.noworderfoodapp.view.act.ChatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChatViewModel extends ViewModel {

    protected final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference;
    private String chatRoom;
    private MutableLiveData<ArrayList<Message>> messageLiveData;
    private ArrayList<Message> messages;
    private ValueEventListener seenListener;

    public ChatViewModel(){
        messageLiveData = new MutableLiveData<>();
        messages = new ArrayList<>();
    }

    public void setChatRoom(String sendUid, String receiveUid){
        if(sendUid.compareTo(receiveUid) > 0){
            chatRoom =  sendUid+receiveUid;
        }
        else{
            chatRoom =  receiveUid+sendUid;
        }
    }

    public void sendMessage(String senderUserUid,String receiverUserUid, String message, String timestamp){
        try {
        String encryptedMessage = EncryptionUtils.encrypt(message, "123");
          //  String base64EncodedMessage = Base64.encodeToString(encryptedMessage.getBytes(), Base64.DEFAULT);

            Message mess = new Message(encryptedMessage, senderUserUid, receiverUserUid, timestamp, false);

        reference.push().setValue(mess).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {


                    Log.d("SendMessage", "Message sent successfully");

                } else {
                    // Xử lý trường hợp tin nhắn gửi thất bại
                    // ...
                }
            }
        });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMessage(){
        reference = firebaseDatabase.getReference().child("all-chat").child(chatRoom);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Message mess = dataSnapshot.getValue(Message.class);
                    try {
                        String decryptedMessage = EncryptionUtils.decrypt(mess.getMessage(), "123");
                      //  String base64DEncodedMessage = new String(decryptedMessage.getBytes(), StandardCharsets.UTF_8);
                        mess.setMessage(decryptedMessage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    messages.add(mess);

                    DatabaseReference lastMessReference = firebaseDatabase
                            .getReference("all-chat")
                            .child("last_mess")
                            .child(chatRoom);
                    lastMessReference.setValue(mess);
                }
                messageLiveData.postValue(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void seenMess() {
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Message mess = dataSnapshot.getValue(Message.class);
                    if (mess.getReceiverUid().equals(App.getInstance().getUser())){
                        dataSnapshot.child("seen").getRef().setValue(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void removeSeenListener() {
        reference.removeEventListener(seenListener);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public MutableLiveData<ArrayList<Message>> getMessageLiveData() {
        return messageLiveData;
    }
}
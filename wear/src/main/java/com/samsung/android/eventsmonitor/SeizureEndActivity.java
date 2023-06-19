package com.samsung.android.eventsmonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Wearable;
import com.samsung.android.eventsmonitor.databinding.ActivitySeizureEndBinding;

import java.util.concurrent.ExecutionException;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;


import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.samsung.android.eventsmonitor.databinding.ActivityMainBinding;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SeizureEndActivity extends Activity {

    private ActivitySeizureEndBinding binding;

    // data layer
    int receivedMessageNumber = 1;
    int sentMessageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySeizureEndBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // SoundActivity로부터 전달받은 데이터 가져오기
        String eventTime = getIntent().getStringExtra("eventTime");
        String elapsedTime = getIntent().getStringExtra("elapsedTime");

        // 텍스트뷰에 데이터 설정
//        binding.textEventTime.setText(eventTime);
//        binding.textElapsedTime.setText(elapsedTime);

        //Create an OnClickListener//
        binding.talkClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String onClickMessage = "I just sent the handheld a message " + sentMessageNumber++;
                binding.textView4.setText(onClickMessage);

                //Use the same path//
                String datapath = "/my_path";
                new SendMessage(datapath, onClickMessage).start();
            }
        });
//Register to receive local broadcasts, which we'll be creating in the next step//
        IntentFilter newFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, newFilter);
    }
    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//Display the following when a new message is received//
            String onMessageReceived = "I just received a message from the handheld " + receivedMessageNumber++;
            binding.textView4.setText(onMessageReceived);

        }
    }
    class SendMessage extends Thread {
        String path;
        String message;
        //Constructor for sending information to the Data Layer//
        SendMessage(String p, String m) {
            path = p;
            message = m;
        }
        public void run() {
//Retrieve the connected devices//
            Task<List<Node>> nodeListTask =
                    Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
            try {
//Block on a task and get the result synchronously//
                List<Node> nodes = Tasks.await(nodeListTask);
                for (Node node : nodes) {
//Send the message///
                    Task<Integer> sendMessageTask =
                            Wearable.getMessageClient(SeizureEndActivity.this).sendMessage(node.getId(), path, message.getBytes());
                    try {
                        Integer result = Tasks.await(sendMessageTask);
//Handle the errors//
                    } catch (ExecutionException exception) {
//TO DO//
                    } catch (InterruptedException exception) {
//TO DO//
                    }
                }
            } catch (ExecutionException exception) {
//TO DO//
            } catch (InterruptedException exception) {
//TO DO//
            }
        }
    }
}

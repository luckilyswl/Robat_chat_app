package com.qingshangzuo.robatchatapp.pages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.qingshangzuo.robatchatapp.R;
import com.qingshangzuo.robatchatapp.adapter.ChatMessageAdapter;
import com.qingshangzuo.robatchatapp.message.ChatMessage;
import com.qingshangzuo.robatchatapp.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemActivity extends Activity {

    //新添加聊天组件
    private List<ChatMessage> list;
    private ListView chat_listview;
    private EditText chat_input;
    private Button chat_send;
    private ChatMessageAdapter chatAdapter;
    private ChatMessage chatMessage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        initChat();
    }


    private void initChat() {
        // 1.初始化
        chat_listview =  findViewById(R.id.chat_listview);
        chat_input =  findViewById(R.id.chat_input_message);
        chat_send =  findViewById(R.id.chat_send);

        // 2.设置监听事件
        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.chat_send:
                        chat();
                        break;
                }
            }
        });

        // 3.初始化数据
        list = new ArrayList<ChatMessage>();
        list.add(new ChatMessage("您终于来了!", ChatMessage.Type.INCOUNT, new Date()));
        list.add(new ChatMessage("很高兴，您能与小清聊天!", ChatMessage.Type.INCOUNT, new Date()));
        list.add(new ChatMessage("那，我们开始吧!", ChatMessage.Type.INCOUNT, new Date()));
        chatAdapter = new ChatMessageAdapter(list);
        chat_listview.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }

    // 发送消息聊天
    private void chat() {
        final String send_message = chat_input.getText().toString().trim();
        if (TextUtils.isEmpty(send_message)) {
            Toast.makeText(ItemActivity.this, "对不起，您还未发送任何消息",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 2.自己输入的内容也是一条记录，记录刷新
        ChatMessage sendChatMessage = new ChatMessage();
        sendChatMessage.setMessage(send_message);
        sendChatMessage.setData(new Date());
        sendChatMessage.setType(ChatMessage.Type.OUTCOUNT);
        list.add(sendChatMessage);
        chatAdapter.notifyDataSetChanged();
        chat_input.setText("");

        // 3.发送你的消息，去服务器端，返回数据
        new Thread() {
            public void run() {
                ChatMessage chat = HttpUtils.sendMessage(send_message);
                Message message = new Message();
                message.what = 0x1;
                message.obj = chat;
                handler.sendMessage(message);
            }

            ;
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x1) {
                if (msg.obj != null) {
                    chatMessage = (ChatMessage) msg.obj;
                }
                // 添加数据到list中，更新数据
                list.add(chatMessage);
                chatAdapter.notifyDataSetChanged();
            }
        }

        ;
    };
}


package com.qingshangzuo.robatchatapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingshangzuo.robatchatapp.R;
import com.qingshangzuo.robatchatapp.message.ChatMessage;
import com.qingshangzuo.robatchatapp.utils.DateUtils;

import java.util.List;

public class ChatMessageAdapter extends BaseAdapter {

    private List<ChatMessage> list;

    public ChatMessageAdapter(List<ChatMessage> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.isEmpty() ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = list.get(position);
        // 如果是接收消息：0，发送消息：1
        if (chatMessage.getType() == ChatMessage.Type.INCOUNT) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage = list.get(position);
        if (convertView == null) {
            ViewHolder viewHolder = null;
            // 通过ItemType加载不同的布局
            if (getItemViewType(position) == 0) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.layout_left, null);
                viewHolder = new ViewHolder();
                viewHolder.chat_time = (TextView) convertView
                        .findViewById(R.id.chat_left_time);
                viewHolder.chat_message = (TextView) convertView
                        .findViewById(R.id.chat_left_message);
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.layout_right, null);
                viewHolder = new ViewHolder();
                viewHolder.chat_time = (TextView) convertView
                        .findViewById(R.id.chat_right_time);
                viewHolder.chat_message = (TextView) convertView
                        .findViewById(R.id.chat_right_message);
            }
            convertView.setTag(viewHolder);
        }
        // 设置数据
        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.chat_time.setText(DateUtils.dateToString(chatMessage.getData()));
        vh.chat_message.setText(chatMessage.getMessage());
        return convertView;
    }

    /**
     * 内部类：只寻找一次控件
     */
    private class ViewHolder {
        private TextView chat_time, chat_message;
    }

    /**
     * Created by Shiweilong on 2018/11/17.
     */

    public static class Config {

        public static final String URL_KEY = "http://www.tuling123.com/openapi/api";
        public static final String APP_KEY = "f4c578cf2ec841ab8bccf8c764c753b7";//此处是你申请的Apikey

    }
}


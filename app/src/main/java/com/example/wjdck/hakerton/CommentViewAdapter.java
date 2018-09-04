package com.example.wjdck.hakerton;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CommentViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    private String title, body, agree, progress, key;
    private int option = 1;
    // option = 1 --> detailActivity, option = 2 --> detaildiscussActivity
    private ArrayList<CommentItem> commentItems;
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public CommentViewAdapter(final Context context, ArrayList<CommentItem> items, String title, String body, String agree, String progress, String key, int option){
        this.context = context;
        this.commentItems = items;
        this.title = title;
        this.body = body;
        this.agree = agree;
        this.progress = progress;
        this.key = key;
        this.option = option;
        commentItems.add(new CommentItem());
        commentItems.add(new CommentItem());
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void setAgree(String agree){ this.agree = agree; }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;
        if(viewType == 0) {
            if(option == 1){
                v = inflater.inflate(R.layout.item_detail_title, parent, false);
                return new DetailTitleViewHolder(v);
            }
            else {
                v = inflater.inflate(R.layout.item_discuss_detail_title, parent, false);
                return new DiscussDetailTitleViewHolder(v);
            }
        } else if(viewType == 1) {
            if(option == 1) {
                v = inflater.inflate(R.layout.item_detail_body, parent, false);
                return new DetailBodyViewHolder(v);
            }
            else {
                v = inflater.inflate(R.layout.item_discuss_detail_body, parent, false);
                return new DiscussDetailBodyViewHolder(v);
            }

        } else {
            if(option == 1){
                v = inflater.inflate(R.layout.item_comment, parent, false);
                return new CommentViewHolder(v);
            }
            else {
                v = inflater.inflate(R.layout.item_discuss_detail_comment, parent, false);
                return new DiscussDetailCommentViewHolder(v);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0) {
            if(option == 1) {
                ((DetailTitleViewHolder) holder).title.setText(title);
                ((DetailTitleViewHolder) holder).progress.setText(progress);
            }
            else{
                ((DiscussDetailTitleViewHolder) holder).title.setText(title);
            }
        } else if(position == 1) {
            if(option == 1){
                ((DetailBodyViewHolder)holder).body.setText(body);
                ((DetailBodyViewHolder)holder).agree.setText(agree);
                ((DetailBodyViewHolder)holder).answer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, openPdfActivity.class);
                        context.startActivity(intent);
                    }
                });
                ((DetailBodyViewHolder)holder).report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, reportPopupActivity.class);
                        intent.putExtra("data", key);
                        context.startActivity(intent);
                    }
                });
            }
            else{
                ((DiscussDetailBodyViewHolder) holder).body.setText(body);
            }

        } else {
            if(option == 1) {
                ((CommentViewHolder) holder).userId.setText(commentItems.get(position).getUserid());
                ((CommentViewHolder) holder).body.setText(commentItems.get(position).getBody());
                ((CommentViewHolder) holder).date.setText(mSimpleDateFormat.format(commentItems.get(position).getDate()));
            }
            else{
                ((DiscussDetailCommentViewHolder) holder).id.setText(commentItems.get(position).getUserid());
                ((DiscussDetailCommentViewHolder) holder).body.setText(commentItems.get(position).getBody());
                ((DiscussDetailCommentViewHolder) holder).date.setText(mSimpleDateFormat.format(commentItems.get(position).getDate()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return commentItems.size();
    }

    public void addItem(CommentItem item){
        commentItems.add(item);
    }

    public int findItem(String key) {
        for(int i=0; i<commentItems.size(); i++) {
            if(commentItems.get(i).getKey().equals(key)){
                return i;
            }
        }
        return -1;
    }
    public void removeItem(int position) {
        commentItems.remove(position);
    }
}

class CommentViewHolder extends RecyclerView.ViewHolder {
    public TextView userId;
    public TextView date;
    public TextView body;

    public CommentViewHolder(View itemView) {
        super(itemView);
        this.userId = itemView.findViewById(R.id.item_comment_userid);
        this.body = itemView.findViewById(R.id.item_comment_body);
        this.date = itemView.findViewById(R.id.item_comment_date);
    }
}
class DetailTitleViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView progress;
    public DetailTitleViewHolder(View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.title);
        this.progress = itemView.findViewById(R.id.agenda_progress);
    }
}

class DetailBodyViewHolder extends RecyclerView.ViewHolder {
    public TextView body;
    public TextView agree;
    public Button answer;
    public Button report;
    public DetailBodyViewHolder(View itemView) {
        super(itemView);
        this.body = itemView.findViewById(R.id.body);
        this.agree = itemView.findViewById(R.id.agree_people);
        this.answer = itemView.findViewById(R.id.answer_btn);
        this.report = itemView.findViewById(R.id.report_btn);
    }
}

class DiscussDetailCommentViewHolder extends RecyclerView.ViewHolder{
    public TextView id;
    public TextView date;
    public TextView body;

    public DiscussDetailCommentViewHolder(View itemView){
        super(itemView);
        this.id = itemView.findViewById(R.id.discuss_detail_comment_id);
        this.body = itemView.findViewById(R.id.discuss_detail_comment_body);
        this.date = itemView.findViewById(R.id.discuss_detail_comment_date);
    }
}

class DiscussDetailTitleViewHolder extends RecyclerView.ViewHolder{
    public TextView title;
    public TextView date;
    public TextView id;

    public DiscussDetailTitleViewHolder(View itemView){
        super(itemView);
        this.title = itemView.findViewById(R.id.discuss_detail_title);
        this.id = itemView.findViewById(R.id.discuss_detail_id);
        this.date = itemView.findViewById(R.id.discuss_detail_date);
    }
}


class DiscussDetailBodyViewHolder extends RecyclerView.ViewHolder{
    public TextView body;
    public DiscussDetailBodyViewHolder(View itemView){
        super(itemView);
        this.body = itemView.findViewById(R.id.discuss_detail_body);
    }

}
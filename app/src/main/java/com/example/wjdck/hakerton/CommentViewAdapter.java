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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.wjdck.hakerton.loginActivity.Uid;

public class CommentViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    private int option = 1;
    // option = 1 --> detailActivity, option = 2 --> detaildiscussActivity
    private ArrayList<CommentItem> commentItems;
    private ListViewItem item1;
    private discussItem item2;
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public CommentViewAdapter(final Context context, ArrayList<CommentItem> items, ListViewItem item1, discussItem item2, int option){
        this.context = context;
        this.commentItems = items;
        this.item1 = item1;
        this.item2 = item2;
        this.option = option;
        commentItems.add(new CommentItem());
        commentItems.add(new CommentItem());
    }

    public void setItem1(ListViewItem item1){
        this.item1 = item1;
    }
    public void setItem2(discussItem item2){
        this.item2 = item2;
    }

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(position == 0) {
            if(option == 1) {
                ((DetailTitleViewHolder) holder).title.setText(item1.getTitle());
                if(item1.getAnswerNum() == 0){
                    ((DetailTitleViewHolder) holder).progress.setText("청원 진행중");
                }
                else{
                    ((DetailTitleViewHolder) holder).progress.setText("답변 완료");
                }
                ((DetailTitleViewHolder) holder).category.setText(item1.getCategory());
                ((DetailTitleViewHolder) holder).startDate.setText(mSimpleDateFormat.format(Long.parseLong(item1.getDate())));
                ((DetailTitleViewHolder) holder).endDate.setText(mSimpleDateFormat.format(Long.parseLong(item1.getDate()) + 2592000000L));
            }
            else{
                ((DiscussDetailTitleViewHolder) holder).title.setText(item2.getTitle());
                ((DiscussDetailTitleViewHolder) holder).date.setText(mSimpleDateFormat.format(Long.parseLong(item2.getDate())));
                String star="";
                for(int i=0; i<item2.getId().length()-1;i++) {
                    star = star + "*";
                }
                ((DiscussDetailTitleViewHolder) holder).id.setText(item2.getId().substring(0,1)+star);
            }
        } else if(position == 1) {
            if(option == 1){
                ((DetailBodyViewHolder)holder).body.setText(item1.getText());
                ((DetailBodyViewHolder)holder).agree.setText(Long.toString(item1.getRecommend()));
                ((DetailBodyViewHolder)holder).answer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        if(item1.getAnswerNum() == 0){
                            intent = new Intent(context, agreePopupActivity.class);
                            intent.putExtra("data", "답변이 미등록된 청원입니다.");
                        }
                        else{
                            intent = new Intent(context, openPdfActivity.class);
                        }
                        context.startActivity(intent);
                    }
                });
                ((DetailBodyViewHolder)holder).report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, reportPopupActivity.class);
                        intent.putExtra("data", item1.getKey());
                        context.startActivity(intent);
                    }
                });
            }
            else{
                ((DiscussDetailBodyViewHolder) holder).body.setText(item2.getText());
                ((DiscussDetailBodyViewHolder) holder).recommend.setText(Long.toString(item2.getRecommend()));
                ((DiscussDetailBodyViewHolder) holder).unrecommend.setText(Long.toString(item2.getUnrecommend()));
                ((DiscussDetailBodyViewHolder) holder).good.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Discuss");
                        onRecommendClicked(ref.child(item2.getKey()),1);
                        ((DiscussDetailBodyViewHolder) holder).recommend.setText(Long.toString(item2.getRecommend()));
                        notifyDataSetChanged();
                    }
                });
                ((DiscussDetailBodyViewHolder) holder).bad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Discuss");
                        onRecommendClicked(ref.child(item2.getKey()),2);
                        ((DiscussDetailBodyViewHolder) holder).unrecommend.setText(Long.toString(item2.getUnrecommend()));
                        notifyDataSetChanged();
                    }
                });
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

    private void onRecommendClicked(DatabaseReference ref, final int op) {
        ref.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                discussItem item = mutableData.getValue(discussItem.class);
                if(item == null) {
                    return Transaction.success(mutableData);
                }
                if (item.getRecommended().containsKey(Uid)) {
                    Intent intent = new Intent(context, agreePopupActivity.class);
                    intent.putExtra("data", "추천은 한 번만 할 수 있습니다.");
                    context.startActivity(intent);
                } else {
                    item.getRecommended().put(Uid, true);
                    if(op==1){
                        item.setRecommend(item.getRecommend()+1);
                    }
                    else{
                        item.setUnrecommend(item.getUnrecommend()+1);
                    }
                }
                item2 = item;
                mutableData.setValue(item);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("discussDetailActivity", "register Transaction : onComplete:" + databaseError);
            }
        });
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
    public TextView category;
    public TextView startDate;
    public TextView endDate;

    public DetailTitleViewHolder(View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.title);
        this.progress = itemView.findViewById(R.id.agenda_progress);
        this.category = itemView.findViewById(R.id.agenda_title_category);
        this.startDate = itemView.findViewById(R.id.agenda_title_startdate);
        this.endDate = itemView.findViewById(R.id.agenda_title_enddate);
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
    public TextView recommend;
    public TextView unrecommend;
    public ImageButton good;
    public ImageButton bad;

    public DiscussDetailBodyViewHolder(View itemView){
        super(itemView);
        this.body = itemView.findViewById(R.id.discuss_detail_body);
        this.good = itemView.findViewById(R.id.good_btn);
        this.bad = itemView.findViewById(R.id.bad_btn);
        this.recommend = itemView.findViewById(R.id.discuss_good_num);
        this.unrecommend = itemView.findViewById(R.id.discuss_bad_num);
    }

}
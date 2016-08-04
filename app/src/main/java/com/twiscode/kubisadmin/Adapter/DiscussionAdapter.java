package com.twiscode.kubisadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twiscode.kubisadmin.POJO.Discussion;
import com.twiscode.kubisadmin.POJO.User;
import com.twiscode.kubisadmin.R;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Crusader on 8/2/2016.
 */
public class DiscussionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String header;

    private ArrayList<Discussion> listDiscussion;
    private ArrayList<User> listName;
    private Context context;

    private static final int TYPE_HEADER = 111;
    private static final int SENDER = 0;
    private static final int RECIPIENT = 1;

    public DiscussionAdapter(Context context, ArrayList<Discussion> listDiscussion, ArrayList<User> listName) {
        this.context = context;
        this.listDiscussion = listDiscussion;
        this.listName = listName;
    }

    @Override
    public int getItemCount() {
        return this.listDiscussion.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if( position == 0 ) {
            return TYPE_HEADER;
        } else {
            return SENDER;
//            if (listDiscussion.get(position).getMessageType() == SENDER) {
//                return SENDER;
//            } else {
//                return RECIPIENT;
//            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                View viewHeader = inflater.inflate(R.layout.header_detail_discussion, parent, false);
                viewHolder = new ViewHolderHeader(viewHeader);
                break;
//                FrameLayout frameLayout = new FrameLayout(parent.getContext());
//                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                viewHolder = new ViewHolderHeader(frameLayout);
            case SENDER:
                View viewSender = inflater.inflate(R.layout.list_item_discussion, parent, false);
                viewHolder = new ViewHolderSender(viewSender);
                break;
            case RECIPIENT:
                View viewRecipient = inflater.inflate(R.layout.list_item_discussion, parent, false);
                viewHolder = new ViewHolderRecipient(viewRecipient);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof  ViewHolderHeader) {
            configureHeaderView((ViewHolderHeader) viewHolder, position);
        } else {
            ViewHolderSender viewHolderSender = (ViewHolderSender) viewHolder;
            configureSenderView(viewHolderSender,position);
        }

//        switch (viewHolder.getItemViewType()){
//            case TYPE_HEADER:
//                configureHeaderView((ViewHolderHeader) viewHolder, position);
//            case SENDER:
//                Log.e("DiscussionAdapter", listName.toString());
//                ViewHolderSender viewHolderSender = (ViewHolderSender) viewHolder;
//                configureSenderView(viewHolderSender,position);
//                break;
//            case RECIPIENT:
//                ViewHolderRecipient viewHolderRecipient = (ViewHolderRecipient) viewHolder;
//                configureRecipientView(viewHolderRecipient,position);
//                break;
//        }
    }

    private void configureHeaderView(ViewHolderHeader viewHolderHeader, int position) {
        if(header == null) {
            viewHolderHeader.hide();
        } else {
//            viewHolderHeader.unhide();
            viewHolderHeader.getHeaderText().setText(header);
        }
    }

    private Discussion getDiscussion(int position) {
        return listDiscussion.get(position - 1);
    }

    private User getUser(int position) {
        return listName.get(position - 1);
    }

    private void configureSenderView(ViewHolderSender viewHolderSender, int position) {
        Discussion discussion = getDiscussion(position);
        User user = getUser(position);
        viewHolderSender.getTvName().setText(user.getName());
        viewHolderSender.getTvComment().setText(discussion.getComment());
        Picasso.with(context).load(user.getImageUrl()).into(viewHolderSender.getCivProfilePicture());
    }

    private void configureRecipientView(ViewHolderRecipient viewHolderRecipient, int position) {
        Discussion discussion = getDiscussion(position);
        User user = getUser(position);
        viewHolderRecipient.getTvName().setText(user.getName());
        viewHolderRecipient.getTvComment().setText(discussion.getComment());
    }

    public void refillAdapter(Discussion discussion, User user){
        if(this.listDiscussion.isEmpty()) {
            this.listDiscussion.add(discussion);
            this.listName.add(user);
        } else {
            boolean added = false;
            for(int i=0; i<this.listDiscussion.size(); i++) {
                Log.d("DiscussionAdapter", listDiscussion.toString());
                if(this.listDiscussion.get(i).getTimestamp() < discussion.getTimestamp()) {
                    this.listDiscussion.add(i, discussion);
                    this.listName.add(i, user);
                    added = true;
                    break;
                }
            }
            if(!added) {
                this.listDiscussion.add(discussion);
                this.listName.add(user);
            }
        }
        notifyDataSetChanged();
    }

    public void spillAdapter(Discussion discussion) {
        int index = this.listDiscussion.indexOf(discussion);
        this.listDiscussion.remove(index);
        this.listName.remove(index);
        notifyDataSetChanged();
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void cleanUp() {
        listName.clear();
        listDiscussion.clear();
    }

    public static class ViewHolderHeader extends RecyclerView.ViewHolder{

        @BindView(R.id.headerText) TextView headerText;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getHeaderText() {
            return headerText;
        }

        public void hide() {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.height = 0;
            itemView.setLayoutParams(params);
        }
        public void unhide() {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemView.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            itemView.setLayoutParams(params);
        }
    }

    public class ViewHolderSender extends RecyclerView.ViewHolder {

        @BindView(R.id.civProfilePicture)
        CircleImageView civProfilePicture;
        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvComment) TextView tvComment;

        public ViewHolderSender(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvComment() {
            return tvComment;
        }

        public CircleImageView getCivProfilePicture() {
            return civProfilePicture;
        }
    }

    public class ViewHolderRecipient extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvComment) TextView tvComment;

        public ViewHolderRecipient(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvComment() {
            return tvComment;
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd MMMM yyyy HH:mm", cal).toString();
        return date;
    }
}

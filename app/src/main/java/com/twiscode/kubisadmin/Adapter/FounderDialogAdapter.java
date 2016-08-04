package com.twiscode.kubisadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.twiscode.kubisadmin.POJO.User;
import com.twiscode.kubisadmin.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Crusader on 8/2/2016.
 */
public class FounderDialogAdapter extends BaseAdapter{
    ArrayList<User> data = new ArrayList<User>();
    LayoutInflater inflater;
    DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    Activity activity;
    ArrayList<User> listFounder;
    public FounderDialogAdapter(Activity activity, final ArrayList<User> listFounder) {
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listFounder = listFounder;
        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Boolean status = false;
                User user = dataSnapshot.getValue(User.class);
                for(int i=0;i<listFounder.size();i++){
                    if(listFounder.get(i).getUid().equals(user.getUid())){
                        status = true;
                        break;
                    }
                }
                if(status){
                    data.add(user);
                    notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_list_founder, parent, false);
        }
        User founder = data.get(position);
        CircleImageView founderLogo = (CircleImageView)convertView.findViewById(R.id.founderLogo);
        TextView founderName = (TextView)convertView.findViewById(R.id.founderListName);
        TextView founderDesc = (TextView) convertView.findViewById(R.id.founderListDesc);
        if(founder.getImageUrl()!=null){
            Picasso.with(activity).load(founder.getImageUrl()).into(founderLogo);
        }
        founderName.setText(founder.getName());
        founderDesc.setText(founder.getDescription());


        return convertView;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if(convertView==null){
//            convertView = inflater.inflate(R.layout.item_list_founder, parent, false);
//        }
//        User founder = data.get(position);
//        CircleImageView founderLogo = (CircleImageView)convertView.findViewById(R.id.founderLogo);
//        TextView founderName = (TextView)convertView.findViewById(R.id.founderListName);
//        TextView founderDesc = (TextView) convertView.findViewById(R.id.founderListDesc);
//        if(founder.getImageUrl()!=null){
//            Picasso.with(activity).load(founder.getImageUrl()).into(founderLogo);
//        }
//        founderName.setText(founder.getName());
//        founderDesc.setText(founder.getDescription());
//
//
//        return convertView;
//    }
}

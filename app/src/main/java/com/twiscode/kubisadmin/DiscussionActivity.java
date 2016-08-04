package com.twiscode.kubisadmin;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twiscode.kubisadmin.Adapter.DiscussionAdapter;
import com.twiscode.kubisadmin.POJO.Discussion;
import com.twiscode.kubisadmin.POJO.User;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscussionActivity extends AppCompatActivity {

    @BindView(R.id.lvDiscussion)
    RecyclerView lvDiscussion;
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.btnWrite)
    Button btnWrite;

    DiscussionAdapter adapterDiscussion;
    DatabaseReference mRef, usersRef, discussRef;

    ArrayList<Discussion> listDiscussion;
    ArrayList<User> listName;

    Toolbar mToolbar;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        ButterKnife.bind(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(null);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        final String startupID = getIntent().getExtras().getString("startupID");//"-KNpcrYOHczcILXToTOS";
        listDiscussion = new ArrayList<>();
        listName = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        lvDiscussion.setLayoutManager(llm);

        adapterDiscussion = new DiscussionAdapter(this, listDiscussion, listName);
        lvDiscussion.setAdapter(adapterDiscussion);

        mRef = FirebaseDatabase.getInstance().getReference();
        usersRef = mRef.child("users");
        discussRef = mRef.child("discuss");

        final View activityRootView = findViewById(R.id.activityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > dpToPx(200)) { // if more than 200 dp, it's probably a keyboard...
                    lvDiscussion.scrollToPosition(0);
                }
            }
        });
        etComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    lvDiscussion.scrollToPosition(0);
                }
            }
        });
        discussRef.orderByChild("sid").equalTo(startupID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                progressBar.setVisibility(View.GONE);
                final Discussion discussion = dataSnapshot.getValue(Discussion.class);
                usersRef.child(discussion.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                            Log.d("Firebase data", dataSnapshot.toString());
                        if(dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);
//                            String name = (String) dataSnapshot.child("name").getValue();
                            adapterDiscussion.refillAdapter(discussion, user);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if(databaseError != null) {
                            Toast.makeText(DiscussionActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Discussion discussion = dataSnapshot.getValue(Discussion.class);
                adapterDiscussion.spillAdapter(discussion);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(databaseError != null) {
                    Toast.makeText(DiscussionActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnWrite.setBackgroundColor(Color.GRAY);
        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    btnWrite.setBackgroundColor(Color.GRAY);
                }else{
                    btnWrite.setBackgroundColor(getResources().getColor(R.color.lime_green));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etComment.getText().toString().isEmpty()) {

                    Discussion d = new Discussion();
                    d.setUid(mAuth.getCurrentUser().getUid());
                    d.setComment(etComment.getText().toString());
                    d.setMessageType(0);
                    d.setTimestamp(Calendar.getInstance().getTimeInMillis());
                    d.setSid(startupID);
                    etComment.setText("");
                    discussRef.push().setValue(d, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError != null) {
                                Toast.makeText(DiscussionActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();

                            }
//                            scrollMyListViewToBottom();
                        }
                    });

                }

            }
        });
    }
    public float dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        float px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
//    private void scrollMyListViewToBottom() {
//
//        // Select the last row so it will scroll into view...
//        lvDiscussion.smoothScrollToPosition(adapterDiscussion.getItemCount() - 1);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //Intent j = new Intent(ChatActivity2.this, LobbyChatActivity.class);
                //j.putExtra("username",mUsername);
                //startActivity(j);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

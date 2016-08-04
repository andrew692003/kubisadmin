package com.twiscode.kubisadmin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.twiscode.kubisadmin.POJO.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuggestionDetailActivity extends AppCompatActivity {

    @BindView(R.id.accept)
    FloatingActionButton btnAcc;
    @BindView(R.id.decline)
    FloatingActionButton btnDec;
    @BindView(R.id.tab_viewpager)
    ViewPager viewPager;
    @BindView(R.id.nameSuggest)
    TextView nameSuggest;
    @BindView(R.id.imageSuggest)
    ImageView imageSuggest;
    @BindView(R.id.gotoProf)
    TextView gotoprof;

    ProgressDialog mAuthProgressDialog;

    String key,userid="";
    Bundle mBundle;

    SuggestionDetailFragment mSuggestionDetailFragment;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_detail);
        database = FirebaseDatabase.getInstance().getReference();
        ButterKnife.bind(this, this);
        Intent intent = getIntent();
        key=intent.getStringExtra("key");
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Please Wait...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();
        // VIEWPAGER
        if(viewPager != null) {
            DetailPagerAdapter adapter = new DetailPagerAdapter(getSupportFragmentManager());

            mSuggestionDetailFragment = new SuggestionDetailFragment();

            mBundle = new Bundle();
            mBundle.putString("idRequest", key);
            mSuggestionDetailFragment.setArguments(mBundle);

            adapter.addFragment(mSuggestionDetailFragment, "DetailFragment");
            viewPager.setAdapter(adapter);
        }

        database.child("request").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Long status=dataSnapshot.child("status").getValue(Long.class);
                    userid=dataSnapshot.child("userid").getValue(String.class);
                    if(status==0)
                    {
                        btnDec.setVisibility(View.VISIBLE);
                        btnAcc.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        btnDec.setVisibility(View.GONE);
                        btnAcc.setVisibility(View.GONE);
                    }

                    database.child("users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            if(user.getName()!=null){
                                nameSuggest.setText(user.getName());}
                            if(user.getImageUrl()==null || user.getImageUrl().equals("")){}
                            else Picasso.with(getApplicationContext()).load(user.getImageUrl()).into(imageSuggest);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("databaseErr",databaseError.toString());
                        }
                    });

                    btnAcc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AlertDialog.Builder(SuggestionDetailActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Accept user")
                                    .setMessage("Are you sure?")
                                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            database.child("users").child(userid).child("status").setValue(2);
                                            database.child("request").orderByChild("userid").equalTo(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                                        String postKey=postSnapshot.getKey();
                                                        database.child("request").child(postKey).child("status").setValue(1);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Log.e("databaseErr",databaseError.toString());
                                                }
                                            });
                                            finish();
                                        }
                                    }).setNegativeButton("no", null).show();
                        }
                    });

                    btnDec.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AlertDialog.Builder(SuggestionDetailActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Decline user")
                                    .setMessage("Are you sure?")
                                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            database.child("request").orderByChild("userid").equalTo(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                                        String postKey=postSnapshot.getKey();
                                                        database.child("request").child(postKey).child("status").setValue(2);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Log.e("databaseErr",databaseError.toString());
                                                }
                                            });
                                            finish();
                                        }
                                    }).setNegativeButton("no", null).show();
                        }
                    });
                    mAuthProgressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("databaseErr",databaseError.toString());
            }
        });
        gotoprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(SuggestionDetailActivity.this, ProfileUserActivity.class);
                j.putExtra("memberId", userid);
//                    Log.v("key", String.valueOf(parent.getItemIdAtPosition(position)));
//                    j.putExtra("key", parent.getItemIdAtPosition(position));
                startActivityForResult(j, 1);
            }
        });
    }
    public class DetailPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public DetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return mFragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }
}

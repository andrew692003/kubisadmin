package com.twiscode.kubisadmin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.net.Uri;
import android.opengl.Visibility;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.twiscode.kubisadmin.Interface.StartupPassing;
import com.twiscode.kubisadmin.POJO.Startup;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartupDetailActivity extends AppCompatActivity implements StartupPassing {

    @BindView(R.id.text_description)
    TextView textDescription;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewpager)
    ViewPager viewPager;

    /*
    @BindView(R.id.accept)
    Button btnAcc;
    @BindView(R.id.decline)
    Button btnDec;*/

    @BindView(R.id.accept)
    FloatingActionButton btnAcc;
    @BindView(R.id.decline)
    FloatingActionButton btnDec;
    @BindView(R.id.btnUpvote)
    Button btnUpvote;
    @BindView(R.id.btnGet) Button btnGet;
    @BindView(R.id.btnDiscuss) Button btnDiscuss;
    @BindView(R.id.main_image)
    ImageView mainImage;

    String key;
    ProgressDialog mAuthProgressDialog;
    Bundle mBundle;
    StartupDiscussionFragment mDetailDiscussionFragment;
    StartupInfoFragment mDetailInfoFragment;
    StartupMediaFragment mDetailMediaFragment;

    Startup startupCurrent;
    int upvotersCount;
    boolean upvoted = false;

    DatabaseReference database;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_detail);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this, this);
        Intent intent = getIntent();
        key = getIntent().getStringExtra("startupId");
        startupCurrent = Parcels.unwrap(getIntent().getExtras().getParcelable("startup"));
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Please Wait...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();

        ButterKnife.bind(this);
        if(startupCurrent.getUpvoters()==null)
        {

        }
        else {
            Log.v("countUp", String.valueOf(startupCurrent.getUpvoters().size()));
        }


        // VIEWPAGER
        if(viewPager != null) {
            DetailPagerAdapter adapter = new DetailPagerAdapter(getSupportFragmentManager());

            mDetailDiscussionFragment = new StartupDiscussionFragment();
            mDetailInfoFragment = new StartupInfoFragment();
            mDetailMediaFragment = new StartupMediaFragment();

            mBundle = new Bundle();
            mBundle.putString("startupID", key);
            mBundle.putParcelable("startup", Parcels.wrap(startupCurrent));
            mDetailDiscussionFragment.setArguments(mBundle);
            mDetailInfoFragment.setArguments(mBundle);
            mDetailMediaFragment.setArguments(mBundle);

            adapter.addFragment(mDetailDiscussionFragment, "Discussion");
            adapter.addFragment(mDetailInfoFragment, "Information");
            adapter.addFragment(mDetailMediaFragment, "Media");
            viewPager.setAdapter(adapter);
        }

        if(startupCurrent.getUpvoters() != null) {
            upvotersCount = startupCurrent.getUpvoters().size();
            if (startupCurrent.getUpvoters().contains(mAuth.getCurrentUser().getUid())) {
                upvoted = true;
                btnUpvote.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(StartupDetailActivity.this, R.color.fresh_green)));
            }
            btnUpvote.setText(String.valueOf(upvotersCount));
        } else {
            btnUpvote.setText(String.valueOf(0));
        }

        if(startupCurrent.getImageUrl() != null) {
            Picasso.with(this).load(startupCurrent.getImageUrl().get(0)).into(mainImage);
            mainImage.setColorFilter(Color.argb(122,0,0,0), PorterDuff.Mode.DARKEN);
        }

//        if(startupCurrent.getUpvoters() != null) {
//            upvotersCount = startupCurrent.getUpvoters().size();
//            if(startupCurrent.getUpvoters().contains(mAuth.getCurrentUser().getUid())) {
//                upvoted = true;
//                btnUpvote.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(StartupDetailActivity.this, R.color.fresh_green)));
//            }
//        } else {
//            upvotersCount = 0;
//        }

        // TABLAYOUT
        tabLayout.setupWithViewPager(viewPager);

        // TOOLBAR
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(startupCurrent.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textDescription.setText(startupCurrent.getType());
        btnUpvote.setText(String.valueOf(upvotersCount));

        btnDec.setVisibility(View.GONE);
        btnAcc.setVisibility(View.GONE);

//        database.child("startups").child(key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Startup startup = dataSnapshot.getValue(Startup.class);
//                collapsingToolbarLayout.setTitle(startup.getName());
//                textDescription.setText(startup.getDescription());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e("databaseErr",databaseError.toString());
//            }
//        });

        btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uid = mAuth.getCurrentUser().getUid();
                database.child("startups").child(key).child("upvoters").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> upvoters = (ArrayList<String>) dataSnapshot.getValue();
                        if(upvoters == null) {
                            upvoters = new ArrayList<String>();
                        }
                        if(upvoters.contains(uid)) {
                            upvoters.remove(uid);
                            btnUpvote.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(StartupDetailActivity.this, R.color.md_grey_700)));
                        } else {
                            upvoters.add(uid);
                            btnUpvote.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(StartupDetailActivity.this, R.color.fresh_green)));
                        }
                        database.child("startups").child(key).child("upvoters").setValue(upvoters);
                        btnUpvote.setText(String.valueOf(upvoters.size()));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if(databaseError != null) {
                            Log.d("DetailActivity", databaseError.getMessage());
                        }
                    }
                });
//                if(upvoted) {
//                    database.child("startups").child(key).child("upvoters").orderByValue().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if(dataSnapshot.exists()) {
//                                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                                    String key = postSnapshot.getKey();
//                                    database.child("startups").child(key).child("upvoters").child(key).removeValue();
//                                    btnUpvote.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(StartupDetailActivity.this, R.color.md_grey_700)));
//                                    upvoted = false;
//                                }
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                        }
//                    });
//                } else {
//                    database.child("startups").child(key).child("upvoters").child(String.valueOf(upvotersCount)).setValue(uid, new DatabaseReference.CompletionListener() {
//                        @Override
//                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                            if (databaseError != null) {
//                                Toast.makeText(StartupDetailActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
//                            } else {
//                                upvoted = true;
//                                btnUpvote.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(StartupDetailActivity.this, R.color.fresh_green)));
//                                Toast.makeText(StartupDetailActivity.this, "Upvoted!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
            }
        });

        database.child("startupstatus").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                //Log.e("DATASNAP",dataSnapshot.toString());
                database.child("limit").child("limit").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshotLimit) {
                        Boolean status=(Boolean)dataSnapshot.getValue();
                        if(status==null)
                        {
                            btnDec.setVisibility(View.VISIBLE);
                            btnAcc.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            btnDec.setVisibility(View.GONE);
                            btnAcc.setVisibility(View.GONE);
                        }

                        btnAcc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(((MyApplication) getApplication()).getLimit()<dataSnapshotLimit.getValue(Long.class))
                                    new AlertDialog.Builder(StartupDetailActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Accept startup")
                                            .setMessage("Are you sure?")
                                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    database.child("startupstatus").child(key).setValue(true);
                                                    database.child("startups").child(key).child("isDisplayed").setValue(true);
                                                    database.child("startups").child(key).child("timestamp").setValue(System.currentTimeMillis());
                                                    finish();
                                                }
                                            }).setNegativeButton("no", null).show();
                                else{
                                    AlertDialog alertDialog;
                                    alertDialog = new AlertDialog.Builder(StartupDetailActivity.this).create();
                                    alertDialog.setTitle("Can't Submit");
                                    alertDialog.setMessage("Anda sudah melebihi kuota submit startup untuk hari ini");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }
                            }
                        });

                        btnDec.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AlertDialog.Builder(StartupDetailActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Decline startup")
                                        .setMessage("Are you sure?")
                                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                database.child("startupstatus").child(key).setValue(false);
                                                finish();
                                            }
                                        }).setNegativeButton("no", null).show();
                            }
                        });
                        mAuthProgressDialog.dismiss();
                        Log.v("STATUS", String.valueOf(status));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("databaseErr",databaseError.toString());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("databaseErr",databaseError.toString());
            }
        });

//        btnGet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(startupCurrent.getLinkUrl()));
//                startActivity(intent);
//            }
//        });

        btnDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartupDetailActivity.this, DiscussionActivity.class);
                i.putExtra("startupID",key);
                startActivity(i);
            }
        });
    }

    @Override
    public Startup getStartup() {
        return startupCurrent;
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

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }
    */
}

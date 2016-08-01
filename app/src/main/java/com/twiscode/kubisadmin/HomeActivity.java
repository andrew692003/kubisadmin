package com.twiscode.kubisadmin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.twiscode.kubisadmin.Interface.CustomToolbarActivity;
import com.twiscode.kubisadmin.POJO.User;

public class HomeActivity extends AppCompatActivity implements CustomToolbarActivity, NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener{

    private TextView toolbarTitle;
    private FragmentManager fragmentManager;
    private static final int FRAGMENT_PLACEHOLDER = R.id.fragmentPlaceholder;
    private DrawerLayout drawer;
    private MenuArrowDrawable drawerArrow;
    View headerView;
    private ImageButton sideback;
    GoogleApiClient mGoogleApiClient;
    private TextView namaTextNav, descTextNav;
    private ImageView profPic;
    Toolbar toolbar;
    DatabaseReference database;
    FirebaseAuth mAuth;
    ProgressDialog mAuthProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Please Wait...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle = (TextView) findViewById(R.id.main_toolbar_title);
        setActionBarTitle(getString(R.string.app_name));
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(FRAGMENT_PLACEHOLDER, new HomeMainFragment())
                .commit();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerArrow = new MenuArrowDrawable(this);
        toolbar.setNavigationIcon(drawerArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
            headerView = navigationView.getHeaderView(0);
        }
        namaTextNav = (TextView) headerView.findViewById(R.id.navNama);
        profPic = (ImageView) headerView.findViewById(R.id.profile_image);
        descTextNav = (TextView) headerView.findViewById(R.id.txtDesc);

        if(mAuth.getCurrentUser()!=null)
        {
            database.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if(user != null) {
                        if (user.getName() == null || user.getName().equals("")) {
                            return;
                        } else {
//                            Log.v("username",user.getName());
                            namaTextNav.setText(user.getName());
                        }
                        if (user.getDescription() == null || user.getDescription().equals("")) {
//                            Log.v("useremailgaada","tidak ada phone");
                            return;
                        } else {
//                            Log.v("useremail",user.getPhone());
                            descTextNav.setText(user.getDescription());
                        }
                        if (user.getImageUrl() == null || user.getImageUrl().equals("")) {
                            profPic.setImageResource(R.drawable.kubis);
                            return;
                        } else {
                            Picasso.with(getApplicationContext()).load(user.getImageUrl()).into(profPic);
                        }
                    }
                    else
                    {
                        return;
                    }
                    mAuthProgressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.v("databaseError","Database Error");
                }
            });
        }

        sideback = (ImageButton) headerView.findViewById(R.id.side_menuback);


        sideback.setOnClickListener(new View.OnClickListener() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void setActionBarTitle(String title) {
        toolbarTitle.setText(title);
    }

    @Override
    public void onBackStackChanged() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            drawerArrow.animateDrawable(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            drawerArrow.animateDrawable(false);
            setActionBarTitle(getString(R.string.app_name));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            else if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                getSupportFragmentManager().popBackStack();
            }
            else {
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                        .setMessage("Are you sure?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("no", null).show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStackImmediate();
            }
        }
        else if (id == R.id.nav_promoted_user)
        {
            fragment = new PromotedUserFragment();
            toolbarTitle.setText("Promoted User");
            setActionBarTitle("Promoted User");
            //startActivity(new Intent(this, ShopProfileActivity.class));
        }
        else if (id == R.id.nav_setting)
        {
            //startActivity(new Intent(this, ShopProfileActivity.class));
        }
        else if (id == R.id.nav_logout)
        {
            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.action_logout_confirm))
//                            .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.dialog_exit, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
//                            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
//                            LoginManager.getInstance().logOut();
                            Intent ii = new Intent(HomeActivity.this, LoginActivity.class);
                            ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(ii);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();
            //startActivity(new Intent(this, ShopProfileActivity.class));
        }
        if(fragment != null) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(FRAGMENT_PLACEHOLDER, fragment, fragment.getTag())
                    .addToBackStack(fragment.getTag())
                    .commit();
        }

        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

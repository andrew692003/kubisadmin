package com.twiscode.kubisadmin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class PromoteUserActivity extends AppCompatActivity {

    private Activity act;
    private int DISPLAY = 0;
    private final int CHOOSE = 0, FILLING = 1;
    private ImageButton closeSubmitBtn;
    private BroadcastReceiver gotoFilling;
    String s1;
    Toolbar mToolbar;
    String startupType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote_user);

        act=this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(null);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        //closeSubmitBtn = (ImageButton) findViewById(R.id.closeSubmitBtn);
        displayView(FILLING);

//        closeSubmitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                finish();
//            }
//        });
    }

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case FILLING:
                fragment = new FillingFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentPlaceholder, fragment);
            fragmentTransaction.commitAllowingStateLoss();
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
}

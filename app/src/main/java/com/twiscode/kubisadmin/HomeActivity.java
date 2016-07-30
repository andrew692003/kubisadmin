package com.twiscode.kubisadmin;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.twiscode.kubisadmin.Interface.CustomToolbarActivity;

public class HomeActivity extends AppCompatActivity implements CustomToolbarActivity, NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener{

    private TextView toolbarTitle;
    private FragmentManager fragmentManager;
    private static final int FRAGMENT_PLACEHOLDER = R.id.fragmentPlaceholder;
    private DrawerLayout drawer;
    private MenuArrowDrawable drawerArrow;
    View headerView;
    private ImageButton sideback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle = (TextView) findViewById(R.id.main_toolbar_title);
        setActionBarTitle(getString(R.string.app_name));

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

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            fragment = new HomeMainFragment();
            toolbarTitle.setText("Kubis Admin");
            setActionBarTitle("Kubis Admin");
        }
        else if (id == R.id.nav_promoted_user)
        {

            //startActivity(new Intent(this, ShopProfileActivity.class));
        }
        else if (id == R.id.nav_setting)
        {
            //startActivity(new Intent(this, ShopProfileActivity.class));
        }
        else if (id == R.id.nav_logout)
        {
            //startActivity(new Intent(this, ShopProfileActivity.class));
        }

        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        ft.replace(FRAGMENT_PLACEHOLDER, fragment).commit();
//        } else if (id == R.id.nav_shop_setting) {
//            //
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

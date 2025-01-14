package kitkatstudio.com.taskmanagement;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import kitkatstudio.com.taskmanagement.fragments.AddTaskFragment;
import kitkatstudio.com.taskmanagement.fragments.CompletedAdminFragment;
import kitkatstudio.com.taskmanagement.fragments.HomeAdminFragment;
import kitkatstudio.com.taskmanagement.fragments.HomeFragment;
import kitkatstudio.com.taskmanagement.fragments.PendingAdminFragment;
import kitkatstudio.com.taskmanagement.fragments.ProfileAdminFragment;
import kitkatstudio.com.taskmanagement.fragments.ShowMessageFragment;
import kitkatstudio.com.taskmanagement.fragments.UpdateAdminFragment;
import kitkatstudio.com.taskmanagement.prefManager.PrefManager;

public class MainAdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fm;

    private PrefManager prefManager;

    Class fragmentClass;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefManager = new PrefManager(this);

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.contentAdmin, new HomeAdminFragment()).commit();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        String frag = getSupportFragmentManager().getFragments().toString();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        else  if (!prefManager.getAdminEmailId().equals(""))
        {
            if (! frag.contains("HomeAdminFragment") )
            {
                try
                {
                    fm.beginTransaction().replace(R.id.contentAdmin, new HomeAdminFragment()).commit();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        else {

            if(doubleBackToExitPressedOnce) {
                finish();
                System.exit(0);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_admin, menu);
        return true;
    }


    public void logout()
    {
        //prefManager.setUserEmailId("","","","");

        prefManager.setAdminEmailId("","");
        Intent intent = new Intent(this, TypeActivity.class);
        ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent, activityOptions.toBundle());
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            fm.beginTransaction().replace(R.id.contentAdmin, new HomeAdminFragment()).commit();

        } else if (id == R.id.nav_profile) {

            fm.beginTransaction().replace(R.id.contentAdmin, new ProfileAdminFragment()).commit();

        } else if (id == R.id.nav_task) {

            fm.beginTransaction().replace(R.id.contentAdmin, new AddTaskFragment()).commit();

        } else if (id == R.id.nav_pending) {

            fm.beginTransaction().replace(R.id.contentAdmin, new PendingAdminFragment()).commit();

        } else if (id == R.id.nav_completed) {

            fm.beginTransaction().replace(R.id.contentAdmin, new CompletedAdminFragment()).commit();

        } else if (id == R.id.nav_message) {

            fm.beginTransaction().replace(R.id.contentAdmin, new ShowMessageFragment()).commit();

        } else if (id == R.id.nav_update) {

            fm.beginTransaction().replace(R.id.contentAdmin, new UpdateAdminFragment()).commit();

        } else if (id == R.id.nav_logout) {
            logout();
        } else if (id == R.id.nav_share) {

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String appLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
            i.putExtra(Intent.EXTRA_TEXT, appLink);
            startActivity(Intent.createChooser(i, "Choose One"));

        } else if (id == R.id.nav_rate) {

            startActivity(new Intent(Intent.ACTION_VIEW, getPlayStoreLink()));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Uri getPlayStoreLink() {
        return Uri.parse("market://details?id=" + getPackageName());
    }
}

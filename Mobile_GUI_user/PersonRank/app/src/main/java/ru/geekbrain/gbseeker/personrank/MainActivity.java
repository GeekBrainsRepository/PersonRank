package ru.geekbrain.gbseeker.personrank;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.net.RestAPI;
import ru.geekbrain.gbseeker.personrank.net.iNet2SQL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final String TAG = "MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //RestAPI.authentication(new emptyINet2SQL());

        DBHelper.createDBHelper(this);
        DBHelper.getInstance().fillByFakeData();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.FrameContainer);
        if (fragment == null) {
            fragment = new SiteList();
            fm.beginTransaction()
                    .add(R.id.FrameContainer, fragment)
                    .commit();
          /*  fragment = new PersonList();
            fm.beginTransaction()
                    .replace(R.id.FrameContainer, fragment)
                    .commit();
            fragment = new CommonStatsFragment();
            fm.beginTransaction()
                    .replace(R.id.FrameContainer, fragment)
                    .commit();*/
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

        if (id == R.id.common_stats_menu) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.FrameContainer);
            fragment = new CommonStatsFragment();
            fm.beginTransaction()
                    .replace(R.id.FrameContainer, fragment)
                    .commit();
        } else if (id == R.id.daily_stats_menu) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.FrameContainer);
            fragment = new DailyStatsFragment();
            fm.beginTransaction()
                    .replace(R.id.FrameContainer, fragment)
                    .commit();
        } else if (id == R.id.nav_persons) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.FrameContainer);
            fragment = new PersonList();
            fm.beginTransaction()
                    .replace(R.id.FrameContainer, fragment)
                    .commit();
        } else if (id == R.id.nav_keys) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.FrameContainer);
            fragment = new KeyWordList();
            fm.beginTransaction()
                    .replace(R.id.FrameContainer, fragment)
                    .commit();

        } else if (id == R.id.nav_sites) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.FrameContainer);
            fragment = new SiteList();
            fm.beginTransaction()
                    .replace(R.id.FrameContainer, fragment)
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}


class emptyINet2SQL implements iNet2SQL {
    public void updateDB(String json){}
    public void updateUI(){}
    public String getInfo(){return "MAIN";}
}
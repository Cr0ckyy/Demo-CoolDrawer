package sg.edu.rp.webservices.c347_l7_ex1democooldrawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    String[] drawerItems;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle drawerToggle;
    ArrayAdapter<String> aa;
    String currentTitle;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.left_drawer);

        drawerItems = new String[]{"Bio", "Vaccination", "Anniversary"};
        ab = getSupportActionBar();

        currentTitle = this.getTitle().toString();
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                drawerLayout,    /* DrawerLayout object */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close) { /* "close drawer" description */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                ab.setTitle(currentTitle);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ab.setTitle("Make a selection");
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        ab.setDisplayHomeAsUpEnabled(true);

        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, drawerItems);
        drawerList.setAdapter(aa);
        drawerList.setOnItemClickListener((parent, view, position, id) -> {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new BioFragment();
            } else if (position == 1) {
                fragment = new VaccinationFragment();
            } else if (position == 2)
                fragment = new AnniversaryFragment();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction trans = fm.beginTransaction();
            trans.replace(R.id.content_frame, fragment);
            trans.commit();
            drawerList.setItemChecked(position, true);
            currentTitle = drawerItems[position];

            // Set the drawer toggle as the DrawerListener
            ab.setTitle(currentTitle);
            drawerLayout.closeDrawer(drawerList);
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync toggle state so the indicator is shown properly.
        //  Have to call in onPostCreate()
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
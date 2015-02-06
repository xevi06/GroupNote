package com.example.propietario.groupnote;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private FrameLayout frameLayout;
    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> leftMenuItems = new ArrayList<String>();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle; //v4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        ////////////////////Drawer icon
        mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_drawer,R.string.open,R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ///////////////////////////////////////////////////////////
        //set fragment my notes
        Fragment fragment = new Data_Fragment("My notes");
            // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        leftMenuItems.add("My notes");
        leftMenuItems.add("New Group");
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,leftMenuItems);
        listView.setAdapter(adapter);
            // Highlight the selected item, update the title, and close the drawer
        listView.setItemChecked(0, true);
        setTitle(leftMenuItems.get(0));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //selectItem(adapter.getItem(position).toString(), position);
            }
        });


    }
    /**
     * Swaps fragments in the main content view
     */
    /*private void selectItem(String group, int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new Data_Fragment();
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();


        // Highlight the selected item, update the title, and close the drawer
        listView.setItemChecked(position, true);
        setTitle(leftMenuItems.get(position));
        drawerLayout.closeDrawer(listView);
    }*/

    @Override
        protected void onStart() {
            super.onStart();
    }

    private void initComponents() {
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        listView = (ListView) findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            ParseUser.logOut();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}



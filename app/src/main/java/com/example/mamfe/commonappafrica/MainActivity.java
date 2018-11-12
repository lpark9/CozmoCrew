package com.example.mamfe.commonappafrica;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        

        
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();


        Fragment searchFragment = new SearchFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.frame_container, searchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        //startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code
            } else {
                getFragmentManager().popBackStack(null, 0);

            }
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
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
////        if (id == R.id.nav_app) {
////            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ApplicationFragment()).commit();
////        } else
//        if (id == R.id.nav_college) {
//            Fragment collegeFragment = new CollegeFragment();
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//            transaction.replace(R.id.frame_container, collegeFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        } else if (id == R.id.nav_logout) {
//            //This is kept as a seperate act
//            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//        } else if (id == R.id.nav_profile) {
//            ProfileFragment profileFragment = new ProfileFragment();
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//            transaction.replace(R.id.frame_container, profileFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        } else if (id == R.id.nav_search) {
//            Fragment searchFragment = new SearchFragment();
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//            transaction.replace(R.id.frame_container, searchFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        } else if (id == R.id.nav_settings) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareMenuData() {
        /*
        <item
        android:id="@+id/nav_profile"
        android:title="Academic Profile" />
        <!--<item-->
            <!--android:id="@+id/nav_app"-->
            <!--android:title="My Application" />-->
        <item
        android:id="@+id/nav_college"
        android:title="My College" />
        <item
        android:id="@+id/nav_search"
        android:title="Search for College" />
        <item
        android:id="@+id/nav_settings"
        android:title="Settings" />
        <item
        android:id="@+id/nav_logout"
        android:title="Log Out" />
        */



        MenuModel academicProfileModel = new MenuModel("Academic Profile", true, false);
        MenuModel myAppModel = new MenuModel("My Application", true, true);
        MenuModel myCollegesModel = new MenuModel("My Colleges", true, false);
        MenuModel settingsModel = new MenuModel("Settings", true, false);
        MenuModel searchModel = new MenuModel("Search", true, false);
        MenuModel logoutModel = new MenuModel("Logout", true, false);

        headerList.add(searchModel);
        headerList.add(academicProfileModel);
        headerList.add(myAppModel);
        headerList.add(myCollegesModel);
        headerList.add(settingsModel);
        headerList.add(logoutModel);

        List<MenuModel> myAppChildList = new ArrayList<>();
        MenuModel appDetailsModel = new MenuModel("Application Details", false, false);
        MenuModel eduBackgroundModel = new MenuModel("Education Background", false, false);
        MenuModel familyInfoModel = new MenuModel("Family Information", false, false);
        MenuModel generalInfoModel = new MenuModel("General Information", false, false);
        MenuModel personalHealthModel = new MenuModel("Personal Health", false, false);
        MenuModel workExperienceModel = new MenuModel("Work Experience", false, false);

        myAppChildList.add(appDetailsModel);
        myAppChildList.add(eduBackgroundModel);
        myAppChildList.add(familyInfoModel);
        myAppChildList.add(generalInfoModel);
        myAppChildList.add(personalHealthModel);
        myAppChildList.add(workExperienceModel);

        childList.put(myAppModel, myAppChildList);
    }


    private void populateExpandableList() {

        expandableListAdapter = new NavigationExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (headerList.get(groupPosition).menuName.equals("Academic Profile")) {
                        ProfileFragment profileFragment = new ProfileFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.frame_container, profileFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);

                    } else if (headerList.get(groupPosition).menuName.equals("My Colleges")) {
                        Fragment collegeFragment = new CollegeFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.frame_container, collegeFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    } else if (headerList.get(groupPosition).menuName.equals("Settings")) {

                    } else if (headerList.get(groupPosition).menuName.equals("Logout")) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    } else if (headerList.get(groupPosition).menuName.equals("Search")) {
                        Fragment searchFragment = new SearchFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.frame_container, searchFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    }



                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);

                    if(model.menuName.equals("Application Details")) {
                        Fragment appDetailsFragment = new AcademicProfileApplicationDetails();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.frame_container, appDetailsFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    } else if(model.menuName.equals("Education Background")) {
                        Fragment eduBackgroundFragment = new AcademicProfileEducationBackground();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.frame_container, eduBackgroundFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    } else if(model.menuName.equals("Family Information")) {
                        Fragment familyInfoFragment = new AcademicProfileFamilyInfo();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.frame_container, familyInfoFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    } else if(model.menuName.equals("General Information")) {
                        Fragment genInfoFragment = new AcademicProfileGeneralInfo();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.frame_container, genInfoFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    } else if(model.menuName.equals("Personal Health")) {
                        Fragment personalHealthFragment = new AcademicProfilePersonalHealth();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.frame_container, personalHealthFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    } else if(model.menuName.equals("Work Experience")) {
                        Fragment workExperienceFragment = new AcademicProfileWorkExperience();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.frame_container, workExperienceFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    }
//                    if (model.url.length() > 0) {
//                        WebView webView = findViewById(R.id.webView);
//                        webView.loadUrl(model.url);
//                        onBackPressed();
//                    }
                }

                return false;
            }
        });
    }


}

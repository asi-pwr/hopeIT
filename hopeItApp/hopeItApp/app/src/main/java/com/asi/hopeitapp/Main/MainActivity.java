package com.asi.hopeitapp.Main;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.asi.hopeitapp.MainPage.MainPageFragment;
import com.asi.hopeitapp.Messages.MessagesFragment;
import com.asi.hopeitapp.MyPayments.PaymentsFragment;
import com.asi.hopeitapp.R;
import com.bumptech.glide.Glide;
import com.payu.android.sdk.payment.PaymentService;

import java.util.Stack;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private final String CLASS_TAG = "MainActivity";

    public static boolean appOnRestartCalled = false;

    private ActionBar appBar;

    private int currentMenuItem = 0;
    private Stack<Fragment> fragmentBackStack = new Stack<>();

    //  PLEASE NO
    public static PaymentService paymentService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        appBar = getSupportActionBar();

        paymentService = PaymentService.createInstance(this);

        if (savedInstanceState == null) {
            loadBaseFragment();
        }
    }

    private void loadBaseFragment(){
        MainPageFragment mainPageFragment = new MainPageFragment();
        fragmentBackStack.add(mainPageFragment);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainPageFragment).commit();
        setAppBar(mainPageFragment);
    }

    private void switchFragment(Fragment fragment){
        fragmentBackStack.add(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        setAppBar(fragment);
    }

    private void setAppBar(Fragment fragment) {
        if (appBar != null) {
            if (fragment instanceof MainPageFragment) {
                appBar.setTitle(R.string.title_main);
                currentMenuItem = 0;
            }
            if (fragment instanceof PaymentsFragment) {
                appBar.setTitle(R.string.title_payments);
                currentMenuItem = 1;
            }
            if (fragment instanceof MessagesFragment) {
                appBar.setTitle(R.string.title_messages);
                currentMenuItem = 2;
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(fragmentBackStack.size() >= 2){ //manage custom fragment back stack
                fragmentBackStack.pop();
                switchFragment(fragmentBackStack.pop());
            }
            else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ImageView menuImage = findViewById(R.id.menuImage);
        Glide.with(this)
                .load("http://vps345245.ovh.net/static/images/logos/hopeit-logo.png")
                .into(menuImage);

        menuImage.setOnClickListener(view -> lunchWebsite("http://hopeit.pl/"));

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks
        int id = item.getItemId();

        if (id == R.id.nav_main && currentMenuItem != 0) {
            currentMenuItem = 0;
            switchFragment(new MainPageFragment());
        }
        else if (id == R.id.nav_payments && currentMenuItem != 1) {
            currentMenuItem = 1;
            switchFragment(new PaymentsFragment());
        }
        else if (id == R.id.nav_messages && currentMenuItem != 2) {
            currentMenuItem = 2;
            switchFragment(new MessagesFragment());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        appOnRestartCalled = true;
        Log.i(CLASS_TAG, "app restart");
    }

    private void lunchWebsite(String url){
        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
    }
}

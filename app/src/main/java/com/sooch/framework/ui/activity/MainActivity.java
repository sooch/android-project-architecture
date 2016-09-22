package com.sooch.framework.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sooch.framework.R;
import com.sooch.framework.databinding.ActivityMainBinding;
import com.sooch.framework.ui.MainPages;
import com.sooch.framework.ui.fragment.FragmentCamera;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        FragmentManager.OnBackStackChangedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final long DRAWER_CLOSE_DELAY_MILLS = 350L;
    private static String EXTRA_MENU = "menu";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initToolbar();
        initView();

        if (savedInstanceState == null) {
            binding.main.toolbar.setTitle(getString(R.string.title_camera));
            replaceFragment(FragmentCamera.newInstance());

        } else if (savedInstanceState.getString(EXTRA_MENU) != null) {
            MainPages page = MainPages.forMenuId(savedInstanceState.getInt(EXTRA_MENU));
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(page.getPageName());
            if(fragment == null) {
                fragment = MainPages.forName(page.getPageName()).createFragment();
            }
            binding.main.toolbar.setTitle(page.getTitleResId());
            replaceFragment(fragment);
        }
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.content);
        if (current != null) {
            outState.putString(EXTRA_MENU, MainPages.forName(current).getPageName());
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
    public void onBackStackChanged() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment current = fm.findFragmentById(R.id.content);
        if (current == null) {
            finish();
            return;
        }
        MainPages page = MainPages.forName(current);
        binding.navView.setCheckedItem(page.getMenuId());
        binding.main.toolbar.setTitle(page.getTitleResId());
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
        if(item.getGroupId() == R.id.group_main) {
            MainPages page = MainPages.forMenuId(id);
            changePage(page.getTitleResId(), page.createFragment());
        }

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initToolbar() {
        setSupportActionBar(binding.main.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.main.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);
    }

    private void initView() {
        binding.main.fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

    }

    /**
     * Fragmentの切り替え
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                .replace(R.id.content, fragment, fragment.getClass().getSimpleName())
                // addToBackStackをコメントアウトしない場合, Fragmentは破棄されないことに注意する.
                // http://stackoverflow.com/questions/27913009/memory-leak-in-fragmentmanager/27913962#27913962
                .addToBackStack(null)
                .commit();
    }

    /**
     * メニューからページの変更
     * @param titleRes
     * @param fragment
     */
    private void changePage(@StringRes int titleRes, @NonNull Fragment fragment) {
        new Handler().postDelayed(() -> {
            binding.main.toolbar.setTitle(titleRes);
            replaceFragment(fragment);
        }, DRAWER_CLOSE_DELAY_MILLS);
    }

}

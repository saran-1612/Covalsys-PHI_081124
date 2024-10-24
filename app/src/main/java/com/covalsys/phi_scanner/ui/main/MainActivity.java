package com.covalsys.phi_scanner.ui.main;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.WorkInfo;

import com.covalsys.phi_scanner.BR;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.databinding.ActivityMainBinding;
import com.covalsys.phi_scanner.ui.ViewModelProviderFactory;
import com.covalsys.phi_scanner.ui.base.BaseActivity;
import com.covalsys.phi_scanner.ui.login.LoginActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final String TAG = "LocationUpdate";
    private TextView notificationCount, cartCount;

    @Inject
    ViewModelProviderFactory factory;

    private MainViewModel mMainViewModel;

    @Override
    public int getBindingVariable() {
        return BR.mainViewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        return mMainViewModel;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerLayout = navigationView.getHeaderView(0);
        //View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView name = headerLayout.findViewById(R.id.name);
        TextView mobile = headerLayout.findViewById(R.id.mobile);
        TextView login = headerLayout.findViewById(R.id.loginName);
        name.setText(mMainViewModel.getUserName());
        mobile.setText(mMainViewModel.getUserMobile());
        login.setText(mMainViewModel.getUserCode());

        navigationView.getMenu().findItem(R.id.nav_settings).setOnMenuItemClickListener(menuItem -> {
            if (drawer != null) {
                drawer.closeDrawer(GravityCompat.START);
            }
            //startActivity(SettingsActivity.getStartIntent(MainActivity.this));
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            if (drawer != null) {
                drawer.closeDrawer(GravityCompat.START);
            }
            logout();
            return true;
        });

       /* navigationView.getMenu().findItem(R.id.nav_products).setOnMenuItemClickListener(menu -> {
            if (drawer != null) {
                drawer.closeDrawer(GravityCompat.START);
            }
            Bundle bundle = new Bundle();
            bundle.putString("cardCode", "");
            bundle.putString("cardName", "");
            //startActivity(ProductsListActivity.getStartIntent(MainActivity.this, bundle));
            return true;
        });*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(() -> {
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment);
            currentFragment.onResume();
        });

        /*if (!checkLocationPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }*/

        //mMainViewModel.getApprovalDetails();
        //mMainViewModel.loadData();
        /*if (!mMainViewModel.getUserType().equalsIgnoreCase("SE")) {
            if (mMainViewModel.getUserType().equalsIgnoreCase("AG")) {
                Menu menu = navigationView.getMenu();
                MenuItem activity = menu.findItem(R.id.nav_activity);
                activity.setVisible(false);
                MenuItem checkOut = menu.findItem(R.id.nav_check_out);
                checkOut.setVisible(true);
                MenuItem lead = menu.findItem(R.id.nav_leads);
                lead.setVisible(false);
                MenuItem payment = menu.findItem(R.id.nav_history);
                payment.setVisible(false);
                MenuItem expense = menu.findItem(R.id.nav_expense);
                expense.setVisible(false);
            } else {
                Menu menu = navigationView.getMenu();
                MenuItem activity = menu.findItem(R.id.nav_activity);
                activity.setVisible(false);
                MenuItem checkOut = menu.findItem(R.id.nav_check_out);
                checkOut.setVisible(false);
                MenuItem lead = menu.findItem(R.id.nav_leads);
                lead.setVisible(false);
                MenuItem payment = menu.findItem(R.id.nav_history);
                payment.setVisible(false);
                MenuItem expense = menu.findItem(R.id.nav_expense);
                expense.setVisible(false);
            }
        }*/
        // START Worker
        /*PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(MyWorkManager.class, 15, TimeUnit.MINUTES)
                .addTag(TAG)
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork("Location", ExistingPeriodicWorkPolicy.REPLACE, periodicWork);
        showSnackBar("Location tracker started");*/

    }

    private boolean isWorkScheduled(List<WorkInfo> workInfos) {
        boolean running = false;
        if (workInfos == null || workInfos.size() == 0) return false;
        for (WorkInfo workStatus : workInfos) {
            running = workStatus.getState() == WorkInfo.State.RUNNING | workStatus.getState() == WorkInfo.State.ENQUEUED;
        }
        return running;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        final MenuItem menuItem1 = menu.findItem(R.id.action_cart);
        final MenuItem menuItem = menu.findItem(R.id.action_notification);
        View actionView = menuItem1.getActionView();
        notificationCount = actionView.findViewById(R.id.cart_badge);

        mMainViewModel.getItemCount().observe(this, t -> {
            if(!t.isEmpty()) {
                notificationCount.setText(String.valueOf(t.get(0).getOpeningQty() + "/" + t.get(0).getClosingQty()));
            }
        });

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                //startActivity(CartActivity.newIntent(this));
                return true;
            case R.id.action_notification:
                //startActivity(NotificationActivity.newIntent(this));
                return true;
            case R.id.action_sync:
                //startActivity(ViewCreatedActivity.newIntent(this));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    mMainViewModel.deleteLocal();
                    startActivity(LoginActivity.newIntent(MainActivity.this));
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * All about permission
     */
    private boolean checkLocationPermission() {
        int result3 = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
        int result4 = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        return result3 == PackageManager.PERMISSION_GRANTED &&
                result4 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean coarseLocation = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean fineLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (coarseLocation && fineLocation)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private long lastPressedTime;
    private static final int PERIOD = 2000;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (event.getDownTime() - lastPressedTime < PERIOD) {
                    finish();
                } else {
                    showSnackBar("Press back again to exit Covalsys app.");
                    lastPressedTime = event.getEventTime();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mMainViewModel.getApprovalDetails();
        String add =  "Covalsysoft Solutions Pvt Ltd";
        //getLocationFromAddress(add);
    }

    public void getLocationFromAddress(String strAddress) {

        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress, 5);
            //check for null
            if (address == null) {
                return;
            }

            //Lets take first possibility from the all possibilities.
            Address location = address.get(0);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Log.e("latLng", String.valueOf(latLng));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

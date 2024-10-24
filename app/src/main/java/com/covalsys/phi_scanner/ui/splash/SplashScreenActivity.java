package com.covalsys.phi_scanner.ui.splash;

import static com.covalsys.phi_scanner.utils.Constants.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.covalsys.phi_scanner.BR;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.databinding.ActivitySplashScreenBinding;
import com.covalsys.phi_scanner.ui.ViewModelProviderFactory;
import com.covalsys.phi_scanner.ui.base.BaseActivity;
import com.covalsys.phi_scanner.ui.login.LoginActivity;
import com.covalsys.phi_scanner.ui.main.MainActivity;
import com.covalsys.phi_scanner.utils.NetworkUtils;

import javax.inject.Inject;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends BaseActivity<SplashScreenViewModel, ActivitySplashScreenBinding> implements SplashScreenNavigator {

    @Inject
    ViewModelProviderFactory factory;

    SplashScreenViewModel mViewModel;

    @Override
    public int getBindingVariable() {
        return BR.splashViewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected SplashScreenViewModel getViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(SplashScreenViewModel.class);
        return mViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
        setUp();
    }



    public void setUp() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.indigo_trans));
        showLoading();
        mViewModel.getPreferenceHelper().getSalesEmpCode();
        Log.e(TAG, String.valueOf(mViewModel.getPreferenceHelper().getSalesEmpCode()));
        boolean checkConnection = NetworkUtils.isNetworkConnected(this);
        //if (checkConnection) {
            hideLoading();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if(mViewModel.getPreferenceHelper().getIsLoggedIn()){
                    Intent mainIntent = MainActivity.newIntent(SplashScreenActivity.this);
                    startActivity(mainIntent);
                    finish();
                }else{
                    Intent mainIntent = LoginActivity.newIntent(SplashScreenActivity.this);
                    startActivity(mainIntent);
                    finish();
                }
            }, 3000);

        /*} else {
            hideLoading();
            showSnackBar("You are in Offline");
        }*/
    }

}


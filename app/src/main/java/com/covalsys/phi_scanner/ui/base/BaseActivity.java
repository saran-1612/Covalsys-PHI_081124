package com.covalsys.phi_scanner.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.utils.CommonUtils;
import com.covalsys.phi_scanner.utils.NetworkUtils;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public abstract class BaseActivity<V extends ViewModel,D extends ViewDataBinding> extends AppCompatActivity
        implements HasSupportFragmentInjector,BaseFragment.Callback {

    public static final String MyPREFERENCES = "vnc_temp" ;
    public static final String KEY_CURRENT_THEME = "current_theme";
    public static final String GREEN_THEME = "green";
    public static final String RED_THEME = "red";
    public static final String BLUE_THEME = "blue";
    public static final String PURPLE_THEME = "purple";
    public static final String YELLOW_THEME = "yellow";
    public static final String PINK_THEME = "pink";
    public static final String ORANGE_THEME = "orange";
    public static final String INDIGO_THEME = "indigo";

    public static final String UPS_THEME = "ups";

    public String mCurrentTheme="";
    SharedPreferences mSpref;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;
    public D dataBinding;
    private V mViewModel;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    protected abstract V getViewModel();


    /*public D getViewDataBinding() {
        return dataBinding;
    }
*/
    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        mSpref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mCurrentTheme = mSpref.getString(KEY_CURRENT_THEME,INDIGO_THEME);
        setAppTheme(mCurrentTheme);
        performDataBinding();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }


    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    private void performDataBinding() {
       // mViewModel = new ViewModelProvider(this,viewModelFactory).get(getViewModel());
        dataBinding = DataBindingUtil.setContentView(this, getLayoutRes());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        dataBinding.setVariable(getBindingVariable(), mViewModel);
        dataBinding.executePendingBindings();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentAndroidInjector;
    }

    public void showSnackBar(String message,int color) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(color);
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
       // sbView.setBackgroundColor(color);
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    public void showAlertDialog(String message){
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes" ,(dialog, which) -> {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_LONG).show();
                }).setNegativeButton("No",(dialog, which) -> {
                    Toast.makeText(getApplicationContext(),"Re-Scan The Item",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String mSelectedTheme = mSpref.getString(KEY_CURRENT_THEME,INDIGO_THEME);
        if(!mCurrentTheme.equals(mSelectedTheme)){
            reload();
        }
    }

    public void reload() {
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private void setAppTheme(String mCurrentTheme) {
        switch (mCurrentTheme){
            case YELLOW_THEME:
                setTheme(R.style.AppTheme_Yellow);
                break;
            case GREEN_THEME:
                setTheme(R.style.AppTheme_Green);
                break;
            case RED_THEME:
                setTheme(R.style.AppTheme_Red);
                break;
            case PURPLE_THEME:
                setTheme(R.style.AppTheme_Purple);
                break;
            case BLUE_THEME:
                setTheme(R.style.AppTheme_Blue);
                break;
            case INDIGO_THEME:
                setTheme(R.style.AppTheme_Indigo);
                break;
            case PINK_THEME:
                setTheme(R.style.AppTheme_Pink);
                break;
            case ORANGE_THEME:
                setTheme(R.style.AppTheme_Deep_Orange);
                break;
        }
    }
}

package com.covalsys.phi_scanner.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.covalsys.phi_scanner.BR;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.network.models.get.GetDatabaseModel;
import com.covalsys.phi_scanner.data.network.models.get.LoginModel;
import com.covalsys.phi_scanner.databinding.ActivityLoginBinding;
import com.covalsys.phi_scanner.ui.ViewModelProviderFactory;
import com.covalsys.phi_scanner.ui.base.BaseActivity;
import com.covalsys.phi_scanner.ui.main.MainActivity;

import java.util.List;
import java.util.concurrent.Executor;
import javax.inject.Inject;

/**
 * Created by Prasanth on 11-07-2020.
 */
public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> implements LoginNavigator {

    @Inject
    ViewModelProviderFactory factory;

    private BroadcastReceiver mBroadcastReceiver;
    private String token;
    private LoginViewModel mLoginViewModel;
    private String dbCode,dbName;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    public int getBindingVariable() {
        return BR.loginViewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public LoginViewModel getViewModel() {
        mLoginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
        return mLoginViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModel.setNavigator(this);
        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
               // Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // Authentication succeeded
                Toast.makeText(getApplicationContext(), "Login Success Full!", Toast.LENGTH_SHORT).show();
                Intent intent = MainActivity.newIntent(LoginActivity.this);
                startActivity(intent);
                finish();
                // Continue to next activity or unlock the app
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
               // Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();

            }
        });

        // Setup the prompt info
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Fingerprint Authentication")
                .setSubtitle("Authenticate using your fingerprint")
                .setNegativeButtonText("Use account password")
                .build();

        // Show the biometric prompt
        showBiometricPrompt();
        initObservables();
    }

    private void showBiometricPrompt() {
        BiometricManager biometricManager = BiometricManager.from(this);
        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            biometricPrompt.authenticate(promptInfo);
        } else {
            Toast.makeText(this, "Biometric authentication is not available on this device.", Toast.LENGTH_SHORT).show();
        }
    }

    public void initObservables() {
        mLoginViewModel.error.removeObservers(this);
        mLoginViewModel.error.observe(this, this::showSnackBar);

        mLoginViewModel.progressBar.observe(this, aBoolean -> {
            if (aBoolean) {
               showLoading();
            } else {
               hideLoading();
            }
        });

        mLoginViewModel.getLogin().removeObservers(this);
        mLoginViewModel.getLogin().observe(this, loginModelResource -> {
            switch (loginModelResource.status) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    hideLoading();
                    assert loginModelResource.data != null;
                    setLoginResponse(loginModelResource.data.getResponseObject());
                    break;
                case ERROR:
                    hideLoading();
                    showSnackBar(loginModelResource.getMessage());
                    break;
                default:
                    break;
            }
        });
    }

    public void setTypeResponse(List<GetDatabaseModel.ResponseObject> dataResponse) {

        ArrayAdapter<GetDatabaseModel.ResponseObject> adapter = new ArrayAdapter<GetDatabaseModel.ResponseObject>(this,
                android.R.layout.simple_spinner_item, dataResponse);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dataBinding.spType.setAdapter(adapter);
        if(dataResponse.size() > 0) {
            GetDatabaseModel.ResponseObject data = dataResponse.get(0);
            mLoginViewModel.setTypeCode(data.getDBID(), data.getDBName(), this);
        }

        dataBinding.spType.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            dbCode = dataResponse.get(position).getDBID();
            dbName = dataResponse.get(position).getDBName();
            mLoginViewModel.setTypeCode(dbCode, dbName,this);
        });
    }

    public void setLoginResponse(List<LoginModel.ResponseObject> loginResponse) {

        mLoginViewModel.saveDetails(loginResponse, LoginActivity.this);
        Intent intent = MainActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();

    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void onPostSuccess(List<LoginModel> loginModel) {

    }

    @Override
    public void onPostFailed(String msg) {

    }

    @Override
    public void openForgotPassword() {
        //startActivity(ForgotPasswordActivity.newIntent(LoginActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //sendFcmRegistrationToken();
    }
}

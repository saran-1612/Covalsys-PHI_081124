package com.covalsys.phi_scanner.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.covalsys.phi_scanner.di.AppContext;
import com.covalsys.phi_scanner.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Prasanth on 09,July,2020
 */
@Singleton
public class PreferencesManager implements PreferenceHelper {

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_LOGGED_IN_MODE";
    private static final String PREF_KEY_USER_CODE = "PREF_KEY_USER_CODE";
    private static final String PREF_KEY_USER_MOBILE = "PREF_KEY_CURRENT_MOBILE";
    private static final String PREF_KEY_USER_FIRST_NAME = "PREF_KEY_CURRENT_USER_FIRST_NAME";
    private static final String PREF_KEY_USER_LAST_NAME = "PREF_KEY_CURRENT_USER_LAST_NAME";
    private static final String PREF_KEY_EMP_NAME = "PREF_KEY_EMP_NAME";
    private static final String PREF_KEY_USER_PWD = "PREF_KEY_CURRENT_USER_PWD";
    private static final String PREF_KEY_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_FIRST_TIME = "PREF_KEY_FIRST_TIME";
    private static final String PREF_KEY_USER_PROFILE_PIC_URL = "PREF_KEY_USER_PROFILE_PIC_URL";
    private static final String PREF_KEY_COACH_MARK = "PREF_KEY_COACH_MARK";
    private static final String PREF_KEY_DB_NAME = "PREF_KEY_DB_NAME";
    private static final String PREF_KEY_DB_ID = "PREF_KEY_DB_ID";
    private static final String PREF_KEY_SALES_EMP_CODE = "PREF_KEY_SALES_EMP_CODE";
    private static final String PREF_KEY_EMP_TYPE_CODE = "PREF_KEY_EMP_TYPE_CODE";
    private static final String PREF_KEY_EMP_TYPE_NAME = "PREF_KEY_EMP_TYPE_NAME";
    private static final String PREF_KEY_FCM_TOKEN = "PREF_KEY_FCM_TOKEN";
    private static final String PREF_KEY_SALES_APPROVER = "PREF_KEY_SALES_APPROVER";
    private static final String PREF_KEY_PAYMENT_APPROVER = "PREF_KEY_PAYMENT_APPROVER";
    private static final String PREF_KEY_BP_APPROVER = "PREF_KEY_BP_APPROVER";
    private static final String PREF_KEY_CardCode = "PREF_KEY_CardCode";
    private static final String PREF_KEY_CardName = "PREF_KEY_CardName";
    private static final String PREF_KEY_DocDate = "PREF_KEY_DocDate";
    private static final String PREF_KEY_DocNum = "PREF_KEY_DocNum";
    private static final String PREF_KEY_stock_DocNum = "PREF_KEY_Stock_DocNum";
    private static final String PREF_KEY_DocEntry = "PREF_KEY_DocEntry";
    private static final String PREF_KEY_DocStatus = "PREF_KEY_DocStatus";

    private final SharedPreferences mPrefs;

    @Inject
    public PreferencesManager(@AppContext Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
       // mAppContext = context;
    }

    @Override
    public boolean getIsLoggedIn() {
        return mPrefs.getBoolean(PREF_KEY_USER_LOGGED_IN_MODE,false);
    }

    @Override
    public void setIsLoggedIn(boolean isLoggedIn) {
        mPrefs.edit().putBoolean(PREF_KEY_USER_LOGGED_IN_MODE,isLoggedIn).apply();
    }

    @Override
    public String getFcmToken() {
        return mPrefs.getString(PREF_KEY_FCM_TOKEN,null);
    }

    @Override
    public void setFcmToken(String token) {
        mPrefs.edit().putString(PREF_KEY_FCM_TOKEN,token).apply();
    }

    @Override
    public String getUserEmail() {
        return mPrefs.getString(PREF_KEY_USER_EMAIL,null);
    }

    @Override
    public void setUserEmail(String userId) {
        mPrefs.edit().putString(PREF_KEY_USER_EMAIL,userId).apply();
    }

    @Override
    public String getFirstName() {
        return mPrefs.getString(PREF_KEY_USER_FIRST_NAME,null);
    }

    @Override
    public void setFirstName(String firstName) {
        mPrefs.edit().putString(PREF_KEY_USER_FIRST_NAME,firstName).apply();
    }

    @Override
    public String getLastName() {
        return mPrefs.getString(PREF_KEY_USER_LAST_NAME,null);
    }

    @Override
    public void setLastName(String lastName) {
        mPrefs.edit().putString(PREF_KEY_USER_LAST_NAME,lastName).apply();
    }


    @Override
    public String getSalesEmpCode() {
        return mPrefs.getString(PREF_KEY_SALES_EMP_CODE,null);
    }

    @Override
    public void setSalesEmpCode(String userId) {
        mPrefs.edit().putString(PREF_KEY_SALES_EMP_CODE,userId).apply();
    }

    @Override
    public void setSalesEmpName(String empName) {
        mPrefs.edit().putString(PREF_KEY_EMP_NAME,empName).apply();
    }

    @Override
    public String getSalesEmpName() {
        return mPrefs.getString(PREF_KEY_EMP_NAME,null);
    }

    @Override
    public String getEmpTypeCode() {
        return mPrefs.getString(PREF_KEY_EMP_TYPE_CODE,null);
    }

    @Override
    public void setEmpTypeCode(String typeCode) {
        mPrefs.edit().putString(PREF_KEY_EMP_TYPE_CODE,typeCode).apply();
    }

    @Override
    public String getEmpTypeName() {
        return mPrefs.getString(PREF_KEY_EMP_TYPE_NAME,null);
    }

    @Override
    public void setEmpTypeName(String typeName) {
        mPrefs.edit().putString(PREF_KEY_EMP_TYPE_NAME,typeName).apply();
    }


    @Override
    public String getMobileNo() {
        return mPrefs.getString(PREF_KEY_USER_MOBILE,null);
    }

    @Override
    public void setMobileNo(String mobileNo) {
        mPrefs.edit().putString(PREF_KEY_USER_MOBILE,mobileNo).apply();
    }

    @Override
    public String getDbName() {
        return mPrefs.getString(PREF_KEY_DB_NAME,null);
    }

    @Override
    public void setDbName(String dbName) {
        mPrefs.edit().putString(PREF_KEY_DB_NAME,dbName).apply();
    }

    @Override
    public String getDbId() {
        return mPrefs.getString(PREF_KEY_DB_ID,null);
    }

    @Override
    public void setDbId(String dbId) {
        mPrefs.edit().putString(PREF_KEY_DB_ID,dbId).apply();
    }

    @Override
    public String getPassword() {
        return mPrefs.getString(PREF_KEY_USER_PWD,null);
    }

    @Override
    public void setPassword(String password) {
        mPrefs.edit().putString(PREF_KEY_USER_PWD,password).apply();
    }

    @Override
    public String getSalesApprover() {
        return mPrefs.getString(PREF_KEY_SALES_APPROVER,null);
    }

    @Override
    public void setSalesApprover(String salesApprover) {
        mPrefs.edit().putString(PREF_KEY_SALES_APPROVER,salesApprover).apply();
    }

    @Override
    public String getBpApprover() {
        return mPrefs.getString(PREF_KEY_BP_APPROVER,null);
    }

    @Override
    public void setBpApprover(String bpApprover) {
        mPrefs.edit().putString(PREF_KEY_BP_APPROVER,bpApprover).apply();
    }

    @Override
    public String getPaymentApprover() {
        return mPrefs.getString(PREF_KEY_PAYMENT_APPROVER,null);
    }

    @Override
    public void setPaymentApprover(String paymentApprover) {
        mPrefs.edit().putString(PREF_KEY_PAYMENT_APPROVER,paymentApprover).apply();
    }

    @Override
    public String getUserCode() {
        return mPrefs.getString(PREF_KEY_USER_CODE,null);
    }

    @Override
    public void setUserCode(String userCode) {
        mPrefs.edit().putString(PREF_KEY_USER_CODE,userCode).apply();
    }

    @Override
    public String getDeliveryCardCode() {
        return mPrefs.getString(PREF_KEY_CardCode,null);
    }

    @Override
    public void setDeliveryCardCode(String cardCode) {
        mPrefs.edit().putString(PREF_KEY_CardCode, cardCode).apply();
    }

    @Override
    public String getDeliveryCardName() {
        return mPrefs.getString(PREF_KEY_CardName,null);
    }

    @Override
    public void setDeliveryCardName(String cardName) {
        mPrefs.edit().putString(PREF_KEY_CardName,cardName).apply();
    }

    @Override
    public String getDeliveryDocDate() {
        return mPrefs.getString(PREF_KEY_DocDate,null);
    }

    @Override
    public void setDeliveryDocDate(String DocDate) {
        mPrefs.edit().putString(PREF_KEY_DocDate,DocDate).apply();
    }

    @Override
    public String getDeliveryDocNum() {
        return mPrefs.getString(PREF_KEY_DocNum,null);
    }

    @Override
    public void setDeliveryDocNum(String DocNum) {
        mPrefs.edit().putString(PREF_KEY_DocNum,DocNum).apply();
    }

    @Override
    public String getStockDocNum() {
        return mPrefs.getString(PREF_KEY_stock_DocNum,null);
    }

    @Override
    public void setStockDocNum(String DocNum) {
        mPrefs.edit().putString(PREF_KEY_stock_DocNum,DocNum).apply();
    }

    @Override
    public String getDeliveryDocEntry() {
        return mPrefs.getString(PREF_KEY_DocEntry,"");
    }

    @Override
    public void setDeliveryDocEntry(String DocEntry) {
        mPrefs.edit().putString(PREF_KEY_DocEntry,DocEntry).apply();
    }

    @Override
    public String getDeliveryDocStatus() {
        return mPrefs.getString(PREF_KEY_DocStatus,"");
    }

    @Override
    public void setDeliveryDocStatus(String Status) {
        mPrefs.edit().putString(PREF_KEY_DocStatus,Status).apply();
    }

    @Override
    public void deleteUserName() {
        mPrefs.edit().remove(PREF_KEY_USER_FIRST_NAME).apply();
    }

    @Override
    public void deleteUserPassword() {
        mPrefs.edit().remove(PREF_KEY_USER_PWD).apply();
    }

    @Override
    public void deletePreferences() {
        mPrefs.edit().clear().apply();
    }
}

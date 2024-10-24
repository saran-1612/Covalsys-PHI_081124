package com.covalsys.phi_scanner.data.preferences;

/**
 * Created by Prasanth on 11,November,2019
 */
public interface PreferenceHelper {

    boolean getIsLoggedIn();

    void setIsLoggedIn(boolean isLoggedIn);

    String getFcmToken();

    void setFcmToken(String token);

    String getUserEmail();

    void setUserEmail(String userId);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getSalesEmpCode();

    void setSalesEmpCode(String empCode);

    void setSalesEmpName(String empName);

    String getSalesEmpName();

    String getEmpTypeCode();

    void setEmpTypeCode(String typeCode);

    String getEmpTypeName();

    void setEmpTypeName(String typeName);

    String getMobileNo();

    void setMobileNo(String mobileNo);

    String getDbName();

    void setDbName(String dbName);

    String getDbId();

    void setDbId(String dbId);

    String getPassword();

    void setPassword(String password);

    String getSalesApprover();

    void setSalesApprover(String salesApprover);

    String getBpApprover();

    void setBpApprover(String bpApprover);

    String getPaymentApprover();

    void setPaymentApprover(String paymentApprover);

    String getUserCode();

    void setUserCode(String userCode);

    String getDeliveryCardCode();

    void setDeliveryCardCode(String cardCode);

    String getDeliveryCardName();

    void setDeliveryCardName(String cardName);

    String getDeliveryDocDate();

    void setDeliveryDocDate(String DocDate);

    String getDeliveryDocNum();

    void setDeliveryDocNum(String DocNum);

    String getStockDocNum();

    void setStockDocNum(String DocNum);

    String getDeliveryDocEntry();

    void setDeliveryDocEntry(String DocEntry);

    String getDeliveryDocStatus();

    void setDeliveryDocStatus(String Status);

    void deleteUserName();

    void deleteUserPassword();

    void deletePreferences();
}

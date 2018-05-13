package wong.org.ex.model;

import android.content.SharedPreferences;

import wong.org.ex.sql.UserAccountDao;

public interface ILoginModel {
    UserAccountDao getUserInfoFromDatabase(String name);

    void setUserInfoToDatabase(UserAccountDao userAccountDao);

    boolean getLoginState();

    String getLoginStateName();

    String getLoginStatePsw();

    void setLoginStateName(String name);

    void setLoginStatePsw(String psw);

    void setLoginState(boolean state);
}

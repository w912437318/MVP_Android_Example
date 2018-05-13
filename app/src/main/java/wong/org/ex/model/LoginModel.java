package wong.org.ex.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import wong.org.ex.sql.UserAccountDao;
import wong.org.ex.sql.UserAccountDatabaseOpenHelper;

public class LoginModel implements ILoginModel {
    private Context mContext;
    private Dao<UserAccountDao, Integer> mUserAccountDao;
    private SharedPreferences mLoginState;
    private SharedPreferences.Editor mLoginStateEditor;

    public LoginModel(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        UserAccountDatabaseOpenHelper userAccountDatabaseOpenHelper = UserAccountDatabaseOpenHelper.getUserAccountDatabaseOpenHelper(mContext);
        mUserAccountDao = userAccountDatabaseOpenHelper.getUserAccountDao();
        mLoginState = mContext.getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        mLoginStateEditor = mLoginState.edit();
    }

    @Override
    public UserAccountDao getUserInfoFromDatabase(String name) {
        try {
            List<UserAccountDao> userAccountDaoList = mUserAccountDao.queryForEq("UserName", name);
            if (userAccountDaoList.size() != 0) return userAccountDaoList.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setUserInfoToDatabase(UserAccountDao userAccountDao) {
        try {
            mUserAccountDao.create(userAccountDao);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getLoginState() {
        return mLoginState.getBoolean("LoginState", false);
    }

    @Override
    public String getLoginStateName() {
        return mLoginState.getString("LoginStateName", "");
    }

    @Override
    public String getLoginStatePsw() {
        return mLoginState.getString("LoginStatePsw", "");
    }

    @Override
    public void setLoginStateName(String name) {
        mLoginStateEditor.putString("LoginStateName", name);
        mLoginStateEditor.commit();
    }

    @Override
    public void setLoginStatePsw(String psw) {
        mLoginStateEditor.putString("LoginStatePsw", psw);
        mLoginStateEditor.commit();
    }

    @Override
    public void setLoginState(boolean state) {
        mLoginStateEditor.putBoolean("LoginState", state);
        mLoginStateEditor.commit();
    }
}

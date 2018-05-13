package wong.org.ex.presenter;

import android.content.Intent;
import android.content.SharedPreferences;

import wong.org.ex.model.ILoginModel;
import wong.org.ex.model.LoginModel;
import wong.org.ex.sql.UserAccountDao;
import wong.org.ex.view.HomeActivity;
import wong.org.ex.view.ILoginActivity;

public class LoginPresenter {
    private ILoginActivity mView;
    private ILoginModel mModel;

    public LoginPresenter(ILoginActivity view) {
        this.mView = view;
        this.mModel = new LoginModel(mView.getContext());
    }

    public void userLogin() {
        if (checkUserInputInfo() && compareUserInputInfo()) {
            mView.getContext().startActivity(new Intent(mView.getContext(), HomeActivity.class));
            saveLoginState();
        }
    }

    private void saveLoginState() {
        if (mView.getLoginState()) {
            mModel.setLoginState(mView.getLoginState());
            mModel.setLoginStateName(mView.getUserName());
            mModel.setLoginStatePsw(mView.getUserPsw());
        }
    }

    public void loadLoginState() {
        if (mModel.getLoginState()) {
            mView.setLoginState(mModel.getLoginState());
            mView.setUserName(mModel.getLoginStateName());
            mView.setUserPsw(mModel.getLoginStatePsw());
        }
    }

    public boolean userRegister() {
        if (checkRegisterInfo() && compareRegisterInfo()) {
            mModel.setUserInfoToDatabase(new UserAccountDao(
                    mView.getRegisterUserName(),
                    mView.getRegisterUserPsw(),
                    mView.getRegisterUserMail()));
            return true;
        }
        return false;
    }

    private boolean checkRegisterInfo() {
        String registerUserName = mView.getRegisterUserName();
        String registerUserPsw = mView.getRegisterUserPsw();
        String registerUserConfirmPsw = mView.getRegisterUserConfirmPsw();
        String registerUserMail = mView.getRegisterUserMail();
        if (registerUserName.matches("\\w+") && registerUserName.length() >= 4) {
            if (registerUserMail.matches("\\w+@\\w+\\.\\w+")) {
                if (registerUserPsw.matches("\\w+") && registerUserPsw.length() >= 6) {
                    if (registerUserConfirmPsw.equals(registerUserPsw)) {
                        return true;
                    } else {
                        mView.toastMsg("两次输入的密码不同，请检查");
                    }
                } else {
                    mView.toastMsg("用户密码不能包含特殊字符且长度必须大于6");
                }
            } else {
                mView.toastMsg("请输入正确的邮箱");
            }
        } else {
            mView.toastMsg("用户名不能包含特殊字符并且长度必须大于4");
        }
        return false;
    }

    private boolean compareRegisterInfo() {
        UserAccountDao userAccountDao = mModel.getUserInfoFromDatabase(mView.getRegisterUserName());
        if (userAccountDao == null) {
            return true;
        } else {
            mView.toastMsg("该用户名已存在！");
        }
        return false;
    }

    //比对数据库和用户输入的信息
    private boolean compareUserInputInfo() {
        UserAccountDao userAccountDao = mModel.getUserInfoFromDatabase(mView.getUserName());
        if (userAccountDao != null) {
            if (userAccountDao.getUserPsw().equals(mView.getUserPsw())) {
                return true;
            } else {
                mView.toastMsg("密码错误！");
            }
        } else {
            mView.toastMsg("该账户不存在！");
        }
        return false;
    }

    //检查用户输入的用户名是否合法
    private boolean checkUserInputInfo() {
        String userName = mView.getUserName();
        String userPsw = mView.getUserPsw();
        if (userName.matches("\\w+") && userName.length() >= 4) {
            if (userPsw.matches("\\w+") && userPsw.length() >= 6) {
                return true;
            } else {
                mView.toastMsg("密码输入不合法！");
            }
        } else {
            mView.toastMsg("用户名输入不合法！");
        }
        return false;
    }
}

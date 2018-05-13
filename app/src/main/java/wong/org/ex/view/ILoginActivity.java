package wong.org.ex.view;

import android.content.Context;

public interface ILoginActivity {
    String getUserName();

    String getUserPsw();

    String getRegisterUserName();

    String getRegisterUserPsw();

    String getRegisterUserConfirmPsw();

    String getRegisterUserMail();

    boolean getLoginState();

    void toastMsg(String msg);

    void setUserName(String userName);

    void setUserPsw(String userPsw);

    void setLoginState(boolean state);

    Context getContext();
}

package wong.org.ex.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import wong.org.ex.R;
import wong.org.ex.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginActivity {

    private EditText mLoginEdtUserName;
    private EditText mLoginEdtUserPsw;
    private CheckBox mLoginCbSavePsw;
    private TextView mLoginTvRegister;
    private Button mLoginBtnLogin;
    private Button mLoginBtnQuit;
    private LoginPresenter mLoginPresenter;
    private View mRegisterView;
    private EditText mRegisterEdtUserName;
    private EditText mRegisterEdtUserPsw1;
    private EditText mRegisterEdtUserPsw2;
    private EditText mRegisterEdtUserMail;
    private Button mRegisterBtnCancel;
    private Button mRegisterBtnCommit;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mRegisterView = getLayoutInflater().inflate(R.layout.dialog_login_register, null);
        init();
    }

    private void init() {
        findWidgets();
        setListener();
        mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.setView(mRegisterView);
        mLoginPresenter = new LoginPresenter(this);
        mLoginPresenter.loadLoginState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:
                mLoginPresenter.userLogin();
                finish();
                break;
            case R.id.login_btn_quit:
                finish();
                break;
            case R.id.login_tv_register:
                mAlertDialog.show();
                break;
            case R.id.register_btn_cancel:
                mAlertDialog.cancel();
                break;
            case R.id.register_btn_commit:
                if (mLoginPresenter.userRegister()) {
                    toastMsg("注册成功");
                    mAlertDialog.dismiss();
                }
                break;
        }
    }

    private void findWidgets() {
        mLoginEdtUserName = findViewById(R.id.login_tv_user_name);
        mLoginEdtUserPsw = findViewById(R.id.login_tv_user_psw);
        mLoginCbSavePsw = findViewById(R.id.login_cb_save_psw);
        mLoginTvRegister = findViewById(R.id.login_tv_register);
        mLoginBtnLogin = findViewById(R.id.login_btn_login);
        mLoginBtnQuit = findViewById(R.id.login_btn_quit);
        //Dialog
        mRegisterEdtUserName = mRegisterView.findViewById(R.id.register_edt_user_name);
        mRegisterEdtUserPsw1 = mRegisterView.findViewById(R.id.register_edt_user_psw1);
        mRegisterEdtUserPsw2 = mRegisterView.findViewById(R.id.register_edt_user_psw2);
        mRegisterEdtUserMail = mRegisterView.findViewById(R.id.register_edt_user_mail);
        mRegisterBtnCancel = mRegisterView.findViewById(R.id.register_btn_cancel);
        mRegisterBtnCommit = mRegisterView.findViewById(R.id.register_btn_commit);
    }

    private void setListener() {
        mLoginBtnLogin.setOnClickListener(this);
        mLoginBtnQuit.setOnClickListener(this);
        mLoginTvRegister.setOnClickListener(this);
        mRegisterBtnCancel.setOnClickListener(this);
        mRegisterBtnCommit.setOnClickListener(this);
    }

    @Override
    public String getUserName() {
        return mLoginEdtUserName.getText().toString();
    }

    @Override
    public String getUserPsw() {
        return mLoginEdtUserPsw.getText().toString();
    }

    @Override
    public String getRegisterUserName() {
        return mRegisterEdtUserName.getText().toString();
    }

    @Override
    public String getRegisterUserPsw() {
        return mRegisterEdtUserPsw1.getText().toString();
    }

    @Override
    public String getRegisterUserConfirmPsw() {
        return mRegisterEdtUserPsw2.getText().toString();
    }

    @Override
    public String getRegisterUserMail() {
        return mRegisterEdtUserMail.getText().toString();
    }

    @Override
    public boolean getLoginState() {
        return mLoginCbSavePsw.isChecked();
    }

    @Override
    public void setUserName(String userName) {
        mLoginEdtUserName.setText(userName);
    }

    @Override
    public void setUserPsw(String userPsw) {
        mLoginEdtUserPsw.setText(userPsw);
    }

    @Override
    public void setLoginState(boolean state) {
        mLoginCbSavePsw.setChecked(state);
    }

    @Override
    public Context getContext() {
        return LoginActivity.this;
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
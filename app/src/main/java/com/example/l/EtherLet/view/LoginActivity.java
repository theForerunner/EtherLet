package com.example.l.EtherLet.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.EtherLet.GlobalData;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.dto.User;
import com.example.l.EtherLet.presenter.LoginPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginViewInterface {
    @BindView(R.id.login_constraint_layout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.id_text)
    EditText userAccount;
    @BindView(R.id.password_text)
    EditText userPassword;
    @BindView(R.id.button_login)
    MaterialButton loginButton;
    @BindView(R.id.button_register)
    MaterialButton RegisterButton;
    @BindView(R.id.button_history)
    TextView userHistory;
    @BindView(R.id.check_savePassword)
    CheckBox checkSavePassword;
    ListPopupWindow historyWindow;
    private ArrayList historyList;
    private boolean click=false;
    private LoginPresenter loginPresenter;
    private static LoginActivity instance;
    GlobalData globalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);

        globalData = (GlobalData) getApplication();
        initViews();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews(){
        instance=this;
        loginPresenter=new LoginPresenter(this);
        historyWindow=new ListPopupWindow(this);

        loginButton.setOnClickListener(v -> {
            String account = getAccount();
            String password = getPassword();
            if (!account.equals("") && !password.equals("")) {
                Map<String, Object> map = new HashMap<>();
                map.put("userAccount", getAccount());
                map.put("userPassword", getPassword());
                loginPresenter.Login(this, map);
                loginPresenter.clear();
            } else {
                Toast.makeText(LoginActivity.this, R.string.info_incomplete, Toast.LENGTH_SHORT).show();
            }


        });
        RegisterButton.setOnClickListener(v -> {
            loginPresenter.clear();
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
            this.finish();
        });
        userHistory.setOnClickListener(v -> {
            if(!click){
                historyList = loginPresenter.getHistoryList();
                historyWindow.setAdapter(new ArrayAdapter(LoginActivity.this, R.layout.history_list, historyList));
                historyWindow.setDropDownGravity(Gravity.END);
                historyWindow.setAnchorView(userAccount);
                historyWindow.show();
                click = true;
            } else click = false;

        });
        historyWindow.setOnItemClickListener((parent, view, position, id) -> {
            userAccount.setText(historyList.get(position).toString());
            /*String password=getPassword(historyList.get(position));
            userPassword.setText(password);
            if(!password.equals(""))
            {
                checkBox.setChecked(true);
            }
            else{
                checkBox.setChecked(false);
            }*/
            historyWindow.dismiss();
            click=false;
        });

        constraintLayout.setOnTouchListener((v, event) -> {
            constraintLayout.setFocusable(true);
            constraintLayout.setFocusableInTouchMode(true);
            constraintLayout.requestFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(userAccount.getWindowToken(),0);
            imm.hideSoftInputFromWindow(userPassword.getWindowToken(),0);
            return false;
        });
    }

    @Override
    public String getAccount(){
        return userAccount.getText().toString();
    }

    @Override
    public String getPassword() {
        return userPassword.getText().toString();
    }

    @Override
    public void clearUserId() {
        userAccount.setText("");
    }

    @Override
    public void clearPassword() {
        userPassword.setText("");
    }


    @Override
    public void enterMainActivity(User user) {//转到主界面

        //Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        //startActivity(intent);
        globalData.setPrimaryUser(user);
        Toast.makeText(this,R.string.login_success, Toast.LENGTH_SHORT).show();
        instance.finish();
    }

    @Override
    public void showFail() {
        Toast.makeText(this,R.string.login_fail, Toast.LENGTH_SHORT).show();
    }

}

package com.example.l.EtherLet.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.User;
import com.example.l.EtherLet.presenter.LoginPresenter;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements LoginViewInterface {

    private ConstraintLayout constraintLayout;
    private EditText userId;
    private EditText userPassword;
    private MaterialButton loginButton;
    private MaterialButton RegisterButton;
    private TextView userHistory;
    private ListPopupWindow historyWindow;
    private CheckBox checkSavePassword;
    private ArrayList historyList;
    private boolean click=false;
    private LoginPresenter loginPresenter;
    private static LoginActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initViews();
    }

    private void initViews(){
        instance=this;
        loginPresenter=new LoginPresenter(this);
        constraintLayout=findViewById(R.id.login_constraint_layout);
        userId=findViewById(R.id.id_text);
        userPassword=findViewById(R.id.password_text);
        loginButton=findViewById(R.id.button_login);
        RegisterButton=findViewById(R.id.button_register);
        userHistory=findViewById(R.id.button_history);
        checkSavePassword=findViewById(R.id.check_savePassword);
        historyWindow=new ListPopupWindow(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=userId.getText().toString();
                String password=userPassword.getText().toString();
                if(!id.equals("")&&!password.equals(""))
                {
                    loginPresenter.Login();
                    loginPresenter.clear();

                }
                else
                {
                    Toast.makeText(LoginActivity.this, R.string.info_incomplete, Toast.LENGTH_SHORT).show();
                }


            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginPresenter.clear();
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);

            }
        });
        userHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!click){
                    historyList=loginPresenter.getHistoryList();
                    historyWindow.setAdapter(new ArrayAdapter(LoginActivity.this,R.layout.history_list,historyList));
                    historyWindow.setDropDownGravity(Gravity.END);
                    historyWindow.setAnchorView(userId);
                    historyWindow.show();
                    click=true;
                }
                else click=false;

            }
        });
        historyWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userId.setText(historyList.get(position).toString());
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
            }
        });

        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                constraintLayout.setFocusable(true);
                constraintLayout.setFocusableInTouchMode(true);
                constraintLayout.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(userId.getWindowToken(),0);
                imm.hideSoftInputFromWindow(userPassword.getWindowToken(),0);
                return false;
            }
        });
    }

    @Override
    public String getUserId(){
        return userId.getText().toString();
    }

    @Override
    public String getPassword() {
        return userPassword.getText().toString();
    }

    @Override
    public void clearUserId() {
        userId.setText("");
    }

    @Override
    public void clearPassword() {
        userPassword.setText("");
    }


    @Override
    public void enterMainActivity(User user) {//转到主界面

        //Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        //startActivity(intent);
        instance.finish();
        Toast.makeText(this,R.string.login_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFail() {
        Toast.makeText(this,R.string.login_fail, Toast.LENGTH_SHORT).show();
    }

}

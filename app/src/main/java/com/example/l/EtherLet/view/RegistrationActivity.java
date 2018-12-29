package com.example.l.EtherLet.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.l.EtherLet.GlobalData;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.dto.User;
import com.example.l.EtherLet.presenter.RegistrationPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements RegistrationViewInterface {
    @BindView(R.id.registration_constraint_layout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.image_registration)
    ImageView imageRegistration;
    @BindView(R.id.registration_id)
    MaterialEditText editID;
    @BindView(R.id.registration_password)
    MaterialEditText editPassword;
    @BindView(R.id.registration_password_repeat)
    MaterialEditText editPasswordRepeat;
    @BindView(R.id.button_sign)
    MaterialButton signButton;

    private static RegistrationActivity instance;
    private RegistrationPresenter presenter;
    GlobalData globalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        ButterKnife.bind(this);

        globalData = (GlobalData) getApplication();
        initViews();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews(){
        presenter=new RegistrationPresenter(this);
        instance=this;

        signButton.setOnClickListener(v -> {
            String account = getAccount();
            String password = getPassword();
            String passwordRepeat = getPasswordRepeat();

            if(!account.equals("")&&!password.equals("")&&!passwordRepeat.equals("")){
                if(password.equals(passwordRepeat)){
                    Map<String, Object> map = new HashMap<>();
                    map.put("userAccount", getAccount());
                    map.put("userPassword", getPassword());
                    map.put("userUsername", getAccount());
                    presenter.registration(this, map);
                } else{
                    editPasswordRepeat.setError(getString(R.string.password_not_same));
                }
            } else {
                if(account.equals("")){
                    editID.setError(getString(R.string.no_empty));
                }
                if(password.equals("")){
                    editPassword.setError(getString(R.string.no_empty));
                }
                if(passwordRepeat.equals("")){
                    editPasswordRepeat.setError(getString(R.string.no_empty));
                }
            }

        });

        constraintLayout.setOnTouchListener((v, event) -> {
            constraintLayout.setFocusable(true);
            constraintLayout.setFocusableInTouchMode(true);
            constraintLayout.requestFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editID.getWindowToken(),0);
            imm.hideSoftInputFromWindow(editPassword.getWindowToken(),0);
            imm.hideSoftInputFromWindow(editPasswordRepeat.getWindowToken(),0);
            return false;
        });

    }

    @Override
    public String getAccount() {
        return editID.getText().toString();
    }

    @Override
    public String getPassword() {
        return editPassword.getText().toString();
    }

    private String getPasswordRepeat() {
        return editPasswordRepeat.getText().toString();
    }

    @Override
    public void registrationSuccess(User user) {
        Toast.makeText(this,R.string.registration_success, Toast.LENGTH_SHORT).show();
        globalData.setPrimaryUser(user);
        instance.finish();
    }

    @Override
    public void registrationFail() {
        Toast.makeText(this,R.string.registration_fail, Toast.LENGTH_SHORT).show();
        editID.setText("");
        editPassword.setText("");
        editPasswordRepeat.setText("");
    }
}

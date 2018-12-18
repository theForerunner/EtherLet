package com.example.l.EtherLet.view;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.l.EtherLet.R;
import com.example.l.EtherLet.presenter.RegistrationPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegistrationActivity extends AppCompatActivity implements RegistrationViewInterface {

    private ConstraintLayout constraintLayout;
    private ImageView imageRegistration;
    private MaterialEditText editID;
    private MaterialEditText editPassword;
    private MaterialEditText editPasswordRepeat;
    private MaterialButton signButton;
    private static RegistrationActivity instance;

    private RegistrationPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        initViews();
    }

    private void initViews(){
        presenter=new RegistrationPresenter(this);
        instance=this;
        constraintLayout=findViewById(R.id.registration_constraint_layout);
        imageRegistration=findViewById(R.id.image_registration);
        editID=findViewById(R.id.registration_id);
        editPassword=findViewById(R.id.registration_password);
        editPasswordRepeat=findViewById(R.id.registration_password_repeat);
        signButton=findViewById(R.id.button_sign);


        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=editID.getText().toString();
                String password=editPassword.getText().toString();
                String passwordRepeat=editPasswordRepeat.getText().toString();
                if(!id.equals("")&&!password.equals("")&&!passwordRepeat.equals("")){
                    if(password.equals(passwordRepeat)){

                        presenter.registration();
                    }
                    else{
                        editPasswordRepeat.setError(getString(R.string.password_not_same));
                    }
                }
                else {
                    if(id.equals("")){
                        editID.setError(getString(R.string.no_empty));
                    }
                    if(password.equals("")){
                        editPassword.setError(getString(R.string.no_empty));
                    }
                    if(passwordRepeat.equals("")){
                        editPasswordRepeat.setError(getString(R.string.no_empty));
                    }
                }

            }
        });
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                constraintLayout.setFocusable(true);
                constraintLayout.setFocusableInTouchMode(true);
                constraintLayout.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editID.getWindowToken(),0);
                imm.hideSoftInputFromWindow(editPassword.getWindowToken(),0);
                imm.hideSoftInputFromWindow(editPasswordRepeat.getWindowToken(),0);
                return false;
            }
        });

    }

    @Override
    public String getId() {
        return editID.getText().toString();
    }

    @Override
    public String getPassword() {
        return editPassword.getText().toString();
    }

    @Override
    public void registrationSuccess() {
        Toast.makeText(this,R.string.registration_success, Toast.LENGTH_SHORT).show();
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

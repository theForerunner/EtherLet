package com.example.l.EtherLet.view;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.EtherLet.DBHelper;
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
    RelativeLayout relativeLayout;
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
    @BindView(R.id.delete_user)
    ImageView deleteUserButton;
    ListPopupWindow historyWindow;
    private ArrayList<String> historyList;
    private boolean click=false;
    private LoginPresenter loginPresenter;
    private LoginActivity instance;
    GlobalData globalData;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this,"EtherLet.db",null,1);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        globalData = (GlobalData) getApplication();
        initViews();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews(){//绑定控件
        instance=this;
        loginPresenter=new LoginPresenter(this);
        historyWindow=new ListPopupWindow(this);
        deleteUserButton.setVisibility(View.INVISIBLE);
        userAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(getAccount().equals("")){
                    deleteUserButton.setVisibility(View.INVISIBLE);
                }
                else {
                    deleteUserButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginButton.setOnClickListener(v -> {//登陆
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
        RegisterButton.setOnClickListener(v -> {//注册
            loginPresenter.clear();
            checkSavePassword.setChecked(false);
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
            this.finish();
        });
        userHistory.setOnClickListener(v -> {//点开历史用户列表
            if(!click){
                historyList=initHistory();
                historyWindow.setAdapter(new ArrayAdapter(LoginActivity.this, R.layout.history_list, historyList));
                historyWindow.setDropDownGravity(Gravity.END);
                historyWindow.setAnchorView(userAccount);
                historyWindow.show();
                click = true;
            } else click = false;

        });
        historyWindow.setOnItemClickListener((parent, view, position, id) -> {//选择用户
            userAccount.setText(historyList.get(position));
            String password=getPassword(historyList.get(position));
            userPassword.setText(password);
            if(!password.equals(""))
            {
                checkSavePassword.setChecked(true);
            }
            else{
                checkSavePassword.setChecked(false);
            }
            historyWindow.dismiss();
            click=false;
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {//删除历史用户
            @Override
            public void onClick(View v) {
                String account=getAccount();
                if(isInHistory(getAccount())){
                    deleteUserHistory(account);
                }
                loginPresenter.clear();
                checkSavePassword.setChecked(false);
            }
        });

        relativeLayout.setOnTouchListener((v, event) -> {//edittext点击外部失焦
            relativeLayout.setFocusable(true);
            relativeLayout.setFocusableInTouchMode(true);
            relativeLayout.requestFocus();
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

        globalData.setPrimaryUser(user);
        if(checkSavePassword.isChecked()){
            insertHistoryWithPassword(user.getUserAccount(),user.getUserPassword());
        }
        else if(!checkSavePassword.isChecked()){
            insertHistoryWithoutPassword(user.getUserAccount());
        }
        Toast.makeText(this,R.string.login_success, Toast.LENGTH_SHORT).show();
        instance.finish();
    }

    @Override
    public void showFail() {
        Toast.makeText(this,R.string.login_fail, Toast.LENGTH_SHORT).show();
    }

    public boolean isInHistory(String id){//sqlite判断是否在库内
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from userHistory where userAccount=?";
        Cursor cursor = db.rawQuery(sql, new String[] {id});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
    public ArrayList<String> initHistory(){//sqlite获取历史用户表

        ArrayList<String> list=new ArrayList();
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        Cursor cursor=db.query("userHistory",null,null,null,null,null,null);
        while(cursor.moveToNext())
        {
            String id=cursor.getString(cursor.getColumnIndex("userAccount"));
            list.add(id);
        }
        cursor.close();
        return list;
    }
    public String getPassword(String id){//sqlite获取密码
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String sql="Select password from userHistory where userAccount=?";
        Cursor cursor=db.rawQuery(sql,new String[]{id});
        if(cursor.getCount()==1)
        {
            while(cursor.moveToNext())
            {
                String string=cursor.getString(cursor.getColumnIndex("password"));
                cursor.close();
                return string;
            }
            return "";

        }
        else return "";

    }
    public void insertHistoryWithPassword(String id,String password){//sqlite插入用户带密码
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("userAccount",id);
        values.put("password",password);
        if(!isInHistory(id)){
            db.insert("userHistory",null,values);
        }
        else{
            db.update("userHistory",values,"userAccount=?",new String[]{id});
        }
        Toast.makeText(LoginActivity.this, "记录成功", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void deleteUserHistory(String id){//sqlite删除历史用户
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete("userHistory","userAccount=?",new String[]{id});
        Toast.makeText(LoginActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void insertHistoryWithoutPassword(String id){//sqlite插入用户不带密码
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("userAccount",id);
        values.put("password","");
        if(!isInHistory(id)){
            db.insert("userHistory",null,values);
        }
        else{
            db.update("userHistory",values,"userAccount=?",new String[]{id});
        }
        Toast.makeText(LoginActivity.this, "记录成功", Toast.LENGTH_SHORT).show();
        db.close();
    }

}

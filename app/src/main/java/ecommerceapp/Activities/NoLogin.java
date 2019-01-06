package ecommerceapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ecommerceapp.authentications.LoginValidation;
import model.quiz.glarimy.com.ecommerceapp.R;

public class NoLogin extends AppCompatActivity {

    LoginValidation loginValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_login);
        loginValidation=new LoginValidation(this);
    }

    public void loginIntent(View v)
    {
            Intent login=new Intent(this,UserLogin.class);
            startActivity(login);
    }

    public void registerIntent(View v)
    {
        Intent register=new Intent(this,UserRegisteration.class);
        startActivity(register);
    }
}

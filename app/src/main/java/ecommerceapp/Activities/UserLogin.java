package ecommerceapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ecommerceapp.authentications.LoginValidation;
import model.quiz.glarimy.com.ecommerceapp.R;

public class UserLogin extends AppCompatActivity {

    EditText etName, etPassword;
    String userName, password;
    LoginValidation loginValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        loginValidation=new LoginValidation(this);
        etName = findViewById(R.id.loginemail);
        etPassword = findViewById(R.id.loginpassword);


    }

    public void register(View v)
    {
        Intent registerIntent=new Intent(this,UserRegisteration.class);
        startActivity(registerIntent);
    }


    public void login(View v)
    {

        userName = etName.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (userName.equals("") || password.equals("")) {
            Toast.makeText(this, "Enter details", Toast.LENGTH_LONG).show();
        }
        else {

            loginValidation.setUserName(userName);
            loginValidation.setPassword(password);

            if (loginValidation.validateIdentity())
            {

                Intent accountIntent = new Intent(this, YourAccount.class);
                startActivity(accountIntent);
                finish();
                Toast.makeText(this, "user login", Toast.LENGTH_LONG).show();
            }

        }
    }
}

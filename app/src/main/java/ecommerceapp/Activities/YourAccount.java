package ecommerceapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import ecommerceapp.ProductData.UserData;
import ecommerceapp.authentications.LoginValidation;
import model.quiz.glarimy.com.ecommerceapp.R;

public class YourAccount extends AppCompatActivity
{
    TextView nameView,emailView,phoneView;
    CircleImageView accountImageView;
    UserData userData;
    LoginValidation loginValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        loginValidation=new LoginValidation(this);

        nameView=findViewById(R.id.txtname);
        emailView=findViewById(R.id.txtemail);
        phoneView=findViewById(R.id.txtphone);

        userData=new UserData();

        if(loginValidation.getLoginStatus()==1)
        {
            //accountImageView.setImageURI(Uri.parse(userData.getImageUri()));
            nameView.setText("Name :"+userData.getName());
            emailView.setText("Email :"+userData.getEmail());
            phoneView.setText("Phone :"+userData.getPhone());
        }
    }

    public void logOut(View v)
    {
        loginValidation.logOut();
        finish();
    }

}

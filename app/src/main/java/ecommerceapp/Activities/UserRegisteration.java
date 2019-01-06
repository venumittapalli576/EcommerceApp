package ecommerceapp.Activities;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ecommerceapp.databases.EcommerceDatabase;
import model.quiz.glarimy.com.ecommerceapp.R;

public class UserRegisteration extends AppCompatActivity
{
    int ids[] = {R.id.txtname,R.id.txtemail,R.id.txtphone,R.id.txtpass, R.id.txtrepass};
    EditText et[] = new EditText[ids.length];
    String reg_details[] = new String[ids.length];
    int i;


    ImageView userUmage;
    private static final int SELECT_PICTURE = 100;
    String imageUri="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        for (i = 0; i < ids.length; i++) {
            et[i] =findViewById(ids[i]);
        }
    }



    public void registerUser(View v) throws InterruptedException
    {
        for (i = 0; i < ids.length; i++)
        {
            reg_details[i] = et[i].getText().toString().trim();
            if (reg_details[i].trim().isEmpty())
            {
                et[i].setError("empty field");
                et[i].requestFocus();
                break;
            }
        }

        if (i == et.length)
        {
            int status=0;
            if (reg_details[3].equals(reg_details[4]))
            {
                EcommerceDatabase ecommerceDatabase = new EcommerceDatabase(this);
                SQLiteDatabase sqLiteDatabase = ecommerceDatabase.getWritableDatabase();

                String detailstable = "insert into registerUsers values('" + reg_details[0] + "','" + imageUri + "','" + reg_details[1] + "','" + reg_details[2] + "','"+reg_details[3]+"')";

                try {
                    sqLiteDatabase.execSQL(detailstable);
                }catch (Exception e)
                {
                    status=1;
                }
                if(status==0)
                {
                    Toast.makeText(this, "Account Registered", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    et[1].setError("Email already in use");
                    et[1].requestFocus();
                }

            }
            else
            {
                et[4].setError("Password not match");
                et[4].requestFocus();
            }
        }
    }
}
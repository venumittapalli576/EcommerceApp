package ecommerceapp.authentications;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import ecommerceapp.ProductData.UserData;
import ecommerceapp.databases.EcommerceDatabase;

public class LoginValidation
{
    String userName, password;
    Context context;

    static
    int loginStatus;
    EcommerceDatabase database;

    SharedPreferences sharedPreferences;
    UserData userData;

    public LoginValidation(Context context) {
        sharedPreferences=context.getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
        this.context = context;

        userData= new UserData();
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Boolean validateIdentity()
    {
        database = new EcommerceDatabase(context);
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        String qry = "select name,phone,password from registerUsers where email ='" + userName + "'";
        Cursor c = sqLiteDatabase.rawQuery(qry, null);

        boolean res = c.moveToFirst();

        if (res)
        {
            String password1 = c.getString(2);
            if (password.equals(password1))
            {
                sharedPreferences.edit().putInt("isLogin", 1).putString("loginUser",userName).apply();
                loginStatus=1;
                userData.setEmail(userName);
                userData.setPhone(c.getString(1));
                userData.setName(c.getString(0));
                return true;
            }
            else {
                sharedPreferences.edit().putInt("isLogin", 0).apply();
                loginStatus=0;
                Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else{
            Toast.makeText(context, "No account present", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void logOut()
    {
        sharedPreferences.edit().putInt("isLogin", 0).apply();
        sharedPreferences.edit().putString("loginUser","").apply();
        loginStatus=0;
    }
}

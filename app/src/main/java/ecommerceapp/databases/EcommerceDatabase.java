package ecommerceapp.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EcommerceDatabase extends SQLiteOpenHelper
{
    public EcommerceDatabase(Context context) {
        super(context, "EcommerceDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String registerUsers = "create table registerUsers(name text,uri text,email text primary key,phone text,password text)";

        String cartProducts="create table cartProducts(imageId long primary key,description text,cost int,brand text,rating float,name text)";

        String orderProducts="create table orderProducts(imageId long,description text,cost int,brand text,rating float,name text)";

        db.execSQL(registerUsers);
        db.execSQL(orderProducts);
        db.execSQL(cartProducts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

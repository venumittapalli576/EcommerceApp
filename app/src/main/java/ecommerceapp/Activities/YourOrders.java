package ecommerceapp.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import ecommerceapp.ProductData.UserData;
import ecommerceapp.databases.EcommerceDatabase;
import model.quiz.glarimy.com.ecommerceapp.R;

public class YourOrders extends AppCompatActivity {

    ProductsAdapter products;
    EcommerceDatabase ecommerceDatabase;
    SQLiteDatabase sqLiteDatabase;


    long imageId[];
    int cost[];
    String description[], brand[];
    float rating[];

    int listItemLayout;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        listItemLayout = R.layout.order_account_sub_item_view;
        userData = new UserData();

        ecommerceDatabase = new EcommerceDatabase(this);
        sqLiteDatabase = ecommerceDatabase.getReadableDatabase();

        String qry = "select * from orderProducts where name ='" + userData.getName() + "'";
        Cursor c = sqLiteDatabase.rawQuery(qry, null);

        if (c.moveToFirst()) {
            imageId = new long[c.getCount()];
            cost = new int[c.getCount()];
            description = new String[c.getCount()];
            brand = new String[c.getCount()];
            rating = new float[c.getCount()];

            int i = 0;
            do {
                imageId[i] = c.getLong(0);
                description[i] = c.getString(1);
                cost[i] = c.getInt(2);
                brand[i] = c.getString(3);
                rating[i] = c.getFloat(4);
                i++;
            } while (c.moveToNext());

            ListView order_list = findViewById(R.id.orderList);
            products = new ProductsAdapter(this, description, imageId, cost, rating, brand, listItemLayout);
            order_list.setAdapter(products);
        } else {

            Intent noItemIntent = new Intent(this, NoItems.class);
            noItemIntent.putExtra("status", "ORDERS");
            startActivity(noItemIntent);
            finish();
            // Toast.makeText(this, "no orders", Toast.LENGTH_LONG).show();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_orders) {

            sqLiteDatabase.delete("orderProducts",null,null);
            Toast.makeText(this,"Orders cleared",Toast.LENGTH_LONG).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

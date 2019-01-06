package ecommerceapp.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ecommerceapp.ProductData.SingleProductData;
import ecommerceapp.ProductData.UserData;
import ecommerceapp.databases.EcommerceDatabase;
import model.quiz.glarimy.com.ecommerceapp.R;

public class YourCart extends AppCompatActivity {
    ProductsAdapter products;
    EcommerceDatabase ecommerceDatabase;
    SQLiteDatabase sqLiteDatabase;
    ListView cart_list;


    long imageId[] = {};
    int cost[] = {};
    String description[] = {}, brand[] = {};
    float rating[] = {};

    int listItemLayout;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        listItemLayout = R.layout.cart_account_sub_item_view;
        userData = new UserData();

        ecommerceDatabase = new EcommerceDatabase(this);
        sqLiteDatabase = ecommerceDatabase.getReadableDatabase();

        String qry = "select * from cartProducts where name ='" + userData.getName() + "'";
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

            cart_list = findViewById(R.id.cartList);
            products = new ProductsAdapter(this, description, imageId, cost, rating, brand, listItemLayout);
            cart_list.setAdapter(products);

            cart_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SingleProductData singleProductData = new SingleProductData();

                    singleProductData.setImageId(imageId[position]);
                    singleProductData.setBrand(brand[position]);
                    singleProductData.setRating(rating[position]);
                    singleProductData.setCost(cost[position]);

                    TextView txtDesc = view.findViewById(R.id.selectedProductDescription);
                    singleProductData.setDescription(txtDesc.getText().toString());
                    SingleProduct singleProduct = new SingleProduct();
                    singleProduct.orderDialog(YourCart.this, singleProductData, "cartProducts");
                    //parent.removeViewInLayout(view);
                }
            });

        } else {
            Intent noItemIntent = new Intent(this, NoItems.class);
            noItemIntent.putExtra("status", "CART");
            startActivity(noItemIntent);
            finish();
            // Toast.makeText(this, "no orders", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_cart) {

            sqLiteDatabase.delete("cartProducts",null,null);
            Toast.makeText(this,"Cart cleared",Toast.LENGTH_LONG).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

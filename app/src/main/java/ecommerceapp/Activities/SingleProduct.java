package ecommerceapp.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ecommerceapp.ProductData.SingleProductData;
import ecommerceapp.ProductData.UserData;
import ecommerceapp.authentications.LoginValidation;
import ecommerceapp.databases.EcommerceDatabase;
import model.quiz.glarimy.com.ecommerceapp.R;

public class SingleProduct extends AppCompatActivity {
    ImageView img;
    TextView cost, desc;

    SingleProductData singleProductData;
    LoginValidation loginValidation;

    EcommerceDatabase ecommerceDatabase;
    SQLiteDatabase sqLiteDatabase;

    long imageId;
    int costT;
    String description, brand;
    float rating;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        loginValidation = new LoginValidation(this);
        ecommerceDatabase = new EcommerceDatabase(this);
        sqLiteDatabase = ecommerceDatabase.getWritableDatabase();

        userData = new UserData();

        img = findViewById(R.id.selectedProductImage);
        cost = findViewById(R.id.selectedProductCost);
        desc = findViewById(R.id.selectedProductDescription);

        Intent i = getIntent();
        singleProductData = (SingleProductData) i.getSerializableExtra("singleProductData");

        imageId = singleProductData.getImageId();
        costT = singleProductData.getCost();
        description = singleProductData.getDescription();
        brand = singleProductData.getBrand();
        rating = singleProductData.getRating();

        desc.setText(description);
        cost.setText("cost:" + costT);
        img.setImageResource((int) imageId);
    }

    public void buyNow(View v) {
        orderDialog(this, singleProductData, "orederProducts");
    }

    @SuppressLint("CutPasteId")
    public void orderDialog(final Context context, SingleProductData singleproductdata, String tableName) {

        this.singleProductData = singleproductdata;
        if (tableName.equals("cartProducts")) {
            imageId = singleproductdata.getImageId();
            costT = singleProductData.getCost();
            description = singleProductData.getDescription();
            brand = singleProductData.getBrand();
            rating = singleProductData.getRating();
        }

        ecommerceDatabase = new EcommerceDatabase(context);
        sqLiteDatabase = ecommerceDatabase.getWritableDatabase();

        loginValidation = new LoginValidation(context);
        userData = new UserData();

        final int cost = singleproductdata.getCost();

        if (loginValidation.getLoginStatus() == 1) {
            final int[] totalQuantity = {1};
            ImageView productImage;
            final TextView noOfProducts, productsCost;
            final AlertDialog myDialog;

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(context);
            LayoutInflater mlayout = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View mview = mlayout.inflate(R.layout.order_dialog, null);

            noOfProducts = mview.findViewById(R.id.txt_quantity);
            productsCost = mview.findViewById(R.id.txt_cost);
            productsCost.setText("Amount:" + cost + "/-");
            productImage = mview.findViewById(R.id.orderingProductImage);

            productImage.setImageResource((int) singleproductdata.getImageId());

            myBuilder.setView(mview);
            myDialog = myBuilder.create();
            myDialog.show();


            mview.findViewById(R.id.btn_plus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (totalQuantity[0] == 5) {
                        Toast.makeText(context, "Maximum Limit=5", Toast.LENGTH_LONG).show();
                    } else {
                        totalQuantity[0] += 1;
                        productsCost.setText("Amount:" + totalQuantity[0] * cost + "/-");
                        noOfProducts.setText("" + totalQuantity[0]);
                    }
                }
            });


            mview.findViewById(R.id.btn_minus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (totalQuantity[0] == 1) {
                    } else {
                        totalQuantity[0] -= 1;
                        productsCost.setText("Amount:" + totalQuantity[0] * cost + "/-");
                        noOfProducts.setText("" + totalQuantity[0]);
                    }
                }
            });


            mview.findViewById(R.id.check_out).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginValidation = new LoginValidation(context);
                    String qry = "insert into orderProducts values('" + imageId + "','" + description + "','" + costT + "','" + brand + "','" + rating + "','" + userData.getName() + "')";
                    sqLiteDatabase.execSQL(qry);
                    Toast.makeText(context, "Order Placed", Toast.LENGTH_LONG).show();
                    myDialog.cancel();
                    String qryDel = "delete from cartProducts where imageId='" + imageId + "'";
                    sqLiteDatabase.execSQL(qryDel);

                }
            });

        } else {
            loginDialog();
        }

    }

    public void addToCart(View v) {
        int status = 0;
        if (loginValidation.getLoginStatus() == 1) {
            String qry = "insert into cartProducts values('" + imageId + "','" + description + "','" + costT + "','" + brand + "','" + rating + "','" + userData.getName() + "')";

            try {
                sqLiteDatabase.execSQL(qry);
            } catch (Exception e) {
                status = 1;
            }

            if (status == 0) {
                Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(this).setTitle("Sorry!").setMessage("This product is already in your cart").setCancelable(false)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        } else {
            loginDialog();
        }

    }


    public void loginDialog() {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(SingleProduct.this);
        View mview = getLayoutInflater().inflate(R.layout.login_dialog, null);
        myBuilder.setView(mview);
        final AlertDialog myDialog = myBuilder.create();
        myDialog.show();
        final EditText et1 = mview.findViewById(R.id.email);
        final EditText et2 = mview.findViewById(R.id.pass);
        final Button loginButton = mview.findViewById(R.id.dialogLogin);
        final Button registerButton = mview.findViewById(R.id.dialogRegister);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = et1.getEditableText().toString().trim();
                String password = et2.getEditableText().toString().trim();
                if (!(userName.equals("")) && !(password.equals(""))) {
                    loginValidation.setUserName(userName);
                    loginValidation.setPassword(password);

                    if (loginValidation.validateIdentity()) {
                        myDialog.cancel();
                        Toast.makeText(SingleProduct.this, "Logged In", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(SingleProduct.this, "Please fill all fields", Toast.LENGTH_LONG).show();
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent regIntent = new Intent(SingleProduct.this, UserRegisteration.class);
                startActivity(regIntent);

            }
        });
    }
}

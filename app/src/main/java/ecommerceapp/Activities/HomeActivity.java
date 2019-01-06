package ecommerceapp.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import ecommerceapp.ProductData.SingleProductData;
import ecommerceapp.ProductData.UserData;
import ecommerceapp.authentications.LoginValidation;
import ecommerceapp.databases.EcommerceDatabase;
import model.quiz.glarimy.com.ecommerceapp.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int SELECT_PICTURE = 100;
    SharedPreferences sharedPreferences;

    String imgUri;

    String spinnerBrandNames[] = {"Search by productBrand category", "Redmi", "Samsung", "Nokia"};

    //mixed brands
    String Brand[] = {"samsung", "Nokia", "samsung", "redmi","Nokia"};
    float Rating[] = {(float) 2.5, 3, (float) 4.8, 5, (float) 2.1};
    long Ids[] = {R.drawable.samsung_galaxy_j6_sm, R.drawable.nokia_2_ta_1011, R.drawable.samsung_guru_music_2, R.drawable.note4,R.drawable.nokia_1_n1_original};
    String Des[] = {"Samsung Galaxy J6 (Blue, 32 GB)  (3 GB RAM)",
            "Nokia Ta -1010/ Nokia 105  (Blue)",
            "Samsung Guru Music 2  (Blue)",
            "Redmi Note 4 (Dark Grey, 64 GB)  (4 GB RAM)",
            "Nokia 1 (Dark Blue, 8 GB)  (1 GB RAM)"};
    int Cost[] = {13990, 999, 1625, 10999,4779};




    //samsung productBrand
    String samsungBrand[] = {"samsung", "samsung", "samsung", "samsung"};
    float samsungRating[] = {(float) 2.5, 3, (float) 4.8, 5, (float) 2.1};
    long samsungiIds[] = {R.drawable.samsung_galaxy_j6_sm, R.drawable.samsung_on_max, R.drawable.samsung_guru_music_2, R.drawable.samsung_j7_pro_sm};
    String samsungDes[] = {"Samsung Galaxy J6 (Blue, 32 GB)  (3 GB RAM)",
            "Samsung Galaxy On Max (Black, 32 GB)  (4 GB RAM)",
            "Samsung Guru Music 2  (Blue)",
            "Samsung Galaxy J7 Pro (Gold, 64 GB)  (3 GB RAM)"};
    int samsungCost[] = {13990, 13900, 1625, 16900};

    //redmi productBrand
    String redmiBrand[] = {"redmi", "redmi", "redmi", "redmi", "redmi"};
    float redMiRating[] = {(float) 2.5, 3, (float) 4.8, 5, (float) 2.1};
    long redMiIds[] = {R.drawable.note4, R.drawable.redmi_note_5pro, R.drawable.mi_redmi_y1, R.drawable.mi_redmi_y1_lite, R.drawable.ivoomi_i1s_new_edition};
    String redMiDes[] = {"Redmi Note 4 (Dark Grey, 64 GB)  (4 GB RAM)",
            "Redmi Note 5 Pro (Gold, 64 GB)  (4 GB RAM)",
            "Redmi Y1 (Gold, 32 GB)  (3 GB RAM)",
            "Redmi Y1 lite (Gold, 16 GB)  (2 GB RAM)",
            "iVooMi i1s (New Edition) (Classic Black, 32 GB)  (3 GB RAM)"};
    int redMiCost[] = {10999, 14999, 9875, 7998, 7499};

    //nokia brand
    String nokiaBrand[] = {"Nokia","Nokia","Nokia","Nokia","Nokia","Nokia"};
    float nokiaRating[] = {(float) 2.5, 3, (float) 4.8, 5, (float) 2.1, (float) 3.2};
    long nokiaIds[] = {R.drawable.nokia_2_ta_1011, R.drawable.nokia_1_n1_original, R.drawable.nokia_150, R.drawable.nokia_105, R.drawable.nokia_5_na, R.drawable.nokia_130_ta_1017};
    String nokiaDes[] = {"Nokia Ta -1010/ Nokia 105  (Blue)",
            "Nokia 1 (Dark Blue, 8 GB)  (1 GB RAM)",
            "Nokia 150  (Black)",
            "Nokia 2 (Pewter/ Black, 8 GB)  (1 GB RAM)",
            "Nokia 5 (Tempered Blue, 16 GB)  (3 GB RAM)",
            "Nokia 130  (Grey)"};
    int nokiaCost[] = {999, 4779, 1969, 6649, 12499, 1567};


    int listItemLayout = R.layout.list_view_item;


    TextView nameView, emailView, phoneView;

    NavigationView navigationView;
    TextView navText;

    ListView productList;
    ProductsAdapter products;
    SingleProductData singleProductData = new SingleProductData();

    LoginValidation loginValidation;
    UserData userData = new UserData();

    long selectedSingleImageId[];
    String selectedBrand;

    EcommerceDatabase database;
    SQLiteDatabase sqLiteDatabase;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new EcommerceDatabase(this);
        sqLiteDatabase = database.getWritableDatabase();


        navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navText=hView.findViewById(R.id.nav_text);


        loginValidation = new LoginValidation(this);
        sharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE);

        if (sharedPreferences.getInt("isLogin", 0) == 1) {

            String user = sharedPreferences.getString("loginUser", " ");
            String qry = "select name,uri,phone from registerUsers where email ='" + user + "'";
            Cursor c = sqLiteDatabase.rawQuery(qry, null);
            c.moveToFirst();

            userData.setEmail(user);
            userData.setPhone(c.getString(2));
            userData.setName(c.getString(0));
            navText.setText("HELLO! "+c.getString(0));
            loginValidation.setLoginStatus(1);
        } else {
            loginValidation.setLoginStatus(0);
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        productList = findViewById(R.id.productsList);

        Spinner spin = findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        selectedBrand = " ";
                        selectedSingleImageId = Ids;
                        productList = (ListView) findViewById(R.id.productsList);
                        products = new ProductsAdapter(HomeActivity.this, Des, Ids, Cost, Rating, Brand, listItemLayout);
                        productList.setAdapter(products);
                        break;
                    case 1:
                        selectedBrand = "Redmi";
                        selectedSingleImageId = redMiIds;
                        productList = (ListView) findViewById(R.id.productsList);
                        products = new ProductsAdapter(HomeActivity.this, redMiDes, redMiIds, redMiCost, redMiRating, redmiBrand, listItemLayout);
                        productList.setAdapter(products);
                        break;

                    case 2:
                        selectedBrand = "Samsung";
                        selectedSingleImageId = samsungiIds;
                        productList = findViewById(R.id.productsList);
                        ProductsAdapter products = new ProductsAdapter(HomeActivity.this, samsungDes, samsungiIds, samsungCost, samsungRating, samsungBrand, listItemLayout);
                        productList.setAdapter(products);
                        break;
                    case 3:
                        selectedBrand = "Nokia";
                        selectedSingleImageId = nokiaIds;
                        productList = findViewById(R.id.productsList);
                        ProductsAdapter nokiaProducts = new ProductsAdapter(HomeActivity.this, nokiaDes, nokiaIds, nokiaCost, nokiaRating, nokiaBrand, listItemLayout);
                        productList.setAdapter(nokiaProducts);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter spinner = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerBrandNames);

        spinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spin.setAdapter(spinner);


        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                singleProductData.setImageId(selectedSingleImageId[position]);

                singleProductData.setBrand(selectedBrand);

                RatingBar txtRating = findViewById(R.id.ratingBar);
                singleProductData.setRating(txtRating.getRating());

                TextView txtCost = view.findViewById(R.id.selectedProductCost);
                String cost = txtCost.getText().toString().trim();
                singleProductData.setCost(Integer.parseInt(cost));

                TextView txtDesc = view.findViewById(R.id.selectedProductDescription);
                singleProductData.setDescription(txtDesc.getText().toString());


                Intent singleProductIntent = new Intent(HomeActivity.this, SingleProduct.class);
                singleProductIntent.putExtra("singleProductData", singleProductData);
                startActivity(singleProductIntent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginValidation.getLoginStatus() == 1) {
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(HomeActivity.this);
                    View mview = getLayoutInflater().inflate(R.layout.activity_my_account, null);
                    myBuilder.setView(mview);

                    nameView = mview.findViewById(R.id.txtname);
                    emailView = mview.findViewById(R.id.txtemail);
                    phoneView = mview.findViewById(R.id.txtphone);

                    Button b = mview.findViewById(R.id.register_btn);
                    b.setVisibility(View.GONE);

                    final AlertDialog myDialog = myBuilder.create();
                    myDialog.show();

                    //accountImageView.setImageURI(Uri.parse(userData.getImageUri()));
                    nameView.setText("Name :" + userData.getName());
                    emailView.setText("Email :" + userData.getEmail());
                    phoneView.setText("Phone :" + userData.getPhone());

                } else {
                    Snackbar.make(view, "Please register and login to continue", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_account) {
            if (loginValidation.getLoginStatus() == 1) {
                Intent accountIntent = new Intent(this, YourAccount.class);
                accountIntent.putExtra("userDataObject", userData);
                startActivity(accountIntent);
            } else {

                Intent noLoginIntent = new Intent(this, NoLogin.class);
                startActivity(noLoginIntent);
            }
        } else if (id == R.id.nav_cart) {

            if (loginValidation.getLoginStatus() == 1) {
                Intent cartIntent = new Intent(this, YourCart.class);
                startActivity(cartIntent);
            } else {
                new AlertDialog.Builder(this).setTitle("Requesting!").setMessage("You need to login to view the items in your cart.").setCancelable(false)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        }).show();
            }


        } else if (id == R.id.nav_orders) {

            if (loginValidation.getLoginStatus() == 1) {
                Intent orderIntent = new Intent(this, YourOrders.class);
                startActivity(orderIntent);
            } else {
                new AlertDialog.Builder(this).setTitle("Requesting!").setMessage("You need to login to view the ordered items.").setCancelable(false)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

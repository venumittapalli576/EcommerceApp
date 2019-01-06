package ecommerceapp.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import model.quiz.glarimy.com.ecommerceapp.R;


public class ProductsAdapter extends BaseAdapter
{

    ImageView productImage;
    TextView productDescription, productCost, productBrand;
    RatingBar productRating;

    LayoutInflater inflter;
    String productDescriptions[],brand[];
    long imageIds[];
    int productCosts[];
    float productRatings[];
    int listItemLayout;

    public ProductsAdapter(Context context, String[] description, long[] ids, int[] cost, float[] rating, String[] brand,int listItemLayout)
    {

        this.productDescriptions = description;
        this.imageIds =ids;
        this.productCosts =cost;
        this.productRatings=rating;
        this.brand=brand;
        inflter=(LayoutInflater.from(context));
        this.listItemLayout=listItemLayout;
    }


    @Override
    public int getCount() {
        return productDescriptions.length;
    }

    @Override
    public Object getItem(int position) {
            return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = inflter.inflate( listItemLayout, null);

        productDescription = convertView.findViewById(R.id.selectedProductDescription);
        productCost =convertView.findViewById(R.id.selectedProductCost);
        productImage=convertView.findViewById(R.id.selectedProductImage);
        productRating=convertView.findViewById(R.id.ratingBar);
        productBrand=convertView.findViewById(R.id.product_brand);

        productRating.setRating(productRatings[position]);
        productDescription.setText(productDescriptions[position]);
        productCost.setText(""+ productCosts[position]);
        productImage.setImageResource((int) imageIds[position]);
        productBrand.setText(brand[position]);

        return convertView;
    }
}

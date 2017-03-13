package xmart.com.xmart_android.activity.user.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alertdialogpro.AlertDialogPro;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.adapter.user.main.AdapterCartItem;
import xmart.com.xmart_android.db.CartItem;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.service.NguoiDungService;


public class AddToCartDetailActivity extends AppCompatActivity {


    private Toolbar toolbar;

    private NguoiDungService nguoiDungService;

    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private AdapterCartItem adapterCartItem;
    private RecyclerView recyclerView;
    private NguoiDung nguoiDung;

    private Button pay;
    private Button addToCart;

    private ImageView addQuantity;
    private ImageView subQuantity;

    private ImageView imageViewProduct;

    private TextView nameSp;
    private TextView priceSp;
    private TextView nameOwner;
    public String productId;
    private EditText quantity;

    private int badgeCount = 0;
    private int ans = 0;

    public AlertDialogPro.Builder goToCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart_detail);
        setTitle("Detail item");
        nguoiDungService = new NguoiDungService(getApplicationContext());
        nguoiDung = nguoiDungService.selectAllNguoiDung().get(0);
        mapping();

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        String nameProduct = intent.getStringExtra("nameProduct");
        String priceProduct = intent.getStringExtra("priceProduct");
        String nameOw = intent.getStringExtra("nameOwner");
        String imageProduct = intent.getStringExtra("imageProduct");

        //settext
        nameSp.setText(nameProduct);
        priceSp.setText(priceProduct);
        nameOwner.setText(nameOw);

        //get image from url
        String url = imageProduct.replace("../", "http://xapp.codew.net/");
        new DownloadImageTask(imageViewProduct).execute(url);


        final AlertDialogPro.Builder addProductToCart = new AlertDialogPro.Builder(this);
        addProductToCart.setIcon(R.drawable.ic_cart).
                setTitle("Thông báo").
                setMessage("Bạn muốn thêm sản phẩm vào giỏ hàng...? ").
                setPositiveButton("Thêm vào giỏ hàng", new ButtonClickedListener("Đồng ý")).
                setNegativeButton("Bỏ qua",  new ButtonClickedListener("Bỏ qua"));

        goToCart = new AlertDialogPro.Builder(this);
        goToCart.setIcon(R.drawable.ic_cart).
                setTitle("Thông báo").
                setMessage("Thêm vào giỏ hàng giỏ hàng thành công :)\n" +
                        " Bạn có muốn đến giỏ hàng ?").
                setPositiveButton("Đến giỏ hàng", new ButtonClickedListener("Đến giỏ hàng")).
                setNegativeButton("Bỏ qua",  new ButtonClickedListener("Bỏ qua"));

        //pay click and add to order
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add sp to cart and after go to cart activity
                try {
                    Integer quan = Integer.parseInt(quantity.getText().toString());
                    addProductToCart(productId, nguoiDung.getId().toString(), quan.toString());
                    L.T(getApplicationContext(), "Đã thêm sản phầm vào giỏ hàng");
                } catch (Exception ex) {
                    L.t(getApplicationContext(), "Số lượng là một con số");
                }
                startActivity(new Intent(AddToCartDetailActivity.this, CartItemActivity.class));
                overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
            }
        });


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add product to cart
                addProductToCart.show();
            }
        });


        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer temp = 1;
                try {
                    temp = Integer.parseInt(quantity.getText().toString());
                    quantity.setText("" + (temp + 1));
                } catch (Exception ex) {
                    L.t(getApplicationContext(), "Số lượng là một con số");
                }

            }
        });

        subQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer temp = 1;
                try {
                    temp = Integer.parseInt(quantity.getText().toString());
                    if (temp > 1) {
                        quantity.setText("" + (temp - 1));
                    }
                } catch (Exception ex) {
                    L.t(getApplicationContext(), "Số lượng là một con số");
                }
            }
        });

    }

    private class ButtonClickedListener implements DialogInterface.OnClickListener {
        private CharSequence mShowWhenClicked;

        public ButtonClickedListener(CharSequence showWhenClicked) {
            mShowWhenClicked = showWhenClicked;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(mShowWhenClicked.equals("Đồng ý")){
                try {
                    Integer quan = Integer.parseInt(quantity.getText().toString());
                    addProductToCart(productId, nguoiDung.getId().toString(), quan.toString());
                    goToCart.show();
                } catch (Exception ex) {
                    L.t(getApplicationContext(), "Số lượng là một con số");
                }
            }
            //sad
            if(mShowWhenClicked.equals("Đến giỏ hàng")){
                startActivity(new Intent(AddToCartDetailActivity.this, CartItemActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
            if(mShowWhenClicked.equals("camera")){
                //camere
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);//zero can be replaced with any action code
            }
            if(mShowWhenClicked.equals("photo")){
                //chon tu photo
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
            }
        }
    }


    //class set image
    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bmImage.setImageBitmap(result);
        }
    }


//    public void updateBadge(){
//        ((MainActivity)getApplicationContext()).updateBadge(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString());
//
//    }

    public void addProductToCart(final String productId, final String userId, final String quantity) {
        L.m("le hong phuong post");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://xmart.codew.net/addToCart.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        L.m(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        L.m(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ProductId", productId);
                params.put("UserId", userId);
                params.put("Quantity", quantity);

                return params;
            }
        };
        queue.add(postRequest);
    }


    //tao menu cho menu main
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_product_menu, menu);
        if (badgeCount > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.action_cart), getResources().getDrawable(R.drawable.ic_cart), ActionItemBadge.BadgeStyles.RED, badgeCount);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.action_cart));
        }
        return true;
    }

    //xu ly su kien cho menu main
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            //get id
            startActivity(new Intent(AddToCartDetailActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }
        if (id == R.id.action_cart) {
            //get id
            startActivity(new Intent(AddToCartDetailActivity.this, CartItemActivity.class));
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }
        return true;
    }


    public void mapping() {
        imageViewProduct = (ImageView) findViewById(R.id.imageSP);

        pay = (Button) findViewById(R.id.go_to_cart);
        addToCart = (Button) findViewById(R.id.add_to_cart);
        addQuantity = (ImageView) findViewById(R.id.addQuantity);
        subQuantity = (ImageView) findViewById(R.id.subQuantity);

        nameSp = (TextView) findViewById(R.id.name_sp);
        priceSp = (TextView) findViewById(R.id.price_sp);
        nameOwner = (TextView) findViewById(R.id.owner_name);
        quantity = (EditText) findViewById(R.id.quantity);

    }
}

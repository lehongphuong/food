package xmart.com.xmart_android.activity.user.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alertdialogpro.AlertDialogPro;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.adapter.user.main.AdapterCartItem;
import xmart.com.xmart_android.db.CartItem;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.other.DividerItemDecoration;
import xmart.com.xmart_android.service.NguoiDungService;


public class CartItemActivity extends AppCompatActivity {


    private Toolbar toolbar;

    private NguoiDungService nguoiDungService;

    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private AdapterCartItem adapterCartItem;
    private RecyclerView recyclerView;
    private NguoiDung nguoiDung;

    private Button pay;
    private TextView total;

    private int badgeCount = 0;

    public AlertDialogPro.Builder goToOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_item);
        MultiDex.install(this);
        setTitle("Cart item");
        nguoiDungService = new NguoiDungService(getApplicationContext());
        nguoiDung = nguoiDungService.selectAllNguoiDung().get(0);
        mapping();


        //toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //khoi tao recycleview
        recyclerView = (RecyclerView) findViewById(R.id.listCartItem);
        //horizontal rycycleview
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        adapterCartItem = new AdapterCartItem(CartItemActivity.this);
        recyclerView.setAdapter(adapterCartItem);

        adapterCartItem.setData(cartItems);

        final AlertDialogPro.Builder addProductToOrder = new AlertDialogPro.Builder(this);
        addProductToOrder.setIcon(R.drawable.ic_cart).
                setTitle("Thông báo").
                setMessage("Bạn chắc chắn đặt hàng với các sản phẩm này không...? ").
                setPositiveButton("Bạn muốn Đặt hàng", new CartItemActivity.ButtonClickedListener("Đồng ý")).
                setNegativeButton("Bỏ qua", new CartItemActivity.ButtonClickedListener("Bỏ qua"));

        goToOrder = new AlertDialogPro.Builder(this);
        goToOrder.setIcon(R.drawable.ic_cart).
                setTitle("Thông báo").
                setMessage("Thêm vào giỏ hàng giỏ hàng thành công :)\n" +
                        " Bạn có muốn xem đơn đặt hàng  ?").
                setPositiveButton("Xem đơn đặt hàng", new CartItemActivity.ButtonClickedListener("Xem đơn hàng")).
                setNegativeButton("Bỏ qua", new CartItemActivity.ButtonClickedListener("Bỏ qua"));


        //pay click and add to order
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToOrder.show();
            }
        });

        getCartItem(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString());
    }

    private class ButtonClickedListener implements DialogInterface.OnClickListener {
        private CharSequence mShowWhenClicked;

        public ButtonClickedListener(CharSequence showWhenClicked) {
            mShowWhenClicked = showWhenClicked;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (mShowWhenClicked.equals("Đồng ý")) {
                addItemToOrder(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString());
                goToOrder.show();
            }
            //sad
            if (mShowWhenClicked.equals("Xem đơn hàng")) {
                startActivity(new Intent(CartItemActivity.this, OrderFormActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
            if (mShowWhenClicked.equals("camera")) {
                //camere
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);//zero can be replaced with any action code
            }
            if (mShowWhenClicked.equals("photo")) {
                //chon tu photo
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
            }
        }
    }


    public void updateTotal(int temp) {
      /*  int sum=Integer.parseInt(total.getText().toString());
        total.setText((sum+temp) + " VND");*/
    }


    public void getCartItem(final String user, final String token, final String userId) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://xapp.codew.net/api/cartItems.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            L.m(response);
                            //kiem tra co loi khong
                            String errorLogic = jsonObject.getString("errorLogic");
                            String errorSQL = jsonObject.getString("errorSQL");
                            if (errorLogic.length() == 0) {
                                //get array tu data jsonobject
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    CartItem cartItem = new CartItem();
                                    cartItem.setId(jsonObject1.getString("Id"));
                                    cartItem.setProductName(jsonObject1.getString("ProductName"));
                                    cartItem.setShopName(jsonObject1.getString("ShopName"));
                                    cartItem.setPrice(jsonObject1.getString("Price"));
                                    cartItem.setQuantity(jsonObject1.getString("Quantity"));
                                    cartItem.setImage(jsonObject1.getString("Image"));
                                    cartItems.add(cartItem);
                                }
                                adapterCartItem.setData(cartItems);
                                int sum=0;
                                for (CartItem c:cartItems) {
                                    sum+=Integer.parseInt(c.getQuantity())*Integer.parseInt(c.getPrice());
                                }
                                total.setText(sum+"");

                            } else {
                                L.T(getApplicationContext(), "Có ai đó đăng nhập tài khoản của bạn..");
                                startActivity(new Intent(CartItemActivity.this, UserLoginActivity.class));
                                finish();
                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject object = new JSONObject();
                try {
                    //chuyen doi tuong thanh json string
                    object.put("Type", "1");
                    object.put("Command", "userGetItemsInCart");
                    object.put("UserName", user);
                    object.put("Token", token);
                    object.put("UserId", userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = object.toString();
                return json.getBytes();
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void addItemToOrder(final String user, final String token, final String userId) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://xapp.codew.net/api/cartItems.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            L.m(response);
                            //kiem tra co loi khong
                            String errorLogic = jsonObject.getString("errorLogic");
                            String errorSQL = jsonObject.getString("errorSQL");
                            if (errorLogic.length() == 0) {
                                //after pay success remove all item in cart
                                cartItems.clear();
                                adapterCartItem.notifyDataSetChanged();

                            } else {
                                L.T(getApplicationContext(), "Có ai đó đăng nhập tài khoản của bạn..");
                                startActivity(new Intent(CartItemActivity.this, UserLoginActivity.class));
                                finish();
                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject object = new JSONObject();
                try {
                    //chuyen doi tuong thanh json string
                    object.put("Type", "1");
                    object.put("Command", "userMakeOrdersFromCart");
                    object.put("UserName", user);
                    object.put("Token", token);
                    object.put("UserId", userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = object.toString();
                return json.getBytes();
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    //tao menu cho menu main
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.infor_menu, menu);
        return true;
    }

    //xu ly su kien cho menu main
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            startActivity(new Intent(CartItemActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }
        return true;
    }


    public void mapping() {
        pay = (Button) findViewById(R.id.pay);
        total = (TextView) findViewById(R.id.total);

    }
}

package xmart.com.xmart_android.activity.user.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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
import xmart.com.xmart_android.adapter.user.main.AdapterProduct;
import xmart.com.xmart_android.db.ProductNew;

public class ProductsOfOwnerActivity extends AppCompatActivity {

    private ArrayList<ProductNew> arrayList = new ArrayList<>();
    private AdapterProduct adapterProduct;
    private RecyclerView listProduct;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_of_owner);

        listProduct = (RecyclerView) findViewById(R.id.list_product);
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tao cot va nhieu dong
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        listProduct.setHasFixedSize(true);
        listProduct.setLayoutManager(layoutManager);
        //chia lay out thanh tung box nho voi nhau
//        listProduct.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
//        listSubjectHits1.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        listProduct.setItemAnimator(new DefaultItemAnimator());
        adapterProduct = new AdapterProduct(getApplicationContext());
        listProduct.setAdapter(adapterProduct);

        adapterProduct.setData(arrayList);


        listProduct.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), listProduct, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //update history when click
                ProductNew productNew = arrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), AddToCartDetailActivity.class);
                intent.putExtra("productId", productNew.getId());
                intent.putExtra("nameProduct", productNew.getName());
                intent.putExtra("priceProduct", productNew.getPrice());
                intent.putExtra("nameOwner", productNew.getOwnerId());
                intent.putExtra("imageProduct", productNew.getImage());

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        Intent itent=getIntent();
        String ownerId=itent.getStringExtra("OwnerId");
        getProductByCategoriesId(ownerId);

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
            startActivity(new Intent(ProductsOfOwnerActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }
        return true;
    }

    public void getProductByCategoriesId(final String ownerId ) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://xapp.codew.net/api/mobileView.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            L.m(response);
                            //kiem tra co loi khong
                            String errorLogic = jsonObject.getString("errorLogic");
                            String errorSQL = jsonObject.getString("errorSQL");
                            if ("null".equals(errorLogic)) {
                                //get array tu data jsonobject
//                                message.setText("Thanh Cong");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    ProductNew productNew = new ProductNew();
                                    productNew.setId(jsonObject1.getString("Id"));
                                    productNew.setOwnerId(jsonObject1.getString("OwnerId"));
                                    productNew.setSKU(jsonObject1.getString("SKU"));
                                    productNew.setName(jsonObject1.getString("Name"));
                                    productNew.setPrice(jsonObject1.getString("Price"));
                                    productNew.setDesc(jsonObject1.getString("Desc"));
                                    productNew.setUnitId(jsonObject1.getString("UnitId"));
                                    productNew.setUnitName(jsonObject1.getString("UnitName"));
                                    productNew.setCategoryId(jsonObject1.getString("CategoryId"));
                                    productNew.setCategoryName(jsonObject1.getString("CategoryName"));
                                    productNew.setExpired(jsonObject1.getString("Expired"));
                                    productNew.setStock(jsonObject1.getString("Stock"));
                                    productNew.setImage(jsonObject1.getString("Image"));

                                    arrayList.add(productNew);
                                }
                                adapterProduct.notifyDataSetChanged();



                            } else {
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
                    object.put("Command", "getProductsByOwner");
                    object.put("OwnerId", ownerId);
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

}

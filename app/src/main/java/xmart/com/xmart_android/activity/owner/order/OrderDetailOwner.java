package xmart.com.xmart_android.activity.owner.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gc.materialdesign.views.ButtonFlat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.db.OrderOwner;
import xmart.com.xmart_android.service.NguoiDungService;


public class OrderDetailOwner extends AppCompatActivity {

    private NguoiDungService nguoiDungService;

    private NguoiDung nguoiDung;

    private TextView customName;
    private TextView phone;
    private TextView gender;
    private TextView homeAddress;
    private TextView workAddress;
    private TextView status;
    private TextView ordered;
    private TextView processed;
    private TextView canceled;
    private TextView completed;
    private TextView total;
    private TextView nameProduct;
    private ButtonFlat process;
    private Toolbar toolbar;

    private OrderOwner owner;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_owner);
        nguoiDungService = new NguoiDungService(getApplicationContext());
        nguoiDung = nguoiDungService.selectAllNguoiDung().get(0);
        setTitle("Đơn hàng đã đặt");
        mapping();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //lap du lieu tu ordered fragment
        final Intent intent = getIntent();
         owner = (OrderOwner) intent.getSerializableExtra("order");
        position=intent.getIntExtra("position",0);


        //set du lieu
        customName.setText(owner.getFirstName() + " " + owner.getLastName());
        phone.setText(owner.getPhoneNumber());
        gender.setText(owner.getGender() == "0" ? "Female" : "Male");
        homeAddress.setText(owner.getHomeAddr());
        workAddress.setText(owner.getWorkAddr());
        status.setText(owner.getStatus());
        ordered.setText(owner.getOrdered());
        processed.setText(owner.getProcessed());
        canceled.setText(owner.getCanceled());
        completed.setText(owner.getCompleted());
        total.setText(owner.getTotal());


        getOrderItem(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString(), owner.getId());

        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processOrder(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString(), owner.getId());
                intent.putExtra("position",position);
                setResult(2,intent);
                finish();
                overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }
        return super.onOptionsItemSelected(item);
    }


    private void mapping() {
        customName = (TextView) findViewById(R.id.name_custom);
        phone = (TextView) findViewById(R.id.phone);
        gender = (TextView) findViewById(R.id.gender);
        homeAddress = (TextView) findViewById(R.id.homeAddress);
        workAddress = (TextView) findViewById(R.id.workAddress);
        status = (TextView) findViewById(R.id.status);
        ordered = (TextView) findViewById(R.id.order);
        processed = (TextView) findViewById(R.id.process);
        canceled = (TextView) findViewById(R.id.cancel);
        completed = (TextView) findViewById(R.id.complete);
        total = (TextView) findViewById(R.id.total);
        nameProduct = (TextView) findViewById(R.id.name_product);
        process = (ButtonFlat) findViewById(R.id.buttonProcess);

    }

    /**
     * process order, pruduct will to the table Process
     * @param user
     * @param token
     * @param ownerId
     * @param orderId
     */
    public void processOrder(final String user, final String token, final String ownerId, final String orderId) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://xapp.codew.net/api/orders.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response

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
                    object.put("Type", "2");
                    object.put("Command", "ownerProcessOrder");
                    object.put("UserName", user);
                    object.put("Token", token);
                    object.put("OwnerId", ownerId);
                    object.put("OrderId", orderId);
                    object.put("Note", "Đơn hàng đã được gởi ...");
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

    public void getOrderItem(final String user, final String token, final String ownerId, final String orderId) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://xapp.codew.net/api/items.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //kiem tra co loi khong
                            String errorLogic = jsonObject.getString("errorLogic");
                            String errorSQL = jsonObject.getString("errorSQL");

                            if (errorLogic.length() == 0) {
                                //get array tu data jsonobject
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                nameProduct.setText(jsonObject1.getString("ProductName"));
                                for (int i = 1; i < jsonArray.length(); i++) {
                                    jsonObject1 = jsonArray.getJSONObject(i);

                                    nameProduct.setText(nameProduct.getText() + System.getProperty("line.separator")
                                            + jsonObject1.getString("ProductName"));

                                }

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
                    object.put("Type", "2");
                    object.put("Command", "ownerGetItemsOfOrder");
                    object.put("UserName", user);
                    object.put("Token", token);
                    object.put("OwnerId", ownerId);
                    object.put("OrderId", orderId);
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

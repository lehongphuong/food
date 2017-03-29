package xmart.com.xmart_android.activity.owner.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.service.NguoiDungService;

public class OrderDetailOwner extends AppCompatActivity {

    private NguoiDungService nguoiDungService;

    private NguoiDung nguoiDung;

    private TextView tenShop;
    private TextView trangThai;
    private TextView ngayTao;
    private TextView ngayXuLy;
    private TextView ngayHuy;
    private TextView ngayHoanThanh;
    private TextView tongTien;
    private TextView tenSanPham;
    private FloatingActionButton back;
    private FloatingActionButton delete;

    private int ans = 0;

    public Intent intent;
    public String orderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_owner);
        nguoiDungService = new NguoiDungService(getApplicationContext());
        nguoiDung = nguoiDungService.selectAllNguoiDung().get(0);
        setTitle("Detail Order");
        mapping();

        //lap du lieu tu ordered fragment
        intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        String mTrangThai = intent.getStringExtra("trangThai");
        String mTenShop = intent.getStringExtra("tenShop");

        //set du lieu
        tenShop.setText(mTenShop);
        trangThai.setText(mTrangThai);
        tongTien.setText(intent.getStringExtra("tongTien"));
        ngayXuLy.setText(intent.getStringExtra("ngayXuLy"));
        ngayHuy.setText(intent.getStringExtra("ngayHuy"));
        ngayHoanThanh.setText(intent.getStringExtra("ngayHoanThanh"));


        getOrderItem(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString(), orderId);

        final AlertDialogPro.Builder materialDialog = new AlertDialogPro.Builder(this);
        materialDialog.setIcon(R.drawable.ic_cart).
                setTitle("Thông báo").
                setMessage("Bạn có muốn hủy đơn hàng...? ").
                setPositiveButton("Hủy đơn hàng", new ButtonClickedListener("Đồng ý")).
                setNegativeButton("Bỏ qua", new ButtonClickedListener("Bỏ qua"));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans = 0;
                materialDialog.show();

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
            if (mShowWhenClicked.equals("Đồng ý")) {
                deleteOrder(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString(), orderId);
                intent.putExtra("position", intent.getIntExtra("position", -1));
                setResult(2, intent);
                finish();
                overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
            }
            if (mShowWhenClicked.equals("Đến giỏ hàng")) {

            }
            if (mShowWhenClicked.equals("camera")) {
                //camere
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);//zero can be replaced with any action code
            }
            if (mShowWhenClicked.equals("photo")) {
                //chon tu photo
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
            }
        }
    }

    private void mapping() {
        tenShop = (TextView) findViewById(R.id.ten_shop);
        trangThai = (TextView) findViewById(R.id.trang_thai);
        ngayTao = (TextView) findViewById(R.id.ngay_tao);
        ngayXuLy = (TextView) findViewById(R.id.ngay_xu_ly);
        ngayHuy = (TextView) findViewById(R.id.ngay_huy);
        ngayHoanThanh = (TextView) findViewById(R.id.ngay_hoan_thanh);
        tongTien = (TextView) findViewById(R.id.tong_tien);
        tenSanPham = (TextView) findViewById(R.id.ten_san_pham);
        back = (FloatingActionButton) findViewById(R.id.action_back);
        delete = (FloatingActionButton) findViewById(R.id.action_delete);
    }

    public void deleteOrder(final String user, final String token, final String userId, final String orderId) {
        // Instantiate the RequestQueue.
        L.m("That bai" + userId);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://app.codew.net/api/orders.php";
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
                            L.m("kiem tra " + errorLogic + " " + errorSQL);

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
                    object.put("Command", "userCancelOrdered");
                    object.put("UserName", user);
                    object.put("UserId", userId);
                    object.put("Token", token);
                    object.put("OrderId", orderId);
                    object.put("Note", "Sai thông tin");
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

    public void getOrderItem(final String user, final String token, final String userId, final String orderId) {
        // Instantiate the RequestQueue.
        L.m("That bai" + userId);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://app.codew.net/api/items.php";
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
                            L.m("kiem tra " + errorLogic + " " + errorSQL);

                            if (errorLogic.length() == 0) {
                                //get array tu data jsonobject
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    ngayTao.setText(jsonObject1.getString("Added"));

                                    tenSanPham.setText(jsonObject1.getString("ProductName"));

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
                    object.put("Type", "1");
                    object.put("Command", "userGetItemsOfOrder");
                    object.put("UserName", user);
                    object.put("Token", token);
                    object.put("UserId", userId);
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

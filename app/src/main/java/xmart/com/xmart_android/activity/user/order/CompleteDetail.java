package xmart.com.xmart_android.activity.user.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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


public class CompleteDetail extends AppCompatActivity {

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_detail);
        nguoiDungService = new NguoiDungService(getApplicationContext());
        nguoiDung=nguoiDungService.selectAllNguoiDung().get(0);
        setTitle("Detail Competed");
        mapping();

        //lap du lieu tu ordered fragment
        final Intent intent=getIntent();

        final String orderId=intent.getStringExtra("orderId");
        String mTrangThai=intent.getStringExtra("trangThai");
        String mTenShop=intent.getStringExtra("tenShop");

        //set du lieu
        tenShop.setText(mTenShop);
        trangThai.setText(mTrangThai);
        tongTien.setText(intent.getStringExtra("tongTien"));
        ngayXuLy.setText(intent.getStringExtra("ngayXuLy"));
        ngayHuy.setText(intent.getStringExtra("ngayHuy"));
        ngayHoanThanh.setText(intent.getStringExtra("ngayHoanThanh"));


        getOrderItem(nguoiDung.getUserName(),nguoiDung.getToken(),nguoiDung.getId().toString(),orderId);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
            }
        });

    }



    private void mapping() {
        tenShop= (TextView) findViewById(R.id.ten_shop);
        trangThai= (TextView) findViewById(R.id.trang_thai);
        ngayTao= (TextView) findViewById(R.id.ngay_tao);
        ngayXuLy= (TextView) findViewById(R.id.ngay_xu_ly);
        ngayHuy= (TextView) findViewById(R.id.ngay_huy);
        ngayHoanThanh= (TextView) findViewById(R.id.ngay_hoan_thanh);
        tongTien= (TextView) findViewById(R.id.tong_tien);
        tenSanPham= (TextView) findViewById(R.id.ten_san_pham);
        back= (FloatingActionButton) findViewById(R.id.action_back);

    }

    public void getOrderItem(final String user, final String token, final  String userId, final String orderId) {
        // Instantiate the RequestQueue.
        L.m("That bai"+ userId);
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
                                JSONArray jsonArray=jsonObject.getJSONArray("data");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                    ngayTao.setText(jsonObject1.getString("Added"));
//                                    tongTien.setText(jsonObject1.getString("Amount"));
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
                    object.put("Command", "userGetCompleted");
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

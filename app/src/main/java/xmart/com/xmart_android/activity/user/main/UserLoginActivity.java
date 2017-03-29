package xmart.com.xmart_android.activity.user.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import xmart.com.xmart_android.R;
import xmart.com.xmart_android.activity.owner.main.OwnerMainActivity;
import xmart.com.xmart_android.db.Info;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.service.InfoService;
import xmart.com.xmart_android.service.NguoiDungService;

public class UserLoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private TextView createAccount;
    private MaterialSpinner spinner1;
    private ArrayAdapter<String> adapter;
    private int idSpinnerSelect = -1;
    private ArrayList<String> arrayListNameSubject = new ArrayList<>();

    private Button login;
    InfoService infoService;
    Info info;
    NguoiDungService nguoiDungService;

    private final int delay = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        nguoiDungService = new NguoiDungService(getApplicationContext());
        infoService = new InfoService(getApplicationContext());


        anhXa();
        userName.setText("bao");
        password.setText("123456");


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idSpinnerSelect = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ///login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.m("login click");
                String user = userName.getText().toString();
                String pass = password.getText().toString();

                if (idSpinnerSelect == -1) {
                    L.T(getApplicationContext(), "Vui lòng chọn loại người dùng");
                } else {
                    //user login
                    if (idSpinnerSelect == 0) {
                        getUserFirtLogin(user, pass);
                    } else {
                        //owner login
                        getOwnerFirtLogin(user, pass);
                    }

                }
            }
        });

        //create account user
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://xmart.codew.net/";
                Intent intent = new Intent(UserLoginActivity.this, WebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });


    }


    /**
     * user firt login
     *
     * @param user
     * @param pass
     */
    public void getUserFirtLogin(final String user, final String pass) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://xapp.codew.net/api/login.php";
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
//                                message.setText("Thanh Cong");
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String json = jsonObject1.toString();
                                NguoiDung nguoiDung = new NguoiDung();
                                nguoiDung.setId(jsonObject1.getString("Id"));
                                nguoiDung = nguoiDung.jsonToObject(json);
                                nguoiDungService.deleteAll();
                                nguoiDungService.insert(nguoiDung);
                                Info info = new Info();
                                info.setId("1");
                                info.setUser(nguoiDung.getUserName());
                                info.setType(idSpinnerSelect == 0 ? "User" : "Owner");
                                info.setToken(nguoiDung.getToken());
                                infoService.deleteAll();
                                infoService.insert(info);
                                //dang nhap thanh cong thi vao man hinh chinh

                                Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                                startActivityForResult(intent, 1);
//                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            } else {
//                                L.t(getApplicationContext(),errorLogic);
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
                    object.put("Command", "login");
                    object.put("UserName", user);
                    object.put("Password", pass);
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

    /**
     * owner firt login
     *
     * @param user
     * @param pass
     */
    public void getOwnerFirtLogin(final String user, final String pass) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://xapp.codew.net/api/login.php";
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
                            if (errorLogic.length() == 0) {
                                //get array tu data jsonobject
//                                message.setText("Thanh Cong");
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String json = jsonObject1.toString();
                                NguoiDung nguoiDung = new NguoiDung();
                                nguoiDung.setId(jsonObject1.getString("Id"));
                                nguoiDung = nguoiDung.jsonToObject(json);
                                nguoiDungService.deleteAll();
                                nguoiDungService.insert(nguoiDung);
                                Info info = new Info();
                                info.setId("1");
                                info.setUser(nguoiDung.getUserName());
                                info.setType(idSpinnerSelect == 0 ? "User" : "Owner");
                                info.setToken(nguoiDung.getToken());
                                infoService.deleteAll();
                                infoService.insert(info);
                                //dang nhap thanh cong thi vao man hinh chinh

                                Intent intent = new Intent(UserLoginActivity.this, OwnerMainActivity.class);
                                startActivityForResult(intent, 1);
                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            } else {
//                                L.t(getApplicationContext(),errorLogic);

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
                    object.put("Command", "login");
                    object.put("UserName", user);
                    object.put("Password", pass);
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

    public void anhXa() {
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        spinner1 = (MaterialSpinner) findViewById(R.id.spinner1);
        login = (Button) findViewById(R.id.login);
        createAccount = (TextView) findViewById(R.id.link_signup);

        //sniping
        arrayListNameSubject.add("User");
        arrayListNameSubject.add("Owner");
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, arrayListNameSubject);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setPaddingSafe(0, 0, 0, 0);
        spinner1.setSelection(1);

    }

}

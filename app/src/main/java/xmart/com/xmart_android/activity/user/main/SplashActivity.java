package xmart.com.xmart_android.activity.user.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

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

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.db.Categories;
import xmart.com.xmart_android.db.Info;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.service.CategoriesService;
import xmart.com.xmart_android.service.InfoService;
import xmart.com.xmart_android.service.NguoiDungService;


public class SplashActivity extends Activity {


    private Handler mHandler;

    private long delay = 1500;
    private int i = 0;
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    InfoService infoService;
    Info info;
    //login =0 login to UserLogin activity
    //lgoin =1 login to user
    //login =2 login to owner
    NguoiDungService nguoiDungService;
    CategoriesService categoriesService;
    private int login = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        infoService = new InfoService(getApplicationContext());

        nguoiDungService = new NguoiDungService(getApplicationContext());
        categoriesService = new CategoriesService(getApplicationContext());

        getCategories();
        //check login
        if (!infoService.isEmpty()) {
            //other first login
            info = infoService.selectAllInfo().get(0);
            String type = info.getType();
            //check login user
            if ("User".equals(type)) {
                checkUserLogin(info.getUser(), info.getToken());
            } else {
                //check login owner
                checkOwnerLogin(info.getUser(), info.getToken());
            }
        }

        Timer timer = new Timer();
        timer.schedule(task, delay);
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            if (login == 0) {
                Intent intent = new Intent(SplashActivity.this, UserLoginActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
            if (login == 1) {
                //login to user
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
            if (login == 2) {
                //login to owner
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

//            Intent in = new Intent().setClass(SplashActivity.this,
//                    UserLoginActivity.class).addFlags(
//                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(in);
//            finish();

        }
    };

    /**
     * check user login
     *
     * @param user
     * @param token
     */
    public void checkUserLogin(final String user, final String token) {
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
                                //dang nhap thanh cong thi vao man hinh chinh user

                                login = 1;
//                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            } else {
//                                L.t(getApplicationContext(),errorLogic);
                                login = 0;
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
                    object.put("Command", "loginAuto");
                    object.put("UserName", user);
                    object.put("Token", token);
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
     * get all categorie
     */
    public void getCategories() {
        // Instantiate the RequestQueue.
        L.m("bat dau get categorie ");
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://xapp.codew.net/api/categoriesView.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            L.m("1");
                            L.m(response);

                            //kiem tra co loi khong
                            String errorLogic = jsonObject.getString("errorLogic");
                            String errorSQL = jsonObject.getString("errorSQL");
                            L.m("2");
                            categoriesService.deleteAll();
                            L.m("3");
                            if ("null".equals(errorLogic)) {
                                //get array tu data jsonobject
//                                message.setText("Thanh Cong");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    Categories categories = new Categories();
                                    categories.setId(jsonObject1.getString("Id"));
                                    categories.setName(jsonObject1.getString("Name"));
                                    categories.setDesc(jsonObject1.getString("Desc"));
                                    categories.setImage(jsonObject1.getString("Image"));
                                    categoriesService.insert(categories);
                                    L.m("phuong "+i);
                                }

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
                    object.put("Command", "getCategories");
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
     * check owner login
     *
     * @param user
     * @param token
     */
    public void checkOwnerLogin(final String user, final String token) {
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
                                //dang nhap thanh cong thi vao man hinh chinh owner

                                login = 2;
                            } else {
//                                L.t(getApplicationContext(),errorLogic);
                                login = 0;
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
                    object.put("Command", "loginAuto");
                    object.put("UserName", user);
                    object.put("Token", token);
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

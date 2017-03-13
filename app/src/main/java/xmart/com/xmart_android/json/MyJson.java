package xmart.com.xmart_android.json;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import xmart.com.xmart_android.activity.user.main.UserLoginActivity;
import xmart.com.xmart_android.logging.L;

public class MyJson {



    private Context context;

    public MyJson() {

    }

    public MyJson(Context context) {
        this.context = context;

    }

    public boolean isLoginError(String error) {
        if (error.length() > 0) return true;
        return false;
    }


    //get all user first login
    public void getUserFirtLogin(final String user, final String pass) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://app.codew.net/api/usersLogin.php";
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
                            UserLoginActivity loginActivity = new UserLoginActivity();

                            L.m("kiem tra "+errorLogic+" "+errorSQL);
                            if (errorLogic.length() == 0) {
//                                //get array tu data jsonobject
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//                                String json = jsonObject1.toString();
//                                L.m("a " + jsonObject1.toString());
//                                UserLogin userLogin = new UserLogin();
//                                userLogin = userLogin.jsonToObject(json);
//                                userLogin.setBirthday(errorLogic);
////                                loginService.deleteAll();
//                                loginService.insert(userLogin);
//                                int size=loginService.getSize();
//                                L.m("size "+size);
                                //dang nhap thanh cong
                                L.m("thanh cong");
//                                loginActivity.dangNhapThanhCong("thanhCong");
                            }else{
                                L.m("That bai");
//                                loginActivity.dangNhapThanhCong(errorLogic.toString());
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

}

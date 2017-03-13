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

import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.service.NguoiDungService;

/**
 * Created by LehongphuongCntt on 2/25/2017.
 */

public class GetUser extends Thread {
    private Context context;
    NguoiDungService nguoiDungService;
    private String user;
    private String pass;


    public GetUser(){

    }
    public GetUser(Context context){
        this.context=context;
        nguoiDungService=new NguoiDungService(context);
        nguoiDungService.deleteAll();
    }


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
                            L.m("kiem tra "+errorLogic+" "+errorSQL);
                            if (errorLogic.length() == 0) {
                                //get array tu data jsonobject
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String json = jsonObject1.toString();
                                NguoiDung nguoiDung=new NguoiDung();
                                nguoiDung=nguoiDung.jsonToObject(json);
                                nguoiDungService.insert(nguoiDung);
                                L.m("thanh cong "+ nguoiDungService.getSize());
                            }else{
                                L.m("that bai "+ nguoiDungService.getSize());
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

    @Override
    public void run() {
        L.m("get user bat dau chay");
        getUserFirtLogin(getUser(),getPass());
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

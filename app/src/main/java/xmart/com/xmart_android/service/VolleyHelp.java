package xmart.com.xmart_android.service;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xmart.com.xmart_android.logging.L;


/**
 * Created by LehongphuongCntt on 2/23/2017.
 */

public class VolleyHelp {
    private static final int MY_SOCKET_TIMEOUT_MS = 60000;

    public String result;

    private Context context;

    public VolleyHelp() {

    }

    public VolleyHelp(Context context) {
        this.context = context;
    }

    public void getDataByGetMethod() {

        String url = "http://app.codew.net/api/test.php";
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //chuyen string thanh json object
                            JSONObject jsonObject = new JSONObject(response);
                            //get array tu data jsonobject
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                //subject.setName(jsonObject.getString("namesub"));
//                            }
                            result = jsonArray.toString();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the queue
        Volley.newRequestQueue(context).add(stringRequest);

    }

//    public void getDataByPostMethod() {
//
//        RequestQueue queue = Volley.newRequestQueue(context);  // this = context
//        String url = "http://httpbin.org/post";
//        url = "http://app.codew.net/api/test.php";
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        L.m("phuong");
//                        L.m(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        L.m("error " + error.getMessage());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Type", "1");
//                params.put("Command", "login");
//                params.put("UserName", "tuandang");
//                params.put("Password", "123456");
//
////
////                L.m(params.get("Type"));
////                L.m(params.get("Command"));
////                L.m(params.get("UserName"));
////                L.m(params.get("Password"));
//                return params;
//            }
//        };
//        queue.add(postRequest);
//
//        postRequest.setRetryPolicy(new DefaultRetryPolicy(
//                MY_SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }

    public void getDataByPostMethod() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://app.codew.net/api/usersLogin.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response
                        L.m("1 " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            L.m("2   " + jsonObject.toString());
                            //get array tu data jsonobject
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                            L.m("a " + jsonObject1.toString());


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
                    object.put("Type", "1");
                    object.put("Command", "login");
                    object.put("UserName", "tuandang");
                    object.put("Password", "123456");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = object.toString();
                return json.getBytes();
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
//        requestQueue.start();
    }


}

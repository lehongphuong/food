package xmart.com.xmart_android.fragment.user.main_fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.activity.user.main.AddToCartDetailActivity;
import xmart.com.xmart_android.activity.user.main.MainActivity;
import xmart.com.xmart_android.activity.user.main.ProductsOfOwnerActivity;
import xmart.com.xmart_android.adapter.user.main.AdapterHome;
import xmart.com.xmart_android.adapter.user.main.AdapterOwner;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.db.Owner;
import xmart.com.xmart_android.db.ProductNew;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.service.NguoiDungService;

public class FragmentHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_MOVIES = "state_movies";
    private static final String STATE_SUBJECT = "state_subjects";
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static Integer page;

    //khai bao recycle view
    private ArrayList<ProductNew> productNews = new ArrayList<>();
    private ArrayList<ProductNew> productNews1 = new ArrayList<>();
    private AdapterHome adapterHomeNew;
    private RecyclerView recyclerViewNew;

    //khai bao recycle view
    private ArrayList<ProductNew> productBestSellers = new ArrayList<>();
    private ArrayList<ProductNew> productBestSellers1 = new ArrayList<>();
    private AdapterHome adapterHomeBestSeller;
    private RecyclerView recyclerViewBestSeller;

    //khai bao recycle view onwer
    private ArrayList<Owner> arrayListOwner = new ArrayList<>();
    private ArrayList<Owner> arrayListOwner1 = new ArrayList<>();
    private AdapterOwner adapterOwner;
    private RecyclerView recyclerViewOnwer;

    private NguoiDungService nguoiDungService;
    private NguoiDung nguoiDung;


    public FragmentHome() {
        // Required empty public constructor

    }

    public static FragmentHome newInstance(String param1, String param2, int num) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        page = num;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //cho phep su dung menu tai fragment
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //chinh layout voi recycle view

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        nguoiDungService = new NguoiDungService(getContext());
        nguoiDung = nguoiDungService.selectAllNguoiDung().get(0);


//        list onner
                recyclerViewOnwer = (RecyclerView) view.findViewById(R.id.list_owner);
        //horizontal rycycleview
        LinearLayoutManager layoutManager0
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewOnwer.setHasFixedSize(true);
        recyclerViewOnwer.setLayoutManager(layoutManager0);
//        recyclerViewNew.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        recyclerViewOnwer.setItemAnimator(new DefaultItemAnimator());
        adapterOwner = new AdapterOwner(getActivity());
        recyclerViewOnwer.setAdapter(adapterOwner);
        adapterOwner.setData(arrayListOwner);
        L.m("get list owner "+ nguoiDung.getUserName()+nguoiDung.getHomeLatitude()+nguoiDung.getHomeLongitude());
        getListOwner(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString(),nguoiDung.getHomeLatitude(),nguoiDung.getHomeLongitude());


//      product new
        recyclerViewNew = (RecyclerView) view.findViewById(R.id.list_home_new);
        //horizontal rycycleview
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNew.setHasFixedSize(true);
        recyclerViewNew.setLayoutManager(layoutManager);
//        recyclerViewNew.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        recyclerViewNew.setItemAnimator(new DefaultItemAnimator());
        adapterHomeNew = new AdapterHome(getActivity());
        recyclerViewNew.setAdapter(adapterHomeNew);
        adapterHomeNew.setData(productNews);
        getProductNew();

//        product best seller
        recyclerViewBestSeller = (RecyclerView) view.findViewById(R.id.list_home_best_seller);
        //horizontal rycycleview
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBestSeller.setHasFixedSize(true);
        recyclerViewBestSeller.setLayoutManager(layoutManager1);
//        recyclerViewBestSeller.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        recyclerViewBestSeller.setItemAnimator(new DefaultItemAnimator());
        adapterHomeBestSeller = new AdapterHome(getActivity());
        recyclerViewBestSeller.setAdapter(adapterHomeBestSeller);
        adapterHomeBestSeller.setData(productBestSellers);
        getProductBestSeller();


        recyclerViewNew.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getActivity(), recyclerViewNew, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //update history when click
                ProductNew productNew = productNews.get(position);
                Intent intent = new Intent(getActivity(), AddToCartDetailActivity.class);
                intent.putExtra("productId", productNew.getId());
                intent.putExtra("nameProduct", productNew.getName());
                intent.putExtra("priceProduct", productNew.getPrice());
                intent.putExtra("nameOwner", productNew.getOwnerId());
                intent.putExtra("imageProduct", productNew.getImage());

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recyclerViewBestSeller.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getActivity(), recyclerViewNew, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //update history when click
                ProductNew productNew = productBestSellers.get(position);
                Intent intent = new Intent(getActivity(), AddToCartDetailActivity.class);
                intent.putExtra("productId", productNew.getId());
                intent.putExtra("nameProduct", productNew.getName());
                intent.putExtra("priceProduct", productNew.getPrice());
                intent.putExtra("nameOwner", productNew.getOwnerId());
                intent.putExtra("imageProduct", productNew.getImage());

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerViewOnwer.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getActivity(), recyclerViewNew, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //update history when click
                Owner owner = arrayListOwner.get(position);
                Intent intent = new Intent(getActivity(), ProductsOfOwnerActivity.class);

                intent.putExtra("OwnerId", owner.getId());

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        return view;
    }

    public void getProductNew() {
        // Instantiate the RequestQueue.
//        L.m("bat dau get product new");
        RequestQueue queue = Volley.newRequestQueue(getContext());
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

                                    productNews.add(productNew);
                                    //gan gia tri de search
                                    productNews1.add(productNew);
                                }
                                adapterHomeNew.notifyDataSetChanged();


                            } else {
//                                L.t(getApplicationContext(),errorLogic);
//                                L.m("That bai");
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
                    object.put("Command", "getProductsNew");
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


    public void getListOwner(final String userName, final String token, final String userId,
                             final String homeLat, final String homeLong) {
        // Instantiate the RequestQueue.
        L.m("bat dau get owner");
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://xapp.codew.net/api/mobileView.php";
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
                            L.m("adafsad "+errorLogic);
                            if ("null".equals(errorLogic)) {
                                //get array tu data jsonobject
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    Owner owner = new Owner();
                                    owner.setId(jsonObject1.getString("Id"));
                                    owner.setUserName(jsonObject1.getString("UserName"));
                                    owner.setToken(jsonObject1.getString("Token"));
                                    owner.setShopName(jsonObject1.getString("ShopName"));
                                    owner.setFirstName(jsonObject1.getString("FirstName"));
                                    owner.setLastName(jsonObject1.getString("LastName"));
                                    owner.setPhoneNumber(jsonObject1.getString("PhoneNumber"));
                                    owner.setBirthday(jsonObject1.getString("Birthday"));
                                    owner.setGender(jsonObject1.getString("Gender"));
                                    owner.setImage(jsonObject1.getString("Image"));
                                    owner.setImageThumb(jsonObject1.getString("ImageThumb"));
                                    owner.setAddress(jsonObject1.getString("Address"));
                                    owner.setAddrLatitude(jsonObject1.getString("AddrLatitude"));
                                    owner.setAddrLongitude(jsonObject1.getString("AddrLongitude"));
                                    L.m("list owner ");

                                    arrayListOwner.add(owner);
                                    //gan gia tri de search
                                    arrayListOwner1.add(owner);
                                }
                                adapterOwner.notifyDataSetChanged();


                            } else {
//                                L.t(getApplicationContext(),errorLogic);
//                                L.m("That bai");
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
                    object.put("Command", "getOwnersNearby");
                    object.put("UserName",userName);
                    object.put("Token", token);
                    object.put("UserId", userId);
                    object.put("HomeLatitude", homeLat);
                    object.put("HomeLongitude", homeLong);
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

    public void getProductBestSeller() {
        // Instantiate the RequestQueue.
//        L.m("bat dau get product new");
        RequestQueue queue = Volley.newRequestQueue(getContext());
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
//                            L.m("kiem tra " + errorLogic + " " + errorSQL);
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

                                    productBestSellers.add(productNew);
                                    //gan gia tri de search
                                    productBestSellers1.add(productNew);
                                }

                                adapterHomeBestSeller.notifyDataSetChanged();


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
                    object.put("Command", "getProductsBoughtMost");
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
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main_menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //search list owner
                arrayListOwner.clear();
                for (Owner owner : arrayListOwner1) {
                    //chuyen text thanh chu hoar
                    newText=newText.toUpperCase();
                    //chuan hoa truoc khi so sanh
                    String temp=owner.getShopName();
                    //chuyen tieng viet co dau thanh khong dau
                    temp=StringUtils.unAccent(temp).toUpperCase();
                    if(temp.contains(newText)){
                        arrayListOwner.add(owner);
                    }
                }
                adapterOwner.notifyDataSetChanged();

                //search list best seller by name
                productBestSellers.clear();
                for (ProductNew productNew : productBestSellers1) {
                    //chuyen text thanh chu hoa
                    newText=newText.toUpperCase();
                    //chuan hoa truoc khi so sanh
                    String temp=productNew.getName();
                    //chuyen tieng viet co dau thanh khong dau
                    temp=StringUtils.unAccent(temp).toUpperCase();
                    if(temp.contains(newText)){
                        productBestSellers.add(productNew);
                    }
                }
                adapterHomeBestSeller.notifyDataSetChanged();

                //search list new by name
                productNews.clear();
                for (ProductNew productNew : productNews1) {
                    //chuyen text thanh chu hoa
                    newText=newText.toUpperCase();
                    //chuan hoa truoc khi so sanh
                    String temp=productNew.getName();
                    //chuyen tieng viet co dau thanh khong dau
                    temp=StringUtils.unAccent(temp).toUpperCase();
                    if(temp.contains(newText)){
                        productNews.add(productNew);
                    }
                }
                adapterHomeNew.notifyDataSetChanged();

                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                          }
                                      }
        );
    }

    //chuyen doi chu tieng viet co dau thanh khong dau
    public static class StringUtils {

        public static String unAccent(String s) {
            String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
        }

    }


}


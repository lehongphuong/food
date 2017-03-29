package xmart.com.xmart_android.fragment.user.main_fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import xmart.com.xmart_android.adapter.user.main.AdapterProduct;
import xmart.com.xmart_android.db.Categories;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.db.ProductNew;
import xmart.com.xmart_android.service.CategoriesService;
import xmart.com.xmart_android.service.NguoiDungService;

public class FragmentProduct extends Fragment {

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
    private ArrayList<ProductNew> arrayList = new ArrayList<>();
    private AdapterProduct adapterProduct;
    private RecyclerView listProduct;
    CategoriesService categoriesService;

    private NguoiDungService nguoiDungService;
    private NguoiDung nguoiDung;


    public FragmentProduct() {
        // Required empty public constructor

    }

    public static FragmentProduct newInstance(String param1, String param2, int num) {
        FragmentProduct fragment = new FragmentProduct();
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

        View view = inflater.inflate(R.layout.fragment_product, container, false);

        nguoiDungService = new NguoiDungService(getContext());
        nguoiDung = nguoiDungService.selectAllNguoiDung().get(0);

        listProduct = (RecyclerView) view.findViewById(R.id.list_product);
        //dua vao bien page de tao gia tri cho cac class khac nhau
        categoriesService = new CategoriesService(getContext());

        if (!categoriesService.isEmpty()) {
            ArrayList<Categories> arrayList = categoriesService.selectAllCategories();
            try{
                getProductByCategoriesId(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString(),nguoiDung.getHomeLatitude(),nguoiDung.getHomeLongitude(),arrayList.get(page-1).getId());
            }catch (Exception ex){

            }
        }

        //tao cot va nhieu dong
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        listProduct.setHasFixedSize(true);
        listProduct.setLayoutManager(layoutManager);
        //chia lay out thanh tung box nho voi nhau
//        listProduct.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
//        listSubjectHits1.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        listProduct.setItemAnimator(new DefaultItemAnimator());
        adapterProduct = new AdapterProduct(getActivity());
        listProduct.setAdapter(adapterProduct);

        adapterProduct.setData(arrayList);


        listProduct.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getActivity(), listProduct, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //update history when click
                ProductNew productNew = arrayList.get(position);
                Intent intent = new Intent(getActivity(), AddToCartDetailActivity.class);
                intent.putExtra("productId", productNew.getId());
                intent.putExtra("nameProduct", productNew.getName());
                intent.putExtra("priceProduct", productNew.getPrice());
                intent.putExtra("nameOwner", productNew.getOwnerId());
                intent.putExtra("imageProduct", productNew.getImage());
                intent.putExtra("ownerId", productNew.getOwnerId());

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    public void getProductByCategoriesId(final String user, final String token, final String userId,
                                         final String homeLat, final String homeLong, final String cateId) {
        // Instantiate the RequestQueue.
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
                    object.put("Type", "1");
                    object.put("Command", "getProductsNearbyByCategory");
                    object.put("UserName", user);
                    object.put("Token", token);
                    object.put("UserId", userId);
                    object.put("HomeLatitude", homeLat);
                    object.put("HomeLongitude", homeLong);
                    object.put("CategoryId", cateId);
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
//        menu.clear();
//        inflater.inflate(R.menu.main_menu, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
//        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
//        MenuItemCompat.setActionView(item, searchView);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ArrayList<Subject> list = new ArrayList<>();
////                for (Subject subject : listSubjectsTemp) {
////                    //chuyen text thanh chu hoa
////                    newText=newText.toUpperCase();
////                    //chuan hoa truoc khi so sanh
////                    String temp=subject.getName();
////                    //chuyen tieng viet co dau thanh khong dau
////                    temp=StringUtils.unAccent(temp);
////                    if(temp.contains(newText)){
////                        list.add(subject);
////                    }
////                }
////                listSubjects.clear();
////                listSubjects=list;
////                checkAz=true;
////                sortByName();
////                adapterListSubject.setSubject(listSubjects);
////                adapterListSubject.notifyDataSetChanged();
//                return false;
//            }
//        });
//        searchView.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View v) {
//                                          }
//                                      }
//        );
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


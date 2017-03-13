package xmart.com.xmart_android.fragment.user.Order;

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

import javax.security.auth.Subject;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.activity.user.main.OrderFormActivity;
import xmart.com.xmart_android.activity.user.order.OrderDetail;
import xmart.com.xmart_android.adapter.user.order.AdapterOrder;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.db.Order;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.service.NguoiDungService;

import static xmart.com.xmart_android.R.id.listSubjectHits;

/**
 * Created by LehongphuongCntt on 2/25/2017.
 */


public class OrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static Integer page;

    private ArrayList<Order> arrayList = new ArrayList<>();
    private AdapterOrder adapterOrder;
    private RecyclerView recyclerView;

    private NguoiDungService nguoiDungService;
    private NguoiDung nguoiDung;


    public OrderFragment() {
        // Required empty public constructor

    }

    public static OrderFragment newInstance(String param1, String param2, int num) {
        OrderFragment fragment = new OrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_oder, container, false);


        nguoiDungService = new NguoiDungService(getContext());
        nguoiDung = nguoiDungService.selectAllNguoiDung().get(0);


        recyclerView = (RecyclerView) view.findViewById(listSubjectHits);

        registerForContextMenu(recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //chia lay out thanh tung box nho voi nhau
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapterOrder = new AdapterOrder(getContext());
        recyclerView.setAdapter(adapterOrder);

        adapterOrder.setData(arrayList);
        getOrderItem(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString());

        recyclerView.addOnItemTouchListener(new OrderFormActivity.RecyclerTouchListener(getActivity(), recyclerView, new OrderFormActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //update history when click
                Order order = arrayList.get(position);
                Intent intent = new Intent(getContext(), OrderDetail.class);
                intent.putExtra("orderId", order.getId());
                intent.putExtra("trangThai", "Đã đặt");
                intent.putExtra("tenShop", order.getShopName());
                intent.putExtra("position", position);
                intent.putExtra("tongTien", order.getTotal());

                intent.putExtra("ngayXuLy", order.getProcessed());
                intent.putExtra("ngayHuy", order.getCancelled());
                intent.putExtra("ngayHoanThanh", order.getCompleted());

                startActivityForResult(intent, 1);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            int i = data.getIntExtra("position", -1);
            if (i != -1) {
                arrayList.remove(i);
                adapterOrder.notifyDataSetChanged();
            }
        }
    }


    public void getOrderItem(final String user, final String token, final String userId) {
        // Instantiate the RequestQueue.
        L.m("That bai" + userId);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://xapp.codew.net/api/orders.php";
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
                                    L.m("ans");
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    Order order = new Order();
                                    order.setId(jsonObject1.getString("Id"));
                                    order.setShopName(jsonObject1.getString("ShopName"));
                                    order.setOrdered(jsonObject1.getString("Ordered"));
                                    order.setTotal(jsonObject1.getString("Total"));
                                    order.setProcessed(jsonObject1.getString("Processed"));
                                    order.setCancelled(jsonObject1.getString("Canceled"));
                                    order.setCompleted(jsonObject1.getString("Completed"));
                                    arrayList.add(order);
                                }
                                L.m("size array lit " + arrayList.size());
                                adapterOrder.setData(arrayList);

                            } else {
                                ((OrderFormActivity) getActivity()).login();
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
                    object.put("Command", "userGetOrdered");
                    object.put("UserName", user);
                    object.put("Token", token);
                    object.put("UserId", userId);
                    object.put("Page", "1");
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.order_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((OrderFormActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Subject> list = new ArrayList<>();
//                for (Subject subject : listSubjectsTemp) {
//                    //chuyen text thanh chu hoa
//                    newText=newText.toUpperCase();
//                    //chuan hoa truoc khi so sanh
//                    String temp=subject.getName();
//                    //chuyen tieng viet co dau thanh khong dau
//                    temp=StringUtils.unAccent(temp);
//                    if(temp.contains(newText)){
//                        list.add(subject);
//                    }
//                }
//                listSubjects.clear();
//                listSubjects=list;
//                checkAz=true;
//                sortByName();
//                adapterListSubject.setSubject(listSubjects);
//                adapterListSubject.notifyDataSetChanged();
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


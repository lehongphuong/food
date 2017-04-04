package xmart.com.xmart_android.fragment.owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import java.util.ArrayList;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.activity.owner.main.OwnerMainActivity;
import xmart.com.xmart_android.activity.owner.order.CancelDetailOwner;
import xmart.com.xmart_android.activity.user.main.OrderFormActivity;
import xmart.com.xmart_android.adapter.owner.AdapterOwnerCancel;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.db.OrderOwner;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.service.NguoiDungService;


public class OwnerCancelFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static Integer page;

    private ArrayList<OrderOwner> arrayList = new ArrayList<>();
    private AdapterOwnerCancel adapterCancel;
    private RecyclerView recyclerView;

    private NguoiDungService nguoiDungService;
    private NguoiDung nguoiDung;


    public OwnerCancelFragment() {
        // Required empty public constructor

    }

    public static OwnerCancelFragment newInstance(String param1, String param2, int num) {
        OwnerCancelFragment fragment = new OwnerCancelFragment();
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
        View view = inflater.inflate(R.layout.fragment_cancel, container, false);


        nguoiDungService = new NguoiDungService(getContext());
        nguoiDung = nguoiDungService.selectAllNguoiDung().get(0);


        recyclerView = (RecyclerView) view.findViewById(R.id.listSubjectHits);

        registerForContextMenu(recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //chia lay out thanh tung box nho voi nhau
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapterCancel = new AdapterOwnerCancel(getContext());
        recyclerView.setAdapter(adapterCancel);

        adapterCancel.setData(arrayList);


        recyclerView.addOnItemTouchListener(new OrderFormActivity.RecyclerTouchListener(getActivity(), recyclerView, new OrderFormActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //update history when click
                OrderOwner order = arrayList.get(position);
                Intent intent = new Intent(getContext(), CancelDetailOwner.class);
                intent.putExtra("order", order);


                startActivityForResult(intent, 1);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        L.m("cancel");
        getCancelItem(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            int i = data.getIntExtra("position", -1);
            if (i != -1) {
                arrayList.remove(i);
                adapterCancel.notifyDataSetChanged();
            }
        }
    }

        public void getCancelItem(final String user, final String token, final String ownerId) {
            // Instantiate the RequestQueue.
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
                                //kiem tra co loi khong
                                String errorLogic = jsonObject.getString("errorLogic");
                                String errorSQL = jsonObject.getString("errorSQL");
                                if (errorLogic.length() == 0) {
                                    //get array tu data jsonobject
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        OrderOwner order = new OrderOwner();
                                        order.setId(jsonObject1.getString("Id"));
                                        order.setUserId(jsonObject1.getString("UserId"));
                                        order.setOwnerId(jsonObject1.getString("OwnerId"));
                                        order.setTotal(jsonObject1.getString("Total"));
                                        order.setStatus(jsonObject1.getString("Status"));
                                        order.setLastName(jsonObject1.getString("LastName"));
                                        order.setPhoneNumber(jsonObject1.getString("PhoneNumber"));
                                        order.setHomeAddr(jsonObject1.getString("HomeAddr"));
                                        order.setWorkAddr(jsonObject1.getString("WorkAddr"));
                                        order.setGender(jsonObject1.getString("Gender"));
                                        order.setFirstName(jsonObject1.getString("FirstName"));
                                        order.setOrdered(jsonObject1.getString("Ordered"));
                                        order.setProcessed(jsonObject1.getString("Processed"));
                                        order.setCanceled(jsonObject1.getString("Canceled"));
                                        order.setCompleted(jsonObject1.getString("Completed"));
                                        order.setShopName(jsonObject1.getString("ShopName"));

                                        arrayList.add(order);

                                    }
                                    adapterCancel.setData(arrayList);

                                } else {
                                    L.T(getContext(),"Có ai đó đăng nhập vào tài khoản của bạn...");
                                    ((OwnerMainActivity) getActivity()).login();
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
                        object.put("Command", "ownerGetCanceled");
                        object.put("UserName", user);
                        object.put("Token", token);
                        object.put("OwnerId", ownerId);
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

}




package xmart.com.xmart_android.adapter.user.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gc.materialdesign.views.ButtonFloatSmall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.activity.user.main.MainActivity;
import xmart.com.xmart_android.db.CartItem;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.db.ProductNew;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.service.NguoiDungService;

/**
 * Created by LehongphuongCntt on 2/26/2017.
 */


public class AdapterCartItem extends RecyclerView.Adapter<AdapterCartItem.ViewHolderListSubject> {

    private ArrayList<CartItem> cartItemArrayList = new ArrayList<>();
    private ArrayList<ProductNew> listProduct = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private int previousPosition = 0;
    private final static int FADE_DURATION = 1000; // in milliseconds
    public Context context;

    private NguoiDungService nguoiDungService;
    private NguoiDung nguoiDung;
    private String idProduct="";
    private int sum=0;

    public AdapterCartItem(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        nguoiDungService = new NguoiDungService(context);
        nguoiDung = nguoiDungService.selectAllNguoiDung().get(0);
    }

    //tao set du lieu cho rycyvlew
    public void setData(ArrayList<CartItem> list) {
        this.cartItemArrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderListSubject onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_row_cart_item, parent, false);
        ViewHolderListSubject viewHolder = new ViewHolderListSubject(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderListSubject holder, int position) {
        // anh xa gia tri cho moi phan tu cua recycle view
        CartItem cartItem = cartItemArrayList.get(position);

        //get image from url
        String url=cartItem.getImage().replace("../","http://xapp.codew.net/");
        new  DownloadImageTask(holder.image).execute(url);

        holder.name.setText(cartItem.getProductName());
        holder.owner.setText(cartItem.getShopName());
        holder.price.setText(cartItem.getPrice() + " VND");
        holder.quantity.setText(cartItem.getQuantity());
        Integer p=Integer.parseInt(cartItem.getPrice());
        Integer q=Integer.parseInt(cartItem.getQuantity());
        sum+=p*q;
//        ((CartItemActivity)context).updateTotal(sum);
//        L.m("tong tien cua cart la "+sum);
//        setAnimation(holder.itemView, position);
    }



    //class set image
    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bmImage.setImageBitmap(result);
        }
    }

    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục drawable).
    public int getDrawableResIdByName(String resName) {
        String pkgName = context.getPackageName();
        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
        Log.i("CustomListView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

    @Override
    public int getItemCount() {
        return cartItemArrayList.size();
    }


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        //position > lastPosition
        if (true) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(600);//to make duration random number between [0,501)new Random().nextInt(1001)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    //clear problem animation
    @Override
    public void onViewDetachedFromWindow(ViewHolderListSubject holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    class ViewHolderListSubject extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView image;
        ButtonFloatSmall delete;
        ButtonFloatSmall remove;
        ButtonFloatSmall add;
        TextView name;
        TextView owner;
        TextView price;
        TextView quantity;

        public ViewHolderListSubject(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageSP);
            delete = (ButtonFloatSmall) itemView.findViewById(R.id.delete);
            remove = (ButtonFloatSmall) itemView.findViewById(R.id.remove);
            add = (ButtonFloatSmall) itemView.findViewById(R.id.add);
            name = (TextView) itemView.findViewById(R.id.nameSp1);
            owner = (TextView) itemView.findViewById(R.id.owner);
            price = (TextView) itemView.findViewById(R.id.priceSp);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            delete.setOnClickListener(this);
            remove.setOnClickListener(this);
            add.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (view.getId() == delete.getId()) {
                //ham get adapterPosition chi get duoc mot mot lan dau tien thoi nha
                int position = getAdapterPosition();
                idProduct=cartItemArrayList.get(position).getId();
                deleteItem(position);
                deleteItemInCart(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString(), position);
            } else {
                //remove click
                if (view.getId() == remove.getId()) {
                    Integer ans = Integer.parseInt(quantity.getText().toString());
//                    L.t(view.getContext(), "remove click " + String.valueOf(getAdapterPosition() + " " + ans));
                    if (ans > 0) {
                        int position = getAdapterPosition();
                        updateCartItem(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString(), String.valueOf(ans - 1), position);
                        quantity.setText("" + (ans - 1));
                    }
                } else {
                    //add click
                    if (view.getId() == add.getId()) {
                        int position = getAdapterPosition();
                        Integer ans = Integer.parseInt(quantity.getText().toString());
                        updateCartItem(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString(), String.valueOf(ans + 1), position);
                        quantity.setText("" + (ans + 1));
                    } else {
                        //item click
//                        L.t(view.getContext(), "item click " + String.valueOf(getAdapterPosition()));
                    }
                }
            }
        }
        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    public void updateBadge(){
        ((MainActivity)context).updateBadge(nguoiDung.getUserName(), nguoiDung.getToken(), nguoiDung.getId().toString());

    }

    /**
     * delete item in cart (detete logic)
     *
     * @param position
     */
    public void deleteItem(int position) {
        //remove item and have animation slow
        cartItemArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartItemArrayList.size());
        //remove not animation very quick and not beautifull
//        this.notifyDataSetChanged();
    }

    /**
     * update quantity of item in cart
     *
     * @param user
     * @param token
     * @param userId
     */
    public void updateCartItem(final String user, final String token, final String userId, final String quantity, final int position) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://xapp.codew.net/api/cartItems.php";
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

                            } else {
//                                L.T(getApplicationContext(), "Có ai đó đăng nhập tài khoản của bạn..");
//                                startActivity(new Intent(CartItemActivity.this, UserLoginActivity.class));
//                                finish();
//                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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
                    object.put("Command", "userEditQuantityItemsInCart");
                    object.put("UserName", user);
                    object.put("Token", token);
                    object.put("UserId", userId);
                    object.put("Quantity", quantity);

                    object.put("Id", cartItemArrayList.get(position).getId());
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
     * remove item in cart
     *
     * @param user
     * @param token
     * @param userId
     * @param position
     */
    public void deleteItemInCart(final String user, final String token, final String userId, final int position) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://xapp.codew.net/api/cartItems.php";
        L.m("delete ");
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
//
//                            String errorLogic = jsonObject.getString("errorLogic");
//                            String errorSQL = jsonObject.getString("errorSQL");
//                            L.m("kiem tra remove item in cart " + errorLogic + " " + errorSQL);

                            //update badge afer delete
//                            updateBadge();
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
                    object.put("Command", "userRemoveItemsInCart");
                    object.put("UserName", user);
                    object.put("Token", token);
                    object.put("UserId", userId);
                    object.put("Id", idProduct);
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

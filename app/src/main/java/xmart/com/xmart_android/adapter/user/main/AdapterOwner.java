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

import java.io.InputStream;
import java.util.ArrayList;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.db.Owner;

/**
 * Created by LehongphuongCntt on 3/12/2017.
 */

public class AdapterOwner extends RecyclerView.Adapter<AdapterOwner.ViewHolderBoxOffice> {

    private ArrayList<Owner> listMovies = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private int previousPosition = 0;
    private final static int FADE_DURATION = 1000; // in milliseconds
    Context context;

    public AdapterOwner(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context=context;
    }

    //tao set du lieu cho rycyvlew
    public void setData(ArrayList<Owner> list) {
        this.listMovies = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_row_list_owner, parent, false);
        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderBoxOffice holder, int position) {
        // anh xa gia tri cho moi phan tu cua recycle view
        Owner onwer = listMovies.get(position);

        //get image from url
        String url=onwer.getImage().replace("../","http://xapp.codew.net/");
        new DownloadImageTask(holder.image).execute(url);
        holder.shopName.setText(onwer.getShopName());
        holder.address.setText(onwer.getAddress());
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
        return listMovies.size();
    }

    //clear problem animation
    @Override
    public void onViewDetachedFromWindow(ViewHolderBoxOffice holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }



    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView shopName;
        TextView address;



        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_owner);
            shopName = (TextView) itemView.findViewById(R.id.shop_name);
            address = (TextView) itemView.findViewById(R.id.address);


        }

        @Override
        public void onClick(View v) {

        }
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
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(700);//to make duration random number between [0,501)new Random().nextInt(1001)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }
}

package xmart.com.xmart_android.adapter.owner;

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
import xmart.com.xmart_android.db.OrderOwner;
import xmart.com.xmart_android.logging.L;


public class AdapterOwnerCancel extends RecyclerView.Adapter<AdapterOwnerCancel.ViewHolderListSubject> {

    private final static int FADE_DURATION = 1000; // in milliseconds
    public ArrayList<OrderOwner> listSubject = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private int previousPosition = 0;
    private int lastPosition = -1;
    private Context context;

    public AdapterOwnerCancel(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<OrderOwner> listSubject) {
        this.listSubject = listSubject;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderListSubject onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_row_owner_cancel, parent, false);
        ViewHolderListSubject viewHolder = new ViewHolderListSubject(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderListSubject holder, final int position) {
        // anh xa gia tri cho moi phan tu cua recycle view
        final OrderOwner order = listSubject.get(position);
        holder.nameCustom.setText(order.getFirstName()+" "+order.getLastName());
        holder.dateOrder.setText(order.getOrdered());
        holder.dateCancel.setText(order.getCanceled());
        holder.amount.setText(order.getTotal());

        //set animation
        setAnimation(holder.itemView, position);
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
        L.m("get count "+listSubject.size());
        return listSubject.size();
//        return 3;
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
//        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        anim.setDuration(701);//to make duration random number between [0,501)new Random().nextInt(1001)
//        viewToAnimate.startAnimation(anim);
        //position > lastPosition
        if (true) {
            //sp4lit
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(701);//to make duration random number between [0,501)new Random().nextInt(1001)
            viewToAnimate.startAnimation(anim);
//            lastPosition = position;
            //tu trai qua
//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
        } else {
//            setFadeAnimation(viewToAnimate);
            lastPosition = position;
        }
    }

    //clear problem animation
    @Override
    public void onViewDetachedFromWindow(ViewHolderListSubject holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    static class ViewHolderListSubject extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        //        public ImageView linkImageSubject, favourite;
//        public TextView nameSubject, numSentence;
//        TextView movieTitle;
        public TextView nameCustom, dateOrder,dateCancel, amount;


        public ViewHolderListSubject(View itemView) {
            super(itemView);
            nameCustom = (TextView) itemView.findViewById(R.id.name_custom);
            dateOrder = (TextView) itemView.findViewById(R.id.date_order);
            dateOrder = (TextView) itemView.findViewById(R.id.date_order);
            dateCancel = (TextView) itemView.findViewById(R.id.date_cancel);
            amount = (TextView) itemView.findViewById(R.id.amount);
//            nameSubject = (TextView) itemView.findViewById(R.id.nameSubject);
//            numSentence = (TextView) itemView.findViewById(R.id.numSentence);
//            linkImageSubject = (ImageView) itemView.findViewById(R.id.linkImageSubject);
//            favourite = (ImageView) itemView.findViewById(R.id.favourite);
//
//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
//            favourite.setOnClickListener(this);
//            favourite.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (view.getId()==favourite.getId()){
////                AdapterListSubject
////                DataSubject currentSubject = view.listSubject.get(getAdapterPosition());
////                L.t(view.getContext(),"favourite click " +String.valueOf(getAdapterPosition()));
//
//            }else{
//
////                int idsub=Integer.parseInt(listSubjects.get(position).getIdsub());
////                updateHistory(idsub-1);
////                updateNotSeen(idsub-1);
////                Intent intent = new Intent(getActivity(), SubActivity.class);
////                intent.putExtra("idsub", listSubjects.get(position).getIdsub());
////                intent.putExtra("nameSubject", listSubjects.get(position).getNameSubject());
////                startActivity(intent);
////                L.t(view.getContext(),"row click "+view.getId());
//            }
        }

        @Override
        public boolean onLongClick(View view) {
//            if (view.getId()==favourite.getId()){
//                int imageIdFavourite;
//                favourite.setImageResource(R.drawable.ic_favourite_true);
////                if(checkFavourite==1){
////                    imageIdFavourite = getDrawableResIdByName("ic_favourite_true");
////                }else{
////                    imageIdFavourite = getDrawableResIdByName("ic_favourite_false");
////                }
////                igvFavourite.setImageResource(imageIdFavourite);
////                AdapterListSubject
////                DataSubject currentSubject = view.listSubject.get(getAdapterPosition());
////                L.t(view.getContext(),"favourite click " +String.valueOf(getAdapterPosition()));
//
//            }else{
//
//            }
            return false;
        }
    }

}


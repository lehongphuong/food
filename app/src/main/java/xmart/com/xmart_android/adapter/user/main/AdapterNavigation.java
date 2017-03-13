package xmart.com.xmart_android.adapter.user.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.data.Information;

public class AdapterNavigation extends RecyclerView.Adapter<AdapterNavigation.ViewHolderNavigation>{
    List<Information> data= Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public AdapterNavigation(Context context, List<Information> data){
        this.context=context;
        this.inflater= LayoutInflater.from(context);
        this.data=data;
    }
    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolderNavigation onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row, parent,false);
        ViewHolderNavigation holder=new ViewHolderNavigation(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderNavigation holder, int position) {
        Information current=data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolderNavigation extends RecyclerView.ViewHolder{
        TextView title;
        ImageView icon;
        public ViewHolderNavigation(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.listText);
            icon= (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }
}

package com.example.kustevents;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolderClass>
{
    private Context mContext;
    private List<ModelClass> list;

    public CustomAdapter(Context context, List<ModelClass> alist)
    {
        mContext = context;
        list = alist;
    }

    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_recyclerview, viewGroup, false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder( ViewHolderClass viewHolderClass, int i) {
        ModelClass uploadObject = list.get(i);
        viewHolderClass.Message.setText(uploadObject.getMessage());
        viewHolderClass.Date.setText(uploadObject.getDate());
        Picasso.get().load(uploadObject.getImageUri())
                .fit().placeholder(R.drawable.upload_image_icon)
                .into(viewHolderClass.Image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder
    {
        TextView Message, Date;
        ImageView Image;
        View mview;

        public ViewHolderClass(View itemView) {
            super(itemView);
            mview = itemView;
            Message = mview.findViewById(R.id.textView_msg);
            Date = mview.findViewById(R.id.date);
            Image = mview.findViewById(R.id.imageRecieved);
        }
    }
}

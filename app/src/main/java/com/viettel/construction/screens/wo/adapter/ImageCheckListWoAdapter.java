package com.viettel.construction.screens.wo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.viettel.construction.R;
import com.viettel.construction.model.api.wo.ImgChecklistDTO;
import com.viettel.construction.server.util.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ramona on 2/21/2018.
 */

public class ImageCheckListWoAdapter extends RecyclerView.Adapter<ImageCheckListWoAdapter.ImageViewHolder> {
    private List<ImgChecklistDTO> mList;
    private Context mContext;
    private RequestOptions requestOptions;

    public ImageCheckListWoAdapter(List<ImgChecklistDTO> mList, Context context) {

        this.mList = mList;
        this.mContext = context;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.img_default);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.img_default);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_image_from_url, parent, false);
        return new ImageViewHolder(view);
    }

    public void loadImageFromUrl(String base64, ImageView imageView) {

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .asBitmap()
                .load(StringUtil.setImage(base64))
                .into(imageView);
    }


    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
//        ConstructionImageInfo image = (String) mList.get(position);
        //  1 : is server =======  image name is filePath of image
        Glide.with(mContext).setDefaultRequestOptions(requestOptions.override(200, 200)).asBitmap().load(StringUtil.setImage(mList.get(position).getImgBase64())).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                holder.IVItem.setImageBitmap(resource);
                holder.IVItem.setClickable(true);
            }
        });

        holder.IVDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null || mList.size() == 0 ? 0 : mList.size();
    }

    public void setData(List<ImgChecklistDTO> mData){
        this.mList = mData;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item)
        ImageView IVItem;
        @BindView(R.id.btn_image_delete)
        ImageView IVDelete;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

package com.viettel.construction.screens.wo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.viettel.construction.R;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.model.api.wo.WoMappingChecklistDTO;
import com.viettel.construction.screens.wrac.BillAdapter;
import com.viettel.construction.server.util.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ramona on 2/21/2018.
 */

public class SpinnerWoAdapter extends BaseAdapter {

    private List<AppParamDTO> lstAppParamDTO;
    private Context mContext;
    private LayoutInflater inflater;

    public SpinnerWoAdapter(List<AppParamDTO> mList, Context context) {
        this.mContext = context;
        this.lstAppParamDTO = mList;
        inflater = (LayoutInflater.from(mContext));

    }

    public void setData(List<AppParamDTO> mData){
        this.lstAppParamDTO = mData;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return lstAppParamDTO == null || lstAppParamDTO.size() == 0 ? 0 : lstAppParamDTO.size();
    }

    @Override
    public Object getItem(int i) {
        return lstAppParamDTO.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_spinner_wo, null);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvNumber = view.findViewById(R.id.tv_number);
        tvNumber.setText(String.valueOf(i + 1));
        tvName.setText(lstAppParamDTO.get(i).getName());
        return view;
    }

//
//    public interface CallbackInterface{
//
//        void onSelectedItem(String code);
//    }

}

package com.viettel.construction.appbase;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;


/***
 * KePham
 * @param <T>
 * @param <VM>
 */
public abstract class AdapterFragmentSwipeBase<T,VM extends ViewModelFragmentListBase> extends RecyclerSwipeAdapter<VM> {

    protected Context context;
    private List<T> listData;


    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }

    protected IItemRecyclerviewClick<T> itemRecyclerviewClick;

    public AdapterFragmentSwipeBase(Context context, List<T> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public VM onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(VM holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface IItemRecyclerviewClick<T> {
        void onItemRecyclerViewclick(T item);
        void onItemRecyclerViewLongclick(T item);
    }
}

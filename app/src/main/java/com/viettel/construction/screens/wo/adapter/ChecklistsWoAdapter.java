package com.viettel.construction.screens.wo.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.wo.WoMappingChecklistDTO;

import java.util.ArrayList;
import java.util.List;

public class ChecklistsWoAdapter extends RecyclerView.Adapter<ChecklistsWoAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<WoMappingChecklistDTO> list;
    private String state;
    private CallbackInterface callbackInterface;


    public ChecklistsWoAdapter(Context context, List<WoMappingChecklistDTO> list, String state, CallbackInterface callback) {
        this.context = context;
        this.list = list;
        this.state = state;
        this.callbackInterface = callback;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ChecklistsWoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_checklists_wo, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(list.get(position), state);
        holder.itemView.setOnClickListener(view -> {
            callbackInterface.onHandleSelection(list.get(position), position);
        });
//        holder.mAdapter.setData(lstImgChecklist);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setListData(List<WoMappingChecklistDTO> mData){
        list = mData;
        notifyDataSetChanged();
    }


//    private List<String> lstImgChecklist = new ArrayList<>();

//    public void setListDataImgs(List<String> lstString, int post){
//        lstImgChecklist = lstString;
////        notifyItemChanged(post);
//        notifyItemInserted(post);
//
//    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCode;
//        private ImageView btnCamera;
//        private Spinner spStatus;
//        private RecyclerView rvData;
        private TextView txtStatus;
//        private String[] itemStatus = {"Mới", "Hoàn thành"};
//
//        private List<String> mList = new ArrayList<>();
//        private ImageCheckListWoAdapter mAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCode = itemView.findViewById(R.id.txtCode);
            txtStatus = itemView.findViewById(R.id.txtStatus);
//            btnCamera = itemView.findViewById(R.id.btn_camera);
//            spStatus = itemView.findViewById(R.id.spStatus);
//            rvData = itemView.findViewById(R.id.rv_data);
//            btnCamera.setOnClickListener(view -> {
////                if (checkRuntimePermission()) {
////                    onLaunchCamera();
////                }
//            });
        }

        public void bindData(WoMappingChecklistDTO item, String state){
            if (item != null){
//                btnCamera.setEnabled(state == VConstant.StateWO.Processing ? false : true);
                txtCode.setText(item.getChecklistName());
                switch (item.getState()){
                    case "NEW":
                        txtStatus.setText("Mới");
                        break;
                    case "DONE":
                        txtStatus.setText("Kết thúc");
                        break;
                        default:
                            txtStatus.setText("Mới");
                            break;
                }
//                spStatus.setSelection(item.getState() == "NEW" ? 0 : 1);
//                codeSpinner(itemStatus, spStatus, item);
//                initAdapterImage();
            }
        }

//        private void initAdapterImage(){
//            mAdapter = new ImageCheckListWoAdapter(mList, context);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            rvData.setLayoutManager(linearLayoutManager);
//            rvData.setAdapter(mAdapter);
//        }

//        private void codeSpinner(String[] item, Spinner spinner, WoMappingChecklistDTO dto){
//            ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, item );
//            langAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//            spinner.setAdapter(langAdapter);
//
//            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                    if (adapterView.getItemAtPosition(position).toString().equals("Mới")){
//                        dto.setState("NEW");
//                    }else {
//                        dto.setState("DONE");
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
//        }
    }

    public interface CallbackInterface{

        void onHandleSelection(WoMappingChecklistDTO dto, int position);
    }

}

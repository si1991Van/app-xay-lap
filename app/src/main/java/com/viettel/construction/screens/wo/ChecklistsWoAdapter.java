package com.viettel.construction.screens.wo;


import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.common.GPSTracker;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.plan.WoDTOResponse;
import com.viettel.construction.model.api.wo.ImgeChecklistDTO;
import com.viettel.construction.model.api.wo.WoMappingChecklistDTO;
import com.viettel.construction.screens.atemp.adapter.ImageAppParamAdapter;
import com.viettel.construction.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.viettel.construction.appbase.BaseCameraActivity.PERMISSION_REQUEST_CODE;

public class ChecklistsWoAdapter extends RecyclerView.Adapter<ChecklistsWoAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<WoMappingChecklistDTO> list;
    private List<String> lstImg = new ArrayList<>();
    private String state;
    private CallbackInterface callbackInterface;
    private int post;
    private String path;
//    private ViewHolder viewHolder;


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
//        viewHolder = holder;
        holder.bindData(list.get(position), state);
        holder.btnCamera.setOnClickListener(view -> {
            callbackInterface.onHandleSelection(position);
        });
        if (path == null) return;
        if (post == position) {
            lstImg.clear();
            for (ImgeChecklistDTO dto: lstImgChecklist) {
                if (dto.getPostion() == position ){
                    lstImg.add(dto.getStringBase64());
                    list.get(position).setLstImgs(lstImg);
                }
            }
            holder.mAdapter.setData(list.get(position).getLstImgs());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setListData(List<WoMappingChecklistDTO> mData){
        list = mData;
        notifyDataSetChanged();
    }

    private ImgeChecklistDTO imgeChecklistDTO = new ImgeChecklistDTO();
    private List<ImgeChecklistDTO> lstImgChecklist = new ArrayList<>();

    public void setListDataImgs(String path, int pos){
        this.post = pos;
        this.path = path;
        imgeChecklistDTO.setPostion(pos);
        imgeChecklistDTO.setStringBase64(path);
        lstImgChecklist.add(imgeChecklistDTO);
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCode;
        private ImageView btnCamera;
        private Spinner spStatus;
        private RecyclerView rvData;
        private String[] itemStatus = {"Mới", "Hoàn thành"};

        private List<String> mList = new ArrayList<>();
        private ImageCheckListWoAdapter mAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCode = itemView.findViewById(R.id.txtCode);
            btnCamera = itemView.findViewById(R.id.btn_camera);
            spStatus = itemView.findViewById(R.id.spStatus);
            rvData = itemView.findViewById(R.id.rv_data);
            btnCamera.setOnClickListener(view -> {
//                if (checkRuntimePermission()) {
//                    onLaunchCamera();
//                }
            });
        }

        public void bindData(WoMappingChecklistDTO item, String state){
            if (item != null){
                btnCamera.setEnabled(state == VConstant.StateWO.Processing ? false : true);
                txtCode.setText(item.getChecklistName());
                spStatus.setSelection(item.getState() == "NEW" ? 0 : 1);
                codeSpinner(itemStatus, spStatus, item);
                initAdapterImage();
            }
        }

        private void initAdapterImage(){
            mAdapter = new ImageCheckListWoAdapter(mList, context);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvData.setLayoutManager(linearLayoutManager);
            rvData.setAdapter(mAdapter);
        }

        private void codeSpinner(String[] item, Spinner spinner, WoMappingChecklistDTO dto){
            ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, item );
            langAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(langAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (adapterView.getItemAtPosition(position).toString().equals("Mới")){
                        dto.setState("NEW");
                    }else {
                        dto.setState("DONE");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    public interface CallbackInterface{

        void onHandleSelection(int pos);
    }

}

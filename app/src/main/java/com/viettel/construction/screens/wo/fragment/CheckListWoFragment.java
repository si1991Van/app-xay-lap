package com.viettel.construction.screens.wo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.plan.WoDTORequest;
import com.viettel.construction.model.api.plan.WoDTOResponse;
import com.viettel.construction.model.api.wo.WoMappingChecklistDTO;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.screens.plan.UpdateImageCheckListWoActivity;
import com.viettel.construction.screens.wo.adapter.ChecklistsWoAdapter;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckListWoFragment extends Fragment {

    @Nullable
    @BindView(R.id.progressBar)
    public CustomProgress progressBar;

    @Nullable
    @BindView(R.id.rcvData)
    public RecyclerView rcvData;

    @Nullable
    @BindView(R.id.btnSave)
    public Button btnSave;

    private WoDTO woDTO;
    private WoDTORequest woDTORequest = new WoDTORequest();
    private List<WoMappingChecklistDTO> lstChecklistsOfWo = new ArrayList<>();
    private WoMappingChecklistDTO woMappingChecklistDTO;
    private ChecklistsWoAdapter adapter;


    public  CheckListWoFragment(WoDTO dto)  {
        super();
        this.woDTO = dto;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checklist_wo, container, false);
        ButterKnife.bind(this, view);

        btnSave.setVisibility(woDTO.getState().equals(VConstant.StateWO.Processing) ? View.VISIBLE : View.GONE);
        initAdapterCheckLists();
        getCheckListWo();
        return view;
    }



    private void initAdapterCheckLists(){
        adapter = new ChecklistsWoAdapter(getContext(), lstChecklistsOfWo, woDTO.getState(), (object, position) -> {
            woMappingChecklistDTO = object;
            Intent intent = new Intent(getContext(), UpdateImageCheckListWoActivity.class);
            intent.putExtra("WoMappingChecklistDTO", woMappingChecklistDTO);
            intent.putExtra("State", woDTO.getState());
//            intent.putExtra("Position", position);
            startActivityForResult(intent, 123);

        });
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rcvData.setLayoutManager(linearLayoutManager2);
        rcvData.setAdapter(adapter);
    }

    private void getCheckListWo(){
        woDTORequest.setSysUserRequest(VConstant.getUser());
        woDTORequest.setWoDTO(woDTO);
        ApiManager.getInstance().checkListWo(woDTORequest, WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    WoDTOResponse response = WoDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (response.getLstChecklistsOfWo() != null){
                            lstChecklistsOfWo = response.getLstChecklistsOfWo();
                            adapter.setListData(lstChecklistsOfWo);
                        }
                    } else {
                        Toast.makeText(getContext(), getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getContext(), getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCheckList(){
        woDTORequest.setLstChecklistsOfWo(lstChecklistsOfWo);
        ApiManager.getInstance().updateCheckListWo(woDTORequest, WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    WoDTOResponse response = WoDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(getContext(), getString(R.string.update_checklist_success), Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.update_checklist_fail), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                }
                progressBar.setLoading(false);
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getContext(), getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                progressBar.setLoading(false);
            }
        });

    }

    @OnClick(R.id.btnSave)
    public void onClickSave(){
        updateCheckList();
    }
    
}

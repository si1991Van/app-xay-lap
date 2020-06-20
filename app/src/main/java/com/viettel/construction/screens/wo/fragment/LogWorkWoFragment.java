package com.viettel.construction.screens.wo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.viettel.construction.model.api.wo.WoWorkLogsBO;
import com.viettel.construction.screens.plan.UpdateImageCheckListWoActivity;
import com.viettel.construction.screens.wo.CheckListWOActivity;
import com.viettel.construction.screens.wo.adapter.ChecklistsWoAdapter;
import com.viettel.construction.screens.wo.adapter.WorkLogsWoAdapter;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogWorkWoFragment extends Fragment {


    @BindView(R.id.rcvData)
    RecyclerView recyclerView;
    @BindView(R.id.txtNoData)
    TextView txtNoData;


    private WoDTO woDTO;
    private WoDTORequest woDTORequest = new WoDTORequest();
    private List<WoWorkLogsBO> list = new ArrayList<>();
    private WorkLogsWoAdapter adapter;

    public  LogWorkWoFragment(WoDTO dto)  {
        super();
        this.woDTO = dto;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_logs_wo, container, false);
        ButterKnife.bind(this, view);
        initAdapter();
        getListWorklogs();
        return view;
    }


    private void initAdapter(){
        adapter = new WorkLogsWoAdapter(getContext(), list);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager2);
        recyclerView.setAdapter(adapter);
    }


    private void getListWorklogs(){
        woDTORequest.setSysUserRequest(VConstant.getUser());
        woDTORequest.setWoDTO(woDTO);
        ApiManager.getInstance().listWorklogs(woDTORequest, WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                WoDTOResponse response = WoDTOResponse.class.cast(result);
                if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    if (response.getLogs() != null){
                        txtNoData.setVisibility(View.GONE);
                        adapter.setData(response.getLogs());
                    }else {
                        txtNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getContext(), getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
            }
        });


    }



}

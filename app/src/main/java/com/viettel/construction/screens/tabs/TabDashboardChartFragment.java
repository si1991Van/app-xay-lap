package com.viettel.construction.screens.tabs;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.ResultApiBgmbDashBoard;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.model.api.ConstructionMerchandiseDTOResponse;
import com.viettel.construction.model.api.ConstructionScheduleDTOResponse;
import com.viettel.construction.model.api.ConstructionScheduleItemDTO;
import com.viettel.construction.model.api.CountConstructionTaskDTO;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.plan.WoDTOResponse;
import com.viettel.construction.screens.menu_acceptance.AcceptanceLevel1Fragment;
import com.viettel.construction.screens.menu_bgmb.TabPageBGMBFragment;
import com.viettel.construction.screens.menu_construction.TabPageConstructionFragment;
import com.viettel.construction.screens.menu_ex_warehouse.TabPageExWareHouseFragment;
import com.viettel.construction.screens.plan.PlanningItemFragment;
import com.viettel.construction.screens.wo.WOItemFragment;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Màn hình Dashboard
 */
public class TabDashboardChartFragment extends BaseChartFragment {

    private final String TAG = "VTTabDashboard";
    public static int CheckbgmgReceived;
    public static int CheckbgmbNotReceived;

    @BindView(R.id.chartBGMB)
    PieChart chartBGMB;

    @BindView(R.id.chartCongViec)
    PieChart chartCongViec;
    @BindView(R.id.chartXuatKho)
    PieChart chartXuatKho;
    //    @BindView(R.id.chartVTTB)
//    PieChart chartVTTB;
    @BindView(R.id.chartHangMuc)
    PieChart chartHangMuc;

    @BindView(R.id.txt_bgmb_received)
    TextView txtBGMBReceived;
    @BindView(R.id.txt_bgmb_notreceived)
    TextView txtBGMBNotReceived;

    @BindView(R.id.on_schedule_description)
    TextView txtOnSchedule;
    @BindView(R.id.behind_schedule_description)
    TextView txtBehindSchedule;
    @BindView(R.id.txt_received)
    TextView txtReceived;
    @BindView(R.id.txt_also_receive)
    TextView txtAlsoReceive;
    @BindView(R.id.txt_rejected_chart)
    TextView txtRejected;
    @BindView(R.id.txt_month_current)
    TextView txtMonth;

    @BindView(R.id.tv_total_acceptance)
    TextView txtTotalAcceptance;

    @BindView(R.id.tv_already_acceptance)
    TextView txtAlreadyAcceptance;

    @BindView(R.id.tv_have_not_acceptance)
    TextView txtHaveNotAcceptance;

//    @BindView(R.id.txt_returned)
//    TextView txtReturn;
//    @BindView(R.id.txt_not_return)
//    TextView txtNotReturn;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.txtUnImplemented)
    TextView txtUnImplemented;

    @BindView(R.id.txtPending)
    TextView txtPending;

    @BindView(R.id.txtInProgress)
    TextView txtInProgress;

    @BindView(R.id.txtCompleted)
    TextView txtCompleted;
    @BindView(R.id.txtWoAssignFT)
    TextView txtWoAssignFT;
    @BindView(R.id.txtRejectFt)
    TextView txtRejectFt;

    @BindView(R.id.chartWO)
    PieChart chartWO;
    @BindView(R.id.txtWoAcceptFt)
    TextView txtWoAcceptFt;
    @BindView(R.id.txtWoNg)
    TextView txtWoNg;
    @BindView(R.id.txtWoProcessing)
    TextView txtWoProcessing;
    @BindView(R.id.txtWoDone)
    TextView txtWoDone;

    @BindView(R.id.chartPlaning)
    PieChart chartPlaning;
    @BindView(R.id.txt_plan_done)
    TextView txt_plan_done;
    @BindView(R.id.txt_plan_ng)
    TextView txt_plan_ng;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        txtHeader.setText("Dashboard");
        if (getActivity() != null)
            getActivity().registerReceiver(receiverInitData,
                    new IntentFilter(ParramConstant.DashBoardReload));
        return view;
    }

    private BroadcastReceiver receiverInitData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                Log.d(TAG,"onReceive");
                initDataForChart();
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtOnSchedule.setText(getActivity().getString(R.string.on_schedule, 0));
        txtBehindSchedule.setText(getActivity().getString(R.string.behind_schedule, 0));

        chartCongViec.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                clickChartCongViec();
            }

            @Override
            public void onNothingSelected() {
                clickChartCongViec();
            }
        });

        chartBGMB.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                clickChartBGMB();
            }

            @Override
            public void onNothingSelected() {
                clickChartBGMB();
            }
        });

//        chartVTTB.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                clickChartVTTB();
//            }
//
//            @Override
//            public void onNothingSelected() {
//                clickChartVTTB();
//            }
//        });

        chartXuatKho.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                clickChartXuatKho();
            }

            @Override
            public void onNothingSelected() {
                clickChartXuatKho();
            }
        });

        chartHangMuc.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                clickHangMuc();
            }

            @Override
            public void onNothingSelected() {
                clickHangMuc();
            }
        });

        chartWO.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                clickWO();
            }

            @Override
            public void onNothingSelected() {
                clickWO();
            }
        });
        chartPlaning.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                clickPlaning();
            }

            @Override
            public void onNothingSelected() {
                clickPlaning();
            }
        });

        //
        initDataForChart();
    }

    @OnClick(R.id.imgRefresh)
    public void onRefresh() {
        initDataForChart();
    }


    @OnClick(R.id.lnBgmb)
    public void clickChartBGMB() {
        commitChange(new TabPageBGMBFragment(), true);

    }


    @OnClick(R.id.lnCongViec)
    public void clickChartCongViec() {
        commitChange(new DashboardCV1ChartFragment(), true);
//        commitChange(new PlanningItemFragment(), true);
    }

//    @OnClick(R.id.lnVTTB)
//    public void clickChartVTTB() {
////        commitChange(new ListRefundLever1ChartFragment(), true);
//        commitChange(new RefundInventoryLevel1Fragment(), true);
//    }

    @OnClick(R.id.lnNghiemThu)
    public void clickChartNghiemThu() {
//        commitChange(new WAcceptanceLevel1ChartFragment(), true);
        commitChange(new AcceptanceLevel1Fragment(), true);
    }


    @OnClick(R.id.lnXuatKho)
    public void clickChartXuatKho() {
        commitChange(new TabPageExWareHouseFragment(), true);
    }

    @OnClick(R.id.lnHangMuc)
    public void clickHangMuc() {
        commitChange(new TabPageConstructionFragment(), true);
    }
    @OnClick(R.id.lnWO)
    public void clickWO() {
        // di den man hinh danh sach WO
        commitChange(new WOItemFragment(), true);
    }

    @OnClick(R.id.lnPlaning)
    public void clickPlaning() {
        commitChange(new PlanningItemFragment(), true);
    }

    private void initDataForChart() {
        Log.d(TAG,"initDataForChart");
        //Dashboard Thi cong
        ApiManager.getInstance().categoryCountDashboard(ConstructionScheduleDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ConstructionScheduleDTOResponse resultApi = ConstructionScheduleDTOResponse.class.cast(result);
                    ResultInfo resultInfo = resultApi.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {

                        List<ConstructionScheduleItemDTO> data = resultApi.getListConstructionScheduleItemDTO();
                        if (data != null && data.size() > 0) {
                            ConstructionScheduleItemDTO obj = data.get(0);
                            if (obj != null) {
                                List<Integer> listData = new ArrayList<>();
                                listData.add(obj.getPerUnImplemented());//chua thuc hien
                                listData.add(obj.getPerImplemented());//Dang thuc hien
                                listData.add(obj.getPerComplete());//hoan thanh
                                listData.add(obj.getPerStop());//tam dung
                                Activity activity = getActivity();
                                if (activity != null && isAdded()) {
                                    txtUnImplemented.setText(getActivity().getString(R.string.incompleteTC, obj.getPerUnImplemented()));
                                }
                                if (getActivity() != null) {
                                    txtInProgress.setText(getActivity().getString(R.string.in_progressTC, obj.getPerImplemented()));
                                }
                                if (getActivity() != null) {
                                    txtPending.setText(getActivity().getString(R.string.pauseTC, obj.getPerStop()));
                                }
                                if (getActivity() != null) {
                                    txtCompleted.setText(getActivity().getString(R.string.completeTC, obj.getPerComplete()));
                                }
                                initChart4(listData, chartHangMuc, 0f, 0f, mParties4, 4, false);
                            }
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });

        //Dashboard Hshc, doanh thu
        ApiManager.getInstance().getCompleteStateConstructionTask(ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    List<Integer> list = new ArrayList<>();
                    ResultApi resultApi = ResultApi.class.cast(result);
                    ResultInfo resultInfo = resultApi.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        list.clear();
                            CountConstructionTaskDTO countConstructionTaskDTO = resultApi.getCountConstructionTaskDTO();
                        int slowProcess = countConstructionTaskDTO.getSlowProcess();
                        int fastProcess = countConstructionTaskDTO.getFastProcess();
                        list.add(fastProcess);
                        list.add(slowProcess);

                        Activity activity = getActivity();
                        if (activity != null && isAdded()) {
                            txtOnSchedule.setText(getActivity().getString(R.string.on_schedule, fastProcess));
                        }
                        if (getActivity() != null) {
                            txtBehindSchedule.setText(getActivity().getString(R.string.behind_schedule, slowProcess));
                            initChartCongViec(list, chartCongViec, 0, 0, mParties, 2, false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });


        //Dashboard BGMB
        ApiManager.getInstance().getConstructionBGMBStatus(ResultApiBgmbDashBoard.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    List<Integer> list = new ArrayList<>();
                    ResultApiBgmbDashBoard resultApi = ResultApiBgmbDashBoard.class.cast(result);
                    ResultInfo resultInfo = resultApi.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        list.clear();
                        int bgmgReceived = resultApi.getTotalRecordReceived();
                        int bgmbNotReceived =resultApi.getTotalRecordNotReceived();
                        CheckbgmgReceived = bgmgReceived;
                        CheckbgmbNotReceived = bgmbNotReceived;
                        Log.d(TAG, "bgmgReceived : " + bgmgReceived + "- bgmbNotReceived:  " + bgmbNotReceived);
                        list.add(bgmgReceived);
                        list.add(bgmbNotReceived);

                        Activity activity = getActivity();
                        if (activity != null && isAdded()) {
                            txtBGMBReceived.setText(getActivity().getString(R.string.BGMB_received, bgmgReceived));
                        }
                        if (getActivity() != null) {
                            txtBGMBNotReceived.setText(getActivity().getString(R.string.BGMB_not_received, bgmbNotReceived));

                            initChartBGMB(list, chartBGMB, 0, 0, mParties5, 2, false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });


        //Dashboard phiếu xuất kho
        ApiManager.getInstance().getCountDeliveryBill(ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    List<Integer> list = new ArrayList<>();
                    ResultApi resultApi = ResultApi.class.cast(result);
                    ResultInfo resultInfo = resultApi.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        list.clear();
                        Log.e("isOK", "OK");
                        if (resultApi.getCountStockTrans() != null) {
                            CountConstructionTaskDTO countConstructionTaskDTO = resultApi.getCountStockTrans();
                            int datiepnhan = countConstructionTaskDTO.getDatiepnhan();
                            int chotiepnhan = countConstructionTaskDTO.getChotiepnhan();
                            int datuchoi = countConstructionTaskDTO.getDatuchoi();
                            Log.e("check", datiepnhan + " " + chotiepnhan + " " + datuchoi + "");
                            list.add(datiepnhan);
                            list.add(chotiepnhan);
                            list.add(datuchoi);
                            txtReceived.setText("Đã tiếp nhận (" + datiepnhan + ")");
                            txtAlsoReceive.setText("Chờ tiếp nhận (" + chotiepnhan + ")");
                            txtRejected.setText("Đã từ chối (" + datuchoi + ")");
                            initChartXuatKho(list, chartXuatKho, 0f, 0f, mParties2, 3, false);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(int statusCode) {

            }
        });

        //Nghiệm thu - THANGTV24 - start
        ApiManager.getInstance().callDataForAcceptanceChart(ConstructionAcceptanceDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ConstructionAcceptanceDTOResponse resultApi = ConstructionAcceptanceDTOResponse.class.cast(result);
                    ResultInfo resultInfo = resultApi.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Activity activity = getActivity();
                        Log.d(TAG,"callDataForAcceptanceChart - getTotalConstructionAcceptance : " + resultApi.getTotalConstructionAcceptance() +
                        " - resultApi.getNumberConstructionAcceptance() : " + resultApi.getNumberConstructionAcceptance()
                        + "- resultApi.getNumberNoConstructionAcceptance() : " + resultApi.getNumberNoConstructionAcceptance());
                        if (activity != null && isAdded()) {
                            txtTotalAcceptance.setText(resultApi.getTotalConstructionAcceptance() + "");
                            txtAlreadyAcceptance.setText(resultApi.getNumberConstructionAcceptance() + "");
                            txtHaveNotAcceptance.setText(resultApi.getNumberNoConstructionAcceptance() + "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });

        // Hoàn trả VTTB
        ApiManager.getInstance().getValuePercentRefund(ConstructionMerchandiseDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    List<Integer> list = new ArrayList<>();
                    ConstructionMerchandiseDTOResponse response = ConstructionMerchandiseDTOResponse.class.cast(result);
                    ResultInfo resultInfo = response.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Activity activity = getActivity();
                        if (activity != null && isAdded()) {
                            int numberReturn = response.getNumberConstructionReturn();
                            int numberNotReturn = response.getNumberNoConstructionReturn();

                            list.add(numberReturn);
                            list.add(numberNotReturn);

//                            txtReturn.setText("Đã hoàn trả (" + numberReturn + ")");
//                            txtNotReturn.setText("Chưa hoàn trả (" + numberNotReturn + ")");

//                            initChartVTTB(list, chartVTTB, 0f, 0f, mParties3, 2, false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });

        getChartWO();

    }



    private void getChartWO(){
        ApiManager.getInstance().getDataForChart(WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    List<Integer> list = new ArrayList<>();
                    List<Integer> listPlan = new ArrayList<>();
                    WoDTOResponse response = WoDTOResponse.class.cast(result);
                    ResultInfo resultInfo = response.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        list.clear();
                        listPlan.clear();
                        if (response.getMapDataWoForChart() != null){
                            list.add(response.getMapDataWoForChart().getASSIGN_FT());
                            list.add(response.getMapDataWoForChart().getACCEPT_FT());
                            list.add(response.getMapDataWoForChart().getREJECT_FT());
                            list.add(response.getMapDataWoForChart().getPROCESSING());
                            list.add(response.getMapDataWoForChart().getOK());
                            list.add(response.getMapDataWoForChart().getNG());
                            txtWoAssignFT.setText(getActivity().getString(R.string.on_wo_assign_ft, response.getMapDataWoForChart().getASSIGN_FT()));
                            txtWoAcceptFt.setText(getActivity().getString(R.string.on_wo_accept_ft, response.getMapDataWoForChart().getACCEPT_FT()));
                            txtRejectFt.setText(getActivity().getString(R.string.on_wo_reject_ft, response.getMapDataWoForChart().getREJECT_FT()));
                            txtWoProcessing.setText(getActivity().getString(R.string.on_wo_processing, response.getMapDataWoForChart().getPROCESSING()));
                            txtWoDone.setText(getActivity().getString(R.string.on_wo_ok, response.getMapDataWoForChart().getOK()));
                            txtWoNg.setText(getActivity().getString(R.string.on_wo_ng, response.getMapDataWoForChart().getNG()));
                            initChart4(list, chartWO, 0f, 0f, mPartiesWO, 6, false);
                        }
                        if (response.getMapDataWoPlanForChart() != null){
                            listPlan.add(response.getMapDataWoPlanForChart().getUNDONE());
                            listPlan.add(response.getMapDataWoPlanForChart().getDONE());

                            txt_plan_ng.setText(getActivity().getString(R.string.on_plan_undone, response.getMapDataWoPlanForChart().getUNDONE()));
                            txt_plan_done.setText(getActivity().getString(R.string.on_plan_done, response.getMapDataWoPlanForChart().getDONE()));

                            initChartBGMB(listPlan, chartPlaning, 0, 0, mParties5, 2, false);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Tag: ", "co loi");
                }
            }

            @Override
            public void onError(int statusCode) {
                Log.e("Tag: ", statusCode + "");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (getActivity() != null)
                getActivity().unregisterReceiver(receiverInitData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

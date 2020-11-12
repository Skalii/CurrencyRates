package skalii.testjob.db2limited.ui.fragment;


import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import skalii.testjob.db2limited.R;
import skalii.testjob.db2limited.data.remote.RemoteApiNBU;
import skalii.testjob.db2limited.data.remote.RemoteServiceNBU;
import skalii.testjob.db2limited.data.util.CallbackOperation;

import static skalii.testjob.db2limited.ui.util.ComponentHelper.clickOnCalendar;
import static skalii.testjob.db2limited.ui.util.ComponentHelper.setDateText;


public class BottomGraph extends BottomSheetDialogFragment {

    private final Calendar calendarFrom = Calendar.getInstance();
    private final Calendar calendarBy = Calendar.getInstance();

    private GraphView graph;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        calendarFrom.setTime(Calendar.getInstance().getTime());
        calendarBy.setTime(Calendar.getInstance().getTime());
        super.onDestroyView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_graph, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        graph = view.findViewById(R.id.graph);
        TextView textFrom = view.findViewById(R.id.text_date_from);
        TextView textBy = view.findViewById(R.id.text_date_by);
        CardView cardViewFrom = view.findViewById(R.id.card_calendar_from);
        CardView cardViewBy = view.findViewById(R.id.card_calendar_by);

        setDateText(calendarFrom, textFrom);
        setDateText(calendarBy, textBy);

        clickOnCalendar(getContext(), calendarFrom, cardViewFrom, () -> {
            setDateText(calendarFrom, textFrom);
            refreshGraph();
        });

        clickOnCalendar(getContext(), calendarBy, cardViewBy, () -> {
            setDateText(calendarBy, textBy);
            refreshGraph();
        });

        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    private void refreshGraph() {

        graph.removeAllSeries();
        graph.setVisibility(View.VISIBLE);

        RemoteApiNBU remoteApiNBU = RemoteServiceNBU.getInstance().getApi();

        Calendar calendarFromClone = (Calendar) calendarFrom.clone();
        Calendar calendarByClone = (Calendar) calendarBy.clone();
        calendarByClone.add(Calendar.DATE, 1);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return new SimpleDateFormat("dd.MM.yy").format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });

//        graph.getGridLabelRenderer().setHighlightZeroLines(false);
//        graph.getGridLabelRenderer().setLabelsSpace(30);
        graph.getGridLabelRenderer().setGridColor(getResources().getColor(R.color.color_text));
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.BOTH);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(135);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Даты");
        graph.getGridLabelRenderer().setHorizontalAxisTitleColor(getResources().getColor(R.color.color_accent));
        graph.getGridLabelRenderer().setHorizontalLabelsColor(getResources().getColor(R.color.color_accent));
        graph.getGridLabelRenderer().setVerticalAxisTitle("UAH");
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(getResources().getColor(R.color.color_accent));
        graph.getGridLabelRenderer().setVerticalLabelsColor(getResources().getColor(R.color.color_accent));
        graph.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.LEFT);
        graph.getGridLabelRenderer().setVerticalLabelsVAlign(GridLabelRenderer.VerticalLabelsVAlign.ABOVE);
        graph.getGridLabelRenderer().reloadStyles();

        graph.getViewport().setBorderColor(getResources().getColor(R.color.color_text));
        graph.getViewport().setXAxisBoundsManual(false);
        graph.getViewport().setMinX(calendarFrom.getTime().toInstant().toEpochMilli());
        graph.getViewport().setMaxX(calendarByClone.getTime().toInstant().toEpochMilli());
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
//        graph.getViewport().setYAxisBoundsManual(false);
//        graph.getViewport().setScrollableY(true);
//        graph.getViewport().setScalableY(true);

//        final double[] y = new double[]{999.99, 0.0};

        new Handler().post(() -> {
            while (calendarFromClone.before(calendarByClone)) {

                Calendar calendarFixedDate = (Calendar) calendarFromClone.clone();

                remoteApiNBU
                        .getExchangeRates(new SimpleDateFormat("yyyyMMdd").format(calendarFromClone.getTime()), "USD")
                        .enqueue(new CallbackOperation<>((list) -> {

                            /*y[0] = Double.min(y[0], list.get(0).getFirstValue());
                              y[1] = Double.max(y[1], list.get(0).getFirstValue());
                              if (list.get(0).getDate().equals(new SimpleDateFormat("dd.MM.yyyy").format(calendarBy.getTime()))) {
                                  graph.getViewport().setMinY(y[0]);
                                  graph.getViewport().setMaxY(y[1]);
                              }*/

                            try {
                                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{new DataPoint(calendarFixedDate.getTime(), list.get(0).getFirstValue())});
                                series.setColor(getResources().getColor(R.color.color_accent));
                                series.setBackgroundColor(getResources().getColor(R.color.color_text_accent));
                                series.setDrawDataPoints(true);
                                series.setAnimated(true);
                                series.setDataPointsRadius(8f);
                                graph.addSeries(series);
                                graph.refreshDrawableState();
                            } catch (NullPointerException | IndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }

                        }));

                calendarFromClone.add(Calendar.DATE, 1);

            }
        });

    }

}
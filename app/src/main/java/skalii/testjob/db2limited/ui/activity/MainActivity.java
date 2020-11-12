package skalii.testjob.db2limited.ui.activity;


import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import skalii.testjob.db2limited.R;
import skalii.testjob.db2limited.data.remote.RemoteApiPB;
import skalii.testjob.db2limited.data.remote.RemoteServiceNBU;
import skalii.testjob.db2limited.data.remote.RemoteServicePB;
import skalii.testjob.db2limited.data.util.CallbackOperation;
import skalii.testjob.db2limited.ui.fragment.BottomGraph;

import static skalii.testjob.db2limited.ui.util.ComponentHelper.addRows;
import static skalii.testjob.db2limited.ui.util.ComponentHelper.clickOnCalendar;
import static skalii.testjob.db2limited.ui.util.ComponentHelper.setDateText;


public class MainActivity extends AppCompatActivity {

    private final Calendar calendarPB = Calendar.getInstance();
    private final Calendar calendarNBU = Calendar.getInstance();

    private boolean isInitTablePB = false;
    private boolean isInitTableNBU = false;

    private TableLayout tablePB;
    private TableLayout tableNBU;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomGraph bottomGraph;


    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color_accent)));

        swipeRefreshLayout = findViewById(R.id.swipe_page_exchange_rates);
        bottomGraph = new BottomGraph();

        ((TextView) findViewById(R.id.block_pb_title).findViewById(R.id.text_bank_title)).setText(R.string.title_pb);
        ((TextView) findViewById(R.id.block_nbu_title).findViewById(R.id.text_bank_title)).setText(R.string.title_nbu);

        ConstraintLayout blockPBTitle = findViewById(R.id.block_pb_title);
        CardView cardViewPB = blockPBTitle.findViewById(R.id.card_calendar);
        setDateText(calendarPB, blockPBTitle.findViewById(R.id.text_date));
        tablePB = findViewById(R.id.table_exchange_rates_pb);

        ConstraintLayout blockNBUTitle = findViewById(R.id.block_nbu_title);
        CardView cardViewNBU = blockNBUTitle.findViewById(R.id.card_calendar);
        setDateText(calendarNBU, blockNBUTitle.findViewById(R.id.text_date));
        tableNBU = findViewById(R.id.table_exchange_rates_nbu);

        swipeRefreshLayout.post(() -> {
            swipeRefreshLayout.setRefreshing(true);
            refreshTablePB(null);
            refreshTableNBU();
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            refreshTablePB(new SimpleDateFormat("dd.MM.yyyy").format(calendarPB.getTime()));
            refreshTableNBU();
        });

        clickOnCalendar(this, calendarPB, cardViewPB, () -> {
            swipeRefreshLayout.post(() -> {
                swipeRefreshLayout.setRefreshing(true);
                setDateText(calendarPB, cardViewPB.findViewById(R.id.text_date));
                refreshTablePB(new SimpleDateFormat("dd.MM.yyyy").format(calendarPB.getTime()));
            });
        });

        clickOnCalendar(this, calendarNBU, cardViewNBU, () -> {
            swipeRefreshLayout.post(() -> {
                swipeRefreshLayout.setRefreshing(true);
                setDateText(calendarNBU, cardViewNBU.findViewById(R.id.text_date));
                refreshTableNBU();
            });
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.option_graph == item.getItemId()) {
            bottomGraph.show(getSupportFragmentManager(), bottomGraph.getTag());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void refreshTablePB(String date) {
        isInitTablePB = false;

        RemoteApiPB remoteApiPB = RemoteServicePB.getInstance().getApi();

        if (date != null && !date.equals(new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()))) {
            remoteApiPB.getExchangeRates(date)
                    .enqueue(new CallbackOperation<>((list) -> {
                        tablePB.removeViews(1, tablePB.getChildCount() - 1);
                        addRows(this, tablePB, tableNBU, R.layout.table_row_exchange_pb, list.getList());
                        isInitTablePB = true;
                        if (isInitTableNBU) swipeRefreshLayout.setRefreshing(false);
                    }));
        } else {
            remoteApiPB.getExchangeRates()
                    .enqueue(new CallbackOperation<>((list) -> {
                        tablePB.removeViews(1, tablePB.getChildCount() - 1);
                        addRows(this, tablePB, tableNBU, R.layout.table_row_exchange_pb, list);
                        isInitTablePB = true;
                        if (isInitTableNBU) swipeRefreshLayout.setRefreshing(false);
                    }));
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void refreshTableNBU() {
        isInitTableNBU = false;
        RemoteServiceNBU
                .getInstance()
                .getApi()
                .getExchangeRates(new SimpleDateFormat("dd.MM.yyyy").format(calendarNBU.getTime()))
                .enqueue(new CallbackOperation<>((list) -> {
                    tableNBU.removeAllViews();
                    addRows(this, tableNBU, tablePB, R.layout.table_row_exchange_nbu, list);
                    isInitTableNBU = true;
                    if (isInitTablePB) swipeRefreshLayout.setRefreshing(false);
                }));
    }

}
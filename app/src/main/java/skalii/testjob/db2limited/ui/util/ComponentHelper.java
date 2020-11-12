package skalii.testjob.db2limited.ui.util;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import skalii.testjob.db2limited.R;
import skalii.testjob.db2limited.data.model.CurrencyBase;
import skalii.testjob.db2limited.data.util.ExpressionRun;
import skalii.testjob.db2limited.data.type.CurrencyType;


public class ComponentHelper {

    @SuppressLint("SimpleDateFormat")
    public static void setDateText(Calendar calendar, TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.color_accent));
        textView.setText(
                new SimpleDateFormat("dd.MM.yyyy")
                        .format(calendar.getTime())
        );
    }

    public static void clickOnCalendar(Context context,
                                       Calendar calendar,
                                       View view,
                                       ExpressionRun expressionRun) {

        DatePickerDialog.OnDateSetListener date = (v, year, month, day) -> {
            calendar.set(year, month, day);
            expressionRun.run();
        };

        view.setOnClickListener(v -> new DatePickerDialog(
                context,
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        )
                .show());

    }

    public static <RowType extends CurrencyBase> void addRows(Context context,
                                                              TableLayout tableLayout,
                                                              TableLayout anotherTableLayout,
                                                              @LayoutRes int tableRowRes,
                                                              List<RowType> data
    ) {
        LayoutInflater inflater = LayoutInflater.from(context);

        for (RowType row : data) {

            TableRow tableRow = (TableRow) inflater.inflate(tableRowRes, null);

            try {
                if ((row.getFirstValue() == 0 && row.getSecondValue() == 0)
                        || row.getCurrencyType() == null
                        || row.getCurrencyType() == CurrencyType.UAH) {
                    continue;
                }

                if (row.getCurrencyType() == CurrencyType.RUR) {
                    row.setCurrencyType(CurrencyType.RUB);
                } else if (row.getCurrencyType() == CurrencyType.PLZ) {
                    row.setCurrencyType(CurrencyType.PLN);
                }

                ((TextView) tableRow.findViewById(R.id.col_currency)).setText(row.getCurrencyType().getTitle());
            } catch (Exception e) {
                Log.d("EXCEPTION CURRENCY", e.getLocalizedMessage());
                break;
            }

            tableRow.setTag(row.getCurrencyType().getTitle());

            String firstValue = Double.toString(row.getFirstValue());
            if (firstValue.substring(firstValue.indexOf(".") + 1, firstValue.length() - 1).length() > 1)
                firstValue = firstValue.substring(0, firstValue.indexOf(".") + 3);
            else if (firstValue.endsWith(".0"))
                firstValue = firstValue.substring(0, firstValue.indexOf("."));

            String secondValue = Double.toString(row.getSecondValue());
            if (secondValue.substring(secondValue.indexOf(".") + 1, secondValue.length() - 1).length() > 1)
                secondValue = secondValue.substring(0, secondValue.indexOf(".") + 3);
            else if (secondValue.endsWith(".0"))
                secondValue = secondValue.substring(0, secondValue.indexOf("."));

            ((TextView) tableRow.findViewById(R.id.col_first_value)).setText(firstValue);
            ((TextView) tableRow.findViewById(R.id.col_second_value)).setText(secondValue);

            tableRow.setOnClickListener((l) -> {

                for (int i = 0; i < tableLayout.getChildCount(); i++) {
                    tableLayout.getChildAt(i).setPressed(false);
                }

                for (int i = 0; i < anotherTableLayout.getChildCount(); i++) {
                    anotherTableLayout.getChildAt(i).setPressed(false);
                }

                setFocusOnAnotherTable(row.getCurrencyType().getTitle(), anotherTableLayout);
            });

            tableLayout.addView(tableRow);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    public static void setFocusOnAnotherTable(String currency, TableLayout tableLayout) {
        try {

            TableRow tableRow = tableLayout.findViewWithTag(currency);

            Rect rectangle = new Rect(0, 0, tableRow.getWidth(), tableRow.getHeight());
            tableRow.getDrawingRect(rectangle);
            tableRow.requestRectangleOnScreen(rectangle);

            tableRow.setPressed(true);

        } catch (NullPointerException ignored) {
        }

    }

}
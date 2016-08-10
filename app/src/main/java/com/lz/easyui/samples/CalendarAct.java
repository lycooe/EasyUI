package com.lz.easyui.samples;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.lz.easyui.activity.LibraryBaseActivity;
import com.lz.easyui.widget.timessquare.CalendarCellDecorator;
import com.lz.easyui.widget.timessquare.CalendarCellView;
import com.lz.easyui.widget.timessquare.CalendarPickerView;
import com.lz.easyui.widget.timessquare.DefaultDayViewAdapter;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public class CalendarAct extends LibraryBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_calendar);


        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 3);
        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.act_calendar_view);

        calendar.setCustomDayView(new DefaultDayViewAdapter());
        calendar.setDecorators(Arrays.<CalendarCellDecorator>asList(new SampleDecorator()));
        calendar.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
                if (dateFormat.format(date).equals("2016年6月28日") || dateFormat.format(date).equals("2016年7月2日") || dateFormat.format(date).equals("2016年7月5日") || dateFormat.format(date).equals("2016年7月6日")){
                    return false;
                }
                return true;
            }
        });
        calendar.init(new Date(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                .withSelectedDate(new Date());

    }

    @Override
    protected void initHeader() {

    }

    @Override
    protected void initWidget() {

    }

    @Override
    protected void setWidgetState() {

    }

    class SampleDecorator implements CalendarCellDecorator {
        @Override
        public void decorate(CalendarCellView cellView, Date date, boolean selectable) {
            String title = "";
            if (selectable){
                title = "title";
            }

            String dateString = Integer.toString(date.getDate());
            SpannableString string = new SpannableString(dateString + "\n"+title);
            string.setSpan(new RelativeSizeSpan(0.6f), dateString.length(), string.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            cellView.getDayOfMonthTextView().setText(string);
        }
    }

}

package com.lewis.magicalui.samples;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lewis.magicalui.activity.LibraryBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends LibraryBaseActivity {

    @Bind(R.id.testid)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        textView.setText("adfasdfasdfasdfasdfasd");


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

    @OnClick(R.id.calender_btn)
    public void calenderClick(){
        startActivity(new Intent(getApplicationContext(), CalendarAct.class));
    }


}

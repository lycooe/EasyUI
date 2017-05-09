package com.lewis.easyui.samples;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lewis.easyui.activity.LibraryBaseActivity;
import com.lewis.easyui.event.BaseEvent;
import com.lewis.easyui.util.EasyLog;
import com.lewis.easyui.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends LibraryBaseActivity {

    @Bind(R.id.testid)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);

        getExtra("key", new TestEvent());

        textView.setText("adfasdfasdfasdfasdfasd");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new TestEvent());
            }
        });

    }

    @Override
    protected void initHeader() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setWidgetState() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TestEvent event) {
        EasyLog.d("aaaaaaaaaaaaaaaaaaa");
        ToastUtil.toast("aaaaaaaaaaaaaaaaaaa");

    }

    @OnClick(R.id.calender_btn)
    public void calenderClick() {
        startActivity(new Intent(getApplicationContext(), CalendarAct.class));
    }


}

package com.lz.easyui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lz.easyui.EasyUI;
import com.lz.easyui.util.RelayoutViewTool;
import com.lz.easyui.util.UIDialogUtil;
import com.lz.easyui.R;

/**
 * 转轮
 */
public class LibraryProgressBar {

    private static LibraryProgressBar libraryProgressBar;

    private LibraryProgressBar() {
    }

    public static synchronized LibraryProgressBar getInstance() {
        if (libraryProgressBar == null) {
            libraryProgressBar = new LibraryProgressBar();
        }
        return libraryProgressBar;
    }


    private Dialog progressBarDialog;

    /**
     * 启动加载进度条
     *
     * @param message   提示文字int 不接收String
     * @param canCancel 是否可关闭进度条状态
     * @param canFinish 是否可关闭当前Activity
     */
    public synchronized void startProgressBar(final Activity act, String message, final boolean canCancel, final boolean canFinish) {
        startProgressBar(act, message, canCancel, canFinish, false);
    }

    /**
     * 启动加载进度条
     *
     * @param message   提示文字int 不接收String
     * @param canCancel 是否可关闭进度条状态
     * @param canFinish 是否可关闭当前Activity
     * @param relayout  是否使用适配
     */
    public synchronized void startProgressBar(final Activity act, String message, final boolean canCancel, final boolean canFinish, boolean relayout) {
        if (progressBarDialog != null && progressBarDialog.isShowing())
            return;

        View view = View.inflate(act, R.layout.library_common_dialog_progressbar, null);
        if (relayout) {
            RelayoutViewTool.relayoutViewWithScale(view, EasyUI.screenWidthScale);
        }
        progressBarDialog = UIDialogUtil.getInstance().buildDialog(act, view, false);
        TextView titleTest = (TextView) view.findViewById(R.id.library_common_dialog_loading_txt);

        titleTest.setText(message);

        progressBarDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK && (canCancel || canFinish)) {
                    if (canFinish) {
                        act.finish();
                    }
                    closeProgressBar();
                    return true;
                }
                return false;
            }
        });
    }


    // 进度条 关
    public synchronized boolean closeProgressBar() {
        if (progressBarDialog != null && progressBarDialog.isShowing()) {
            progressBarDialog.dismiss();
            return true;
        }
        return false;
    }
}

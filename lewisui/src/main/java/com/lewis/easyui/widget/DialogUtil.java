package com.lewis.easyui.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.lewis.easyui.util.CheckTool;

public class DialogUtil {

    public static void showDialog(Context context, String title, String message, String positiveBtnText, DialogInterface.OnClickListener positiveBtnListener) {
        showDialog(context, true, title, message, positiveBtnText, null, positiveBtnListener, null, null);
    }

    public static void showDialog(Context context, String title, String message, String positiveBtnText, String negativeBtnText, DialogInterface.OnClickListener positiveBtnListener, DialogInterface.OnClickListener negativeBtnListener) {
        showDialog(context, true, title, message, positiveBtnText, negativeBtnText, positiveBtnListener, negativeBtnListener, null);
    }

    public static void showDialog(Context context, @StringRes int titleRes, @StringRes int messageRes, @StringRes int positiveBtnTextRes, DialogInterface.OnClickListener positiveBtnListener) {
        showDialog(context, true, titleRes, messageRes, positiveBtnTextRes, 0, positiveBtnListener, null, null);
    }

    public static void showDialog(Context context, @StringRes int titleRes, @StringRes int messageRes, @StringRes int positiveBtnTextRes, @StringRes int negativeBtnTextRes, DialogInterface.OnClickListener positiveBtnListener, DialogInterface.OnClickListener negativeBtnListener) {
        showDialog(context, true, titleRes, messageRes, positiveBtnTextRes, negativeBtnTextRes, positiveBtnListener, negativeBtnListener, null);
    }

    public static void showDialog(Context context, String title, String message, String positiveBtnText, DialogInterface.OnClickListener positiveBtnListener, DialogInterface.OnCancelListener cancelListener) {
        showDialog(context, true, title, message, positiveBtnText, null, positiveBtnListener, null, cancelListener);
    }

    public static void showDialog(Context context, boolean cancelable, @StringRes int titleRes, @StringRes int messageRes, @StringRes int positiveBtnTextRes, @StringRes int negativeBtnTextRes, DialogInterface.OnClickListener positiveBtnListener, DialogInterface.OnClickListener negativeBtnListener, DialogInterface.OnCancelListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleRes)
                .setPositiveButton(positiveBtnTextRes, positiveBtnListener)
                .setCancelable(cancelable)
                .setOnCancelListener(cancelListener);
        if (messageRes > 0) {
            builder.setMessage(messageRes);
        }
        if (negativeBtnTextRes > 0) {
            builder.setNegativeButton(negativeBtnTextRes, negativeBtnListener);
        }
        builder.create().show();
    }

    public static void showDialog(Context context, boolean cancelable, String title, String message, String positiveBtnText, String negativeBtnText, DialogInterface.OnClickListener positiveBtnListener, DialogInterface.OnClickListener negativeBtnListener, DialogInterface.OnCancelListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setPositiveButton(positiveBtnText, positiveBtnListener)
                .setCancelable(cancelable)
                .setOnCancelListener(cancelListener);
        if (!CheckTool.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (!CheckTool.isEmpty(negativeBtnText)) {
            builder.setNegativeButton(negativeBtnText, negativeBtnListener);
        }
        builder.create().show();
    }

    public static void showListDialog(Context context, @StringRes int titleRes, @NonNull String[] items, @NonNull final DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleRes).setItems(items, listener).create().show();
    }

    public static void showListDialog(Context context, String title, @NonNull String[] items, @NonNull final DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setItems(items, listener).create().show();
    }

    public interface OnDialogItemClickListener<T> {
        void onItemClick(int position, T string);
    }
}

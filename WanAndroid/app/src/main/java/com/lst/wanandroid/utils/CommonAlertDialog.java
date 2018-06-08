package com.lst.wanandroid.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lst.wanandroid.R;

public class CommonAlertDialog {
    private AlertDialog alertDialog;

    public static CommonAlertDialog newInstance(){
        return CommonAlertDialogHolder.COMMON_ALERT_DIALOG;
    }

    private static class CommonAlertDialogHolder{
        private static final CommonAlertDialog COMMON_ALERT_DIALOG = new CommonAlertDialog();
    }

    public void cancelDialog(boolean isAdd) {
        if (isAdd && alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    public void showDialog(Activity activity, String content, String btnContent) {
        if (activity == null) {
            return;
        }
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(activity, R.style.myCorDialog).create();
        }
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
        alertDialog.setCanceledOnTouchOutside(false);
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.common_alert_dialog);
            TextView contentTv = (TextView) window.findViewById(R.id.dialog_content);
            contentTv.setText(content);
            Button mOkBtn = (Button) window.findViewById(R.id.dialog_btn);
            mOkBtn.setText(btnContent);
            mOkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (alertDialog != null) {
                        alertDialog.cancel();
                        alertDialog = null;
                    }
                }
            });
            View btnDivider = window.findViewById(R.id.dialog_btn_divider);
            btnDivider.setVisibility(View.GONE);
            Button negativeButton = (Button) window.findViewById(R.id.dialog_negative_btn);
            negativeButton.setVisibility(View.GONE);
        }
    }

    public void showDialog(Activity activity, String content, String btContent,
                           final View.OnClickListener onClickListener) {
        if (activity == null) {
            return;
        }
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(activity, R.style.myCorDialog).create();
        }
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
        alertDialog.setCanceledOnTouchOutside(false);
        //todo:搞清楚这个window
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.common_alert_dialog);
            TextView contentTv = window.findViewById(R.id.dialog_content);
            contentTv.setText(content);
            Button mOkBt = (Button) window.findViewById(R.id.dialog_btn);
            mOkBt.setOnClickListener(onClickListener);
            View btnDivider = window.findViewById(R.id.dialog_btn_divider);
            btnDivider.setVisibility(View.GONE);
            Button mNegBtn = window.findViewById(R.id.dialog_negative_btn);
            mNegBtn.setVisibility(View.GONE);
        }
    }

    public void showDialog(Activity activity, String content, String btnContext,
                           String negContent, final View.OnClickListener okClickListenr,
                           final View.OnClickListener negClickListener) {
        if (activity == null) {
            return;
        }
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(activity, R.style.myCorDialog).create();
        }
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
        alertDialog.setCanceledOnTouchOutside(false);
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.common_alert_dialog);
            TextView contentTv = (TextView) window.findViewById(R.id.dialog_content);
            contentTv.setText(content);
            Button okBt = window.findViewById(R.id.dialog_btn);
            okBt.setText(btnContext);
            Button negBt = window.findViewById(R.id.dialog_negative_btn);
            negBt.setText(negContent);
            View btnDivider = window.findViewById(R.id.dialog_btn_divider);
            btnDivider.setVisibility(View.VISIBLE);
            negBt.setVisibility(View.VISIBLE);
            okBt.setOnClickListener(okClickListenr);
            negBt.setOnClickListener(negClickListener);
        }

    }


}

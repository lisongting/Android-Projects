package cn.ssdut.lst.easyreader.about;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.ssdut.lst.easyreader.R;
import cn.ssdut.lst.easyreader.customtabs.CustomFallback;
import cn.ssdut.lst.easyreader.customtabs.CustomTabActivityHelper;
import cn.ssdut.lst.easyreader.license.OpenSourceLicenseActivity;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by lisongting on 2017/5/6.
 */

public class AboutPresenter implements AboutContract.Presenter {
    private AboutContract.View view;
    private AppCompatActivity activity;
    private SharedPreferences sharedPreferences;
    private CustomTabsIntent.Builder customTabsIntent;

    public AboutPresenter(AppCompatActivity activity, AboutContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
        sharedPreferences = activity.getSharedPreferences("user_settings", MODE_PRIVATE);

        customTabsIntent = new CustomTabsIntent.Builder();
        customTabsIntent.setToolbarColor(activity.getResources().getColor(R.color.colorPrimary));
        customTabsIntent.setShowTitle(true);

    }
    @Override
    public void start() {

    }

    @Override
    public void rate() {
        try {
            Uri uri = Uri.parse("marcket://details?id=?" + activity.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openLicense() {
        activity.startActivity(new Intent(activity, OpenSourceLicenseActivity.class));
    }

    @Override
    public void followOnGithub() {
        if (sharedPreferences.getBoolean("in_app_browser", true)) {
            CustomTabActivityHelper.openCustomTab(
                    activity,
                    customTabsIntent.build(),
                    Uri.parse(activity.getString(R.string.github_url)),
                    new CustomFallback() {
                        @Override
                        public void openUri(Activity activity, Uri uri) {
                            super.openUri(activity, uri);
                        }
                    });
        } else {
            try {
                activity.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(activity.getString(R.string.github_url))));
            } catch (ActivityNotFoundException e) {
                view.showBrowserNotFoundError();
            }
        }
    }

    @Override
    public void followOnZhihu() {
        Toast.makeText(activity, "知乎", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void feedback() {
        try {
            Uri uri = Uri.parse(activity.getString(R.string.sendto));
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.mail_topic));
            intent.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.device_model) + Build.MODEL + "\n"
                    + activity.getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
                    + activity.getString(R.string.version));
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            view.showFeedbackError();
        }
    }

    @Override
    public void donate() {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.setTitle(R.string.donate);
        dialog.setMessage(activity.getString(R.string.donate_content));
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 将指定账号添加到剪切板
                // add the alipay account to clipboard
                ClipboardManager manager = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", activity.getString(R.string.donate_account));
                manager.setPrimaryClip(clipData);
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(R.string.negative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    long[] hits = new long[3];

    @Override
    public void showEasterEgg() {
        System.arraycopy(hits, 1, hits, 0, hits.length - 1);
        hits[hits.length - 1] = SystemClock.uptimeMillis();
        if (hits[0] >= (SystemClock.uptimeMillis() - 500)) {
            AlertDialog dialog = new AlertDialog.Builder(activity).create();
            dialog.setCancelable(false);
            dialog.setTitle(R.string.easter_egg);
            dialog.setMessage(activity.getString(R.string.easter_egg_content));
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.sure), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }
    }
}

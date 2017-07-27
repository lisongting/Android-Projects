package com.paperfish.espresso.mvp.addpackage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.paperfish.espresso.R;
import com.paperfish.espresso.realm.CaptureActivity;

import java.util.Random;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lisongting on 2017/7/27.
 */

public class AddPackageFragment extends Fragment implements AddPackageContract.View {

    public final static int SCANNING_REQUEST_CODE = 1;
    public final static int REQUEST_CAMERA_PERMISSION_CODE = 0;

    public static final String ACTION_SCAN_CODE = "com.paperfish.espresso.AddPackageActivity";

    private TextInputEditText editTextNumber,editTextName;
    private AppCompatTextView textViewScanCode;
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    private NestedScrollView scrollView;
    private AddPackageContract.Presenter presenter;
    private int[] colorRes;

    public AddPackageFragment(){}

    public static AddPackageFragment getInstance(){
        return new AddPackageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colorRes = new int[]{
                R.color.cyan_500, R.color.amber_500,
                R.color.pink_500, R.color.orange_500,
                R.color.light_blue_500, R.color.lime_500,
                R.color.green_500};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_package, container, false);
        initViews(v);

        addLayoutListener(scrollView, editTextName);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideImm();
                String name = editTextName.getText().toString();
                //replaceAll的作用应该是去除所有空格
                String number = editTextNumber.getText().toString().replaceAll("\\s*","");

                if (number.length() < 5 || number.replace(" ", "").isEmpty()) {
                    showNumberError();
                    return;
                }
                for (char c : number.toCharArray()) {
                    if (!Character.isLetterOrDigit(c)) {
                        showNumberError();
                        return;
                    }
                }

                if (name.isEmpty()) {
                    name = getString(R.string.package_name_default_pre) + number.substring(0, 4);
                }
                editTextName.setText(name);
                //保存包裹
                presenter.savePackage(editTextNumber.getText().toString(), name,
                        colorRes[new Random().nextInt(colorRes.length)]);

            }
        });
        textViewScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionOrToScan();
            }
        });

        String action = getActivity().getIntent().getAction();
        if (action != null && action.equals(ACTION_SCAN_CODE)) {
            checkPermissionOrToScan();
        }

        setHasOptionsMenu(true);

        return v;
    }

    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    //隐藏输入法管理器
    private void hideImm() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(fab.getWindowToken(), 0);
        }
    }

    private void addLayoutListener(final NestedScrollView scrollView,final TextInputEditText editTextName) {
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //调用getWindowVisibleDisplayFrame，获得当前可见的区域大小
                scrollView.getWindowVisibleDisplayFrame(rect);
                //当前不可见的高度
                int mainInvisibleHeight = scrollView.getRootView().getHeight() - rect.bottom;
                if (mainInvisibleHeight > 150) {
                    int[] location = new int[2];
                    editTextName.getLocationInWindow(location);
                    int scrollHeight = (location[1] + editTextName.getHeight()) - rect.bottom;
                    scrollView.scrollTo(0, scrollHeight);
                } else {
                    scrollView.scrollTo(0, 0);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return true;
    }

    @Override
    public void initViews(View view) {
        AddPackageActivity activity = (AddPackageActivity) getActivity();
        activity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextName = (TextInputEditText) view.findViewById(R.id.editTextName);
        editTextNumber = (TextInputEditText) view.findViewById(R.id.editTextNumber);
        textViewScanCode = (AppCompatTextView) view.findViewById(R.id.textViewScanCode);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
    }

    //打开扫描界面后，扫到而条形码后，将结果放在Bundle中回传
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNING_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (null != bundle) {
                        editTextNumber.setText(bundle.getString("result"));
                    }
                }
                break;
            default:
                break;
        }
    }

    private void checkPermissionOrToScan() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
        } else {
            //如果已经得到摄像头权限，则直接启动
            startScanningActivity();
        }
    }

    private void startScanningActivity() {
        Intent intent = new Intent(getContext(),CaptureActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,SCANNING_REQUEST_CODE);
    }

    //请求权限后，用来得知用户是否允许使用摄像头权限
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanningActivity();
            } else {
                hideImm();
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle(R.string.permission_denied)
                        .setMessage(R.string.require_permission)
                        .setPositiveButton(R.string.go_to_settings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //进入到设置界面
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.mdtp_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        }

    }

    @Override
    public void setPresenter(AddPackageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showNumberExistError() {
        Snackbar.make(fab, R.string.package_exist, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNumberError() {
        Snackbar.make(fab, R.string.wrong_number_and_check, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setProgressIndicator(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPackagesList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showNetworkError() {
        Snackbar.make(fab, R.string.network_error, Snackbar.LENGTH_SHORT)
                .setAction(R.string.go_to_settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent().setAction(Settings.ACTION_SETTINGS));
                    }
                })
                .show();
    }
}

package com.hsun.appupdater;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hsun.appupdater.databinding.AppUpdaterDialogBinding;

public class AppUpdaterDialog extends DialogFragment {

    private static Activity activity;
    private AppUpdaterDialogBinding appUpdaterDialogBinding;
    private AppUpdaterDialogViewModel appUpdaterDialogViewModel;

    public AppUpdaterDialog() {

    }

    public static AppUpdaterDialog instance(Activity activity, String updateData,
                                            AppUpdaterDialogSettings appUpdaterDialogSettings) {
        AppUpdaterDialog fragment = new AppUpdaterDialog();
        Bundle args = new Bundle();
        args.putString("updateData", updateData);
        args.putString("appUpdaterDialogSettings", appUpdaterDialogSettings.toJson());
        數字文字布林用extra view用其他種類使用static
        fragment.setArguments(args);
        AppUpdaterDialog.activity = activity;
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (null != getDialog().getWindow())
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        View view = inflater.inflate(R.layout.app_updater_dialog, container);

        appUpdaterDialogBinding =
                DataBindingUtil.inflate(inflater, R.layout.app_updater_dialog, container, false);
        appUpdaterDialogViewModel = new AppUpdaterDialogViewModel(activity, appUpdaterDialogBinding);
        appUpdaterDialogViewModel.setListener(new AppUpdaterDialogViewModel.Listener() {
            @Override
            public void onClose() {
                dismiss();
            }
        });
        appUpdaterDialogViewModel.setUpdateData(JsonUpdateData.parse(getArguments().getString("updateData")));
        appUpdaterDialogBinding.setViewModel(appUpdaterDialogViewModel);
        appUpdaterDialogViewModel.setAppUpdaterDialogSettings(new AppUpdaterDialogSettings()
                .parse(getArguments().getString("appUpdaterDialogSettings")));

        UtilAnimation.setRotateInfinite(appUpdaterDialogBinding.headerContainer.imgLogo);

        setCancelable(false);

        return appUpdaterDialogBinding.getRoot();
    }

    public void show() {
        show(activity.getFragmentManager(), "appUpdater");
    }
}

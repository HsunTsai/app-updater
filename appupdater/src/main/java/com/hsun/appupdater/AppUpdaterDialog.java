package com.hsun.appupdater;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
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
        appUpdaterDialogViewModel = new AppUpdaterDialogViewModel(activity);
        appUpdaterDialogViewModel.setListener(new AppUpdaterDialogViewModel.Listener() {
            @Override
            public void onClose() {
                dismiss();
            }
        });
        appUpdaterDialogViewModel.setUpdateData(JsonUpdateData.parse(getArguments().getString("updateData")));
        appUpdaterDialogViewModel.setAppUpdaterDialogSettings(new AppUpdaterDialogSettings().parse(getArguments().getString("appUpdaterDialogSettings")));
        appUpdaterDialogBinding.setViewModel(appUpdaterDialogViewModel);

        UtilAnimation.setRotateInfinite(appUpdaterDialogBinding.headerContainer.imgLogo);

        setThemeColor(getResources().getColor(R.color.colorPrimaryLight));
        setCancelable(false);

        return appUpdaterDialogBinding.getRoot();
    }

    AppUpdaterDialog setThemeColor(int colorResource) {
        if (null != appUpdaterDialogBinding) {
            ((GradientDrawable) appUpdaterDialogBinding.headerContainer.bgLogo.getBackground()).setColor(colorResource);
            ((GradientDrawable) appUpdaterDialogBinding.headerContainer.bgHeader.getBackground()).setColor(colorResource);
            ((GradientDrawable) appUpdaterDialogBinding.imgUpdate.getBackground()).setColor(colorResource);
        }
        return this;
    }

    AppUpdaterDialog setThemeColor(String themeColor) {
        int color = getResources().getColor(R.color.colorPrimaryLight);
        try {
            color = Color.parseColor(themeColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setThemeColor(color);
        return this;
    }

    public void show() {
        show(activity.getFragmentManager(), "appUpdater");
    }
}

//            setThemeColor(activity.getResources().getColor(R.color.colorPrimaryLight));

package com.hsun.appupdater;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class AppUpdater extends DialogFragment implements View.OnClickListener {

    private Activity activity;
    private UpdateDataModel updateDataModel;

    private Button bt_update;
    private ImageView img_update, bg_header, bg_logo;

    public AppUpdater() {

    }

    public static AppUpdater instance(String updateData) {
        AppUpdater fragment = new AppUpdater();
        Bundle args = new Bundle();
        args.putString("updateData", updateData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (null != getDialog().getWindow())
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.app_updater_dialog, container);

        if (getArguments() == null || null == getArguments().getString("updateData")) {
            //Log.e("")
        } else {
            updateDataModel = JsonUpdateData.parse(getArguments().getString("updateData"));
        }

        ImageView img_logo = (ImageView) view.findViewById(R.id.img_logo);
        AnimationUtil.setRotateInfinite(img_logo);
        bt_update = (Button) view.findViewById(R.id.bt_update);
        bt_update.setOnClickListener(this);
        img_update = (ImageView) view.findViewById(R.id.img_update);
        bg_logo = (ImageView) view.findViewById(R.id.bg_logo);
        bg_header = (ImageView) view.findViewById(R.id.bg_header);

        setThemeColor(getResources().getColor(R.color.colorPrimaryLight));

        return view;
    }

    public AppUpdater setThemeColor(int colorResource) {
        ((GradientDrawable) bg_logo.getBackground()).setColor(colorResource);
        ((GradientDrawable) bg_header.getBackground()).setColor(colorResource);
        ((GradientDrawable) img_update.getBackground()).setColor(colorResource);
        return this;
    }

    public AppUpdater setThemeColor(String themeColor) {
        int color = getResources().getColor(R.color.colorPrimaryLight);
        try {
            color = Color.parseColor(themeColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setThemeColor(color);
        return this;
    }

    public AppUpdater setBtnUpdateText(String text) {
        bt_update.setText(text);
        return this;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_update) {
            dismiss();
        }
    }
}

package com.example.noworderfoodapp.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.noworderfoodapp.R;

public class FilterOptionDialog extends DialogFragment {
    public static final String KEY_REDUCE = "KEY_REDUCE";
    public static final String KEY_ADVANCE = "KEY_ADVANCE";
    public static String TAG = FilterOptionDialog.class.getName();

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_filter_option, container);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvPriceReduce = view.findViewById(R.id.tv_price_reduce);
        TextView tvPriceAdvance = view.findViewById(R.id.tv_price_advance);
        tvPriceReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onItemClick(KEY_REDUCE);
                dismiss();
            }
        });
        tvPriceAdvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onItemClick(KEY_ADVANCE);
                dismiss();
            }
        });
    }
    private OnItemClick callBack;

    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    public interface OnItemClick {
        void onItemClick(String key);
    }
}

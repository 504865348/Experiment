package com.joshua.experiment.utils.myinfoCityAndDate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;

import com.joshua.experiment.R;

/**
 * Created by nzz on 2017/7/5.
 */

public class ChooseDateUtil implements View.OnClickListener, NumberPicker.OnValueChangeListener {
    Context context;
    AlertDialog dialog;
    ChooseDateInterface dateInterface;
    NumberPicker npYear, npMonth, npDay;
    TextView tvCancel, tvSure;
    int[] newDateArray = new int[3];

    public void createDialog(Context context, int[] oldDateArray, ChooseDateInterface dateInterface) {
        this.context = context;
        this.dateInterface = dateInterface;
        newDateArray[0] = oldDateArray[0];
        newDateArray[1] = oldDateArray[1];
        newDateArray[2] = oldDateArray[2];

        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.set_edit_myinfo_dialog_choose_date);
        tvCancel = (TextView) window.findViewById(R.id.tvCancel);
        tvSure = (TextView) window.findViewById(R.id.tvSure);
        tvCancel.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        npYear = (NumberPicker) window.findViewById(R.id.npYear);
        npMonth = (NumberPicker) window.findViewById(R.id.npMonth);
        npDay = (NumberPicker) window.findViewById(R.id.npDay);
        npYear.setMinValue(1900);
        npYear.setMaxValue(2100);
        npMonth.setMinValue(1);
        npMonth.setMaxValue(12);
        npDay.setMinValue(1);
        npDay.setMaxValue(31);
        npYear.setValue(oldDateArray[0]);
        npMonth.setValue(oldDateArray[1]);
        npDay.setValue(oldDateArray[2]);
        npYear.setOnValueChangedListener(this);
        npMonth.setOnValueChangedListener(this);
        npDay.setOnValueChangedListener(this);
        setNumberPickerDividerColor(npYear);
        setNumberPickerDividerColor(npMonth);
        setNumberPickerDividerColor(npDay);
        setNumberPickerTextColor(npYear, context.getResources().getColor(R.color.black));
        setNumberPickerTextColor(npMonth, context.getResources().getColor(R.color.black));
        setNumberPickerTextColor(npDay, context.getResources().getColor(R.color.black));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                dialog.dismiss();
                break;
            case R.id.tvSure:
                dialog.dismiss();
                dateInterface.sure(newDateArray);
                break;
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()) {
            case R.id.npYear:
                newDateArray[0] = newVal;
                npDay.setMaxValue(DateUtil.getNumberOfDays(newDateArray[0], newDateArray[1]));
                break;
            case R.id.npMonth:
                newDateArray[1] = newVal;
                npDay.setMaxValue(DateUtil.getNumberOfDays(newDateArray[0], newDateArray[1]));
                break;
            case R.id.npDay:
                newDateArray[2] = newVal;
                break;
        }
    }

    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(picker, new ColorDrawable(context.getResources().getColor(R.color.red)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        boolean result = false;
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    result = true;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}

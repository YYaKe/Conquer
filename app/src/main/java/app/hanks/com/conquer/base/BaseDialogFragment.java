package app.hanks.com.conquer.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import app.hanks.com.conquer.CustomApplication;
import app.hanks.com.conquer.R;

/**
 * author：wiki on 2018/11/13
 * email：zhengweiqunemail@qq.com
 */
public class BaseDialogFragment extends DialogFragment {

    private OnDialogFragmentDismissListener onDialogFragmentDismissListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetDialogStyle();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        if (isAdded()) return transaction.commit();
        return super.show(transaction, tag);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (isAdded()) return;
        super.show(manager, tag);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        transparentDialogBackground();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDialogFragmentDismissListener != null)
            onDialogFragmentDismissListener.onDialogFragmentDismiss();
    }

    protected void transparentDialogBackground() {
        if (getDialog() == null || getDialog().getWindow() == null) return;
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        if (params != null) {
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes(params);
        }
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setGravity(Gravity.TOP);
        getDialog().setCanceledOnTouchOutside(false);
    }

    protected void onSetDialogStyle() {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.dialog_style);
    }

    protected void displayToast(String toast) {
        Toast.makeText(getApp(), toast, Toast.LENGTH_SHORT).show();
    }

    protected void displayToast(int id) {
        displayToast(getApp().getString(id));
    }

    public void dismissDelay(long delayTime) {
        dismissDelay(delayTime, false);
    }

    public void dismissDelay(long delayTime, final boolean allowingStateLoss) {
        Activity activity = getActivity();
        if (activity == null) return;
        activity.getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (allowingStateLoss)
                    dismissAllowingStateLoss();
                else
                    dismiss();
            }
        }, delayTime);
    }

    public CustomApplication getApp() {
        return CustomApplication.getInstance();
    }

    public void setOnDialogFragmentDismissListener(OnDialogFragmentDismissListener onDialogFragmentDismissListener) {
        this.onDialogFragmentDismissListener = onDialogFragmentDismissListener;
    }

    public interface OnDialogFragmentDismissListener {
        void onDialogFragmentDismiss();
    }

}

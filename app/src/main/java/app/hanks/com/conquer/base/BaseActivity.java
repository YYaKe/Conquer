package app.hanks.com.conquer.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.util.SP;

/**
 * author：wiki on 2019/3/10
 * email：zhengweiqunemail@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initTheme();
        initView();
    }

    protected abstract void initView();

    /**
     * 设置主题
     */
    protected void initTheme() {

        int theme = (Integer) SP.get(context, "theme", 0);
        switch (theme) {
            case 0:
                setTheme(R.style.SwitchTheme0);
                break;
            case 1:
                setTheme(R.style.SwitchTheme1);
                break;
            case 2:
                setTheme(R.style.SwitchTheme2);
                break;
            case 3:
                setTheme(R.style.SwitchTheme3);
                break;
        }
    }

}

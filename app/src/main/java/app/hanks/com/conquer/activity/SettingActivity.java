package app.hanks.com.conquer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.base.BaseActivity;
import app.hanks.com.conquer.util.A;
import app.hanks.com.conquer.util.AlertDialogUtils;
import app.hanks.com.conquer.util.AlertDialogUtils.OkCallBack;
import app.hanks.com.conquer.util.CommonUtils;
import app.hanks.com.conquer.util.L;
import app.hanks.com.conquer.util.SP;

public class SettingActivity extends BaseActivity implements OnClickListener {

    private static final int GET_ALERT_AUDIO = 0;
    //----UIView---
    private RelativeLayout toolbar;
    private TextView titleTv;
    private ImageButton block_0, block_1, block_2, block_3;
    private TextView tv_alert_audio, tv_alert_time, tv_version;

    final String[] s = {"提前10分钟", "提前20分钟", "提前30分钟", "提前1小时"};

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);
        //设置返回箭头
        TextView backImg = (TextView) findViewById(R.id.common_left);
        backImg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
        backImg.setOnClickListener(this);

        toolbar = (RelativeLayout) findViewById(R.id.common_relative);
        titleTv = (TextView) findViewById(R.id.common_center);
        block_0 = (ImageButton) findViewById(R.id.block_0);
        block_1 = (ImageButton) findViewById(R.id.block_1);
        block_2 = (ImageButton) findViewById(R.id.block_2);
        block_3 = (ImageButton) findViewById(R.id.block_3);

        block_0.setOnClickListener(this);
        block_1.setOnClickListener(this);
        block_2.setOnClickListener(this);
        block_3.setOnClickListener(this);

        tv_alert_audio = (TextView) findViewById(R.id.tv_alert_audio);
        tv_alert_time = (TextView) findViewById(R.id.tv_alert_time);
        tv_version = (TextView) findViewById(R.id.tv_version);
        findViewById(R.id.ll_alert_audio).setOnClickListener(this);
        findViewById(R.id.ll_alarm).setOnClickListener(this);
        findViewById(R.id.ll_black).setOnClickListener(this);
        findViewById(R.id.ll_version).setOnClickListener(this);
        findViewById(R.id.ll_about).setOnClickListener(this);
        findViewById(R.id.ll_use).setOnClickListener(this);
        findViewById(R.id.ll_help).setOnClickListener(this);
        setThemeBgColor((Integer) SP.get(context, "theme", 0));
        String defaultUri = (String) SP.get(context, "alert_audio", "");
        tv_alert_audio.setText(RingtoneManager.getRingtone(context, Uri.parse(defaultUri)).getTitle(context));
        tv_alert_time.setText(s[(Integer) SP.get(context, "alert_time", 0)]);
        tv_version.setText(CommonUtils.getVersionName(context));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_left:
                finish();
                break;
            case R.id.ll_alert_audio:
                showAlertAudio();
                break;
            case R.id.ll_alarm:
                showSelectAlertTime();
                break;
            case R.id.ll_black:
                A.goOtherActivity(context, BlackListActivity.class);
                break;
            case R.id.ll_version:
                checkVersion();
                break;
            case R.id.ll_about:
                A.goOtherActivity(context, new Intent(context, AboutActivity.class).putExtra("type", "about"));
                break;
            case R.id.ll_use:
                A.goOtherActivity(context, new Intent(context, AboutActivity.class).putExtra("type", "use"));
                break;
            case R.id.ll_help:
                A.goOtherActivity(context, new Intent(context, AboutActivity.class).putExtra("type", "help"));
                break;
            case R.id.block_0:
                setThemeBgColor(0);
                break;
            case R.id.block_1:
                setThemeBgColor(1);
                break;
            case R.id.block_2:
                setThemeBgColor(2);
                break;
            case R.id.block_3:
                setThemeBgColor(3);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 设置主题颜色块
     */
    private void setThemeBgColor(int i) {
        titleTv.setTextColor(Color.WHITE);
        switch (i) {
            case 0:
                toolbar.setBackgroundColor(getResources().getColor(R.color.theme_0));
                titleTv.setTextColor(Color.BLACK);
                break;
            case 1:
                toolbar.setBackgroundColor(getResources().getColor(R.color.theme_1));
                break;
            case 2:
                toolbar.setBackgroundColor(getResources().getColor(R.color.theme_2));
                break;
            case 3:
                toolbar.setBackgroundColor(getResources().getColor(R.color.theme_3));
                break;
            default:
                titleTv.setTextColor(Color.BLACK);
                break;
        }
        block_0.setImageResource(i == 0 ? R.drawable.ic_done_white_18dp : R.color.transparent);
        block_1.setImageResource(i == 1 ? R.drawable.ic_done_white_18dp : R.color.transparent);
        block_2.setImageResource(i == 2 ? R.drawable.ic_done_white_18dp : R.color.transparent);
        block_3.setImageResource(i == 3 ? R.drawable.ic_done_white_18dp : R.color.transparent);
        SP.put(context, "theme", i);
    }

    /*
     * 选择铃声
     */
    private void showAlertAudio() {
        Intent i = new Intent("android.intent.action.RINGTONE_PICKER");
        startActivityForResult(i, GET_ALERT_AUDIO);
    }

    /**
     * 默认提醒时间
     */
    private void showSelectAlertTime() {

        int defaltItem = (Integer) SP.get(context, "alert_time", 0);
        AlertDialogUtils.showChiceGender(context, s, defaltItem, new OkCallBack() {
            @Override
            public void onOkClick(DialogInterface dialog, int which) {
                SP.put(context, "alert_time", which);
                tv_alert_time.setText(s[which]);
            }
        });
    }

    /**
     * 检查版本
     */
    private void checkVersion() {
        AlertDialogUtils.show(context, "版本更新", "当前版本：" + CommonUtils.getVersionName(context), "立即更新", "以后再说", new OkCallBack() {
            @Override
            public void onOkClick(DialogInterface dialog, int which) {
                update();
            }
        }, null);
    }

    /**
     * 版本更新
     */
    protected void update() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.d(requestCode + "," + resultCode + "," + data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GET_ALERT_AUDIO) {
                Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI); // 获取选择的URI
                if (pickedUri != null) {
                    SP.put(context, "alert_audio", pickedUri.toString());
                    tv_alert_audio.setText(RingtoneManager.getRingtone(context, pickedUri).getTitle(context));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

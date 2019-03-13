package app.hanks.com.conquer.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.base.BaseActivity;
import app.hanks.com.conquer.bean.TodoBean;
import app.hanks.com.conquer.util.L;
import app.hanks.com.conquer.util.T;
import app.hanks.com.conquer.view.datetime.datepicker.DatePickerDialog;
import app.hanks.com.conquer.view.datetime.timepicker.RadialPickerLayout;
import app.hanks.com.conquer.view.datetime.timepicker.TimePickerDialog;
import cn.bmob.v3.listener.SaveListener;

/**
 * author：wiki on 2019/3/12
 * email：zhengweiqunemail@qq.com
 */
public class PostActivity extends BaseActivity implements View.OnClickListener {

    private final Calendar mCalendar = Calendar.getInstance();
    private TimePickerDialog timePickerDialog24h;
    private DatePickerDialog datePickerDialog;
    //---UIView
    private EditText etInput;
    private TextView tvDateTime;

    @Override
    protected void initView() {
        setContentView(R.layout.post);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnChooseCalendar).setOnClickListener(this);
        findViewById(R.id.btnTypeList).setOnClickListener(this);
        findViewById(R.id.btnPriority).setOnClickListener(this);
        findViewById(R.id.btnOk).setOnClickListener(this);
        etInput = (EditText) findViewById(R.id.etInput);
        tvDateTime = (TextView) findViewById(R.id.tvDateTime);

        initDatePicker();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnChooseCalendar://日期
                datePickerDialog.show(getFragmentManager(), "");
                break;
            case R.id.btnTypeList://类型
                break;
            case R.id.btnPriority://优先级
                break;
            case R.id.btnOk:
                TodoBean bean = new TodoBean(1222, "", "3.15日，中期检查",
                        0, "", 0, 1, "毕设", 1, 1, 1);
                bean.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        L.i("post success");
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        L.e("post failed");
                        T.show(PostActivity.this, "post failed");
                    }
                });
                break;
        }
    }

    /**
     * 初始化日历时间的选择控件
     */
    private void initDatePicker() {
        mCalendar.add(Calendar.MINUTE, 60);// 设成60分钟后
//        setTimeAndTip(new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA).format(mCalendar.getTime()) + " "
//                + pad(mCalendar.get(Calendar.HOUR_OF_DAY)) + ":" + pad(mCalendar.get(Calendar.MINUTE)));

        timePickerDialog24h = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                tvDateTime.setText(new StringBuffer().append(tvDateTime.getText()).append(" ").
//                        append(pad(hourOfDay)).append(":").append(pad(minute)));
                setTimeAndTip(tvDateTime.getText() + " " + pad(hourOfDay) + ":" + pad(minute));
            }
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);

        datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                tvDateTime.setText(new StringBuilder().append(pad(year)).append("/").append(pad(month + 1))
                        .append("/").append(pad(day)));
                timePickerDialog24h.show(getFragmentManager(), "");
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        int curYear = mCalendar.get(Calendar.YEAR);
        datePickerDialog.setYearRange(curYear, mCalendar.get(Calendar.MONTH) >= 11 ? curYear + 1 : curYear);
    }

    private String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    /**
     * 设置tv_time 和 tv_time_tip的文本内容
     *
     * @param string 类似yyyy/MM/dd HH:mm
     */
    private void setTimeAndTip(String string) {
        tvDateTime.setText(string);
//        tvDateTime.setText(string.substring(string.length() - 5, string.length()));
        try {
            Date d = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA).parse(string);
//            tv_time_tip.setTextColor(d.getTime() > System.currentTimeMillis() ? Color.GRAY : Color.RED);
//            tv_time_tip.setText("(" + TaskUtil.getDescriptionTimeFromTimestamp(d.getTime()) + ")");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}

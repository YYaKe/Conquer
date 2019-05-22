package app.hanks.com.conquer.activity;

import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xujiaji.happybubble.BubbleDialog;

import java.util.Calendar;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.base.BaseActivity;
import app.hanks.com.conquer.bean.TodoBean;
import app.hanks.com.conquer.bean.User;
import app.hanks.com.conquer.config.Constants;
import app.hanks.com.conquer.helper.BubbleCreator;
import app.hanks.com.conquer.util.CalendarUtils;
import app.hanks.com.conquer.util.L;
import app.hanks.com.conquer.util.T;
import app.hanks.com.conquer.view.datetime.datepicker.DatePickerDialog;
import app.hanks.com.conquer.view.datetime.timepicker.RadialPickerLayout;
import app.hanks.com.conquer.view.datetime.timepicker.TimePickerDialog;
import app.hanks.com.conquer.view.selector.SelectRemindCyclePopup;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * author：wiki on 2019/3/12
 * email：zhengweiqunemail@qq.com
 */
public class PostActivity extends BaseActivity implements View.OnClickListener {

    private final Calendar mCalendar = Calendar.getInstance();
    private TimePickerDialog timePickerDialog24h;
    private DatePickerDialog datePickerDialog;
    private BubbleDialog mBubbleDialog;
    private BubbleDialog mChoosePriorityDialog;
    private int priority = 0;
    private int toDoType = 0;
    private int cycle;
    private int ring;
    private String time;
    private long beginTime;
    //---UIView
    private ConstraintLayout layout;
    private EditText etInput;
    private TextView tvDateTime;
    private ImageView imgCalendar;
    private ImageView imgType;
    private ImageView imgPriority;
    private ImageView btnOK;

    @Override
    protected void initView() {
        setContentView(R.layout.post);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnChooseCalendar).setOnClickListener(this);
        findViewById(R.id.btnTypeList).setOnClickListener(this);
        findViewById(R.id.btnPriority).setOnClickListener(this);
        findViewById(R.id.btnOk).setOnClickListener(this);
        layout = (ConstraintLayout) findViewById(R.id.rootContainer);
        etInput = (EditText) findViewById(R.id.etInput);
        tvDateTime = (TextView) findViewById(R.id.tvDateTime);
        imgType = (ImageView) findViewById(R.id.btnTypeList);
        imgPriority = (ImageView) findViewById(R.id.btnPriority);
        imgCalendar = (ImageView) findViewById(R.id.btnChooseCalendar);
        btnOK = (ImageView) findViewById(R.id.btnOk);

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
                showChooseTodoCategory();
                break;
            case R.id.btnPriority://优先级
                showChoosePriority();
                break;
            case R.id.btnOk:
                setClock();
                TodoBean bean = new TodoBean(0, tvDateTime.getText().toString(), etInput.getText().toString(),
                        beginTime, tvDateTime.getText().toString(), 0, 1, "", toDoType,
                        BmobChatUser.getCurrentUser(this, User.class).getObjectId()
                        , priority);
                bean.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        L.i("post success");
                        T.show(PostActivity.this, "添加成功");
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
        timePickerDialog24h = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                imgCalendar.setColorFilter(getResources().getColor(R.color.pink_200));
                time = pad(hourOfDay) + ":" + pad(minute);
                setTimeAndTip(tvDateTime.getText() + " " + time);
            }
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);

        datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                btnOK.setTag(R.id.year, year);
                btnOK.setTag(R.id.month, month);
                btnOK.setTag(R.id.day, day);

                tvDateTime.setText(new StringBuilder().append(pad(year)).append("/").append(pad(month + 1))
                        .append("/").append(pad(day)));
                timePickerDialog24h.show(getFragmentManager(), "");
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        int curYear = mCalendar.get(Calendar.YEAR);
        datePickerDialog.setYearRange(curYear, mCalendar.get(Calendar.MONTH) >= 11 ? curYear + 1 : curYear);
    }

    /**
     * int < 10 ,在前面+0
     */
    private String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    /**
     * 设置tvDateTime的文本内容
     *
     * @param string 类似yyyy/MM/dd HH:mm
     */
    private void setTimeAndTip(String string) {
        tvDateTime.setText(string);
    }

    private void showChooseTodoCategory() {
        if (mBubbleDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_choose_category, null);
            mBubbleDialog = new BubbleDialog(this)
                    .setPosition(BubbleDialog.Position.TOP)
                    .setBubbleLayout(BubbleCreator.get(this))
                    .addContentView(view);
            RadioGroup group = (RadioGroup) view.findViewById(R.id.rgGroup);
            group.check(R.id.rbUseOne);
            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    mBubbleDialog.dismiss();
                    imgType.setColorFilter(getResources().getColor(R.color.blue_300));
                    switch (checkedId) {
                        case R.id.rbUseOne:
                            toDoType = Constants.USER_ONE;
                            break;
                        case R.id.rbWork:
                            toDoType = Constants.WORK;
                            break;
                        case R.id.rbLearn:
                            toDoType = Constants.LEARN;
                            break;
                        case R.id.rbLife:
                            toDoType = Constants.LIFE;
                            break;
                    }
                }
            });
        }

        mBubbleDialog.setClickedView(imgType);
        mBubbleDialog.show();

        if (mBubbleDialog.getWindow() != null)
            mBubbleDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void showChoosePriority() {
        if (mChoosePriorityDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_choose_priority, null);
            mChoosePriorityDialog = new BubbleDialog(this)
                    .setPosition(BubbleDialog.Position.TOP)
                    .addContentView(view)
                    .setBubbleLayout(BubbleCreator.get(this));
            RadioGroup group = (RadioGroup) view.findViewById(R.id.rgGroup);
            group.check(R.id.rbPriorityNotUrgentNotImportant);
            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rbPriorityUrgentImportant:
                            priority = Constants.URGENT_IMPROTANT;
                            imgPriority.setColorFilter(getResources().getColor(R.color.red_800));
                            break;
                        case R.id.rbPriorityImportantNotUrgent:
                            priority = Constants.IMPROTANT_NOT_URGENT;
                            imgPriority.setColorFilter(getResources().getColor(R.color.orange_800));
                            break;
                        case R.id.rbPriorityUrgentNotImportant:
                            priority = Constants.URGETN_NOT_IMPORTANT;
                            imgPriority.setColorFilter(getResources().getColor(R.color.yellow_800));
                            break;
                        case R.id.rbPriorityNotUrgentNotImportant:
                            priority = Constants.NOT_URGENT_NOT_IMPORTANT;
                            imgPriority.setColorFilter(getResources().getColor(R.color.grey_500));
                            break;
                    }
                    mChoosePriorityDialog.dismiss();
                }
            });
        }
        mChoosePriorityDialog.setClickedView(imgPriority);
        mChoosePriorityDialog.show();

        if (mChoosePriorityDialog.getWindow() != null)
            mChoosePriorityDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * @param repeat 解析二进制闹钟周期
     * @param flag   flag=0返回带有汉字的周一，周二cycle等，flag=1,返回weeks(1,2,3)
     * @return
     */
    public static String parseRepeat(int repeat, int flag) {
        String cycle = "";
        String weeks = "";
        if (repeat == 0) {
            repeat = 127;
        }
        if (repeat % 2 == 1) {
            cycle = "周一";
            weeks = "1";
        }
        if (repeat % 4 >= 2) {
            if ("".equals(cycle)) {
                cycle = "周二";
                weeks = "2";
            } else {
                cycle = cycle + "," + "周二";
                weeks = weeks + "," + "2";
            }
        }
        if (repeat % 8 >= 4) {
            if ("".equals(cycle)) {
                cycle = "周三";
                weeks = "3";
            } else {
                cycle = cycle + "," + "周三";
                weeks = weeks + "," + "3";
            }
        }
        if (repeat % 16 >= 8) {
            if ("".equals(cycle)) {
                cycle = "周四";
                weeks = "4";
            } else {
                cycle = cycle + "," + "周四";
                weeks = weeks + "," + "4";
            }
        }
        if (repeat % 32 >= 16) {
            if ("".equals(cycle)) {
                cycle = "周五";
                weeks = "5";
            } else {
                cycle = cycle + "," + "周五";
                weeks = weeks + "," + "5";
            }
        }
        if (repeat % 64 >= 32) {
            if ("".equals(cycle)) {
                cycle = "周六";
                weeks = "6";
            } else {
                cycle = cycle + "," + "周六";
                weeks = weeks + "," + "6";
            }
        }
        if (repeat / 64 == 1) {
            if ("".equals(cycle)) {
                cycle = "周日";
                weeks = "7";
            } else {
                cycle = cycle + "," + "周日";
                weeks = weeks + "," + "7";
            }
        }
        return flag == 0 ? cycle : weeks;
    }

    private void setClock() {
        if (time != null && time.length() > 0) {
            String[] times = time.split(":");
            beginTime = CalendarUtils.remindTimeCalculator(
                    (int) btnOK.getTag(R.id.year),
                    (int) btnOK.getTag(R.id.month) + 1,
                    (int) btnOK.getTag(R.id.day),
                    Integer.valueOf(times[0]),
                    Integer.valueOf(times[1]));
            ;
            CalendarUtils.addCalendarEventRemind(this, Constants.APPLICATION_NAME, etInput.getText().toString(),
                    beginTime, beginTime, 30, new CalendarUtils.onCalendarRemindListener() {
                        @Override
                        public void onFailed(Status error_code) {
                            Toast.makeText(PostActivity.this, "提醒设置失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSuccess() {
                            Toast.makeText(PostActivity.this, "提醒设置成功", Toast.LENGTH_LONG).show();
                        }
                    });
//
//            if (cycle == 0) {//是每天的闹钟
//                AlarmManagerUtil.setAlarm(this, 0, Integer.parseInt(times[0]), Integer.parseInt
//                        (times[1]), 0, 0, "闹钟响了", ring);
//            }
//            if (cycle == -1) {//是只响一次的闹钟
//                AlarmManagerUtil.setAlarm(this, 1, Integer.parseInt(times[0]), Integer.parseInt
//                        (times[1]), 0, 0, "闹钟响了", ring);
//            } else {//多选，周几的闹钟
//                String weeksStr = parseRepeat(cycle, 1);
//                String[] weeks = weeksStr.split(",");
//                for (int i = 0; i < weeks.length; i++) {
//                    AlarmManagerUtil.setAlarm(this, 2, Integer.parseInt(times[0]), Integer
//                            .parseInt(times[1]), i, Integer.parseInt(weeks[i]), "闹钟响了", ring);
//                }
//            }
//            Toast.makeText(this, "闹钟设置成功", Toast.LENGTH_LONG).show();
        }

    }

}

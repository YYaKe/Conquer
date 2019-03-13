package app.hanks.com.conquer.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xujiaji.happybubble.BubbleDialog;

import java.util.Calendar;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.base.BaseActivity;
import app.hanks.com.conquer.bean.TodoBean;
import app.hanks.com.conquer.helper.BubbleCreator;
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
    private BubbleDialog mBubbleDialog;
    private BubbleDialog mChoosePriorityDialog;
    //---UIView
    private EditText etInput;
    private TextView tvDateTime;
    private ImageView imgCalendar;
    private ImageView imgType;
    private ImageView imgPriority;

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
        imgType = (ImageView) findViewById(R.id.btnTypeList);
        imgPriority = (ImageView) findViewById(R.id.btnPriority);
        imgCalendar = (ImageView) findViewById(R.id.btnChooseCalendar);

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
        timePickerDialog24h = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                imgCalendar.setColorFilter(getResources().getColor(R.color.pink_200));
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

    public void showChooseTodoCategory() {
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
                            break;
                        case R.id.rbWork:
                            break;
                        case R.id.rbLearn:
                            break;
                        case R.id.rbLife:
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

    public void showChoosePriority() {
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
                            imgPriority.setColorFilter(getResources().getColor(R.color.red_800));
                            break;
                        case R.id.rbPriorityImportantNotUrgent:
                            imgPriority.setColorFilter(getResources().getColor(R.color.orange_800));
                            break;
                        case R.id.rbPriorityUrgentNotImportant:
                            imgPriority.setColorFilter(getResources().getColor(R.color.yellow_800));
                            break;
                        case R.id.rbPriorityNotUrgentNotImportant:
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


}

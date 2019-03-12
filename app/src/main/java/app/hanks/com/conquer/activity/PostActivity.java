package app.hanks.com.conquer.activity;

import android.view.View;
import android.widget.EditText;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.base.BaseActivity;
import app.hanks.com.conquer.bean.TodoBean;
import app.hanks.com.conquer.util.L;
import app.hanks.com.conquer.util.T;
import cn.bmob.v3.listener.SaveListener;

/**
 * author：wiki on 2019/3/12
 * email：zhengweiqunemail@qq.com
 */
public class PostActivity extends BaseActivity implements View.OnClickListener {

    //---UIView
    private EditText inputEt;

    @Override
    protected void initView() {
        setContentView(R.layout.post);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnChooseCalendar).setOnClickListener(this);
        findViewById(R.id.btnTypeList).setOnClickListener(this);
        findViewById(R.id.btnPriority).setOnClickListener(this);
        findViewById(R.id.btnOk).setOnClickListener(this);
        inputEt = (EditText) findViewById(R.id.etInput);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnChooseCalendar:
                break;
            case R.id.btnTypeList:
                break;
            case R.id.btnPriority:
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
}

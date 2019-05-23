package app.hanks.com.conquer.activity;

import android.view.View;
import android.widget.TextView;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.base.BaseActivity;
import app.hanks.com.conquer.main.HomeActivity;
import app.hanks.com.conquer.util.A;

public class SoftActivity extends BaseActivity implements View.OnClickListener {

    private TextView textView;
    private TextView title;

    @Override
    protected void initView() {
        setContentView(R.layout.soft);
        textView = (TextView) findViewById(R.id.common_text_view);
        title = (TextView) findViewById(R.id.common_title);
        //设置返回箭头
        TextView backImg = (TextView) findViewById(R.id.common_left);
        backImg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
        backImg.setOnClickListener(this);

        String type = getIntent().getStringExtra("type");
        if (type.endsWith("about")) {
            textView.setText(R.string.about);
            title.setText("关于软件");
        } else if (type.endsWith("use")) {
            textView.setText(R.string.use);
            title.setText("用户协议");
        } else if (type.endsWith("help")) {
            textView.setText(R.string.help);
            title.setText("使用帮助");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_left:
                finish();
                break;
        }
    }
}

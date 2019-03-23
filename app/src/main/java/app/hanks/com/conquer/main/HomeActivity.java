package app.hanks.com.conquer.main;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.base.BaseActivity;
import app.hanks.com.conquer.fragment.MenuFragment;
import app.hanks.com.conquer.main.fragment.ToDoFragment;
import app.hanks.com.conquer.util.PixelUtil;


/**
 * author：wiki on 2019/3/10
 * email：zhengweiqunemail@qq.com
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private ToDoFragment toDoFragment;
    //---UIView--
    private DrawerLayout drawerLayout;
    private MenuFragment menuFragment;// 侧滑菜单Fragment
    private TextView mTitle;
    private TextView menu;


    @Override
    protected void initView() {
        setContentView(R.layout.home);
        toDoFragment = new ToDoFragment();
        mTitle = (TextView) findViewById(R.id.common_center);
        menu = (TextView) findViewById(R.id.common_left);
        menu.setOnClickListener(this);
        initDrawerMenu();
        startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeFragment(R.id.layout_content, toDoFragment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_left:
                toggle();
                break;
        }
    }

    /**
     * 侧滑抽屉界面
     */
    private void initDrawerMenu() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);// 侧滑控件
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.LEFT);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int arg0) {
            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {
            }

            @Override
            public void onDrawerOpened(View arg0) {
            }

            @Override
            public void onDrawerClosed(View arg0) {

            }
        });

        // 侧滑菜单
        menuFragment = new MenuFragment();
        changeFragment(R.id.left_drawer, menuFragment);
    }

    private void startAnimation() {
        int actionbarSize = PixelUtil.dp2px(57);
        mTitle.setTranslationY(-actionbarSize);
        menu.setTranslationY(-actionbarSize);

        menu.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        mTitle.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
    }

    /**
     * 切换Fragment
     *
     * @param id       要切换的布局id
     * @param fragment 要切换的Fragment
     */
    protected void changeFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }

    /**
     * 切换侧滑菜单布局打开或关闭
     */
    private void toggle() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

}

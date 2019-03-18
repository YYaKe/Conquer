package app.hanks.com.conquer.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.base.BaseActivity;
import app.hanks.com.conquer.fragment.MenuFragment;
import app.hanks.com.conquer.main.fragment.ToDoFragment;
import app.hanks.com.conquer.util.PixelUtil;
import app.hanks.com.conquer.view.materialmenu.MaterialMenuView;


/**
 * author：wiki on 2019/3/10
 * email：zhengweiqunemail@qq.com
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private ToDoFragment toDoFragment;
    //---UIView--
    private DrawerLayout drawerLayout;
    private MaterialMenuView materialMenu;
    private MenuFragment menuFragment;// 侧滑菜单Fragment
    private ImageButton iv_sort;
    private ImageButton iv_search;
    private View toolbar;
    private TextView mTitle;

    @Override
    protected void initView() {
        setContentView(R.layout.home);
        toDoFragment = new ToDoFragment();
//        materialMenu = (MaterialMenuView) findViewById(R.id.material_menu);
//        toolbar = findViewById(R.id.title);
//        mTitle = (TextView) findViewById(R.id.tv_title);
//        iv_sort = (ImageButton) findViewById(R.id.iv_sort);
//        iv_search = (ImageButton) findViewById(R.id.iv_search);
//        iv_search.setVisibility(View.VISIBLE);
//        iv_search.setOnClickListener(this);
//        materialMenu.setOnClickListener(this);
//        iv_sort.setOnClickListener(this);
        initDrawerMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        materialMenu.animateState(MaterialMenuDrawable.IconState.BURGER);
        changeFramgnt(R.id.layout_content, toDoFragment);
    }

    @Override
    public void onClick(View view) {
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
//                materialMenu.animatePressedState(MaterialMenuDrawable.IconState.X);
            }

            @Override
            public void onDrawerClosed(View arg0) {

            }
        });

        // 侧滑菜单
        menuFragment = new MenuFragment();
        changeFramgnt(R.id.left_drawer, menuFragment);
    }

    private void startIntroAnimation() {
        int actionbarSize = PixelUtil.dp2px(57);
        toolbar.setTranslationY(-actionbarSize);
        mTitle.setTranslationY(-actionbarSize);
        iv_sort.setTranslationY(-actionbarSize);

        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        mTitle.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        iv_sort.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        changeFramgnt(R.id.layout_content, toDoFragment);
                    }
                })
                .start();
    }

    /**
     * 切换Fragment
     *
     * @param id       要切换的布局id
     * @param fragment 要切换的Fragment
     */
    protected void changeFramgnt(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }

}

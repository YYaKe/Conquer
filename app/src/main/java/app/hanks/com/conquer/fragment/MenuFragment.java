package app.hanks.com.conquer.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.activity.AllMyTaskActivity;
import app.hanks.com.conquer.activity.FriendActivity;
import app.hanks.com.conquer.activity.MyHistoryActivity;
import app.hanks.com.conquer.activity.SettingActivity;
import app.hanks.com.conquer.bean.User;
import app.hanks.com.conquer.main.HomeActivity;
import app.hanks.com.conquer.otto.BusProvider;
import app.hanks.com.conquer.otto.MenuPhotoClickEvent;
import app.hanks.com.conquer.util.A;
import app.hanks.com.conquer.util.CollectionUtils;
import app.hanks.com.conquer.util.UserDataUtils;
import app.hanks.com.conquer.view.CircularImageView;

public class MenuFragment extends BaseFragment implements OnClickListener {

    private CircularImageView iv_photo;
    private TextView tv_nickname;
    private ImageView iv_home_bg;
    private HomeActivity homeActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        homeActivity = (HomeActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        init(v);
        return v;
    }

    @Override
    public void onResume() {
        initUserData();
        UserDataUtils.queryUserByUsername(context, currentUser.getUsername(), new UserDataUtils.QueryUserDataListener() {
            @Override
            public void onSuccess(List<User> arg0) {
                if (CollectionUtils.isNotNull(arg0)) {
                    currentUser = arg0.get(0);
                    initUserData();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
            }
        });
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_photo:
                BusProvider.getInstance().post(new MenuPhotoClickEvent());
                break;
            case R.id.ll_friend:
                A.goOtherActivity(context, FriendActivity.class);
                break;
            case R.id.ll_all_zixi:
                A.goOtherActivity(context, AllMyTaskActivity.class);
                break;
            case R.id.ll_history:
                A.goOtherActivity(context, MyHistoryActivity.class);
                break;
            case R.id.ll_setting:
                A.goOtherActivityFinish(context, SettingActivity.class);
                break;
        }
        homeActivity.toggle();
    }

    /**
     * 初始化
     *
     * @param v
     */
    private void init(View v) {
        iv_photo = (CircularImageView) v.findViewById(R.id.iv_photo);
        iv_home_bg = (ImageView) v.findViewById(R.id.iv_home_bg);
        tv_nickname = (TextView) v.findViewById(R.id.tv_nickname);
        iv_photo.setOnClickListener(this);
        v.findViewById(R.id.ll_friend).setOnClickListener(this);
        v.findViewById(R.id.ll_all_zixi).setOnClickListener(this);
        v.findViewById(R.id.ll_history).setOnClickListener(this);
        v.findViewById(R.id.ll_setting).setOnClickListener(this);
    }

    private void initUserData() {
        if (currentUser != null) {

            ImageLoader.getInstance().displayImage(currentUser.getAvatar(), iv_photo);

//            if (TextUtils.isEmpty(currentUser.getAvatar()))
//                iv_home_bg.setBackgroundResource(R.drawable.logo);
//            else {
//                Log.i("imgaeloader", "getAvatar: " + currentUser.getAvatar());
//            }

//            if (TextUtils.isEmpty(currentUser.getHomeBg()))
//                iv_home_bg.setBackgroundResource(R.drawable.logo);
//            else {
//                iv_home_bg.setBackgroundResource(R.drawable.logo);
//            }
            tv_nickname.setText(currentUser.getNick());
        }
    }

}

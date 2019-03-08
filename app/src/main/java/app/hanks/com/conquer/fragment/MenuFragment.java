package app.hanks.com.conquer.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        init(v);
        return v;
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

    @Override
    public void onClick(View v) {
        // ((MainActivity) context).toggle();
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

    public void initUserData() {
        if (currentUser != null) {

            if (TextUtils.isEmpty(currentUser.getAvatar()))
                iv_home_bg.setBackgroundResource(R.drawable.logo);
            else{
                ImageLoader.getInstance().displayImage(currentUser.getAvatar(), iv_photo);
                Log.i("imgaeloader", "getAvatar: "+currentUser.getAvatar());
            }

            if (TextUtils.isEmpty(currentUser.getHomeBg()))
                iv_home_bg.setBackgroundResource(R.drawable.logo);
            else{
                iv_home_bg.setBackgroundResource(R.drawable.logo);
//                ImageLoader.getInstance().displayImage(currentUser.getHomeBg(), iv_home_bg);
            }
            tv_nickname.setText(currentUser.getNick());
        }
    }
}

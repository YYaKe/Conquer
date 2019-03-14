package app.hanks.com.conquer.config;

import android.os.Environment;

/**
 * 存放系统常量
 * @author wmf
 */
public class Constants {

	/**
	 * Bmob的key
	 */
	public static final String BMOB_KEY = "913569cce4e092a100029804ab5b5062";

	/**
	 * QQ的key
	 */
	public static final String QQ_KEY = "1108142585";
	public static final String Weibo_KEY = "2309789244";

	/**
	 * 广播意图
	 */
	public static final String ACTION_REGISTER_SUCCESS_FINISH = "register.success.finish";
	public static final String ACTION_DESTORY_PLAYER = "register.destroy.player";
	public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";
	/**
	 * 存放发送图片的目录
	 */
	public static String PICTURE_PATH = Environment.getExternalStorageDirectory() + "/pnszx/image/";

	/**
	 * 我的头像保存目录
	 */
	public static String MyAvatarDir = Environment.getExternalStorageDirectory() + "/pnszx/avatar/";
	/**
	 * 拍照回调
	 */
	public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;// 拍照修改头像
	public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;// 本地相册修改头像
	public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;// 系统裁剪头像

	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;// 拍照
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;// 本地图片
	public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;// 位置
	public static final int REQUESTCODE_TAKE_CROP = 0x000004;
	public static final String EXTRA_STRING = "extra_string";
	/**
	 * 调用系统相册拍照时返回图片的uri
	 */
	public static final String IMG_URI = "img_uri";
	public static final int MAIN_MYZIXI_LIMIT = 4;
	public static final String SP_SORT = "sort";
	public static final String COURSE_DB_NAME = "kecheng";
	/**
	 * 优先级
	 */
	public static final int URGENT_IMPROTANT = 1;//紧急重要
	public static final int IMPROTANT_NOT_URGENT = 2;//重要不紧急
	public static final int URGETN_NOT_IMPORTANT = 3;//紧急不重要
	public static final int NOT_URGENT_NOT_IMPORTANT = 4;//不紧急不重要
	/**
	 * 清单类型
	 */
	public static final int USER_ONE = 1;//z只用这一个
	public static final int WORK = 2;//工作
	public static final int LEARN = 3;//学习
	public static final int LIFE = 4;//生活


}

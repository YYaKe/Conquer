package app.hanks.com.conquer.helper;

import android.app.Activity;

import com.xujiaji.happybubble.BubbleLayout;

import app.hanks.com.conquer.R;


public class BubbleCreator {

    public static BubbleLayout get(Activity activity) {
        BubbleLayout bl = new BubbleLayout(activity);
        bl.setShadowColor(activity.getResources().getColor(R.color.colorAccent));
        return bl;
    }

    public static BubbleLayout getAmber(Activity activity) {
        BubbleLayout bl = new BubbleLayout(activity);
        bl.setBubbleColor(activity.getResources().getColor(R.color.amber_800));
        bl.setShadowColor(activity.getResources().getColor(R.color.overlay_dark_80));
        return bl;
    }
}

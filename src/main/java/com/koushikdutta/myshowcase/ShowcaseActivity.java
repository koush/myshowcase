package com.koushikdutta.myshowcase;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.boilerplate.WindowChromeCompatActivity;

import java.util.ArrayList;

/**
 * Created by koush on 9/29/16.
 */

public class ShowcaseActivity extends WindowChromeCompatActivity {
    public abstract static class SimpleFragment extends Fragment {
        public abstract int appIcon();
        public abstract String appName();
        public abstract String appDescription();
        public abstract String appPackageName();
        public abstract int appColorPrimary();

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View ret = inflater.inflate(R.layout.showcase_fragment, container, false);
            ((ImageView)ret.findViewById(R.id.icon)).setImageResource(appIcon());
            ((TextView)ret.findViewById(R.id.name)).setText(appName());
            ((TextView)ret.findViewById(R.id.description)).setText(appDescription());

            ret.findViewById(R.id.next)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ShowcaseActivity)getActivity()).nextPage();
                }
            });

            ret.findViewById(R.id.download)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String appPackageName = appPackageName();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                    }
                }
            });

            ShowcaseActivity activity = (ShowcaseActivity)getActivity();
            if (activity.adapter.getItem(activity.adapter.getCount() - 1) == this)
                ret.findViewById(R.id.next).setVisibility(View.GONE);

            return ret;
        }
    }


    public static class AllCastFragment extends SimpleFragment {
        @Override
        public int appIcon() {
            return R.drawable.allcast_web_hi_res_512;
        }

        @Override
        public String appName() {
            return "AllCast";
        }

        @Override
        public String appDescription() {
            return "Send videos to your Chromecast, Roku, Xbox, Apple TV, or Smart TV";
        }

        @Override
        public String appPackageName() {
            return "com.koushikdutta.cast";
        }

        @Override
        public int appColorPrimary() {
            return 0xffc74b46;
        }
    }

    public static class VysorFragment extends SimpleFragment {
        @Override
        public int appIcon() {
            return R.drawable.vysor_web_hi_res_512;
        }

        @Override
        public String appName() {
            return "Vysor";
        }

        @Override
        public String appDescription() {
            return "Vysor lets you view and control your Android on your computer.";
        }

        @Override
        public String appPackageName() {
            return "com.koushikdutta.vysor";
        }

        @Override
        public int appColorPrimary() {
            return 0xff008fcc;
        }
    }

    public static class MirrorFragment extends SimpleFragment {
        @Override
        public int appIcon() {
            return R.drawable.mirror_web_hi_res_512;
        }

        @Override
        public String appName() {
            return "Mirror";
        }

        @Override
        public String appDescription() {
            return "Record your Android's screen or cast it to Fire TV, Apple TV, or Chrome.";
        }

        @Override
        public String appPackageName() {
            return "com.koushikdutta.mirror";
        }

        @Override
        public int appColorPrimary() {
            return 0xffffc117;
        }
    }

    public static class HeliumFragment extends SimpleFragment {
        @Override
        public int appIcon() {
            return R.drawable.helium_web_hi_res_512;
        }

        @Override
        public String appName() {
            return "Helium";
        }

        @Override
        public String appDescription() {
            return "Backup, restore, and sync your apps.";
        }

        @Override
        public String appPackageName() {
            return "com.koushikdutta.backup";
        }

        @Override
        public int appColorPrimary() {
            return 0xff4CAF50;
        }
    }

    public static class InkwireFragment extends SimpleFragment {
        @Override
        public int appIcon() {
            return R.drawable.inkwire_web_hi_res_512;
        }

        @Override
        public String appName() {
            return "Inkwire";
        }

        @Override
        public String appDescription() {
            return "Inkwire lets you easily share your screen to another Android user.";
        }

        @Override
        public String appPackageName() {
            return "com.koushikdutta.inkwire";
        }

        @Override
        public int appColorPrimary() {
            return 0xff3F51B5;
        }
    }

    class PagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        ArrayList<Fragment> fragments = new ArrayList<>();

        public PagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            LinearLayout dots = (LinearLayout)findViewById(R.id.dots);
            for (int i = 0; i < dots.getChildCount(); i++) {
                View dot = dots.getChildAt(i);
                dot.setEnabled(position == i);
            }

            SimpleFragment f = (SimpleFragment)object;
            int colorPrimary = f.appColorPrimary();
            currentAnimation = fadeBackground(ShowcaseActivity.this, currentAnimation, colorPrimary);
        }
    }

    ValueAnimator currentAnimation;
    private ValueAnimator fadeBackground(Context context, ValueAnimator currentAnimation, int color) {
        if (currentAnimation != null) {
//            currentAnimation.end();
            if ((int)currentAnimation.getAnimatedValue() == color)
                return currentAnimation;
            currentAnimation.cancel();
        }
        final View root = findViewById(R.id.root);

        int currentColor;
        if (currentAnimation == null)
            currentColor = color;
        else
            currentColor = (int)currentAnimation.getAnimatedValue();

        currentAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), currentColor, color);
        currentAnimation.setDuration(context.getResources().getInteger(android.R.integer.config_longAnimTime));
        currentAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentColor = (int) animation.getAnimatedValue();
                root.setBackgroundColor(currentColor);
            }
        });
        currentAnimation.start();
        return currentAnimation;
    }

    void nextPage() {
        int item = pager.getCurrentItem();
        if (item < pager.getChildCount())
            pager.setCurrentItem(item + 1);
    }

    NonSwipeableViewPager pager;
    PagerAdapter adapter;

    void addFragment(PagerAdapter adapter, LinearLayout dots, SimpleFragment f) {
        if (f.appPackageName().equals(getPackageName()))
            return;

        adapter.fragments.add(f);
        dots.addView(getLayoutInflater().inflate(R.layout.dot, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.showcase);

        pager = (NonSwipeableViewPager) findViewById(R.id.pager);
        pager.allowSwiping(true);

        LinearLayout dots = (LinearLayout)findViewById(R.id.dots);

        adapter = new PagerAdapter();

        addFragment(adapter, dots, new AllCastFragment());
        addFragment(adapter, dots, new VysorFragment());
        addFragment(adapter, dots, new MirrorFragment());
        addFragment(adapter, dots, new HeliumFragment());
        addFragment(adapter, dots, new InkwireFragment());

        adapter.notifyDataSetChanged();

        pager.setAdapter(adapter);
    }
}

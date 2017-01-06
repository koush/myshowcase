package com.koushikdutta.myshowcase;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        public abstract ClockworkModProduct getProduct();

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View ret = inflater.inflate(R.layout.showcase_fragment, container, false);
            ((ImageView)ret.findViewById(R.id.icon)).setImageResource(getProduct().appIcon());
            ((TextView)ret.findViewById(R.id.name)).setText(getProduct().appName());
            ((TextView)ret.findViewById(R.id.description)).setText(getProduct().appDescription());

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
                    getProduct().openPlayStore(getActivity());
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
        public ClockworkModProduct getProduct() {
            return ClockworkModProduct.ALLCAST;
        }
    }

    public static class VysorFragment extends SimpleFragment {
        @Override
        public ClockworkModProduct getProduct() {
            return ClockworkModProduct.VYSOR;
        }
    }

    public static class MirrorFragment extends SimpleFragment {
        @Override
        public ClockworkModProduct getProduct() {
            return ClockworkModProduct.MIRROR;
        }
    }

    public static class HeliumFragment extends SimpleFragment {
        @Override
        public ClockworkModProduct getProduct() {
            return ClockworkModProduct.HELIUM;
        }
    }

    public static class InkwireFragment extends SimpleFragment {
        @Override
        public ClockworkModProduct getProduct() {
            return ClockworkModProduct.INKWIRE;
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
            int colorPrimary = f.getProduct().appColorPrimary();
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
        if (f.getProduct().appPackageName().equals(getPackageName()))
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

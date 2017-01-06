package com.koushikdutta.myshowcase;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.Random;

/**
 * Created by koush on 10/1/16.
 */

public abstract class ClockworkModProduct {
    public abstract int appIcon();
    public abstract String appName();
    public abstract String appDescription();
    public abstract String appPackageName();
    public abstract int appColorPrimary();

    public void openPlayStore(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
        }
    }

    public static ClockworkModProduct ALLCAST = new ClockworkModProduct() {
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
            return "Cast to your Chromecast, Roku, Xbox, Apple TV, or Smart TV";
        }

        @Override
        public String appPackageName() {
            return "com.koushikdutta.cast";
        }

        @Override
        public int appColorPrimary() {
            return 0xffc74b46;
        }
    };
    public static ClockworkModProduct VYSOR = new ClockworkModProduct() {
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
            return "View and control your Android on your PC.";
        }

        @Override
        public String appPackageName() {
            return "com.koushikdutta.vysor";
        }

        @Override
        public int appColorPrimary() {
            return 0xff008fcc;
        }
    };
    public static ClockworkModProduct MIRROR = new ClockworkModProduct() {
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
            return "Android screen recording. Mirror to Fire TV, Apple TV, or Chrome.";
        }

        @Override
        public String appPackageName() {
            return "com.koushikdutta.mirror";
        }

        @Override
        public int appColorPrimary() {
            return 0xffff7d19;
        }
    };
    public static ClockworkModProduct HELIUM = new ClockworkModProduct() {
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
    };
    public static ClockworkModProduct INKWIRE = new ClockworkModProduct() {
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
            return "Easily share your screen to another Android user.";
        }

        @Override
        public String appPackageName() {
            return "com.koushikdutta.inkwire";
        }

        @Override
        public int appColorPrimary() {
            return 0xff3F51B5;
        }
    };

    public static ClockworkModProduct[] ALL_PRODUCTS = new ClockworkModProduct[] {
            ALLCAST,
            VYSOR,
            MIRROR,
            HELIUM,
            INKWIRE,
    };

    public static ClockworkModProduct getRandom(Context context) {
        int random = Math.abs(new Random().nextInt()) % ALL_PRODUCTS.length;
        ClockworkModProduct ret = ALL_PRODUCTS[random];
        if (ret.appPackageName().equals((context.getPackageName())))
            return getRandom(context);
        return ret;
    }
}

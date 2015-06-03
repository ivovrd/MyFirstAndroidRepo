package com.example.admin.myfinalapp;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * Created by ADMIN on 30.4.2015..
 */
public class CarDetails extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        Transition exitTrans = new Slide();
        getWindow().setExitTransition(exitTrans);

        Transition reenterTrans = new Slide();
        getWindow().setReenterTransition(reenterTrans);

        Transition enterTrans = new Slide(Gravity.BOTTOM);
        getWindow().setEnterTransition(enterTrans);

        Integer itemPosition = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            itemPosition = extras.getInt("ITEM_POSITION");
        }

        ImageView image = (ImageView)findViewById(R.id.image);
        TextView model = (TextView)findViewById(R.id.model);
        TextView price = (TextView)findViewById(R.id.price);
        TextView description = (TextView)findViewById(R.id.description);
        final ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        final ActionBar actionBar = getActionBar();


        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scroll = scrollView.getScrollY();

                if(scroll > 600 && BoolVar.scrollStatus){
                    //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.material_deep_teal_200)));
                    //getWindow().setStatusBarColor(getResources().getColor(R.color.material_deep_teal_500));
                    tintSystemBars();
                    BoolVar.scrollStatus = false;
                }
                else if(scroll <= 600 && !BoolVar.scrollStatus){
                    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                    getWindow().setStatusBarColor(Color.parseColor("#00000000"));
                    BoolVar.scrollStatus = true;
                }
            }
        });

        Car car = DataStorage.cars[itemPosition];

        if(actionBar != null) {
            actionBar.setTitle(car.manufacturer);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        UrlImageViewHelper.setUrlDrawable(image, car.img, R.drawable.abc_cab_background_top_material);
        model.setText(car.model);
        price.setText(String.valueOf(car.price));
        description.setText(car.description);
    }

    private void tintSystemBars() {
        // Initial colors of each system bar.
        final int statusBarColor = Color.parseColor("#00000000");
        final int toolbarColor = Color.parseColor("#00000000");

        // Desired final colors of each bar.
        final int statusBarToColor = getResources().getColor(R.color.material_deep_teal_500);
        final int toolbarToColor = getResources().getColor(R.color.material_deep_teal_200);

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Use animation position to blend colors.
                float position = animation.getAnimatedFraction();

                // Apply blended color to the status bar.
                int blended = blendColors(statusBarColor, statusBarToColor, position);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(blended);
                }

                // Apply blended color to the ActionBar.
                blended = blendColors(toolbarColor, toolbarToColor, position);
                ColorDrawable background = new ColorDrawable(blended);
                getActionBar().setBackgroundDrawable(background);
            }
        });

        anim.setDuration(300).start();
    }

    private int blendColors(int from, int to, float ratio) {
        final float inverseRatio = 1f - ratio;

        final float a = Color.alpha(to) * ratio + Color.alpha(from) * inverseRatio;
        final float r = Color.red(to) * ratio + Color.red(from) * inverseRatio;
        final float g = Color.green(to) * ratio + Color.green(from) * inverseRatio;
        final float b = Color.blue(to) * ratio + Color.blue(from) * inverseRatio;

        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }
}

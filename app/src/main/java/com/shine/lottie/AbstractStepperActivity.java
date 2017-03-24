/*
Copyright 2016 StepStone Services

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.shine.lottie;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AbstractStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener,
        OnNavigationBarListener {
    public static final String TAG = AbstractStepperActivity.class.getSimpleName();
    private static final String CURRENT_STEP_POSITION_KEY = "position";
    private static final float[] ANIMATION_TIMES = new float[]{
            0,
            0.2116f,
            0.315f,
            0.6066f,
            0.695f,
            1f,
            1f
    };

    protected StepperLayout mStepperLayout;
    ViewPager viewPager;
    @BindView(R.id.animation_view)
	LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        setTitle("Stepper sample");

        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        viewPager = (ViewPager) mStepperLayout.findViewById(R.id.ms_stepPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setAnimationProgress(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        int startingStepPosition = savedInstanceState != null ? savedInstanceState.getInt(CURRENT_STEP_POSITION_KEY) : 0;
        mStepperLayout.setAdapter(new SampleFragmentStepAdapter(getSupportFragmentManager(), this), startingStepPosition);
        mStepperLayout.setListener(this);
        setViewPagerScroller();
    }

    private void setAnimationProgress(int position, float positionOffset) {
        float startProgress = ANIMATION_TIMES[position];
        float endProgress = ANIMATION_TIMES[position + 1];

        animationView.setProgress(lerp(startProgress, endProgress, positionOffset));

    }

    private float lerp(float startValue, float endValue, float f) {
        return startValue + f * (endValue - startValue);
    }

    private void setViewPagerScroller() {
        //noinspection TryWithIdenticalCatches
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(this, (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    //Change Scroll Speed
                    super.startScroll(startX, startY, dx, dy, duration * 10);
                }
            };
            scrollerField.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }


    @LayoutRes
    protected abstract int getLayoutResId();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, mStepperLayout.getCurrentStepPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        final int currentStepPosition = mStepperLayout.getCurrentStepPosition();
        if (currentStepPosition > 0) {
            mStepperLayout.setCurrentStepPosition(currentStepPosition - 1);
        } else {
            finish();
        }
    }

    @Override
    public void onCompleted(View completeButton) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
//        Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReturn() {
        finish();
    }

    @Override
    public void onChangeEndButtonsEnabled(boolean enabled) {
        mStepperLayout.setNextButtonVerificationFailed(!enabled);
        mStepperLayout.setCompleteButtonVerificationFailed(!enabled);
    }
}

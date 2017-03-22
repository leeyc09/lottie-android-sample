package com.shine.lottie;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.shine.lottie.step.StepFragmentSample;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class SampleFragmentStepAdapter extends AbstractFragmentStepAdapter {

    public SampleFragmentStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        return new StepViewModel.Builder(context)
                .setTitle(R.string.tab_title)
                .create();
    }

    @Override
    public Step createStep(int position) {
        switch (position) {
            case 0:
                return StepFragmentSample.newInstance(R.layout.fragment_step, R.string.step_1_str);
            case 1:
                return StepFragmentSample.newInstance(R.layout.fragment_step, R.string.step_2_str);
            case 2:
                return StepFragmentSample.newInstance(R.layout.fragment_step, R.string.step_3_str);
            case 3:
                return StepFragmentSample.newInstance(R.layout.fragment_step, R.string.step_4_str);
            case 4:
                return StepFragmentSample.newInstance(R.layout.fragment_step, R.string.step_5_str);
            case 5:
                return StepFragmentSample.newInstance(R.layout.fragment_step, R.string.step_6_str);
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 6;
    }
}
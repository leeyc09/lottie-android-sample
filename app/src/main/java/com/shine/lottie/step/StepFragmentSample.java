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

package com.shine.lottie.step;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.shine.lottie.OnNavigationBarListener;
import com.shine.lottie.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class StepFragmentSample extends Fragment implements Step {

    private static final String CLICKS_KEY = "clicks";

    private static final int TAP_THRESHOLD = 1;

    private static final String LAYOUT_RESOURCE_ID_ARG_KEY = "messageResourceId";
    private static final String STRING_RESOURCE_ID_ARG_KEY = "stringResourceId";

    private int i = 0;

    private Button button;
    private TextView textView;

    @Nullable
    private OnNavigationBarListener onNavigationBarListener;

    public static StepFragmentSample newInstance(@LayoutRes int layoutResId, @StringRes int string) {
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RESOURCE_ID_ARG_KEY, layoutResId);
        args.putInt(STRING_RESOURCE_ID_ARG_KEY, string);
        StepFragmentSample fragment = new StepFragmentSample();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNavigationBarListener) {
            onNavigationBarListener = (OnNavigationBarListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getArguments().getInt(LAYOUT_RESOURCE_ID_ARG_KEY), container, false);
        if (savedInstanceState != null) {
            i = savedInstanceState.getInt(CLICKS_KEY);
        }

        updateNavigationBar();

        textView = (TextView) v.findViewById(R.id.text_string);
        textView.setText(getArguments().getInt(STRING_RESOURCE_ID_ARG_KEY));

        button = (Button) v.findViewById(R.id.button);
        button.setText(Html.fromHtml("Taps: <b>" + i + "</b>"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setText(Html.fromHtml("Taps: <b>" + (++i) + "</b>"));
                updateNavigationBar();
            }
        });

        return v;
    }

    @Override
    public VerificationError verifyStep() {
        return isAboveThreshold() ? null : new VerificationError("Click " + (TAP_THRESHOLD - i) + " more times!");
    }

    private boolean isAboveThreshold() {
        return i >= TAP_THRESHOLD;
    }

    @Override
    public void onSelected() {
        updateNavigationBar();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        button.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
    }

    private void updateNavigationBar() {
        if (onNavigationBarListener != null) {
            onNavigationBarListener.onChangeEndButtonsEnabled(isAboveThreshold());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CLICKS_KEY, i);
        super.onSaveInstanceState(outState);
    }

}

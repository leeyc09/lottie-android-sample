package com.shine.lottie;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class motionPolymorpheActivity extends AppCompatActivity {

	@BindView(R.id.animation_view)
	LottieAnimationView animationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		ButterKnife.bind(this);
		animationView.setAnimation("data_poli.json", LottieAnimationView.CacheStrategy.Weak);
		animationView.loop(true);
		animationView.playAnimation();
		animationView.addAnimatorListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
	}
}

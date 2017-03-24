package com.shine.lottie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.button_intro)
	Button btn_intro;
	@BindView(R.id.button_stepper)
	Button btn_stepper;
	@BindView(R.id.button_polymorphe)
	Button btn_polymorphe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		btn_intro.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, IntroActivity.class));
			}
		});

		btn_stepper.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, DefaultDotsActivity.class));
			}
		});

		btn_polymorphe.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, motionPolymorpheActivity.class));
			}
		});
	}
}

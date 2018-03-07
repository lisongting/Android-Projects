package com.lst.gdx;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * 测试使用libGdx加载三维模型文件
 * 注：需要先将模型文件(obj)转换为.g3dj文件。Dae文件不建议直接转.g3dj
 */
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		Toast.makeText(this, "模型加载中....", Toast.LENGTH_LONG).show();

		initialize(new ModelLoaderTest(), config);
	}
}

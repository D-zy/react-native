package com.dnreactnative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dnreactnative.MainActivity;
import com.dnreactnative.MainApplication;
import com.dnreactnative.R;


/**
 * 调式页面
 */
public class TestReactActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private EditText url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_react);
        url = findViewById(R.id.url);
        sharedPreferences = getSharedPreferences("webApp", Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(sharedPreferences.getString("URL", ""))) {
            url.setText(sharedPreferences.getString("URL", ""));
        } else {
            url.setText("");
        }
    }


    /**
     * 加载
     *
     * @param view
     */
    public void loadBundle(View view) {
        if (saveIp()) {
            if (!sharedPreferences.getBoolean("FIRST", false)) {
                sharedPreferences.edit().putBoolean("FIRST", true).apply();
                reloadJs(view);
            }
            openReactActivity();
        }
    }

    /**
     * handleReloadJS
     *
     * @param view
     */
    public void reloadJs(View view) {
        if (saveIp()) {
            MainApplication.getInstance().getReactNativeHost().getReactInstanceManager()
                    .getDevSupportManager().handleReloadJS();
        }
    }

    /**
     * 显示配置dialog
     *
     * @param view
     */
    public void showConfig(View view) {
        MainApplication.getInstance().getReactNativeHost()
                .getReactInstanceManager().getDevSupportManager().setDevSupportEnabled(true);
        MainApplication.getInstance().getReactNativeHost()
                .getReactInstanceManager().showDevOptionsDialog();
    }

    /**
     * 保存ip地址
     */
    private boolean saveIp() {
        String ip = url.getText().toString().trim();
        if (!TextUtils.isEmpty(ip)) {
            SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor mEditor = mPerferences.edit();
            mEditor.putString("debug_http_host", ip);
            mEditor.apply();
            sharedPreferences.edit().putString("URL", ip).apply();
            return true;
        } else {
            Toast.makeText(this, "URL error", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void loadAssets(View view) {
        openReactActivity();
    }

    private void openReactActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

}

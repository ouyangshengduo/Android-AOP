package senduo.com.aopdemo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

import senduo.com.aopdemo.annotation.CalculateDuration;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "senduo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @CalculateDuration("查询数据库")
    public void onFetchDatabase(View view){
        SystemClock.sleep(new Random().nextInt(2000));
    }

    @CalculateDuration("访问网络服务器")
    public void doNetAction(View view){
        SystemClock.sleep(new Random().nextInt(2000));
    }

    @CalculateDuration("保存本地文件")
    public void saveLocalFile(View view){
        SystemClock.sleep(new Random().nextInt(2000));
    }

    @CalculateDuration("做其他操作")
    public void onDoOtherSomething(View view){
        SystemClock.sleep(new Random().nextInt(2000));
    }


//    /**
//     * 模拟耗时操作：查询数据库
//     * @param view
//     *//*
//    public void onFetchDatabase(View view){
//        long begin = System.currentTimeMillis();
//        SystemClock.sleep(new Random().nextInt(2000));
//        long duration = System.currentTimeMillis() - begin;
//        Log.i(TAG,"本次查询数据库耗时: " + duration + "ms");
//    }
//
//    *//**
//     * 模拟耗时操作：访问网络服务器
//     * @param view
//     *//*
//    public void doNetAction(View view){
//        long begin = System.currentTimeMillis();
//        SystemClock.sleep(new Random().nextInt(2000));
//        long duration = System.currentTimeMillis() - begin;
//        Log.i(TAG,"本次访问网络服务器耗时: " + duration + "ms");
//    }
//
//
//    *//**
//     * 模拟耗时操作：保存本地文件
//     * @param view
//     *//*
//    public void saveLocalFile(View view){
//        long begin = System.currentTimeMillis();
//        SystemClock.sleep(new Random().nextInt(2000));
//        long duration = System.currentTimeMillis() - begin;
//        Log.i(TAG,"本次保存本地文件耗时: " + duration + "ms");
//    }
//
//    *//**
//     * 模拟耗时操作：做其他操作
//     * @param view
//     *//*
//    public void onDoOtherSomething(View view){
//        long begin = System.currentTimeMillis();
//        SystemClock.sleep(new Random().nextInt(2000));
//        long duration = System.currentTimeMillis() - begin;
//        Log.i(TAG,"本次操作耗时: " + duration + "ms");
//    }*/
}

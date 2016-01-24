package com.doudou.cn.testaddlayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
private static final String TAG = MainActivity.class.getSimpleName();
    private static String[] testName = {"日历", "360", "三国", "消除", "播放器",
            "游戏", "清理大师", "跑酷", "壁纸", "单机斗地主",
            "捕鱼达人3", "雷电2014(雷霆版)", "打车", "输入法",
            "动作", "免费单机", "手电筒", "网游", "视频", "休闲", "漫画",
            "飞行射击", "保卫萝卜", "塔防", "爸爸去哪儿2", "中国象棋", "宅女必备", "三国",
            "消除", "跑酷", "壁纸", "单机斗地主", "免费单机", "手电筒"};

    private Button button;
    private TextView tvContent;

    private TestViewGroup testViewGroup;

private  WordWrapView wordWrapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn_add);
        tvContent = (TextView) findViewById(R.id.tv_content);
        testViewGroup = (TestViewGroup) findViewById(R.id.test_content);

        initView();
        initWordView();
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("TestViewGroup", "onClick 添加一个textView");
                        TextView textview = new TextView(MainActivity.this);
                        textview.setText(tvContent.getText().toString().trim());
//                        testViewGroup.addView(textview);
                        wordWrapView.addView(textview);
                    }
                }
        );
    }


    private void initView() {
        LinearLayout parentLL = (LinearLayout) findViewById(R.id.content);  // contentView的布局

        int size = testName.length; // 添加Button的个数
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
//        layoutParams.setMargins(10, 3, 10, 3);
//        layoutParams.setMargins(0, 0, 0, 0);

        ArrayList<Button> childBtns = new ArrayList<Button>();
        int totoalBtns = 0;

        for (int i = 0; i < size; i++) {
            String item = testName[i];
            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int length = item.length();

            if (length < 4) {  // 根据字数来判断按钮的空间长度, 少于4个当一个按钮
                itemParams.weight = 1;
                totoalBtns++;
            } else if (length < 8) { // <8个两个按钮空间
                itemParams.weight = 2;
                totoalBtns += 2;
            } else {
                itemParams.weight = 3;
                totoalBtns += 3;
            }

            itemParams.width = 0;
//            itemParams.setMargins(1,1,1,1);
            Button childBtn = (Button) LayoutInflater.from(this).inflate(R.layout.button_layout, null);
            childBtn.setText(item);
            childBtn.setTag(item);
            childBtn.setLayoutParams(itemParams);
            childBtns.add(childBtn);

            if (totoalBtns >= 5) {
                LinearLayout horizLL = new LinearLayout(this);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for (Button addBtn : childBtns) {
                    horizLL.addView(addBtn);
                }
                parentLL.addView(horizLL);
                childBtns.clear();
                totoalBtns = 0;
            }
        }
//最后一行添加一下
        if (!childBtns.isEmpty()) {
            LinearLayout horizLL = new LinearLayout(this);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);
            horizLL.setLayoutParams(layoutParams);

            for (Button addBtn : childBtns) {
                horizLL.addView(addBtn);
            }
            parentLL.addView(horizLL);
            childBtns.clear();
            totoalBtns = 0;
        }
    }


    public void initWordView() {
         wordWrapView = (WordWrapView) findViewById(R.id.word_wrap_view);
        int size = testName.length; // 添加Button的个数
        for (int i = 0; i < size; i++) {
            TextView textview = new TextView(MainActivity.this);
            textview.setText(testName[i]);
            wordWrapView.addView(textview);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

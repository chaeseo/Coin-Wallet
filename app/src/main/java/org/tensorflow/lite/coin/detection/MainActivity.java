package org.tensorflow.lite.coin.detection;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ViewPager 및 어댑터 설정
        ViewPager viewPager = findViewById(R.id.viewPager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // TabLayout과 ViewPager 연결
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // 각 탭에 이미지 설정
        tabLayout.getTabAt(0).setIcon(R.drawable.camera_w);
        tabLayout.getTabAt(1).setIcon(R.drawable.money_w);

        // FirstFragment를 추가
        addFragment(new FirstFragment());
    }

    private void addFragment(Fragment fragment) {
        // FragmentTransaction을 사용하여 프래그먼트 추가
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}


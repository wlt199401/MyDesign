package com.example.wlt.mydesign;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wlt.mydesign.Presenter.HomePresenter;
import com.example.wlt.mydesign.Views.MvpView;
import com.example.wlt.mydesign.Views.fragment.GoodsFragment;
import com.example.wlt.mydesign.Views.fragment.HomeFragment;
import com.example.wlt.mydesign.Views.fragment.MineFragment;
import com.example.wlt.mydesign.Views.fragment.OrderFragment;
import com.example.wlt.mydesign.base.BaseActivity;
import com.example.wlt.mydesign.base.StatusBarUtils;


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,ViewPager.OnPageChangeListener,MvpView{

    private Toolbar toolbar;
    private TextView tb_text;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ViewPager viewPager;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_home;
    private RadioButton rb_order;
    private RadioButton rb_goods;
    private RadioButton rb_mine;
    private MyFragmentPagerAdapter frgAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_design);
//        StatusBarUtils.setActivityTranslucent(this);
        StatusBarUtils.setStatusBarColor(this,"#059f9e");
        initView();
        initEvent();
        initState();
    }

    //绑定view
    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        tb_text = findViewById(R.id.tb_text);
        drawerLayout = findViewById(R.id.draw_layout);
        navView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.viewPager);
        setSupportActionBar(toolbar);
        rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rb_home = findViewById(R.id.rb_home);
        rb_order = findViewById(R.id.rb_order);
        rb_goods = findViewById(R.id.rb_goods);
        rb_mine = findViewById(R.id.rb_mine);
        frgAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(frgAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        rg_tab_bar.setOnCheckedChangeListener(this);
        rb_home.setChecked(true);
    }

    //事件view
    private void initEvent(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//导航按钮显示
            actionBar.setHomeAsUpIndicator(R.mipmap.menu_up_menu);
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();//关闭抽屉
                Toast.makeText(MainActivity.this, "点击了"+item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
            default:
                break;
        }
        return true;
    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if(state==2){
            switch (viewPager.getCurrentItem()){
                case PAGE_ONE:
                    rb_home.setChecked(true);
                    tb_text.setText(R.string.tab_home);
                    break;
                case PAGE_TWO:
                    rb_order.setChecked(true);
                    tb_text.setText(R.string.tab_order);
                    break;
                case PAGE_THREE:
                    rb_goods.setChecked(true);
                    tb_text.setText(R.string.tab_goods);
                    break;
                case PAGE_FOUR:
                    rb_mine.setChecked(true);
                    tb_text.setText(R.string.tab_mine);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                viewPager.setCurrentItem(PAGE_ONE);
                tb_text.setText(R.string.tab_home);
                break;
            case R.id.rb_order:
                viewPager.setCurrentItem(PAGE_TWO);
                tb_text.setText(R.string.tab_order);
                break;
            case R.id.rb_goods:
                viewPager.setCurrentItem(PAGE_THREE);
                tb_text.setText(R.string.tab_goods);
                break;
            case R.id.rb_mine:
                viewPager.setCurrentItem(PAGE_FOUR);
                tb_text.setText(R.string.tab_mine);
                break;
        }
    }

    @Override
    public void showData(String data) {
        Log.d("MainActivity","ss");
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter{

        private final int PAGER_COUNT = 4;

        private HomeFragment homeFragment;
        private OrderFragment orderFragment;
        private GoodsFragment goodsFragment;
        private MineFragment mineFragment;
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            homeFragment = new HomeFragment();
            orderFragment = new OrderFragment();
            goodsFragment = new GoodsFragment();
            mineFragment = new MineFragment();
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case MainActivity.PAGE_ONE:
                    fragment = homeFragment;
                    break;
                case MainActivity.PAGE_TWO:
                    fragment = orderFragment;
                    break;
                case MainActivity.PAGE_THREE:
                    fragment = goodsFragment;
                    break;
                case MainActivity.PAGE_FOUR:
                    fragment = mineFragment;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return PAGER_COUNT;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

    }
    private void initState() {

    }
}

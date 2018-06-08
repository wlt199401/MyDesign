package com.example.wlt.mydesign.Views.imageSlideShow;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wlt.mydesign.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25122 on 2018/6/5.
 */

public class ImageSlideView extends FrameLayout {

    private View mView;
    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout slide_dot;
    private int count;
    private boolean isAutoPlay;
    private Handler handler;
    private List<View> mList;
    private List<ImageSlideBean> imageSlideBeanList;
    private SparseBooleanArray isLarge;
    private Animator animatorToLarge;
    private Animator animatorToSmall;
    private int currentItem;
    private int dotSize = 12;
    private int dotSpace = 12;
    private int delay = 2000;
    public ImageSlideView(@NonNull Context context) {
        this(context,null);
    }

    public ImageSlideView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ImageSlideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
        initAnimator();
        initData();
    }

    private void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.slide_main_view,this,true);
        mViewPager = mView.findViewById(R.id.slide_image);
        slide_dot = mView.findViewById(R.id.slide_dot);
    }

    private void initAnimator(){
        animatorToLarge = AnimatorInflater.loadAnimator(mContext, R.animator.scale_to_large);
        animatorToSmall = AnimatorInflater.loadAnimator(mContext, R.animator.scale_to_small);
    }

    private void initData(){
        imageSlideBeanList = new ArrayList<>();
    }

    public void setDotSize(int dotSize) {
        this.dotSize = dotSize;
    }

    public void setDotSpace(int dotSpace) {
        this.dotSpace = dotSpace;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void addImageUrl(String imagePath){
        ImageSlideBean imageSlideBean = new ImageSlideBean();
        imageSlideBean.setImagePath(imagePath);
        imageSlideBeanList.add(imageSlideBean);
    }
    public void addImageUrl(String imagePath,String imageTitle){
        ImageSlideBean imageSlideBean = new ImageSlideBean();
        imageSlideBean.setImagePath(imagePath);
        imageSlideBean.setImageTitle(imageTitle);
        imageSlideBeanList.add(imageSlideBean);
    }
    public void addImageUrl(ImageSlideBean imageSlideBean){
        imageSlideBeanList.add(imageSlideBean);
    }
    public void addImageUrl(List<ImageSlideBean> list){
        imageSlideBeanList = list;
    }
    //设置完成最终提交
    public void commit(){
        if(imageSlideBeanList != null){
            count = imageSlideBeanList.size();
            setViewPager(imageSlideBeanList);
            setIndicator();
            starPlay();
        }else {
            Log.e("imageSlide","数据为空");
        }
    }

    /**
     * 设置指示器
     */
    private void setIndicator() {
        isLarge = new SparseBooleanArray();
        // 记得创建前先清空数据，否则会受遗留数据的影响。
        slide_dot.removeAllViews();
        for (int i = 0; i < count; i++) {
            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.dot_unselected);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotSize, dotSize);
            layoutParams.leftMargin = dotSpace / 2;
            layoutParams.rightMargin = dotSpace / 2;
            layoutParams.topMargin = dotSpace / 2;
            layoutParams.bottomMargin = dotSpace / 2;
            slide_dot.addView(view, layoutParams);
            isLarge.put(i, false);
        }
        slide_dot.getChildAt(0).setBackgroundResource(R.drawable.dot_selected);
        animatorToLarge.setTarget(slide_dot.getChildAt(0));
        animatorToLarge.start();
        isLarge.put(0, true);
    }

    /**
     * 开始自动播放图片
     */
    private void starPlay() {
        // 如果少于2张就不用自动播放了
        if (count < 2) {
            isAutoPlay = false;
        } else {
            isAutoPlay = true;
            handler = new Handler();
            handler.postDelayed(task, delay);
        }
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay) {
                // 位置循环
                currentItem = currentItem % (count + 1) + 1;
                Log.d("ImageSlide", "run: "+currentItem);
                // 正常每隔3秒播放一张图片
                mViewPager.setCurrentItem(currentItem);
                handler.postDelayed(task, delay);
            } else {
                // 如果处于拖拽状态停止自动播放，会每隔5秒检查一次是否可以正常自动播放。
                handler.postDelayed(task, 5000);
            }
        }
    };
    //设置ViewPager
    private void setViewPager(List<ImageSlideBean> imageSlideBeanList){
        setViewList(imageSlideBeanList);
        mViewPager.setAdapter(new ImagePagerAdapter());
        currentItem = 1;
        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //遍历一次子view，设置相应背景
                for (int i = 0; i < slide_dot.getChildCount(); i++) {
                    if (i == position - 1) {// 被选中
                        slide_dot.getChildAt(i).setBackgroundResource(R.drawable.dot_selected);
                        if (!isLarge.get(i)) {
                            animatorToLarge.setTarget(slide_dot.getChildAt(i));
                            animatorToLarge.start();
                            isLarge.put(i, true);
                        }
                    } else {// 未被选中
                        slide_dot.getChildAt(i).setBackgroundResource(R.drawable.dot_unselected);
                        if (isLarge.get(i)) {
                            animatorToSmall.setTarget(slide_dot.getChildAt(i));
                            animatorToSmall.start();
                            isLarge.put(i, false);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    // 闲置中
                    case ViewPager.SCROLL_STATE_IDLE:
                        // “偷梁换柱”
                        if (mViewPager.getCurrentItem() == 0) {
                            mViewPager.setCurrentItem(count, false);
                        } else if (mViewPager.getCurrentItem() == count + 1) {
                            mViewPager.setCurrentItem(1, false);
                        }
                        currentItem = mViewPager.getCurrentItem();
                        isAutoPlay = true;
                        break;
                    // 拖动中
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isAutoPlay = false;
                        break;
                    // 设置中
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isAutoPlay = true;
                        break;
                }
            }
        });
    }

    private void setViewList(List<ImageSlideBean> imageSlideBeanList){
        mList = new ArrayList<>();
        for(int i=0;i<count+2;i++){
            View view = LayoutInflater.from(mContext).inflate(R.layout.slide_title_view,null);
            ImageView ivImage = view.findViewById(R.id.iv_image);
            TextView tvTitle = view.findViewById(R.id.tv_title);
            if(i==0){
                Glide.with(mContext).load(imageSlideBeanList.get(count-1).getImagePath()).into(ivImage);
                tvTitle.setText(imageSlideBeanList.get(count-1).getImageTitle());
            }else if(i==count+1){
                Glide.with(mContext).
                        load(imageSlideBeanList.get(0).getImagePath()).into(ivImage);
                tvTitle.setText(imageSlideBeanList.get(0).getImageTitle());
            }else {
                Glide.with(mContext).
                        load(imageSlideBeanList.get(i - 1).getImagePath()).into(ivImage);
                tvTitle.setText(imageSlideBeanList.get(i - 1).getImageTitle());
            }
            mList.add(view);
        }
    }

    //释放资源
    public void releaseResource(){
        mContext = null;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }
    class ImagePagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mList.get(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v,position-1);
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    }
}

package com.example.wlt.mydesign.View.fragment.imageSlideShow;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AndroidRuntimeException;
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
import java.util.concurrent.CompletionService;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

/**
 * Created by 25122 on 2018/6/6.
 */

public class MyImageSlideView extends FrameLayout {

    private static final String TAG = "MySlideView";
    private View view;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private Context context;
    private List<View> viewList;
    private List<ImageSlideBean> imageSlideBeanList;
    private Animator animatorToLarge;
    private Animator animatorToSmall;
    private SparseBooleanArray isLarge;
    private boolean isAutoPlay;
    private Disposable disposable;
    private int count;
    private int currentItem;
    private int dotSize = 12;
    private int dotSpace = 12;
    private int delay = 3000;

    public MyImageSlideView(@NonNull Context context) {
        this(context, null);
    }

    public MyImageSlideView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageSlideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        imageSlideBeanList = new ArrayList<>();
        initView();
        initAnimator();
    }

    public void addImageUrl(String imagePath, String imageTitle) {
        ImageSlideBean imageSlideBean = new ImageSlideBean();
        imageSlideBean.setImagePath(imagePath);
        imageSlideBean.setImageTitle(imageTitle);
        imageSlideBeanList.add(imageSlideBean);
    }

    public void setImageSlideBeanList(List<ImageSlideBean> imageSlideBeanList) {
        this.imageSlideBeanList = imageSlideBeanList;
    }

    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.slide_main_view, this, true);
        viewPager = view.findViewById(R.id.slide_image);
        linearLayout = view.findViewById(R.id.slide_dot);
    }

    private void initAnimator() {
        animatorToLarge = AnimatorInflater.loadAnimator(context, R.animator.scale_to_large);
        animatorToSmall = AnimatorInflater.loadAnimator(context, R.animator.scale_to_small);
    }

    public void commit() {
        if (imageSlideBeanList != null) {
            count = imageSlideBeanList.size();
            setViewPager(imageSlideBeanList);
            setIndicator();
            starPlay();
        } else {
            Log.d(TAG, "data is null");
        }
    }

    private void starPlay() {
        if (count < 2) {
            isAutoPlay = false;
        } else {
            isAutoPlay = true;
            disposable = Flowable.interval(3, TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (isAutoPlay = true) {
                                if (currentItem == count - 1) {
                                    currentItem = 0;
                                } else {
                                    currentItem = currentItem + 1;
                                }
                            }
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            viewPager.setCurrentItem(currentItem);
                        }
                        });
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> e) throws Exception {

                }
            });
            }
    }

    private void setIndicator() {
        isLarge = new SparseBooleanArray();
        linearLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            View view = new View(context);
            view.setBackgroundResource(R.drawable.dot_unselected);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotSize, dotSize);
            layoutParams.leftMargin = dotSpace / 2;
            layoutParams.rightMargin = dotSpace / 2;
            layoutParams.topMargin = dotSpace / 2;
            layoutParams.bottomMargin = dotSpace / 2;
            linearLayout.addView(view, layoutParams);
            isLarge.put(i, false);
        }
        linearLayout.getChildAt(0).setBackgroundResource(R.drawable.dot_selected);
        animatorToLarge.setTarget(linearLayout.getChildAt(0));
        animatorToLarge.start();
        isLarge.put(0, true);
    }

    private void setViewPager(List<ImageSlideBean> imageSlideBeanList) {
        setViewList(imageSlideBeanList);
        viewPager.setAdapter(new MyPagerAdapter());
        currentItem = 0;
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < count; i++) {
                    if (i == position) {
                        linearLayout.getChildAt(i).setBackgroundResource(R.drawable.dot_selected);
                        if (!isLarge.get(i)) {
                            animatorToLarge.setTarget(linearLayout.getChildAt(i));
                            animatorToLarge.start();
                            isLarge.put(i, true);
                        }
                    } else {
                        linearLayout.getChildAt(i).setBackgroundResource(R.drawable.dot_unselected);
                        if (isLarge.get(i)) {
                            animatorToSmall.setTarget(linearLayout.getChildAt(i));
                            animatorToSmall.start();
                            isLarge.put(i, false);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    //没动
                    case ViewPager.SCROLL_STATE_IDLE:
                        currentItem = viewPager.getCurrentItem();
                        isAutoPlay = true;
                        break;
                    //拖动中
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isAutoPlay = true;
                        break;
                    //设置中
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isAutoPlay = true;
                        break;
                }
            }
        });
    }

    private void setViewList(List<ImageSlideBean> imageSlideBeanList) {
        viewList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.slide_title_view, null);
            ImageView iv_image = view.findViewById(R.id.iv_image);
            TextView tv_title = view.findViewById(R.id.tv_title);
            Glide.with(context).load(imageSlideBeanList.get(i).getImagePath()).into(iv_image);
            tv_title.setText(imageSlideBeanList.get(i).getImageTitle());
            viewList.add(view);
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = viewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }

    //释放资源
    public void releaseResource(){
        context = null;
        disposable.dispose();
    }
}

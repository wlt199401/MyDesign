package com.example.wlt.mydesign.View.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wlt.mydesign.R;
import com.example.wlt.mydesign.View.fragment.imageSlideShow.ImageSlideBean;
import com.example.wlt.mydesign.View.fragment.imageSlideShow.ImageSlideView;
import com.example.wlt.mydesign.View.fragment.imageSlideShow.MyImageSlideView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25122 on 2018/6/4.
 */

public class HomeFragment extends Fragment {

    MyImageSlideView imageSlideView;
    private List<String> imageUrlList;
    private List<String> titleList;
    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_home,null,false);
        imageSlideView = view.findViewById(R.id.slide_main);
        imageUrlList = new ArrayList<>();
        titleList = new ArrayList<>();
        initData();
        imageSlideView.commit();
        return view;
    }
    /**
     * 初始化数据
     */
    private void initData() {
        String[] imageUrls = {"http://pic3.zhimg.com/b5c5fc8e9141cb785ca3b0a1d037a9a2.jpg",
                "http://pic2.zhimg.com/551fac8833ec0f9e0a142aa2031b9b09.jpg",
                "http://pic2.zhimg.com/be6f444c9c8bc03baa8d79cecae40961.jpg",
                "http://pic1.zhimg.com/b6f59c017b43937bb85a81f9269b1ae8.jpg",
                "http://pic2.zhimg.com/a62f9985cae17fe535a99901db18eba9.jpg"};
        String[] titles = {"读读日报 24 小时热门 TOP 5 · 余文乐和「香港贾玲」乌龙绯闻",
                "写给产品 / 市场 / 运营的数据抓取黑科技教程",
                "学做这些冰冰凉凉的下酒宵夜，简单又方便",
                "知乎好问题 · 有什么冷门、小众的爱好？",
                "欧洲都这么发达了，怎么人均收入还比美国低"};
        for (int i = 0; i < 5; i++) {
            imageSlideView.addImageUrl(imageUrls[i], titles[i]);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        imageSlideView.releaseResource();//释放资源
    }
}

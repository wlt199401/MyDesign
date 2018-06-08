package com.example.wlt.mydesign.Views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wlt.mydesign.Bean.Goods;
import com.example.wlt.mydesign.Presenter.HomePresenter;
import com.example.wlt.mydesign.R;
import com.example.wlt.mydesign.Views.MvpView;
import com.example.wlt.mydesign.Views.imageSlideShow.MyImageSlideView;
import com.example.wlt.mydesign.base.net.GoodsService;
import com.example.wlt.mydesign.base.util.Internet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by 25122 on 2018/6/4.
 */

public class HomeFragment extends BaseFragment implements MvpView{

    HomePresenter presenter;
    private MyImageSlideView imageSlideView;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private View view;
    private List<String> imageUrlList;
    private List<String> titleList;

    public HomeFragment() {
    }

    @Override
    public int getContentViewId() {
        return R.layout.frg_home;
    }

    @Override
    protected void initAllMembersView(Bundle saveInstanceState) {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frg_home, null, false);
        initAllMembersView(savedInstanceState);
        initView();
        initData();
        imageSlideView.commit();
        return view;
    }


    private void initView() {
        imageSlideView = view.findViewById(R.id.slide_main);
        recyclerView = view.findViewById(R.id.home_recy);
        imageUrlList = new ArrayList<>();
        titleList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        presenter = new HomePresenter();
        presenter.attachView(this);
        presenter.getData("1");
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

    @Override
    public void showData(String data) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Goods>>(){}.getType();
        List<Goods> goodsList = gson.fromJson(data,type);
        adapter = new MyRecyclerViewAdapter(getActivity(),goodsList);
        recyclerView.setAdapter(adapter);
    }
    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
        private Context context;
        private List<Goods> list;

        public MyRecyclerViewAdapter(Context context, List<Goods> list) {
            this.context = context;
            this.list = list;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.item_image);
                textView = itemView.findViewById(R.id.item_text);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (context==null){
                context = parent.getContext();
            }
            View view = LayoutInflater.from(context).inflate(R.layout.recy_home_item,null,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(context).load(list.get(position).getPIC_THUMB()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }
}

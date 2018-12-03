package com.example.musicplayer.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.musicplayer.R;
import com.example.musicplayer.util.CommonUtil;
import com.github.florent37.materialviewpager.MaterialViewPager;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by 残渊 on 2018/11/25.
 */

public class AlbumContentFragment extends Fragment {
    private static final String TAG="AlbumContentFragment";

    public static final String ALBUM_ID_KEY="id";
    private static final String ALBUM_NAME_KEY="albumName";
    private static final  String SINGER_NAME_KEY="singerName";
    private static final  String ALBUM_PIC_KEY="albumPic";
    public static final  String PUBLIC_TIEM_KEY="publicTime";

    private String mAlbumName,mSingerNmae,mAlbumPic,mPublicTime,mId;

    private MaterialViewPager mViewPager;
    private Toolbar toolbar;
    private RelativeLayout mAlbumBackground;
    private TextView mSingerNameTv;
    private TextView mPublicTimeTv;
    private ImageView mAlbumPicIv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getBundle();
        View view = inflater.inflate(R.layout.fragment_album_content, container, false);
        mViewPager = view.findViewById(R.id.materialViewPager);
        toolbar = mViewPager.getToolbar();
        mAlbumBackground = mViewPager.getHeaderBackgroundContainer().findViewById(R.id.relative_album);
        mAlbumPicIv = mViewPager.getHeaderBackgroundContainer().findViewById(R.id.iv_album);
        mSingerNameTv = mViewPager.getHeaderBackgroundContainer().findViewById(R.id.tv_singer_name);
        mPublicTimeTv = mViewPager.getHeaderBackgroundContainer().findViewById(R.id.tv_public_time);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    private void initView(){
        toolbar.setTitle(mAlbumName);

        SimpleTarget target = new SimpleTarget<Drawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(@Nullable Drawable resource, Transition<? super Drawable> transition) {
                Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                mAlbumBackground.setBackground(CommonUtil.getForegroundDrawable(bitmap));
                mAlbumPicIv.setImageBitmap(bitmap);
            }
        };
        Glide.with(getActivity())
                .load(mAlbumPic)
                .apply(RequestOptions.placeholderOf(R.drawable.welcome))
                .apply(RequestOptions.errorOf(R.drawable.welcome))
                .into(target);



        mSingerNameTv.setText("歌手 "+mSingerNmae);
        mPublicTimeTv.setText("发行时间 "+mPublicTime);

        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

            final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }
        //返回键的监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                       return AlbumSongFragment.newInstance(AlbumSongFragment.ALBUM_SONG,mId,mPublicTime);
                    case 1:
                        return AlbumSongFragment.newInstance(AlbumSongFragment.ALBUM_INFORATION,mId,mPublicTime);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "歌曲列表";
                    case 1:
                        return "专辑信息";
                }
                return "";
            }
        });

        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
        mViewPager.getPagerTitleStrip().setIndicatorColorResource(R.color.yellow);
        mViewPager.getPagerTitleStrip().setTabBackground(R.color.tab);
        mViewPager.getPagerTitleStrip().setTextColorStateListResource(R.color.white);


    }


    public static Fragment newInstance(String id,String albumName,String albumPic,String singerName,String publicTime){
        AlbumContentFragment albumContentFragment = new AlbumContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ALBUM_ID_KEY,id);
        bundle.putString(ALBUM_NAME_KEY,albumName);
        bundle.putString(ALBUM_PIC_KEY,albumPic);
        bundle.putString(SINGER_NAME_KEY,singerName);
        bundle.putString(PUBLIC_TIEM_KEY,publicTime);
        albumContentFragment.setArguments(bundle);
        return albumContentFragment;
    }
    private void getBundle(){
        Bundle bundle =getArguments();
        if(bundle !=null){
            mId =bundle.getString(ALBUM_ID_KEY);
            mAlbumName = bundle.getString(ALBUM_NAME_KEY);
            mAlbumPic = bundle.getString(ALBUM_PIC_KEY);
            mSingerNmae = bundle.getString(SINGER_NAME_KEY);
            mPublicTime = bundle.getString(PUBLIC_TIEM_KEY);
        }
    }
}

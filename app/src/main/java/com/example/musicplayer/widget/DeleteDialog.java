package com.example.musicplayer.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.musicplayer.R;


public class DeleteDialog extends Dialog implements View.OnClickListener{

    private OnClickListener mOnClickListener;

    private TextView mTitle;

    public interface OnClickListener {
        void selectCancel();    //选择取消
        void selectDelete();    //选择删除
        String setTitle();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public DeleteDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        initView();
    }

    private void initView() {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_delete_photo, null);
        mTitle = view.findViewById(R.id.tv_dialog_delete_title);        //标题
        TextView cancel = view.findViewById(R.id.tv_dialog_delete_photo_cancel);    //取消
        cancel.setOnClickListener(this);
        TextView delete = view.findViewById(R.id.tv_dialog_delete_photo_delete);    //删除
        delete.setOnClickListener(this);
        super.setContentView(view);     //设置布局
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_delete_photo_cancel:    //取消
                mOnClickListener.selectCancel();    //回调给主活动，让主活动处理相关逻辑，下同
                break;
            case R.id.tv_dialog_delete_photo_delete:     //删除
                mOnClickListener.selectDelete();
                break;
            default:
                break;
        }
    }

    /**
     * 重新该方法，使dialog适应屏幕的宽高
     */
    @Override
    public void show() {
        super.show();
        Window dialogWindow = this.getWindow();
        assert dialogWindow != null;
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        //设置高宽
        lp.width = (int) (screenWidth * 0.9); // 宽度
        lp.height = (int) (lp.width * 0.4);     // 高度
        dialogWindow.setAttributes(lp);

        mTitle.setText(mOnClickListener.setTitle());    //设置标题
    }
}

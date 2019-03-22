package com.example.musicplayer.util;

import com.example.musicplayer.entiy.LrcBean;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/03/18
 *     desc   : 歌词工具类
 * </pre>
 */

public class LrcUtil {
    /**
     * 传入的参数为标准歌词字符串
     *
     * @param lrcStr
     * @return
     */
    public static List<LrcBean> parseStr2List(String lrcStr) {
        List<LrcBean> list = new ArrayList<>();
        String lrcText = lrcStr.replaceAll("&#58;", ":")
                .replaceAll("&apos;", "'")
                .replaceAll("&#10;", "\n")
                .replaceAll("&#46;", ".")
                .replaceAll("&#32;", " ")
                .replaceAll("&#45;", "-")
                .replaceAll("&#13;", "\r")
                .replaceAll("&#39;", "'");
        String[] split = lrcText.split("\n");
        for (int i = 4; i < split.length; i++) {
            String lrc = split[i];
            if (lrc.contains(".")) {
                String min = lrc.substring(lrc.indexOf("[") + 1, lrc.indexOf("[") + 3);
                String seconds = lrc.substring(lrc.indexOf(":") + 1, lrc.indexOf(":") + 3);
                String mills = lrc.substring(lrc.indexOf(".") + 1, lrc.indexOf(".") + 3);
                long startTime = Long.valueOf(min) * 60 * 1000 + Long.valueOf(seconds) * 1000 + Long.valueOf(mills) * 10;
                String text = lrc.substring(lrc.indexOf("]") + 1);
                if (text.equals("") || text == null) continue;
                if (i == 5) {
                    int first = text.indexOf("(");
                    int second = text.indexOf("(", first + 1);
                    String textFront = null;
                    String textBehind = null;
                    boolean isTwo = true;
                    boolean isOne = true;
                    try {
                        textFront = text.substring(0, first);
                    }catch (StringIndexOutOfBoundsException e){
                        isOne = false;
                    }
                    try {
                        textBehind = text.substring(first + 1, second);
                    } catch (StringIndexOutOfBoundsException e) {
                        isTwo = false;
                    }
                    LrcBean lrcBean1 = new LrcBean();
                    lrcBean1.setStart(startTime);
                    if(isOne) {
                        lrcBean1.setLrc(textFront);
                    } else {
                        lrcBean1.setLrc(text);
                    }
                    list.add(lrcBean1);
                    if (isTwo) {
                        LrcBean lrcBean2 = new LrcBean();
                        lrcBean2.setStart(startTime);
                        lrcBean2.setLrc(textBehind);
                        list.add(lrcBean2);
                    }
                } else {
                    LrcBean lrcBean = new LrcBean();
                    lrcBean.setStart(startTime);
                    lrcBean.setLrc(text);
                    list.add(lrcBean);
                }
                if (list.size() > 1) {
                    list.get(list.size() - 2).setEnd(startTime);
                }
                if (i == split.length - 1) {
                    list.get(list.size() - 1).setEnd(startTime + 100000);
                }
            }
        }
        return list;
    }
}

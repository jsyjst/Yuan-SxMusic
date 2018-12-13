package com.example.musicplayer.entiy;

import java.util.List;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SeachSong {


    /**
     * result : SUCCESS
     * code : 200
     * data : [{"id":"452804665","name":"告白气球","singer":"Mc梦柯","pic":"https://api.bzqll.com/music/netease/pic?id=452804665&key=579621905","lrc":"https://api.bzqll.com/music/netease/lrc?id=452804665&key=579621905","url":"https://api.bzqll.com/music/netease/url?id=452804665&key=579621905"},{"id":"536570450","name":"魔术与歌曲：告白气球","singer":"周杰伦/蔡威泽","pic":"https://api.bzqll.com/music/netease/pic?id=536570450&key=579621905","lrc":"https://api.bzqll.com/music/netease/lrc?id=536570450&key=579621905","url":"https://api.bzqll.com/music/netease/url?id=536570450&key=579621905"},{"id":"864255512","name":"告白气球","singer":"初见","pic":"https://api.bzqll.com/music/netease/pic?id=864255512&key=579621905","lrc":"https://api.bzqll.com/music/netease/lrc?id=864255512&key=579621905","url":"https://api.bzqll.com/music/netease/url?id=864255512&key=579621905"},{"id":"454924401","name":"告白气球","singer":"张穆庭","pic":"https://api.bzqll.com/music/netease/pic?id=454924401&key=579621905","lrc":"https://api.bzqll.com/music/netease/lrc?id=454924401&key=579621905","url":"https://api.bzqll.com/music/netease/url?id=454924401&key=579621905"},{"id":"1301125157","name":"告白气球","singer":"文景熙","pic":"https://api.bzqll.com/music/netease/pic?id=1301125157&key=579621905","lrc":"https://api.bzqll.com/music/netease/lrc?id=1301125157&key=579621905","url":"https://api.bzqll.com/music/netease/url?id=1301125157&key=579621905"},{"id":"1300594463","name":"告白气球","singer":"张阿辉","pic":"https://api.bzqll.com/music/netease/pic?id=1300594463&key=579621905","lrc":"https://api.bzqll.com/music/netease/lrc?id=1300594463&key=579621905","url":"https://api.bzqll.com/music/netease/url?id=1300594463&key=579621905"},{"id":"509726593","name":"告白气球 (卡拉偶客版)","singer":"SNH48","pic":"https://api.bzqll.com/music/netease/pic?id=509726593&key=579621905","lrc":"https://api.bzqll.com/music/netease/lrc?id=509726593&key=579621905","url":"https://api.bzqll.com/music/netease/url?id=509726593&key=579621905"},{"id":"863515406","name":"告白气球","singer":"战小二吖","pic":"https://api.bzqll.com/music/netease/pic?id=863515406&key=579621905","lrc":"https://api.bzqll.com/music/netease/lrc?id=863515406&key=579621905","url":"https://api.bzqll.com/music/netease/url?id=863515406&key=579621905"}]
     */

    private String result;
    private int code;
    private List<DataBean> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 452804665
         * name : 告白气球
         * singer : Mc梦柯
         * pic : https://api.bzqll.com/music/netease/pic?id=452804665&key=579621905
         * lrc : https://api.bzqll.com/music/netease/lrc?id=452804665&key=579621905
         * url : https://api.bzqll.com/music/netease/url?id=452804665&key=579621905
         */

        private String id;
        private String name;
        private String singer;
        private String pic;
        private String lrc;
        private String url;
        private long time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getLrc() {
            return lrc;
        }

        public void setLrc(String lrc) {
            this.lrc = lrc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}

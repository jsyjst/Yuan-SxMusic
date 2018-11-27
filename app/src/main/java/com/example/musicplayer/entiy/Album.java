package com.example.musicplayer.entiy;

import java.util.List;

/**
 * Created by 残渊 on 2018/11/25.
 */

public class Album {

    /**
     * result : SUCCESS
     * code : 200
     * data : [{"albumID":8217,"albumMID":"000I5jJB3blWeN","albumName":"范特西","albumName_hilight":"范特西","albumPic":"http://y.gtimg.cn/music/photo_new/T002R180x180M000000I5jJB3blWeN.jpg","catch_song":"","docid":"9955806126048031202","publicTime":"2001-09-20","singerID":4558,"singerMID":"0025NhlN2yWrP4","singerName":"周杰伦","singerName_hilight":"<em>周杰伦<\/em>","singer_list":[{"id":4558,"mid":"0025NhlN2yWrP4","name":"周杰伦","name_hilight":"<em>周杰伦<\/em>"}],"song_count":10,"type":0}]
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
         * albumID : 8217
         * albumMID : 000I5jJB3blWeN
         * albumName : 范特西
         * albumName_hilight : 范特西
         * albumPic : http://y.gtimg.cn/music/photo_new/T002R180x180M000000I5jJB3blWeN.jpg
         * catch_song :
         * docid : 9955806126048031202
         * publicTime : 2001-09-20
         * singerID : 4558
         * singerMID : 0025NhlN2yWrP4
         * singerName : 周杰伦
         * singerName_hilight : <em>周杰伦</em>
         * singer_list : [{"id":4558,"mid":"0025NhlN2yWrP4","name":"周杰伦","name_hilight":"<em>周杰伦<\/em>"}]
         * song_count : 10
         * type : 0
         */

        private int albumID;
        private String albumMID;
        private String albumName;
        private String albumName_hilight;
        private String albumPic;
        private String catch_song;
        private String docid;
        private String publicTime;
        private int singerID;
        private String singerMID;
        private String singerName;
        private String singerName_hilight;
        private int song_count;
        private int type;
        private List<SingerListBean> singer_list;

        public int getAlbumID() {
            return albumID;
        }

        public void setAlbumID(int albumID) {
            this.albumID = albumID;
        }

        public String getAlbumMID() {
            return albumMID;
        }

        public void setAlbumMID(String albumMID) {
            this.albumMID = albumMID;
        }

        public String getAlbumName() {
            return albumName;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public String getAlbumName_hilight() {
            return albumName_hilight;
        }

        public void setAlbumName_hilight(String albumName_hilight) {
            this.albumName_hilight = albumName_hilight;
        }

        public String getAlbumPic() {
            return albumPic;
        }

        public void setAlbumPic(String albumPic) {
            this.albumPic = albumPic;
        }

        public String getCatch_song() {
            return catch_song;
        }

        public void setCatch_song(String catch_song) {
            this.catch_song = catch_song;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getPublicTime() {
            return publicTime;
        }

        public void setPublicTime(String publicTime) {
            this.publicTime = publicTime;
        }

        public int getSingerID() {
            return singerID;
        }

        public void setSingerID(int singerID) {
            this.singerID = singerID;
        }

        public String getSingerMID() {
            return singerMID;
        }

        public void setSingerMID(String singerMID) {
            this.singerMID = singerMID;
        }

        public String getSingerName() {
            return singerName;
        }

        public void setSingerName(String singerName) {
            this.singerName = singerName;
        }

        public String getSingerName_hilight() {
            return singerName_hilight;
        }

        public void setSingerName_hilight(String singerName_hilight) {
            this.singerName_hilight = singerName_hilight;
        }

        public int getSong_count() {
            return song_count;
        }

        public void setSong_count(int song_count) {
            this.song_count = song_count;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<SingerListBean> getSinger_list() {
            return singer_list;
        }

        public void setSinger_list(List<SingerListBean> singer_list) {
            this.singer_list = singer_list;
        }

        public static class SingerListBean {
            /**
             * id : 4558
             * mid : 0025NhlN2yWrP4
             * name : 周杰伦
             * name_hilight : <em>周杰伦</em>
             */

            private int id;
            private String mid;
            private String name;
            private String name_hilight;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName_hilight() {
                return name_hilight;
            }

            public void setName_hilight(String name_hilight) {
                this.name_hilight = name_hilight;
            }
        }
    }
}

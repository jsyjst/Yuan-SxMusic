package com.example.musicplayer.entiy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchSong {

    /**
     * code : 200
     * msg : OK
     * timestamp : 1558586469283
     * data : {"curnum":30,"curpage":1,"totalnum":453,"list":[{"preview":{"tryend":141466,"trybegin":84746,"trysize":960887},"songname_hilight":"晴天","belongCD":0,"newStatus":2,"singer":[{"name":"周杰伦","name_hilight":"<em>周杰伦<\/em>","mid":"0025NhlN2yWrP4","id":4558}],"albumname_hilight":"叶惠美","lyric_hilight":"","nt":537335406,"songmid":"0039MnYb0qxYhV","pure":0,"type":0,"chinesesinger":0,"switch":17405697,"albumname":"叶惠美","vid":"w0026q7f01a","stream":1,"tag":11,"ver":0,"isonly":1,"grp":[],"docid":"4660176279196411288","albummid":"000MkMni19ClKG","albumid":8220,"msgid":15,"pay":{"payplay":1,"payalbum":0,"paydownload":1,"paytrackmouth":1,"paytrackprice":200,"payalbumprice":0,"payinfo":1},"size128":4319991,"sizeflac":31518872,"sizeogg":5871273,"songname":"晴天","size320":10793267,"strMediaMid":"0039MnYb0qxYhV","media_mid":"0039MnYb0qxYhV","t":1,"lyric":"","sizeape":30143423,"pubtime":1059580800,"interval":269,"alertid":24,"cdIdx":3,"songid":97773}]}
     */

    private int code;
    private String msg;
    private long timestamp;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * curnum : 30
         * curpage : 1
         * totalnum : 453
         * list : [{"preview":{"tryend":141466,"trybegin":84746,"trysize":960887},"songname_hilight":"晴天","belongCD":0,"newStatus":2,"singer":[{"name":"周杰伦","name_hilight":"<em>周杰伦<\/em>","mid":"0025NhlN2yWrP4","id":4558}],"albumname_hilight":"叶惠美","lyric_hilight":"","nt":537335406,"songmid":"0039MnYb0qxYhV","pure":0,"type":0,"chinesesinger":0,"switch":17405697,"albumname":"叶惠美","vid":"w0026q7f01a","stream":1,"tag":11,"ver":0,"isonly":1,"grp":[],"docid":"4660176279196411288","albummid":"000MkMni19ClKG","albumid":8220,"msgid":15,"pay":{"payplay":1,"payalbum":0,"paydownload":1,"paytrackmouth":1,"paytrackprice":200,"payalbumprice":0,"payinfo":1},"size128":4319991,"sizeflac":31518872,"sizeogg":5871273,"songname":"晴天","size320":10793267,"strMediaMid":"0039MnYb0qxYhV","media_mid":"0039MnYb0qxYhV","t":1,"lyric":"","sizeape":30143423,"pubtime":1059580800,"interval":269,"alertid":24,"cdIdx":3,"songid":97773}]
         */

        private int curnum;
        private int curpage;
        private int totalnum;
        private List<ListBean> list;

        public int getCurnum() {
            return curnum;
        }

        public void setCurnum(int curnum) {
            this.curnum = curnum;
        }

        public int getCurpage() {
            return curpage;
        }

        public void setCurpage(int curpage) {
            this.curpage = curpage;
        }

        public int getTotalnum() {
            return totalnum;
        }

        public void setTotalnum(int totalnum) {
            this.totalnum = totalnum;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * preview : {"tryend":141466,"trybegin":84746,"trysize":960887}
             * songname_hilight : 晴天
             * belongCD : 0
             * newStatus : 2
             * singer : [{"name":"周杰伦","name_hilight":"<em>周杰伦<\/em>","mid":"0025NhlN2yWrP4","id":4558}]
             * albumname_hilight : 叶惠美
             * lyric_hilight :
             * nt : 537335406
             * songmid : 0039MnYb0qxYhV
             * pure : 0
             * type : 0
             * chinesesinger : 0
             * switch : 17405697
             * albumname : 叶惠美
             * vid : w0026q7f01a
             * stream : 1
             * tag : 11
             * ver : 0
             * isonly : 1
             * grp : []
             * docid : 4660176279196411288
             * albummid : 000MkMni19ClKG
             * albumid : 8220
             * msgid : 15
             * pay : {"payplay":1,"payalbum":0,"paydownload":1,"paytrackmouth":1,"paytrackprice":200,"payalbumprice":0,"payinfo":1}
             * size128 : 4319991
             * sizeflac : 31518872
             * sizeogg : 5871273
             * songname : 晴天
             * size320 : 10793267
             * strMediaMid : 0039MnYb0qxYhV
             * media_mid : 0039MnYb0qxYhV
             * t : 1
             * lyric :
             * sizeape : 30143423
             * pubtime : 1059580800
             * interval : 269
             * alertid : 24
             * cdIdx : 3
             * songid : 97773
             */

            private PreviewBean preview;
            private String songname_hilight;
            private int belongCD;
            private int newStatus;
            private String albumname_hilight;
            private String lyric_hilight;
            private long nt;
            private String songmid;
            private int pure;
            private int type;
            private int chinesesinger;
            @SerializedName("switch")
            private int switchX;
            private String albumname;
            private String vid;
            private int stream;
            private int tag;
            private int ver;
            private int isonly;
            private String docid;
            private String albummid;
            private int albumid;
            private int msgid;
            private PayBean pay;
            private int size128;
            private int sizeflac;
            private int sizeogg;
            private String songname;
            private int size320;
            private String strMediaMid;
            private String media_mid;
            private int t;
            private String lyric;
            private int sizeape;
            private long pubtime;
            private int interval;
            private int alertid;
            private int cdIdx;
            private int songid;
            private List<SingerBean> singer;
            private List<?> grp;

            public PreviewBean getPreview() {
                return preview;
            }

            public void setPreview(PreviewBean preview) {
                this.preview = preview;
            }

            public String getSongname_hilight() {
                return songname_hilight;
            }

            public void setSongname_hilight(String songname_hilight) {
                this.songname_hilight = songname_hilight;
            }

            public int getBelongCD() {
                return belongCD;
            }

            public void setBelongCD(int belongCD) {
                this.belongCD = belongCD;
            }

            public int getNewStatus() {
                return newStatus;
            }

            public void setNewStatus(int newStatus) {
                this.newStatus = newStatus;
            }

            public String getAlbumname_hilight() {
                return albumname_hilight;
            }

            public void setAlbumname_hilight(String albumname_hilight) {
                this.albumname_hilight = albumname_hilight;
            }

            public String getLyric_hilight() {
                return lyric_hilight;
            }

            public void setLyric_hilight(String lyric_hilight) {
                this.lyric_hilight = lyric_hilight;
            }

            public long getNt() {
                return nt;
            }

            public void setNt(long nt) {
                this.nt = nt;
            }

            public String getSongmid() {
                return songmid;
            }

            public void setSongmid(String songmid) {
                this.songmid = songmid;
            }

            public int getPure() {
                return pure;
            }

            public void setPure(int pure) {
                this.pure = pure;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getChinesesinger() {
                return chinesesinger;
            }

            public void setChinesesinger(int chinesesinger) {
                this.chinesesinger = chinesesinger;
            }

            public int getSwitchX() {
                return switchX;
            }

            public void setSwitchX(int switchX) {
                this.switchX = switchX;
            }

            public String getAlbumname() {
                return albumname;
            }

            public void setAlbumname(String albumname) {
                this.albumname = albumname;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public int getStream() {
                return stream;
            }

            public void setStream(int stream) {
                this.stream = stream;
            }

            public int getTag() {
                return tag;
            }

            public void setTag(int tag) {
                this.tag = tag;
            }

            public int getVer() {
                return ver;
            }

            public void setVer(int ver) {
                this.ver = ver;
            }

            public int getIsonly() {
                return isonly;
            }

            public void setIsonly(int isonly) {
                this.isonly = isonly;
            }

            public String getDocid() {
                return docid;
            }

            public void setDocid(String docid) {
                this.docid = docid;
            }

            public String getAlbummid() {
                return albummid;
            }

            public void setAlbummid(String albummid) {
                this.albummid = albummid;
            }

            public int getAlbumid() {
                return albumid;
            }

            public void setAlbumid(int albumid) {
                this.albumid = albumid;
            }

            public int getMsgid() {
                return msgid;
            }

            public void setMsgid(int msgid) {
                this.msgid = msgid;
            }

            public PayBean getPay() {
                return pay;
            }

            public void setPay(PayBean pay) {
                this.pay = pay;
            }

            public int getSize128() {
                return size128;
            }

            public void setSize128(int size128) {
                this.size128 = size128;
            }

            public int getSizeflac() {
                return sizeflac;
            }

            public void setSizeflac(int sizeflac) {
                this.sizeflac = sizeflac;
            }

            public int getSizeogg() {
                return sizeogg;
            }

            public void setSizeogg(int sizeogg) {
                this.sizeogg = sizeogg;
            }

            public String getSongname() {
                return songname;
            }

            public void setSongname(String songname) {
                this.songname = songname;
            }

            public int getSize320() {
                return size320;
            }

            public void setSize320(int size320) {
                this.size320 = size320;
            }

            public String getStrMediaMid() {
                return strMediaMid;
            }

            public void setStrMediaMid(String strMediaMid) {
                this.strMediaMid = strMediaMid;
            }

            public String getMedia_mid() {
                return media_mid;
            }

            public void setMedia_mid(String media_mid) {
                this.media_mid = media_mid;
            }

            public int getT() {
                return t;
            }

            public void setT(int t) {
                this.t = t;
            }

            public String getLyric() {
                return lyric;
            }

            public void setLyric(String lyric) {
                this.lyric = lyric;
            }

            public int getSizeape() {
                return sizeape;
            }

            public void setSizeape(int sizeape) {
                this.sizeape = sizeape;
            }

            public long getPubtime() {
                return pubtime;
            }

            public void setPubtime(long pubtime) {
                this.pubtime = pubtime;
            }

            public int getInterval() {
                return interval;
            }

            public void setInterval(int interval) {
                this.interval = interval;
            }

            public int getAlertid() {
                return alertid;
            }

            public void setAlertid(int alertid) {
                this.alertid = alertid;
            }

            public int getCdIdx() {
                return cdIdx;
            }

            public void setCdIdx(int cdIdx) {
                this.cdIdx = cdIdx;
            }

            public int getSongid() {
                return songid;
            }

            public void setSongid(int songid) {
                this.songid = songid;
            }

            public List<SingerBean> getSinger() {
                return singer;
            }

            public void setSinger(List<SingerBean> singer) {
                this.singer = singer;
            }

            public List<?> getGrp() {
                return grp;
            }

            public void setGrp(List<?> grp) {
                this.grp = grp;
            }

            public static class PreviewBean {
                /**
                 * tryend : 141466
                 * trybegin : 84746
                 * trysize : 960887
                 */

                private int tryend;
                private int trybegin;
                private int trysize;

                public int getTryend() {
                    return tryend;
                }

                public void setTryend(int tryend) {
                    this.tryend = tryend;
                }

                public int getTrybegin() {
                    return trybegin;
                }

                public void setTrybegin(int trybegin) {
                    this.trybegin = trybegin;
                }

                public int getTrysize() {
                    return trysize;
                }

                public void setTrysize(int trysize) {
                    this.trysize = trysize;
                }
            }

            public static class PayBean {
                /**
                 * payplay : 1
                 * payalbum : 0
                 * paydownload : 1
                 * paytrackmouth : 1
                 * paytrackprice : 200
                 * payalbumprice : 0
                 * payinfo : 1
                 */

                private int payplay;
                private int payalbum;
                private int paydownload;
                private int paytrackmouth;
                private int paytrackprice;
                private int payalbumprice;
                private int payinfo;

                public int getPayplay() {
                    return payplay;
                }

                public void setPayplay(int payplay) {
                    this.payplay = payplay;
                }

                public int getPayalbum() {
                    return payalbum;
                }

                public void setPayalbum(int payalbum) {
                    this.payalbum = payalbum;
                }

                public int getPaydownload() {
                    return paydownload;
                }

                public void setPaydownload(int paydownload) {
                    this.paydownload = paydownload;
                }

                public int getPaytrackmouth() {
                    return paytrackmouth;
                }

                public void setPaytrackmouth(int paytrackmouth) {
                    this.paytrackmouth = paytrackmouth;
                }

                public int getPaytrackprice() {
                    return paytrackprice;
                }

                public void setPaytrackprice(int paytrackprice) {
                    this.paytrackprice = paytrackprice;
                }

                public int getPayalbumprice() {
                    return payalbumprice;
                }

                public void setPayalbumprice(int payalbumprice) {
                    this.payalbumprice = payalbumprice;
                }

                public int getPayinfo() {
                    return payinfo;
                }

                public void setPayinfo(int payinfo) {
                    this.payinfo = payinfo;
                }
            }

            public static class SingerBean {
                /**
                 * name : 周杰伦
                 * name_hilight : <em>周杰伦</em>
                 * mid : 0025NhlN2yWrP4
                 * id : 4558
                 */

                private String name;
                private String name_hilight;
                private String mid;
                private int id;

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

                public String getMid() {
                    return mid;
                }

                public void setMid(String mid) {
                    this.mid = mid;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }
    }
}

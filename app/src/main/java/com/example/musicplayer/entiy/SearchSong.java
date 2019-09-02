package com.example.musicplayer.entiy;

import java.util.List;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/08/31
 *     desc   :
 * </pre>
 */

public class SearchSong {
    /**
     * code : 0
     * data : {"keyword":"邓紫棋","priority":0,"qc":[],"semantic":{"curnum":0,"curpage":1,"list":[],"totalnum":0},"song":{"curnum":2,"curpage":1,"list":[{"albumid":7806097,"albummid":"001F3IM92zEXSX","albumname":"中国新说唱2019 第12期","albumname_hilight":"中国新说唱2019 第12期","alertid":23,"belongCD":0,"cdIdx":6,"chinesesinger":0,"docid":"1263425476867044852","grp":[],"interval":232,"isonly":0,"lyric":"","lyric_hilight":"","media_mid":"003XrSXL43XhhP","msgid":16,"newStatus":1,"nt":3078011703,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200},"preview":{"trybegin":0,"tryend":0,"trysize":960887},"pubtime":1567166403,"pure":0,"singer":[{"id":13948,"mid":"001fNHEf1SFEFN","name":"G.E.M. 邓紫棋","name_hilight":"G.E.M. <em>邓紫棋<\/em>"},{"id":3344622,"mid":"003MDz1629ej3X","name":"OBi","name_hilight":"OBi"},{"id":2626134,"mid":"001yBcHD4adsSA","name":"刘炫廷","name_hilight":"刘炫廷"},{"id":2085196,"mid":"001eBoUt4SiKgg","name":"Capper","name_hilight":"Capper"}],"size128":3728026,"size320":9319693,"sizeape":0,"sizeflac":0,"sizeogg":5221536,"songid":237274487,"songmid":"003XrSXL43XhhP","songname":"We Are Young (Live)","songname_hilight":"We Are Young (Live)","strMediaMid":"003XrSXL43XhhP","stream":1,"switch":17413891,"t":1,"tag":10,"type":0,"ver":0,"vid":""},{"albumid":1196826,"albummid":"003c616O2Zlswm","albumname":"新的心跳","albumname_hilight":"新的心跳","alertid":2,"belongCD":0,"cdIdx":4,"chinesesinger":0,"docid":"3872246129178486611","grp":[],"interval":245,"isonly":0,"lyric":"","lyric_hilight":"","media_mid":"001DI2Jj3Jqve9","msgid":16,"newStatus":2,"nt":2292267207,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200},"preview":{"trybegin":0,"tryend":0,"trysize":0},"pubtime":1446739200,"pure":0,"singer":[{"id":13948,"mid":"001fNHEf1SFEFN","name":"G.E.M. 邓紫棋","name_hilight":"G.E.M. <em>邓紫棋<\/em>"}],"size128":3929034,"size320":9822259,"sizeape":0,"sizeflac":29903656,"sizeogg":5405648,"songid":104913384,"songmid":"004dFFPd4JNv8q","songname":"来自天堂的魔鬼","songname_hilight":"来自天堂的魔鬼","strMediaMid":"001DI2Jj3Jqve9","stream":1,"switch":636675,"t":1,"tag":11,"type":0,"ver":0,"vid":"v001892io9b"}],"totalnum":416},"tab":0,"taglist":[],"totaltime":0,"zhida":{"chinesesinger":0,"type":0}}
     * message :
     * notice :
     * subcode : 0
     * time : 1567229228
     * tips :
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * keyword : 邓紫棋
         * priority : 0
         * qc : []
         * semantic : {"curnum":0,"curpage":1,"list":[],"totalnum":0}
         * song : {"curnum":2,"curpage":1,"list":[{"albumid":7806097,"albummid":"001F3IM92zEXSX","albumname":"中国新说唱2019 第12期","albumname_hilight":"中国新说唱2019 第12期","alertid":23,"belongCD":0,"cdIdx":6,"chinesesinger":0,"docid":"1263425476867044852","grp":[],"interval":232,"isonly":0,"lyric":"","lyric_hilight":"","media_mid":"003XrSXL43XhhP","msgid":16,"newStatus":1,"nt":3078011703,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200},"preview":{"trybegin":0,"tryend":0,"trysize":960887},"pubtime":1567166403,"pure":0,"singer":[{"id":13948,"mid":"001fNHEf1SFEFN","name":"G.E.M. 邓紫棋","name_hilight":"G.E.M. <em>邓紫棋<\/em>"},{"id":3344622,"mid":"003MDz1629ej3X","name":"OBi","name_hilight":"OBi"},{"id":2626134,"mid":"001yBcHD4adsSA","name":"刘炫廷","name_hilight":"刘炫廷"},{"id":2085196,"mid":"001eBoUt4SiKgg","name":"Capper","name_hilight":"Capper"}],"size128":3728026,"size320":9319693,"sizeape":0,"sizeflac":0,"sizeogg":5221536,"songid":237274487,"songmid":"003XrSXL43XhhP","songname":"We Are Young (Live)","songname_hilight":"We Are Young (Live)","strMediaMid":"003XrSXL43XhhP","stream":1,"switch":17413891,"t":1,"tag":10,"type":0,"ver":0,"vid":""},{"albumid":1196826,"albummid":"003c616O2Zlswm","albumname":"新的心跳","albumname_hilight":"新的心跳","alertid":2,"belongCD":0,"cdIdx":4,"chinesesinger":0,"docid":"3872246129178486611","grp":[],"interval":245,"isonly":0,"lyric":"","lyric_hilight":"","media_mid":"001DI2Jj3Jqve9","msgid":16,"newStatus":2,"nt":2292267207,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200},"preview":{"trybegin":0,"tryend":0,"trysize":0},"pubtime":1446739200,"pure":0,"singer":[{"id":13948,"mid":"001fNHEf1SFEFN","name":"G.E.M. 邓紫棋","name_hilight":"G.E.M. <em>邓紫棋<\/em>"}],"size128":3929034,"size320":9822259,"sizeape":0,"sizeflac":29903656,"sizeogg":5405648,"songid":104913384,"songmid":"004dFFPd4JNv8q","songname":"来自天堂的魔鬼","songname_hilight":"来自天堂的魔鬼","strMediaMid":"001DI2Jj3Jqve9","stream":1,"switch":636675,"t":1,"tag":11,"type":0,"ver":0,"vid":"v001892io9b"}],"totalnum":416}
         * tab : 0
         * taglist : []
         * totaltime : 0
         * zhida : {"chinesesinger":0,"type":0}
         */


        private SongBean song;
        public SongBean getSong() {
            return song;
        }

        public void setSong(SongBean song) {
            this.song = song;
        }

        public static class SongBean {
            /**
             * curnum : 2
             * curpage : 1
             * list : [{"albumid":7806097,"albummid":"001F3IM92zEXSX","albumname":"中国新说唱2019 第12期","albumname_hilight":"中国新说唱2019 第12期","alertid":23,"belongCD":0,"cdIdx":6,"chinesesinger":0,"docid":"1263425476867044852","grp":[],"interval":232,"isonly":0,"lyric":"","lyric_hilight":"","media_mid":"003XrSXL43XhhP","msgid":16,"newStatus":1,"nt":3078011703,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200},"preview":{"trybegin":0,"tryend":0,"trysize":960887},"pubtime":1567166403,"pure":0,"singer":[{"id":13948,"mid":"001fNHEf1SFEFN","name":"G.E.M. 邓紫棋","name_hilight":"G.E.M. <em>邓紫棋<\/em>"},{"id":3344622,"mid":"003MDz1629ej3X","name":"OBi","name_hilight":"OBi"},{"id":2626134,"mid":"001yBcHD4adsSA","name":"刘炫廷","name_hilight":"刘炫廷"},{"id":2085196,"mid":"001eBoUt4SiKgg","name":"Capper","name_hilight":"Capper"}],"size128":3728026,"size320":9319693,"sizeape":0,"sizeflac":0,"sizeogg":5221536,"songid":237274487,"songmid":"003XrSXL43XhhP","songname":"We Are Young (Live)","songname_hilight":"We Are Young (Live)","strMediaMid":"003XrSXL43XhhP","stream":1,"switch":17413891,"t":1,"tag":10,"type":0,"ver":0,"vid":""},{"albumid":1196826,"albummid":"003c616O2Zlswm","albumname":"新的心跳","albumname_hilight":"新的心跳","alertid":2,"belongCD":0,"cdIdx":4,"chinesesinger":0,"docid":"3872246129178486611","grp":[],"interval":245,"isonly":0,"lyric":"","lyric_hilight":"","media_mid":"001DI2Jj3Jqve9","msgid":16,"newStatus":2,"nt":2292267207,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200},"preview":{"trybegin":0,"tryend":0,"trysize":0},"pubtime":1446739200,"pure":0,"singer":[{"id":13948,"mid":"001fNHEf1SFEFN","name":"G.E.M. 邓紫棋","name_hilight":"G.E.M. <em>邓紫棋<\/em>"}],"size128":3929034,"size320":9822259,"sizeape":0,"sizeflac":29903656,"sizeogg":5405648,"songid":104913384,"songmid":"004dFFPd4JNv8q","songname":"来自天堂的魔鬼","songname_hilight":"来自天堂的魔鬼","strMediaMid":"001DI2Jj3Jqve9","stream":1,"switch":636675,"t":1,"tag":11,"type":0,"ver":0,"vid":"v001892io9b"}]
             * totalnum : 416
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
                 * albumid : 7806097
                 * albummid : 001F3IM92zEXSX
                 * albumname : 中国新说唱2019 第12期
                 * albumname_hilight : 中国新说唱2019 第12期
                 * alertid : 23
                 * belongCD : 0
                 * cdIdx : 6
                 * chinesesinger : 0
                 * docid : 1263425476867044852
                 * grp : []
                 * interval : 232
                 * isonly : 0
                 * lyric :
                 * lyric_hilight :
                 * media_mid : 003XrSXL43XhhP
                 * msgid : 16
                 * newStatus : 1
                 * nt : 3078011703
                 * pay : {"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200}
                 * preview : {"trybegin":0,"tryend":0,"trysize":960887}
                 * pubtime : 1567166403
                 * pure : 0
                 * singer : [{"id":13948,"mid":"001fNHEf1SFEFN","name":"G.E.M. 邓紫棋","name_hilight":"G.E.M. <em>邓紫棋<\/em>"},{"id":3344622,"mid":"003MDz1629ej3X","name":"OBi","name_hilight":"OBi"},{"id":2626134,"mid":"001yBcHD4adsSA","name":"刘炫廷","name_hilight":"刘炫廷"},{"id":2085196,"mid":"001eBoUt4SiKgg","name":"Capper","name_hilight":"Capper"}]
                 * size128 : 3728026
                 * size320 : 9319693
                 * sizeape : 0
                 * sizeflac : 0
                 * sizeogg : 5221536
                 * songid : 237274487
                 * songmid : 003XrSXL43XhhP
                 * songname : We Are Young (Live)
                 * songname_hilight : We Are Young (Live)
                 * strMediaMid : 003XrSXL43XhhP
                 * stream : 1
                 * switch : 17413891
                 * t : 1
                 * tag : 10
                 * type : 0
                 * ver : 0
                 * vid :
                 */

                private int albumid;
                private String albummid;
                private String albumname;
                private String albumname_hilight;
                private int alertid;
                private int belongCD;
                private int cdIdx;
                private int chinesesinger;
                private String docid;
                private int interval;
                private int isonly;
                private String lyric;
                private String lyric_hilight;
                private String media_mid;
                private int msgid;
                private int newStatus;
                private long nt;
                private int pubtime;
                private int pure;
                private int size128;
                private int size320;
                private int sizeape;
                private int sizeflac;
                private int sizeogg;
                private int songid;
                private String songmid;
                private String songname;
                private String songname_hilight;
                private String strMediaMid;
                private int stream;
                private int switchX;
                private int t;
                private int tag;
                private int type;
                private int ver;
                private List<SingerBean> singer;

                public int getAlbumid() {
                    return albumid;
                }

                public void setAlbumid(int albumid) {
                    this.albumid = albumid;
                }

                public String getAlbummid() {
                    return albummid;
                }

                public void setAlbummid(String albummid) {
                    this.albummid = albummid;
                }

                public String getAlbumname() {
                    return albumname;
                }

                public void setAlbumname(String albumname) {
                    this.albumname = albumname;
                }

                public String getAlbumname_hilight() {
                    return albumname_hilight;
                }

                public void setAlbumname_hilight(String albumname_hilight) {
                    this.albumname_hilight = albumname_hilight;
                }

                public int getAlertid() {
                    return alertid;
                }

                public void setAlertid(int alertid) {
                    this.alertid = alertid;
                }

                public int getBelongCD() {
                    return belongCD;
                }

                public void setBelongCD(int belongCD) {
                    this.belongCD = belongCD;
                }

                public int getCdIdx() {
                    return cdIdx;
                }

                public void setCdIdx(int cdIdx) {
                    this.cdIdx = cdIdx;
                }

                public int getChinesesinger() {
                    return chinesesinger;
                }

                public void setChinesesinger(int chinesesinger) {
                    this.chinesesinger = chinesesinger;
                }

                public String getDocid() {
                    return docid;
                }

                public void setDocid(String docid) {
                    this.docid = docid;
                }

                public int getInterval() {
                    return interval;
                }

                public void setInterval(int interval) {
                    this.interval = interval;
                }

                public int getIsonly() {
                    return isonly;
                }

                public void setIsonly(int isonly) {
                    this.isonly = isonly;
                }

                public String getLyric() {
                    return lyric;
                }

                public void setLyric(String lyric) {
                    this.lyric = lyric;
                }

                public String getLyric_hilight() {
                    return lyric_hilight;
                }

                public void setLyric_hilight(String lyric_hilight) {
                    this.lyric_hilight = lyric_hilight;
                }

                public String getMedia_mid() {
                    return media_mid;
                }

                public void setMedia_mid(String media_mid) {
                    this.media_mid = media_mid;
                }

                public int getMsgid() {
                    return msgid;
                }

                public void setMsgid(int msgid) {
                    this.msgid = msgid;
                }

                public int getNewStatus() {
                    return newStatus;
                }

                public void setNewStatus(int newStatus) {
                    this.newStatus = newStatus;
                }

                public long getNt() {
                    return nt;
                }

                public void setNt(long nt) {
                    this.nt = nt;
                }

                public int getPubtime() {
                    return pubtime;
                }

                public void setPubtime(int pubtime) {
                    this.pubtime = pubtime;
                }

                public int getPure() {
                    return pure;
                }

                public void setPure(int pure) {
                    this.pure = pure;
                }

                public int getSize128() {
                    return size128;
                }

                public void setSize128(int size128) {
                    this.size128 = size128;
                }

                public int getSize320() {
                    return size320;
                }

                public void setSize320(int size320) {
                    this.size320 = size320;
                }

                public int getSizeape() {
                    return sizeape;
                }

                public void setSizeape(int sizeape) {
                    this.sizeape = sizeape;
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

                public int getSongid() {
                    return songid;
                }

                public void setSongid(int songid) {
                    this.songid = songid;
                }

                public String getSongmid() {
                    return songmid;
                }

                public void setSongmid(String songmid) {
                    this.songmid = songmid;
                }

                public String getSongname() {
                    return songname;
                }

                public void setSongname(String songname) {
                    this.songname = songname;
                }

                public String getSongname_hilight() {
                    return songname_hilight;
                }

                public void setSongname_hilight(String songname_hilight) {
                    this.songname_hilight = songname_hilight;
                }

                public String getStrMediaMid() {
                    return strMediaMid;
                }

                public void setStrMediaMid(String strMediaMid) {
                    this.strMediaMid = strMediaMid;
                }

                public int getStream() {
                    return stream;
                }

                public void setStream(int stream) {
                    this.stream = stream;
                }

                public int getSwitchX() {
                    return switchX;
                }

                public void setSwitchX(int switchX) {
                    this.switchX = switchX;
                }

                public int getT() {
                    return t;
                }

                public void setT(int t) {
                    this.t = t;
                }

                public int getTag() {
                    return tag;
                }

                public void setTag(int tag) {
                    this.tag = tag;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getVer() {
                    return ver;
                }

                public void setVer(int ver) {
                    this.ver = ver;
                }


                public List<SingerBean> getSinger() {
                    return singer;
                }

                public void setSinger(List<SingerBean> singer) {
                    this.singer = singer;
                }


                public static class SingerBean {
                    /**
                     * id : 13948
                     * mid : 001fNHEf1SFEFN
                     * name : G.E.M. 邓紫棋
                     * name_hilight : G.E.M. <em>邓紫棋</em>
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
    }


}

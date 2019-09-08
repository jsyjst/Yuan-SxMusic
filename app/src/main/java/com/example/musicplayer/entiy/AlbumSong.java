package com.example.musicplayer.entiy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 残渊 on 2018/11/25.
 */

public class AlbumSong {

    /**
     * code : 0
     * data : {"aDate":"2019-07-21","albumTips":"","color":1704579,"company":"G Nation","company_new":{"brief":"","headPic":"","id":343411,"is_show":1,"name":"G Nation"},"cur_song_num":1,"desc":"差 不 多 姑 娘\nG.E.M. 邓紫棋\n在物欲横流的现实世界，随波逐流的意识形态愈演愈烈。姑娘们被差不多的愿望所牵引，\n像孔雀一样渴望展示漂亮的皮囊，追逐差不多的浮华，迷失于差不多的诱惑。\n\n\u201c差不多姑娘\u201d该如何打破\u201c差不多\u201d的枷锁？面临无形之手的操控，她们又将何去何从？\n\n同样活在现代都市的邓紫棋，用音乐为每位差不多姑娘说出心声，以她的力量鼓励姑娘们重新找到自己。\n\n坚持用音乐表达观点，是邓紫棋的创作初心。她在「差不多姑娘」中注入了对现实敏锐的洞察，和其对女性群体的观照。因为曾几何时，她也陷入过「差不多」困境，对镜子里的自己失望，而后终于挣脱泥淖，接受没有武装的模样。邓紫棋把自己的感同身受写进音乐里，警醒那些被欲望绑架的「差不多姑娘」，告诉她们「人生真的不该这样过」。歌曲中不加修饰的歌词不断敲打人心，每一句都似在叩问，意图击碎代表虚荣假象的泡沫。\n\n每个女生都是独特的宇宙，拥有自己的光芒万丈。\n邓紫棋这颗运转中的小小宇宙，此刻正在用音乐释放出惊人能量。","genre":"流行","id":7215891,"lan":"国语","list":[{"albumdesc":"","albumid":7215891,"albummid":"002N84iF3QxG1c","albumname":"差不多姑娘","alertid":21,"belongCD":1,"cdIdx":0,"interval":230,"isonly":1,"label":"0","msgid":14,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200,"timefree":0},"preview":{"trybegin":0,"tryend":0,"trysize":960887},"rate":23,"singer":[{"id":13948,"mid":"001fNHEf1SFEFN","name":"G.E.M. 邓紫棋"}],"size128":3682018,"size320":9204722,"size5_1":0,"sizeape":0,"sizeflac":26527121,"sizeogg":5277229,"songid":234824638,"songmid":"000wocYU11tSzS","songname":"差不多姑娘","songorig":"差不多姑娘","songtype":0,"strMediaMid":"000wocYU11tSzS","stream":13,"switch":17413891,"type":0,"vid":"b0031kyhy1p"}],"mid":"002N84iF3QxG1c","name":"差不多姑娘","radio_anchor":0,"singerid":13948,"singermblog":"gemtang","singermid":"001fNHEf1SFEFN","singername":"G.E.M. 邓紫棋","song_begin":0,"total":1,"total_song_num":1}
     * message : succ
     * subcode : 0
     */

    private int code;
    private DataBean data;
    private String message;
    private int subcode;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSubcode() {
        return subcode;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public static class DataBean {
        /**
         * aDate : 2019-07-21
         * albumTips :
         * color : 1704579
         * company : G Nation
         * company_new : {"brief":"","headPic":"","id":343411,"is_show":1,"name":"G Nation"}
         * cur_song_num : 1
         * desc : 差 不 多 姑 娘
         G.E.M. 邓紫棋
         在物欲横流的现实世界，随波逐流的意识形态愈演愈烈。姑娘们被差不多的愿望所牵引，
         像孔雀一样渴望展示漂亮的皮囊，追逐差不多的浮华，迷失于差不多的诱惑。

         “差不多姑娘”该如何打破“差不多”的枷锁？面临无形之手的操控，她们又将何去何从？

         同样活在现代都市的邓紫棋，用音乐为每位差不多姑娘说出心声，以她的力量鼓励姑娘们重新找到自己。

         坚持用音乐表达观点，是邓紫棋的创作初心。她在「差不多姑娘」中注入了对现实敏锐的洞察，和其对女性群体的观照。因为曾几何时，她也陷入过「差不多」困境，对镜子里的自己失望，而后终于挣脱泥淖，接受没有武装的模样。邓紫棋把自己的感同身受写进音乐里，警醒那些被欲望绑架的「差不多姑娘」，告诉她们「人生真的不该这样过」。歌曲中不加修饰的歌词不断敲打人心，每一句都似在叩问，意图击碎代表虚荣假象的泡沫。

         每个女生都是独特的宇宙，拥有自己的光芒万丈。
         邓紫棋这颗运转中的小小宇宙，此刻正在用音乐释放出惊人能量。
         * genre : 流行
         * id : 7215891
         * lan : 国语
         * list : [{"albumdesc":"","albumid":7215891,"albummid":"002N84iF3QxG1c","albumname":"差不多姑娘","alertid":21,"belongCD":1,"cdIdx":0,"interval":230,"isonly":1,"label":"0","msgid":14,"pay":{"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200,"timefree":0},"preview":{"trybegin":0,"tryend":0,"trysize":960887},"rate":23,"singer":[{"id":13948,"mid":"001fNHEf1SFEFN","name":"G.E.M. 邓紫棋"}],"size128":3682018,"size320":9204722,"size5_1":0,"sizeape":0,"sizeflac":26527121,"sizeogg":5277229,"songid":234824638,"songmid":"000wocYU11tSzS","songname":"差不多姑娘","songorig":"差不多姑娘","songtype":0,"strMediaMid":"000wocYU11tSzS","stream":13,"switch":17413891,"type":0,"vid":"b0031kyhy1p"}]
         * mid : 002N84iF3QxG1c
         * name : 差不多姑娘
         * radio_anchor : 0
         * singerid : 13948
         * singermblog : gemtang
         * singermid : 001fNHEf1SFEFN
         * singername : G.E.M. 邓紫棋
         * song_begin : 0
         * total : 1
         * total_song_num : 1
         */

        private String aDate;
        private String albumTips;
        private int color;
        private String company;
        private CompanyNewBean company_new;
        private int cur_song_num;
        private String desc;
        private String genre;
        private int id;
        private String lan;
        private String mid;
        private String name;
        private int radio_anchor;
        private int singerid;
        private String singermblog;
        private String singermid;
        private String singername;
        private int song_begin;
        private int total;
        private int total_song_num;
        private List<ListBean> list;

        public String getADate() {
            return aDate;
        }

        public void setADate(String aDate) {
            this.aDate = aDate;
        }

        public String getAlbumTips() {
            return albumTips;
        }

        public void setAlbumTips(String albumTips) {
            this.albumTips = albumTips;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public CompanyNewBean getCompany_new() {
            return company_new;
        }

        public void setCompany_new(CompanyNewBean company_new) {
            this.company_new = company_new;
        }

        public int getCur_song_num() {
            return cur_song_num;
        }

        public void setCur_song_num(int cur_song_num) {
            this.cur_song_num = cur_song_num;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLan() {
            return lan;
        }

        public void setLan(String lan) {
            this.lan = lan;
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

        public int getRadio_anchor() {
            return radio_anchor;
        }

        public void setRadio_anchor(int radio_anchor) {
            this.radio_anchor = radio_anchor;
        }

        public int getSingerid() {
            return singerid;
        }

        public void setSingerid(int singerid) {
            this.singerid = singerid;
        }

        public String getSingermblog() {
            return singermblog;
        }

        public void setSingermblog(String singermblog) {
            this.singermblog = singermblog;
        }

        public String getSingermid() {
            return singermid;
        }

        public void setSingermid(String singermid) {
            this.singermid = singermid;
        }

        public String getSingername() {
            return singername;
        }

        public void setSingername(String singername) {
            this.singername = singername;
        }

        public int getSong_begin() {
            return song_begin;
        }

        public void setSong_begin(int song_begin) {
            this.song_begin = song_begin;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal_song_num() {
            return total_song_num;
        }

        public void setTotal_song_num(int total_song_num) {
            this.total_song_num = total_song_num;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class CompanyNewBean {
            /**
             * brief :
             * headPic :
             * id : 343411
             * is_show : 1
             * name : G Nation
             */

            private String brief;
            private String headPic;
            private int id;
            private int is_show;
            private String name;

            public String getBrief() {
                return brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIs_show() {
                return is_show;
            }

            public void setIs_show(int is_show) {
                this.is_show = is_show;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ListBean {
            /**
             * albumdesc :
             * albumid : 7215891
             * albummid : 002N84iF3QxG1c
             * albumname : 差不多姑娘
             * alertid : 21
             * belongCD : 1
             * cdIdx : 0
             * interval : 230
             * isonly : 1
             * label : 0
             * msgid : 14
             * pay : {"payalbum":0,"payalbumprice":0,"paydownload":1,"payinfo":1,"payplay":0,"paytrackmouth":1,"paytrackprice":200,"timefree":0}
             * preview : {"trybegin":0,"tryend":0,"trysize":960887}
             * rate : 23
             * singer : [{"id":13948,"mid":"001fNHEf1SFEFN","name":"G.E.M. 邓紫棋"}]
             * size128 : 3682018
             * size320 : 9204722
             * size5_1 : 0
             * sizeape : 0
             * sizeflac : 26527121
             * sizeogg : 5277229
             * songid : 234824638
             * songmid : 000wocYU11tSzS
             * songname : 差不多姑娘
             * songorig : 差不多姑娘
             * songtype : 0
             * strMediaMid : 000wocYU11tSzS
             * stream : 13
             * switch : 17413891
             * type : 0
             * vid : b0031kyhy1p
             */

            private String albumdesc;
            private int albumid;
            private String albummid;
            private String albumname;
            private int alertid;
            private int belongCD;
            private int cdIdx;
            private int interval;
            private int isonly;
            private String label;
            private int msgid;
            private PayBean pay;
            private PreviewBean preview;
            private int rate;
            private int size128;
            private int size320;
            private int size5_1;
            private int sizeape;
            private int sizeflac;
            private int sizeogg;
            private int songid;
            private String songmid;
            private String songname;
            private String songorig;
            private int songtype;
            private String strMediaMid;
            private int stream;
            @SerializedName("switch")
            private int switchX;
            private int type;
            private String vid;
            private List<SingerBean> singer;

            public String getAlbumdesc() {
                return albumdesc;
            }

            public void setAlbumdesc(String albumdesc) {
                this.albumdesc = albumdesc;
            }

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

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
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

            public PreviewBean getPreview() {
                return preview;
            }

            public void setPreview(PreviewBean preview) {
                this.preview = preview;
            }

            public int getRate() {
                return rate;
            }

            public void setRate(int rate) {
                this.rate = rate;
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

            public int getSize5_1() {
                return size5_1;
            }

            public void setSize5_1(int size5_1) {
                this.size5_1 = size5_1;
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

            public String getSongorig() {
                return songorig;
            }

            public void setSongorig(String songorig) {
                this.songorig = songorig;
            }

            public int getSongtype() {
                return songtype;
            }

            public void setSongtype(int songtype) {
                this.songtype = songtype;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public List<SingerBean> getSinger() {
                return singer;
            }

            public void setSinger(List<SingerBean> singer) {
                this.singer = singer;
            }

            public static class PayBean {
                /**
                 * payalbum : 0
                 * payalbumprice : 0
                 * paydownload : 1
                 * payinfo : 1
                 * payplay : 0
                 * paytrackmouth : 1
                 * paytrackprice : 200
                 * timefree : 0
                 */

                private int payalbum;
                private int payalbumprice;
                private int paydownload;
                private int payinfo;
                private int payplay;
                private int paytrackmouth;
                private int paytrackprice;
                private int timefree;

                public int getPayalbum() {
                    return payalbum;
                }

                public void setPayalbum(int payalbum) {
                    this.payalbum = payalbum;
                }

                public int getPayalbumprice() {
                    return payalbumprice;
                }

                public void setPayalbumprice(int payalbumprice) {
                    this.payalbumprice = payalbumprice;
                }

                public int getPaydownload() {
                    return paydownload;
                }

                public void setPaydownload(int paydownload) {
                    this.paydownload = paydownload;
                }

                public int getPayinfo() {
                    return payinfo;
                }

                public void setPayinfo(int payinfo) {
                    this.payinfo = payinfo;
                }

                public int getPayplay() {
                    return payplay;
                }

                public void setPayplay(int payplay) {
                    this.payplay = payplay;
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

                public int getTimefree() {
                    return timefree;
                }

                public void setTimefree(int timefree) {
                    this.timefree = timefree;
                }
            }

            public static class PreviewBean {
                /**
                 * trybegin : 0
                 * tryend : 0
                 * trysize : 960887
                 */

                private int trybegin;
                private int tryend;
                private int trysize;

                public int getTrybegin() {
                    return trybegin;
                }

                public void setTrybegin(int trybegin) {
                    this.trybegin = trybegin;
                }

                public int getTryend() {
                    return tryend;
                }

                public void setTryend(int tryend) {
                    this.tryend = tryend;
                }

                public int getTrysize() {
                    return trysize;
                }

                public void setTrysize(int trysize) {
                    this.trysize = trysize;
                }
            }

            public static class SingerBean {
                /**
                 * id : 13948
                 * mid : 001fNHEf1SFEFN
                 * name : G.E.M. 邓紫棋
                 */

                private int id;
                private String mid;
                private String name;

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
            }
        }
    }
}

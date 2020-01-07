package com.nanhuajiaren.bson;
import java.util.List;

public class AvData
{
    public Integer code;
    public String message;
    public Integer ttl;
    public Data data;

    /** Data is the inner class of AvApiResult */
    public class Data
	{
        public String bvid;
        public Integer aid;
        public Integer videos;
        public Integer tid;
        public String tname;
        public Integer copyright;
        public String pic;
        public String title;
        public Integer pubdate;
        public Integer ctime;
        public String desc;
        public Integer state;
        public Integer attribute;
        public Integer duration;
        public Rights rights;

        /** Rights is the inner class of Data */
        public class Rights
		{
            public Integer bp;
            public Integer elec;
            public Integer download;
            public Integer movie;
            public Integer pay;
            public Integer hd5;
            public Integer no_reprint;
            public Integer autoplay;
            public Integer ugc_pay;
            public Integer is_cooperation;
            public Integer ugc_pay_preview;
            public Integer no_background;
        }

        public Owner owner;

        /** Owner is the inner class of Data */
        public class Owner
		{
            public Integer mid;
            public String name;
            public String face;
        }

        public Stat stat;

        /** Stat is the inner class of Data */
        public class Stat
		{
            public Integer aid;
            public Integer view;
            public Integer danmaku;
            public Integer reply;
            public Integer favorite;
            public Integer coin;
            public Integer share;
            public Integer now_rank;
            public Integer his_rank;
            public Integer like;
            public Integer dislike;
            public String evaluation;
        }

        public String dynamic;
        public Integer cid;
        public Dimension dimension;

        /** Dimension is the inner class of Data */
        public class Dimension
		{
            public Integer width;
            public Integer height;
            public Integer rotate;
        }

        public Boolean no_cache;
        public List<Pages> pages;

        /** Pages is the inner class of Data */
        public class Pages
		{
            public Integer cid;
            public Integer page;
            public String from;
            public String part;
            public Integer duration;
            public String vid;
            public String weblink;
            public Dimension dimension;

            /** Dimension is the inner class of Pages */
            public class Dimension
			{
                public Integer width;
                public Integer height;
                public Integer rotate;
            }
        }
    }
}


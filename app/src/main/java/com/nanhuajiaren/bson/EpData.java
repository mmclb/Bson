package com.nanhuajiaren.bson;
import java.util.List;

public class EpData
{
    public boolean loaded;
    public String h1Title;
	public MediaInfo mediaInfo;

    /** MediaInfo is the inner class of JavaBean */
    public class MediaInfo
	{
        public Integer id;
        public Integer ssId;
        public String title;
        public String jpTitle;
//        public String series;
        public String alias;
        public String evaluate;
        public Integer ssType;
        public SsTypeFormat ssTypeFormat;

        /** SsTypeFormat is the inner class of MediaInfo */
        public class SsTypeFormat
		{
            public String name;
            public String homeLink;
        }

        public Integer status;
        public Boolean multiMode;
        public Boolean forceWide;
        public String specialCover;
        public String squareCover;
        public String cover;
        public String playerRecord;
        public Rights rights;

        /** Rights is the inner class of MediaInfo */
        public class Rights
		{
            public Boolean allowBp;
            public Boolean allowBpRank;
            public Boolean allowReview;
            public Boolean isPreview;
            public Boolean appOnly;
            public Boolean limitNotFound;
            public Boolean isCoverShow;
            public Boolean canWatch;
        }

        public Pub pub;

        /** Pub is the inner class of MediaInfo */
        public class Pub
		{
            public String time;
            public String timeShow;
            public Boolean isStart;
            public Boolean isFinish;
        }

        public UpInfo upInfo;

        /** UpInfo is the inner class of MediaInfo */
        public class UpInfo
		{
            public Integer mid;
            public String avatar;
            public String name;
            public Boolean isAnnualVip;
            public Integer pendantId;
            public String pendantName;
            public String pendantImage;
        }

        public Rating rating;

        /** Rating is the inner class of MediaInfo */
        public class Rating
		{
            public Double score;
            public Integer count;
        }

        public NewestEp newestEp;

        /** NewestEp is the inner class of MediaInfo */
        public class NewestEp
		{
            public Integer id;
            public String desc;
            public Boolean isNew;
        }

        public PayMent payMent;

        /** PayMent is the inner class of MediaInfo */
        public class PayMent
		{
            public String price;
            public String tip;
            public String promotion;
            public String vipProm;
            public String vipFirstProm;
            public Integer discount;
            public Integer vipDiscount;
            public SixType sixType;

            /** SixType is the inner class of PayMent */
            public class SixType
			{
                public Boolean allowTicket;
                public Boolean allowTimeLimit;
                public Boolean allowDiscount;
                public Boolean allowVipDiscount;
            }

        }

        public PayPack payPack;

        /** PayPack is the inner class of MediaInfo */
        public class PayPack
		{
            public String title;
            public String appNoPayText;
            public String appPayText;
            public String url;
        }

        public Activity activity;

        /** Activity is the inner class of MediaInfo */
        public class Activity
		{
            public Integer id;
            public String title;
            public String pendantOpsImg;
            public String pendantOpsLink;
        }

        public Count count;

        /** Count is the inner class of MediaInfo */
        public class Count
		{
            public Integer coins;
            public Integer danmus;
            public Integer follows;
            public Integer views;
        }

        public String pgcType;
        public Boolean epSpMode;
        public Boolean newEpSpMode;
        public String mainSecTitle;
    }

    public List<EpList> epList;

    /** EpList is the inner class of JavaBean */
    public class EpList
	{
        public Boolean loaded;
        public Integer id;
        public String badge;
        public Integer badgeType;
        public Integer epStatus;
        public Integer aid;
        public Integer cid;
        public String from;
        public String cover;
        public String title;
        public String titleFormat;
        public String vid;
        public String long_title;
        public Boolean hasNext;
        public Integer i;
        public Integer sectionType;
        public String releaseDate;
		
		public AvData linkedAvData;
    }

    public EpInfo epInfo;

    /** EpInfo is the inner class of JavaBean */
    public class EpInfo
	{
        public Boolean loaded;
        public Integer id;
        public String badge;
        public Integer badgeType;
        public Integer epStatus;
        public Integer aid;
        public Integer cid;
        public String from;
        public String cover;
        public String title;
        public String titleFormat;
        public String vid;
        public String longTitle;
        public Boolean hasNext;
        public Integer i;
        public Integer sectionType;
        public String releaseDate;
    }

    public List<Sections> sections;

    /** Sections is the inner class of JavaBean */
    public class Sections
	{
        public Integer id;
        public String title;
        public Integer type;
        public List<EpList> epList;

        /** EpList is the inner class of Sections */
        public class EpList
		{
            public Boolean loaded;
            public Integer id;
            public String badge;
            public Integer badgeType;
            public Integer epStatus;
            public Integer aid;
            public Integer cid;
            public String from;
            public String cover;
            public String title;
            public String titleFormat;
            public String vid;
            public String longTitle;
            public Boolean hasNext;
            public Integer i;
            public Integer sectionType;
            public String releaseDate;
        }

    }

    public List<SsList> ssList;

    /** SsList is the inner class of JavaBean */
    public class SsList
	{
        public Integer id;
        public String title;
        public Integer type;
        public String pgcType;
        public String cover;
        public String epCover;
        public String desc;
        public String badge;
        public Integer badgeType;
        public Integer views;
        public Integer follows;
    }

    public UserState userState;

    /** UserState is the inner class of JavaBean */
    public class UserState
	{
        public Boolean loaded;
    }
    public Player player;

    /** Player is the inner class of JavaBean */
    public class Player
	{
        public Boolean loaded;
        public Boolean miniOn;
        public Integer limitType;
    }

    public Sponsor sponsor;

    /** Sponsor is the inner class of JavaBean */
    public class Sponsor
	{
        public Boolean allReady;
        public Integer allState;
        public Integer allCount;
        public Boolean weekReady;
        public Integer weekState;
        public Integer weekCount;
    }

    public Interact interact;

    /** Interact is the inner class of JavaBean */
    public class Interact
	{
        public Boolean shown;
        public String btnText;
    }

    public PlayerEpList playerEpList;

    /** PlayerEpList is the inner class of JavaBean */
    public class PlayerEpList
	{
        public Integer code;
        public String message;
    }

    public List<String> insertScripts;
}


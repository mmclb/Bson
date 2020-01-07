package com.nanhuajiaren.bson;
import com.google.gson.annotations.SerializedName;

public class BiliEntry
{
	public Integer media_type;
    public Boolean has_dash_audio;
    public Boolean is_completed;
    public Integer total_bytes;
    public Integer downloaded_bytes;
    public String title;
    public String type_tag;
    public String cover;
    public Integer prefered_video_quality;
    public Integer guessed_total_bytes;
    public Integer total_time_milli;
    public Integer danmaku_count;
    public Long time_update_stamp;
    public Long time_create_stamp;
    public long avid;
    public Integer spid;
    public Integer seasion_id;
    public String bvid;
    public Page_data page_data;

    /** Page_data is the inner class of BiliEntry */
    public class Page_data
	{
        public Integer cid;
        public Integer page;
        public String from;
        public String part;
        public String link;
        public String rich_vid;
        public String vid;
        public Boolean has_alias;
        public String weblink;
        public String offsite;
        public Integer tid;
        public Integer width;
        public Integer height;
        public Integer rotate;
    }

}


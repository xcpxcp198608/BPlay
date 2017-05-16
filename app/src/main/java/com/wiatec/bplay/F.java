package com.wiatec.bplay;

/**
 * Created by patrick on 2017/1/13.
 */

public final class F {
    public static final class url{
        public static final String base_url = "http://www.ldlegacy.com:8080/";
        public static final String channel = base_url+"bplay/channel/get";
        public static final String channel_country = base_url+"bplay/channel/getChannelByCountry";
        public static final String channel_style = base_url+"bplay/channel/getChannelByStyle";
        public static final String channel_type = base_url+"bplay/channeltype/get";
        public static final String update = base_url+"bplay/update/get";
        public static final String ad_image = base_url+"control_panel/adimage/get";

        public static final String login = base_url+"control_panel/user/login";
        public static final String logout = base_url+"control_panel/user/logout";
        public static final String check_token = base_url+"control_panel/user/checkToken";
        public static final String check_repeat = base_url+"control_panel/user/checkRepeat";

    }

    public static final class package_name{
        public static final String market = "com.px.bmarket";
        public static final String btv = "org.xbmc.kodi";
        public static final String tv_house = "com.fanshi.tvvideo";
        public static final String show_box = "com.tdo.showbox";
    }
}

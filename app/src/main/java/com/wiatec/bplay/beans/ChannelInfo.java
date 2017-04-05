package com.wiatec.bplay.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by patrick on 2017/2/10.
 */

public class ChannelInfo implements Parcelable {
    private int id;
    private String name;
    private String url;
    private String icon;
    private String type;
    private String country;
    private int sequence;
    private String style;
    private String favorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "ChannelInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", sequence=" + sequence +
                ", style='" + style + '\'' +
                ", favorite='" + favorite + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.icon);
        dest.writeString(this.type);
        dest.writeString(this.country);
        dest.writeInt(this.sequence);
        dest.writeString(this.style);
        dest.writeString(this.favorite);
    }

    public ChannelInfo() {
    }

    protected ChannelInfo(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.url = in.readString();
        this.icon = in.readString();
        this.type = in.readString();
        this.country = in.readString();
        this.sequence = in.readInt();
        this.style = in.readString();
        this.favorite = in.readString();
    }

    public static final Parcelable.Creator<ChannelInfo> CREATOR = new Parcelable.Creator<ChannelInfo>() {
        @Override
        public ChannelInfo createFromParcel(Parcel source) {
            return new ChannelInfo(source);
        }

        @Override
        public ChannelInfo[] newArray(int size) {
            return new ChannelInfo[size];
        }
    };
}

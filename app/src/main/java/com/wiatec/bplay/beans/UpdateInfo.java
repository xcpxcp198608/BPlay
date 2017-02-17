package com.wiatec.bplay.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by patrick on 2017/1/13.
 */

public class UpdateInfo implements Parcelable {
    private int id;
    private int code;
    private String fileName;
    private String packageName;
    private String url;
    private String version;
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "id=" + id +
                ", code=" + code +
                ", fileName='" + fileName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.code);
        dest.writeString(this.fileName);
        dest.writeString(this.packageName);
        dest.writeString(this.url);
        dest.writeString(this.version);
        dest.writeString(this.info);
    }

    public UpdateInfo() {
    }

    protected UpdateInfo(Parcel in) {
        this.id = in.readInt();
        this.code = in.readInt();
        this.fileName = in.readString();
        this.packageName = in.readString();
        this.url = in.readString();
        this.version = in.readString();
        this.info = in.readString();
    }

    public static final Parcelable.Creator<UpdateInfo> CREATOR = new Parcelable.Creator<UpdateInfo>() {
        @Override
        public UpdateInfo createFromParcel(Parcel source) {
            return new UpdateInfo(source);
        }

        @Override
        public UpdateInfo[] newArray(int size) {
            return new UpdateInfo[size];
        }
    };
}

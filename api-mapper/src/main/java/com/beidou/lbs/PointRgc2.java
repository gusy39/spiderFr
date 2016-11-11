package com.beidou.lbs;

/** RGC2逆地理返回
 * Created by Administrator on 16-1-20.
 */
public class PointRgc2 {

    private String distance;

    private String dir;

    private String name;

    private String lng;

    private String lat;

    private String roadAddress;

    private String districtText;

    private PointRgc2Road pointRgc2Road;

    private String district;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public String getDistrictText() {
        return districtText;
    }

    public void setDistrictText(String districtText) {
        this.districtText = districtText;
    }

    public PointRgc2Road getPointRgc2Road() {
        return pointRgc2Road;
    }

    public void setPointRgc2Road(PointRgc2Road pointRgc2Road) {
        this.pointRgc2Road = pointRgc2Road;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}

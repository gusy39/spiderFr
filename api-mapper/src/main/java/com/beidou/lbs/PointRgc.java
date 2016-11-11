package com.beidou.lbs;

/** RGC逆地理返回
 * Created by Administrator on 16-1-20.
 */
public class PointRgc {

    private String districtText;

    private String district;

    private String province;

    private String city;

    private String area;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictText() {
        return districtText;
    }

    public void setDistrictText(String districtText) {
        try {
            if(districtText!=null) {
                String  disSplit[]= districtText.split(">");
                this.province = disSplit[0];
                this.city = disSplit[1];
                this.area = disSplit[2];
            }
        } catch (Exception e) {
        }
        this.districtText = districtText;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }
}

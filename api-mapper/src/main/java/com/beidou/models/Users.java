package com.beidou.models;

import com.beidou.common.dto.BaseDTO;
import java.math.BigDecimal;
import java.util.Date;

public class Users extends BaseDTO {
    private Integer id;

    private String mobile;

    private String email;

    private String userName;

    private String password;

    private String question;

    private String answer;

    private Integer sex;

    private Date birthday;

    private BigDecimal userMoney;

    private BigDecimal frozenMoney;

    private Integer payPoints;

    private Integer rankPoints;

    private Integer addressId;

    private Date lastTime;

    private String lastIp;

    private Integer visitCount;

    private Integer userRank;

    private Integer isSpecial;

    private String salt;

    private Integer flag;

    private String alias;

    private String msn;

    private String qq;

    private Integer isValidated;

    private BigDecimal creditLine;

    private String passwdQuestion;

    private String passwdAnswer;

    private Integer agencyId;

    private Date createTime;

    private Date updateTime;

    private String perImgUrl;

    private String testCreate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(BigDecimal userMoney) {
        this.userMoney = userMoney;
    }

    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public Integer getPayPoints() {
        return payPoints;
    }

    public void setPayPoints(Integer payPoints) {
        this.payPoints = payPoints;
    }

    public Integer getRankPoints() {
        return rankPoints;
    }

    public void setRankPoints(Integer rankPoints) {
        this.rankPoints = rankPoints;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp == null ? null : lastIp.trim();
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getUserRank() {
        return userRank;
    }

    public void setUserRank(Integer userRank) {
        this.userRank = userRank;
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn == null ? null : msn.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public Integer getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Integer isValidated) {
        this.isValidated = isValidated;
    }

    public BigDecimal getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(BigDecimal creditLine) {
        this.creditLine = creditLine;
    }

    public String getPasswdQuestion() {
        return passwdQuestion;
    }

    public void setPasswdQuestion(String passwdQuestion) {
        this.passwdQuestion = passwdQuestion == null ? null : passwdQuestion.trim();
    }

    public String getPasswdAnswer() {
        return passwdAnswer;
    }

    public void setPasswdAnswer(String passwdAnswer) {
        this.passwdAnswer = passwdAnswer == null ? null : passwdAnswer.trim();
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPerImgUrl() {
        return perImgUrl;
    }

    public void setPerImgUrl(String perImgUrl) {
        this.perImgUrl = perImgUrl == null ? null : perImgUrl.trim();
    }

    public String getTestCreate() {
        return testCreate;
    }

    public void setTestCreate(String testCreate) {
        this.testCreate = testCreate == null ? null : testCreate.trim();
    }
}
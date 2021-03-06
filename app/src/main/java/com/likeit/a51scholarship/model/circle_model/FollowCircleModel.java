package com.likeit.a51scholarship.model.circle_model;

/**
 * Created by Administrator on 2017/8/16.
 */

public class FollowCircleModel {

    /**
     * id : 6
     * title : 留学美国
     * detail : 留学美国
     * logo :
     * member_num : 1
     * post_num : 3
     */

    private String id;
    private String title;
    private String detail;
    private String logo;
    private String member_num;
    private String post_num;

    @Override
    public String toString() {
        return "FollowCircleModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", logo='" + logo + '\'' +
                ", member_num='" + member_num + '\'' +
                ", post_num='" + post_num + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMember_num() {
        return member_num;
    }

    public void setMember_num(String member_num) {
        this.member_num = member_num;
    }

    public String getPost_num() {
        return post_num;
    }

    public void setPost_num(String post_num) {
        this.post_num = post_num;
    }

}

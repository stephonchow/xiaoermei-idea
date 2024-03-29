package com.blogfirsttry.po;

import org.hibernate.annotations.Proxy;
import com.blogfirsttry.po.QuickCommentClass;
import javax.persistence.*;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity//添加这行才具备和数据库对应生成的能力
@Table(name = "t_blog")
@Proxy(lazy = false)//关闭懒加载
public class Blog {

    @Id//主键
    @GeneratedValue//自动生成
    private Long id;
    private String title;//标题
    @Basic(fetch = FetchType.LAZY)//懒加载
    @Lob//添加这个注解让content容量变得足够大
    private String content;//博客内容
    private String flag;//标记
    private Integer views;//浏览量
    private boolean appreciation;//赞赏开关
    private boolean shareStatement;//转载声明开关
    private boolean commentabled;//评论开关
    private boolean published;//是否发布
    private boolean recommend;//是否推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//创建事件
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//更新时间
    private boolean adminComment;
//    private QuickCommentClass quickComment = new QuickCommentClass();//快捷回复
    @ManyToOne//多个博客对应一个分类
    //many的那端是关系维护端
    private Type type;
//    @ManyToMany(cascade = {CascadeType.MERGE})
    @ManyToMany(fetch = FetchType.EAGER)
    //博客和标签是多对多，并且由blog维护
    //添加cascade = {CascadeType.PERSIST}后新增
    // 一个tag，会将该tag添加到数据库
    private List<Tag> tags = new ArrayList<>();
    @ManyToOne
    private User user;
//
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();
//
    @Transient
    private String tagIds;
//
    private String description;
//
    public Blog() {
    }
//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
//
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
//
//
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
//
//
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
//
//
    public String getTagIds() {
        return tagIds;
    }
//
    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }
//
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
//
    public void initTags() {
        this.tagIds = tagsToIds(this.getTags());
    }
//
//    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    //
//

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", adminComment=" + adminComment +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

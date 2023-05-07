package com.social.media.app.entity.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
@Entity
public class Post {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    private String postDescription;

    @Lob
    @Column(name = "imageData", length = 1000)
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "userProfileID")
    private UserProfile postedBy;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = {
        CascadeType.ALL
    }, orphanRemoval = true)
    private List<Comments> commentsList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
        name = "UserProfilePost",
        joinColumns = @JoinColumn(name = "PostID"),
        inverseJoinColumns = @JoinColumn(name = "UserProfileID"))
    private Set<UserProfile> likedUserProfiles;

    @CreationTimestamp
    private Date createdDate;

    @UpdateTimestamp
    private Date updatedDate;

    /**
     * @return value of id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id
     *     the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }
    /**
     * @return value of postDescription
     */
    public String getPostDescription() {
        return postDescription;
    }
    /**
     * @param postDescription
     *     the postDescription to set
     */
    public void setPostDescription(final String postDescription) {
        this.postDescription = postDescription;
    }
    /**
     * @return value of postImageUrl
     */
    public byte[] getImageData() {
        return imageData;
    }
    /**
     * @param imageData
     *     the postImageUrl to set
     */
    public void setImageData(final byte[] imageData) {
        this.imageData = imageData;
    }
    /**
     * @return value of postedBy
     */
    public UserProfile getPostedBy() {
        return postedBy;
    }
    /**
     * @param postedBy
     *     the postedBy to set
     */
    public void setPostedBy(final UserProfile postedBy) {
        this.postedBy = postedBy;
    }
    /**
     * @return value of commentsList
     */
    public List<Comments> getCommentsList() {
        return commentsList;
    }
    /**
     * @param commentsList
     *     the commentsList to set
     */
    public void setCommentsList(final List<Comments> commentsList) {
        this.commentsList.clear();
        if (commentsList != null) {

            this.commentsList.addAll(commentsList);
        }
    }
    /**
     * @return value of likedUserProfiles
     */
    public Set<UserProfile> getLikedUserProfiles() {
        return likedUserProfiles;
    }
    /**
     * @param likedUserProfiles
     *     the likedUserProfiles to set
     */
    public void setLikedUserProfiles(final Set<UserProfile> likedUserProfiles) {
        this.likedUserProfiles = likedUserProfiles;
    }
    /**
     * @return value of createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }
    /**
     * @param createdDate
     *     the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }
    /**
     * @return value of updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }
    /**
     * @param updatedDate
     *     the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}

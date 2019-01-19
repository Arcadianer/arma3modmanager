
package manuel.fun.arma3.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "publishedfileid",
    "result",
    "creator",
    "creator_app_id",
    "consumer_app_id",
    "filename",
    "file_size",
    "file_url",
    "hcontent_file",
    "preview_url",
    "hcontent_preview",
    "title",
    "description",
    "time_created",
    "time_updated",
    "visibility",
    "banned",
    "ban_reason",
    "subscriptions",
    "favorited",
    "lifetime_subscriptions",
    "lifetime_favorited",
    "views",
    "tags"
})

public class Publishedfiledetail implements Serializable
{

    @JsonProperty("publishedfileid")
    private String publishedfileid;
    @JsonProperty("result")
    private Long result;
    @JsonProperty("creator")
    private String creator;
    @JsonProperty("creator_app_id")
    private Long creatorAppId;
    @JsonProperty("consumer_app_id")
    private Long consumerAppId;
    @JsonProperty("filename")
    private String filename;
    @JsonProperty("file_size")
    private Long fileSize;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("hcontent_file")
    private String hcontentFile;
    @JsonProperty("preview_url")
    private String previewUrl;
    @JsonProperty("hcontent_preview")
    private String hcontentPreview;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("time_created")
    private Long timeCreated;
    @JsonProperty("time_updated")
    private Long timeUpdated;
    @JsonProperty("visibility")
    private Long visibility;
    @JsonProperty("banned")
    private Long banned;
    @JsonProperty("ban_reason")
    private String banReason;
    @JsonProperty("subscriptions")
    private Long subscriptions;
    @JsonProperty("favorited")
    private Long favorited;
    @JsonProperty("lifetime_subscriptions")
    private Long lifetimeSubscriptions;
    @JsonProperty("lifetime_favorited")
    private Long lifetimeFavorited;
    @JsonProperty("views")
    private Long views;
    @JsonProperty("tags")
    private List<Tag> tags = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -2605379723006373212L;

    @JsonProperty("publishedfileid")
    public String getPublishedfileid() {
        return publishedfileid;
    }

    @JsonProperty("publishedfileid")
    public void setPublishedfileid(String publishedfileid) {
        this.publishedfileid = publishedfileid;
    }

    @JsonProperty("result")
    public Long getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(Long result) {
        this.result = result;
    }

    @JsonProperty("creator")
    public String getCreator() {
        return creator;
    }

    @JsonProperty("creator")
    public void setCreator(String creator) {
        this.creator = creator;
    }

    @JsonProperty("creator_app_id")
    public Long getCreatorAppId() {
        return creatorAppId;
    }

    @JsonProperty("creator_app_id")
    public void setCreatorAppId(Long creatorAppId) {
        this.creatorAppId = creatorAppId;
    }

    @JsonProperty("consumer_app_id")
    public Long getConsumerAppId() {
        return consumerAppId;
    }

    @JsonProperty("consumer_app_id")
    public void setConsumerAppId(Long consumerAppId) {
        this.consumerAppId = consumerAppId;
    }

    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    @JsonProperty("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @JsonProperty("file_size")
    public Long getFileSize() {
        return fileSize;
    }

    @JsonProperty("file_size")
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @JsonProperty("file_url")
    public String getFileUrl() {
        return fileUrl;
    }

    @JsonProperty("file_url")
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @JsonProperty("hcontent_file")
    public String getHcontentFile() {
        return hcontentFile;
    }

    @JsonProperty("hcontent_file")
    public void setHcontentFile(String hcontentFile) {
        this.hcontentFile = hcontentFile;
    }

    @JsonProperty("preview_url")
    public String getPreviewUrl() {
        return previewUrl;
    }

    @JsonProperty("preview_url")
    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @JsonProperty("hcontent_preview")
    public String getHcontentPreview() {
        return hcontentPreview;
    }

    @JsonProperty("hcontent_preview")
    public void setHcontentPreview(String hcontentPreview) {
        this.hcontentPreview = hcontentPreview;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("time_created")
    public Long getTimeCreated() {
        return timeCreated;
    }

    @JsonProperty("time_created")
    public void setTimeCreated(Long timeCreated) {
        this.timeCreated = timeCreated;
    }

    @JsonProperty("time_updated")
    public Long getTimeUpdated() {
        return timeUpdated;
    }

    @JsonProperty("time_updated")
    public void setTimeUpdated(Long timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    @JsonProperty("visibility")
    public Long getVisibility() {
        return visibility;
    }

    @JsonProperty("visibility")
    public void setVisibility(Long visibility) {
        this.visibility = visibility;
    }

    @JsonProperty("banned")
    public Long getBanned() {
        return banned;
    }

    @JsonProperty("banned")
    public void setBanned(Long banned) {
        this.banned = banned;
    }

    @JsonProperty("ban_reason")
    public String getBanReason() {
        return banReason;
    }

    @JsonProperty("ban_reason")
    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    @JsonProperty("subscriptions")
    public Long getSubscriptions() {
        return subscriptions;
    }

    @JsonProperty("subscriptions")
    public void setSubscriptions(Long subscriptions) {
        this.subscriptions = subscriptions;
    }

    @JsonProperty("favorited")
    public Long getFavorited() {
        return favorited;
    }

    @JsonProperty("favorited")
    public void setFavorited(Long favorited) {
        this.favorited = favorited;
    }

    @JsonProperty("lifetime_subscriptions")
    public Long getLifetimeSubscriptions() {
        return lifetimeSubscriptions;
    }

    @JsonProperty("lifetime_subscriptions")
    public void setLifetimeSubscriptions(Long lifetimeSubscriptions) {
        this.lifetimeSubscriptions = lifetimeSubscriptions;
    }

    @JsonProperty("lifetime_favorited")
    public Long getLifetimeFavorited() {
        return lifetimeFavorited;
    }

    @JsonProperty("lifetime_favorited")
    public void setLifetimeFavorited(Long lifetimeFavorited) {
        this.lifetimeFavorited = lifetimeFavorited;
    }

    @JsonProperty("views")
    public Long getViews() {
        return views;
    }

    @JsonProperty("views")
    public void setViews(Long views) {
        this.views = views;
    }

    @JsonProperty("tags")
    public List<Tag> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	@Override
	public String toString() {
		return "Publishedfiledetail [publishedfileid=" + publishedfileid + ", result=" + result + ", creator=" + creator
				+ ", creatorAppId=" + creatorAppId + ", consumerAppId=" + consumerAppId + ", filename=" + filename
				+ ", fileSize=" + fileSize + ", fileUrl=" + fileUrl + ", hcontentFile=" + hcontentFile + ", previewUrl="
				+ previewUrl + ", hcontentPreview=" + hcontentPreview + ", title=" + title + ", description="
				+ description + ", timeCreated=" + timeCreated + ", timeUpdated=" + timeUpdated + ", visibility="
				+ visibility + ", banned=" + banned + ", banReason=" + banReason + ", subscriptions=" + subscriptions
				+ ", favorited=" + favorited + ", lifetimeSubscriptions=" + lifetimeSubscriptions
				+ ", lifetimeFavorited=" + lifetimeFavorited + ", views=" + views + ", tags=" + tags
				+ ", additionalProperties=" + additionalProperties + "]";
	}

}

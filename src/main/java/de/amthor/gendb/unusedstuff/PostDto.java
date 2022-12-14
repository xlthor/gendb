// Generated by delombok at Fri Sep 16 17:50:31 CEST 2022
package de.amthor.gendb.unusedstuff;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(description = "Post model information")
public class PostDto {
    @ApiModelProperty("Blog post id")
    private long id;
    // title should not be null  or empty
    // title should have at least 2 characters
    @ApiModelProperty("Blog post title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;
    // post description should be not null or empty
    // post description should have at least 10 characters
    @ApiModelProperty("Blog post description")
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    // post content should not be null or empty
    @ApiModelProperty("Blog post conent")
    @NotEmpty
    private String content;
    @ApiModelProperty("Blog post comments")
    private Set<CommentDto> comments;

    @java.lang.SuppressWarnings("all")
    public PostDto() {
    }

    @java.lang.SuppressWarnings("all")
    public long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getTitle() {
        return this.title;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescription() {
        return this.description;
    }

    @java.lang.SuppressWarnings("all")
    public String getContent() {
        return this.content;
    }

    @java.lang.SuppressWarnings("all")
    public Set<CommentDto> getComments() {
        return this.comments;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setTitle(final String title) {
        this.title = title;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescription(final String description) {
        this.description = description;
    }

    @java.lang.SuppressWarnings("all")
    public void setContent(final String content) {
        this.content = content;
    }

    @java.lang.SuppressWarnings("all")
    public void setComments(final Set<CommentDto> comments) {
        this.comments = comments;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PostDto)) return false;
        final PostDto other = (PostDto) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final java.lang.Object this$title = this.getTitle();
        final java.lang.Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final java.lang.Object this$description = this.getDescription();
        final java.lang.Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final java.lang.Object this$content = this.getContent();
        final java.lang.Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        final java.lang.Object this$comments = this.getComments();
        final java.lang.Object other$comments = other.getComments();
        if (this$comments == null ? other$comments != null : !this$comments.equals(other$comments)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PostDto;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final java.lang.Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final java.lang.Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final java.lang.Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        final java.lang.Object $comments = this.getComments();
        result = result * PRIME + ($comments == null ? 43 : $comments.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PostDto(id=" + this.getId() + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ", content=" + this.getContent() + ", comments=" + this.getComments() + ")";
    }
}

// src/main/java/yaDTO/BoardCommentDTO.java
package yaDTO;

import java.sql.Timestamp;

public class BoardCommentDTO {
	private long commentId;
    private long postId;
    private String content;    
	private String isDeleted;
    private long createdBy;
	private Timestamp createdAt;
    private Timestamp updatedAt;
    private String writerName; // 조인 표시용
    
    private Long parentId;  // nullable
	private Long rootId;    // nullable
    private int  depth;     // 0=최상위
    private int level; // SELECT LEVEL 값을 담고 싶으면
    
    public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getRootId() {
		return rootId;
	}
	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
    public long getCommentId() {
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	public long getPostId() {
		return postId;
	}
	public void setPostId(long postId) {
		this.postId = postId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	
    @Override
	public String toString() {
		return "BoardCommentDTO [commentId=" + commentId + ", postId=" + postId + ", content=" + content
				+ ", isDeleted=" + isDeleted + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", writerName=" + writerName + ", parentId=" + parentId + ", rootId=" + rootId
				+ ", depth=" + depth + ", level=" + level + "]";
	}
	

}

/*
전체 정리
- writerName은 JOIN 결과 표시용 필드.
- 댓글 soft delete를 위해 isDeleted 유지.
*/

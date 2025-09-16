// src/main/java/yaDTO/BoardPostDTO.java
package yaDTO;

import java.sql.Timestamp;

public class BoardPostDTO {
    private long postId;
    private int categoryId;
    private String title;
    private String content; // CLOB은 문자열로 매핑
    private String isNotice; // 'Y'/'N'
    private long viewCnt;
    private long likeCnt;
    private String isDeleted; // 'Y'/'N'
    private long createdBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // getters/setters
    public long getPostId() { return postId; }
    public void setPostId(long postId) { this.postId = postId; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getIsNotice() { return isNotice; }
    public void setIsNotice(String isNotice) { this.isNotice = isNotice; }
    public long getViewCnt() { return viewCnt; }
    public void setViewCnt(long viewCnt) { this.viewCnt = viewCnt; }
    public long getLikeCnt() { return likeCnt; }
    public void setLikeCnt(long likeCnt) { this.likeCnt = likeCnt; }
    public String getIsDeleted() { return isDeleted; }
    public void setIsDeleted(String isDeleted) { this.isDeleted = isDeleted; }
    public long getCreatedBy() { return createdBy; }
    public void setCreatedBy(long createdBy) { this.createdBy = createdBy; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}

/*
전체 정리
- DTO는 DB 컬럼과 1:1 매핑. CLOB은 문자열로 수신.
- isNotice, isDeleted는 'Y'/'N' 그대로 보존해서 JSP에서 조건 분기.
- Timestamp는 Oracle TIMESTAMP와 매핑.
- 실무 중요: DTO는 가볍게 유지(비즈니스 로직 넣지 않음).
*/

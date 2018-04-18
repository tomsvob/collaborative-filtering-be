package app.DTO;

public class RatingDTO {
    Long fid;
    Long uid;
    Integer rating;

    public RatingDTO(Long fid, Long uid, Integer rating) {
        this.fid = fid;
        this.uid = uid;
        this.rating = rating;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}

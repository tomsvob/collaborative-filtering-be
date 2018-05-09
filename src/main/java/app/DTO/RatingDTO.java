package app.DTO;

public class RatingDTO {
    private Long fid;
    private Long uid;
    private Double rating;

    public RatingDTO(Long fid, Long uid, Double rating) {
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}

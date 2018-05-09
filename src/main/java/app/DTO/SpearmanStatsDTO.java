package app.DTO;

public class SpearmanStatsDTO {
    int usersChanged;
    int filmsRecommended;

    public SpearmanStatsDTO(int usersChanged, int filmsRecommended) {
        this.usersChanged = usersChanged;
        this.filmsRecommended = filmsRecommended;
    }

    public int getUsersChanged() {
        return usersChanged;
    }

    public void setUsersChanged(int usersChanged) {
        this.usersChanged = usersChanged;
    }

    public int getFilmsRecommended() {
        return filmsRecommended;
    }

    public void setFilmsRecommended(int filmsRecommended) {
        this.filmsRecommended = filmsRecommended;
    }
}

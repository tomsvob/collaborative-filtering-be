package app.Spearman;

import app.DTO.RatingDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SpearmanRecommender {

    /**
     * This method calculates recommended movies for all users.
     * Goes through all ratings
     * Assigns ratings to their users (hash map)
     * Calculates recommendations for all users
     *
     * @param ratings (List with all ratings that are to be used for cf)
     * @return (Collection of all users that come up in ratings with their recommendations)
     */
    public static Collection<SpearmanUser> calculateRecommendedMovies(List<RatingDTO> ratings, SpearmanSettings spearmanSettings) {
        HashMap<Long, SpearmanUser> usermap = new HashMap<>();

        // 1. Assign ratings to their spearmanUsers

        for (RatingDTO r : ratings) {
            if (usermap.get(r.getUid()) == null) {
                SpearmanUser u = new SpearmanUser(r.getUid());
                u.addRating(r);
                usermap.put(r.getUid(), u);
            } else {
                usermap.get(r.getUid()).addRating(r);
            }
        }

        // 2. calculate recommendations for all spearmanUsers
        Collection<SpearmanUser> spearmanUsers = usermap.values();
        for (SpearmanUser u : spearmanUsers) {
            u.recommend(spearmanUsers, spearmanSettings);
            System.out.printf("SpearmanUser ID: %d Recommendations: %d%n", u.getUserID(), u.getRecommendations().size());
        }

        return spearmanUsers;
    }
}

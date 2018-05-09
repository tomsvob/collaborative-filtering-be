package app.Spearman;

import app.DTO.RatingDTO;

import java.util.*;

import static java.lang.Math.abs;


public class SpearmanUser {

    private long userid;
    private ArrayList<RatingDTO> ratings;
    private ArrayList<Long> recommendations;

    private Comparator<RatingDTO> ratingDTOComparator = (r1, r2) -> {
        // a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
        if (r1.getRating() > r2.getRating()) return 1;
        else if (r1.getRating() < r2.getRating()) return -1;
        else {
            return Long.compare(r1.getFid(), r2.getFid());
        }
    };

    public ArrayList<Long> getRecommendations() {
        return recommendations;
    }

    public void addRating(RatingDTO r) {
        ratings.add(r);
    }

    public long getUserID() {
        return userid;
    }

    public ArrayList<RatingDTO> getRatings() {
        return ratings;
    }

    private Comparator<SimilarUser> similarUserComparator = (o1, o2) -> -1 * Double.compare(o1.getSpearmanRank(), o2.getSpearmanRank());
    private Comparator<RecommendedMovie> recommendedMovieComparator = Comparator.comparingDouble(o -> o.aggregatedRating);

    public SpearmanUser(long userid) {
        this.userid = userid;
        this.ratings = new ArrayList<>();
        this.recommendations = new ArrayList<>();
    }

    /**
     * This method compares ratings of this and one other user, outputs intersecting and exclusive ratings for second user.
     *
     * @param firstRatings       (ratings of first user)
     * @param secondRatings      (ratings of second user)
     * @param commonRatings (intersection - ratings shared between first and second user)
     * @param ratingsExclusiveToSecond   (exlusives - ratings exclusive to second user)
     */
    private void intersect(ArrayList<RatingDTO> firstRatings, ArrayList<RatingDTO> secondRatings, ArrayList<RatingDTO> commonRatings, ArrayList<RatingDTO> ratingsExclusiveToSecond) {

        boolean otherflag = true;
        if (ratingsExclusiveToSecond == null) otherflag = false;
        for (RatingDTO r : secondRatings) {
            long movieid = r.getFid();
            boolean flag = false;
            for (RatingDTO p : firstRatings) {
                if (movieid == p.getFid()) {
                    flag = true;
                    break;
                }
            }
            if (flag) commonRatings.add(r);
            else if (otherflag) ratingsExclusiveToSecond.add(r);
        }
    }

    /**
     * This method calculates spearman rank between for this and one other user.
     *
     * @param other (other user)
     * @return double - spearman rank <-1,1> or -2 in case of insufficient data present.
     */
    private SimilarUser calculateSpearmanRank(SpearmanUser other) {
        // get ratings from both users
        ArrayList<RatingDTO> otherRatings = other.getRatings();
        // get user rated movies intersection (eliminate nonmutual ratings)
        ArrayList<RatingDTO> userSharedRatings = new ArrayList<>();
        ArrayList<RatingDTO> otherSharedRatings = new ArrayList<>();
        ArrayList<RatingDTO> otherExclusiveRatings = new ArrayList<>();
        double spearman = -2;


        // intersect A and B
        intersect(otherRatings, this.ratings, userSharedRatings, null);

        // intersect B and A
        intersect(this.ratings, otherRatings, otherSharedRatings, otherExclusiveRatings);


        // calculate ranks for both users
        userSharedRatings.sort(ratingDTOComparator);
        otherSharedRatings.sort(ratingDTOComparator);


        // if the sample is sufficiently large (at least 10 shared movie ratings), calculate spearman
        if (userSharedRatings.size() <= 10) {

            // calculate spearman
            double d = 0;
            for (int i = 0; i < userSharedRatings.size(); i++) {
                RatingDTO r = userSharedRatings.get(i);
                long movieid = r.getFid();
                int j = 0;
                for (; j < otherSharedRatings.size(); j++) {
                    long idatj = otherSharedRatings.get(j).getFid();
                    if (idatj == movieid) break;
                }
                int absval = abs(j - i);
                int sqabs = absval * absval;
                d += sqabs;
            }
            spearman = 1 - (d / (userSharedRatings.size() * ((userSharedRatings.size() * userSharedRatings.size()) - 1)));

        }
        // return spearman
        return new SimilarUser(spearman, other, otherExclusiveRatings);
    }

    /**
     * This method calculates recommended movies for this user based on
     *
     * @param spearmanUsers (ArrayList with data for cf - ratings paired with thier spearmanUsers)
     */
    void recommend(Collection<SpearmanUser> spearmanUsers, SpearmanSettings spearmanSettings) {
        // find x most similar spearmanUsers
        PriorityQueue<SimilarUser> similarQueue = new PriorityQueue<>(10, similarUserComparator);

        for (SpearmanUser u : spearmanUsers) {
            SimilarUser su = calculateSpearmanRank(u);
            if (similarQueue.size() < spearmanSettings.getNumberOfSimilarUsers()) {
                similarQueue.add(su);
            } else if (su.getSpearmanRank() > similarQueue.peek().getSpearmanRank()) {
                similarQueue.poll();
                similarQueue.add(su);
            }
        }

        // aggregate their ratings and find best recommendations
        ArrayList<SimilarUser> similarUsers = new ArrayList<>();
        while (!similarQueue.isEmpty()) {
            similarUsers.add(similarQueue.poll());
        }

        HashMap<Long, RecommendedMovie> aggregatedRatings = new HashMap<>();

        for (SimilarUser u : similarUsers) {
            for (RatingDTO r : u.getExclusive_ratings()) {
                if (aggregatedRatings.containsKey(r.getFid())) continue;
                long movieid = r.getFid();
                double rating = r.getRating();
                int ctr = 1;
                long usrid = u.getSpearmanUser().getUserID();
                for (SimilarUser i : similarUsers) {
                    if (i.getSpearmanUser().getUserID() != usrid) {
                        for (RatingDTO a : i.getExclusive_ratings()) {
                            if (a.getFid() == movieid) {
                                rating += a.getRating();
                                ctr++;
                                break;
                            }
                        }
                    }
                }
                // calculate average rating
                double averageRating = rating / ctr;

                // TODO experiment with required similarity rate (currently 40%)
                if (ctr > spearmanSettings.getNumberOfRequiredMatches())
                    aggregatedRatings.put(movieid, new RecommendedMovie(movieid, averageRating)); //
            }
        }

        List<RecommendedMovie> recommendedMovies = new ArrayList<>(aggregatedRatings.values());
        recommendedMovies.sort(recommendedMovieComparator);
        this.recommendations.clear();
        int numOfRecommendedFilms = Math.min(spearmanSettings.getNumberOfRecommendations(), recommendedMovies.size());
        for (RecommendedMovie movie : recommendedMovies.subList(0, numOfRecommendedFilms)) {
            this.recommendations.add(movie.movieID);
        }
    }
}

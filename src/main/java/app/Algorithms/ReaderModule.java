package app.Algorithms;

import app.DTO.RatingDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ReaderModule {


    /**
     * Retrieves all ratings and runs the collaborative filtering process.
     * Then it updates recommendations for all users in db.
     *
     * (this is a prototype - reads ratings from file, writes recommendations to file)
     *
     * @param args
     */
    // local prototype - this functionality has to be implemented in services!
//    public static void main (String[] args) throws IOException {
//
//
//        long maxUserID = 0; // TODO replace with number of users from db
//        Scanner scanner = new Scanner(System.in);
//        ArrayList<RatingDTO> ratings = new ArrayList<>();
//
//        // read ratings
//        Scanner ratingscanner;
//        File rfile = new File("ratings.csv");
//        try {
//            ratingscanner = new Scanner(rfile);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            System.out.println("File with ratings not found!");
//            return;
//        }
//        String header;
//        ratingscanner.useDelimiter("\r\n");
//        header = ratingscanner.nextLine();
//
//        int ctr = 0;
//        while (ratingscanner.hasNext()) {
//            ratingscanner.useDelimiter(",");
//            long userid;
//            userid = ratingscanner.nextInt();
//            if (userid > maxUserID) maxUserID = userid;
//            long movieid;
//            movieid = ratingscanner.nextInt();
//            double score;
//            score = ratingscanner.nextDouble();
//            ratingscanner.useDelimiter("\r\n");
//            String dummy = ratingscanner.nextLine();
//            RatingDTO rating = new RatingDTO(movieid, userid, score);
//            ratings.add(rating);
//        }
//
//        ArrayList<User> users = calculateRecommendedMovies(ratings);
//
//
//
//
//        for (User u : users) {
//            System.out.println("User ID: " + u.getUserID() + " Recommendations: " + u.writeRecommendations());
//        }
//    }
//

    /**
     * This method calculates recommended movies for all users.
     * Goes through all ratings
     * Assigns ratings to their users (hash map)
     * Calculates recommendations for all users
     * Returns arraylist with recommended users
     *
     * @param ratings (ArrayList with all ratings that are to be used for cf)
     * @return (ArrayList with all users that come up in ratings with their recommendations)
     */
    public static ArrayList<User> calculateRecommendedMovies(List<RatingDTO> ratings, Settings settings) {

        ArrayList<User> users = new ArrayList<>();
        HashMap<Long, User> usermap = new HashMap<>();


        // 1. Assign ratings to their users

        for (RatingDTO r : ratings) {
            if (usermap.get(r.getUid()) == null) {
                User u = new User(r.getUid());
                u.addRating(r);
                usermap.put(r.getUid(), u);
            } else {
                usermap.get(r.getUid()).addRating(r);
            }
        }


        // 2. move users into arrayList

        Iterator it = usermap.values().iterator();
        while (it.hasNext()) {
            users.add((User) it.next());
        }

        // 3. calculate recommendations for all users

        for (User u : users) {
            u.recommend(users, settings);
        }

        return users;
    }
}

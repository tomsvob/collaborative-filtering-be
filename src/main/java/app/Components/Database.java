package app.Components;

import app.Enums.DataGender;
import app.Enums.UserType;
import app.Models.AppUser;
import org.ajbrown.namemachine.Gender;
import org.ajbrown.namemachine.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Component
public class Database {

    private NameGenerator generator = new NameGenerator();

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void insertDataToDatabase() {
        String userSQL = loadUsers();
        runQuery(entityManager, userSQL);
//        String movieSQL = loadMovies("data/ml-1m/movies.dat", "::");
        String movieSQL = loadMovies("data/ml-latest-small/movies.csv", ",");

        runQuery(entityManager, movieSQL);
//        loadRatings(entityManager, "data/ml-1m/ratings.dat", "::");
        loadRatings(entityManager, "data/ml-latest-small/ratings.csv", ",");
        System.out.println("== Database LOADED ==");
    }

    private AppUser generateUser() {
        return new AppUser(UserType.USER, generator.generateName(Gender.MALE).toString());
    }

    private Gender getGender(DataGender dataGender) {
        return dataGender.equals(DataGender.M) ? Gender.MALE : Gender.FEMALE;
    }

    private String generateUserName(Gender gender) {
        return generator.generateName(gender).toString();
    }

    private void loadDataFromFile(Path path, Consumer<? super String> action) {
        try {
            System.out.printf("Loading data from: %s%n", path.toAbsolutePath());
            Files.lines(path).forEach(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadUsers() {
        List<String> sqlLines = new ArrayList<>();
        loadDataFromFile(Paths.get("data/ml-1m/users.dat"), line -> {
            String[] split = line.split("::");
            if (split.length == 5) {
                Long id = Long.parseLong(split[0]);
                DataGender gender = DataGender.valueOf(split[1]);
                String name = generateUserName(getGender(gender));
                List<String> data = new ArrayList<>();
                data.add(id.toString());
                data.add(String.format("'%s'", UserType.USER.toString()));
                data.add(String.format("'%s'", name));
                String join = String.join(", ", data);
                sqlLines.add("( " + join + " )");
            }
        });

        return "INSERT INTO app_user ( id, user_type, username ) VALUES" + System.lineSeparator() + String.join("," + System.lineSeparator(), sqlLines) + ";";
    }

    private String loadMovies(String fileName, String separator) {
        List<String> sqlLines = new ArrayList<>();
        loadDataFromFile(Paths.get(fileName), line -> {
            String[] split = line.split(separator);
            if (split.length == 3) {
                Long id = Long.parseLong(split[0]);
                String title = split[1];

                List<String> data = new ArrayList<>();
                data.add(id.toString());
                data.add(String.format("'%s'", title.replace("'", "''")));
                String join = String.join(", ", data);
                sqlLines.add("( " + join + " )");
            }
        });

        return "INSERT INTO film ( id, name ) VALUES" + System.lineSeparator() + String.join("," + System.lineSeparator(), sqlLines) + ";";
    }

    private void loadRatings(EntityManager entityManager, String fileName, String separator) {
        List<String> sqlLines = new ArrayList<>();
        AtomicReference<Long> counter = new AtomicReference<>(1L);
        loadDataFromFile(Paths.get(fileName), (line) -> {
            String[] split = line.split(separator);

            if (split.length == 4) {
                Long uid = Long.parseLong(split[0]);
                Long fid = Long.parseLong(split[1]);
                Double rating = Double.parseDouble(split[2]);

                List<String> data = new ArrayList<>();
                data.add(counter.get().toString());
                data.add(rating.toString());
                data.add(fid.toString());
                data.add(uid.toString());
                String join = String.join(", ", data);
                sqlLines.add("( " + join + " )");
                counter.getAndSet(counter.get() + 1);

                if ((counter.get() % 10000) == 0) {
                    String sql = String.format("INSERT INTO rating ( id, rating, film_id, app_user_id ) VALUES%s%s;", System.lineSeparator(), String.join("," + System.lineSeparator(), sqlLines));
                    Query nativeQuery = entityManager.createNativeQuery(sql);
                    nativeQuery.executeUpdate();
                    System.out.printf("== LOADED %d ==%n", counter.get());
                    sqlLines.clear();
                }
            }
        });

        runQuery(entityManager, "INSERT INTO rating ( id, rating, film_id, app_user_id ) VALUES" + System.lineSeparator() + String.join("," + System.lineSeparator(), sqlLines) + ";");
    }

    private <E, ID extends Serializable> void storeData(JpaRepository<E, ID> dao, Iterable<E> list) {
        System.out.printf("Storing %s...%n", list.getClass().getName());
        List<E> save = dao.save(list);
        System.out.printf("Inserted: %d%n", save.size());
        System.out.println("-- DONE --");
    }

    private void runQuery(EntityManager entityManager, String sql) {
        System.out.println("Creating sql...");
        Query nativeQuery = entityManager.createNativeQuery(sql);
        System.out.println("Running sql...");
        nativeQuery.executeUpdate();
        System.out.println("== LOADED ==");
    }
}

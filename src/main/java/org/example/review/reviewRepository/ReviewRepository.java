package org.example.review.reviewRepository;

import org.example.Container;
import org.example.db.DBConnection;
import org.example.review.entity.Review;
import org.example.review.reviewService.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewRepository {
    private DBConnection dbConnection;

    public ReviewRepository () {
        dbConnection = Container.getDBconnection();
    }
    public int create(int score, String selectMovie, String writing, String user_id, String regDate) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("INSERT INTO review "));
        sb.append(String.format("SET score = '%d', ", score));
        sb.append(String.format("movieTitle = '%s', ", selectMovie));
        sb.append(String.format("writing = '%s', ", writing));
        sb.append(String.format("user_id = '%s' , ", user_id));
        sb.append(String.format("regDate = now(); "));

        int id = dbConnection.insert(sb.toString());

        return id;
    }

    public List<Review> getReviewAllList() {
        List<Review> reviewList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("SELECT * FROM review"));

        List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());

        for (Map<String, Object> row : rows) {
            reviewList.add(new Review(row));
        }
        return reviewList;
    }

    public void getReviewUserList() {
        List<Review> reviewList = getReviewAllList();

        String user_id = Container.getLoginedUser().getUser_id();
        for (int i = 0; i < reviewList.size(); i++) {
            Review review = reviewList.get(i);
            if (review.getUser_id().equals(user_id)) {
                System.out.printf("%s, %s, %d, %s, %s\n", review.getUser_id(), review.getId(), review.getScore(), review.getWriting(), review.getRegDate());
            }
        }
    }

    public void remove(Review review) {
        int id = review.getId();

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("DELETE FROM review "));
        sb.append(String.format("WHERE id = '%d' ", id));

        dbConnection.delete(sb.toString());
    }
    public void modify(Review review, int score, String writing) {
        int id = review.getId();

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("UPDATE review "));
        sb.append(String.format("SET score = '%d', ", score ));
        sb.append(String.format("writing = '%s' ", writing));
        sb.append(String.format("WHERE id = '%d' ", id));

        dbConnection.update(sb.toString());
    }
    public Review getReviewListById(int id) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM review "));
        sb.append(String.format("WHERE id = '%d' ", id));


        Map<String, Object> row = dbConnection.selectRow(sb.toString());

        Review review = new Review(row);

        return review;
    }
    public Review getReviewUserListById() {
        List<Review> reviewList = getReviewAllList();
        for (int i = 0; i < reviewList.size(); i++) {
            Review review = reviewList.get(i);
            if (review.getUser_id().equals(Container.getLoginedUser().getUser_id())) {
                return review;
            }
        }
        return null;
    }

}
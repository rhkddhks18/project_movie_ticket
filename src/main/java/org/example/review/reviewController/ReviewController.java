package org.example.review.reviewController;

import org.example.Container;
import org.example.movie.movieService.MovieService;
import org.example.review.entity.Review;
import org.example.review.entity.UserReview;
import org.example.review.reviewService.ReviewService;
import org.example.util.Util;

import java.util.List;

public class ReviewController {
    ReviewService reviewService = new ReviewService();
    MovieService movieService = new MovieService();

    public void write(int movie_id) {

        if (reviewService.isReserved() == false) {
            System.out.println("예매를 완료 후 이용해주세요");
            return;
        }

        System.out.println(movieService.getMovie(movie_id).getTitle() + " 리뷰작성");

        int score = reviewService.checkScore();
        System.out.print("리뷰 내용: ");
        String writing = Container.getSc().nextLine();

        int id = reviewService.create(score, Container.getLoginedUser().getId(), movieService.getMovie(movie_id).getTitle(), writing, Util.nowDateTime());
        System.out.println(id + "번째 리뷰가 등록되었습니다.");
    }

    public void list(int movie_id) {

        List<Review> reviewList = reviewService.getReviewAllList();

        while (true) {
            System.out.println(movieService.getMovie(movie_id).getTitle() + " 게시판");
                if (reviewList.size() == 0) {
                    System.out.println("게시물이 없습니다.");
                    break;
                } else {
                    System.out.println("게시물 번호 / 작성자 / 평점 / 리뷰내용 / 작성일자");
                    for (int i = 0; i < reviewList.size(); i++) {
                        Review review = reviewList.get(i);
                        if (movieService.getMovie(movie_id).getTitle().equals(review.getMovie_title())) {
                            System.out.printf("%d / %s / %d / %s / %s\n", review.getId(), review.getUserName(), review.getScore(), review.getWriting(), review.getRegDate());
                        }
                    }
                    break;
                }
        }
    }

    public void remove() {
        List<UserReview> userReviewList = reviewService.getReviewUserList();
        if (userReviewList == null) {
            System.out.println("작성한 리뷰가 존재하지 않습니다.");
        } else {
            System.out.println("==== 내 리뷰 ====");
            for(int i = 0; i < userReviewList.size(); i++) {
                UserReview userReview = userReviewList.get(i);
                System.out.printf("%d / %s / %d / %s / %s\n", userReview.getId(), userReview.getMovie_title(), userReview.getScore(), userReview.getWriting(), userReview.getRegDate());
            }

            System.out.println("삭제할 리뷰의 ID 번호를 입력해주세요");
            int id = Integer.parseInt(Container.getSc().nextLine());
            Review review = reviewService.getReviewListById(id);
            if (review == null) {
                System.out.printf("%d번 리뷰내용이 존재하지 않습니다.\n", id);
                return;
            }
            System.out.printf("%d번 리뷰내용이 삭제 되었습니다.\n", id);
                this.reviewService.remove(review);
        }
    }

    public void modify() {
        List<UserReview> userReviewList = reviewService.getReviewUserList();
        if (userReviewList == null) {
            System.out.println("작성한 리뷰가 존재하지 않습니다.");
        } else {
            System.out.println("==== 내 리뷰 ====");
            for(int i = 0; i < userReviewList.size(); i++) {
                UserReview userReview = userReviewList.get(i);
                System.out.printf("%d / %s / %d / %s / %s\n", userReview.getId(), userReview.getMovie_title(), userReview.getScore(), userReview.getWriting(), userReview.getRegDate());
            }
            try {
                System.out.println("수정할 리뷰의 ID 번호를 입력해주세요");
                int id = Integer.parseInt(Container.getSc().nextLine());
                Review review = reviewService.getReviewListById(id);
                if (review.getId() == 0) {
                    System.out.printf("%d번 리뷰내용이 존재하지 않습니다.\n", id);
                    return;
                }
                int score = reviewService.checkScore();

                System.out.print("리뷰 수정: ");
                String writing = Container.getSc().nextLine();

                reviewService.modify(review, score, writing);
                System.out.printf("%d번 리뷰내용이 수정되었습니다.\n", id);
            } catch (Exception e) {
                System.out.println("수정할 리뷰의 ID값을 숫자로 입력해주세요");
            }

        }
    }
}
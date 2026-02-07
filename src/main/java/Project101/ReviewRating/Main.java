package Project101.ReviewRating;

public class Main {
    public static void main(String[] args) {
        ReviewService reviewService = new ReviewService();
        reviewService.postReview(1,123.2,"sd");
    }
}

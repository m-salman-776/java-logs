package Project101.ReviewRating;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceTest {

    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        reviewService = new ReviewService();
    }

    @Test
    void testPostReviewAndSummary() {
        int entityId = 101;
        reviewService.postReview(entityId, 5.0, "Great!");
        reviewService.postReview(entityId, 3.0, "Okay");

        ReviewSummary summary = reviewService.getReviewSummary(entityId);
        // Average: (5 + 3) / 2 = 4.0
        assertEquals(4.0, summary.getRating(), 0.01);

        List<Review> reviews = reviewService.getReviews(entityId, 0, 10);
        assertEquals(2, reviews.size());
    }

    @Test
    void testPagination() {
        int entityId = 102;
        // Post 10 reviews
        for (int i = 1; i <= 10; i++) {
            reviewService.postReview(entityId, 4.0, "Review " + i);
        }

        // Offset 0, Limit 5 -> Should get reviews 1-5
        List<Review> page1 = reviewService.getReviews(entityId, 0, 5);
        assertEquals(5, page1.size());
        assertEquals("Review 1", page1.get(0).content);
        assertEquals("Review 5", page1.get(4).content);

        // Offset 5, Limit 5 -> Should get reviews 6-10
        List<Review> page2 = reviewService.getReviews(entityId, 5, 5);
        assertEquals(5, page2.size());
        assertEquals("Review 6", page2.get(0).content);
        assertEquals("Review 10", page2.get(4).content);

        // Offset 8, Limit 5 -> Should get reviews 9-10 (only 2 remaining)
        List<Review> page3 = reviewService.getReviews(entityId, 8, 5);
        assertEquals(2, page3.size());
        assertEquals("Review 9", page3.get(0).content);
    }

    @Test
    void testAddReaction() {
        int entityId = 103;
        reviewService.postReview(entityId, 5.0, "React to me");
        List<Review> reviews = reviewService.getReviews(entityId, 0, 1);
        int reviewId = reviews.get(0).id;

        reviewService.addReaction(reviewId, "Like");
        reviewService.addReaction(reviewId, "Like");
        reviewService.addReaction(reviewId, "Funny");

        Review review = reviewService.getReviews(entityId, 0, 1).get(0);
        Map<String, Integer> reactions = review.getReactions();

        assertEquals(2, reactions.get("Like"));
        assertEquals(1, reactions.get("Funny"));
    }

    @Test
    void testConcurrentPostReview() throws InterruptedException {
        int entityId = 104;
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    reviewService.postReview(entityId, 4.0, "Concurrent Review");
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        ReviewSummary summary = reviewService.getReviewSummary(entityId);
        assertEquals(4.0, summary.getRating(), 0.01);

        List<Review> reviews = reviewService.getReviews(entityId, 0, 200);
        assertEquals(threadCount, reviews.size());
    }

    @Test
    void testGetReviewForNonExistentEntity() {
        List<Review> reviews = reviewService.getReviews(999, 0, 10);
        assertTrue(reviews.isEmpty());
    }
}
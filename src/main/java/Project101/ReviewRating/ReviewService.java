package Project101.ReviewRating;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

public class ReviewService {
    // key : EntityId , value : Set of Review
    Map<Integer, ConcurrentSkipListSet<Integer>> entityReview;
    // key : EntityId :
    Map<Integer,ReviewSummary> reviewSummary;
    Map<Integer,Review> reviews;
    AtomicInteger reviewIdGen;
    public ReviewService(){
        entityReview = new ConcurrentHashMap<>();
        reviewSummary = new ConcurrentHashMap<>();
        reviews = new ConcurrentHashMap<>();
        reviewIdGen = new AtomicInteger(0);
    }
    public void postReview(int entityId,double rating,String content){
        int reviewId = reviewIdGen.incrementAndGet();
        Review review = new Review(reviewId,rating,content,entityId,"HOTEL");
        if (!checkReview(review)) {
            return;
        }
        reviews.put(reviewId,review);
        entityReview.computeIfAbsent(entityId,k -> new ConcurrentSkipListSet<>()).add(reviewId);
        updateReviewSummary(review);
    }
    public List<Review> getReviews(int entityId,int offset,int limit){
        List<Review> reviewList = new ArrayList<>();
        if (!entityReview.containsKey(entityId)) return reviewList;

        Iterator<Integer> iterator = this.entityReview.get(entityId).iterator();
        while (offset > 0 && iterator.hasNext()){
            iterator.next();
            offset -= 1;
        }
        while (limit > 0 && iterator.hasNext()){
            reviewList.add(reviews.get(iterator.next()));
            limit -= 1;
        }
        return reviewList;
    }
    public ReviewSummary getReviewSummary(int entityId){
        return reviewSummary.getOrDefault(entityId,new ReviewSummary(0,0));
    }
    private void updateReviewSummary(Review review){
        this.reviewSummary.putIfAbsent(review.entityId, new ReviewSummary(0,0));
        this.reviewSummary.get(review.entityId).updateSummary(review.rating);
    }
    public boolean checkReview(Review review){
        return true;
    }
    public void addReaction(int reviewId, String reactionType){
        Review review = reviews.get(reviewId);
        if (review != null){
            review.addReaction(reactionType);
        }
    }
}

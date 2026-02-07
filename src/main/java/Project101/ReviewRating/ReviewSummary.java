package Project101.ReviewRating;

public class ReviewSummary {
    double ratingSum;
    int count;
//    Map<>
    public ReviewSummary(double ratingSum,int count){
        this.ratingSum = ratingSum;
        this.count = count;
    }
    public synchronized void updateSummary(double rating){
        this.ratingSum += rating;
        this.count++;
    }
    public synchronized double getRating(){
        return this.ratingSum / (double) Math.max(1,count);
    }
}

package Project101.ReviewRating;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Review {
    int id;
    double rating;
    String content;
    int entityId;
    String entityType;
    Map<String, Integer> reactions;

    public Review(int id,double rating,String content,int entityId,String entityType){
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.entityId = entityId;
        this.entityType = entityType;
        this.reactions = new ConcurrentHashMap<>();
    }

    public void addReaction(String reactionType) {
        this.reactions.merge(reactionType, 1, Integer::sum);
    }

    public Map<String, Integer> getReactions() {
        return this.reactions;
    }
}

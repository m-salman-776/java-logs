package Twitter;

public class Tweet {
    int id;
    int timeStamp;
    Tweet next;
    Tweet(int id,int timeStamp){
        this.timeStamp = timeStamp;
        this.id = id;
        this.next = null;
    }
}

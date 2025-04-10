package apiassignment.wishlist.model;

public class Friend {
    private int friendshipId;
    private int friendOne;
    private int friendTwo;
    private String friendStatus;


    public Friend(int friendshipId, int friendOne, int friendTwo, String friendStatus) {
        this.friendshipId = friendshipId;
        this.friendOne = friendOne;
        this.friendTwo = friendTwo;
        this.friendStatus = friendStatus;
    }

    public Friend() {

    }

    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }

    public int getFriendOne() {
        return friendOne;
    }

    public void setFriendOne(int friendOne) {
        this.friendOne = friendOne;
    }

    public int getFriendTwo() {
        return friendTwo;
    }

    public void setFriendTwo(int friendTwo) {
        this.friendTwo = friendTwo;
    }

    public String getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(String friendStatus) {
        this.friendStatus = friendStatus;
    }
}

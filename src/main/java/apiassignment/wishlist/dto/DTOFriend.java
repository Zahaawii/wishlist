package apiassignment.wishlist.dto;

public class DTOFriend {
    private int friendship;
    private String name;
    private String username;


    public DTOFriend(int friendship, String name, String username) {
        this.friendship = friendship;
        this.name = name;
        this.username = username;
    }
    public DTOFriend(){
    }

    public int getFriendship() {
        return friendship;
    }

    public void setFriendship(int friendship) {
        this.friendship = friendship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString(){
        return "\nfriendship: " + friendship + "\nname:" + name + "\nUsername: " + username;
    }
}

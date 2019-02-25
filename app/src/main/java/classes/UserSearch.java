package classes;

public class UserSearch {
    private String keyword;

    public UserSearch() { }

    public Userlist searchUser(String keyword, Userlist users) {
        //TODO
        this.keyword = keyword;
        Userlist userlist = new Userlist();
        return userlist;
    }

    public String getKeyword() { return keyword; }
}

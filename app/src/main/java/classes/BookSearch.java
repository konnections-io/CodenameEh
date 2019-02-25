package classes;

public class BookSearch {
    private User user;

    public BookSearch(User user) {
        this.user = user;
    }

    public Booklist searchDatabase(String keyword) {
        //TODO
        Booklist booklist = new Booklist();
        return booklist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

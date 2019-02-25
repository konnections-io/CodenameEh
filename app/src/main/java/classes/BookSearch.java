package classes;

public class BookSearch {
    private User user;

    public BookSearch(User user) {
        this.user = user;
    }

    public Booklist searchDatabase(String keyword) {
        //TODO: Have a booklist of books related to keyword by accessing a database
        Booklist booklist = new Booklist();
        // Add books to this booklist depending on keyword specified
        return booklist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

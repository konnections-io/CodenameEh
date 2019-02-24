package classes;

public class Scanning {
    private int ISBN;

    public Scanning(int ISBN) {
        this.ISBN = ISBN;
    }

    public int scanISBN(String image) {
        return 0;
    }

    public void changeBookProperties(Book book) {
        //TODO
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }
}

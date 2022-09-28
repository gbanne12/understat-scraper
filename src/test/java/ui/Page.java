package ui;

public enum Page {
    HOME_PAGE("http://localhost/index.html");

    private final String url;

    Page(String url) {
        this.url = url;
    }
}

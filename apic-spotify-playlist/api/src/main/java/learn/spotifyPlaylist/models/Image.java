package learn.spotifyPlaylist.models;

public class Image {

    //image_id int AI PK
    //url varchar(250)
    //height int
    //width int

    private int imageId;

    private String url;

    private int height;

    private int width;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

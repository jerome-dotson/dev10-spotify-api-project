package learn.spotifyPlaylist.models;

import java.util.List;

public class Playlist {

    private int playlistId;

    private String name;

    private String description;

    private int appUserId;
    private AppUser appUser;

    private List<Track> tracks;

    private List<Tag> tags;

    private Image image;

    private List<UserPlaylist> collaborators;

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<UserPlaylist> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<UserPlaylist> collaborators) {
        this.collaborators = collaborators;
    }
}

package SpotifyData;

import java.util.ArrayList;
import java.util.List;

public class Song {
	
	private String artistName;
	private String trackName;
	private String albumName;
	private String artistURI;
	private String trackURI;
	private String albumURI;
	private int duration;
	private List<String> playlistNames;
	
	public Song(String artistName, String trackName, String albumName, String artistURI, String trackURI,
			String albumURI, int duration) {
		super();
		this.artistName = artistName;
		this.trackName = trackName;
		this.albumName = albumName;
		this.artistURI = artistURI;
		this.trackURI = trackURI;
		this.albumURI = albumURI;
		this.duration = duration;
		playlistNames = new ArrayList<>();
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getArtistURI() {
		return artistURI;
	}

	public void setArtistURI(String artistURI) {
		this.artistURI = artistURI;
	}

	public String getTrackURI() {
		return trackURI;
	}

	public void setTrackURI(String trackURI) {
		this.trackURI = trackURI;
	}

	public String getAlbumURI() {
		return albumURI;
	}

	public void setAlbumURI(String albumURI) {
		this.albumURI = albumURI;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	public List<String> getPlaylistNames() {
		return playlistNames;
	}
	
	
}

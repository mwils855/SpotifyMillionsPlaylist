package SpotifyData;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
	
	private String playListName;
	private int numHoldouts;
	private int playlistID;
	private int numTracks;
	private int numSamples;
	private List<Song> songs;
	
	public Playlist(String playListName, int numHoldouts, int playlistID, int numTracks, int numSamples) {
		super();
		this.playListName = playListName;
		this.numHoldouts = numHoldouts;
		this.playlistID = playlistID;
		this.numTracks = numTracks;
		this.numSamples = numSamples;
		songs = new ArrayList<>();
	}

	public String getPlayListName() {
		return playListName;
	}

	public void setPlayListName(String playListName) {
		this.playListName = playListName;
	}

	public int getNumHoldouts() {
		return numHoldouts;
	}

	public void setNumHoldouts(int numHoldouts) {
		this.numHoldouts = numHoldouts;
	}

	public int getPlaylistID() {
		return playlistID;
	}

	public void setPlaylistID(int playlistID) {
		this.playlistID = playlistID;
	}

	public int getNumTracks() {
		return numTracks;
	}

	public void setNumTracks(int numTracks) {
		this.numTracks = numTracks;
	}

	public int getNumSamples() {
		return numSamples;
	}

	public void setNumSamples(int numSamples) {
		this.numSamples = numSamples;
	}

	public List<Song> getSongs() {
		return songs;
	}
	public void addSongs(Song song) {
		songs.add(song);
	}

	@Override
	public String toString() {
		return "Playlist [playListName=" + playListName + ", numHoldouts=" + numHoldouts + ", playlistID=" + playlistID
				+ ", numTracks=" + numTracks + ", numSamples=" + numSamples + ", songs=" + songs + "]";
	}
	
	
	
}

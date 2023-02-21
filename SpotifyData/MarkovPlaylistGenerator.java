package SpotifyData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.json.simple.parser.ParseException;

import Parser.SpotifyJsonParser;

public class MarkovPlaylistGenerator {
	
	private List<ListNode> songList;
	
	private Random rnGenerator;
	
	public MarkovPlaylistGenerator(Random generator) {
		
		songList = new ArrayList<>();
		rnGenerator = generator;
		
	}
	
	
	public void train(SpotifyJsonParser parser) {
		Map<Integer, Playlist> playlists = parser.getPlaylists();
		for (Integer id : playlists.keySet()) {
			Playlist playlist = playlists.get(id);
			List<Song> songs = playlist.getSongs();
			if (songs.isEmpty()) {
				continue;
			}
			Song prevSong = songs.get(0);
			for (int i = 1; i < songs.size(); i++) {
				boolean containsSong = false;
				for (ListNode ln : songList) {
					if(ln.getSong().equals(prevSong)) {
						ln.addNextSong(songs.get(i));
						containsSong = true;
						break;
					
					}
					/*
					 * 
					 * use the below to get the song in front of the last if statement
					 * 
					if ((i < songs.size() - 1) && ln.getSong().equals(songs.get(i + 1))){
						ln.addNextSong(songs.get(i + 1));
					}
					*/
				}
				if (!containsSong) {
					ListNode newNode = new ListNode(prevSong);
					songList.add(newNode);
					newNode.addNextSong(songs.get(i));
				}
				prevSong = songs.get(i);
			}
		}
	}
	public Playlist generatePlaylist(Song song, int numSongs) throws Exception {
		/*if (songSet.isEmpty()) {
		//if (songList.isEmpty()) {
			throw new Exception("songList is Empty!");
		}
		*/
		Playlist playlist = new Playlist("New Playlist", 0, 0, 0, 0);
		Song currSong = song;
		playlist.addSongs(currSong);
		int count = 1;
		while (count < numSongs) {
			for (ListNode ln : songList) {
				if(ln.getSong().equals(currSong)) {
					currSong = ln.getRandomNextSong(rnGenerator);
					if (currSong == null) {
						currSong = playlist.getSongs().get(playlist.getSongs().size() - 1);
						playlist.getSongs().remove(playlist.getSongs().size() -1);
					}
					playlist.addSongs(currSong);
					break;
				}
			}
			count += 1;
		}
		return playlist;
	}
	
	public void printNextSongs(int start, int end) {
		for (int i = start; i <= end; i++) {			
			ListNode ln = songList.get(i);
			String song = ln.getSong().getArtistName() + "- " + ln.getSong().getTrackName();
			System.out.print(song + ": ");
			List<Song> nextSongs = ln.getNextSongs();
			for (Song s : nextSongs) {
				System.out.print(s.getArtistName() + "- " +s.getTrackName() + ", ");
			}
			System.out.println();
		}
		
	}
	public void printPlaylist(Playlist playlist) {
		System.out.println("Playlist: " + playlist.getPlayListName());
		int count = 1;
		for (Song song : playlist.getSongs()) {
			System.out.println("#" + count + " : " + song.getArtistName() + "- " + song.getTrackName());
			count += 1;
		}
	}
	
	public List<ListNode> getSongList() {
		return songList;
	}
	
	public static void main (String[] args) throws Exception {
		SpotifyJsonParser parser = new SpotifyJsonParser();
		//parser.parseJSON("/Users/mikewilson/Desktop/Sample_Spotify_Data.json.txt");
		parser.parseJSON("/Users/mikewilson/Desktop/Spotify_Challenge/challenge_set.json");
		MarkovPlaylistGenerator mpg = new MarkovPlaylistGenerator(new Random(50));
		mpg.train(parser);
		Random random = new Random(67);
		List<ListNode> list = mpg.getSongList();
		Song randomSong = list.get(random.nextInt(list.size())).getSong();
		Playlist playlist = mpg.generatePlaylist(randomSong, 30);
		mpg.printPlaylist(playlist);
		
	}
}

class ListNode implements Comparable<ListNode>{
	
	private Song song;
	private List<Song> nextSongs;
	
	public ListNode(Song song) {
		this.song = song;
		nextSongs = new ArrayList<>();
	}
	public Song getSong(){
		return song;
	}
	public List<Song> getNextSongs(){
		return nextSongs;
	}
	public void addNextSong(Song song) {
		nextSongs.add(song);
	}
	public Song getRandomNextSong(Random generator) {
		return nextSongs.get(generator.nextInt(nextSongs.size()));
	}
	public String toString() {
		return song.getArtistName() + " " + song.getTrackName();
	}
	public int compareTo(ListNode other) {
		return this.getSong().getTrackURI().compareTo(other.getSong().getTrackURI());
	}
	
	
}

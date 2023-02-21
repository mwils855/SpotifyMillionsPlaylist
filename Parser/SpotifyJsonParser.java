package Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import SpotifyData.Playlist;
import SpotifyData.Song;

public class SpotifyJsonParser {
	
	private Map<String, Song> songs;
	private Map<Integer, Playlist> playlists;
	
	public SpotifyJsonParser() {
		songs = new HashMap<>();
		playlists = new HashMap<>();
	}
	
	public String jsonFileToString(String file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		String json = null;
		while ((line = br.readLine()) != null) {
			json = json + line;
		}
		br.close();
		return json;
	}
	
	public void parseJSON(String jsonFile) throws ParseException, IOException {
		
		Object obj = new JSONParser().parse(new FileReader(jsonFile));
		JSONObject jo = (JSONObject) obj;
		
		JSONArray playlistArray = (JSONArray) jo.get("playlists");
		Iterator itr1 = playlistArray.iterator();
		while (itr1.hasNext()) {
			JSONObject playlist = (JSONObject) itr1.next();
			String name = (String) playlist.get("name");
			int numHoldouts = Math.toIntExact((long) playlist.get("num_holdouts"));
			int pid = Math.toIntExact((long) playlist.get("pid"));
			int numTracks = Math.toIntExact((long) playlist.get("num_tracks"));
			int numSamples = Math.toIntExact((long) playlist.get("num_samples"));
			Playlist p = new Playlist(name, numHoldouts, pid, numTracks, numSamples);
			
			JSONArray tracks = (JSONArray) playlist.get("tracks");
			Iterator itr2 = tracks.iterator();
			
			while (itr2.hasNext()) {
				JSONObject track = (JSONObject) itr2.next();
				String trackURI = (String) track.get("track_uri");
				if(!songs.containsKey(trackURI)) {
					String artistName = (String) track.get("artist_name");
					String artistURI = (String) track.get("artist_uri");
					String trackName = (String) track.get("track_name");
					String albumURI = (String) track.get("album_uri");
					int duration = Math.toIntExact((long) track.get("duration_ms"));
					String albumName = (String) track.get("album_name");
					Song s = new Song(artistName, trackName, albumName, artistURI, trackURI, albumURI, duration);
					songs.put(trackURI, s);
					List<String> playlistNames = s.getPlaylistNames();
					playlistNames.add(name);
					List<Song> songList = p.getSongs();
					songList.add(s);
				}
				else {
					Song s = songs.get(trackURI);
					List<String> playlistNames = s.getPlaylistNames();
					playlistNames.add(name);
					List<Song> songList = p.getSongs();
					songList.add(s);
				}
				
			}
			playlists.put(pid, p);
			
		}
		
		
		
		
	}
	public Map<Integer, Playlist> getPlaylists(){
		return playlists;
	}
	public Map<String, Song> getSongs(){
		return songs;
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		SpotifyJsonParser parser = new SpotifyJsonParser();
		//parser.parseJSON("/Users/mikewilson/Desktop/Sample_Spotify_Data.json.txt");
		parser.parseJSON("/Users/mikewilson/Desktop/Spotify_Challenge/challenge_set.json");
		Map<Integer, Playlist> map = parser.getPlaylists();
		Map<String, Song> songs = parser.getSongs();
		System.out.println("Playlist size: " + map.size());
		System.out.println("Songs size: " + songs.size());
		/*for (Integer i : map.keySet()) {
			System.out.println("*****" + map.get(i).getPlayListName());
			for (Song song : map.get(i).getSongs()) {
				System.out.println(song.getArtistName() + " " + song.getTrackName());
			}
		}
		*/
		int count = 0;
			for (String s : songs.keySet()) {
				if (count == 50) {
					break;
				}
				Song song = songs.get(s);
				System.out.println("*****" + song.getArtistName() + " " + song.getTrackName());
				List<String> playlistNames = song.getPlaylistNames();
				for (String name : playlistNames) {
					System.out.print(name + ", ");
				}
				System.out.println();
				count += 1;
			}
			
		
	}
}	

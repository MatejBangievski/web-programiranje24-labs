package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;

import java.util.List;
import java.util.Optional;

public interface SongService{
    List<Song> listSongs();
    //Artist addArtistToSong(Artist artist, Long songId);
    public Song addArtistToSong(Long songId, Long artistId);
    public Song findByTrackId(String trackId);
    public Optional<Song> findById(Long songId);
    public Optional<Song> saveSongWithAlbum (String trackId, String title, String genre, Integer releaseYear, Long albumId);
    public Optional<Song> saveSongWithAlbumAndArtists (String trackId, String title, String genre, Integer releaseYear, Long albumId, List<Artist> artists);
    void deleteById(Long id);
    public List<Song> getSongsByAlbumId(Long albumId);
}
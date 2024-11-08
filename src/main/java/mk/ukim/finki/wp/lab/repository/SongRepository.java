package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.boostrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SongRepository {
    public List<Song> findAll() {
        return DataHolder.songs.stream().collect(Collectors.toList());
    }

    public Song findByTrackId(String trackId) {
        return DataHolder.songs.stream().filter(s -> s.getTrackId().equals(trackId)).findFirst().orElse(null);
    }

    public Artist addArtistToSong(Artist artist, Song song) {
        Song wanted_song = DataHolder.songs.stream().filter(s -> s.getTrackId().equals(song.getTrackId())).findFirst().orElse(null);
        //DataHolder.songs.contains(song)
        if (wanted_song == null) { //ne postoi, dodaj ja
            wanted_song = song;
            DataHolder.songs.add(wanted_song);
        }

        wanted_song.getPerformers().add(artist);

        return artist;
    }

    public Optional<Song> save (String trackId, String title, String genre, Integer releaseYear, Album album) {
        // Create/Update
        if (trackId == null || title == null || genre == null || releaseYear == null || album == null) {
            throw new IllegalArgumentException();
        }

        Song song = new Song(trackId, title, genre, releaseYear, album);
        DataHolder.songs.removeIf(s -> s.getTrackId().equals(trackId));
        DataHolder.songs.add(song);
        return Optional.of(song);
    }

    public Optional<Song> findById(Long songId) {
        return DataHolder.songs.stream().filter(s -> s.getId().equals(songId)).findFirst();
    }

    public void deleteById (Long id) {
        DataHolder.songs.removeIf(s -> s.getId().equals(id));
    }
}

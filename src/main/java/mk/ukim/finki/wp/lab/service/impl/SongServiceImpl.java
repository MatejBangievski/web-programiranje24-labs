package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.boostrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.model.exception.AlbumNotFoundException;
import mk.ukim.finki.wp.lab.model.exception.ArtistNotFoundException;
import mk.ukim.finki.wp.lab.model.exception.InvalidArgumentsException;
import mk.ukim.finki.wp.lab.repository.AlbumRepository;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.SongRepository;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }

    @Override
    public Artist addArtistToSong(Artist artist, Song song) {
        if (artist == null || song == null) {
            throw new InvalidArgumentsException();
        }

        /* Dali treba da se addne i artist vo listata artisti?
           Ne e navedeno metod za dodavanje artist vo interfejsot...
        if (!artistRepository.findAll().contains(artist)) {
            artistRepository.
        }
         */

        if (!artistRepository.findAll().contains(artist)) {
            throw new ArtistNotFoundException();
        }

        return songRepository.addArtistToSong(artist, song);
    }

    @Override
    public Song findByTrackId(String trackId) {
        return songRepository.findByTrackId(trackId);
    }

    @Override
    public Optional<Song> findById(Long songId) {
        return songRepository.findById(songId);
    }

    @Override
    public Optional<Song> save(String trackId, String title, String genre, Integer releaseYear, Long albumId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException(albumId));
        return songRepository.save(trackId, title, genre, releaseYear, album);
    }

    @Override
    public void deleteById(Long id) {
        songRepository.deleteById(id);
    }
}

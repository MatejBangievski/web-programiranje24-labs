package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.boostrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.model.exception.ArtistNotFoundException;
import mk.ukim.finki.wp.lab.model.exception.InvalidArgumentsException;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.SongRepository;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
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
}

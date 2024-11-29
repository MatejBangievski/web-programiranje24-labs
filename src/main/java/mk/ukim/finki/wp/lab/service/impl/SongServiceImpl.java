package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.model.exception.AlbumNotFoundException;
import mk.ukim.finki.wp.lab.model.exception.ArtistNotFoundException;
import mk.ukim.finki.wp.lab.model.exception.InvalidArgumentsException;
import mk.ukim.finki.wp.lab.model.exception.SongNotFoundException;
import mk.ukim.finki.wp.lab.repository.impl.InMemoryAlbumRepository;
import mk.ukim.finki.wp.lab.repository.impl.InMemoryArtistRepository;
import mk.ukim.finki.wp.lab.repository.impl.InMemorySongRepository;
import mk.ukim.finki.wp.lab.repository.jpa.AlbumRepository;
import mk.ukim.finki.wp.lab.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.jpa.SongRepository;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    //private final InMemorySongRepository inMemorySongRepository;
    //private final InMemoryArtistRepository inMemoryArtistRepository;
    //private final InMemoryAlbumRepository inMemoryAlbumRepository;
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

// - JPA REPOSITORY TREBA DA IMA SAMO CRUD OPERACII, KE SE IMPLEMENTIRA DIREKT VO SERVICE
//    @Override
//    public Artist addArtistToSong(Artist artist, Long songId) {
//        if (artist == null || songId == null) {
//            throw new InvalidArgumentsException();
//        }
//
//        /* Dali treba da se addne i artist vo listata artisti?
//           Ne e navedeno metod za dodavanje artist vo interfejsot...
//        if (!artistRepository.findAll().contains(artist)) {
//            artistRepository.
//        }
//         */
//
//        if (!inMemoryArtistRepository.findAll().contains(artist)) {
//            throw new ArtistNotFoundException();
//        }
//
//        if (!songRepository.findById(songId).isPresent()) {
//            throw new SongNotFoundException(songId);
//        }
//
//        Song song = songRepository.findById(songId).get();
//        return songRepository.addArtistToSong(artist, song);
//    }

    @Override
    public Song addArtistToSong(Long songId, Long artistId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException(songId));
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException(artistId));

        song.getPerformers().add(artist);  // assuming there is a list of artists in the Song entity
        songRepository.save(song);
        return song;
    }

    @Override
    public Song findByTrackId(String trackId) {
        return songRepository.findByTrackId(trackId);
    }

    @Override
    public Optional<Song> findById(Long songId) {
        return songRepository.findById(songId);
    }

//    @Override
//    public Optional<Song> saveWithAlbum(String trackId, String title, String genre, Integer releaseYear, Long albumId) {
//        Album album = albumRepository.findById(albumId)
//                .orElseThrow(() -> new AlbumNotFoundException(albumId));
//        return songRepository.saveWithAlbum(trackId, title, genre, releaseYear, album);
//    }

    public Optional<Song> saveSongWithAlbum(String trackId, String title, String genre, Integer releaseYear, Long albumId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException(albumId));

        Song existingSong = songRepository.findByTrackId(trackId);
        if (existingSong != null) {
            songRepository.delete(existingSong);
        }

        Song song = new Song(trackId, title, genre, releaseYear, album);
        Song savedSong = songRepository.save(song);

        return Optional.of(savedSong);
    }


//    public Optional<Song> saveWithArtists (String trackId, String title, String genre, Integer releaseYear, Long albumId, List<Artist> artists) {
//        Album album = albumRepository.findById(albumId)
//                .orElseThrow(() -> new AlbumNotFoundException(albumId));
//        return songRepository.saveWithArtists(trackId, title, genre, releaseYear, album, artists);
//    }

    public Optional<Song> saveSongWithAlbumAndArtists(String trackId, String title, String genre, Integer releaseYear, Long albumId, List<Artist> artists) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException(albumId));

        Song existingSong = songRepository.findByTrackId(trackId);
        if (existingSong != null) {
            songRepository.delete(existingSong);
        }

        Song song = new Song(trackId, title, genre, releaseYear, album);
        song.setPerformers(artists);
        Song savedSong = songRepository.save(song);

        return Optional.of(savedSong);
    }

    @Override
    public void deleteById(Long id) {
        songRepository.deleteById(id);
    }

    @Override
    public List<Song> findAllByAlbum_Id(Long albumId) {
        return findAllByAlbum_Id(albumId);
    }
}

package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// JpaRepository<Entity, ID>
public interface SongRepository extends JpaRepository<Song, Long> {
    //Query(value = "select * from ...")
    //...NameLike - ne mora celosna sovpadlivost
    Song findByTrackId(String trackId);
    List<Song> findAllByAlbum_Id(Long albumId);

    //    @Query("UPDATE Song s SET s.artists = :artists WHERE s.id = :songId")
    //Artist addArtistToSong(Artist artist, Song song);

    // Samo save - pravi conflict bidejki ima vekje predefinirano save

    //ke se implementira vo service koristejki jpa save metod
    //Optional<Song> saveWithAlbum (String trackId, String title, String genre, Integer releaseYear, Album album);

    //ke se implementira vo service koristejki jpa save metod
    //    @Modifying
    //    @Transactional
    //    @Query("INSERT INTO Song (trackId, title, genre, releaseYear, album) VALUES (:trackId, :title, :genre, :releaseYear, :album)")
    //Optional<Song> saveWithArtists (String trackId, String title, String genre, Integer releaseYear, Album album, List<Artist> artists);

}

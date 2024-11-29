package mk.ukim.finki.wp.lab.boostrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.jpa.AlbumRepository;
import mk.ukim.finki.wp.lab.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.jpa.SongRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataHolder {
    public static List<Artist> artists = null;
    public static List<Song> songs = null;
    public static List<Album> albums = null;

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public DataHolder(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    @PostConstruct
    public void init (){
        artists = new ArrayList<>();
        songs = new ArrayList<>();
        albums = new ArrayList<>();

        //5 vrednosti vo listata artists
        if (this.artistRepository.count() == 0) {
            artists.add(new Artist("Abel", "Makkonen Tesfaye", "the Weeknd")); // L-long
            artists.add(new Artist("Frederick", "John Philip Gibson", "FredAgain")); // L-long
            artists.add(new Artist("Sonny", "John Moore", "Skrillex")); // L-long
            artists.add(new Artist("Jacques", "Bermon Webster", "Travis Scott")); // L-long
            artists.add(new Artist("Kanye", "Omari West", "Ye")); // L-long

            this.artistRepository.saveAll(artists);
        }

        //5 vrednosti vo listata albums
        if (this.albumRepository.count() == 0) {
            albums.add(new Album("ALBUM1", "R&B", "2020"));
            albums.add(new Album("ALBUM2", "EDM", "2023"));
            albums.add(new Album("ALBUM3", "Hip Hop", "2006"));
            albums.add(new Album("ALBUM4", "Rage", "2024"));
            albums.add(new Album("ALBUM5", "POP", "2010"));

            this.albumRepository.saveAll(albums);
        }

        //5 vrednosti vo listata songs
        if (this.songRepository.count() == 0) {
            songs.add(new Song("first", "AfterHours", "R&B", 2020, albums.get(0), artists.stream().filter(a -> a.getBio().equals("the Weeknd")).collect(Collectors.toList()))); //The Weeknd
            songs.add(new Song("second", "Rumble", "EDM", 2023, albums.get(1), artists.stream().filter(a -> a.getBio().equals("FredAgain") || a.getBio().equals("Skrillex")).collect(Collectors.toList()))); //FredAgain, Skrillex
            songs.add(new Song("third", "Homecoming", "Hip Hop", 2006, albums.get(2), artists.stream().filter(a -> a.getBio().equals("Ye")).collect(Collectors.toList()))); //Ye
            songs.add(new Song("fourth", "Fein", "Rage", 2024, albums.get(3), artists.stream().filter(a -> a.getBio().equals("Travis Scott")).collect(Collectors.toList()))); //Travis Scott
            songs.add(new Song("fifth", "K-POP", "Rage", 2023, albums.get(3), artists.stream().filter(a -> a.getBio().equals("Travis Scott") || a.getBio().equals("the Weeknd")).collect(Collectors.toList()))); //Travis Scott, The Weeknd

            this.songRepository.saveAll(songs);
        }
    }
}

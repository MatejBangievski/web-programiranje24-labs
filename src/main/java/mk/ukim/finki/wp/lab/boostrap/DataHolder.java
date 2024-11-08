package mk.ukim.finki.wp.lab.boostrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataHolder {
    public static List<Artist> artists = null;
    public static List<Song> songs = null;

    @PostConstruct
    public void init (){
        artists = new ArrayList<>();
        songs = new ArrayList<>();

        //5 vrednosti vo listata artists
        artists.add(new Artist(0L, "Abel", "Makkonen Tesfaye", "the Weeknd")); // L-long
        artists.add(new Artist(1L, "Frederick", "John Philip Gibson", "FredAgain")); // L-long
        artists.add(new Artist(2L, "Sonny", "John Moore", "Skrillex")); // L-long
        artists.add(new Artist(3L, "Jacques", "Bermon Webster", "Travis Scott")); // L-long
        artists.add(new Artist(4L, "Kanye", "Omari West", "Ye")); // L-long

        //5 vrednosti vo listata songs
        songs.add(new Song("first", "AfterHours", "R&B", 2020, artists.stream().filter(a -> a.getId() == 0L).collect(Collectors.toList()))); //The Weeknd
        songs.add(new Song("second", "Rumble", "EDM", 2023, artists.stream().filter(a -> a.getId() == 1L || a.getId() == 2L).collect(Collectors.toList()))); //FredAgain, Skrillex
        songs.add(new Song("third", "Homecoming", "Hip Hop", 2006, artists.stream().filter(a -> a.getId() == 4L).collect(Collectors.toList()))); //Ye
        songs.add(new Song("fourth", "Fein", "Rage", 2024, artists.stream().filter(a -> a.getId() == 3L).collect(Collectors.toList()))); //Travis Scott
        songs.add(new Song("fifth", "K-POP", "Rage", 2023, artists.stream().filter(a -> a.getId() == 0L || a.getId() == 3L).collect(Collectors.toList()))); //Travis Scott, The Weeknd
    }
}

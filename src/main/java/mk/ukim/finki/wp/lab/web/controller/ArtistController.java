package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.*;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final SongService songService;

    public ArtistController(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping
    public String showArtistsPage() {
        // Ne mozhe direktno da se pristapi ovaa strana
            return "redirect:/songs?error=NoSongSelected";
    }

    @PostMapping
    public String getArtistsPage(@RequestParam(required = false) String error,
                                 @RequestParam(required = false) Long songId,
                                 Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        if (songService.findById(songId).isPresent()) {
            Song song = songService.findById(songId).get();
            model.addAttribute("artists", artistService.listArtists());
            model.addAttribute("song", song);
            return "artistsList";
        } else {
            return "redirect:/songs?error=NoSongSelected";
        }
    }

    @PostMapping("/add-artist")
    public String addArtistsToSong(@RequestParam Long songId,
                                   @RequestParam Long artistId){

        songService.addArtistToSong(songId, artistId);

        return "redirect:/songs";
    }
}

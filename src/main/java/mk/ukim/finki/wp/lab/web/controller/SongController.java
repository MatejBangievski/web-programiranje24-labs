package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.*;
import mk.ukim.finki.wp.lab.service.AlbumService;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;
    private final ArtistService artistService;
    private final AlbumService albumService;

    public SongController(SongService songService, ArtistService artistService, AlbumService albumService) {
        this.songService = songService;
        this.artistService = artistService;
        this.albumService = albumService;
    }

    @GetMapping
    public String getSongsPage(@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("songs", songService.listSongs());
        return "listSongs";
    }

    @GetMapping("/add-form")
    public String getAddSongPage(Model model) {
        List<Album> albums = albumService.findAll();
        model.addAttribute("albums", albums);
        return "add-song";
    }

    @PostMapping("/add")
    public String saveSong(@RequestParam String trackId,
                           @RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam Integer releaseYear,
                           @RequestParam Long albumId,
                           @RequestParam (required = false) List<Long> artistIds) {

        if (artistIds == null  || artistIds.isEmpty()) {
            songService.saveSongWithAlbum(trackId, title, genre, releaseYear, albumId);
        }  else {
            List<Artist> artists = artistService.listByIds(artistIds);
            songService.saveSongWithAlbumAndArtists(trackId, title, genre, releaseYear, albumId, artists);
        }
        return "redirect:/songs";
    }

    @PostMapping("edit-form/{id}")
    public String getEditSongForm(@PathVariable Long id, Model model) {
        if (this.songService.findById(id).isPresent()) {
            Song song = songService.findById(id).get();
            List<Artist> artists = artistService.listArtists();
            List<Album> albums = albumService.findAll();
            model.addAttribute("song", song);
            model.addAttribute("artists", artists);
            model.addAttribute("albums", albums);
            return "add-song";
        }
        return "redirect:/songs?error=SongNotFound";
    }

    @PostMapping("/delete/{id}")
    public String deleteSong(@PathVariable Long id) {
        songService.deleteById(id);
        return "redirect:/songs";
    }
}

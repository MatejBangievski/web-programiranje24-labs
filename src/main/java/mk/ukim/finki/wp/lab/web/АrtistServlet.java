package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "artist-servlet", urlPatterns = "/artist")
public class АrtistServlet extends HttpServlet {

    private ArtistService artistService;
    private SongService songService;
    private SpringTemplateEngine springTemplateEngine;

    public АrtistServlet(ArtistService artistService, SongService songService, SpringTemplateEngine springTemplateEngine) {
        this.artistService = artistService;
        this.songService = songService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        context.setVariable("artists", artistService.listArtists());

        springTemplateEngine.process("artistsList.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Chuvame stringovi i treba da se parsira vo long
        Long artistId = Long.parseLong(req.getParameter("person")) ;

        if (artistId != null) {
            //Stavi go artistot vo pesnata i forwardni na druga strana
            Song song = (Song) req.getSession().getAttribute("song");
            Artist artist = artistService.ArtistfindById(artistId);
            songService.addArtistToSong(artist, song.getId());

            resp.sendRedirect("/songDetails");
        } else {
            //Ne e uspeshno, vrati se nazad
            resp.sendRedirect("/artist");
        }


    }
}

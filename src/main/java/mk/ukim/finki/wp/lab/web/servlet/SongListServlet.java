package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.SongService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name="song-servlet", urlPatterns = "/listSongs")
public class SongListServlet extends HttpServlet {

    private final SongService songService;
    private final SpringTemplateEngine springTemplateEngine;

    public SongListServlet(SongService songService, SpringTemplateEngine springTemplateEngine) {
        this.songService = songService;
        this.springTemplateEngine = springTemplateEngine;
    }

    //Treba da se prikazhat site dobieni pesni od metodot list songs

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);
        context.setVariable("songs", songService.listSongs());

        springTemplateEngine.process("listSongs.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Dokolku e selektirana pesna - preprati
        String trackId = req.getParameter("trackId");
        if (trackId != null) {
            //Kje ja chuvam pesnata vo session
            Song song = songService.findByTrackId(trackId);

            //Pred da ja stavam vo sesija, ke ja invalidiram sesijata za da nemam povekje od 1 pesna
            req.getSession().invalidate();

            req.getSession().setAttribute("song", song);
            resp.sendRedirect("/artist");
        } else {
            //Ako ne e selektirana pesna - vrati se
            resp.sendRedirect("/listSongs");
        }
    }
}

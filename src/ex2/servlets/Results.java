package ex2.servlets;

import ex2.classes.Answer;
import ex2.classes.Poll;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Result Servlet: the "home page" - shows poll results and vote button (only if not voted)<BR>
 * URL Pattern = "", init Params = file path of "poll.txt"
 */
@WebServlet(name = "Results", urlPatterns = "",
        initParams = {@WebInitParam(name = "fpath", value = "poll.txt", description = "File Path")})
public class Results extends HttpServlet {
    // members:
    Poll poll;

    /**
     * initializes the server, opens file path, reads data and builds the poll
     *
     * @param config ServletConfig
     * @throws ServletException Exception
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // init poll object
        poll = new Poll();

        // open file
        String filePath = config.getInitParameter("fpath");
        ServletContext context = getServletContext();
        String path = context.getRealPath(filePath);
        File file = new File(path);
        Scanner scanner = null;

        // read file data
        try {
            // init scanner
            scanner = new Scanner(file);

            String qst = null;

            // skipping empty lines
            while ((qst = scanner.nextLine().trim()).isEmpty()) ;

            // init question
            poll.setQuestion(qst.trim());

            // init answers
            while (scanner.hasNextLine()) {
                String ans = scanner.nextLine();
                if (!ans.trim().isEmpty()) {
                    poll.getAnswers().add(new Answer(ans));
                }
            }
        } catch (Exception e) {
            // if poll.txt isn't as required: set to not valid
            poll.isNotValid();
            System.out.println("--- Error: poll.txt file isn't valid ---");
        } finally {
            // closing input stream
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * redirects to doGet(); just in case
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException Exception
     * @throws IOException      Exception
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Home page:
     * 1. print msg if poll object isn't available
     * 2. show results with counts
     * 3. if never voted: show vote button; else show "voted!" msg and results
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException Exception
     * @throws IOException      Exception
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // set content-type and open writer
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // include header
        request.getRequestDispatcher("/header.html").include(request, response);

        if (!poll.isValid()) {                                                          // file isn't found or not valid
            out.println("<h2><i class=\"fas fa-chevron-right\"></i> ");                 // >
            out.println("Survey isn't available, visit later pls ...</h2>");            // err msg : not available
        } else if (hasVoted(request)) {                                                  // has voted!
            out.println("<h2><i class=\"fas fa-chevron-right\"></i> ");                 // >
            out.println("You have voted! ...</h2>");                                    // msg : voted
            showResults(out);                                                           // show results
        } else {
            // first time in
            // send the Poll object to the vote servlet
            getServletContext().setAttribute("poll", poll);                           // send poll object to Vote

            showResults(out);                                                           // show results
            // create Vote Button
            out.println("<div class=\"col-12\"><form id=\"voteBtn\" action=\"/vote\" method=\"GET\">");
            out.println("<button type=\"submit\" class=\"btn btn-primary\">Vote Now!</button></form></div>");
        }

        // include footer
        request.getRequestDispatcher("/footer.html").include(request, response);
        out.close();
    }

    /**
     * Checks if the user has already voted
     * method: check if cookies has "vote" cookie
     *
     * @param request HttpServletRequest
     * @return true if voted; else false
     */
    private boolean hasVoted(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals("vote")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints the Poll with the Results
     *
     * @param out PrintWriter
     */
    private void showResults(PrintWriter out) {
        // prints the question
        out.println("<div class=\"col-12\"><h2><i class=\"fas fa-chevron-right\"></i> " + poll.getQuestion() + "</h2>");
        // prints answers and counters
        out.println("<UL>");
        for (Answer answer : poll.getAnswers()) {
            out.println("<LI> " + answer.getAnswer() + " = " + answer.getCount() + "</LI>");
        }
        out.println("</UL></div>");
    }
}

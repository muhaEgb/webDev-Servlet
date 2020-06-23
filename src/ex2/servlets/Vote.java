package ex2.servlets;

import ex2.classes.Answer;
import ex2.classes.Poll;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Vote Servlet: dynamic poll page, build and handle poll form<BR>
 * URL Pattern = "/vote"
 */
@WebServlet(name = "Vote", urlPatterns = "/vote")
public class Vote extends HttpServlet {
    // members:
    Poll poll;

    /**
     * Receives POST request from doGet Form, updates poll, makes "vote" cookie, resend to Results
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException Exception
     * @throws IOException      Exception
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (!hasVoted(request)) {
                // increment answer counter
                int answerVal = Integer.parseInt(request.getParameter("answer"));
                poll.getAnswers().get(answerVal - 1).inc();
                System.out.println("--- Your voted: " + answerVal + " ---");
                // setting new cookie -
                response.addCookie(new Cookie("vote", request.getParameter("answer")));
            }
        } catch (Exception e) {
            // ignore the out of range & input exceptions in the catch and handles it in the finally
        } finally {
            // redirect back to Results anyway
            response.sendRedirect("/");
        }
    }

    /**
     * Builds Poll form
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

        poll = (Poll) getServletContext().getAttribute("poll");                         // get poll from Results

        // include header
        request.getRequestDispatcher("/header.html").include(request, response);

        if (hasVoted(request)) {                                                              // already voted: go home
            response.sendRedirect("/");
        } else {
            // build survey form
            out.println("<form id=\"form-poll\" action=\"/vote\" method=\"POST\">");        // init form
            out.println("<h2><i class=\"fas fa-chevron-right\"></i> ");                     // >
            out.println(poll.getQuestion() + "</h2>");                                      // survey question
            int index = 1;
            out.println("<UL>");                                                            // unordered list
            for (Answer answer : poll.getAnswers()) {                                       // print answers
                out.println("<LI><input type=\"radio\" name=\"answer\" value=\""
                        + index + "\"> " + answer.getAnswer() + "</LI>");
                index++;
            }
            out.println("</UL><button type=\"submit\" class=\"btn btn-primary\">Submit</button></form>"); // submit Btn
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
}

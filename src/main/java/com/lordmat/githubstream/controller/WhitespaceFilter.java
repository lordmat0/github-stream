package com.lordmat.githubstream.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * This filter class removes any whitespace from the response. It actually trims
 * all leading and trailing spaces or tabs and newlines before writing to the
 * response stream. This will greatly save the network bandwith, but this will
 * make the source of the response more hard to read.
 * <p>
 * This filter should be configured in the web.xml as follows:
 * <pre>
 * &lt;filter&gt;
 *     &lt;description&gt;
 *         This filter class removes any whitespace from the response. It actually trims all
 *         leading and trailing spaces or tabs and newlines before writing to the response stream.
 *         This will greatly save the network bandwith, but this will make the source of the
 *         response more hard to read.
 *     &lt;/description&gt;
 *     &lt;filter-name&gt;whitespaceFilter&lt;/filter-name&gt;
 *     &lt;filter-class&gt;net.balusc.webapp.WhitespaceFilter&lt;/filter-class&gt;
 * &lt;/filter&gt;
 * &lt;filter-mapping&gt;
 *     &lt;filter-name&gt;whitespaceFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * </pre>
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/12/whitespacefilter.html
 * 
 * @author mat - modifications
 */
public class WhitespaceFilter implements Filter {

    private final static Logger LOGGER = Logger.getLogger(WhitespaceFilter.class.getName());

    // Constants ----------------------------------------------------------------------------------
    // Specify here where you'd like to start/stop the trimming.
    // You may want to replace this by init-param and initialize in init() instead.
    static final String[] START_TRIM_AFTER = {"<html", "</textarea", "</pre"};
    static final String[] STOP_TRIM_AFTER = {"</html", "<textarea", "<pre"};

    // Actions ------------------------------------------------------------------------------------
    /**
     * @param config
     * @throws javax.servlet.ServletException
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        //
    }

    /**
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpres = (HttpServletResponse) response;
            chain.doFilter(request, wrapResponse(httpres, createTrimWriter(httpres)));
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
        //
    }

    // Utility (may be refactored to public utility class) ----------------------------------------
    /**
     * Create a new PrintWriter for the given HttpServletResponse which trims
     * all whitespace.
     *
     * @param response The involved HttpServletResponse.
     * @return A PrintWriter which trims all whitespace.
     * @throws IOException If something fails at I/O level.
     */
    private static PrintWriter createTrimWriter(final HttpServletResponse response)
            throws IOException {
        return new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"), true) {
            private StringBuilder builder = new StringBuilder();
            private boolean trim = false;

            @Override
            public void write(int c) {
                builder.append((char) c); // It is actually a char, not an int.
            }

            @Override
            public void write(char[] chars, int offset, int length) {
                builder.append(chars, offset, length);
                this.flush(); // Preflush it.
            }

            @Override
            public void write(String string, int offset, int length) {
                builder.append(string, offset, length);
                this.flush(); // Preflush it.
            }

            // Finally override the flush method so that it trims whitespace.
            @Override
            public void flush() {
                synchronized (builder) {
                    BufferedReader reader = new BufferedReader(new StringReader(builder.toString()));
                    String line = null;

                    try {
                        boolean firstLine = true;

                        while ((line = reader.readLine()) != null) {

                            if (firstLine && line.trim().startsWith("class")) {
                                out.write(" ");
                                firstLine = false;
                            }

                            if (startTrim(line)) {
                                trim = true;
                                out.write(line);
                            } else if (trim) {

                                if (line.endsWith(" ")) {
                                    out.write(line.trim() + " ");
                                } else if (line.startsWith(" ")) {
                                    out.write(" " + line.trim());
                                } else {
                                    out.write(line.trim());
                                }

                                if (stopTrim(line)) {
                                    trim = false;
                                }

                            } else {
                                out.write(line);
                            }
                        }
                    } catch (IOException ex) {
                        setError();
                        LOGGER.log(Level.SEVERE, "Page failed to render", ex);
                        // Log e or do e.printStackTrace() if necessary.
                    }

                    // Reset the local StringBuilder and issue real flush.
                    builder = new StringBuilder();
                    super.flush();
                }
            }

            private boolean startTrim(String line) {
                for (String match : START_TRIM_AFTER) {
                    if (line.contains(match)) {
                        return true;
                    }
                }
                return false;
            }

            private boolean stopTrim(String line) {
                for (String match : STOP_TRIM_AFTER) {
                    if (line.contains(match)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    /**
     * Wrap the given HttpServletResponse with the given PrintWriter.
     *
     * @param response The HttpServletResponse of which the given PrintWriter
     * have to be wrapped in.
     * @param writer The PrintWriter to be wrapped in the given
     * HttpServletResponse.
     * @return The HttpServletResponse with the PrintWriter wrapped in.
     */
    private static HttpServletResponse wrapResponse(
            final HttpServletResponse response, final PrintWriter writer) {
        return new HttpServletResponseWrapper(response) {
            @Override
            public PrintWriter getWriter() throws IOException {
                return writer;
            }
        };
    }

}

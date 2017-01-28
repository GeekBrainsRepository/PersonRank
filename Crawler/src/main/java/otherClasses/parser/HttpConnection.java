package otherclasses.parser;


import otherclasses.parser.model.InvalidSitemapUrlException;
import otherclasses.parser.model.UrlConnectionException;

import javax.net.ssl.*;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPInputStream;

class HttpConnection implements Closeable {
    private static final String USER_AGENT = "User-Agent";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_ENCODING = "Content-Encoding";
    private final URL url;
    private String userAgent;
    private int timeout;
    private boolean ignoreTlsCertificates;
    private HttpURLConnection httpURLConnection;
    private InputStream inputStream;

    private static final class HostnameVerifierHolder {
        private static final HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    private static final class SslSocketFactoryHolder {
        private static final SSLSocketFactory TRUST_ALL_SSL_SOCKET_FACTORY;

        static {
            TrustManager[] trustManagers = new TrustManager[] {new X509TrustManager() {
                public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
                }

                public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustManagers, new java.security.SecureRandom());
                TRUST_ALL_SSL_SOCKET_FACTORY = sslContext.getSocketFactory();
            } catch (NoSuchAlgorithmException e) {
                throw new UrlConnectionException("Error getting SSL context instance to allow insecure SSL connections.", e);
            } catch (KeyManagementException e) {
                throw new UrlConnectionException("Error initializing SSL context to allow insecure SSL connections.", e);
            }
        }
    }

    public HttpConnection(URL url) {
        this(url, null, 15000, true);
    }

    public HttpConnection(URL url, String userAgent, int timeout, boolean ignoreTlsCertificates) {
        validateProtocol(url);
        this.url = url;
        this.userAgent = userAgent;
        this.timeout = timeout;
        this.ignoreTlsCertificates = ignoreTlsCertificates;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void ignoreTlsCertificates(boolean ignore) {
        this.ignoreTlsCertificates = ignore;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public int getTimeout() {
        return timeout;
    }

    public boolean isIgnoreTlsCertificates() {
        return ignoreTlsCertificates;
    }

    public synchronized InputStream getInputStream() {
        if (inputStream != null) {
            return inputStream;
        }
        initializeConnection();
        String contentType = httpURLConnection.getHeaderField(CONTENT_TYPE);
        String contentEncoding = httpURLConnection.getHeaderField(CONTENT_ENCODING);
        try {
            inputStream = httpURLConnection.getInputStream();
            if ((contentEncoding != null && (contentEncoding.contains("gzip") || contentEncoding.contains("compress")))
                    || (contentType != null && (contentType.contains("gzip") || contentType.contains("compress")))) {
                inputStream = new GZIPInputStream(inputStream);
            }
            return inputStream;
        } catch (IOException e) {
            throw new UrlConnectionException(e.getMessage());
        }
    }

    public int getResponseCode() {
        initializeConnection();
        try {
            return httpURLConnection.getResponseCode();
        } catch (IOException e) {
            throw new UrlConnectionException(e.getMessage());
        }
    }

    @Override
    public void close() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {

            }
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    public static URL newUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new UrlConnectionException(e.getMessage());
        }
    }

    public static URL getRobotsTxtUrl(URL url) {
        try {
            return new URL(url.getProtocol(), url.getHost(), url.getPort(), "/robots.txt");
        } catch (MalformedURLException e) {
            throw new UrlConnectionException(e.getMessage());
        }
    }

    private void validateProtocol(URL url) {
        if (!"http".equals(url.getProtocol()) && !"https".equals(url.getProtocol())) {
            throw new InvalidSitemapUrlException("Only protocols HTTP and HTTPS are supported.");
        }
    }

    private synchronized void initializeConnection() {
        if (httpURLConnection == null) {
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                throw new UrlConnectionException(e.getMessage());
            }
            httpURLConnection.setReadTimeout(timeout);
            httpURLConnection.setConnectTimeout(timeout);
            httpURLConnection.setUseCaches(false);
            if (userAgent != null) {
                httpURLConnection.setRequestProperty(USER_AGENT, userAgent);
            }
            if (ignoreTlsCertificates && httpURLConnection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsUrlConnection = (HttpsURLConnection) httpURLConnection;
                httpsUrlConnection.setHostnameVerifier(HostnameVerifierHolder.ALLOW_ALL_HOSTNAME_VERIFIER);
                httpsUrlConnection.setSSLSocketFactory(SslSocketFactoryHolder.TRUST_ALL_SSL_SOCKET_FACTORY);
            }
        }
    }
}

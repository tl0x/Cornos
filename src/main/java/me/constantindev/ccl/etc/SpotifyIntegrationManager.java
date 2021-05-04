package me.constantindev.ccl.etc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import me.constantindev.ccl.etc.config.MConfToggleable;
import me.constantindev.ccl.etc.helper.Rnd;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.gui.HudElements;
import me.constantindev.ccl.module.ext.SpotifyConfig;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

public class SpotifyIntegrationManager {
    private static boolean loggingIn = false;
    private static String accessToken = "";
    private static boolean initialized = false;
    private static Thread updater;

    public static void init() {
        if (initialized) return;
        initialized = true;
        String accessTokenCache = SpotifyConfig.token.value.substring(1);
        if (!accessTokenCache.isEmpty()) {
            accessToken = accessTokenCache;
        }
    }

    private static void login() {
        if (loggingIn) return;
        loggingIn = true;
        HttpServer srv;
        try {
            srv = HttpServer.create(new InetSocketAddress(8888), 0);
        } catch (Exception ignored) {
            loggingIn = false;
            return;
        }
        HttpContext ctx = srv.createContext("/");
        ctx.setHandler(exchange -> {
            URI u = exchange.getRequestURI();
            List<NameValuePair> nvpQ = URLEncodedUtils.parse(u, "UTF-8");
            String at = "";
            for (NameValuePair nameValuePair : nvpQ) {
                if (nameValuePair.getName().equalsIgnoreCase("access_token")) {
                    at = nameValuePair.getValue();
                    break;
                }
            }
            String resp = "Authorized! Your access token: " + at;
            if (at.isEmpty()) {
                resp = "<script>function getHashParams() {\n" +
                        "var hashParams = {};\n" +
                        "          var e, r = /([^&;=]+)=?([^&;]*)/g,\n" +
                        "              q = window.location.hash.substring(1);\n" +
                        "          while ( e = r.exec(q)) {\n" +
                        "             hashParams[e[1]] = decodeURIComponent(e[2]);\n" +
                        "          }\n" +
                        "          return hashParams;\n" +
                        "        };e=getHashParams();window.location.href=\"http://localhost:8888/?access_token=\"+e.access_token</script>";
            } else {
                accessToken = at;
                SpotifyConfig.token.setValue("0" + accessToken);
            }
            exchange.sendResponseHeaders(200, resp.getBytes().length);
            OutputStream writer = exchange.getResponseBody();
            writer.write(resp.getBytes());
            writer.close();
            if (!at.isEmpty()) {
                srv.stop(5);
                loggingIn = false;
            }
        });
        srv.start();
        String state = Rnd.rndAscii(16);
        try {
            String authU = "https://accounts.spotify.com/authorize" +
                    "?response_type=token" +
                    "&client_id=" + URLEncoder.encode("a37547f913324435a6f0d3b42aed9e39", "UTF-8") +
                    "&scope=" + URLEncoder.encode("user-read-playback-state", "UTF-8") +
                    "&redirect_uri=" + URLEncoder.encode("http://localhost:8888", "UTF-8") +
                    "&state=" + URLEncoder.encode(state, "UTF-8");
            Util.getOperatingSystem().open(authU);
        } catch (Exception ignored) {
            srv.stop(5);
            loggingIn = false;
        }
    }

    private static String getPlayingInternal() {
        if (!initialized) return null;
        String ret;
        try {
            HttpGet get = new HttpGet("https://api.spotify.com/v1/me/player");
            get.addHeader("Authorization", "Bearer " + accessToken);
            HttpClient c = HttpClients.createDefault();
            HttpResponse hrs = c.execute(get);
            InputStream res = hrs.getEntity().getContent();
            ret = IOUtils.toString(res);
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }

    public static String getPlaying() throws IOException {
        String r = getPlayingInternal();
        if (r == null || r.isEmpty()) {
            return "Nothing";
        }
        Gson gson = new Gson();
        JsonObject jobj = gson.fromJson(r, JsonObject.class);
        if (jobj.has("error")) {
            SpotifyConfig.token.setValue("0");
            login();
            return "Unknown";
        }
        JsonObject item = jobj.getAsJsonObject("item");
        String name = item.get("name").getAsString();
        String artist = item.getAsJsonArray("artists").get(0).getAsJsonObject().get("name").getAsString();
        return artist + " - " + name;
    }

    public static void update() {
        if (updater == null) {
            updater = new Thread(() -> {
                while (true) {
                    try {
                        STL.sleep(7000);
                        if (SpotifyConfig.token.value.substring(1).isEmpty()) {
                            HudElements.spotPlaying = "Unknown";
                            login();
                            continue;
                        }
                        if (!((MConfToggleable) ModuleRegistry.search("hud").mconf.getByName("spotify")).isEnabled())
                            continue;
                        HudElements.spotPlaying = getPlaying();
                    } catch (IOException e) {
                        SpotifyConfig.token.setValue("0");
                        e.printStackTrace();
                    }

                }
            });
            updater.start();
        }
    }
}

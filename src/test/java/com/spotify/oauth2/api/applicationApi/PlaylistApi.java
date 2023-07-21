package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistApi {
    // static String accessToken = "BQAi3RmpZV5VXW4xJN8InboFLC1eyfJM20il9j-_MBV-E_epDdPXDpYFkNHW3TaUO-KnMKthZDki2MCuIWoIwmM3QWSVwETrGGagCZeK0pmTga0Sat0nPAcCNrAsyZwt5418X11KnUBaRp9reVVoX5sltqdinaHOEwkLatnulDChTBSL4mgvd2PPbc6WdiaYBnFJqCayY1sOZn9N1dYadp6AeVoSWWYfqAwEsfMmhfWo8TEnmbYn1fxJQp3-4zTqvaG9Os4FjotlWTdX";

    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, getToken(), requestPlaylist);
    }

    public static Response post(String token, Playlist requestPlaylist) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, token, requestPlaylist);
    }

    public static Response get(String playlistId) {
        return RestResource.get(PLAYLISTS + "/" + playlistId, getToken());
    }
    // playlistId: 1CGbPWNtwjM8o66TE0t3Nr

    public static Response put(String playlistId, Playlist requestPlaylist) {
        return RestResource.put(PLAYLISTS + "/" + playlistId, getToken(), requestPlaylist);
    }
    // playlistId 3DvjFWYcJy9yS7RrVJZjYk
}

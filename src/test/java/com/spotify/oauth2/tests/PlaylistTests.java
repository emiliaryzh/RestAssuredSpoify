package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.CodeDescription;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generatePlaylistDescription;
import static com.spotify.oauth2.utils.FakerUtils.generatePlaylistName;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Epic("Spotify API")
public class PlaylistTests extends BaseTest{

    public Playlist playlistBuilder(String name, String description, boolean public_type) {
        return Playlist.builder().
                name(name).
                description(description).
                _public(public_type).
                build();
    }

  /* public Playlist playlistBuilder(String name, String description, boolean public_type) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.set_public(public_type);
        return playlist;
    } */
// an example without the Builder pattern

    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist) {
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    public void assertStatusCode(int actualStatusCode, int expectedStatusCode) {
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    public void assertError(Error responseError, int expectedStatusCode, String expectedMessage) {
        assertThat(responseError.getError().getStatus(), equalTo(expectedStatusCode));
        assertThat(responseError.getError().getMessage(), equalTo(expectedMessage));
    }

    @Story("Create/View/Update a playlist")
    @Issue("233")
    @Test(description = "should be able to create a playlist")
    public void shouldBeAbleToCreateAPlaylist() {
        Playlist requestPlaylist = playlistBuilder(generatePlaylistName(), generatePlaylistDescription(), false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), CodeDescription.CODE_201.getCode());

        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Story("Create/View/Update a playlist")
    @Test
    public void shouldBeAbleToGetAPlaylist() {
        Playlist requestPlaylist = playlistBuilder("Playlist from Rest Assured POJO", "Playlist from Rest Assured POJO", false);

        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(), CodeDescription.CODE_200.getCode());

        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Story("Create/View/Update a playlist")
    @Test
    public void shouldBeAbleToUpdateAPlaylist() {
        Playlist requestPlaylist = playlistBuilder("Updated API Playlist POJO", "Updated playlist description POJO", false);

        Response response = PlaylistApi.put(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertStatusCode(response.statusCode(), CodeDescription.CODE_200.getCode());
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutAName() {
        Playlist requestPlaylist = playlistBuilder("", generatePlaylistDescription(), false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), CodeDescription.CODE_400.getCode());

        assertError(response.as(Error.class), CodeDescription.CODE_400.getCode(), CodeDescription.CODE_400.getMessage());
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithAnExpiredToken() {
        String invalid_token = "132314";

        Playlist requestPlaylist = playlistBuilder(generatePlaylistName(), generatePlaylistDescription(), false);

        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertStatusCode(response.statusCode(), CodeDescription.CODE_401.getCode());

        assertError(response.as(Error.class), CodeDescription.CODE_401.getCode(), CodeDescription.CODE_401.getMessage());
    }
}

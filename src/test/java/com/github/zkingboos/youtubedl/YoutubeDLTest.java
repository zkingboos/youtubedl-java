package com.github.zkingboos.youtubedl;

import com.github.zkingboos.youtubedl.exception.YoutubeDLException;
import com.github.zkingboos.youtubedl.entry.video.VideoFormat;
import com.github.zkingboos.youtubedl.entry.video.VideoInfo;
import com.github.zkingboos.youtubedl.entry.video.VideoThumbnail;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;

public class YoutubeDLTest {

    private final static String DIRECTORY = System.getProperty("java.io.tmpdir");
    private final static String VIDEO_URL = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    private final static String NONE_EXISTENT_VIDEO_URL = "https://www.youtube.com/watch?v=dQw4w9WgXcZ";

    /**@Test
    public void testUsingOwnExecutablePath() throws YoutubeDLException {
        YoutubeDL.setExecutablePath("/usr/bin/youtube-dl");
        Assert.assertNotNull(YoutubeDL.getVersion());
    }**/

    @Test
    public void testGetVersion() throws YoutubeDLException {
        Assert.assertNotNull(YoutubeDL.getVersion());
    }

    @Test
    public void testElapsedTime() throws YoutubeDLException {

        long startTime = System.nanoTime();

        YoutubeRequest request = new YoutubeRequest();
        request.setOption("version");
        YoutubeResponse response = YoutubeDL.execute(request);

        int elapsedTime = (int) (System.nanoTime() - startTime);

        Assert.assertTrue(elapsedTime > response.getElapsedTime());
    }


    @Test
    public void testSimulateDownload() throws YoutubeDLException {

        YoutubeRequest request = new YoutubeRequest();
        request.setUrl(VIDEO_URL);
        request.setOption("simulate");

        YoutubeResponse response = YoutubeDL.execute(request);

        Assert.assertEquals("youtube-dl " + VIDEO_URL + " --simulate", response.getCommand());
    }

    @Test
    public void testDirectory() throws YoutubeDLException {

        YoutubeRequest request = new YoutubeRequest(VIDEO_URL, DIRECTORY);
        request.setOption("simulate");

        YoutubeResponse response = YoutubeDL.execute(request);

        Assert.assertEquals(DIRECTORY, response.getDirectory());
    }

    @Test
    public void testGetVideoInfo() throws YoutubeDLException {
        VideoInfo videoInfo = YoutubeDL.getVideoInfo(VIDEO_URL);
        Assert.assertNotNull(videoInfo);
    }

    @Test
    public void testGetFormats() throws YoutubeDLException {
        List<VideoFormat> formats = YoutubeDL.getFormats(VIDEO_URL);
        Assert.assertNotNull(formats);
        Assert.assertTrue(formats.size() > 0);
    }

    @Test
    public void testGetThumbnails() throws YoutubeDLException {
        List<VideoThumbnail> thumbnails = YoutubeDL.getThumbnails(VIDEO_URL);
        Assert.assertNotNull(thumbnails);
        Assert.assertTrue(thumbnails.size() > 0);
    }

    @Test
    public void testGetTags() throws YoutubeDLException {
        List<String> tags = YoutubeDL.getTags(VIDEO_URL);
        Assert.assertNotNull(tags);
        Assert.assertTrue(tags.size() > 0);
    }

    @Test
    public void testGetCategories() throws YoutubeDLException {
        List<String> categories = YoutubeDL.getCategories(VIDEO_URL);
        Assert.assertNotNull(categories);
        Assert.assertTrue(categories.size() > 0);
    }

    @Test(expected = YoutubeDLException.class)
    public void testFailGetNonExistentVideoInfo() throws YoutubeDLException {
        YoutubeDL.getVideoInfo(NONE_EXISTENT_VIDEO_URL);
    }
}
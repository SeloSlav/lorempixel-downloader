import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Created by mnxe on 8/9/2017.
 */
public class DownloadRandomImages {

    public static void main(String[] args) {

        //noinspection MismatchedQueryAndUpdateOfCollection
        GetPropertyValues properties = new GetPropertyValues();
        try {
            List<String> resultList = properties.getPropValues();

            // The path where your images will be saved
            String DOWNLOAD_PATH = resultList.get(0);

            // Desired dimensions for batch of downloaded images
            final String WIDTH = resultList.get(1);
            final String HEIGHT = resultList.get(2);

            // The number of pictures that will be downloaded
            final int NUM_IMAGES = Integer.parseInt(resultList.get(3));

            // What kind of images do you want to download?
            final String CATEGORY = resultList.get(4);

            downloadImages(DOWNLOAD_PATH, WIDTH, HEIGHT, NUM_IMAGES, CATEGORY); // Run the script

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static URL concatenate(URL baseUrl, String extraPath) throws URISyntaxException, MalformedURLException {
        URI uri = baseUrl.toURI();
        String newPath = uri.getPath() + '/' + extraPath;
        URI newUri = uri.resolve(newPath);
        return newUri.toURL();
    }

    private static void downloadImages(String download_path, String width, String height, int num_images, String category) {


        for (int i = 0; i < num_images; i++) {

            CallURL callURL = new CallURL(width, height).invoke();
            URL url = null;
            if (category != null) {
                try {
                    url = callURL.getUrl();
                    url = concatenate(url, category);
                } catch (URISyntaxException | MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                url = callURL.getUrl();
            }
            InputStream in = callURL.getIn();

            in = getInputStream(url, in);

            ByteArrayOutputStream out = getByteArrayOutputStream(in);
            closeByteArrayOutputStream(out);
            closeInputStream(in);

            byte[] response = out.toByteArray();

            // Creates a file for the downloaded image
            FileOutputStream fos = createFile(download_path);
            writeToFileOutputStream(response, fos);
            closeFileOutputStream(fos);

        }
    }

    private static void closeFileOutputStream(FileOutputStream fos) {
        try {
            if (fos != null) {
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFileOutputStream(byte[] response, FileOutputStream fos) {
        try {
            if (fos != null) {
                fos.write(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeInputStream(InputStream in) {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeByteArrayOutputStream(ByteArrayOutputStream out) {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ByteArrayOutputStream getByteArrayOutputStream(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n;
        try {
            if (in != null) {
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    private static InputStream getInputStream(URL url, InputStream in) {
        try {
            if (url != null) {
                in = new BufferedInputStream(url.openStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    private static FileOutputStream createFile(String DOWNLOAD_PATH) {
        FileOutputStream fos = null;
        try {
            UUID uuid = UUID.randomUUID();
            File f = new File(DOWNLOAD_PATH + uuid + ".jpg");
            //noinspection ResultOfMethodCallIgnored
            f.getParentFile().mkdirs();
            //noinspection ResultOfMethodCallIgnored
            f.createNewFile();
            fos = new FileOutputStream(f, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fos;
    }


    private static class CallURL {
        private String width;
        private String height;
        private URL url;
        private InputStream in;

        CallURL(String WIDTH, String HEIGHT) {
            width = WIDTH;
            height = HEIGHT;
        }

        URL getUrl() {
            return url;
        }

        InputStream getIn() {
            return in;
        }

        CallURL invoke() {
            url = null;
            try {
                url = new URL("http://lorempixel.com/" + width + "/" + height + "/");
                System.out.println("Downloading..." + url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            in = null;
            return this;
        }
    }
}

package forana.tilepreview;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageWatcher {
    private volatile boolean needReload;
    private String imagePath;
    private BufferedImage image;
    
    private List<ImageWatcherSubscribable> subscribers;
    
    public ImageWatcher(String imagePath) {
        needReload = true;
        this.imagePath = imagePath;
        image = null;
        subscribers = new LinkedList<>();
        
        startMonitor();
    }
    
    public void subscribe(ImageWatcherSubscribable s) {
        subscribers.add(s);
    }
    
    private void notifySubscribers() {
        for (ImageWatcherSubscribable s : subscribers) {
            s.imageUpdated();
        }
    }
    
    private void startMonitor() {
        new Thread(new Runnable() {
            public void run() {
                File file = new File(imagePath);
                long modified = file.lastModified();
                try {
                    while (true) {
                        Thread.sleep(5);
                        long last = file.lastModified();
                        if (modified != last) {
                            needReload = true;
                            notifySubscribers();
                            modified = last;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    
    public BufferedImage getImage() {
        if (needReload) {
            loadImage();
            needReload = false;
        }
        return image;
    }
    
    private void loadImage() {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Preview Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}

package forana.tilepreview;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PreviewWindow {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;
    
    private Integer width = null;
    private Integer height = null;
    private int xOffset = 0;
    private int yOffset = 0;
    private int zoom = 1;
    private final ImageWatcher watcher;
    
    public PreviewWindow(String imagePath) {
        watcher = new ImageWatcher(imagePath);
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setX(int x) {
        xOffset = x;
    }
    
    public void setY(int y) {
        yOffset = y;
    }
    
    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
    
    public void show() {
        new JFrame("Tile Preview") {
            private static final long serialVersionUID = 1L;
        {
            setLocationByPlatform(true);
            setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(true);
            setLayout(new BorderLayout());
            add(new PreviewPanel(), BorderLayout.CENTER);
            setVisible(true);
        }};
    }
    
    private class PreviewPanel extends JPanel implements ImageWatcherSubscribable {
        private static final long serialVersionUID = 1L;
        
        public PreviewPanel() {
            watcher.subscribe(this);
        }
        
        public void imageUpdated() {
            repaint();
        }
        
        @Override
        public void paint(Graphics gr) {
            Graphics2D g = (Graphics2D)gr;
            BufferedImage image = watcher.getImage();
            int aWidth = width == null ? image.getWidth() - xOffset : width;
            int aHeight = height == null ? image.getHeight() - yOffset : height;
            g.scale(zoom, zoom);
            for (int x = 0; x < getWidth() / zoom; x += aWidth) {
                for (int y = 0; y < getHeight() / zoom; y += aHeight) {
                    g.drawImage(image, x, y, x + aWidth, y + aHeight, xOffset, yOffset, xOffset + aWidth, yOffset + aHeight, this);
                }
            }
        }
    }
}

package ru.nsu.vbalashov2.icg.filter.components;

import ru.nsu.vbalashov2.icg.filter.FrameWork;
import ru.nsu.vbalashov2.icg.filter.tools.events.EventPublishSubjects;
import ru.nsu.vbalashov2.icg.filter.tools.filters.*;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.Serial;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Image-viewer created in some JScrollPane
 * @author Serge
 */
public class JImagePanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener
{
    @Serial
    private static final long serialVersionUID = 3L;

    private Dimension panelSize;		// visible image size
    private final JScrollPane spIm;
    private final FrameWork parentComponent;
    private BufferedImage currentImg = null;	// image to view
    private BufferedImage originalImg = null;   // original image
    private Dimension imSize = null;	// real image size
    private int lastX=0, lastY=0;		// last captured mouse coordinates
    private final double zoomK = 0.05;	// scroll zoom coefficient

    /**
     * Creates default Image-viewer in the given JScrollPane.
     * Visible space will be painted in black.
     * @param scrollPane - JScrollPane to add a new Image-viewer
     * @throws Exception - given JScrollPane must not be null
     */
    public JImagePanel(
            JScrollPane scrollPane,
            FrameWork parentComponent,
            EventPublishSubjects eventPublishSubjects
    ) throws Exception
    {
        if (scrollPane == null)
            throw new Exception("Отсутствует scroll panel для просмотрщика изображений!");

        spIm = scrollPane;
        spIm.setWheelScrollingEnabled(false);
        spIm.setDoubleBuffered(true);
        spIm.setViewportView(this);

        this.parentComponent = parentComponent;

        panelSize = getVisibleRectSize();	// adjust panel size to maximum visible in scrollPane
        spIm.validate();					// added panel to scrollPane
        // setMaxVisibleRectSize();

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        configureEvents(eventPublishSubjects);
        realSize();
    }

    private void configureEvents(EventPublishSubjects eventPublishSubjects) {
        eventPublishSubjects.getSaveFilePublishSubject().subscribe(file -> {
            try {
                ImageIO.write(currentImg, "PNG", file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        JImagePanel.this,
                        e.getMessage(),
                        "Error saving file",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        eventPublishSubjects.getNewBufferedImagePublishSubject().subscribe(newImg -> {
            originalImg = newImg;
            currentImg = originalImg;
            setImage(currentImg, false);
            realSize();
        });

        eventPublishSubjects.getFitToSizePublishSubject().subscribe(event -> fitScreen());

        eventPublishSubjects.getRealSizePublishSubject().subscribe(event -> realSize());

        BlackWhiteFilter blackWhiteFilter = new BlackWhiteFilter();
        NegativeFilter negativeFilter = new NegativeFilter();
        GaussianBlurFilter gaussianBlurFilter = new GaussianBlurFilter(
                eventPublishSubjects.getGaussianBlurNewSigmaPublishSubject(),
                eventPublishSubjects.getGaussianBlurNewRadiusPublishSubject()
        );
        IncreasingSharpnessFilter increasingSharpnessFilter = new IncreasingSharpnessFilter();
        EmbossingFilter embossingFilter = new EmbossingFilter();
        GammaCorrectionFilter gammaCorrectionFilter = new GammaCorrectionFilter(
                eventPublishSubjects.getGammaCorrectionNewGammaPublishSubject()
        );
        RobertsOperatorFilter robertsOperatorFilter = new RobertsOperatorFilter(
                eventPublishSubjects.getRobertsOperatorNewThresholdPublishSubject()
        );
        SobelOperatorFilter sobelOperatorFilter = new SobelOperatorFilter(
                eventPublishSubjects.getSobelOperatorNewThresholdPublishSubject()
        );
        FloydSteinbergDitherFilter floydSteinbergDitherFilter = new FloydSteinbergDitherFilter();

        eventPublishSubjects.getNewFilterTypePublishSubject().subscribe(filter -> {
            if (currentImg == null) {
                return;
            }
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            currentImg = bufferedImageDeepCopy(originalImg);
            switch (filter) {
                case BLACK_WHITE -> blackWhiteFilter.use(originalImg, currentImg);
                case NEGATIVE -> negativeFilter.use(originalImg, currentImg);
                case GAUSSIAN_BLUR -> gaussianBlurFilter.use(originalImg, currentImg);
                case INCREASING_SHARPNESS -> increasingSharpnessFilter.use(originalImg, currentImg);
                case EMBOSSING -> embossingFilter.use(originalImg, currentImg);
                case GAMMA_CORRECTION -> gammaCorrectionFilter.use(originalImg, currentImg);
                case ROBERTS_OPERATOR -> robertsOperatorFilter.use(originalImg, currentImg);
                case SOBEL_OPERATOR -> sobelOperatorFilter.use(originalImg, currentImg);
                case FLOYD_STEINBERG_DITHER -> floydSteinbergDitherFilter.use(originalImg, currentImg);
            }
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            revalidate();
            repaint();
            spIm.revalidate();
            spIm.repaint();
        });
    }

    private static BufferedImage bufferedImageDeepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    // public JScrollPane getScrollPane ()	{ return spIm; }

//    /**
//     * Creates new Image-viewer of the given image in the given JScrollPane
//     * @param scrollPane - JScrollPane to add a new Image-viewer
//     * @param newIm - image to view
//     * @throws Exception - given JScrollPane must nor be null
//     */
//    public JImagePanel(JScrollPane scrollPane, FrameWork parentComponent, BufferedImage newIm) throws Exception
//    {
//        this(scrollPane, parentComponent);
//        setImage(newIm, true);
//    }

    public void paint(Graphics g)
    {
        if (currentImg == null)
        {
            g.setColor(new Color(200, 200, 230));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        else
            g.drawImage(currentImg, 0, 0, panelSize.width, panelSize.height, null);
    }

//	public void update(Graphics g)
//	{
//		paint(g);
//	}

    /**
     * Sets a new image to view.
     * If the given image is null, visible space will be painted in black.
     * Default view causes to "fit-screen" view.
     * If defaultView is set to false, viewer will show the last viewed part of the previous image.
     * But only if both of the images have the same size.
     * @param newIm - new image to view
     * @param defaultView - view the image in the default view, or save the view on the image
     */
    public void setImage(BufferedImage newIm, boolean defaultView)
    {
        // defaultView means "fit screen (panel)"

        // Draw black screen for no image
        currentImg = newIm;
        if (currentImg == null)
        {
            // make full defaultView
            setMaxVisibleRectSize();	// panelSize = getVisibleRectSize();
            repaint();
            revalidate();	// spIm.validate();
            return;
        }

        // Check if it is possible to use defaultView
        Dimension newImSize = new Dimension(currentImg.getWidth(), currentImg.getHeight());
        if (imSize == null)
            defaultView = true;
        else if ( (newImSize.height != imSize.height) || (newImSize.width != imSize.width) )
            defaultView = true;

        imSize = newImSize;

        if (defaultView)
        {
            setMaxVisibleRectSize();	// panelSize = getVisibleRectSize();

            double kh = (double)imSize.height / panelSize.height;
            double kw = (double)imSize.width / panelSize.width;
            double k = Math.max(kh, kw);

            panelSize.width = (int)(imSize.width / k);
            panelSize.height = (int)(imSize.height / k);
            //this.setSize(panelSize);

            //repaint();
            spIm.getViewport().setViewPosition(new Point(0,0));
            //spIm.getHorizontalScrollBar().setValue(0);
            //spIm.getVerticalScrollBar().setValue(0);
            revalidate();	// spIm.validate();
            //spIm.repaint();	// wipe off the old picture in "spare" space
            spIm.paintAll(spIm.getGraphics());
        }
        else
        {
            // just change image
            //repaint();
            spIm.paintAll(spIm.getGraphics());
        }

    }

    /**
     * Sets "fit-screen" view.
     */
    public void fitScreen()	{ setImage(currentImg, true); }

    /**
     * Sets "real-size" view.
     */
    public void realSize()
    {
        if (imSize == null)
            return;

        double k = (double)imSize.width / panelSize.width;
        Point scroll = spIm.getViewport().getViewPosition();
        scroll.x *= (int) k;
        scroll.y *= (int) k;

        panelSize.setSize(imSize);

        //repaint();
        revalidate();	// spIm.validate();
        spIm.getHorizontalScrollBar().setValue(scroll.x);
        spIm.getVerticalScrollBar().setValue(scroll.y);
        //spIm.repaint();
        spIm.paintAll(spIm.getGraphics());
    }

    /**
     *
     * @return Dimension object with the current view-size
     */
    private Dimension getVisibleRectSize()
    {
        // maximum size for panel with or without scrolling (inner border of the ScrollPane)
        Dimension viewportSize = spIm.getViewport().getSize();
        if (viewportSize.height == 0)
            return new Dimension( spIm.getWidth()-3, spIm.getHeight()-3 );
        else
            return viewportSize;
    }

    /**
     * Sets panelSize to the maximum available view-size with hidden scroll bars.
     */
    private void setMaxVisibleRectSize()
    {
        // maximum size for panel without scrolling (inner border of the ScrollPane)
        panelSize = getVisibleRectSize();	// max size, but possibly with enabled scroll-bars
        revalidate();
        spIm.validate();
        panelSize = getVisibleRectSize();	// max size, without enabled scroll-bars
        revalidate();
    }

    public boolean setView(Rectangle rect)
    { return setView(rect, 10); }

    private boolean setView(Rectangle rect, int minSize)
    {
        // should also take into account ScrollBars size
        if (currentImg == null)
            return false;
        if (imSize.width<minSize || imSize.height<minSize)
            return false;

        if (minSize <= 0)
            minSize = 10;

        if (rect.width < minSize) 	rect.width=minSize;
        if (rect.height < minSize) 	rect.height=minSize;
        if (rect.x < 0) rect.x=0;
        if (rect.y < 0) rect.y=0;
        if (rect.x > imSize.width-minSize) 		rect.x=imSize.width-minSize;
        if (rect.y > imSize.height-minSize) 	rect.y=imSize.height-minSize;
        if ((rect.x+rect.width) > imSize.width) 	rect.width=imSize.width-rect.x;
        if ((rect.y+rect.height) > imSize.height) 	rect.height=imSize.height-rect.y;

        Dimension viewSize = getVisibleRectSize();
        double kw = (double)rect.width / viewSize.width;
        double kh = (double)rect.height / viewSize.height;
        double k = Math.max(kh, kw);

        int newPW = (int)(imSize.width / k);
        int newPH = (int)(imSize.height / k);
        // Check for size whether we can still zoom out
        if (newPW == (int)(newPW * (1-2*zoomK)) )
            return setView(rect, minSize*2);
        panelSize.width = newPW;
        panelSize.height = newPH;

        revalidate();
        spIm.validate();
        // сначала нужно, чтобы scroll понял новый размер, потом сдвигать

        int xc = rect.x+rect.width/2, yc = rect.y+rect.height/2;
        xc = (int)(xc/k); yc = (int)(yc/k);	// we need to center new view
        //int x0 = (int)(rect.x/k), y0 = (int)(rect.y/k);
        spIm.getViewport().setViewPosition(new Point(xc-viewSize.width/2, yc-viewSize.height/2));
        revalidate();	// spIm.validate();
        spIm.paintAll(spIm.getGraphics());

        return true;
    }

    @Override
    public Dimension getPreferredSize()
    {
        return panelSize;
    }

    /**
     * Change zoom when scrolling
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        if (currentImg == null)
            return;

        // Zoom
        double k = 1 - e.getWheelRotation()*zoomK;

        // Check for minimum size where we can still increase size
        int newPW = (int)(panelSize.width*k);
        if (newPW == (int)(newPW * (1+zoomK)) )
            return;
//		if (newW/imSize.width > 50)
//			return;
        if (k > 1)
        {
            int newPH = (int)(panelSize.height*k);
            Dimension viewSize = getVisibleRectSize();
            int pixSizeX = newPW / imSize.width;
            int pixSizeY = newPH / imSize.height;
            if (pixSizeX>0 && pixSizeY>0)
            {
                int pixNumX = viewSize.width / pixSizeX;
                int pixNumY = viewSize.height / pixSizeY;
                if (pixNumX<2 || pixNumY<2)
                    return;
            }
        }

        panelSize.width = newPW;
        // panelSize.height *= k;
        panelSize.height = (int) ((long)panelSize.width * imSize.height / imSize.width);	// not to lose ratio

        // Move so that mouse position doesn't visibly change
        int x = (int) (e.getX() * k);
        int y = (int) (e.getY() * k);
        Point scroll = spIm.getViewport().getViewPosition();
        scroll.x -= e.getX();
        scroll.y -= e.getY();
        scroll.x += x;
        scroll.y += y;

        repaint();	// можно и убрать
        revalidate();
        spIm.validate();
        // сначала нужно, чтобы scroll понял новый размер, потом сдвигать

        //spIm.getViewport().setViewPosition(scroll);	// так верхний левый угол может выйти за рамки изображения
        spIm.getHorizontalScrollBar().setValue(scroll.x);
        spIm.getVerticalScrollBar().setValue(scroll.y);
        spIm.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        lastX = e.getX();
        lastY = e.getY();
    }

    /**
     * Move visible image part when dragging
     */
    @Override
    public void mouseDragged(MouseEvent e)
    {
        // Move image with mouse

        if (e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK)		// ( (e.getModifiers() & MouseEvent.BUTTON3_MASK) == 0)
            return;

        // move picture using scroll
        Point scroll = spIm.getViewport().getViewPosition();
        scroll.x += ( lastX - e.getX() );
        scroll.y += ( lastY - e.getY() );

        //spIm.getViewport().setViewPosition(scroll);
        spIm.getHorizontalScrollBar().setValue(scroll.x);
        spIm.getVerticalScrollBar().setValue(scroll.y);
        spIm.repaint();

        // We changed the position of the underlying picture, take it into account
        //lastX = e.getX() + (lastX - e.getX());	// lastX = lastX
        //lastY = e.getY() + (lastY - e.getY());	// lastY = lastY
    }

    /**
     * When a rectangle is selected with pressed right button,
     * we zoom image to that rectangle
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (e.getModifiersEx() != InputEvent.BUTTON3_DOWN_MASK)
            return;

        int x1 = e.getX();
        int y1 = e.getY();
        // Исключаем клик
        if (Math.abs(x1-lastX)<5 && Math.abs(y1-lastY)<5)
            return;

        double k = (double)imSize.width / panelSize.width;

        int x0 = (int)(k*lastX);
        int y0 = (int)(k*lastY);
        x1 = (int)(k*x1);
        y1 = (int)(k*y1);

        int w = Math.abs(x1-x0);
        int h = Math.abs(y1-y0);
        if (x1 < x0) x0=x1;
        if (y1 < y0) y0=y1;

        Rectangle rect = new Rectangle(x0, y0, w, h);
        setView(rect);
    }


    /**
     * Process image click and call parent's methods
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        if ((e.getModifiersEx() == InputEvent.BUTTON2_DOWN_MASK) ||
                (e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK))
            parentComponent.changeViewedImage();

        if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK)
        {
            if (imSize == null)
            {
                // Клик по пустому изображению
                parentComponent.clickImage( e.getX(), e.getY() );
                return;
            }

            double k = (double)imSize.width / panelSize.width;
            int x = (int)(k*e.getX());
            int y = (int)(k*e.getY());
            if ((x < imSize.width) && (y < imSize.height))
                parentComponent.clickImage( x, y );
        }
    }

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mouseMoved(MouseEvent e){}
}

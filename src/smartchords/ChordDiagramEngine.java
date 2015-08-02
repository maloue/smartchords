package smartchords;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by wilson on 5/9/15.
 */
public class ChordDiagramEngine {

    final static int WIDTH = 360;
    final static int HEIGHT = 400;

    final static int RADIUS = 16;

    final static int STR_SPACE = 48;
    final static int FRET_SPACE = 48;

    final static int white = rgb(255,255,255);
    final static int black = rgb(0,0,0);

    BufferedImage image;
    Graphics2D graphics;
    public ChordDiagramEngine()
    {
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        for(int x = 0;x < WIDTH;x++)
            for(int y = 0;y < HEIGHT;y++)
                image.setRGB(x,y,white);

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        graphics = image.createGraphics();
        graphics.setRenderingHints(rh);
        graphics.setColor(Color.BLACK);
    }

    public void drawChord(int [] chord)
    {
        if(chord.length != 6)
            System.out.println("Chord must have 6 strings accounted for!");
        else
        {
            drawFretboard();
            writeToFile("fretboard.png");
            for(int str = 1;str <= 6;str++)
            {
                int curr = chord[str-1];
                if(curr > 0)
                    drawFinger(curr,str);
                else if (curr == 0)
                    drawOpenString(str);
                else
                    drawClosedString(str);
            }
            writeToFile("image.png");
        }
    }

    public void writeToFile(String filename)
    {
        graphics.drawImage(image,0,0,null);

        File f = new File(filename);
        try
        {
            ImageIO.write(image,"PNG",f);
        }
        catch (IOException i)
        {
            System.out.println("Failed due to IO Exception: "+i.getMessage());
        }
    }

    private void drawFinger(int fret,int string)
    {
        //Radius is 16
        int x = 60+(string-1)*48-RADIUS+1;
        int y = 68+FRET_SPACE+1+(fret-1)*48-24-RADIUS;
        System.out.println(x+", "+y);
        graphics.setColor(Color.BLACK);
        graphics.fillOval(x, y, RADIUS * 2, RADIUS * 2);
    }

    private void drawFretboard()
    {
        //Headstock
        graphics.fillRect(60,60-8,300-60+6,17);

        //Strings
        for(int x = 60;x <= 300;x += STR_SPACE)
            graphics.fillRect(x,68,6,288+2);

        //Frets
        for(int y = 68+FRET_SPACE;y <= 356;y += FRET_SPACE)
        {
            graphics.fillRect(60,y,300-60,6);
        }
    }

    public void drawOffset(int offset)
    {
        Font font = new Font("Serif", Font.PLAIN, 26);
        graphics.setFont(font);

        graphics.drawString(offset+"", 28, 100);

    }


    public void drawHLine(int x1,int x2,int y)
    {
        for(int x = x1;x <= x2;x++)
            image.setRGB(x,y,black);
    }

    public void drawVLine(int y1,int y2,int x)
    {
        for(int y = y1;y <= y2;y++)
            image.setRGB(x,y,black);
    }

    public void drawOpenString(int str)
    {
        //Radius is 16
        int x = 60+(str-1)*48-RADIUS+1;
        graphics.drawOval(x, 16, RADIUS * 2, RADIUS * 2);
    }

    public void drawClosedString(int str)
    {
        int x = 60+(str-1)*48-RADIUS+1;
        int y = 16;

        graphics.drawLine(x,y,x+RADIUS*2,y+RADIUS*2);
        graphics.drawLine(x+RADIUS*2,y,x,y+RADIUS*2);
    }

    public static int rgb(int r,int g,int b)
    {
        return (r << 16) | (g << 8) | b;
    }
}

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import java.awt.FontFormatException;
import java.io.IOException;

/**
 * Miscellaneous utilities related to running a game of Jeopardy.
 */
public class GameUtils {
  /**
   * Constructor for the GameUtils class. GameUtils should never be
   * instantiated, so this method is private.
   */
  private GameUtils() {}

  /**
   * The default font for game text should be 16pt plain sans
   */
  public static final Font GAME_FONT = new Font("SansSerif", Font.PLAIN, 16);

  /**
   * The font the text of the questions is displayed in.
   */
  public static final Font QUESTION_FONT = loadFont("Korinna Bold.ttf", 35);

  /**
   * The font the topic headers are displayed in.
   */
  public static final Font TOPIC_FONT = loadFont("Swiss911 XCm BT.ttf", 32);

  /**
   * The default font for game titles should be 20pt bold sans
   */
  public static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 20);

  /**
   * The icon that's displayed by a questions that has already been asked
   */
  public static final ImageIcon disabled = new ImageIcon(findImage("200.png"));

  /**
   * The background of the question panel
   */
  public static final ImageIcon questionBackground = new ImageIcon(findImage("background.png"));

  /**
   * Change the size of an icon.
   *
   * @param srcImg the original icon
   * @param w the new width
   * @param h the new height
   * @return the resized icon
   */
  public static ImageIcon resize(ImageIcon srcImg, int w, int h){
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImg.createGraphics();

    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg.getImage(), 0, 0, w, h, null);
    g2.dispose();

    return new ImageIcon(resizedImg);
  }

  /**
   * Find any file in the data folder, choosing from one of the
   * possible locations of the folder.
   *
   * @param name the name of the file to find in the data folder. To
   * find {@code data/example.txt}, pass {@code example.txt}.
   * @return the full location of the file or {@code null} if it wasn't found
   */
  public static String findFile(String name) {
    String[] prefixes = { // Possible locations of the data folder
      "data" + File.separator,
      "src" + File.separator + "data" + File.separator
    };

    return GameUtils.chooseFile(prefixes, name); // Return the first location that exists
  }

  /**
   * Find an image in the data folder. Images should be located in
   * {@code data/images}.
   *
   * @param name the name of the image to locate. To find the image
   * {@code data/images/example.png} pass {@code example.png}
   * @return the full path to the image or {@code null} if it wasn't found
   * @see #findFile(String)
   */
  public static String findImage(String name) {
    return GameUtils.findFile("images" + File.separator + name);
  }

  public static String findFont(String name) {
	    return GameUtils.findFile("fonts" + File.separator + name);
	  }

  /**
   * Find a file in one of several possible directories.
   *
   * This method checks if the file {@code prefix/name} exists, where
   * {@code name} is the name of the file and {@code prefix} is a
   * possible directory contianing the file. {@code name} stays the
   * same, while the method runs through many possible prefixes.
   *
   * @param prefixes possible directories containing the file. If the
   * individual strings in the array don't end in {@link
   * File#separator}, it is added.
   * @param name the name of the file within the possible directories
   * @return a location where the file exists or {@code null} if it
   * wasn't found
   */
  public static String chooseFile(String[] prefixes, String name) {
    for (String loc : prefixes) { // Go through each possible location of the file
      // If the location doesn't end with the folder separator, add it
      if (loc.charAt(loc.length()-1) != File.separator.charAt(0)) {
        loc += File.separator;
      }

      File f = new File(loc + name); // Create a file from the location
      if (f.exists()) { // If the file exists, we're done
        return loc + name;
      }
    }

    return null; // If the loop exited then the file wasn't found, so return null
  }

  /**
   * Load a font from a file in the {@code data} directory. Font files
   * should be TTFs and placed in {@code data/fonts}. If the font
   * isn't found or something goes wrong, the default font ({@link
   * #GAME_FONT}) resized to the correct size is returned.
   *
   * @param fName the name of the font file in the fonts directory
   * @param size the size of the font, in pt
   * @return the font from the file, or {@link #GAME_FONT} if
   * something went wrong. In both cases, the font is returned at the
   * correct size.
   */
  public static Font loadFont(String fName, float size) {
    try {
      return Font.createFont(Font.TRUETYPE_FONT, new File(findFile("fonts" + File.separator + fName))).deriveFont(size);
    } catch (FontFormatException | IOException e) {
      return GAME_FONT.deriveFont(size);
    }
  }
}

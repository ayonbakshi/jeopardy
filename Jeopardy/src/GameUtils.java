import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;

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
}

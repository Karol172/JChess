package JChess.gui;

import JChess.chessman.*;
import JChess.enums.Team;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class IconHelper {
    public BufferedImage selectIcon(Chessman chessman) {
        if (chessman != null)
            if (chessman.getClass() == Bishop.class && chessman.getTeam() == Team.WHITE)
                return downloadImage("white_bishop.png");
            else if (chessman.getClass() == King.class && chessman.getTeam() == Team.WHITE)
                return downloadImage("white_king.png");
            else if (chessman.getClass() == KNight.class && chessman.getTeam() == Team.WHITE)
                return downloadImage("white_knight.png");
            else if (chessman.getClass() == Pawn.class && chessman.getTeam() == Team.WHITE)
                return downloadImage("white_pawn.png");
            else if (chessman.getClass() == Queen.class && chessman.getTeam() == Team.WHITE)
                return downloadImage("white_queen.png");
            else if (chessman.getClass() == Rook.class && chessman.getTeam() == Team.WHITE)
                return downloadImage("white_rook.png");
            else if (chessman.getClass() == Bishop.class && chessman.getTeam() == Team.BLACK)
                return downloadImage("black_bishop.png");
            else if (chessman.getClass() == King.class && chessman.getTeam() == Team.BLACK)
                return downloadImage("black_king.png");
            else if (chessman.getClass() == KNight.class && chessman.getTeam() == Team.BLACK)
                return downloadImage("black_knight.png");
            else if (chessman.getClass() == Pawn.class && chessman.getTeam() == Team.BLACK)
                return downloadImage("black_pawn.png");
            else if (chessman.getClass() == Queen.class && chessman.getTeam() == Team.BLACK)
                return downloadImage("black_queen.png");
            else if (chessman.getClass() == Rook.class && chessman.getTeam() == Team.BLACK)
                return downloadImage("black_rook.png");
        return null;
    }

    public BufferedImage downloadImage (String source) {
        BufferedImage image = null;
        try {
            File file = new File(getClass().getClassLoader().getResource(source).toURI());
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Error! Read image fail.");
            e.printStackTrace();
        }
        catch (URISyntaxException e2) {
            System.err.println("Error! Read image fail.");
            e2.printStackTrace();
        }
        return image;
    }
}

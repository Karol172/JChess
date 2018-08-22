package JChess.gui;

import JChess.chessman.*;
import JChess.enums.Team;
import JChess.tool.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PawnPromotionPanel extends JPanel implements ActionListener {

    private JButton queenButton;
    private JButton rookButton;
    private JButton bishopButton;
    private JButton kNightButton;
    private Response response;
    private JDialog parent;

    public PawnPromotionPanel(JDialog parent, Team team, Response response) {
        this.response = response;
        this.parent = parent;
        setVisible(true);
        setSize(new Dimension(400,100));
        setLayout(new FlowLayout());
        IconHelper iconHelper = new IconHelper();

        this.queenButton = new JButton();
        this.rookButton = new JButton();
        this.bishopButton = new JButton();
        this.kNightButton = new JButton();

        this.queenButton.setIcon(new ImageIcon(iconHelper.selectIcon(new Queen(team, null))
                .getScaledInstance(75,75, Image.SCALE_DEFAULT)));
        this.rookButton.setIcon(new ImageIcon(iconHelper.selectIcon(new Rook(team, null))
                .getScaledInstance(75,75, Image.SCALE_DEFAULT)));
        this.bishopButton.setIcon(new ImageIcon(iconHelper.selectIcon(new Bishop(team, null))
                .getScaledInstance(75,75, Image.SCALE_DEFAULT)));
        this.kNightButton.setIcon(new ImageIcon(iconHelper.selectIcon(new KNight(team, null))
                .getScaledInstance(75,75, Image.SCALE_DEFAULT)));

        this.queenButton.addActionListener(this);
        this.rookButton.addActionListener(this);
        this.bishopButton.addActionListener(this);
        this.kNightButton.addActionListener(this);

        add(this.queenButton);
        add(this.rookButton);
        add(this.bishopButton);
        add(this.kNightButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == queenButton)
            response.setObj(Queen.class);
        else if (source == rookButton)
            response.setObj(Rook.class);
        else if (source == bishopButton)
            response.setObj(Bishop.class);
        else if (source == kNightButton)
            response.setObj(KNight.class);

        this.parent.dispose();
    }
}

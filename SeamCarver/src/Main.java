import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Main {
	public static void main(String args[]) throws IOException
    {
//		JButton open = new JButton();
//		JFileChooser fc = new JFileChooser();
//		fc.setCurrentDirectory(new java.io.File("C://Users//devdr//Desktop"));
//		fc.setDialogTitle("Yay");
//		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){ 
//			
//		}
//		System.out.println("file chosen: " + fc.getSelectedFile().getAbsolutePath());
		Scanner scan = new Scanner(System.in);
        JFrame frame2=new JFrame();
        String imgPath = null;
        
       

        JFileChooser chooser = new JFileChooser("C:\\Users\\devdr\\Desktop\\APCSSTUFF\\APCSFinal\\SeamCarver\\TestImgs\\");
       
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "PNG, JPG, & GIF Images", "jpg","png", "gif");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	           imgPath = chooser.getSelectedFile().getPath();
	    }
    
	    BufferedImage img2=ImageIO.read(new File(imgPath));
        ImageIcon icon2=new ImageIcon(img2);
        System.out.println("What how many pixels would you like to crop horizantally?(Out of " + img2.getWidth() + ")");
	    int width = scan.nextInt();
	    while(width >= img2.getWidth()) {
	    	System.out.println("That's too big! Try again...");
	    	width = scan.nextInt();
	    }
        frame2.setLayout(new FlowLayout());
        frame2.setSize(img2.getWidth(),img2.getHeight());
        JLabel lbl2=new JLabel();
        lbl2.setIcon(icon2);
        frame2.add(lbl2);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        
	    
        BufferedImage img=ImageIO.read(new File(imgPath));
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img.getWidth(),img.getHeight());
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // DisplayImage abc=new DisplayImage();
        SeamCarver carver = new SeamCarver(img, width);
        while(carver.getReady()) {
        	
        }
        icon = new ImageIcon(carver.getTest());
        lbl.setIcon(icon);
    }
	
}

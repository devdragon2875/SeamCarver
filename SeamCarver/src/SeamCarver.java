import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

//public class SeamCarver {
//   public SeamCarver(Picture picture)                // create a seam carver object based on the given picture
//   public Picture picture()                          // current picture - DONE
//   public     int width()                            // width of current picture - DONE
//   public     int height()                           // height of current picture - DONE
//   public  double energy(int x, int y)               // energy of pixel at column x and row y
//   public   int[] findHorizontalSeam()               // sequence of indices for horizontal seam
//   public   int[] findVerticalSeam()                 // sequence of indices for vertical seam
//   public    void removeHorizontalSeam(int[] seam)   // remove horizontal seam from current picture
//   public    void removeVerticalSeam(int[] seam)     // remove vertical seam from current picture
//
//   public static void main(String[] args)            //  tests this class by directly calling all instance methods
//}
//grid = new int[3][img.getWidth()][img.getHeight()];
//byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
//System.out.println(pixels.length);
//test = new BufferedImage(img.getWidth(), img.getHeight(), img.TYPE_3BYTE_BGR);
//double z;
/*for(int i = 0; i < img.getWidth(); i++) {
	for(int j = 0; j < img.getHeight(); j++) {
		z = energy(i, j);
		test.setRGB(i, j, (int)z);
	}
}*/

public class SeamCarver{
	private BufferedImage img;
	private BufferedImage test;
	public boolean ready;
	public SeamCarver(BufferedImage img, int cropWidth) {
		this.img = img;
		ready = true;
		this.carv(img.getWidth()-cropWidth, img.getHeight());
		
		
		
		
	}
	public void carv(int width, int height) {
		for(int i = 0; i < img.getWidth() - width; i++) {
			int[] arr = this.findVerticalSeam();
			this.removeSeam(arr);
		}
		System.out.println("Finished Processing");
		test = img;
		ready = false;
		
	}
	
	private int[] checkWrap(int x, int y, String dir) {
		if(dir.equals("up")) {
			//System.out.println("hitup");
			if(y-1 < 0) {
				y = img.getHeight()-1;
				
			}
		} else if(dir.equals("down")) {
			//System.out.println("hitdown");
			if(y+1 >= img.getHeight()) {
				y=0;
				
			}
		} else if(dir.equals("left")) {
			//System.out.println("hitleft");
			if(x-1 < 0) {
				x = img.getWidth()-1;
				
			}
		} else {
			//System.out.println("x+1: " + (x+1) + " width: " + img.getWidth());
			//System.out.println("hitlast");
			if(x+1 >= img.getWidth()) {
				x=0;
				
				
				
			}
		}
		//System.out.println("x: " + x);
		//System.out.println("y: " + y);
		int[] coords = {x, y};
		return coords;
	}
	
	private double energy(int x, int y) {
		
		//Check Args
		//if(x < 0 || x > img.getWidth()) throw new IllegalArgumentException("Pixel outside of x boundary");
		//if(y < 0 || y > img.getHeight()) throw new IllegalArgumentException("Pixel outside of y boundary");
		
		Color topPixel;
		Color bottomPixel;
		Color leftPixel;
		Color rightPixel;
		//Get Neighboring Pixel 
		//System.out.println("repeat");
		int[] coords = checkWrap(x, y-1, "up");
		coords = checkWrap(coords[0], coords[1], "left");
		coords = checkWrap(coords[0], coords[1], "right");
		coords = checkWrap(coords[0], coords[1], "down");
		//System.out.println("u: " +coords[0] + " " + coords[1]);
		topPixel = new Color(img.getRGB(coords[0],coords[1]));
		coords = checkWrap(x, y+1, "down");
		coords = checkWrap(coords[0], coords[1], "left");
		coords = checkWrap(coords[0], coords[1], "right");
		coords = checkWrap(coords[0], coords[1], "up");
		//System.out.println("d: " +coords[0] + " " + coords[1]);
		bottomPixel = new Color(img.getRGB(coords[0],coords[1]));
		coords = checkWrap(x-1, y, "left");
		coords = checkWrap(coords[0], coords[1], "up");
		coords = checkWrap(coords[0], coords[1], "down");
		coords = checkWrap(coords[0], coords[1], "right");
		//System.out.println("l: " +coords[0] + " " + coords[1]);
		leftPixel = new Color(img.getRGB(coords[0],coords[1]));
		coords = checkWrap(x+1, y, "right");
		coords = checkWrap(coords[0], coords[1], "up");
		coords = checkWrap(coords[0], coords[1], "down");
		coords = checkWrap(coords[0], coords[1], "left");
		//System.out.println("r: " +coords[0] + " " + coords[1]);
		rightPixel = new Color(img.getRGB(coords[0],coords[1]));
		//System.out.println(x+ " " + y + " " + coords[0] + " " + coords[1] + " " + img.getWidth() + " " + img.getHeight());


	
		
		
		//Calculate energy in x
		double redX = (Math.pow(leftPixel.getRed() - rightPixel.getRed(), 2));
		double blueX = (double)(Math.pow(leftPixel.getBlue() - rightPixel.getBlue(), 2));
		double greenX = (double)(Math.pow(leftPixel.getGreen() - rightPixel.getGreen(), 2));
		double deltaX = redX + blueX + greenX;
		
		//Calculate energy in y
		double redY = (double)(Math.pow(topPixel.getRed() - bottomPixel.getRed(), 2));
		double blueY = (double)(Math.pow(topPixel.getBlue() - bottomPixel.getBlue(), 2));
		double greenY = (double)(Math.pow(topPixel.getGreen() - bottomPixel.getGreen(), 2));
		double deltaY = redY + blueY + greenY;
		
		//Calculate the energy
		double energy = Math.sqrt(deltaX+deltaY);
		return energy;
		
	}
	
	private int[] findVerticalSeam() {
		//Finding minEnergy on top row
		int[] seam = new int[img.getHeight()]; 
		double minEnergy = Integer.MAX_VALUE;
		int x = 0;
		
		for(int i = 0; i < img.getWidth(); i++) {
			double z = energy(i, 0);
			
			if(z < minEnergy) {
				minEnergy = z;
				x = i;
				//System.out.println(z+" "+ x + " " +img.getWidth());
				
			}
		}
		seam[0] = x;
		for(int row = 1; row < img.getHeight(); row++) {
			 double leftPixel= energy(x-1, row);
			 double middlePixel = energy(x, row);
			 double rightPixel = energy(x + 1, row);
			 //System.out.println("r" + rightPixel);
			// System.out.println("l" + leftPixel);
			// System.out.println("m" + middlePixel);
			 if(leftPixel < middlePixel) {
				 if(leftPixel < rightPixel) {
					 seam[row] = -1;
					 x += -1;
				 } else {
					 seam[row] = 1;
					 x+=1;
				 }
				
			 } else {
				 if(middlePixel < rightPixel) {
					 seam[row] = 0;
				 } else {
					 seam[row] = 1;
					 x+=1;
				 }
			 }
		}
		return seam;
		
		
	}
	
	
	private void removeSeam(int[] seam) {
		BufferedImage temp = new BufferedImage(img.getWidth()-1, img.getHeight(), img.TYPE_INT_RGB);
		//BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(), img.TYPE_INT_RGB);
		int x = seam[0];
		for(int i = 0; i < x; i++) {
			//System.out.println(i + " " + x + " " + seam[0]);
			temp.setRGB(i, 0, img.getRGB(i, 0));
		}
		
		for(int j = x+1; j < img.getWidth(); j++) {
			//System.out.println(i +" "+j+" "+ " " + x + " "+seam[i]);
			temp.setRGB(j-1, 0, img.getRGB(j, 0));
		}
		
		for(int i = 1; i < img.getHeight(); i++) {
			x += seam[i];
			if(x == -1) {
				x = img.getWidth() - 1;
			} else {
				x %= img.getWidth();
			}
			
			for(int j = 0; j < x; j++) {
				//System.out.println(i +" "+j+" "+ " " + x + " "+seam[i]);
				temp.setRGB(j, i, img.getRGB(j, i));
				
			}
			
			
			for(int j = x+1; j < img.getWidth(); j++) {
				//System.out.println(i +" "+j+" "+ " " + x + " "+seam[i]);
				temp.setRGB(j-1, i, img.getRGB(j, i));
			}
			
			
		}
		img = temp;
	}
	
	
	public BufferedImage getTest() {
		return test;
	}
	public boolean getReady() {
		return ready;
	}
	
	
}
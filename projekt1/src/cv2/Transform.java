package cv2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.ArrayList;

import Jama.Matrix;
import ij.ImagePlus;

public class Transform {
	private int[][] red;
	private int[][] green;
	private int[][] blue;

	private int imageHeight;
	private int imageWidth;

	private Matrix y;
	private Matrix cB;
	private Matrix cR;

	private BufferedImage bImage;
	private ColorModel colorModel;

	public Transform(BufferedImage bImage) {
		this.bImage = bImage;
		this.colorModel = bImage.getColorModel();
		this.imageHeight = bImage.getHeight();
		this.imageWidth = bImage.getWidth();
		this.red = new int[this.imageHeight][this.imageWidth];
		this.green = new int[this.imageHeight][this.imageWidth];
		this.blue = new int[this.imageHeight][this.imageWidth];
		this.y = new Matrix(this.imageHeight, this.imageWidth);
		this.cB = new Matrix(this.imageHeight, this.imageWidth);
		this.cR = new Matrix(this.imageHeight, this.imageWidth);

	}

	public void getRGB() {
		for (int i = 0; i < this.imageHeight; i++) {
			for (int j = 0; j < this.imageWidth; j++) {
				int rgb = this.bImage.getRGB(j, i);
				red[i][j] = this.colorModel.getRed(rgb);
				green[i][j] = this.colorModel.getGreen(rgb);
				blue[i][j] = this.colorModel.getBlue(rgb);
			}
		}
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int[][] getRed() {
		return red;
	}

	public int[][] getGreen() {
		return green;
	}

	public int[][] getBlue() {
		return blue;
	}

	public Matrix getY() {
		return y;
	}

	public Matrix getcB() {
		return cB;
	}

	public Matrix getcR() {
		return cR;
	}

	public void setY(Matrix y) {
		this.y = y;
	}

	public void setcB(Matrix cB) {
		this.cB = cB;
	}

	public void setcR(Matrix cR) {
		this.cR = cR;
	}

	// vytvori obrazok rgb kompletny
	public ImagePlus setImageFromRGB(int width, int height, int[][] r, int[][] g, int[][] b) {
		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[][] rgb = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb[i][j] = new Color(r[i][j], g[i][j], b[i][j]).getRGB();
				bImage.setRGB(j, i, rgb[i][j]);
			}
		}
		return (new ImagePlus("RGB", bImage));
	}

	// vytvori obrazok rgb z jednej farby R,G,B
	public ImagePlus setImageFromRGB(int width, int height, int[][] barva, String barvaText) {
		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[][] rgb = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb[i][j] = new Color(barva[i][j], barva[i][j], barva[i][j]).getRGB();
				bImage.setRGB(j, i, rgb[i][j]);
			}
		}
		return (new ImagePlus(barvaText, bImage));
	}

	// vytvori obrazok rgb z jednej farby
	public ImagePlus setImageFromRGB(int width, int height, Matrix barva, String barvaText) {
		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[][] rgb = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb[i][j] = new Color((int) barva.get(i, j), (int) barva.get(i, j), (int) barva.get(i, j)).getRGB();
				bImage.setRGB(j, i, rgb[i][j]);
			}
		}
		return (new ImagePlus(barvaText, bImage));
	}

	// int width,int height,int [][]r,int[][]g,int[][]b
	public void convertRGBtoYcBcR() {

		for (int i = 0; i < this.imageHeight; i++) {
			for (int j = 0; j < this.imageWidth; j++) {
				double y = 0.257 * red[i][j] + 0.504 * green[i][j] + 0.098 * blue[i][j] + 16;
				this.y.set(i, j, y);
				double cB = -0.148 * red[i][j] - 0.291 * green[i][j] + 0.439 * blue[i][j] + 128;
				this.cB.set(i, j, cB);
				double cR = 0.439 * red[i][j] - 0.368 * green[i][j] - 0.071 * blue[i][j] + 128;
				this.cR.set(i, j, cR);
			}
		}

	}

	// int width,int height,Matrix y,Matrix cB,Matrix cR
	public void convertYcBcRtoRGB() {
		for (int i = 0; i < this.imageHeight; i++) {
			for (int j = 0; j < this.imageWidth; j++) {
				double r = 1.164 * (y.get(i, j) - 16) + 1.596 * (cR.get(i, j) - 128);
				double g = 1.164 * (y.get(i, j) - 16) - 0.813 * (cR.get(i, j) - 128) - 0.391 * (cB.get(i, j) - 128);
				double b = 1.164 * (y.get(i, j) - 16) + 2.018 * (cB.get(i, j) - 128);
				if (Math.round(r) > 255) {
					r = 255;
				}
				if (Math.round(r) < 0) {
					r = 0;
				}
				if (Math.round(g) > 255) {
					g = 255;
				}
				if (Math.round(g) < 0) {
					g = 0;
				}
				if (Math.round(b) > 255) {
					b = 255;
				}
				if (Math.round(b) < 0) {
					b = 0;
				}
				this.red[i][j] = (int) r;
				this.green[i][j] = (int) g;
				this.blue[i][j] = (int) b;

			}
		}
	}

	public Matrix downSample(Matrix mat) {
		Matrix nova = new Matrix(mat.getRowDimension(), mat.getColumnDimension() / 2);
		for (int r = 0; r <= mat.getRowDimension(); r++) {
			int nc = 0;
			if (r < mat.getRowDimension()) {
				for (int c = 0; c <= mat.getColumnDimension() - 2; c = c + 2) {
					nova.set(r, nc, mat.get(r, c));
					nc++;
					// System.out.println(nova.getColumnDimension() + "." +
					// nova.getRowDimension());
				}
			}
		}
		return nova;
	}

	public Matrix overSample(Matrix mat) {
		Matrix nova = new Matrix(mat.getRowDimension(), mat.getColumnDimension() * 2);
		for (int r = 0; r <= mat.getRowDimension() - 2; r++) {
			int nc = 0;
			if (r < mat.getRowDimension()) {
				for (int c = 0; c <= mat.getColumnDimension() - 2; c++) {
					nova.set(r, nc, mat.get(r, c));
					nova.set(r + 1, nc + 1, mat.get(r, c));
					nc = nc + 2;
					// System.out.println(nova.getColumnDimension() + "." +
					// nova.getRowDimension());
				}
			}
		}
		return nova;
	}

	public Matrix getDCTMatrix(int size) {
		Matrix nova = new Matrix(size, size);
		for (int i = 0; i <= nova.getRowDimension() - 1; i++) {
			for (int j = 0; j <= nova.getColumnDimension() - 1; j++) {
				if (i == 0) {
					nova.set(i, j, Math.sqrt(1.0 / size) * Math.cos(((2 * j + 1) * i * Math.PI) / (2 * size)));
				} else {
					nova.set(i, j, Math.sqrt(2.0 / size) * Math.cos(((2 * j + 1) * i * Math.PI) / (2 * size)));
				}
			}
		}
		return nova;
	}

	/*public Matrix transform(int size, Matrix inputMatrix) {
		// Matrix nova = new Matrix(size,size);
		Matrix nova = getDCTMatrix(size);
		// Matrix trans = new Matrix(size,size);
		Matrix trans = nova.transpose();
		Matrix matrix = new Matrix(size, size);

		matrix = nova.times(inputMatrix);
		matrix = matrix.times(trans);

		return matrix;
	}

	public Matrix invTransform(int size, Matrix inputMatrix) {
		// Matrix nova = new Matrix(size,size);
		Matrix nova = getDCTMatrix(size);
		// Matrix trans = new Matrix(size,size);
		Matrix trans = nova.transpose();
		Matrix matrix = new Matrix(size, size);

		matrix = trans.times(inputMatrix);
		matrix = matrix.times(nova);

		return matrix;
	}*/
	
	public Matrix transformDCT(int size, Matrix inputMatrix) {
		Matrix matrix = new Matrix(size, size);
		Matrix transformMatrix = getDCTMatrix(size);
		Matrix transpose = transformMatrix.transpose();

		matrix = transformMatrix.times(inputMatrix);
		matrix = matrix.times(transpose);

		return matrix;
	}

	public Matrix inverseTransformDCT(int size, Matrix inputMatrix) {
		Matrix matrix = new Matrix(size, size);
		Matrix transformMatrix = getDCTMatrix(size);
		Matrix transpose = transformMatrix.transpose();

		matrix = transpose.times(inputMatrix);
		matrix = matrix.times(transformMatrix);

		return matrix;
	}
	
	public Matrix transformWHT(int size, Matrix inputMatrix) {
		Matrix matrix = new Matrix(size, size);
		Matrix transformMatrix = getWHTMatrix(size);
		Matrix transpose = transformMatrix.transpose();

		matrix = transformMatrix.times(inputMatrix);
		matrix = matrix.times(transpose);

		return matrix;
	}
	
	public Matrix inverseTransformWHT(int size, Matrix inputMatrix) {
		Matrix matrix = new Matrix(size, size);
		Matrix transformMatrix = getWHTMatrix(size);
		Matrix transpose = transformMatrix.transpose();

		matrix = transpose.times(inputMatrix);
		matrix = matrix.times(transformMatrix);

		return matrix;
	}

	public Matrix getWHTMatrix(int size) {
		Matrix i = new Matrix(1, 1);
		i.set(0, 0, 1);
		Matrix nova = new Matrix(1, 1);
		while (nova.getRowDimension() + 1 <= size) {
			nova = new Matrix(i.getRowDimension() * 2, i.getColumnDimension() * 2);
			for (int j = 0; j < i.getRowDimension(); j++) {
				for (int j2 = 0; j2 < i.getColumnDimension(); j2++) {
					nova.set(j, j2, i.get(j, j2));
					nova.set(j, j2 + i.getColumnDimension(), i.get(j, j2));
					nova.set(j + i.getRowDimension(), j2, i.get(j, j2));
					nova.set(j + i.getRowDimension(), j2 + i.getColumnDimension(), -(i.get(j, j2)));
				}
			}
			i = nova;
		}
		return nova.times(1/Math.sqrt(size));
	}

	
	
	public ArrayList<Matrix> matrixToBlocks(int n, Matrix matrix){
		ArrayList<Matrix> list = new ArrayList<>();
		
		for (int i = 0; i < matrix.getRowDimension()-1; i=i+n) {
			for (int j = 0; j < matrix.getColumnDimension()-1; j=j+n) {
				list.add(matrix.getMatrix(i, i+n-1, j, j+n-1));
			}
		}
		return list;
	}
	
	public Matrix blocksToMatrix(int n , ArrayList<Matrix> list){
		double dimension = Math.sqrt(list.size() * n * n);
		System.out.println(list.size());
		Matrix matrix = new Matrix((int)dimension,(int) dimension);
		int a=0;
		for (int i = 0; i < matrix.getRowDimension()-1; i=i+n) {
			for (int j = 0; j < matrix.getColumnDimension()-1; j=j+n) {
				matrix.setMatrix(i, i+n-1,  j, j+n-1, list.get(a));
				a++;
			}
		}
		return matrix;
	}
	
	public ArrayList<Matrix> quantization (ArrayList<Matrix> list, Matrix matrixQ, double sliderValue){
		//Matrix matrixQ = stringToMatrix(quantizationMatrix);
		
		if (sliderValue >= 1 && sliderValue <= 50) {
			matrixQ.times(50.0/sliderValue);
		}if (sliderValue > 50) {
			matrixQ.times(2-(2.0*sliderValue/100));
		}
		
		for (Matrix m : list) { //foreach pro kazdy matrix a stejny index krat quantiMatrix
			//newList.add(m.arrayRightDivide(matrixQ));
			for (int i = 0; i < m.getRowDimension(); i++) {
				for (int j = 0; j < m.getColumnDimension(); j++) {
					m.set(i, j,(int) m.get(i, j)* matrixQ.get(i, j));
				}
			}
		}
		return list;
	}
	
	public ArrayList<Matrix> deQuantization (ArrayList<Matrix> list, Matrix matrixQ, double sliderValue){
	//Matrix matrixQ = stringToMatrix(quantizationMatrix);
		
		if (sliderValue >= 1 && sliderValue <= 50) {
			matrixQ.times(50.0/sliderValue);
		}if (sliderValue > 50) {
			matrixQ.times(2-(2.0*sliderValue/100));
		}
		
		for (Matrix m : list) { //foreach pro kazdy matrix a stejny index krat quantiMatrix
			//newList.add(m.arrayRightDivide(matrixQ));
			for (int i = 0; i < m.getRowDimension(); i++) {
				for (int j = 0; j < m.getColumnDimension(); j++) {
					m.set(i, j,(int) m.get(i, j)/ matrixQ.get(i, j));
				}
			}
		}
		return list;
	}
	
	/*public Matrix stringToMatrix (String string){
		String[] stringArray = string.split(" ");
		ArrayList<Integer> listOfInt = new ArrayList<>();
		int a = 0;
		for (String s : stringArray) {
			listOfInt.add(Integer.parseInt(s));
		}
		Matrix matrix = new Matrix((int)Math.sqrt(listOfInt.size()),(int)Math.sqrt(listOfInt.size()));
		for (int i = 0; i < matrix.getRowDimension(); i++) {
			for (int j = 0; j < matrix.getColumnDimension(); j++) {
				matrix.set(i, j, listOfInt.get(a));
				a++;
			}
		}
		return matrix;
	}*/
}

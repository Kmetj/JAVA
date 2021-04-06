package cv2;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Jama.Matrix;
import ij.ImagePlus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Procces implements Initializable{

	public static final int RED=1;
	public static final int GREEN=2;
	public static final int BLUE=3;
	public static final int Y=4;
	public static final int CB=5;
	public static final int CR=6;
	public static final int M444=7;
	public static final int M422=8;
	public static final int M420=9;
	public static final int M411=10;
	
	int transType;
	int blockSize=0;
	ArrayList<Matrix> blockofY=new ArrayList<>();
	ArrayList<Matrix> blockofCb=new ArrayList<>();
	ArrayList<Matrix> blockofCr=new ArrayList<>();
	
	private ImagePlus imagePlus;
	private Transform transOrig;
	private Transform transTrans;
	
	@FXML
	private Button button1; // v pripade zapisu do gui
	@FXML
	private TextField fieldR;
	@FXML
	private TextField fieldG;
	@FXML
	private TextField fieldB;
	@FXML private Slider QuantizationSlider;
	
	public ImagePlus getComponent(int component){
		ImagePlus imagePlus=null;
		switch (component) {
		case RED:
			imagePlus= transOrig.setImageFromRGB(transOrig.getImageWidth(), transOrig.getImageHeight(), transOrig.getRed(),"RED");
			break;
		case GREEN:
			imagePlus= transOrig.setImageFromRGB(transOrig.getImageWidth(), transOrig.getImageHeight(), transOrig.getGreen(),"GREEN");
			break;
		case BLUE:
			imagePlus= transOrig.setImageFromRGB(transOrig.getImageWidth(), transOrig.getImageHeight(), transOrig.getBlue(),"BLUE");
			break;
		case Y:
			imagePlus= transOrig.setImageFromRGB(transOrig.getImageWidth(), transOrig.getImageHeight(), transOrig.getY(),"Y");
			break;
		case CB:
			imagePlus= transOrig.setImageFromRGB(transOrig.getImageWidth(), transOrig.getImageHeight(), transOrig.getcB(),"CB");
			break;
		case CR:
			imagePlus= transOrig.setImageFromRGB(transOrig.getImageWidth(), transOrig.getImageHeight(), transOrig.getcR(),"CR");
			break;
		case M444:
			downsample(component);
			break;
		case M422:
			downsample(component);
			break;
		case M420:
			downsample(component);
			break;
		case M411:
			downsample(component);
			break;
		}
		
		return imagePlus;
	}
	
	/*public Procces (ImagePlus imagePlus){
		this.imagePlus=imagePlus;
		this.transOrig=new Transform(imagePlus.getBufferedImage());
		this.transTrans=new Transform(imagePlus.getBufferedImage());
		imagePlus.show();
		test();
		
	}
	*/
	
	private void test(){
		transTrans.getRGB();
		transTrans.convertRGBtoYcBcR();
		transTrans.convertYcBcRtoRGB();
		transTrans.setImageFromRGB(transTrans.getImageWidth(), transTrans.getImageHeight(), transTrans.getRed(),
				transTrans.getGreen(), transTrans.getBlue()).show();
		
	}

	public void buttonRED(ActionEvent event){
		getComponent(RED).show();
	}
	public void buttonGREEN(ActionEvent event){
		getComponent(GREEN).show();
	}
	public void buttonBLUE(ActionEvent event){
		getComponent(BLUE).show();
	}
	public void buttonY(ActionEvent event){
		getComponent(Y).show();
	}
	public void buttonCB(ActionEvent event){
		getComponent(CB).show();
	}
	public void buttonCR(ActionEvent event){
		getComponent(CR).show();
	}
	
	public void button444(ActionEvent event){
		getComponent(M444);
		
	}
	public void button422(ActionEvent event){
		getComponent(M422);
	}
	public void button411(ActionEvent event){
		getComponent(M411);
	}
	public void button420(ActionEvent event){
		getComponent(M420);
	}
	public void overSample(ActionEvent event){
		oversample();
	}
	public void bloky2x2(ActionEvent event) {
		blockofY= transTrans.matrixToBlocks(2, transTrans.getY());
		blockofCb= transTrans.matrixToBlocks(2, transTrans.getcB());
		blockofCr= transTrans.matrixToBlocks(2, transTrans.getcR());
		blockSize=2;
	}
	public void bloky4x4(ActionEvent event) {
		blockofY= transTrans.matrixToBlocks(4, transTrans.getY());
		blockofCb= transTrans.matrixToBlocks(4, transTrans.getcB());
		blockofCr= transTrans.matrixToBlocks(4, transTrans.getcR());
		blockSize=4;
	}
	public void bloky8x8(ActionEvent event) {
		blockofY= transTrans.matrixToBlocks(8, transTrans.getY());
		blockofCb= transTrans.matrixToBlocks(8, transTrans.getcB());
		blockofCr= transTrans.matrixToBlocks(8, transTrans.getcR());
		blockSize=8;
	}
	public void DCT(ActionEvent event) {
		transType=1;
		ArrayList<Matrix> noveBlockofY=new ArrayList<>();
		ArrayList<Matrix> noveBlockofcB=new ArrayList<>();
		ArrayList<Matrix> noveBlockofcR=new ArrayList<>();
		for (Matrix matrix : blockofY) {
			
			noveBlockofY.add(matrix);
		}
		for (Matrix matrix : blockofCb) {
			noveBlockofcB.add(matrix);
		}
		for (Matrix matrix : blockofCr) {
			noveBlockofcR.add(matrix);
		}
		blockofY.clear();
		blockofCb.clear();
		blockofCr.clear();
		
		for (Matrix matrix : noveBlockofY) {
			blockofY.add(transTrans.transformDCT(blockSize, matrix));
		}
		for (Matrix matrix : noveBlockofcB) {
			blockofCb.add(transTrans.transformDCT(blockSize, matrix));
		}
		for (Matrix matrix : noveBlockofcR) {
			blockofCr.add(transTrans.transformDCT(blockSize, matrix));
		}
	}
	public void WHT(ActionEvent event) {
		transType=2;
		ArrayList<Matrix> noveBlockofY=new ArrayList<>();
		ArrayList<Matrix> noveBlockofcB=new ArrayList<>();
		ArrayList<Matrix> noveBlockofcR=new ArrayList<>();
		for (Matrix matrix : blockofY) {
			
			noveBlockofY.add(matrix);
		}
		for (Matrix matrix : blockofCb) {
			noveBlockofcB.add(matrix);
		}
		for (Matrix matrix : blockofCr) {
			noveBlockofcR.add(matrix);
		}
		blockofY.clear();
		blockofCb.clear();
		blockofCr.clear();
		
		for (Matrix matrix : noveBlockofY) {
			blockofY.add(transTrans.transformWHT(blockSize, matrix));
		}
		for (Matrix matrix : noveBlockofcB) {
			blockofCb.add(transTrans.transformWHT(blockSize, matrix));
		}
		for (Matrix matrix : noveBlockofcR) {
			blockofCr.add(transTrans.transformWHT(blockSize, matrix));
		}
	}
	
	public void Quantization(ActionEvent event) {
	
		ArrayList<Matrix> noveBlockofY=new ArrayList<>();
		ArrayList<Matrix> noveBlockofcB=new ArrayList<>();
		ArrayList<Matrix> noveBlockofcR=new ArrayList<>();
		for (Matrix matrix : blockofY) {
			
			noveBlockofY.add(matrix);
		}
		for (Matrix matrix : blockofCb) {
			noveBlockofcB.add(matrix);
		}
		for (Matrix matrix : blockofCr) {
			noveBlockofcR.add(matrix);
		}
		blockofY.clear();
		blockofCb.clear();
		blockofCr.clear();
		
		if(blockSize==2) {
			blockofY=transTrans.quantization(noveBlockofY, getKvantM2na2(), QuantizationSlider.getValue());
			blockofCb=transTrans.quantization(noveBlockofcB, getKvantM2na2(), QuantizationSlider.getValue());
			blockofCr=transTrans.quantization(noveBlockofcR, getKvantM2na2(), QuantizationSlider.getValue());
		}
		if(blockSize==4) {
			blockofY=transTrans.quantization(noveBlockofY, getKvantM4na4(), QuantizationSlider.getValue());
			blockofCb=transTrans.quantization(noveBlockofcB, getKvantM4na4(), QuantizationSlider.getValue());
			blockofCr=transTrans.quantization(noveBlockofcR, getKvantM4na4(), QuantizationSlider.getValue());
		}
		if(blockSize==8) {
			blockofY=transTrans.quantization(noveBlockofY, getKvantMFarebnuZlozku8na8(), QuantizationSlider.getValue());
			blockofCb=transTrans.quantization(noveBlockofcB, getKvantMJasZlozk8na8u(), QuantizationSlider.getValue());
			blockofCr=transTrans.quantization(noveBlockofcR, getKvantMJasZlozk8na8u(), QuantizationSlider.getValue());
		}
	}
	public void deQuantization(ActionEvent event) {
		ArrayList<Matrix> noveBlockofY=new ArrayList<>();
		ArrayList<Matrix> noveBlockofcB=new ArrayList<>();
		ArrayList<Matrix> noveBlockofcR=new ArrayList<>();
		for (Matrix matrix : blockofY) {
			
			noveBlockofY.add(matrix);
		}
		for (Matrix matrix : blockofCb) {
			noveBlockofcB.add(matrix);
		}
		for (Matrix matrix : blockofCr) {
			noveBlockofcR.add(matrix);
		}
		blockofY.clear();
		blockofCb.clear();
		blockofCr.clear();
		
		if(blockSize==2) {
			blockofY=transTrans.deQuantization(noveBlockofY, getKvantM2na2(), QuantizationSlider.getValue());
			blockofCb=transTrans.deQuantization(noveBlockofcB, getKvantM2na2(), QuantizationSlider.getValue());
			blockofCr=transTrans.deQuantization(noveBlockofcR, getKvantM2na2(), QuantizationSlider.getValue());
		}
		if(blockSize==4) {
			blockofY=transTrans.deQuantization(noveBlockofY, getKvantM4na4(), QuantizationSlider.getValue());
			blockofCb=transTrans.deQuantization(noveBlockofcB, getKvantM4na4(), QuantizationSlider.getValue());
			blockofCr=transTrans.deQuantization(noveBlockofcR, getKvantM4na4(), QuantizationSlider.getValue());
		}
		if(blockSize==8) {
			blockofY=transTrans.deQuantization(noveBlockofY, getKvantMFarebnuZlozku8na8(), QuantizationSlider.getValue());
			blockofCb=transTrans.deQuantization(noveBlockofcB, getKvantMJasZlozk8na8u(), QuantizationSlider.getValue());
			blockofCr=transTrans.deQuantization(noveBlockofcR, getKvantMJasZlozk8na8u(), QuantizationSlider.getValue());
		}
	}
	
	public void InvTrans(ActionEvent event) {
		//System.out.println("tututututut");
		//System.out.println(blockofY.size());
		ArrayList<Matrix> noveBlockofY=new ArrayList<>();
		ArrayList<Matrix> noveBlockofcB=new ArrayList<>();
		ArrayList<Matrix> noveBlockofcR=new ArrayList<>();
		for (Matrix matrix : blockofY) {
			noveBlockofY.add(matrix);
		}
		for (Matrix matrix : blockofCb) {
			noveBlockofcB.add(matrix);
		}
		for (Matrix matrix : blockofCr) {
			noveBlockofcR.add(matrix);
		}
		blockofY.clear();;
		blockofCb.clear();
		blockofCr.clear();
		
		if(transType==1) {
			for (Matrix matrix : noveBlockofY) {
				blockofY.add(transTrans.inverseTransformDCT(blockSize, matrix));
			}
			for (Matrix matrix : noveBlockofcB) {
				blockofCb.add(transTrans.inverseTransformDCT(blockSize, matrix));
			}
			for (Matrix matrix : noveBlockofcR) {
				blockofCr.add(transTrans.inverseTransformDCT(blockSize, matrix));
			}
		}
		
		if(transType==2) {
			for (Matrix matrix : noveBlockofY) {
				blockofY.add(transTrans.inverseTransformWHT(blockSize, matrix));
			}
			for (Matrix matrix : noveBlockofcB) {
				blockofCb.add(transTrans.inverseTransformWHT(blockSize, matrix));
			}
			for (Matrix matrix : noveBlockofcR) {
				blockofCr.add(transTrans.inverseTransformWHT(blockSize, matrix));
			}
		}
		
		transTrans.setY(transTrans.blocksToMatrix(blockSize, blockofY));
		transTrans.setcB(transTrans.blocksToMatrix(blockSize, blockofCb));
		transTrans.setcR(transTrans.blocksToMatrix(blockSize, blockofCr));
	}
	
	public void quality(ActionEvent event){
		/*Quality q1= new Quality();
		double mse=(q1.getMSE(transOrig.getRed(), transTrans.getRed())+q1.getMSE(transOrig.getGreen(), transTrans.getGreen())+q1.getMSE(transOrig.getBlue(), transTrans.getBlue())/3);
		//double psnr=(q1.getPSNR(transOrig.getRed(), transTrans.getRed())+q1.getPSNR(transOrig.getGreen(), transTrans.getGreen())+q1.getPSNR(transOrig.getBlue(), transTrans.getBlue())/3);
		double psnr = q1.getPSNR(mse);
		fieldR.setText(Double.toString(mse));
		fieldG.setText(Double.toString(psnr));
		Matrix asd=transOrig.transform(512, transOrig.getY());
		Matrix fgh= transOrig.invTransform(512, asd);
		//transOrig.getY().minus(fgh).print(2, 2);
		//transTrans.getWHTMatrix(4);
		//transTrans.transform(512, transOrig.getY()).minus(transTrans.invTransform(512, transOrig.getY())).print(2, 2);
		//transTrans.invTransform(512, transOrig.getY()).print(2, 2);
		*/
	}
	
	public void downsample (int downsampleType) {
		Matrix cb= new Matrix(transOrig.getcB().getArray());
		Matrix cr= new Matrix(transOrig.getcR().getArray());
		ImagePlus imagePlus1=null;
		ImagePlus imagePlus2=null;
		
		switch (downsampleType) {
		case M444:
			imagePlus= transTrans.setImageFromRGB(cb.getColumnDimension(), cb.getRowDimension(), transTrans.getcB(), "M44 ,cB");
			imagePlus.show();
			
			imagePlus1= transTrans.setImageFromRGB(cr.getColumnDimension(), cr.getRowDimension(), transTrans.getcR(), "M44 ,cR");
			imagePlus1.show();
			
			imagePlus2= transTrans.setImageFromRGB(transTrans.getY().getColumnDimension(), transTrans.getY().getRowDimension(), transTrans.getY(), "M44 ,Y");
			imagePlus2.show();
			
			break;
		case M422:
			cb=transTrans.downSample(cb);
			cr=transTrans.downSample(cr);
			imagePlus= transTrans.setImageFromRGB(cb.getColumnDimension(), cb.getRowDimension(), cb, "M422 ,cB");
			imagePlus.show();
			
			imagePlus1= transTrans.setImageFromRGB(cr.getColumnDimension(), cr.getRowDimension(), cr, "M422 ,cR");
			imagePlus1.show();

			
			imagePlus2= transTrans.setImageFromRGB(transTrans.getY().getColumnDimension(), transTrans.getY().getRowDimension(), transTrans.getY(), "M422 ,Y");
			imagePlus2.show();
			transTrans.setcB(cb);
			transTrans.setcR(cr);
			break;
		case M411:
			cb=transTrans.downSample(cb);
			cr=transTrans.downSample(cr);
			cb=transTrans.downSample(cb);
			cr=transTrans.downSample(cr);
			imagePlus= transTrans.setImageFromRGB(cb.getColumnDimension(), cb.getRowDimension(), cb, "M411 ,cB");
			imagePlus.show();
			
			imagePlus1= transTrans.setImageFromRGB(cr.getColumnDimension(), cr.getRowDimension(), cr, "M411 ,cR");
			imagePlus1.show();
			
			imagePlus2= transTrans.setImageFromRGB(transTrans.getY().getColumnDimension(), transTrans.getY().getRowDimension(), transTrans.getY(), "M411 ,Y");
			imagePlus2.show();
			transTrans.setcB(cb);
			transTrans.setcR(cr);
			break;
		case M420:
			cb=transTrans.downSample(cb);
			cr=transTrans.downSample(cr);
			cb=cb.transpose();
			cr=cr.transpose();
			cb=transTrans.downSample(cb);
			cr=transTrans.downSample(cr);
			cb=cb.transpose();
			cr=cr.transpose();
			imagePlus= transTrans.setImageFromRGB(cb.getColumnDimension(), cb.getRowDimension(), cb, "M420 ,cB");
			imagePlus.show();
			
			imagePlus1= transTrans.setImageFromRGB(cr.getColumnDimension(), cr.getRowDimension(), cr, "M420 ,cR");
			imagePlus1.show();
			
			imagePlus2= transTrans.setImageFromRGB(transTrans.getY().getColumnDimension(), transTrans.getY().getRowDimension(), transTrans.getY(), "M420 ,Y");
			imagePlus2.show();
			transTrans.setcB(cb);
			transTrans.setcR(cr);
			break;
		default:
			break;
		}
	}
	
	public void oversample(){
		Matrix cb= new Matrix(transTrans.getcB().getArray());
		Matrix cr= new Matrix(transTrans.getcR().getArray());
		ImagePlus imagePlus1=null;
		ImagePlus imagePlus2=null;
		
		//422
		if(cb.getColumnDimension()/256==1 && cb.getRowDimension()/512==1){
			cb=transTrans.overSample(cb);
			cr=transTrans.overSample(cr);
			imagePlus=transTrans.setImageFromRGB(cb.getColumnDimension(), cb.getRowDimension(), cb, "overSample z M422 cB");
			imagePlus.show();
			imagePlus1=transTrans.setImageFromRGB(cr.getColumnDimension(), cr.getRowDimension(), cr, "overSample z M422 cR");
			imagePlus1.show();
			imagePlus2=transTrans.setImageFromRGB(transTrans.getY().getColumnDimension(), transTrans.getY().getRowDimension(), transTrans.getY(), "overSample z M422 Y");
			imagePlus2.show();
			transTrans.setcB(cb);
			transTrans.setcR(cr);
		}
		//411
		else if(cb.getColumnDimension()/128==1 && cb.getRowDimension()/512==1){
			cb=transTrans.overSample(cb);
			cr=transTrans.overSample(cr);
			cb=transTrans.overSample(cb);
			cr=transTrans.overSample(cr);
			imagePlus=transTrans.setImageFromRGB(cb.getColumnDimension(), cb.getRowDimension(), cb, "overSample z M411 cB");
			imagePlus.show();
			imagePlus1=transTrans.setImageFromRGB(cr.getColumnDimension(), cr.getRowDimension(), cr, "overSample z M411 cR");
			imagePlus1.show();
			imagePlus2=transTrans.setImageFromRGB(transTrans.getY().getColumnDimension(), transTrans.getY().getRowDimension(), transTrans.getY(), "overSample z M411 Y");
			imagePlus2.show();
			transTrans.setcB(cb);
			transTrans.setcR(cr);
		}
		//420
		else if(cb.getColumnDimension()/256==1 && cb.getRowDimension()/256==1){
			cb=transTrans.overSample(cb);
			cr=transTrans.overSample(cr);
			cb=cb.transpose();
			cr=cr.transpose();
			cb=transTrans.overSample(cb);
			cr=transTrans.overSample(cr);
			cb=cb.transpose();
			cr=cr.transpose();
			imagePlus=transTrans.setImageFromRGB(cb.getColumnDimension(), cb.getRowDimension(), cb, "overSample z M420 cB");
			imagePlus.show();
			imagePlus1=transTrans.setImageFromRGB(cr.getColumnDimension(), cr.getRowDimension(), cr, "overSample z M420 cR");
			imagePlus1.show();
			imagePlus2=transTrans.setImageFromRGB(transTrans.getY().getColumnDimension(), transTrans.getY().getRowDimension(), transTrans.getY(), "overSample z M420 Y");
			imagePlus2.show();
			transTrans.setcB(cb);
			transTrans.setcR(cr);
		}
		transTrans.convertYcBcRtoRGB();
	}
	public Matrix getKvantMJasZlozk8na8u(){
		String eightY = "16 11 10 16 24 40 51 61 12 12 14 19 26 58 60 55 14 13 16 24 40 57 69 56 14 17 22 29 51 87 80 62 18 22 37 56 68 109 103 77 24 35 55 64 81 104 113 92 49 64 78 87 103 121 120 101 72 92 95 98 112 100 103 99";
		String[] eightYArray = eightY.split(" ");
		ArrayList<Integer> asd = new ArrayList<>();
		int a = 0;
		for (String string : eightYArray) {
			asd.add(Integer.parseInt(string));
		}
		Matrix matrix = new Matrix(8, 8);
		for (int i = 0; i < matrix.getRowDimension(); i++) {
			for (int j = 0; j < matrix.getColumnDimension(); j++) {
				matrix.set(i, j, asd.get(a));
				a++;
			}
		}
		return matrix;
	}
	
	public Matrix getKvantMFarebnuZlozku8na8(){
		String eightY = "17 18 24 47 99 99 99 99 18 21 26 66 99 99 99 99 24 26 56 99 99 99 99 99 47 66 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99 99";
		String[] eightYArray = eightY.split(" ");
		ArrayList<Integer> asd = new ArrayList<>();
		int a = 0;
		for (String string : eightYArray) {
			asd.add(Integer.parseInt(string));
		}
		Matrix matrix = new Matrix(8, 8);
		for (int i = 0; i < matrix.getRowDimension(); i++) {
			for (int j = 0; j < matrix.getColumnDimension(); j++) {
				matrix.set(i, j, asd.get(a));
				a++;
			}
		}
		return matrix;
	}
	
	public Matrix getKvantM2na2(){
		String eightY = "10 14 14 20";
		String[] eightYArray = eightY.split(" ");
		ArrayList<Integer> asd = new ArrayList<>();
		int a = 0;
		for (String string : eightYArray) {
			asd.add(Integer.parseInt(string));
		}
		Matrix matrix = new Matrix(2, 2);
		for (int i = 0; i < matrix.getRowDimension(); i++) {
			for (int j = 0; j < matrix.getColumnDimension(); j++) {
				matrix.set(i, j, asd.get(a));
				a++;
			}
		}
		return matrix;
	}
	
	public Matrix getKvantM4na4(){
		String eightY = "10 14 20 24 14 20 24 27 20 24 27 30 24 27 30 34";
		String[] eightYArray = eightY.split(" ");
		ArrayList<Integer> asd = new ArrayList<>();
		int a = 0;
		for (String string : eightYArray) {
			asd.add(Integer.parseInt(string));
		}
		Matrix matrix = new Matrix(4, 4);
		for (int i = 0; i < matrix.getRowDimension(); i++) {
			for (int j = 0; j < matrix.getColumnDimension(); j++) {
				matrix.set(i, j, asd.get(a));
				a++;
			}
		}
		return matrix;
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.imagePlus = new ImagePlus("lena_std.jpg");
		this.imagePlus.show();
		this.transOrig=new Transform(imagePlus.getBufferedImage());
		this.transTrans=new Transform(imagePlus.getBufferedImage());
		this.transOrig.getRGB();
		this.transOrig.convertRGBtoYcBcR();
		this.transTrans.getRGB();
		this.transTrans.convertRGBtoYcBcR();
	}
	
	
}

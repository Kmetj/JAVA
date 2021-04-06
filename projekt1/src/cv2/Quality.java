package cv2;

public class Quality {

	public double getPSNR(/*int[][] orig,int[][] trans*/double MSE){
		/*double suma=10* (Math.log((Math.pow(2, 8)-1)*(Math.pow(2, 8)-1)/getMSE(orig, trans)));
		
		return suma;*/
		double PSNR = 0;
		PSNR = 10*Math.log10(((Math.pow(2, 8)-1)*(Math.pow(2, 8)-1))/MSE);
		return PSNR;
	}
	public double getMSE(int[][] orig,int[][] trans){
		/*double suma=0;
		for (int i = 0; i < orig.length-1; i++) {
			for (int j = 0; j < orig.length-1; j++) {
				suma=suma+(orig[i][j]-trans[i][j])*(orig[i][j]-trans[i][j]);
				//System.out.println(suma);
			}
		}
		suma=suma/(orig.length*orig.length);
		
		return suma;*/
		double MSE = 0;
		for (int i=0; i < (orig.length-1); i++)
		{
			for (int j=0; j < (orig[0].length-1); j++)
			{
				MSE = MSE+(orig[i][j]-trans[i][j])*(orig[i][j]-trans[i][j]);
			}
		}
		MSE = MSE*(1.0/(orig.length*orig[0].length));
		return MSE;
	}
}

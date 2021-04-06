package uloha;

public class Junior extends Osoba{
	
	private int vek;
	
	public Junior(String meno, String priezvisko, int vek) {
		super(meno, priezvisko, vek);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getVek() {
		// TODO Auto-generated method stub
		return vek;
	}

	@Override
	public void setVek(int vek) {
		if(vek <= 26){
			this.vek = vek;
		}
		else{
			System.out.println("Osoba nesplna zadany vek");
		}	
	}
}
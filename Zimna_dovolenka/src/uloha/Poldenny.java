package uloha;

public class Poldenny extends Skipas{
	
	private String castDna;
	
	public Poldenny(int identifikacneCislo, int casZakupenia, String castDna, int cena, Osoba osoba) {
		super(identifikacneCislo, casZakupenia, cena, osoba);
		this.castDna = castDna;
	}

	/**
	 * @return the castDna
	 */
	public String getCastDna() {
		return castDna;
	}

	/**
	 * @param castDna the castDna to set
	 */
	public void setCastDna(String castDna) {
		this.castDna = castDna;
	}
}

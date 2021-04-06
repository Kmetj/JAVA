package uloha;

public abstract class Skipas {

	private int identifikacneCislo;
	private int casZakupenia;
	
	private Osoba osoba;
	private int cena;
	
	public Skipas(){}
	
	public Skipas(int identifikacneCislo, int casZakupenia, int cena, Osoba osoba){
		super();
		this.identifikacneCislo = identifikacneCislo;
		this.casZakupenia = casZakupenia;
		this.cena = cena;
		this.osoba = osoba;
	}
	
	/**
	 * @return the identifikacneCislo
	 */
	public int getIdentifikacneCislo() {
		return identifikacneCislo;
	}
	/**
	 * @param identifikacneCislo the identifikacneCislo to set
	 */
	public void setIdentifikacneCislo(int identifikacneCislo) {
		this.identifikacneCislo = identifikacneCislo;
	}
	/**
	 * @return the casZakupenia
	 */
	public int getCasZakupenia() {
		return casZakupenia;
	}
	/**
	 * @param casZakupenia the casZakupenia to set
	 */
	public void setCasZakupenia(int casZakupenia) {
		this.casZakupenia = casZakupenia;
	}
	/**
	 * @return the osoba
	 */
	public Osoba getOsoba() {
		return osoba;
	}
	/**
	 * @param osoba the osoba to set
	 */
	public void setOsoba(Osoba osoba) {
		this.osoba = osoba;
	}
	/**
	 * @return the cena
	 */
	public int getCena() {
		return cena;
	}
	/**
	 * @param cena the cena to set
	 */
	public void setCena(int cena) {
		this.cena = cena;
	}	
}

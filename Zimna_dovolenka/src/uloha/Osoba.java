package uloha;

public abstract class Osoba {
	
	private String meno;
	private String priezvisko;
	
	private int vek;

	public Osoba(String meno, String priezvisko, int vek) {
		super();
		this.meno = meno;
		this.priezvisko = priezvisko;
		this.vek = vek;
	}

	/**
	 * @return the meno
	 */
	public String getMeno() {
		return meno;
	}

	/**
	 * @param meno the meno to set
	 */
	public void setMeno(String meno) {
		this.meno = meno;
	}

	/**
	 * @return the priezvisko
	 */
	public String getPriezvisko() {
		return priezvisko;
	}

	/**
	 * @param priezvisko the priezvisko to set
	 */
	public void setPriezvisko(String priezvisko) {
		this.priezvisko = priezvisko;
	}

	/**
	 * @return the vek
	 */
	public abstract int getVek();

	/**
	 * @param vek the vek to set
	 */
	public abstract void setVek(int vek);
	
	
}

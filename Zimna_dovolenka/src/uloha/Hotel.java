package uloha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Hotel {
	
	private String nazovHotela;
	private Random rand = new Random(20071969);
	
	//private ArrayList<Osoba> ubytovaneOsoby = new ArrayList<>();
	private HashMap<Osoba, Skipas> ubytovaneOsoby = new HashMap<>();
	
	public Hotel(String nazovHotela){
		this.nazovHotela = nazovHotela;
	}

	/**
	 * @return the nazovHotela
	 */
	public String getNazovHotela() {
		return nazovHotela;
	}

	/**
	 * @param nazovHotela the nazovHotela to set
	 */
	public void setNazovHotela(String nazovHotela) {
		this.nazovHotela = nazovHotela;
	}
	
	public void kupitListokCelodenny(Osoba osoba){
		Celodenny novySkipas;
		if(osoba.getClass() == Junior.class || osoba.getClass() == Senior.class){
			novySkipas = new Celodenny(Math.abs(rand.nextInt()), Math.abs(rand.nextInt()), 300, osoba);
		}
		else{
			novySkipas = new Celodenny(rand.nextInt(), rand.nextInt(), 600, osoba);
		}
		ubytovaneOsoby.put(osoba, novySkipas);
	}
	
	public void kupitListokPoldenny(Osoba osoba, String castDna){
		Poldenny novySkipas;
		if(osoba.getClass() == Junior.class || osoba.getClass() == Senior.class){
			novySkipas = new Poldenny(Math.abs(rand.nextInt()), Math.abs(rand.nextInt()), castDna, 200, osoba);
		}
		else{
			novySkipas = new Poldenny(Math.abs(rand.nextInt()), Math.abs(rand.nextInt()), castDna, 400, osoba);
		}
		ubytovaneOsoby.put(osoba, novySkipas);
	}
	
	public void kupitListok2Hodiny(Osoba osoba){
		Dvojhodinovy novySkipas;
		if(osoba.getClass() == Junior.class || osoba.getClass() == Senior.class){
			novySkipas = new Dvojhodinovy(Math.abs(rand.nextInt()), Math.abs(rand.nextInt()), 100, osoba);
		}
		else{
			novySkipas = new Dvojhodinovy(Math.abs(rand.nextInt()), Math.abs(rand.nextInt()), 200, osoba);
		}
		ubytovaneOsoby.put(osoba, novySkipas);
	}
	
	public void pocetVydanychSkipasov(){
		
		int pocetCelodennych = 0,
			pocetPoldennych = 0,
			pocet2Hodinovych = 0; 	
		
		for (Skipas skipas : ubytovaneOsoby.values()) {
			if(skipas.getClass() == Celodenny.class)
				pocetCelodennych++;
			else if(skipas.getClass() == Poldenny.class)
				pocetPoldennych++;
			else
				pocet2Hodinovych++;
		}
		
		System.out.println("Pocet vydanych skipasov: " + ubytovaneOsoby.size());
		System.out.println("pocetCelodennych: " + pocetCelodennych + " pocetPoldennych: " + pocetPoldennych + " pocet2Hodinovych: " + pocet2Hodinovych);
	}
	
	public void vypisOsobyASkipasy(){
		
		for (Map.Entry<Osoba, Skipas> skipas : ubytovaneOsoby.entrySet()) {
			System.out.println("Meno: " + skipas.getKey().getMeno() + " " + skipas.getKey().getPriezvisko() + " / " + " skipas: " + skipas.getValue().getClass().getSimpleName() + " " + skipas.getValue().getIdentifikacneCislo() + " " + skipas.getValue().getCasZakupenia() + " " + skipas.getValue().getCena());
		}
	}
	
}

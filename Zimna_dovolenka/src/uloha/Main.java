package uloha;

public class Main {

	public static void main(String[] args) {

		Hotel hotel = new Hotel("Kasablanka");
		
		Senior karol = new Senior("Karol", "Novak", 32);
		
		Dospely milan = new Dospely("Milan", "Cierny", 32);
		
		Dospely peter = new Dospely("Peter", "Nojman", 32);
		
		hotel.kupitListokCelodenny(karol);
		
		hotel.kupitListokPoldenny(milan, "dopoludnie");
		
		hotel.kupitListok2Hodiny(peter);
		
		hotel.pocetVydanychSkipasov();

		hotel.vypisOsobyASkipasy();
	}

}

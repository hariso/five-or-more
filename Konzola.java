import java.util.Scanner;

public class Konzola 
{	
	public static void main(String[] args) {
		int brojVrsta, brojKolona, brojPlocica;
		Scanner unosT  = new Scanner (System.in);
		
		//Unos dimenzija
		System.out.println("Unesite dimenzije polja na kojem zelite igrati, prvo broj vrsta, zatim broj kolona:");
		brojVrsta = unosT.nextInt();
		brojKolona = unosT.nextInt();
		
		//Unos broja plocica
		System.out.println("Unesite broj plocica sa kojim igrate:");
		brojPlocica = unosT.nextInt();
		
		//Instanciranje nove igre
		Igra novaIgra = new Igra(brojVrsta,brojKolona,brojPlocica); 
		novaIgra.postaviTri(novaIgra.nasumicnaTriBroja());
		
		//matrica u koju cemo stavljati podatke o stanju igre
		int[][] mat;
		
		//niz u kojeg cemo stavljati nasumicne brojeve kako bismo ih prikazali
		int[] triBroja = new int[3]; 
		
		//x i y koordinata izvornog polja i polja na koje premjestamo
		int izvornoPoljeX, izvornoPoljeY, odredisnoPoljeX, odredisnoPoljeY;
		
	//do-while petlja koja se vrti dok se igra ne zavrsi
	
	do
	{
		mat = novaIgra.dajTabelu(); 
		System.out.println("BODOVI: "+novaIgra.dajBodove());
		ispisiMat(mat);
		
		triBroja = novaIgra.nasumicnaTriBroja();		
		System.out.println("Sljedeca tri su: "+triBroja[0]+" "+triBroja[1]+" "+triBroja[2]+" ");
		
		//--------------------------------------------------------------------
		//Ispravan unos koordinata
		int koordinateIzvornogPolja [] = new int [2];
		koordinateIzvornogPolja = unesiIzvornoPolje(novaIgra, mat);
		
		izvornoPoljeX = koordinateIzvornogPolja[0];
		izvornoPoljeY = koordinateIzvornogPolja[1];
		
		int koordinateOdredisnogPolja [] = new int [2];
		koordinateOdredisnogPolja = unesiOdredisnoPolje(novaIgra, mat, izvornoPoljeX, izvornoPoljeY);
		
		odredisnoPoljeX = koordinateOdredisnogPolja[0];
		odredisnoPoljeY = koordinateOdredisnogPolja[1];
				
	
		//Kraj segmenta koji obezbjedjuje ispravan unos koord.
		//----------------------------------------------------
	
		
		//--------------------------------------------------------------------
		// Prenos polja, 
	
		if (novaIgra.postojiLiPut(izvornoPoljeX, izvornoPoljeY, odredisnoPoljeX, odredisnoPoljeY))
		{
			novaIgra.premjesti(izvornoPoljeX, izvornoPoljeY, odredisnoPoljeX, odredisnoPoljeY);
			if (!novaIgra.postojiPetica(odredisnoPoljeX, odredisnoPoljeY))
			{
				if (novaIgra.brojPraznih() >= 3)
					novaIgra.postaviTri(triBroja);
			}
			System.out.println();
		}
		//		
		//--------------------------------------------------------------------


		
	} while (!novaIgra.jePrazna() && !(novaIgra.brojPraznih() < 3));
				
	mat = novaIgra.dajTabelu(); 
	System.out.println("BODOVI: "+novaIgra.dajBodove());
	ispisiMat(mat);
		
	if (novaIgra.jePrazna())
		System.out.println("Cestitam! Uspjesno ste protracili vrijeme da biste pobjedili u ovoj igri!");
	
	if (novaIgra.brojPraznih() < brojPlocica)
			System.out.println("Zao mi je, u sljedecem kolu... igri vise srece!");
		
	}
	
	//-------------------------------------------------------
	//Ispis niza
	//-------------------------------------------------------
	public static void ispisiNiz (int[] n) 
	{
		for (int i = 0; i < n.length; i++)
			System.out.print(n[i]+" ");
		System.out.println();
	}
	
	//-------------------------------------------------------
	//Ispis matrice u konzolnom interfejsu
	//-------------------------------------------------------
	public static void ispisiMat (int[][] m) 
	{
		for (int i = 0; i <= m.length; i++)
		{
			if (i==0) 
			{
				System.out.print("\\  ");
				for (int k = 1; k <= m[i].length; k++)
					System.out.print(k+" ");
					System.out.println();
					
			}
			else 
			{
				System.out.print(i + "  ");
				
				for (int j = 0; j < m[i-1].length; j++)			
					System.out.print(m[i-1][j] + " ");
				
				System.out.println();
			}
			
		}
	}

	public static int [] unesiIzvornoPolje (Igra novaIgra, int[][] mat)
	{
		int izvornoPoljeX, izvornoPoljeY, rezultat [];
		rezultat = new int [2];
		
		Scanner unosT  = new Scanner (System.in);
		do {
			System.out.println("Unesite koordinate polja koje prenosite");
			izvornoPoljeX = unosT.nextInt() - 1;
			izvornoPoljeY = unosT.nextInt() - 1;
		} while (!novaIgra.valid(izvornoPoljeX, izvornoPoljeY) || mat[izvornoPoljeX][izvornoPoljeY] == 0);
		
		rezultat[0] = izvornoPoljeX;
		rezultat[1] =  izvornoPoljeY;
		
		return rezultat;
		
	}
	
	public static int [] unesiOdredisnoPolje (Igra novaIgra, int [][] mat, int izvornoPoljeX, int izvornoPoljeY)
	{
		int rezultat [] = new int [2];
		int odredisnoPoljeX, odredisnoPoljeY;
		Scanner unosT  = new Scanner (System.in);
		
		do {
			System.out.println("Unesite koordinate odredisnog polja.");
			odredisnoPoljeX = unosT.nextInt() - 1;
			odredisnoPoljeY = unosT.nextInt() - 1;
			
		} while (!novaIgra.valid(odredisnoPoljeX, odredisnoPoljeY) 
				|| (mat[odredisnoPoljeX][odredisnoPoljeY] != 0) 
				|| (!novaIgra.postojiLiPut(izvornoPoljeX, izvornoPoljeY, odredisnoPoljeX, odredisnoPoljeY)));
		
		
		rezultat [0] = odredisnoPoljeX;
		rezultat [1] = odredisnoPoljeY;
		return rezultat;
	}
}
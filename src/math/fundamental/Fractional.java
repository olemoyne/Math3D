package math.fundamental;


/**
 * Représentation d'un décimal sous la forme d'une faction de deux entiers
 * 
 * 
 * @author olemoyne
 *
 */
public class Fractional implements Comparable<Fractional> {

	/* initialisation des nombres premiers **/
	public static final long[] premiers = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
	
	/* Numérateur  et dénominateurs **/
	long numerateur;
	long denominateur;
	
	/*  gestion de la racine **/
	boolean racine;
	
	/* Précision de calcul de la valeur **/
	int precision;
	
	/** 
	 * Constructeur principal
	 *   par défaut, une précision au millionnième
	 *  
	 */
	public Fractional () {
		numerateur= 0;
		denominateur = 1;
		racine = false;
		
		precision = 6;
	}

	/** 
	 * Constructeur avec numerateur
	 *   par défaut, une précision au millionnième
	 *  
	 */
	public Fractional (long val) {
		numerateur= val;
		denominateur = 1;
		racine = false;

		precision = 6; 
	}

	/** 
	 * Constructeur avec numerateur et dénominateur
	 *  
	 */
	public Fractional (long num, long denum) {
		numerateur= num;
		denominateur = denum;
		racine = false;

		precision = 6;
	}

	/** 
	 * Constructeur avec un autre fractional
	 *  
	 */
	public Fractional (Fractional fr) {
		numerateur= fr.numerateur;
		denominateur = fr.denominateur;
		racine = fr.racine;

		precision = fr.precision;
	}

	
	/** 
	 * comparaison avec un autre Fractional
	 *    - nullité 
	 *    - égalité
	 *    - différence
	 *  
	 */
	@Override
	public int compareTo(Fractional other) {
		if (other == null)
			throw new NullPointerException("Comparaison nulle");

		// Gestion de l'égalité
		if ( (other.numerateur == numerateur) && (other.denominateur == denominateur) &&(other.racine == racine)) {
			return 0;
		}
		
		if (other.racine == racine) { 
			if (numerateur*other.denominateur > other.numerateur*denominateur) {
				return 1;
			} else {
				return -1;
			}
		} else { // on compare les carrés
			if (other.racine == true) {
				if (numerateur*numerateur*other.denominateur > other.numerateur*denominateur*denominateur) {
					return 1;
				} else {
					return -1;
				}
			} else {
				if (numerateur*other.denominateur*other.denominateur > other.numerateur*other.numerateur*denominateur) {
					return 1;
				} else {
					return -1;
				}
			}
		}
	}

	/** 
	 * execute la réduction de la faction en tentant de diviser les numerateurs et dénominateurs par un nombre premier 
	 */
	private void reduction() {
		for (long div : premiers) {
			while ((numerateur%div == 0)&&(denominateur%div == 0)) {
				denominateur = denominateur/div;
				numerateur = numerateur/div;
			}
		}
	}

	
	/***Gestion des opérations classiques   **/
	
	/**
	 *  Exécute la division entre deux fractions
	 *     -> problème : gestion de la racine
	 *  
	 * @return le resultat de la division
	 */
	public Fractional divide (Fractional div) {
		Fractional fr = new Fractional(this);
		if (div.precision > this.precision) fr.precision = div.precision;

		if (div.racine == racine) { 
			fr.denominateur = denominateur*div.numerateur;
			fr.numerateur = numerateur*div.denominateur;
			return fr;
		} else {
			fr.racine = true;
			if (div.racine == true) {
				fr.numerateur = numerateur*numerateur*div.denominateur;
				fr.denominateur = denominateur*denominateur*div.numerateur;
			} else {
				fr.numerateur = numerateur*div.denominateur*div.denominateur;
				fr.denominateur = denominateur*div.numerateur*div.numerateur;
			}
		}
		fr.reduction();
		return fr;
	}

	
	/**
	 *  Exécute la multiplication entre deux fractions
	 *     -> problème : gestion de la racine
	 *  
	 * @return le resultat de la multiplication
	 */
	public Fractional multiply (Fractional multiple) {
		Fractional fr = new Fractional(this);
		if (multiple.precision > this.precision) fr.precision = multiple.precision;

		if (multiple.racine == racine) { 
			fr.denominateur = denominateur*multiple.denominateur;
			fr.numerateur = numerateur*multiple.numerateur;
			return fr;
		} else {
			fr.racine = true;
			if (multiple.racine == true) {
				fr.numerateur = numerateur*numerateur*multiple.numerateur;
				fr.denominateur = denominateur*denominateur*multiple.denominateur;
			} else {
				fr.numerateur = numerateur*multiple.numerateur*multiple.numerateur;
				fr.denominateur = denominateur*multiple.denominateur*multiple.denominateur;
			}
		}
		fr.reduction();
		return fr;
	}


	/**
	 *  Exécute l'addition entre deux fractions
	 *     -> problème : gestion de la racine
	 *  
	 * @return le resultat de la l'addition
	 */
	public Fractional add (Fractional add) {
		Fractional fr = new Fractional(this);
		if (add.precision > this.precision) fr.precision = add.precision;

		if (add.racine == racine) { 
			fr.numerateur  = denominateur*add.numerateur + add.denominateur*numerateur ;
			fr.denominateur = denominateur*add.denominateur;
			return fr;
		} else {
			fr.racine = true;
			if (add.racine == true) {
				fr.numerateur  = denominateur*denominateur*add.numerateur + add.denominateur*numerateur*numerateur ;
				fr.denominateur = denominateur*denominateur*add.denominateur;
			} else {
				fr.numerateur  = denominateur*add.numerateur*add.numerateur + add.denominateur*add.denominateur*numerateur ;
				fr.denominateur = denominateur*add.denominateur*add.denominateur;
			}
		}
		fr.reduction();
		return fr;
	}


	/**
	 *  Exécute la soustraction entre deux fractions
	 *     -> problème : gestion de la racine
	 *  
	 * @return le resultat de la l'addition
	 */
	public Fractional minus (Fractional add) {
		Fractional fr = new Fractional(this);
		if (add.precision > this.precision) fr.precision = add.precision;

		if (add.racine == racine) { 
			fr.numerateur  =  add.denominateur*numerateur - denominateur*add.numerateur;
			fr.denominateur = denominateur*add.denominateur;
			return fr;
		} else {
			fr.racine = true;
			if (add.racine == true) {
				fr.numerateur  = add.denominateur*numerateur*numerateur - denominateur*denominateur*add.numerateur ;
				fr.denominateur = denominateur*denominateur*add.denominateur;
			} else {
				fr.numerateur  =  + add.denominateur*add.denominateur*numerateur - denominateur*add.numerateur*add.numerateur;
				fr.denominateur = denominateur*add.denominateur*add.denominateur;
			}
		}
		fr.reduction();
		return fr;
	}
	
	


	
	/** 
	 * Permet d'exécuter des tests de validation des opérations sur les fractions 
	 **/
	public static void main(String[] args) {
		System.out.println(Long.MAX_VALUE);
	
	}


}

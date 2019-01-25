package math.fundamental;


/**
 * Gestion des valeurs entières au delà de Long.MAXVALUE
 *   --> ces nombres sont gérés comme des polynomes  
 * 
 * @author olemoyne
 *
 */
public class VeryLong implements Comparable<VeryLong>{
	// Limite des long      = 9223372036854775807
	public final long PIVOT = 1000000000000000000l; // 10 exp 18
    public final long HALF =     1000000000l;
	private long multiple;
	private long reste;

	/**
	 * Constructeurs 
	 */
	public VeryLong () {
		reste = 0;
		multiple = 0;
	}

	public VeryLong (long val) {
		reste = val;
		if ((reste > PIVOT)||(reste > -1*PIVOT)) {
			reste = val % PIVOT;
			multiple = val / PIVOT;
		} else {
			multiple = 0;
		}
	}
	

	public VeryLong (VeryLong other) {
		reste = other.reste;
		multiple = other.multiple;
	}
	
	/**
	 * Opérations : x / + - racine % >
	 */
	
	public VeryLong multiply(VeryLong other) {
		// Vérifie que les chiffres sont cohérents 
		if ((other.multiple)*(multiple) > 0) 
			throw new NullPointerException("Very long size exceeded the limit"); 
		
		VeryLong ret = new VeryLong();
		
		if (multiple == 0) {
			ret.multiple = other.multiple;
		} else {
			ret.multiple = multiple;
		}
		
		// Dépassement de la limite
		long me = reste/HALF;
		long meR = reste%HALF;
		
		long he = other.reste/HALF;
		long heR = other.reste%HALF;
		if ( (me)*(he) > 1) {
			ret.multiple += me*he;
			ret.reste = (me*heR + meR*he)*HALF + meR*heR;
		} else {
			ret.reste = reste*other.reste;			
		}
		
		return ret;
	}

	/**
	 * Division entre deux very longs
	 * 
	 * @param other
	 * @return
	 */
	public VeryLong divide(VeryLong other) {
		VeryLong ret = new VeryLong();
		
		if (other.multiple == 0) {
			ret.multiple = multiple/other.reste;
			ret.reste = PIVOT*multiple%other.reste+ reste/other.reste;
			return ret;
		}
		throw new NullPointerException("Division by a very long is not possible");
	}
	
	public VeryLong add(VeryLong other) {
		VeryLong ret = new VeryLong();
		
		ret.multiple = multiple + other.multiple;
		long val = reste + other.reste;
		
		// Dépassement de la limite
		if ((ret.reste > PIVOT)||(ret.reste > -1*PIVOT)) {
			ret.reste = val % PIVOT;
			ret.multiple += val / PIVOT;
		} 
		
		return ret;
	}

	public VeryLong minus(VeryLong other) {
		VeryLong ret = new VeryLong();
		
		ret.multiple = multiple - other.multiple;
		long val = reste - other.reste;
		
		// Dépassement de la limite
		if ((ret.reste > PIVOT)||(ret.reste > -1*PIVOT)) {
			ret.reste = val % PIVOT;
			ret.multiple += val / PIVOT;
		} 
		
		return ret;
	}

	
	public VeryLong mod(VeryLong other) {
		VeryLong ret = new VeryLong();
		
		if (other.multiple == 0) {
			ret.reste = (PIVOT*multiple%other.reste+ reste)%other.reste;
			return ret;
		}
		throw new NullPointerException("Division by a very long is not possible");
	}
	
	public VeryLong mod(long other) {
		VeryLong ret = new VeryLong();
		
		ret.reste = (PIVOT*multiple%other+ reste)%other;
		return ret;
	}

	@Override
	public int compareTo(VeryLong other) {
		if (other == null) throw new NullPointerException("Null object comparaison");
		if (other.multiple == multiple) {
			if (other.reste == reste) return 0;
			else {
				if (other.reste > reste) return -1;
				else return 1;
			}
		} else {
			if (other.multiple > multiple) return -1;
			else return 1;
		}
	}

	
	/**
	 * Testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

	}


}

package Logico;

public class Efectivo extends MetodoDePago {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String moneda;
	private float montoPagado;
	
	public Efectivo(String nombreTitular, String idMetodo, String moneda, float montoPagado) {
		super(nombreTitular, idMetodo);
		this.moneda = moneda;
		this.montoPagado = montoPagado;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public float getMontoPagado() {
		return montoPagado;
	}
	public void setMontoPagado(float montoPagado) {
		this.montoPagado = montoPagado;
	}
	
	public Efectivo(float montoPagado) {
        super("Consumidor Final", "CASH-GEN"); // Datos genéricos
        this.moneda = "DOP"; // Pesos Dominicanos por defecto
        this.montoPagado = montoPagado;
    }
	
	

}

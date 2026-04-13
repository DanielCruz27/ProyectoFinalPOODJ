package Logico;

public class Cuenta extends MetodoDePago {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double numeroCuenta;
	private String tipoDeCuenta;
	private String banco;
	
	public Cuenta(String nombreTitular, String idMetodo, double numeroCuenta, String tipoDeCuenta, String banco) {
		super(nombreTitular, idMetodo);
		this.numeroCuenta = numeroCuenta;
		this.tipoDeCuenta = tipoDeCuenta;
		this.banco = banco;
	}
	public double getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(double numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public String getTipoDeCuenta() {
		return tipoDeCuenta;
	}
	public void setTipoDeCuenta(String tipoDeCuenta) {
		this.tipoDeCuenta = tipoDeCuenta;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	
	@Override
	public String toString() {
	    return "Banco: " + banco + " - No. " + numeroCuenta;
	}
	

}

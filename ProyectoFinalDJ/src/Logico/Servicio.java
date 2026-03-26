package Logico;

public abstract class Servicio {
	
	protected String idServicio;
	protected String nombreServicio;
	protected float precioBase;
	
	public Servicio(String idServicio, String nombreServicio, float precioBase) {
		super();
		this.idServicio = idServicio;
		this.nombreServicio = nombreServicio;
		this.precioBase = precioBase;
	}

	public String getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public float getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(float precioBase) {
		this.precioBase = precioBase;
	}

}

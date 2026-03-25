package Logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pago {

	

	private String idFactura;
	private LocalDate fechaEmision;
	private float montoTotal;
	private boolean estadoPago;
	private MetodoDePago metodoUtilizado;
	private float itbis;
	private Contrato elContrato;
}
